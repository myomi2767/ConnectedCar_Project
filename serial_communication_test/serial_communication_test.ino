#include "DHT.h"       // DHT.h 라이브러리를 포함한다
#include <stdio.h>
#include <stdarg.h>
#include <Servo.h>

#define LED1 A2 //LED
#define LED A1 //LED
#define servoPin A0 //서보 모터
#define buzzer A8  //부저 
//온습도 센서
#define DHTPIN 4      // DHT핀을 4번으로 정의한다(DATA핀)
#define DHTTYPE DHT11  // DHT타입을 DHT11로 정의한다
#define FAN 5
DHT dht(DHTPIN, DHTTYPE);  // DHT설정 - dht (디지털4, dht11)

char cmd;
// 0은 꺼진 상태, 1은 켜진 상태
String engineStatus = "0"; //엔진 상태
String emergencyStatus = "0"; //비상등 상태
String doorStatus = "0"; //문 상태
String airconditionStatus = "0"; //공조 상태
String hornStatus = "0"; //경적 상태

String result;

String engineOnInfo = "";
int index = 0;
char tempArr[5];
bool isEngineOn = false;

Servo doorservo;//서보를 제어할 도어 서보 오브젝트를 만든다.
int servopos=0; //도어 서보 위치를 저장할 변수를 선언

void setup() {
  Serial.begin(9600);
  pinMode(LED,OUTPUT);
  pinMode(LED1,OUTPUT);
  pinMode(buzzer,OUTPUT);
  pinMode(FAN, OUTPUT);
  doorservo.attach(servoPin);
  doorservo.write(0);
}
/*  들어오는 값 설정
*   
*/
void loop() {
  if(Serial.available()>0){
    cmd = Serial.read();
    if(cmd=='A'){
      //비상등만 켜기
      emergencyStatus = "1";
      int count = 0;
      Serial.println("EOP");
      while(count<=30){
        if(Serial.read()=='X'){
          emergencyStatus = "0";
          break;
        }else if(Serial.read()=='Z'){
          Serial.println(engineStatus+doorStatus+airconditionStatus+emergencyStatus);
        }
        digitalWrite(LED,HIGH);
        delay(1000);
        digitalWrite(LED,LOW);
        delay(1000);
        count = count+2;
        if(count==30){
          emergencyStatus = "0";
        }
      }
    }else if(cmd =='B'){
      //비상등+경적 켜기
      emergencyStatus = "1";
      hornStatus = "1";
      int count = 0;
      Serial.println("EASP");
      while(count<=30){
        if(Serial.read()=='X'){
          emergencyStatus = "0";
          hornStatus = "0"; //경적 상태
          break;
        }else if(Serial.read()=='Z'){
          Serial.println(engineStatus+doorStatus+airconditionStatus+emergencyStatus);
        }
        digitalWrite(LED,HIGH); // LED ON
        tone(buzzer,783,100); // Horn On
        //digitalWrite(buzzer, HIGH);
        delay(1000);
        digitalWrite(LED,LOW);
        tone(buzzer,783,100); // Horn On
        //digitalWrite(buzzer, LOW);
        delay(1000);
        count = count+2;
        if(count == 30){
          emergencyStatus = "0";
          hornStatus = "0";
        }
      }
    }else if(cmd=='O'){
      //도어 열기
      doorStatus = "1";
      result = "DOP";
      doorservo.write(45);
    }else if(cmd=='L'){
      //도어 닫기
      doorStatus = "0";
      result = "DLP";
      doorservo.write(0);
    }else if(cmd == 'S') {
      String num="";
      //엔진 On
      isEngineOn = true;
      engineStatus = '1';
      //CAN에게 시동이 켜졌다고 알림
      result="ESP";
      //에어컨 모터 제어
    }else if(cmd=='T'){                                                                                                           
      //엔진 Off
      analogWrite(FAN, 0);
      engineStatus = '0';
      result="ETP";
    }else if(cmd == 'Z'){
      //차량 상태 불러오기
      Serial.println(engineStatus+doorStatus+airconditionStatus+emergencyStatus);
    }else{
      if(cmd=='A'){
        result = "EOF";
      }else if(cmd =='B'){
        result = "EASF";
      }else if(cmd == 'S'){
        result="ESF";
      }else if(cmd=='T'){
        result="ETF";
      }else if(cmd=='O'){
        result="DOF";
      }else if(cmd=='L'){
        result = "DLF";
      }
    }
    if((cmd!='Z'||cmd!='A'||cmd!='B')&&isEngineOn!=true){
      Serial.println(result);
    }

    if(isEngineOn == true) {
      if(engineOnInfo.length() < 6) {
        //tempArr[index] = cmd;
        //engineOnInfo = tempArr;
        //index++;
        engineOnInfo = String(engineOnInfo + cmd); 
      } 
      if(engineOnInfo.length() == 5) {
        Serial.println(result);
        int temp = engineOnInfo.substring(1,3).toInt();
        int eTime = engineOnInfo.substring(3,5).toInt();

        int h = dht.readHumidity();  // 변수 h에 습도 값을 저장
        int t = dht.readTemperature();  // 변수 t에 온도 값을 저장
        //engineStart 시작
        if(abs(temp-t)>=10){
          analogWrite(FAN, 255);
          isEngineOn = false;
          engineOnInfo = "";
          index = 0;
        }else if(abs(temp-t)>=5){
          analogWrite(FAN, 200);
          isEngineOn = false;
          engineOnInfo = "";
          index = 0;
        }else{
          analogWrite(FAN, 100);
          isEngineOn = false;
          engineOnInfo = "";
          index = 0;
        }
      }
    }
    
  }
}
