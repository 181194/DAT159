int redLed = 11;
int yellowLed = 12;
int greenLed = 13;
int leftButton = 3;
int rightButton = 2;
int pid = 5;

bool stateBtnLeft = 0;
bool stateBtnRight = 0;

int state = 0;
int counter = 0;
int max_tries = 3;

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
  
  attachInterrupt(digitalPinToInterrupt(leftButton), pin_btnLeft, CHANGE);
  attachInterrupt(digitalPinToInterrupt(rightButton), pin_btnRight, CHANGE);
}

void loop() {
  bool statePid = digitalRead(pid);
  
  currentMillis = millis();
  if (!statePid && (currentMillis - startMillis >= period)) {
      Serial.println((String) "Timeout system for " + period + " ms locking");
      startMillis = currentMillis;
      lock(statePid);
  }
  
  switch(state) {
    case 1:
    detectMotion(statePid);
    break;
    case 2:
    case 3:
    case 4:
    recordInput();
    break;
    case 5:
    unlock();
    break;
    default:
    lock(statePid);
    break;
  }
}

void detectMotion(bool statePid) {
  if(statePid) {
    Serial.println((String) "Detecting motion, state: " + state);
    delay(200);
    digitalWrite(redLed, LOW);
    state = 2;
  }
}

void recordInput() {
  if (counter < max_tries) {
    digitalWrite(yellowLed, HIGH);
    if(stateBtnLeft && !stateBtnRight && state == 2) {
      counter++;
      state = 3;
    } else if (!stateBtnLeft && stateBtnRight && state == 3) {
      counter++;
      state = 4;
    } else if (!stateBtnLeft && stateBtnRight && state == 4) {
      counter++;
      state = 5;
    } else if (stateBtnLeft || stateBtnRight) {
      counter++;
    }
    
    //Make yellow LED blink
  if (stateBtnLeft || stateBtnRight) {
      digitalWrite(yellowLed, LOW);
      stateBtnLeft = 0;
    stateBtnRight = 0;
      startMillis = millis();
      delay(300);
    }
  } else {
    delay(200);
    blinkRed();
    counter = 0;
    lock(1);
  }
}
  
void lock(int statePid) {
  Serial.println((String) "lock state: " + state + " statePid: " + statePid);
  digitalWrite(redLed, HIGH);
  digitalWrite(greenLed, LOW);
  digitalWrite(yellowLed, LOW);
  delay(200);
  if (statePid) {
    state = 1;
  } else {
    state = 0;
    counter = 0;
  }
}

void unlock() {
  Serial.println((String) "Unlock state: " + state);
  //delay(200);
  digitalWrite(redLed, LOW);
  digitalWrite(yellowLed, LOW);
  digitalWrite(greenLed, HIGH);
  delay(5000);
  digitalWrite(redLed, HIGH);
  digitalWrite(greenLed, LOW);
  
  counter = 0;
  state = 0;
}
  
void blinkRed() {
  for (int i = 0; i < 4; i++) {
    digitalWrite(redLed, HIGH);
    delay(200);
    digitalWrite(redLed, LOW);
    delay(200);
  }
}

void pin_btnRight() {
  stateBtnRight = digitalRead(rightButton);
  Serial.println((String) "Right button pressed: " + stateBtnRight);
}

void pin_btnLeft() {
  stateBtnLeft = digitalRead(leftButton);
  Serial.println((String) "Left button pressed: " + stateBtnLeft);
}int redLed = 11;
int yellowLed = 12;
int greenLed = 13;
int leftButton = 3;
int rightButton = 2;
int pid = 5;

bool stateBtnLeft = 0;
bool stateBtnRight = 0;

int state = 0;
int counter = 0;
int max_tries = 3;

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
  
  attachInterrupt(digitalPinToInterrupt(leftButton), pin_btnLeft, CHANGE);
  attachInterrupt(digitalPinToInterrupt(rightButton), pin_btnRight, CHANGE);
}

void loop() {
  bool statePid = digitalRead(pid);
  
  currentMillis = millis();
  if (!statePid && (currentMillis - startMillis >= period)) {
      Serial.println((String) "Timeout system for " + period + " ms locking");
      startMillis = currentMillis;
      lock(statePid);
  }
  
  switch(state) {
    case 1:
    	detectMotion(statePid);
    	break;
    case 2:
    case 3:
    case 4:
    	recordInput();
    	break;
    case 5:
    	unlock();
    	break;
    default:
    	lock(statePid);
    	break;
  }
}

void detectMotion(bool statePid) {
  if(statePid) {
    Serial.println((String) "Detecting motion, state: " + state);
    delay(200);
    digitalWrite(redLed, LOW);
    state = 2;
  }
}

void recordInput() {
  if (counter < max_tries) {
    digitalWrite(yellowLed, HIGH);
    if(stateBtnLeft && !stateBtnRight && state == 2) {
      counter++;
      state = 3;
    } else if (!stateBtnLeft && stateBtnRight && state == 3) {
      counter++;
      state = 4;
    } else if (!stateBtnLeft && stateBtnRight && state == 4) {
      counter++;
      state = 5;
    } else if (stateBtnLeft || stateBtnRight) {
      counter++;
    }
    
    //Make yellow LED blink
  	if (stateBtnLeft || stateBtnRight) {
      digitalWrite(yellowLed, LOW);
      stateBtnLeft = 0;
  	  stateBtnRight = 0;
      startMillis = currentMillis;
      delay(300);
    }
  } else {
    delay(200);
    blinkRed();
    counter = 0;
    lock(1);
  }
}
  
void lock(int statePid) {
  Serial.println((String) "lock state: " + state + " statePid: " + statePid);
  digitalWrite(redLed, HIGH);
  digitalWrite(greenLed, LOW);
  digitalWrite(yellowLed, LOW);
  delay(200);
  if (statePid) {
    state = 1;
  } else {
    state = 0;
    counter = 0;
  }
}

void unlock() {
  Serial.println((String) "Unlock state: " + state);
  //delay(200);
  digitalWrite(redLed, LOW);
  digitalWrite(yellowLed, LOW);
  digitalWrite(greenLed, HIGH);
  delay(5000);
  digitalWrite(redLed, HIGH);
  digitalWrite(greenLed, LOW);
  
  counter = 0;
  state = 0;
}
  
void blinkRed() {
  for (int i = 0; i < 4; i++) {
    digitalWrite(redLed, HIGH);
    delay(200);
    digitalWrite(redLed, LOW);
    delay(200);
  }
}

void pin_btnRight() {
  stateBtnRight = digitalRead(rightButton);
  Serial.println((String) "Right button pressed: " + stateBtnRight);
}

void pin_btnLeft() {
  stateBtnLeft = digitalRead(leftButton);
  Serial.println((String) "Left button pressed: " + stateBtnLeft);
}int redLed = 11;
int yellowLed = 12;
int greenLed = 13;
int leftButton = 4;
int rightButton = 2;
int pid = 3;

bool stateBtnLeft = 0;
bool stateBtnRight = 0;

int state = 0;
int counter = 0;
int max_tries = 3;

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
  
  attachInterrupt(0, pin_btnLeft, CHANGE);
  attachInterrupt(0, pin_btnRight, CHANGE);
}

void loop() {
  bool statePid = digitalRead(pid);
  
  currentMillis = millis();
  if (!statePid && (currentMillis - startMillis >= period)) {
      Serial.println((String) "Timeout system for " + period + " ms locking");
      startMillis = currentMillis;
      lock(statePid);
  }
  
  switch(state) {
    case 1:
    	detectMotion(statePid);
    	break;
    case 2:
    case 3:
    case 4:
    	recordInput(stateBtnLeft, stateBtnRight, state);
    	break;
    case 5:
    	unlock();
    	break;
    default:
    	lock(statePid);
    	break;
  }
}

void detectMotion(bool statePid) {
  if(statePid) {
    Serial.println((String) "Detecting motion, state: " + state);
    delay(200);
    digitalWrite(redLed, LOW);
    state = 2;
  }
}

void recordInput(bool stateBtnLeft, bool stateBtnRight, int state) {
  if (counter < max_tries) {
    digitalWrite(yellowLed, HIGH);
    if(stateBtnLeft && !stateBtnRight && state == 2) {
      counter++;
      state = 3;
    } else if (!stateBtnLeft && stateBtnRight && state == 3) {
      counter++;
      state = 4;
    } else if (!stateBtnLeft && stateBtnRight && state == 4) {
      counter++;
      state = 5;
    } else if (stateBtnLeft || stateBtnLeft) {
      counter++;
    }
    
    //Make yellow LED blink
  	if (stateBtnLeft || stateBtnRight) {
      digitalWrite(yellowLed, LOW);
        stateBtnLeft = 0;
  		stateBtnRight = 0;
      delay(300);
    }
  } else {
    delay(200);
    blinkRed();
  }
}
  
void lock(int statePid) {
  Serial.println((String) "lock state: " + state + " statePir: " + statePid);
  digitalWrite(redLed, HIGH);
  digitalWrite(greenLed, LOW);
  delay(200);
  if (statePid)
      state = 1;
  
  stateBtnLeft = 0;
  stateBtnRight = 0;
}

void unlock() {
  Serial.println((String) "Unlock state: " + state);
  delay(200);
  digitalWrite(redLed, LOW);
  digitalWrite(yellowLed, LOW);
  digitalWrite(yellowLed, HIGH);
  delay(5000);
  digitalWrite(redLed, HIGH);
  digitalWrite(greenLed, LOW);
  
  state = 0;
}
  
void blinkRed() {
    for (int i = 0; i < 4; i++)
    {
        digitalWrite(redLed, HIGH);
        delay(200);
        digitalWrite(redLed, LOW);
        delay(200);
    }
}

void pin_btnRight() {
  stateBtnRight = digitalRead(rightButton);
}

void pin_btnLeft() {
  stateBtnLeft = digitalRead(leftButton);
}
