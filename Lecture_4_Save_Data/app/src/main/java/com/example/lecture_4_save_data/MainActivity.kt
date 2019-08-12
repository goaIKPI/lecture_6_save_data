package com.example.lecture_4_save_data


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var realm: Realm
    var array  = arrayListOf<String?>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Получаем экземпляр базы данных
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .name("MyDataBase.realm")
            .build()
        realm = Realm.getInstance(config)


        // Получаем список всех пользователей
        val allPersons = realm.where(Person::class.java).findAll()


        //Проверяем не была ли пуста база данных
        if (allPersons != null){
            allPersons.forEach {
                // Добавляем имя каждого объекта в массив имен
                array.add(it.name)
            }
            // Обновляем ListView
            updateListView()
        }


        button.setOnClickListener {
            // Добавляем в массив новое имя
            array.add(editText.text.toString())
            // Сохраняем имя в базу данных и обновляем список
            saveData()
            updateListView()
        }

    }


    fun saveData(){
        realm.executeTransactionAsync({ bgRealm ->
            val user = bgRealm.createObject(Person::class.java)
            user.name = editText.text.toString()
        }, {
            // Transaction was a success.
        }, {
            // Transaction failed and was automatically canceled.
        })
    }

    fun updateListView(){
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, array
        )

        listView.setAdapter(adapter)

    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}

