#include <SoftwareSerial.h>// library for setting software serial ports 

int bluetoothTx = 5;// set TX ti digital pin 5 
int bluetoothRx = 6;// set RX to digital pin 6
int grnd = 2;// set ground connection to digtial pin 2
int pwr = 4;// set power pin to pin 4
int incomingByte; // reads incoming bytes 
int counter = 0; // counter variable is set to 0 on start 

SoftwareSerial bluetooth(bluetoothTx, bluetoothRx);//tells the arduino to set RX and TX to the desired pin varables 

void setup() 
{
  bluetooth.begin(115200);
  pinMode(grnd, OUTPUT);// set ground to be an output 
  pinMode(pwr, OUTPUT);// set power to be an output 
  digitalWrite(grnd, LOW); // establish ground 
  digitalWrite(pwr, HIGH);// establish power 
}

void loop() 
{
  counter++;// increase counter every frame

  if (Serial.available() > 0)
    {
      incomingByte = Serial.read();// read the oldest byte in the serial buffer:
  
        if (incomingByte == 'r')// if the incoming byte is equal = R reset the counter
          {
            Serial.println("RESET");// print reset to terminal 
            counter = 0;// set counter to 0
          }
    }
    
  if(counter == 100) // if counter is equal to 100
    {
      counter = 0;// set counter to 0
    }
bluetooth.println(counter);// send the variable counter to the bluetooth shield and output it as data 
delay(10);
}
