/*
   Author: Safwen Baroudi
*/

#include <Servo.h>

#define DELIM " \n"
// This is used to split the string received through serial

Servo myservo;
Servo myservo1;
// We will control the servomotor using this object
//you can instantiate as many objects as pwm pins on your arduino


char buff[32] ;
//buff is a string that will hold the data sent through serial
char* token ;
//this is used with strtok to reference the words splitted according to DELIM

void setup() {
    //This will initialize the serial with a baudrate 115200
    pinMode(2, INPUT_PULLUP);
  myservo.attach(9);
  myservo.write(100);
  delay(500);

  myservo1.attach(10);


  myservo1.write(50);
  delay(500);
  Serial.begin(9600);
  while ( ! Serial ) {
    delay(100);
  }
    attachInterrupt(digitalPinToInterrupt(2), reset, LOW);
   delay(50);
  // Serial.setTimeout(5000);


}

/*
   A single call to this function is equivalent to an up and down motion
   Parameters:
   servo: is a reference to a Servo object, this for decoupling so that you can use
   push with as many servomotors as you need.
   from : an integer denoting the angle from which the servo will go down
   to: an integer denoting the angle the servo will not cross
   t: an integer denoting the interval in milliseconds between between the up and down motion
   decreasing t can speed up the servo motor.
   NOTE: The servo needs about 15 ms to correct
   its position, so t should not be less than 15.
*/
void push(Servo& servo, long from, long to, long t, long duree) {
  servo.write(from);
  delay(duree);
  servo.write(to);
  delay(t);
  /*  servo.write(from);
    delay(t);*/
}

String params = "";
void loop() {
  while ( 1 ) {
    if ( Serial.available() ) {
      bool flipflop = true ;

      params = "";
      params = Serial.readStringUntil('#');
      //  buff = (char*) malloc(sizeof(char) * params.length()) ;
      memset(buff, 0, sizeof(buff));
        if ( params[0] == '1' || params[0] == '2' ) {
        if ( params.substring(1).length() > 31 )
          continue ;
        params.substring(1).toCharArray(buff, params.substring(1).length());

        token = strtok(buff, DELIM );
        if ( token == NULL ) {
          continue ;
        }
        long nb = strtol(token, NULL, 0);
        //duree entre postion de repos et position d'appui
        token = strtok(NULL, DELIM);
        if ( token == NULL ) {
          continue ;
        }
        long t = strtol(token, NULL, 0);

        //duree d'appui
        token = strtok(NULL, DELIM);
        if ( token == NULL ) {
          continue ;
        }
        long dureeAppui = strtol(token, NULL, 0);

        if ( params[0] == '1' ) {
          myservo1.attach(10);
          for ( int i = 0; i < nb; ++i ) {
            push(myservo1, 23, 50, t, dureeAppui);
          }
          myservo1.detach();
        }
        if ( params[0] == '2' ) {
          myservo.attach(9);
          for ( int i = 0; i < nb; ++i ) {
            push(myservo, 140, 110, t, dureeAppui);
          }
          myservo.detach();
        }
       /* void (*reboot)(void) = 0;
        reboot();*/
      }
    }
  }
}

void reset() {
   myservo.attach(9);
  myservo.write(100);
  delay(500);
  myservo.detach();

  myservo1.attach(10);


  myservo1.write(50);
  delay(500);
  myservo1.detach();
  
  void (*reboot)(void) = 0 ;
  reboot();
}

