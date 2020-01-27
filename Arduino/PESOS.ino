//librerias a utilizar
#include "HX711.h"


//Definir los pines de las celdas de carga
#define DT  13
#define SCK  12

#define DT1 11
#define SCK1 10

#define DT2 9
#define SCK2 8

#define DT3 7
#define SCK3 6

// Creacion del objeto para el transmisor de celda de carga HX711
HX711 balanza(DT, SCK);
HX711 balanza1(DT1, SCK1);
HX711 balanza2(DT2, SCK2);
HX711 balanza3(DT3, SCK3);


// escala para tarar o encerar la celda con un peso inicial
int ESCALA = 114000 / 312;

int bal;
int bal1;
int bal2;
int bal3;

//comunicacion serial
#include <SoftwareSerial.h>
SoftwareSerial s(4, 5); //Rx,Tx
void setup() {
  Serial.begin(9600);
  s.begin(9600);
  
  
  Serial.print("Celda 1             ");
  Serial.print("Celda 2           ");
  Serial.print("Celda 3          ");
  Serial.println("Celda 4");
//setear la escala al momento de encerar
  balanza.set_scale(ESCALA); // Establecemos la ESCALA calculada anteriormente
  balanza1.set_scale(ESCALA); //Establecemos la ESCALA calculada anterior
  balanza2.set_scale(ESCALA);
  balanza3.set_scale(ESCALA);
  
  balanza.tare(20); // El peso actual es considerado Tara.
  balanza1.tare(20); //El peso actual es considerado tara (el soporte)
  balanza2.tare(20);
  balanza3.tare(20);
  
  delay(5000); //Esperar 5 segundo para comenzar a pesar 

}
void loop() {
  
 //muestra los pesos en el terminal
  Serial.print("Peso: ");
  Serial.print(balanza.get_units(20), 0);
  Serial.print("g          ");
  Serial.print("Peso:  ");
  Serial.print(balanza1.get_units(20), 0);
  Serial.print("g          ");
  Serial.print("Peso:  ");
  Serial.print(balanza2.get_units(20), 0);
  Serial.print("g          ");
  Serial.print("Peso:  ");
  Serial.print(balanza3.get_units(20), 0); // Se obtiene el valor real del peso en Kg del elemento
  Serial.println("g");

  //obtener valores de cada balanza en gramos
  bal = abs(balanza.get_units(20));
  bal1 = abs(balanza1.get_units(20));
  bal2 = abs(balanza2.get_units(20));
  bal3 = abs(balanza3.get_units(20));
  
  //pasando informacion al nodemcu
  s.write(bal/8.0);
  s.write(bal1/8.0);
  s.write(bal2/8.0);
  s.write(bal3/8.0);
  delay(1000);
}
