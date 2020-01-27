package com.example.refrigeradorinteligente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Programa extends AppCompatActivity {

    /*En esta clase se realiza el objetivo del proyecto, el cual es obtener datos que provienen de una base de datos,
    * los principales atributos a definir son referencias a la cantidad en gramos de cada producto, la base de datos vinculada y elementos
    * vacíos en pantalla los cuales se llenarán al ejecutar ciertos métodos */

    String nombre;
    String apellidos;
    TextView mensaje;
    int agua,gaseosa,jugo,lacteos;
    TextView p1,p2,p3,p4;
    TextView canAgua, canlacteo,cangas,canjugo;
    DataBase bd;
    private PendingIntent pendingIntent;
    private final static String CHANNEL_ID="NOTIFICACION";
    private final static int NOTIFICACION_ID=0;
    boolean valor= true;// bandera utilizada para monitorear las notificaciones


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        //Mensaje de bienvenida al usuario
        generarmensaje();

        bd= new DataBase();
        valor = true;

        p1= (TextView)findViewById(R.id.aguatxt);
        p2= (TextView)findViewById(R.id.lacteostxt);
        p3= (TextView)findViewById(R.id.gaseosatxt);
        p4= (TextView)findViewById(R.id.jugotxt);
        canAgua =(TextView) findViewById(R.id.cantAguatxt);
        cangas=(TextView) findViewById(R.id.cangastxt);
        canjugo=(TextView) findViewById(R.id.cantjugotxt);
        canlacteo=(TextView) findViewById(R.id.Cantlechetxt);

        //Se muestran los datos obtenidos de la base de datos en la activity

        generarValores();

    }


    /*Este método se encarga de generar un mensaje de bienvenida al usuario nombrendo su nombre y apellido
    * los cuales son recividos como extras ya que del intent anterior se los envió al validar los usuarios y contraseñas*/
    public void generarmensaje(){
        Bundle b= getIntent().getExtras();
        nombre= b.getString("nombre");
        apellidos= b.getString("apellidos");
        mensaje= (TextView) findViewById(R.id.mensaje);
        mensaje.setText("Bienvenido "+nombre+" "+apellidos+", sus productos son los siguientes: ");
    }


    /*Este metodo se encarga de descargar los datos de firebase y mostrarlos en la aplicación*/
    public void generarValores(){

        /*El evento ValueEventListener sirve para ir cambiando los datos cada ez que se actualicen en la base de datos,
        * de esta forma se obtendrán siempre datos actualizados*/
        bd.getmRootReference().child("Refrigerador").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    //Se obtienen los datos de los nodos
                   int p11 = Integer.parseInt( dataSnapshot.child("Agua").getValue().toString());
                    int p22 = Integer.parseInt(dataSnapshot.child("Lacteos").getValue().toString());
                    int p33= Integer.parseInt(dataSnapshot.child("Gaseosa").getValue().toString());
                    int p44 = Integer.parseInt(dataSnapshot.child("Jugo").getValue().toString());

                    agua = p11;
                    lacteos =p22;
                    gaseosa = p33;
                    jugo = p44;
                    // los TextView se rellenan con los valores obtenidos en el evento ValueEventListener
                    p1.setText(String.valueOf(agua));
                    p2.setText(String.valueOf(lacteos));
                    p3.setText(String.valueOf(gaseosa));
                    p4.setText(String.valueOf(jugo));

                    //Condiciones para determinar

                    if(agua>0){
                        canAgua.setText("1");
                    }else{canAgua.setText("0");}
                    if (lacteos>0){
                        canlacteo.setText("1");
                    }else{canlacteo.setText("0");}
                    if (gaseosa>0){
                        cangas.setText("1");
                    }else{cangas.setText("0");}
                    if (jugo>0){
                        canjugo.setText("1");
                    }else{canjugo.setText("0");}

                    //Aquí se genera un if para generar las notificaciones utilizando una bandera.
                    if(valor){
                    generarAlerta();}


                }}
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

//Para generar la alerta se utilizan los siguiente métodos:

    //Este crea un canal para versiones de Android más actualizadas
    public void notifchannel()
    {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            CharSequence name= "Notificacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
    }
    }
    //Aquí se genera la notificación
    public void notificacion()
    {
        //Se utilizan las clases para crear la notificaciones
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_notifications_active_black_24dp);
        builder.setContentTitle("Alerta");//El título de la notificación
        String producto = productoAlerta();//Un String con todos los productos que estén por terminarse
        if(producto.length()<=9){

            builder.setContentText("El producto "+producto+ " está por terminarse");
        }
        else{

            builder.setContentText("Los productos: "+producto+ " están por terminarse");
        }
        //Datos estéticos de la notificación
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        //Se define que  el celular debe vibrar y sonar cuando recibe la notificación
        builder.setVibrate(new long[]{1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);
        NotificationManagerCompat notificationManagerCompat =NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID,builder.build());
    }

    /*En este metodo se ponen las condiciones para que exista la alerta uniendo diferente métodos
    creados en la aplicación.
     */

    public void generarAlerta()
    {

        //Se debe añadir la condicion para mandar la notificacion

        if (condicionAlerta()){
            notifchannel();
            notificacion();
        }
    }


    /*Mediante condiciones se logra obtener los productos que están por terminarse y mediante otro método
    se lo insertará en el texto de la notificacion creada
     */
    public String productoAlerta()
    {
        String valor="";

        Map<String,Integer> datos= new HashMap<>();
        datos.put("Agua",agua);
        datos.put("Lacteos",lacteos);
        datos.put("Gaseosa",gaseosa);
        datos.put("Jugo",jugo);

        for(Map.Entry<String,Integer> entry: datos.entrySet()){

            if (entry.getValue()<250&& entry.getValue()>0){

                valor+=entry.getKey()+", ";

            }
        }

        return valor;
    }

    /*Aquí se define la condición para generar la alerta, esta es cuando el peso de un produto es menor a los 250 gramos, cabe recalcar que
    * si el peso del producto es 0, no se debe generar alerta ya que puede ser el caso de que no haya ningun producto en ese sensor y no
    * que se haya terminado totalmente uno.*/
    public boolean condicionAlerta()
    {

        if((agua <250&&agua>0)||(lacteos<250&&lacteos>0)||(gaseosa<250&&gaseosa>0)||(jugo<250&&jugo>0)){
            return true;

        }

        return false;
    }


    /*Este metodo sirve para salir de la aplicación y seguir recibiendonotificaciones siempre que no se
    mate la actividad en segundo plano*/
    public void Minimizar(View view)
    {

        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);

    }
/*Este metodo termina con las notificaciones utilizando la bandera definida al inicio, esto indica que el usuario ya
no quire recibir notificaciones y quiere salir de su cuenta
 */
    public void cerrarsesion(View view){

      valor= false;
      Intent i= new Intent(this, IniciarSesion.class);
      startActivity(i);
      finish();

    }

    //Este metodo bloquea el boton de atras de la aplicación, la unica forma de salir es con unno de los dos botones definidos en la activiy
    @Override
    public void onBackPressed(){
        // no hace nada
    }







}