/*
 * Proframa cargado al NodeMCU 8266, utilizando
 * el IDE de Arduino
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
float sensor;
void setup() {
  Serial.begin(9600);
  s.begin(9600);
  //conexión a la red wi-fi
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("connecting");
  //verificando conexión
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
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
      * sensados por la celda de carga y se actualicen en la
      * base de datos
      */
    for (int op = 1; op < 5; op++) {
      /*se multiplica por 8 para compensar lo que se dividio para 8
       * en el programa subido al arduino ya que al no aplicar este
       * artificio, no se tendrán valores confiables
       */
      sensor = s.read() * 8.0;
      if (op == 1) {
        //subiendo información establecida a la base de datos
        Firebase.setFloat("Refrigerador/Agua", sensor);
        Serial.println(sensor);
      } else if (op == 2) {
        Firebase.setFloat("Refrigerador/Lacteos", sensor);
        Serial.println(sensor);
      } else if (op == 3) {
        Firebase.setFloat("Refrigerador/Jugo", sensor);
        Serial.println(sensor);
      } else if (op == 4) {
        Firebase.setFloat("Refrigerador/Gaseosa", sensor);
        Serial.println(sensor);
      }
    }
  }
}
