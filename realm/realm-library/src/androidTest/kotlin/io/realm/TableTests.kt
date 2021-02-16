/*
 * Copyright 2020 Realm Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.realm

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import io.realm.entities.MyRealmModel
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TableTests {

    private lateinit var config: RealmConfiguration
    private lateinit var realm: Realm

    @Before
    fun setUp() {
        Realm.init(InstrumentationRegistry.getInstrumentation().context)
        config = RealmConfiguration.Builder()
                .schema(MyClass::class.java, MyRealmModel::class.java)
                .build()
        realm = Realm.getInstance(config)
    }

    @After
    fun tearDown() {
        realm.close()
        Realm.deleteRealm(config)
    }

    @Test
    fun jahsgd() {
        realm.executeTransaction { transactionRealm ->
            val myObject = MyClass().apply {
                myIntDict = RealmDictionary<Int>().apply {
                    put("A", 1)
                    put("B", 2)
                }
                myModelDict = RealmDictionary<MyRealmModel>().apply {
                    put("AAA", MyRealmModel().apply { id = "1" })
                    put("BBB", MyRealmModel().apply { id = "2" })
                }
            }
            val objectFromRealm = transactionRealm.copyToRealm(myObject)
        }
    }
}

open class MyClass : RealmObject() {
    var myIntDict: RealmDictionary<Int>? = null
    var myModelDict: RealmDictionary<MyRealmModel>? = null
}
