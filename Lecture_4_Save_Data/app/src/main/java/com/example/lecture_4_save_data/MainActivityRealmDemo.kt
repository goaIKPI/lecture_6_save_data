package com.example.lecture_4_save_data


import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import io.realm.Realm
import io.realm.RealmConfiguration




class MainActivityRealmDemo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Подключаем базу данных
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .name("person.realm")
            .build()
        val realm = Realm.getInstance(config)



        // Создаем объект
        realm.beginTransaction()
        val person = realm.createObject(Person::class.java,1)
        person.age = 30
        person.name = "Dev"
        realm.commitTransaction()

        //Примеры извлечения данных
        val allPersons = realm.where(Person::class.java).findAll()
        val firstPersonsBetweenAge = realm.where(Person::class.java).between("age",20, 100).findFirst()
        val personsUnder30 = realm.where(Person::class.java).lessThan("age",30).findAll()

        //Изменение одного объекта
        realm.beginTransaction()
        firstPersonsBetweenAge!!.name = "Jack"
        realm.commitTransaction()


        //Вывод всех объектов
        allPersons.forEach {
            println("Person - name: ${it.name}, age: ${it.age}")
        }


        //Изменение списка объектов
        personsUnder30.forEach{
            realm.beginTransaction()
            it.age = 60
            realm.commitTransaction()
        }


        val personNameBonch = realm.where(Person::class.java).equalTo("name", "bonch").findAll()

        //Удаление объекта
        personNameBonch.forEach{
            realm.beginTransaction()
            it.deleteFromRealm()
            realm.commitTransaction()
        }
    }

}