/*--------------------------------------------------------------
  Program:      volt_measure

  Description:  Reads value on analog input A2 and calculates
                the voltage assuming that a voltage divider
                network on the pin divides by 11.
  
  Hardware:     Arduino Uno with voltage divider on A2.
                
  Software:     Developed using Arduino 1.0.5 software
                Should be compatible with Arduino 1.0 +

  Date:         22 May 2013
 
  Author:       W.A. Smith, http://startingelectronics.org
--------------------------------------------------------------*/

// number of analog samples to take per reading
#define NUM_SAMPLES 10
float p0,p1,p2,p3,p4,p5;
int sum = 0;                    // sum of samples taken
unsigned char sample_count = 0; // current sample number
float voltage = 0.0;            // calculated voltage
String inData;


void setup()
{
    pinMode(3, OUTPUT);
    pinMode(4, OUTPUT);
    pinMode(5, OUTPUT);
    pinMode(6, OUTPUT);
    pinMode(8, OUTPUT);
    pinMode(9, OUTPUT);
    pinMode(11, OUTPUT);
    pinMode(13, OUTPUT);  
    Serial.begin(9600);
}

void loop()
{

  while (Serial.available() > 0)
    {
        char recieved = Serial.read();
        inData += recieved; 

        // Process message when new line character is recieved
        if (recieved == '\n')
        {
            //Serial.print("Arduino Received: ");
            //Serial.print(inData);

            int pindata = inData.substring(1,3).toInt();
            int op = inData.substring(3,4).toInt();
            //Serial.println("{\"" + String(pindata) +"\" : \"" + String(op) + "\" }\n\n");
            digitalWrite(pindata, op);
            //Serial.println("{\"status\" : \"success\" }");
            

            inData = ""; // Clear recieved buffer
        }
    }

    p0 = calculatevoltage(0);
    p1 = calculatevoltage(1);
    p2 = calculatevoltage(2);
    p3 = calculatevoltage(3);
    p4 = calculatevoltage(4);
    p5 = calculatevoltage(5);
    
    //char fnl[] = ;
    Serial.println("{\"0\":" + String(p0) + ",\"1\":" + String(p1) + ",\"2\":" + String(p2)  + ",\"3\":" + String(p3) + ",\"4\":" + String(p4) + ",\"5\":" + String(p5) + "}");
    delay(2000);
}

float calculatevoltage(int pin)
{
   while (sample_count < NUM_SAMPLES) {
      switch(pin)
      {
        case 0:
          sum += analogRead(A0);
        break;
        case 1:
          sum += analogRead(A1);
        break;
        case 2:
          sum += analogRead(A2);
        break;
        case 3:
          sum += analogRead(A3);
        break;
        case 4:
          sum += analogRead(A4);
        break;
        case 5:
          sum += analogRead(A5);
        break;
      }
      sample_count++;
        delay(10);
   }
   voltage = ((float)sum / (float)NUM_SAMPLES * 5.015) / 1024.0;
    sample_count = 0;
    sum = 0;// * 11.132
   return voltage * 11.132;
}
