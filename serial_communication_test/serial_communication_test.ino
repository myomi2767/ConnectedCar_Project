#include "DHT.h"       // DHT.h 라이브러리를 포함한다
#include <stdio.h>
#include <stdarg.h>
#include <Servo.h>

//모터제어
#define Left_motor_ENA  6
#define Left_motor_go  4
#define Left_motor_back  2
#define Right_motor_ENB  5
#define Right_motor_go  3
#define Right_motor_back  7
#define CAR_DIR_FW 0 //직진
#define CAR_DIR_BK 1 //후진
#define CAR_DIR_LT 2 //좌회전
#define CAR_DIR_RT 3 //우회전
#define CAR_DIR_ST 4 //정지
#define MAX_DISTANCE  200
#define CAR_SPEED_DEFAULT 150


//자동차 제어
#define LED1 A2 //LED
#define LED A1 //LED
#define servoPin A0 //서보 모터
#define buzzer A9  //부저 

//온습도 센서
#define DHTPIN 10      // DHT핀을 4번으로 정의한다(DATA핀)
#define DHTTYPE DHT11  // DHT타입을 DHT11로 정의한다
#define FAN 11
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

//==================================================
int cmFront = 0;
int cmF_old = 0;
int cmF_avg = 0;
int InfraL = HIGH;
int InfraR = HIGH;
int InfraL_Old = HIGH;
int InfraR_Old = HIGH;
int obstacle_cnt = 0;
int obstacle_try = 0;
long duration, cm, avg_cm;
int g_carDirection = CAR_DIR_ST;
int g_carSpeed = 230;
int g_carSpeed_L;
int g_carSpeed_R;
void car_right(){
  digitalWrite(Left_motor_go,HIGH);
  digitalWrite(Left_motor_back,LOW);
  digitalWrite
  (Left_motor_ENA,g_carSpeed);
  digitalWrite(Right_motor_go,HIGH);
  digitalWrite(Right_motor_back,LOW);
  digitalWrite(Right_motor_ENB,g_carSpeed);
}
void car_left(){
  digitalWrite(Left_motor_go,LOW);
  digitalWrite(Left_motor_back,HIGH);
  digitalWrite(Left_motor_ENA,g_carSpeed);
  digitalWrite(Right_motor_go,LOW);
  digitalWrite(Right_motor_back,HIGH);
  digitalWrite(Right_motor_ENB,g_carSpeed);
}
void car_forward(){
  digitalWrite(Left_motor_go,LOW);
  digitalWrite(Left_motor_back,HIGH);
  digitalWrite(Left_motor_ENA,g_carSpeed);
  digitalWrite(Right_motor_go,HIGH);
  digitalWrite(Right_motor_back,LOW);
  digitalWrite(Right_motor_ENB,g_carSpeed);
}
void car_backward() {
  digitalWrite(Left_motor_go,HIGH);
  digitalWrite(Left_motor_back,LOW);
  digitalWrite(Left_motor_ENA,g_carSpeed);
  digitalWrite(Right_motor_go,LOW);
  digitalWrite(Right_motor_back,HIGH);
  digitalWrite(Right_motor_ENB,g_carSpeed);
}
void car_stop(){
  analogWrite(Left_motor_ENA,0);
  analogWrite(Right_motor_ENB,0);
}
void update_Car(){
  switch ( g_carDirection ) {
    case CAR_DIR_FW:
        car_forward();
        break;
    case CAR_DIR_BK:
        car_backward();
        break;
    case CAR_DIR_LT:
        car_left();
        break;
    case CAR_DIR_RT:
        car_right();
        break;
    case CAR_DIR_ST:
        car_stop();
        break;
    default :
        ;
  }
  return;
}
//Serial Protocol
class _CommProtocol
{
private:
  unsigned char protocolPool[28];
  int bufPoint;
public:
  _CommProtocol()
  {
  }
  void addPool(unsigned char cByte)
  {
    if (bufPoint < 28)
    {
      if (bufPoint == 0 and cByte != 0x0c)
        return;  // invalid code
      protocolPool[bufPoint++]=cByte;
      //Serial.print("bufPoint -> ");
      //Serial.println(bufPoint);
    }
  }
  void clearPool()
  {
    bufPoint = 0;
    memset(protocolPool, 0x00, 28);
    //Serial.println("clearPool");
  }
  bool isValidPool()
  {
    if (bufPoint >= 28)
    {
      //Serial.print("protocol length : ");
      if (protocolPool[0] == 0x0c && protocolPool[14] == 0x0c)
      {
        //Serial.println(protocolPool.length());
        return true;
      }
      else
      {
        clearPool();
        //Serial.println("isValidPool 28 OVER");
      }
    }
    return false;
  }
    unsigned char getMotorLValue()
  {
    unsigned char szProto[14];
    memcpy(szProto, protocolPool, 14);
    if (szProto[0] == 0x0C &&
      szProto[1] == 0x00 &&
      szProto[2] == 0x80 &&
      szProto[3] == 0x04 &&
      szProto[4] == 0x02)
    {
      unsigned char l = szProto[5];// -0x32;
      return l;
    }
    return 0x00;
  }
  unsigned char getMotorRValue()
  {
    unsigned char szProto[14];
    memcpy(szProto, &protocolPool[14], 14);
    if (szProto[0] == 0x0C &&
      szProto[1] == 0x00 &&
      szProto[2] == 0x80 &&
      szProto[3] == 0x04 &&
      szProto[4] == 0x01)
    {
      unsigned char l = szProto[5];// -0x32;
      return l;
    }
    return 0x00;
  }
}; //class
_CommProtocol SerialCommData;
void process_SerialCommModule()
{
  if (SerialCommData.isValidPool())
  {
    char motorLR[2];
    motorLR[0] = (char)SerialCommData.getMotorLValue();
    motorLR[1] = (char)SerialCommData.getMotorRValue();
    SerialCommData.clearPool();
    //
    //Serial.print("Left [");
    //Serial.print(motorLR[0],DEC);
    //Serial.print("] Right [");
    //Serial.print(motorLR[1],DEC);
    //Serial.println("]");
    //
    char szCmdValue = '5';
    // set MOVE commands
    if (motorLR[0] == 0 && motorLR[1] == 0) {  // (0,0) stop
      szCmdValue = '5';
    }
    else
    {
      int nSpeed;
      nSpeed = max(abs(motorLR[0]), abs(motorLR[1]));
      // Set direction
      if (motorLR[0] > 0 && motorLR[1] > 0)   // (+,+) forward
      {
        szCmdValue = '2';
        g_carSpeed = 255.0f * ((float)nSpeed / 100.0f);
      }
      else if (motorLR[0] < 0 && motorLR[1] < 0)   // (-,-) backward
      {
        szCmdValue = '8';
        g_carSpeed = 255.0f * ((float)nSpeed / 100.0f);
      }
      else if (motorLR[0] < 0 && motorLR[1] > 0)   // (-,+) left turn
      {
        szCmdValue = '4';
        g_carSpeed = 255.0f * ((float)((float)nSpeed*1.66f) / 100.0f);
      }
      else if (motorLR[0] > 0 && motorLR[1] < 0)   // (+,-) right turn
      {
        szCmdValue = '6';
        g_carSpeed = 255.0f * ((float)((float)nSpeed*1.66f) / 100.0f);
      }
    }
    //
    //Serial.print("speed ");
    //Serial.print(g_carSpeed);
    //Serial.print(" ");
    //Serial.println(szCmdValue);
    //
    // Set the direction and speed with command
    controlByCommand(szCmdValue);
  }
}
void controlByCommand(char doCommand)
{
  switch ( doCommand ) {
    case '+' :    // speed up
      g_carSpeed += 20;
      g_carSpeed = min(g_carSpeed, 255);
      break;
    case '-' :    // speed down
      g_carSpeed -= 20;
      g_carSpeed = max(g_carSpeed, 75);
      break;
    case '2' :    // forward
      g_carDirection = CAR_DIR_FW;
      break;
    case '5' :    // stop
      g_carDirection = CAR_DIR_ST;
      break;
    case '8' :    // backward
      g_carDirection = CAR_DIR_BK;
      break;
    case '4' :    // left
      g_carDirection = CAR_DIR_LT;
      break;
    case '6' :    // right
      g_carDirection = CAR_DIR_RT;
      break;
    default  :
      ;
  }
  return;
}
//==================================================
void setup() {
  Serial.begin(9600);
  
  Serial1.begin(9600);//블루투스용
  
  pinMode(LED,OUTPUT);
  pinMode(LED1,OUTPUT);
  pinMode(buzzer,OUTPUT);
  pinMode(FAN, OUTPUT);
  doorservo.attach(servoPin);
  doorservo.write(0);

  pinMode(Left_motor_go,OUTPUT); // IN1
  pinMode(Left_motor_back,OUTPUT); // IN2
  pinMode(Left_motor_ENA,OUTPUT);
  pinMode(Right_motor_go,OUTPUT);// IN3
  pinMode(Right_motor_back,OUTPUT);// IN4
  pinMode(Right_motor_ENB,OUTPUT);
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
      while(count<=20){
        if(Serial.read()=='Z'){
          Serial.println(engineStatus+doorStatus+airconditionStatus+emergencyStatus);
        }
        //if(Serial.read()=='X'){
        //  emergencyStatus = "0";
        //  break;
        //}
        digitalWrite(LED,HIGH);
        digitalWrite(LED1,HIGH);
        delay(1000);
        digitalWrite(LED,LOW);
        digitalWrite(LED1,LOW);
        delay(1000);
        count = count+2;
        if(count==20){
          emergencyStatus = "0";
        }
      }
    }else if(cmd =='B'){
      //비상등+경적 켜기
      emergencyStatus = "1";
      hornStatus = "1";
      int count = 0;
      Serial.println("EASP");
      while(count<=20){
        //if(Serial.read()=='X'){
        //  emergencyStatus = "0";
        //  hornStatus = "0"; //경적 상태
       //   break;
       // }
        if(Serial.read()=='Z'){
          Serial.println(engineStatus+doorStatus+airconditionStatus+emergencyStatus);
        }
        digitalWrite(LED,HIGH); // LED ON
        digitalWrite(LED1,HIGH);
        tone(buzzer,783,100); // Horn On
        //digitalWrite(buzzer, HIGH);
        delay(1000);
        digitalWrite(LED,LOW);
        digitalWrite(LED1,LOW);
        tone(buzzer,783,100); // Horn On
        //digitalWrite(buzzer, LOW);
        delay(1000);
        count = count+2;
        if(count == 20){
          emergencyStatus = "0";
          hornStatus = "0";
        }
      }
    }else if(cmd=='O'){
      //도어 열기
      doorStatus = "1";
      result = "DOP";
      doorservo.write(45);
      Serial.println(result);
    }else if(cmd=='L'){
      //도어 닫기
      doorStatus = "0";
      result = "DLP";
      doorservo.write(0);
      Serial.println(result);
    }else if(cmd == 'S') {
      String num="";
      //엔진 On
      isEngineOn = true;
      engineStatus = "1";
      airconditionStatus = "1";
      //CAN에게 시동이 켜졌다고 알림
      result="ESP";
      Serial.println(result);
      //에어컨 모터 제어
    }else if(cmd=='T'){                                                                                                           
      //엔진 Off
      analogWrite(FAN, 0);
      engineStatus = "0";
      airconditionStatus = "0";
      result="ETP";
    }else if(cmd == 'Z'){
      //차량 상태 불러오기
      Serial.println(engineStatus+doorStatus+airconditionStatus+emergencyStatus);
    }else{
      if(cmd=='A'){
        result = "EOF";
        Serial.println(result);
      }else if(cmd =='B'){
        result = "EASF";
        Serial.println(result);
      }else if(cmd == 'S'){
        result="ESF";
        Serial.println(result);
      }else if(cmd=='T'){
        result="ETF";
        Serial.println(result);
      }else if(cmd=='O'){
        result="DOF";
        Serial.println(result);
      }else if(cmd=='L'){
        result = "DLF";
        Serial.println(result);
      }
    }

    if(isEngineOn == true) {
      if(engineOnInfo.length() < 6) {
        //tempArr[index] = cmd;
        //engineOnInfo = tempArr;
        //index++;
        engineOnInfo = String(engineOnInfo + cmd); 
      } 
      if(engineOnInfo.length() == 5) {
        
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
  if (Serial1.available()) {
    unsigned char cByte;
    cByte = Serial1.read();
    SerialCommData.addPool(cByte);
    process_SerialCommModule();
    update_Car();
  }
}
