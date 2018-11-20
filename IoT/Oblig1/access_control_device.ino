int redLed = 13;
int yellowLed = 12;
int greenLed = 11;
int leftButton = 4;
int rightButton = 2;
int pid = 7;

int state = 0;
int countBtnLeft = 0;
int countBtnRight = 0;
int counter = 3;

unsigned long startMillis;
unsigned long currentMillis;
const unsigned long period = 10000;

void setup() {
  pinMode(redLed, OUTPUT);
  pinMode(yellowLed, OUTPUT);
  pinMode(greenLed, OUTPUT);
  pinMode(leftButton, INPUT);
  pinMode(rightButton, INPUT);
  pinMode(pid, INPUT);
  startMillis = millis();
  Serial.begin(9600);
}

void loop() {
  bool statePid = digitalRead(pid);
  bool stateBtnLeft = digitalRead(leftButton);
  bool stateBtnRight = digitalRead(rightButton);
  
  switch(state) {
    case 1:
    detectMotion(statePid);
    break;
    case 2:
    case 3:
    case 4:
    recordInput(stateBtnLeft, stateBtnRight, state);
    break;
}

void detectMotion(statePid) {
  if(statePid) {
    Serial.println((String) "Detecting motion, state: " + state);
    delay(200);
    digitalWrite(redLed, LOW);
    state = 2;
  }
}

void recordInput(bool stateBtnLeft, bool stateBtnRight, int state) {
  digitalWrite(ledYellow, HIGH);
  if (counter < 3) {
    digitalWrite(ledYellow, HIGH);
    if(stateBtnLeft && !stateBtnRight && state == 2) {
      counter++;
      state = 3;
    } else if (!stateBtnLeft && stateBtnRight && state == 3) {
      counter++;
      state = 4;
    } else if (!stateBtnLeft && stateBtnRight && state == 4) {
      counter++;
      state = 5;
    } else {
      counter++;
    }
    
    //Make yellow LED blink
  if (stateBtnLeft || stateBtnRight) {
      digitalWrite(ledYellow, LOW);
      delay(300);
    }
  } else {
    delay(200);
    blinkRed();
  }
}
  
void lock(state
  
void blinkRed() {
    for (int i = 0; i < 4; i++)
    {
        digitalWrite(ledRed, HIGH);
        delay(200);
        digitalWrite(ledRed, LOW);
        delay(200);
    }
}
