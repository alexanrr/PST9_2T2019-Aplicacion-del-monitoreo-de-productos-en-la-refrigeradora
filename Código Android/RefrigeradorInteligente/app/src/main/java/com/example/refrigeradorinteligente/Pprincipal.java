package com.example.refrigeradorinteligente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Pprincipal extends AppCompatActivity {
    /*
    Esta es la pantalla de inicio la cual se mostrara por 2 segundos y automáticamente dará paso a
    la pantalla de inicio de sesión puesto que esta es solo de presentación y muestra el nombre de
    la aplicación junto a una imagen referente a las funciones de la aplicación
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //El siguiente método es el que crea un hilo para que la pantalla aparezca solo 2 segundos

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(Pprincipal.this, IniciarSesion.class);
                startActivity(intent);
                // se coloca el finish ya que esta panatalla no debe volver a aparecer en la ejecución
                finish();


            }
        },2000);// el delay muestra el tiempo en milisegundos en que dura el hilo
    }



}
