/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.inventory

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.inventory.data.InventoryDatabase
import com.example.inventory.data.Item
import com.example.inventory.data.ItemDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/* Menunjukkan bahwa kelas uji ini akan dijalankan dengan test runner JUnit4 dari Andorid,
yang cocok untuk pengujian di Android */
@RunWith(AndroidJUnit4::class)
class ItemDaoTest {

    private lateinit var itemDao: ItemDao /* objek akses data yang akan diuji */
    private lateinit var inventoryDatabase: InventoryDatabase /* Referensi ke database InventoryDatabase yang dibuat di memori (selama pengujian berlangsung) */
    private val item1 = Item(1, "Apples", 10.0, 20) /* objek item yang digunakan sebagai data sampel dalam pengujian */
    private val item2 = Item(2, "Bananas", 15.0, 97)

    /* createDb dijalankan sebelum setiap pengujian dimulai untuk membuat database Room
    yang hanya ada di (in-memory) sehingga data akan hilang setelah pengujian selesai
     */
    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        inventoryDatabase = Room.inMemoryDatabaseBuilder(context, InventoryDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries() /* diaktifkan untuk mengizinkan operasi database di thread utama */
            .build()
        itemDao = inventoryDatabase.itemDao() /* diinisialisasi dengan memanggil itemDao() dari instance InventoryDatabase */
    }

    /* closeDb untuk menutup koneksi ke database setelah pengujian selesai */
    @After //untuk menandai metode yang harus dijalankan setelah setiap pengujian selesai
    @Throws(IOException::class) /* untuk mendeklarasikan bahwa metode tersebut dapat melemparkan
    sebuah exception dengan tipe yang disebutkan (IOException) */
    fun closeDb() {
        inventoryDatabase.close()
    }

    /* daoInsert_insertsItemIntoDB() ini menguji ketika sebuah item dimasukkan ke dalam database menggunakan DAO,
    * item tersebut dapat diambil kembali dengan benar di database */
    @Test /* metode pengujian yang akan dijalankan untuk memverifikasi apakah fungsi yang diuji berperilaku sesuai harapan*/
    @Throws(Exception::class) /* menandakan bahwa ini dapat melemparkan pengecualian (Exception) saat dijalankan */
    fun daoInsert_insertsItemIntoDB() = runBlocking {
        addOneItemToDb() /* memanggil untuk memasukkan item1 ke dalam database */
        val allItems = itemDao.getAllItems().first()
        assertEquals(allItems[0], item1)
    }

    /* Untuk menguji apakah fungsi getAllItems mengembalikan semua item yang ada di database */
    @Test
    @Throws(Exception::class)
    fun daoGetAllItems_returnsAllItemsFromDB() = runBlocking {
        addTwoItemsToDb()
        val allItems = itemDao.getAllItems().first() /* memasukkan item1 dan item2 lalu mengambil semua item dari
         database, dan memastikan keduanya dikembalikan dalam urutan yang benar*/
        assertEquals(allItems[0], item1) /* assert membandingkan elemen daftar yang dikembalikan dengan item1 dan item2*/
        assertEquals(allItems[1], item2)
    }

    /* untuk menguji apakah fungsi getItem mengembalikan item yang benar berdasarkan ID */
    @Test
    @Throws(Exception::class)
    fun daoGetItem_returnsItemFromDB() = runBlocking {
        addOneItemToDb()
        val item = itemDao.getItem(1) /* memasukkan item1 dan mengambilnya berdasarkan ID (1) */
        assertEquals(item.first(), item1) /* memastikan bahwa item yang diambil sama dengan item1 */
    }

    /* untuk memastikan menghapus item dari database berfungsi dnegan benar */
    @Test
    @Throws(Exception::class)
    fun daoDeleteItems_deletesAllItemsFromDB() = runBlocking {
        addTwoItemsToDb()
        itemDao.delete(item1)
        itemDao.delete(item2)
        val allItems = itemDao.getAllItems().first() /* memasukkan kedua item, menghapusnya, lalu mengambil semua item
        untuk memastikan semua item telah dihapus */
        assertTrue(allItems.isEmpty()) /* untuk memastikan allItems kosong, berarti smeua item telah berhasil dihapus */
    }

    /* untuk menguji apakah mengupdate item di database berfungsi dengan benar */
    @Test
    @Throws(Exception::class)
    fun daoUpdateItems_updatesItemsInDB() = runBlocking {
        addTwoItemsToDb() /* memasukkan item1 dan item2, lalu memperbarui harga dan jumlah setiap item dengan nilai baru */
        itemDao.update(Item(1, "Apples", 15.0, 25))
        itemDao.update(Item(2, "Bananas", 5.0, 50))

        val allItems = itemDao.getAllItems().first() /* menggunakan assertEquals untuk memastikan nilai
        yang diperbarui berhasil disimpan dan diambil dengan benar */
        assertEquals(allItems[0], Item(1, "Apples", 15.0, 25))
        assertEquals(allItems[1], Item(2, "Bananas", 5.0, 50))
    }

    /* untuk memasukkan item1 ke dalam database, untuk menghindari duplikasi kode dalam fungsi pengujian */
    private suspend fun addOneItemToDb() {
        itemDao.insert(item1)
    }

    /* memasukkan item1 dan item2 ke dalam database, dan untuk menghindari duplikasi kode di berbagai fungsi pengujian */
    private suspend fun addTwoItemsToDb() {
        itemDao.insert(item1)
        itemDao.insert(item2)
    }
}
