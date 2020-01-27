package com.example.refrigeradorinteligente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegistroProductos extends AppCompatActivity {

    //Aqui se registran los usuarios en un nodo de Firebase

    DataBase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        bd= new DataBase();

    }

    //Este boton confirma que el usuario quiere empezar con el servicio y lo registra

    public void finalizar(View view){


        Bundle extras= getIntent().getExtras();
        String usuario= extras.getString("usuario");
        String password= extras.getString("password");
        String nombre= extras.getString("nombre");
        String apellido= extras.getString("apellidos");

        //Subir a la base de datos
        bd.subirDatos(usuario,password,nombre,apellido);

        // Este mensaje indica que se redijira automaticamente al activity IniciarSesion para que pueda ingresar con sus credenciales
        Toast.makeText(this,"Ya puede empezar a utilizar la aplicación",Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(RegistroProductos.this, IniciarSesion.class);
                startActivity(intent);
                finish();


            }
        },2000);


    }

    //Sirve para cancelar el proceso
    public void Cancelar(View view){


        //Regresa a la ventana de inicio de sesión
        Intent inten= new Intent(this, IniciarSesion.class);
        startActivity(inten);
        finish();

    }

    //Se bloquea el boton de retroceso, solo se puede salir cancelando el servicio
    @Override
    public void onBackPressed(){
// no hace nada

    }


}
