package com.example.lecture_4_save_data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

// Указываем что это RealmClass и наследуется от RealmObject
@RealmClass
open class Person: RealmObject() {
    // В переменной id будет храниться идентификатор объекта в базе данных
    @PrimaryKey
    var id: Long = 0

    // Указываем еще два параметра: имя и возраст
    var name: String? = null
    var age: Int? = null
}