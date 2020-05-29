#define TRIGGER_PIN 10
#define ECHO_PIN 9
int Right_motor_ENB = 5;
int Right_motor_back = 7;
int Right_motor_go = 3;
int Left_motor_go = 4;
int Left_motor_back = 2;
int Left_motor_ENA = 6;
long duration, cm;
int speed_left = 80;
int speed_right = 95;
int speed_sum = 0;
int speed_cal = 0;

void setup() {
  Serial.begin(9600);
  pinMode(TRIGGER_PIN, OUTPUT);
  pinMode(ECHO_PIN, INPUT);
  pinMode(Left_motor_go, OUTPUT);
  pinMode(Left_motor_back, OUTPUT);
  pinMode(Left_motor_ENA, OUTPUT);
  pinMode(Right_motor_go, OUTPUT);
  pinMode(Right_motor_back, OUTPUT);
  pinMode(Right_motor_ENB, OUTPUT);
}

void Ultrasonic(){
  digitalWrite(TRIGGER_PIN,HIGH);
  delay(10);
  digitalWrite(TRIGGER_PIN,LOW);
  duration = pulseIn(ECHO_PIN,HIGH);
  cm = microsecondsToCentimeters(duration);
  Serial.print(cm);
  Serial.println("cm");
  Serial.flush();
  delay(50);
  }

long microsecondsToCentimeters(long microseconds)
{
// 시간에 대한 값을 센티미터로 변환
return microseconds / 29 / 2;
}

void motor(){
  digitalWrite(Left_motor_go,HIGH);
  digitalWrite(Left_motor_back,LOW);
  analogWrite(Left_motor_ENA,speed_left);
  digitalWrite(Right_motor_go,HIGH);
  digitalWrite(Right_motor_back,LOW);
  analogWrite(Right_motor_ENB,speed_right);
}

void loop() {
  motor();
  speed_sum = speed_left + speed_right;
  speed_cal = speed_sum;
  Ultrasonic();
  while(cm < 50){
    Serial.println("와일속");
    speed_cal = speed_cal - 35;
    Serial.println(speed_cal);
    if(speed_cal <= 0){
      speed_cal = 0;
      }
    speed_left = speed_cal * 16/35;
    speed_right = speed_cal * 19/35;
    motor();
    Ultrasonic();
    if(cm >= 50){
      Serial.println("if속");
      for(int i = speed_cal; i <= speed_sum; i = i + 35){
        Serial.println(i);
        speed_left = i * 16/35;
        speed_right = i * 19/35;
        motor();
        Ultrasonic();
        }
      }
    }

}
