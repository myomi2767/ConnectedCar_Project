#define CAR_DIR_FW  0   // 전진.
#define CAR_DIR_BK  1   // 후진.  코드 안 만들었음
#define CAR_DIR_LF  2   // 좌회전.
#define CAR_DIR_RF  3   // 우회전
#define CAR_DIR_ST  4   // 정지.

// 차량 운행 방향 상태 전역 변수. // 정지 상태.
int carDirection = CAR_DIR_ST; // 
char cmd;
//모터 속도 시작
int speed_left = 0;
int speed_right = 0;
//int speed_sum =speed_left + speed_right;
int speed_sum =0;
int speed_cal = 0;
int velocity = 0; //계기판에 보낼 값
int current_vel = 0;
int lowCount = 0;
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
  analogWrite(Left_motor_ENA,speed_left);//112대신에 변수 넣고 loop에서 캔으로 속도값 받은거 셋팅
  digitalWrite(Right_motor_go,HIGH);
  digitalWrite(Right_motor_back,LOW);
  analogWrite(Right_motor_ENB,speed_right);
}
//좌회전
void car_left()
{ 
  digitalWrite(Left_motor_go,HIGH);
  digitalWrite(Left_motor_back,LOW);
  analogWrite(Left_motor_ENA,255);
  digitalWrite(Right_motor_go,LOW);
  digitalWrite(Right_motor_back,HIGH);
  analogWrite(Right_motor_ENB,95);
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
  else if (!rr && ll)//왼쪽 센서가 검정이면 우회전
  {
    carDirection = CAR_DIR_RF;
  }
  else if (!ll && rr)//오른쪽 센서가 검정이면 좌회전
  {
    carDirection = CAR_DIR_LF;
  }
}
  void common(){
      speed_sum = speed_left + speed_right;
          lt_mode_update();
          car_update();
    }

    void commonplus(){
          lt_mode_update();
          car_update();
    }
void speed_control30(){
        velocity = 30;
        //current_vel = velocity;
        Serial.println(velocity);
        speed_left = 96;
        speed_right = 114;
        common();
        speed_cal = speed_sum;      
  }

  void speed_control60(){
          velocity = 60;
          //current_vel = velocity;
          Serial.println(velocity);
          speed_left = 128;
          speed_right = 152;
          common();
          speed_cal = speed_sum;
  }

  void speed_control90(){
          velocity = 90;
          //current_vel = velocity;
          Serial.println(velocity);
          speed_left = 160;
          speed_right = 190;
          common();
          speed_cal = speed_sum;
  }

  void speed_Plus(){
          //+3km ==> speed_left랑 speed_right가 몇씩 늘어날건지 정해야됨 +3km면 10번
          velocity = velocity + 3;
          //speed_cal = speed_sum;
          speed_cal = speed_cal +7; //10번 늘어나면 맞음
          speed_left = speed_cal * 16/35;
          speed_right = speed_cal * 16/35;
          commonplus();
  }

  void speed_Minus(){
          //-3km  ==> speed_left랑 speed_right가 몇씩 줄어들건지 정해야됨 -3km면 10번
          velocity = velocity - 3;
          if(velocity <= 0){
              velocity = 0;
            }
          speed_cal = speed_cal -7; //10번 줄어들면 맞음
          if(speed_cal <= 0){
              speed_cal = 0;
            }
          speed_left = speed_cal * 16/35;
          speed_right = speed_cal * 16/35;
          commonplus();
  }

  void lowultra(){
          velocity = velocity - 12;
          
          if(velocity <= 0){
              velocity = 0;
            }
          Serial.println(velocity);
          speed_cal = speed_cal -35; //10번 줄어들면 맞음
          if(speed_cal <= 0){
              speed_cal = 0;
            }
          speed_left = speed_cal * 16/35;
          speed_right = speed_cal * 16/35;
          //Serial.println(speed_cal);
          //Serial.println(speed_sum);
          commonplus();
          lowCount = lowCount + 1;
        }

  void highultra(){
          //Serial.println(speed_cal);
          //Serial.println(speed_sum);

            //velocity = current_vel;//기존속도까지 계기판 속도증가
            for(int i = speed_cal; i<= speed_sum; i = i + 35){
              speed_left = i * 16/35;
              speed_right = i * 19/35;
              speed_cal = speed_left + speed_right;
              lt_mode_update();
              car_update();
              //common();
            }
            velocity = velocity + lowCount*12;
            Serial.println(velocity);
            lowCount = 0;
          }
  

void setup()
{
  //시리얼 초기화
  Serial.begin(38400);
  //핀 모드 셋팅
  init_car_controller_board();
}
void loop() {
      cmd = Serial.read();
      
      if(cmd == '1'){
        //30km
        speed_control30();
        }else if(cmd == '2'){
          //60km
          speed_control60();
        }else if(cmd == '3'){
          //90km
          speed_control90();
        }else if(cmd == '4'){
          speed_Plus();
        }else if(cmd == '5'){
          speed_Minus();
        }else if(cmd == '6'){
          lowultra();
        }else if(cmd == '7'){
          highultra();
        }else if(cmd == '9'){
          //auto_off눌렀을 때
          car_forward();// 우선 주행보조장치 off했을 때 무조건 전진하게 해놨는데 리모트 컨트롤이 가능하게 하는게 더 베스트일듯...
        }else if(cmd == '8'){
          //인포테인먼트를 킬때 velocity를 0으로 초기화
          velocity = 0;
          Serial.println(velocity);
          }

}
