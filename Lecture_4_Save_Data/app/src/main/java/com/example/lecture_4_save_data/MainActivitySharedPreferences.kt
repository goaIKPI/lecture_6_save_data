package com.example.lecture_4_save_data

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.content.SharedPreferences
import android.preference.EditTextPreference
import android.widget.EditText
import android.widget.Toast
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*


class MainActivitySharedPreferences : AppCompatActivity() {

    val APP_PREFERENCES = "MySetting"
//    val APP_PREFERENCES_COUNTER = "counter"

    lateinit  var pref: SharedPreferences
    lateinit  var editor :SharedPreferences.Editor


    // Имя переменной в настройках для сохраненния json
    val APP_PREFERENCES_COUNTER_ARRAY = "counter_array"
    // Массив, который будем заполнять
    var array  = arrayListOf<String>()
    // Переменная для записи json
    var arrayString: String? = ""
    // Тип для конвертирования из json
    val collectionType = object : TypeToken<List<String>>() {}.type

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pref = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        editor = pref.edit()


//    button.setOnClickListener {
//        editor.putString(APP_PREFERENCES_COUNTER, editText.text.toString())
//        editor.apply()
//        Toast.makeText(this,"Save!",Toast.LENGTH_LONG).show()
//    }



        arrayString = pref.getString(APP_PREFERENCES_COUNTER_ARRAY, null)

        if (arrayString != null){
            array = Gson().fromJson(arrayString, collectionType)
            updateListView()
        }



        button.setOnClickListener {
            // Добавляем в массив
            array.add(editText.text.toString())
            // Формируем строку
            arrayString = Gson().toJson(array)
            // Сохраняем строку в настройках
            editor.putString(APP_PREFERENCES_COUNTER_ARRAY, arrayString)
            editor.apply()
            // Обновляем список
            updateListView()
            Toast.makeText(this,"Save!",Toast.LENGTH_LONG).show()
        }

    }

    fun updateListView(){
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, array
        )

        listView.setAdapter(adapter)

    }



//    override fun onResume() {
//        super.onResume()
//
//        if (pref.contains(APP_PREFERENCES_COUNTER)) {
//            // Получаем число из настроек
//            val count = pref.getString(APP_PREFERENCES_COUNTER, "")
//            textView.text = "Последний раз вы сохранили $count"
//
//        }
//    }

}

