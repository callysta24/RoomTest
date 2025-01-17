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

package com.example.inventory.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Database access object to access the Inventory database
 */
@Dao
interface ItemDao {

    @Query("SELECT * from items ORDER BY name ASC")
    fun getAllItems(): Flow<List<Item>>
    /*
    Mengambil semua item dari tabel items, diurutkan berdasarkan name secara ascending.
    Mengembalikan data dalam bentuk Flow<List<Item>>, memungkinkan pemantauan data secara reaktif.
     */

    @Query("SELECT * from items WHERE id = :id")
    fun getItem(id: Int): Flow<Item>
    /*
     Mengambil satu item berdasarkan id dari tabel items dan mengembalikan data sebagai Flow<Item>
     */

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)
    /*
    Menyisipkan item baru ke database. Jika ada konflik (item sudah ada),
    akan diabaikan (OnConflictStrategy.IGNORE)
     */

    @Update
    suspend fun update(item: Item) //Memperbarui data item yang ada dalam database

    @Delete
    suspend fun delete(item: Item) //Menghapus item dari database
}
/*
ItemDao adalah antarmuka DAO (Data Access Object) untuk mengelola operasi database pada entitas Item di Room
DAO ini memungkinkan interaksi data Item dengan Room menggunakan fungsi CRUD (Create, Read, Update, Delete)
 */