/*
   Proframa cargado al NodeMCU 8266, utilizando
   el IDE de Arduino
*/
//Librerías para usar el NodeMCU 8266 y Firebase
#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>

//Informacipon de la base de datos de Firebase
#define FIREBASE_HOST "refrigerador-inteligente.firebaseio.com"
#define FIREBASE_AUTH "rvPLT3FuS117nnM56IQy5ibU5snfU7BSMEO0rObb"

//Nombre y contraseña de la red a conectar
#define WIFI_SSID "Tvcable_Rojas"
#define WIFI_PASSWORD "Jesus2017"

#include <SoftwareSerial.h>
//pines a utilizar para la comunicación serial
SoftwareSerial s(D6, D5); //Rx,Tx
//variable de lectura
String sensor;
//variable a subir a la base de datos
int subir;

void setup() {
  Serial.begin(9600);
  s.begin(9600);
  //conexión a la red wi-fi
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("connecting");
  //verificando conexión
  while (WiFi.status() != WL_CONNECTED) {
    Serial.println("Esperando conexión a la red...");
    delay(500);
  }
  Serial.print("Wi-Fi connectado");
  //una vez conectado a la red wi-fi, se puede conectar Firebase
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  Serial.print("Firebase connectado");
}

void loop() {

  // validando recibimiento de información
  if (s.available() > 0) {
    /*se crea un ciclo for para que se llenen los valores
        sensados por la celda de carga y se actualicen en la
        base de datos
    */
    for (int op = 1; op < 5; op++) {
      /*
         Primero se lee la información que envía
         el arduino, luego se la transforma a float
         para subir a la base de datos
      */
      sensor = s.readStringUntil(13);
      subir = sensor.toInt();
      if (op == 1) {
        //subiendo información establecida a la base de datos
        /*
           cada celda de carga se enceró con su masa inicial
           por eso cuando no haya nada, la lectura será un
           número negativo e indicará que su masa real o vacía
           es 0
        */
        if (subir < 0) {
          Firebase.setInt("Refrigerador/Agua", 0);
          Serial.println(0);
        } else if (subir > 0) {
          /*
             cuando la lectura es mayor a 0, significa que si hay producto
             y por ende medirá dicha masa y la subirá a la base de datos
          */
          Firebase.setInt("Refrigerador/Agua", subir);
          Serial.println(subir);
        } else {
          Serial.println("No se sube nada a la base de datos");
        }
      } 
      //en kas otras opciones se aplica el mismo criterio explicado anteriormente
      else if (op == 2) {
        if (subir < 0) {
          Firebase.setInt("Refrigerador/Lacteos", 0);
          Serial.println(0);
        } else if (subir > 0) {
          Firebase.setInt("Refrigerador/Lacteos", subir);
          Serial.println(subir);
        } else {
          Serial.println("No se sube nada a la base de datos");
        }

      } else if (op == 3) {
        if (subir < 0) {
          Firebase.setInt("Refrigerador/Jugo", 0);
          Serial.println(0);
        } else if (subir > 0) {
          Firebase.setInt("Refrigerador/Jugo", subir);
          Serial.println(subir);
        } else {
          Serial.println("No se sube nada a la base de datos");
        }

      } else if (op == 4) {
        if (subir < 0) {
          Firebase.setInt("Refrigerador/Gaseosa", 0);
          Serial.println(0);
        } else if (subir > 0) {
          Firebase.setInt("Refrigerador/Gaseosa", subir);
          Serial.println(subir);
        } else {
          Serial.println("No se sube nada a la base de datos");
        }

      }

    }
  }
}
