package com.example.sqliteapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText etCodigo, etNombre, etSueldo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        etCodigo= (EditText)findViewById(R.id.etCodigo);
        etNombre= (EditText)findViewById(R.id.etNombre);
        etSueldo=(EditText)findViewById(R.id.etSueldo);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void Guardar(View view){
        AdminSQLiteData admin = new AdminSQLiteData(this, "administracion",null,1);
        SQLiteDatabase database= admin.getWritableDatabase();
        String codigo= etCodigo.getText().toString();
        String nombre= etNombre.getText().toString();
        String sueldo= etSueldo.getText().toString();
        if(!codigo.isEmpty() && !nombre.isEmpty() && !sueldo.isEmpty()){
            ContentValues registrar= new ContentValues();
            registrar.put("codigo",codigo);
            registrar.put("nombre",nombre);
            registrar.put("sueldo",sueldo);
            database.insert("docentes",null,registrar);
            database.close();
            etCodigo.setText("");
            etNombre.setText("");
            etSueldo.setText("");
            etCodigo.setFocusable(true);
            Toast.makeText(this, "Los datos han sido guardados", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Debes completar todos los campos", Toast.LENGTH_SHORT).show();
        }

    }
    public void Buscar(View view){
        AdminSQLiteData admin= new AdminSQLiteData(this, "administracion",null,1);
        SQLiteDatabase database = admin.getWritableDatabase();
        String codigo= etCodigo.getText().toString();
        if(!codigo.isEmpty()){
            Cursor fila= database.rawQuery("SELECT nombre, sueldo FROM docentes WHERE "+codigo,null);
            if(fila.moveToFirst()){
                etNombre.setText(fila.getString(0));
                etSueldo.setText(fila.getString(1));
                database.close();
            }else{
                Toast.makeText(this, "El docente no existe", Toast.LENGTH_SHORT).show();
                database.close();
            }
        }else{
            Toast.makeText(this, "Debes ingresar el codigo del docente", Toast.LENGTH_SHORT).show();
        }
    }
}