package com.example.refrigeradorinteligente;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
* Esta clase es creada para manejar el envio de datos a Firebase y para realizar validaciones hacia
* usuarios y obtener los datos de los productos*/



public class DataBase {

    //La clase principal cuenta con dos atributos definidps, una es la referencia a la base de datos y la otra
    //un arrayList de los usuarios registrados.

    DatabaseReference mRootReference;
    ArrayList<Usuario> users= new ArrayList<>();


    /*El constructor DataBase() se encarga de inicializar la instancia creada de la base de datos,
    * cuando se ejecute este metodo se puede acceder a los metodos definidos*/
    public DataBase() {
        mRootReference = FirebaseDatabase.getInstance().getReference();
        generarUsuarios();// este metodo rellena el arreglo ususarios con Usuarios registrados en Firebase.

    }

    /*El metodo subirDatos  sirve para registrar los usuarios en un nodo creado en Firebase, de forma
    * que se puede hacer validaciones de cuentas mediante una base de datos */
    public void subirDatos(String usuario,String password, String nombre, String apellido)
    {
        //Firebase trabaja con la forma clave:valor, por tanto se debe crear un objeto Map para poder subir los datos
        Map<String,Object> datosUser= new HashMap<>();
        datosUser.put("usuario",usuario);
        datosUser.put("password",password);
        datosUser.put("nombre",nombre);
        datosUser.put("apellidos",apellido);
        //Una vez agregados todos los datos se sube el Map a la base de datos vinculada con el proyecto
        mRootReference.child("Usuario").push().setValue(datosUser);
    }


    /*El siguiente método se encarga de llenar la instancia ususarios con objetos de la clase Usuario los
    * cuales contienen datos de los usuarios que se registran en la aplicación*/
    public ArrayList<Usuario> generarUsuarios()

    //El siguiente evento se encarga de recoger los datos obtenidos en la base de datos en el nodo Usuarios
    //El evento ValueEventListener se encarga de obtener los datos constantemente, esto indica que cuando se registre un nuevo usuario,
    //la lista de usuarios se actualiza completamente
    {
        mRootReference.child("Usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        // El for itera en cada usuario y al obtener los datos subidos se los agrega a un nuevo objeto
                        // de usuario el cual es agregado a la lista de usuarios registrados

                        String usuario= snapshot.child("usuario").getValue().toString();
                        String password= snapshot.child("password").getValue().toString();
                        String nombre= snapshot.child("nombre").getValue().toString();
                        String apellidos= snapshot.child("apellidos").getValue().toString();

                        Usuario u = new Usuario(nombre, apellidos,usuario,password);
                        users.add(u);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return users;

    }


    /*Este método se utiliza en la clase Iniciar Sesion y es utilizado para comprobar si un Usuario
    * con las credenciales de usuario y contraseña ingresados existe dentro de los usuarios registrados*/
    public boolean comoprobarInicioSesion(String user, String clave){

        ArrayList<Usuario> usuarios= generarUsuarios();
        for(Usuario u: usuarios){
            if (u.getUsuario().equals(user)&&u.getPassword().equals(clave)){
                return true;
            }
        }
        return  false;
    }


    /*Este método es utilizado en la clase Registrar, este sirve para validar si el nombre de usuario ingresado
    * no esta repetido*/
    public boolean comoprobarInicioSesion2(String user)
    {

        ArrayList<Usuario> usuarios= generarUsuarios();

        for(Usuario u: usuarios){
            if (u.getUsuario().equals(user)){

                return true;
            }
        }
        return  false;

    }


    /*Este método retorna el objeto usuario con ingresar el nombre de usuario y la contraseña */
    public Usuario retornarUsuario(String user, String clave)
    {
        Usuario us= new Usuario();
        for(Usuario u: users){
            if (u.getUsuario().equals(user)&&u.getPassword().equals(clave)){
                us.setUsuario(u.getUsuario());
                us.setPassword(u.getPassword());
                us.setNombre(u.getNombre());
                us.setApellidos(u.getApellidos());
            }
        }
        return us;
    }


    /*Este getter será utilizado para obtener referecias a la base de datos y obtener
       los datos en tiempo real*/
    public DatabaseReference getmRootReference()

    {
        return mRootReference;
    }
}
