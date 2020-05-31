#define CAR_DIR_FW  0   // 전진.
#define CAR_DIR_BK  1   // 후진.  코드 안 만들었음
#define CAR_DIR_LF  2   // 좌회전.
#define CAR_DIR_RF  3   // 우회전
#define CAR_DIR_ST  4   // 정지.

// 
// 차량 운행 방향 상태 전역 변수. // 정지 상태.
int carDirection = CAR_DIR_ST; // 
char cmd;
//모터 속도 시작
int speed_left = 0;
int speed_right = 0;
int 
speed_sum =speed_left + speed_right;
int speed_cal = 0;
int velocity = 0; //계기판에 보낼 값
//모터 속도 끝
//float g_carSpeed = 255.0f * ((float)((float)150*1.66f) / 100.0f);
// 주의:  ENA, ENB 는 PWM 지원 포트에 연결한다.
// 다음 업데이트시 변경합니다.
// ENA, ENB는 DC모터의 속도 조절 및 출발/정지에 사용하는 포트
#define Left_motor_ENA   6
#define Left_motor_go   4
#define Left_motor_back   2

#define Right_motor_ENB   5
#define Right_motor_go   3
#define Right_motor_back   7

void init_car_controller_board()
{
  pinMode(Left_motor_go,OUTPUT); // IN1
  pinMode(Left_motor_back,OUTPUT); // IN2
  pinMode(Left_motor_ENA,OUTPUT);
  pinMode(Right_motor_go,OUTPUT);// IN3
  pinMode(Right_motor_back,OUTPUT);// IN4
  pinMode(Right_motor_ENB,OUTPUT);
}


void car_forward()
{
  digitalWrite(Left_motor_go,HIGH);
  digitalWrite(Left_motor_back,LOW);
  analogWrite(Left_motor_ENA,112);//112대신에 변수 넣고 loop에서 캔으로 속도값 받은거 셋팅
  digitalWrite(Right_motor_go,HIGH);
  digitalWrite(Right_motor_back,LOW);
  analogWrite(Right_motor_ENB,143);
}

//좌회전
void car_left()
{ 
  digitalWrite(Left_motor_go,HIGH);
  digitalWrite(Left_motor_back,LOW);
  analogWrite(Left_motor_ENA,255);
  digitalWrite(Right_motor_go,LOW);
  digitalWrite(Right_motor_back,HIGH);
  analogWrite(Right_motor_ENB,80);
}

//우회전
void car_right()
{
  digitalWrite(Left_motor_go,LOW);  // IN1
  digitalWrite(Left_motor_back,HIGH);     //IN2
  analogWrite(Left_motor_ENA,80);//ENA
  digitalWrite(Right_motor_go,HIGH);//IN3
  digitalWrite(Right_motor_back,LOW);  // IN4
  analogWrite(Right_motor_ENB,255); //ENB//ENB
}


//
//
void car_stop()
{ 
  analogWrite(Left_motor_ENA, 0);
  analogWrite(Right_motor_ENB, 0);
}

//
// 방향 전환값에 의해 차량 운행.
//
void car_update()
{
  if (carDirection == CAR_DIR_FW)  // 전진
  {
    car_forward();
  }
  else
  if (carDirection == CAR_DIR_LF) // 좌회전
  {
    car_left();
  }
  else
  if (carDirection == CAR_DIR_RF) // 우회전
  {
    car_right();
  }
  else
  if (carDirection == CAR_DIR_ST) // 정지.
  {
    car_stop();
  }
}

////
// 라인트레이서 모듈 핀맵
#define LT_MODULE_R A1   
#define LT_MODULE_L A2  

void init_line_tracer_modules()
{
  pinMode(LT_MODULE_L, INPUT);
  //pinMode(LT_MODULE_F, INPUT);
  pinMode(LT_MODULE_R, INPUT);
}

// is detected left
bool lt_isLeft()
{
  //A0를 digitalRead해서 1인지 0인지 읽고, 이게 1이면 true리턴, 0이면 false리턴.
  int ret = digitalRead(LT_MODULE_L);
  return ret == 1 ? true : false;

}

bool lt_isRight()
{
  int ret = digitalRead(LT_MODULE_R);
  return ret == 1 ? true : false;
}

// 
void lt_mode_update()
{
  //라인트레이서 센서 검정색이면 true를 리턴
  int ll = lt_isLeft();
  //int ff = lt_isForward();
  int rr = lt_isRight();

  if (ll&&rr)//센서 세개가 다 검정색 감지했을 때 정지
  {
    carDirection = CAR_DIR_ST;// stop
  }
  //carDirection = CAR_DIR_FW;
  //감지해야 하는 부분
  if (!ll&&!rr)//센서 세개가 흰색일 때 직진
  {
    carDirection = CAR_DIR_FW; 
  }
  else if (rr && !ll)//왼쪽 센서가 검정이면 우회전
  {
    carDirection = CAR_DIR_RF;
  }
  else if (ll && !rr)//오른쪽 센서가 검정이면 좌회전
  {
    carDirection = CAR_DIR_LF;
  }
}

void setup()
{
  //시리얼 초기화
  Serial.begin(38400);
  //핀 모드 셋팅
  init_car_controller_board();
}
void loop()
{
  if(Serial.available()>0){
    cmd = Serial.read();
    if(cmd == '1'){
      //30km
      velocity = 30;
      Serial.println(velocity);
      speed_left = 96;
      speed_right = 114;
      lt_mode_update();//라인트레이서 센서에 다른 전진, 좌/우회전 판단
      car_update();// 판단된거에 맞는 모터 돌리는 애
      }else if(cmd == '2'){
        //60km
        velocity = 60;
        Serial.println(velocity);
        speed_left = 128;
        speed_right = 152;
        lt_mode_update();
        car_update();
      }else if(cmd == '3'){
        //90km
        velocity = 90;
        Serial.println(velocity);
        speed_left = 160;
        speed_right = 190;
        lt_mode_update();
        car_update();
      }
      else if(cmd == '4'){
        //+3km
        velocity = velocity + 3;
        Serial.println(velocity);
        speed_left = speed_left + 16;
        speed_right = speed_right + 19;
        lt_mode_update();
        car_update();
      }
      else if(cmd == '5'){
        //-3km
        velocity = velocity - 3;
        Serial.println(velocity);
        speed_left = speed_left - 16;
        speed_right = speed_right - 19;
        lt_mode_update();
        car_update();
      }
      else if(cmd == '6'){
        //초음파 50 미만
        Serial.println(50);//코드 완벽히 짜면 나중에 지워
        Serial.flush();//코드 완벽히 짜면 나중에 지워
        while(true){
        velocity = velocity - 5;//velocity를 특정값을 넘겨줄지 지금처럼 -5를 반복해서 넘겨줘야하는지 몰라
        Serial.println(velocity);
        speed_cal = speed_cal - 35;
        speed_left = speed_cal * 16/35;
        speed_right = speed_cal * 19/35;
        lt_mode_update();
        car_update();
          if(Serial.read() == '7'){
            for(int i = speed_cal; i<= speed_sum; i = i + 35){
               velocity = velocity + 5;
               Serial.println(velocity);
               speed_left = i * 16/35;
               speed_right = i * 19/35;
               lt_mode_update();
               car_update();
      }
     }
      }
    }
  }
