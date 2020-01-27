package com.example.refrigeradorinteligente;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class Registrar extends AppCompatActivity {

    /* En esta clase se registran los usuarios en la base de datos*/

    EditText user,clave,nombre,apellidos;
    DataBase bd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        user = (EditText)findViewById(R.id.etusuario);
        clave = (EditText)findViewById(R.id.etpass);
        nombre = (EditText)findViewById(R.id.etnombre);
        apellidos = (EditText)findViewById(R.id.etapellidos);
        bd= new DataBase();

    }

    public void Continuar(View view){

        /*Este boton envia los datos ingresados por el usuario al siguiente intent donde se subirán los datos
        después de dar ciertas indicaciones
         */
        String userstr=user.getText().toString();
        String clavestr=clave.getText().toString();
        String nombrestr=nombre.getText().toString();
        String apellidosstr=apellidos.getText().toString();


        //Se realizan las validaciones respectivas, elementos vacíos y usuarios ya registrados
       if(verificarvacio(userstr,clavestr,nombrestr,apellidosstr)){
            Toast.makeText(this,"Error, Parámetros vacíos", Toast.LENGTH_LONG).show();

        }

       //Se debe validar que el usuario no exista en la base de datos

        else{

            if(bd.comoprobarInicioSesion2(userstr)){

                Toast.makeText(this,"El usuario ingresado ya existe, escoja otro", Toast.LENGTH_LONG).show();

                //Esto vacia los datos
                user.setText("");
                clave.setText("");
                nombre.setText("");
                apellidos.setText("");

            }
            else{

                Intent inten= new Intent(this, RegistroProductos.class);
                inten.putExtra("usuario",userstr);
                inten.putExtra("password",clavestr);
                inten.putExtra("nombre",nombrestr);
                inten.putExtra("apellidos",apellidosstr);

                startActivity(inten);

            }



        }



    }
    // Sirve para cancelar el servicio
    public void Cancelar(View view){
        //Regresa a la ventana de inicio de sesión
        Intent inten= new Intent(this, IniciarSesion.class);
        startActivity(inten);
        finish();

    }

    //Verifica que los campos estén llenos
    public boolean verificarvacio(String user, String clave, String nombre, String apellidos){

        if(user.equals("")||clave.equals("")||nombre.equals("")||apellidos.equals("")){

            return true;
        }
        else{

            return false;
        }

    }

    //Bloquea el boton de retroceso, solo se sale cancelando
    @Override
    public void onBackPressed(){

        //no hace nada
    }



}
