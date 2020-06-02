//모터 정의 시작
//지금 모터 2개만 제어하는 코드인 듯
int Right_motor_ENB=6;   //우측모터 통제 -- PWD 
int Right_motor_back=7;     //우측모터전진(IN1)
int Right_motor_go=3;     //우측모터후진(IN2)
int Left_motor_go=4;    // 좌측모터 전진(IN3) 
int Left_motor_back=2;   //좌측모터 후진(IN4)
int Left_motor_ENA=5;   //좌측모터 통제 --PWD
int car_speed = 100;
void setup(){
  Serial.begin(9600);
  pinMode(Left_motor_go,OUTPUT); // IN1
  pinMode(Left_motor_back,OUTPUT); // IN2
  pinMode(Left_motor_ENA,OUTPUT);
  pinMode(Right_motor_go,OUTPUT);// IN3
  pinMode(Right_motor_back,OUTPUT);// IN4
  pinMode(Right_motor_ENB,OUTPUT);
}
//IN1,2,3,4, ENA, ENB 는 전압을 준다 VS 안준다 니까 digitalWrite
void run()     // 전진
{
  digitalWrite(Left_motor_go,HIGH);  // IN1
  digitalWrite(Left_motor_back,LOW);     //IN2
  analogWrite(Left_motor_ENA,100);//ENA
  digitalWrite(Right_motor_go,HIGH);//IN3
  digitalWrite(Right_motor_back,LOW);  // IN4
  analogWrite(Right_motor_ENB,80); //ENB
}
void loop() {
    run();
}
