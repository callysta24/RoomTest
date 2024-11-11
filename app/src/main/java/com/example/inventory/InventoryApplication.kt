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

import android.app.Application
import com.example.inventory.data.AppContainer
import com.example.inventory.data.AppDataContainer

class InventoryApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer /* sebuah antarmuka (interface) yang memungkinkan pengelolaan dan penyediaan dependensi
    (objek yang diperlukan oleh kelas lain dalam aplikasi) */
    /*
    container adalah properti bertipe AppContainer. Ini dideklarasikan sebagai lateinit, artinya akan
    diinisialisasi nanti, yaitu saat onCreate() dipanggil.
     */

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
        /*
        Di dalam onCreate(), container diinisialisasi sebagai instance dari AppDataContainer,
        yang menerima this (konteks aplikasi) sebagai parameter
         */
    }
}

/*
Digunakan untuk menginisialisasi dependensi pada level aplikasi di Android.
Kelas ini digunakan untuk memastikan bahwa beberapa dependensi dapat diakses secara global di seluruh aplikasi
 */