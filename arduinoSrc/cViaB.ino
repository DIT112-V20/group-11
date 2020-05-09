#include <Smartcar.h>
#include <BluetoothSerial.h>

BrushedMotor leftMotor(smartcarlib::pins::v2::leftMotorPins);
BrushedMotor rightMotor(smartcarlib::pins::v2::rightMotorPins);
DifferentialControl control(leftMotor, rightMotor);

SimpleCar car(control);
BluetoothSerial bluetooth;

void setup() {
  // put your setup code here, to run once:
     Serial.begin(9600);
     bluetooth.begin("TrailGage");
}

void loop() {
  // put your main code here, to run repeatedly:
      handleInput();

}

void handleInput() { //handle serial input if there is any
  if (bluetooth.available()) {
    char input;
    while (bluetooth.available()) { 
      input = bluetooth.read(); } //read till last character
    switch (input) {
      case'l': //rotate left going forward        
       car.setSpeed(80); //80% of the full speed        
       car.setAngle(-75); //75 degrees to the left
       break;
      case'r': //turn right        
       car.setSpeed(80);        
       car.setAngle(75);  //75 to the right
       break;
      case'f': //go forward        
       car.setSpeed(100);        
       car.setAngle(0);
       break;
      case'b': //go back        
       car.setSpeed(-100);        
       car.setAngle(0);
       break;
      case's': //stop
       car.setSpeed(0);
       car.setAngle(0);
       break;
      default://if you receive something that you don't know, just stop        
       car.setSpeed(0);        
       car.setAngle(0);     
    }
  }
}
