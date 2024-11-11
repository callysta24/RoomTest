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

import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [Item] from a given data source.
 */
interface ItemsRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllItemsStream(): Flow<List<Item>>
    /*
    Mengambil semua item sebagai Flow<List<Item>>, memungkinkan data dipantau secara reaktif
     */

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getItemStream(id: Int): Flow<Item?>
    /*
    Mengambil satu item berdasarkan id sebagai Flow<Item?>, dengan reaktifitas
    yang memungkinkan pemantauan perubahan pada item tertentu.
     */

    /**
     * Insert item in the data source
     */
    suspend fun insertItem(item: Item)
    /*
    Menambahkan item ke dalam sumber data secara asinkron
     */

    /**
     * Delete item from the data source
     */
    suspend fun deleteItem(item: Item)
    /*
    Menghapus item dari sumber data secara asinkron
     */

    /**
     * Update item in the data source
     */
    suspend fun updateItem(item: Item)
    /*
    Memperbarui data item yang ada dalam sumber data secara asinkron
     */
}
/*
ItemsRepository adalah antarmuka (interface) yang menyediakan metode untuk mengelola operasi Item
seperti menambah, memperbarui, menghapus, dan mengambil data dari sumber data (misalnya, database)
 */