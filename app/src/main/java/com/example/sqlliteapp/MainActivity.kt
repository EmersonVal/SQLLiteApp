//@Autor:Emerson Valenzuela
package com.example.sqlliteapp

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var etnCodigo:EditText
    private lateinit var etDescripcion:EditText
    private lateinit var etnPrecio:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etnCodigo = findViewById(R.id.etn_Codigo)
        etDescripcion = findViewById(R.id.et_Descripcion)
        etnPrecio = findViewById(R.id.etn_Precio)
    }
    //Crear funcion de rigistrar
    fun registrar(v: View){
    //Crear conexion a la bd
        val admin = adminSQLLite(this, "TIENDA", null, 1)
        val baseDeDatos:SQLiteDatabase = admin.writableDatabase
        val codigo = etnCodigo.text.toString()
        val descripcion = etDescripcion.text.toString()
        val precio = etnPrecio.text.toString()

        if(!codigo.isEmpty() &&!descripcion.isEmpty() &&!precio.isEmpty()){
            codigo.toInt()
            precio.toDouble()
            val registro = ContentValues()
            registro.put("codigo",codigo)
            registro.put("descripcion",descripcion)
            registro.put("precio",precio)
            baseDeDatos.insert("articulos",null, registro)
            baseDeDatos.close()
            //Vaciar campos
            etnCodigo.setText("")
            etDescripcion.setText("")
            etnPrecio.setText("")
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_LONG).show()

        }else{
            Toast.makeText(this,"Debe llenar todos los campos disponibles", Toast.LENGTH_LONG).show()
        }

    }

    fun buscar(vista:View){
        //se crea la conexion a las base de datos
        val admin = adminSQLLite(this,"Tienda",null,1)
        val baseDeDatos:SQLiteDatabase = admin.writableDatabase

        val codigo =etnCodigo.text.toString()

        if(!codigo.isEmpty()){
            //verificar si esta el codigo del produc
            val fila = baseDeDatos.rawQuery("select Descripcion , Precio from Articulos where Codigo=${codigo}",null)
            if(fila.moveToFirst()){
                etDescripcion.setText(fila.getString(1))
                etnPrecio.setText(fila.getString(2))
                baseDeDatos.close()
            }else{
                Toast.makeText(this,"No existe el articulo", Toast.LENGTH_SHORT).show()
                baseDeDatos.close()
            }
        }else{
            Toast.makeText(this,"Debes ingresar un codigo", Toast.LENGTH_LONG).show()
        }

    }

    fun modificar(vista: View){
        //se crea la conexion a las base de datos
        val admin = adminSQLLite(this,"Tienda",null,1)
        val baseDeDatos:SQLiteDatabase = admin.writableDatabase

        val codigo = etnCodigo.text.toString()
        val descripcion = etDescripcion.text.toString()
        val precio = etnPrecio.text.toString()

        if(!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){
            //codigo.toInt()
            //precio.toDouble()
            val registro = ContentValues()
            registro.put("Codigo",codigo)
            registro.put("Descripcion",descripcion)
            registro.put("Precio",precio)

            val cantidad:Int = baseDeDatos.update("Articulos",registro,"Codigo=${codigo}",null)
            baseDeDatos.close()

            //verifica si la consulta tiene valor

            if(cantidad==1){
                Toast.makeText(this,"Articulo modificado correctamente",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"No existe el articulo",Toast.LENGTH_SHORT).show()
                baseDeDatos.close()
            }

            etnCodigo.setText("")
            etDescripcion.setText("")
            etnPrecio.setText("")

            Toast.makeText(this,"Registro exitoso",Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this,"Debes llenar todos los campos",Toast.LENGTH_SHORT).show()
        }

    }

    fun eliminar(vista: View){
        //se crea la conexion a las base de datos
        val admin = adminSQLLite(this,"Tienda",null,1)
        val baseDeDatos:SQLiteDatabase = admin.writableDatabase

        val codigo =etnCodigo.text.toString()

        if(!codigo.isEmpty()){
            //verificar si esta el codigo del produc
            val cantidad:Int = baseDeDatos.delete("articulos","codigo=${codigo}",null)
            baseDeDatos.close()
            etnCodigo.setText("")
            etDescripcion.setText("")
            etnPrecio.setText("")

            if(cantidad==1){
                Toast.makeText(this,"Articulo eliminado",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"No existe el articulo",Toast.LENGTH_SHORT).show()
                baseDeDatos.close()
            }

        }else{
            Toast.makeText(this,"Debes ingresar un codigo", Toast.LENGTH_LONG).show()
        }
    }

    fun siguiente(vista: View){
        val ventana:Intent = Intent(applicationContext,SharedPerferencesApp::class.java)
        startActivity(ventana)
    }


}