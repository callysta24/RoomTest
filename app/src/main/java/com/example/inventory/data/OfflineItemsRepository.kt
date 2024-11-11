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

class OfflineItemsRepository(private val itemDao: ItemDao) : ItemsRepository {
    override fun getAllItemsStream(): Flow<List<Item>> = itemDao.getAllItems()
    /*
    Mengambil semua item dari database menggunakan ItemDao.getAllItems() dan mengembalikannya
    sebagai Flow<List<Item>> agar bisa dipantau secara reaktif
     */

    override fun getItemStream(id: Int): Flow<Item?> = itemDao.getItem(id)
    /*
    Mengambil item tertentu berdasarkan id dari database menggunakan ItemDao.getItem(id),
    juga dalam bentuk Flow<Item?> untuk reaktivitas
     */

    override suspend fun insertItem(item: Item) = itemDao.insert(item)
    /*
    Menambahkan item ke database dengan menggunakan ItemDao.insert(item) secara asinkron
     */

    override suspend fun deleteItem(item: Item) = itemDao.delete(item)
    /*
    Menghapus item dari database dengan ItemDao.delete(item) secara asinkron
     */

    override suspend fun updateItem(item: Item) = itemDao.update(item)
    /*
    Memperbarui item di database dengan ItemDao.update(item) secara asinkron
     */
}
/*
OfflineItemsRepository adalah implementasi dari antarmuka ItemsRepository, yang menggunakan
ItemDao untuk berinteraksi dengan database secara offline.

OfflineItemsRepository menyederhanakan akses data dengan mengandalkan ItemDao untuk melakukan
semua operasi CRUD (Create, Read, Update, Delete) secara langsung pada database Room
 */