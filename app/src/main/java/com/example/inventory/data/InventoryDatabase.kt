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

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Database class with a singleton Instance object.
 */
@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao /* menyediakan akses ke ItemDao, yang merupakan Data Access Object (DAO) untuk entitas Item */

    companion object {
        @Volatile
        /*
        memastikan bahwa perubahan pada Instance akan langsung terlihat oleh semua thread,
        sehingga menghindari inkonsistensi ketika beberapa thread mengakses instance database secara bersamaan.
         */
        private var Instance: InventoryDatabase? = null

        fun getDatabase(context: Context): InventoryDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            /*
            untuk mengembalikan instance dari InventoryDatabase, Jika Instance sudah ada (tidak null), maka instance tersebut akan langsung dikembalikan.
            Jika Instance belum ada (null), maka kode akan membuat instance baru dari database menggunakan Room.databaseBuilder
             */
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, InventoryDatabase::class.java, "item_database")
                /*
                databaseBuilder digunakan untuk membuat atau mendapatkan instance dari database InventoryDatabase
                 */
                    /**
                     * Setting this option in your app's database builder means that Room
                     * permanently deletes all data from the tables in your database when it
                     * attempts to perform a migration with no defined migration path.
                     */
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
/*
Fungsi class ini untuk mengelola akses tunggal ke database InventoryDatabase di seluruh aplikasi,
menggunakan pola singleton untuk efisiensi memori dan akses data yang konsisten.
 */