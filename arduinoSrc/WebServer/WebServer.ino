#include <ESPmDNS.h>
#include <Smartcar.h>
#include <WebServer.h>
#include <WiFi.h>

const auto ssid     = "group11";
const auto password = "Group111";

WebServer server(80);

//pin setup
const int TRIGGER_PIN = 5; //D5 
const int ECHO_PIN =  18; //D18

//parameter setup
const unsigned int MAX_DISTANCE = 100;
const int MIN_OBSTACLE_DISTANCE = 20; //20cm as the minimum stop distance

//sensor setup
SR04 front(TRIGGER_PIN, ECHO_PIN, MAX_DISTANCE);


BrushedMotor leftMotor(smartcarlib::pins::v2::leftMotorPins);
BrushedMotor rightMotor(smartcarlib::pins::v2::rightMotorPins);
DifferentialControl control(leftMotor, rightMotor);

GY50 gyroscope(11);

HeadingCar car(control, gyroscope);

void setup(void)
{
    Serial.begin(115200);
    WiFi.mode(WIFI_STA);
    WiFi.begin(ssid, password);
    Serial.println("");

    // Wait for connection
    while (WiFi.status() != WL_CONNECTED)
    {
        delay(500);
        Serial.print(".");
    }
    Serial.println("");
    Serial.print("Connected to ");
    Serial.println(ssid);
    Serial.print("IP address: ");
    Serial.println(WiFi.localIP());

    if (MDNS.begin("trailgage"))
    {
        MDNS.addService("http", "tcp", 80);
        Serial.println("MDNS responder started");
    }

    server.on("/drive", []() {
        const auto arguments = server.args();

        for (auto i = 0; i < arguments; i++)
        {
            const auto command = server.argName(i);
            if (command == "speed")
            {
                car.setSpeed(server.arg(i).toInt());
            }
            else if (command == "angle")
            {
                car.setAngle(server.arg(i).toInt());
            }
        }
        server.send(200, "text/plain", "ok");
    });

    server.on("/gyro", []() {
        server.send(200, "text/plain", String(car.getHeading()));
    });

    server.onNotFound(
        []() { server.send(404, "text/plain", "Unknown command"); });

    server.begin();
    Serial.println("HTTP server started");
}

//when the sensor detected the distance from the obstacle is less than 15cm
//the car stops
void obstacleAvoid() {
    if (front.getDistance() <= MIN_OBSTACLE_DISTANCE && front.getDistance() > 0) {
        car.setSpeed(0);
    }
}

void loop(void)
{
    server.handleClient();
    car.update();
    obstacleAvoid();
}
