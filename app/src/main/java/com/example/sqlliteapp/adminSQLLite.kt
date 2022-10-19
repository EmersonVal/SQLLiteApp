package com.example.sqlliteapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.security.AccessControlContext

class adminSQLLite (context: Context? ,name:String?, factory:SQLiteDatabase.CursorFactory?, version:Int):SQLiteOpenHelper( context, name, factory, version){
    //Creacion de tabla
    override fun onCreate(BD: SQLiteDatabase?) {
        BD?.execSQL("create table Articulos"+"(Codigo int primary key, Descripcion text, Precio real)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

}