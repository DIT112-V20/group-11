/* -----------------------------------------
This is the source code for basic obstacle avoidance function of the product of Group11
Sensor we use: HC-SR04 Ultrasonic Sensor
Sensor pins:
VCC as +5V DC power input
Trig as pulse input to trigger the module to initialize US wave
Echo as pulse output after receiveing the US wave reflected by the obstacle
GRN as ground
-----------------------------------------
Reason for choosing HC-SR04 Ultrasonic Sensor: 
the field of view range larger than VL53L0X LiDAR
----------------------------------------- */

#include <Smartcar.h> //https://platisd.github.io/smartcar_shield/index.html

//pin setup
const int TRIGGER_PIN = 18; //D18
const int ECHO_PIN = 5; //D5

//parameter setup
const unsigned int MAX_DISTANCE = 100;
const int MIN_OBSTACLE_DISTANCE = 20; //20cm as the minimum stop distance

//sensor setup
SR04 front(TRIGGER_PIN, ECHO_PIN, MAX_DISTANCE);

//hardware setup
const int START_SPEED = 30; //at the speed of the 30% of the motor's full capacity
const int STOP =0; //set the speed to 0
BrushedMotor leftMotor(smartcarlib::pins::v2::leftMotorPins);
BrushedMotor rightMotor(smartcarlib::pins::v2::rightMotorPins);
DifferentialControl control(leftMotor, rightMotor);

SimpleCar car(control);

//program entry
void setup() {
    Serial.begin(9600); //start the system with thr baud rate of 9600
    car.setSpeed(START_SPEED);
}

//program function code
void loop() {
    
    dataPrint();
    obstacleAvoid();
   
}
int getDistance()
{
  int d =  front.SR04_cm();
  if(d == 0)
  {
    return MAX_DISTANCE;
  }else{
    return d;
  }

//print the distance data on serial monitor every 0.2s
void dataPrint() {
    Serial.println(front.getDistance()); 
    delay(200);
}

//when the sensor detected the distance from the obstacle is less than 15cm
//the car stops
void obstacleAvoid() {
    if (front.getDistance() <= MIN_OBSTACLE_DISTANCE && front.getDistance() > 0) {
        car.setSpeed(STOP);
    }
}
