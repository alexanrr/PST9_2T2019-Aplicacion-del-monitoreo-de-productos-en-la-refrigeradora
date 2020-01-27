package com.example.refrigeradorinteligente;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class IniciarSesion extends AppCompatActivity {
    /*En esta pantalla se pueden realizar dos funciones, la primera valida que el usuario ingrese los
    datos de una cuenta existente y la segunda el registro de un nuevo usuario*/


    //Los atributos principales son una base de datos y las referencias a el usuario y contraseña ingresados por el usuario
    EditText usuario,password;
    DataBase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        usuario=(EditText) findViewById(R.id.txtusuario);
        password=(EditText) findViewById(R.id.etpass);
        bd= new DataBase();
    }


    /*Metodo utilizado al aplastar el boton de empezar, sirve para ingresar al sistema utilizando
    * un usuario y contraseña registrados anteriormente*/
    public void IniciarSesion(View view)
    {

        //Se obtienen los datos ingresados en pantalla y se los guarda en variables
        String usuariostr=usuario.getText().toString();
        String passwordstr= password.getText().toString();
        //Se realiza una validación para que no se caiga el programa al tener datos vacíos
        if (usuariostr.equals("")||passwordstr.equals((""))){
            //Si uno de los campos esta vacío y se da click al boton, se crea un mensaje emergente
            Toast.makeText(this,"Error, campos vacíos",Toast.LENGTH_LONG).show();
        }

        /*La siguiente condición llama a un método definido en la clase DataBase el cual valida la
        existencia del usuario en la base de datos.*/
        else if(bd.comoprobarInicioSesion(usuariostr,passwordstr)){
            Usuario escojido = bd.retornarUsuario(usuariostr,passwordstr);
            Toast.makeText(this,"Iniciando Sesion",Toast.LENGTH_LONG).show();
            Intent inte = new Intent(this, Programa.class);
            inte.putExtra("Usuario",escojido.getUsuario());
            inte.putExtra("password",escojido.getPassword());
            inte.putExtra("nombre",escojido.getNombre());
            inte.putExtra("apellidos",escojido.getApellidos());
            startActivity(inte);
            finish();
        }
        else{
            Toast.makeText(this,"Datos Incorrectos",Toast.LENGTH_LONG).show();
        }
    }

    /*Este método nos lleva a una nueva ventana que sirve para registrar los datos de un nuevo usuario*/
    public void Registrarse(View view)
    {
        //Nos lleva a la ventana de registro de un nuevo cliente
        Intent inte = new Intent(this, Registrar.class);
        startActivity(inte);
    }








}
