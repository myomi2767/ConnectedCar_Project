#include <Servo.h>
#define LED A2
char cmd;
boolean status = true; // 도중에 인터럽트 걸기 위한 조건
// 0은 꺼진 상태, 1은 켜진 상태
String engineStatus = "0"; //엔진 상태
String emergencyStatus = "0"; //비상등 상태
String doorStatus = "0"; //문 상태
String airconditionStatus = "0"; //공조 상태
String hornStatus = "0"; //경적 상태

String result;

Servo doorservo;//서보를 제어할 도어 서보 오브젝트를 만든다.
int servopos=0; //도어 서보 위치를 저장할 변수를 선언

void setup() {
Serial.begin(9600);
pinMode(LED,OUTPUT);
doorservo.attach(D7); //핀9번의 서보를 서보오브젝트에 연결.
}
/*  들어오는 값 설정
*   
*/
void loop() {
  if(Serial.available()>0){
    cmd = Serial.read();
    if(cmd=='A'){
      //비상등만 켜기
      result = "success";
      emergencyStatus = "1";
      int count = 0;
      while(count<=60&status==true){
        digitalWrite(LED,HIGH);
        delay(1000);
        digitalWrite(LED,LOW);
        delay(1000);
        count = count+2;
      }
    }else if(cmd =='B'){
      //비상등+경적 켜기
      result = "success";
      emergencyStatus = "1";
      hornStatus = "1";
      int count = 0;
      while(count<=30){
        digitalWrite(LED,HIGH); // LED ON
        //tone(speakerpin,500,1000); // Horn On
        delay(1000);
        digitalWrite(LED,LOW);
        //tone(speakerpin,500,1000); // Horn On
        delay(1000);
        count = count+2;
      }
    }else if(cmd == '0'){
      //차량 상태 불러오기
      Serial.println(engineStatus+doorStatus+airconditionStatus+emergencyStatus);
    }else if(cmd == 'S') {
      engineStatus = '1';
      //CAN에게 시동이 켜졌다고 알림
      Serial.println(engineStatus);
      //에어컨 모터 제어
    }else if(cmd=='O'){
      //도어 열기
      result = "success";
      doorservo.write(45);

    }else if(cmd=='L'){
      //도어 닫기
      result = "success";
      doorservo.write(0);

    }else{
      result = "fail";
    }
    
    if(cmd!='0'){
      Serial.println(result);
    }
  }
}
