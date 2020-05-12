#include <Smartcar.h>
#include <WiFi.h>
#include <ESPmDNS.h>
#include <WebServer.h>

// Resource:
// https://randomnerdtutorials.com/esp32-web-server-arduino-ide/?fbclid=IwAR1bAyt8uVaCNt-9zgqoXfB_i5d4xWJBqkvdivkWT8SmBXv6tETwr3PEi1Q

// Replace with your network credentials
const char* ssid     = "group11";
const char* password = "Group111";

// Set web server port number to 80
WiFiServer server(80);

BrushedMotor leftMotor(smartcarlib::pins::v2::leftMotorPins);
BrushedMotor rightMotor(smartcarlib::pins::v2::rightMotorPins);
DifferentialControl control(leftMotor, rightMotor);

SimpleCar car(control);

// Variable to store the HTTP request
String header;

void setup() {
  Serial.begin(9600);
  
  // Connect to Wi-Fi network with SSID and password
  Serial.print("Connecting to ");
  Serial.println(ssid);
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  
  // Print local IP address and start web server
  Serial.println("");
  Serial.println("WiFi connected.");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
  
  if (MDNS.begin("trailgage"))
    {
        MDNS.addService("http", "tcp", 80);
        Serial.println("MDNS responder started");
    }

  server.begin();
}

void loop() {
  WiFiClient client = server.available();   // Listen for incoming clients

  if (client) {                             // If a new client connects,
    Serial.println("New Client.");          // print a message out in the serial port
    String currentLine = "";                // make a String to hold incoming data from the client
    while (client.connected()) {            // loop while the client's connected
      if (client.available()) {             // if there's bytes to read from the client,
        char c = client.read();             // read a byte, then
        Serial.write(c);                    // print it out the serial monitor
        header += c;
        if (c == '\n') {                    // if the byte is a newline character
          // if the current line is blank, you got two newline characters in a row.
          // that's the end of the client HTTP request, so send a response:
          if (currentLine.length() == 0) {
            // HTTP headers always start with a response code (e.g. HTTP/1.1 200 OK)
            // and a content-type so the client knows what's coming, then a blank line:
            client.println("HTTP/1.1 200 OK");
            client.println("Content-type:text/html");
            client.println("Connection: close");
            client.println();

            // The basic functions
            if (header.indexOf("GET /forward") >= 0) {
              Serial.println("Move forward");
              car.setSpeed(100);
              car.setAngle(0);
            } else if (header.indexOf("GET /backward") >= 0) {
              Serial.println("Move backward");
              car.setSpeed(-100);
              car.setAngle(0);
            } else if (header.indexOf("GET /left") >= 0) {
              Serial.println("Go left");
              car.setSpeed(80);
              car.setAngle(-75);
            } else if (header.indexOf("GET /right") >= 0) {
              Serial.println("Go right");
              car.setSpeed(80);
              car.setAngle(75);
            } else if (header.indexOf("GET /stop") >= 0) {
              Serial.println("Stop");
              car.setSpeed(0);
              car.setAngle(0);
            }

            // Display the HTML web page
            client.println("<!DOCTYPE html><html>");
            client.println("<head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
            client.println("<link rel=\"icon\" href=\"data:,\">");
            // CSS to style the on/off buttons
            // Feel free to change the background-color and font-size attributes to fit your preferences
            client.println("<style>html { font-family: Helvetica; display: inline-block; margin: 0px auto; text-align: center;}");
            client.println(".button { background-color: #4CAF50; border: none; color: white; padding: 16px 40px;");
            client.println("text-decoration: none; font-size: 30px; margin: 2px; cursor: pointer;}");
            client.println(".button2 {background-color: #266ebf;}</style></head>");

            // Web Page Heading
            client.println("<body><h1>TrailGage Web Server</h1>");

           // Buttons of the functions
            client.println("<p><a href=\"/forward\"><button class=\"button button2\">Forward</button></a></p>");
           
            client.println("<p><a href=\"/backward\"><button class=\"button button2\">Backward</button></a></p>");

            client.println("<p><a href=\"/stop\"><button class=\"button button2\">Stop</button></a></p>");

            client.println("<p><a href=\"/left\"><button class=\"button button2\">Left</button></a></p>");

            client.println("<p><a href=\"/right\"><button class=\"button button2\">Right</button></a></p>");
            

            client.println("</body></html>");

            // The HTTP response ends with another blank line
            client.println();
            // Break out of the while loop
            break;
          } else { // if you got a newline, then clear currentLine
            currentLine = "";
          }
        } else if (c != '\r') {  // if you got anything else but a carriage return character,
          currentLine += c;      // add it to the end of the currentLine
        }
      }
    }
    // Clear the header variable
    header = "";
    // Close the connection
    client.stop();
    Serial.println("Client disconnected.");
    Serial.println("");
  }
}
