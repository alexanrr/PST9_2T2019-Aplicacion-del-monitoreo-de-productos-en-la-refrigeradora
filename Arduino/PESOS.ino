//librerias a utilizar
#include "HX711.h"


//Definir los pines de las celdas de carga
#define DT  13
#define SCK  12

#define DT1 31 //pin pwm 31
#define SCK1 30 //pin pwm 10

#define DT2 29  //pin pwm 9
#define SCK2 28  //pin pwm 8

#define DT3 27  //pint pwm 7
#define SCK3 26 //pin pwm 6

// Creacion del objeto para el transmisor de celda de carga HX711
HX711 balanza(DT, SCK);
HX711 balanza1(DT1, SCK1);
HX711 balanza3(DT3, SCK3);
HX711 balanza2(DT2, SCK2);


// escala
float ESCALA = 114000 / 301.5;


float bal;
float bal1;
float bal2;
float bal3;

#include <SoftwareSerial.h>
SoftwareSerial s(4, 5); //Rx,Tx

void setup() {
  Serial.begin(9600);
  s.begin(9600);
  Serial.print("     Agua             ");
  Serial.print("Lacteos              ");
  Serial.print("Jugo             ");
  Serial.println("Gaseosa");

  balanza2.set_scale(ESCALA); // Establecemos la ESCALA calculada anteriormente
  balanza1.set_scale(ESCALA); //Establecemos la ESCALA calculada anterior
  balanza3.set_scale(ESCALA);
  balanza.set_scale(ESCALA);

  balanza2.tare(20); // El peso actual es considerado Tara.
  balanza1.tare(20); //El peso actual es considerado tara (el soporte)
  balanza3.tare(20);
  balanza.tare(20);

  delay(5000); //Esperar 5 segundo para comenzar a pesar

}
void loop() {

  //muestra por pantalla los valores de porcentaje de 23,5 cm real = 20 cm en arduino y los pesoso obtenidos
  Serial.print("Peso: ");
  Serial.print(balanza.get_units(20), 0);
  Serial.print("g          ");
  Serial.print("Peso: ");
  Serial.print(balanza1.get_units(20), 0);
  Serial.print("g          ");
  Serial.print("Peso: ");
  Serial.print(balanza2.get_units(20), 0);
  Serial.print("g          ");
  Serial.print("Peso: ");
  Serial.print(balanza3.get_units(20), 0); // Se obtiene el valor real del peso en Kg del elemento
  Serial.println("g");

  //obtener valores de cada balanza en gramos
  bal = balanza.get_units(20);
  bal1 = balanza1.get_units(20);
  bal2 = balanza2.get_units(20);
  bal3 = balanza3.get_units(20);

  //pasando informacion al nodemcu
  s.println(bal);
  s.println(bal1);
  s.println(bal2);
  s.println(bal3);
  delay(1000);
}
