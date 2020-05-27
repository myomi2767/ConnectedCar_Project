//Control LED using Given by Serial communication
//if input value is 1 => LED : On
//if inputt value is 0 => LED : Off
#define LED A2
char cmd;
void setup() {
Serial.begin(9600);
pinMode(LED,OUTPUT);
}

void loop() {
  if(Serial.available()>0){
    cmd = Serial.read();
    if(cmd=='1'){
      digitalWrite(LED,HIGH);
      Serial.println("success");
    }else if(cmd =='0'){
      digitalWrite(LED,LOW);
      Serial.println("fail");
    }
  }

}
