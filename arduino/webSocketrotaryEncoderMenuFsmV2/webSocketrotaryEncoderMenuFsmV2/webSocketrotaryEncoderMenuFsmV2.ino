  #include <MD_MAX72xx.h>                         // Needed for Parola
  #include <MD_Parola.h>                          // Parola LED library
  
  #include <DS3231.h>
    
  #include "Font_Data.h" // FOR JK FONT
 // #include "Parola_Fonts_data.h" // includes arabic and greek fonts
 
  #include <WiFi.h>
  #include <WebServer.h>
  #include <WebSocketsServer.h>
  //#include <ArduinoJson.h>
  #include "webpage.h"


  #define HARDWARE_TYPE MD_MAX72XX::FC16_HW       // setup define the type of hardware connected
  
  #define CLK_PIN     18                          // setup CLK / SCK pin number  (EB setup: yellow)
  #define DATA_PIN    23                          // setup DIN / MOSI pin number (EB setup: brown)
  #define CS_PIN      15                           // setup LOAD / SS pin number  (EB setup: green)
  
  #define MAX_DEVICES 16                          // setup num of LED matrices
  #define MAX_ZONES   3                           // num of zones
  
  MD_Parola P = MD_Parola(HARDWARE_TYPE, CS_PIN, MAX_DEVICES);

  #define BUF_SIZE 250
  
  char P_message0[9] = { "" };                   // message buffer zone 0 (time)
  char P_message1[20] = { "" };                  // message buffer zone 1
  char P_message2[BUF_SIZE] = {""};            // message buffer zone 2 


  WebServer server;
  WebSocketsServer webSocket = WebSocketsServer(81); //create websocket server object with port 81

  //StaticJsonDocument<200> buf;

  const char *ssid = "JK32AP2"; //AP ssid
  const char *password = "123456789"; 
  
     
  DS3231 rtclock;  // DS3231 object declaration
  RTCDateTime dt;  // Declare RTCDateTime data structure

  int pinDT = 2;
  int pinCLK = 0;
  int pinSW = 4; 

  #define BUZZER_PIN 12                           // @EB-setup buzzer pin
int buzz_volume = 80;                           // @EB-share buzzer volume (duty cycle 0-100)
int buzz_channel = 0;
int buzz_resolution = 8;

/****************************************************************************************************************/
/****************************************  setup      ***********************************************************/
/****************************************************************************************************************/

   void setup()
   {
        pinMode (pinDT, INPUT);
        pinMode (pinCLK, INPUT);
        pinMode(pinSW,INPUT_PULLUP);

        Serial.begin(115200);                          // for debugging
        
       setZones3();                                    // start the Parola display
        

        WiFi.softAP(ssid, password);
        Serial.println("\n");
        Serial.print("Soft Access point ready: ");
        Serial.println(ssid);
        IPAddress myIP = WiFi.softAPIP();
        Serial.print("ESP 32 has AP IP address: ");
        Serial.println(myIP);
    
        server.on("/",[](){server.send_P(200, "text/html", webpage);  
                          });
        server.begin();
        webSocket.begin();
        webSocket.onEvent(webSocketEvent);

        ledcSetup(buzz_channel, 0, buzz_resolution);  // setup the buzzer
        //ledcSetup(uint8_t channel, double freq, uint8_t resolution_bits);
    
  }

/****************************************************************************************************************/
/****************************************  main loop  ***********************************************************/
/****************************************************************************************************************/

 void loop() { 

  

   webSocket.loop();
   server.handleClient();
   if(Serial.available() > 0) {
    char c[] = {char(Serial.read())};
   
    webSocket.broadcastTXT(c, sizeof(c));
  }
  
  // showClk();
  //showMenu();
  
    state_machine_run(Monitor_Encoder()); // Run state machine and start monitoring the encoder movement

    P.displayAnimate(); 
    //Serial.println(WiFi.softAPIP());
  }


/****************************************************************************************************************/
/****************************************  state machine   ******************************************************/
/****************************************************************************************************************/

     enum Encoder_enum {NONE,CLICK, TURN_RIGHT, TURN_LEFT};
     
     enum state_enum {HOME, SHOW_MENU, SET_LANG, SET_HOUR, SET_MINUTES, SET_SECONDS, SET_DAY,
                      SET_MONTH, SET_YEAR, SET_BRIGHT, SET_SCROLL, SET_ANIM, SET_FONT};
     uint8_t state = HOME;

     bool done = false;
   

     void state_machine_run(uint8_t encoder)  {
      switch(state)
      {
      case HOME: 
        showClk();
        
        if(encoder == CLICK) state = SHOW_MENU; 
        else state = HOME;  
      break;

      case SHOW_MENU:
 
               showMenu();

              if(done == true) {
                   
                      if (strcmp(P_message2, "Home") == 0){ // strcmp() returns 0 when the strings match
                   
                            state = HOME;
                            done = false;
                      }
                      else if (strcmp(P_message2, "set language") == 0){
                              
                              state = SET_LANG;
                              done = false;
                      }
                      else if (strcmp(P_message2, "set hour") == 0){

                              state = SET_HOUR;
                              done = false;
                      }
                      else if (strcmp(P_message2, "set minutes") == 0){
     
                              state = SET_MINUTES;
                              done = false;
                      }
                      else if (strcmp(P_message2, "set seconds") == 0){
                       
                              state = SET_SECONDS;
                              done = false;
                      }
                      else if (strcmp(P_message2, "set day") == 0){
                        
                              state = SET_DAY;
                              done = false;
                      }
                     else if (strcmp(P_message2, "set month") == 0){
                        
                              state = SET_MONTH;
                              done = false;
                      }
                      else if (strcmp(P_message2, "set year") == 0){
                        
                              state = SET_YEAR;
                              done = false;
                      }
                      else if (strcmp(P_message2, "set brightness") == 0){
                       
                              state = SET_BRIGHT;
                              done = false;
                      }
                      else if (strcmp(P_message2, "set scroll") == 0){
                       
                              state = SET_SCROLL;
                              done = false;
                      }
                      else if (strcmp(P_message2, "set animation") == 0){
                        
                              state = SET_ANIM;
                              done = false;
                      }
                      else if (strcmp(P_message2, "set font") == 0){
                        
                              state = SET_FONT;
                              done = false;
                      }
//                      else if(isMessage == true){
//                              state = SHOW_MESSAGE;
//                              Serial.println(state);
//                              done = false;
//                        }
                      
                      else   state = HOME;
         }
        break;

        case SET_LANG: 
              setLanguage();
              if(done == true){
                state = HOME;
                done = false;
                } 
        break;
        
        case SET_HOUR: 
              setHour();
              if(done == true){
                state = HOME;
                done = false;
                } 
        break;

        case SET_MINUTES: 
              setMinute();
              if(done == true){
                state = HOME;
                done = false;
                } 
        break;

        case SET_SECONDS: 
              setSecond();
              if(done == true){
                state = HOME;
                done = false;
                } 
        break;
        
        case SET_DAY: 
              setDay();
              if(done == true){
                state = HOME;
                done = false;
                } 
        break;

        case SET_MONTH: 
              setMonth();
              if(done == true){
                state = HOME;
                done = false;
                } 
        break;

        case SET_YEAR: 
              setYear();
              if(done == true){
                state = HOME;
                done = false;
                } 
        break;

        case SET_BRIGHT: 
              setBright();
              if(done == true){
                state = HOME;
                done = false;
                } 
        break;

        case SET_SCROLL: 
              setScroll();
              if(done == true){
                state = HOME;
                done = false;
                } 
        break;

//        case SET_ANIM: 
//            //  setAnim();
//              if(done == true){
//                state = HOME;
//                done = false;
//                } 
//        break;
//
//        case SET_FONT: 
//            //  setFont();
//              if(done == true){
//                state = HOME;
//                done = false;
//                } 
//        break;
//        case SHOW_MESSAGE: 
//             showMessage();
//           
//              if(done == true){
//                state = HOME;
//                done = false;
//                } 
//        break;

        default: HOME;  
      
      }
   }


/****************************************************************************************************************/
/****************************************  websocket event ******************************************************/
/****************************************************************************************************************/
  typedef struct{int yyyy; int mm; int dd; int hh; int ii; int ss;} dateTime;
  
  dateTime dtSettings;
  
  char webMessage[BUF_SIZE] = {};
 // static uint32_t webMessageTime = 0;
 // int webMessagePause = 30000;

   uint8_t lang = 0;
   uint8_t sprite = 0;
  

  void webSocketEvent(uint8_t num, WStype_t type, uint8_t * payload, size_t length){

   String Hour = "";
   String Minute = "";
   String Second = "";
   String Day = "";
   String Month = "";
   String Year = "";
   String scrol = "";
   String br = "";
   uint8_t counter = 0;
  
  if(type == WStype_TEXT){
    
      if(payload[0] == '#'){


    
        dtSettings.yyyy = dt.year; 
        dtSettings.mm = dt.month; 
        dtSettings.dd = dt.day; 
        dtSettings.hh = dt.hour; 
        dtSettings.ii = dt.minute; 
        dtSettings.ss = dt.second;

          switch(payload[1]){

          case 'h':
                for(int i = 2; i < 4; i++)  Hour += char(payload[i]);
                dtSettings.hh = Hour.toInt();
          break;

          case 'i':
                for(int i = 2; i < 4; i++)  Minute += char(payload[i]);
                dtSettings.ii = Minute.toInt();
          break;

          case 's':
                for(int i = 2; i < 4; i++)  Second += char(payload[i]);
                dtSettings.ss = Second.toInt();
          break;

          case 'd':
                for(int i = 2; i < 4; i++)  Day += char(payload[i]);
                dtSettings.dd = Day.toInt();
          break;

          case 'm':
                for(int i = 2; i < 4; i++)  Month += char(payload[i]);
                dtSettings.mm = Month.toInt();
          break;

          case 'y':
                for(int i = 2; i < 6; i++)  Year += char(payload[i]);
                dtSettings.yyyy = Year.toInt();
          break;

          case 'b':
                for(int i = 2; i < 4; i++)  br += char(payload[i]);
                P.setIntensity(2, br.toInt());
                P.setIntensity(1, br.toInt());
                P.setIntensity(0, br.toInt());
          break;

          case 'r':
                for(int i = 2; i < 4; i++)  scrol += char(payload[i]);
                P.setSpeed(2, scrol.toInt());
          break;

          case 'l':
               if(payload[2] == 49) lang = 1; //49 ascii for 1
               else lang = 0; //48 ascii for 0
          break;
          
          default:
          break;

        }  
          
          rtclock.setDateTime(dtSettings.yyyy,dtSettings.mm,dtSettings.dd,dtSettings.hh,dtSettings.ii,dtSettings.ss);
    }

    else {   // P.setFont(2,fontArabic);
               memset(P_message2,0,sizeof(P_message2)); //empty out the previous contents
               memset(webMessage,0, sizeof(webMessage));// before putting new ones in
                         //can also use for loop and assign '\0' all elements, to do the above
               P.setInvert(1, true);
               P.setInvert(0,true); 
              // playTone(true);
              playBuzzer();
            
            for(int i = 0; i < length; i++){
              webMessage[i] = char (payload[i]); 
              }         
             
              sprintf(P_message2," >>>>> %s *** %s *** %s <<<<<< ",webMessage,webMessage,webMessage);            
              P.displayReset(2);
              
              Serial.println(P_message2);
         }  
    } 
}


/****************************************************************************************************************/
/****************************************  play tone  ***********************************************************/
/****************************************************************************************************************/

  void playBuzzer() {

      ledcAttachPin(BUZZER_PIN, buzz_channel);  
      ledcWrite(buzz_channel, buzz_volume);
      for (int i = 600; i <= 1400; i += 200) {
        ledcWriteTone(buzz_channel, i);
        delay(100);
        ledcWriteTone(buzz_channel, i);
        delay(100);
        ledcWriteTone(buzz_channel, i);
        delay(100);
        ledcWriteTone(buzz_channel, i);
        delay(100);
        ledcWriteTone(buzz_channel, i);
        delay(100);
        ledcWriteTone(buzz_channel, i);
        delay(100);
      }
      ledcWriteTone(buzz_channel, 0);
      ledcWrite(buzz_channel, LOW);
      ledcDetachPin(BUZZER_PIN);
    
      pinMode(BUZZER_PIN, INPUT);           // make sure the buzzer is silent 
  }
/****************************************************************************************************************/
/****************************************  show clk  ***********************************************************/
/****************************************************************************************************************/

  static uint32_t lastZone0Time = 0;
  static uint32_t lastZone1Time = 0;
  //static uint32_t lastZone2Time = 0;

 // int zone2Pause = 100; 
  int zone1Pause = 5000; 
    
  boolean colonFlag = true; 
  
  char daysOfTheWeek[2][7][12] = {{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday","Sunday"},
                                  {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi","Dimanche"}};
  
  char months[2][12][12] = {{"January", "February", "March", "April", "May", "June", "July","August","September","October","November","December"},
                            {"Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin","Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Decembre" }};

  uint8_t loop1Counter = 0;
  uint8_t loop2Counter = 0;
  
  void showClk(){
   
          P.setTextEffect (2, PA_SCROLL_LEFT, PA_SCROLL_LEFT);
          
            
          
          if ((millis() - lastZone0Time >= 1000)) { // blink colon flag every second  
           
              snprintf(P_message0, sizeof(P_message0), "%02d%c%02d", dt.hour, (colonFlag ? ':' : ' '), dt.minute);
              P.displayReset(0);
              colonFlag = !colonFlag;
              lastZone0Time = millis();
            }
            
     
             if ((millis() - lastZone1Time >= zone1Pause)) { // display the zone 1 animation after a pause
                lastZone1Time = millis(); 
               //if (P.getZoneStatus(1)){ 
                    switch (loop1Counter) {
                        case 0:     
                        sprintf(P_message1, " %2d°C", T);
                        P.displayReset(1);         
                        loop1Counter++;   
                        break;           

                        case 1:     
                        sprintf(P_message1, "%02d:%02d", dt.day,dt.month);
                        P.displayReset(1);         
                        loop1Counter = 0;                     
                        break;

//                        case 2:     
//                        sprintf(P_message1, " %02d", dt.second);
//                        P.displayReset(1);         
//                        loopCounter = 0;                     
//                        break;   
                    }  
             }
           
             if (P.getZoneStatus(2)){ // see if the current animation is done
                 // if ((millis() - lastZone2Time >= zone2Pause)) { 
                  //    lastZone2Time = millis();
                      P.setInvert(0,false);
                      P.setInvert(1,false);
                     // P.setFont(2,0);
                    
                     switch(loop2Counter){
                      case 0:
                        sprintf(P_message2," %s %02d %s %d ",daysOfTheWeek[lang][dt.dayOfWeek-1 ],dt.day, months[lang][dt.month-1], dt.year);
                        P.displayReset(2);
                        loop2Counter++;
//                      break;
//                      
//                      case 1:
//                        sprintf(P_message2," Access Point: JK32AP,  IP: 192.168.4.1 ");
//                        P.displayReset(2);
//                        loop2Counter++;
//                      break;
//
//                      case 2:
//                        sprintf(P_message2," Connect to the AP for settings and displaying messages ");
//                        P.displayReset(2);
                        loop2Counter = 0;
                      break;
                     }
                   
                 //}
                
            }

     }


/****************************************************************************************************************/
/****************************************  show menu  ***********************************************************/
/****************************************************************************************************************/

    uint8_t menuIndex = 0;
    const uint8_t numMenuItems = 12;

    char menu[numMenuItems][18] = {"set language", "set hour", "set minutes", "set seconds", "set day", 
                                  "set month", "set year", "set brightness", "set scroll", "set animation","set font", "Home"};

   void showMenu(){

    done = false;
      
        P.setTextEffect (2, PA_PRINT, PA_NO_EFFECT);

       // uint8_t A =  Monitor_Encoder();
      
       if  (Monitor_Encoder() == TURN_RIGHT)
       {
         if (menuIndex < numMenuItems - 1)  menuIndex+=1; else menuIndex = 0;
       //   sprintf(P_message2,"%s", menu[menuIndex]);
           P.displayReset(2);
        }
        if ( Monitor_Encoder() == TURN_LEFT){                
         if (menuIndex > 0) menuIndex-=1; else menuIndex = numMenuItems - 1;
            sprintf(P_message2, "%s",menu[menuIndex]);
            P.displayReset(2);
        }
       if ( Monitor_Encoder() == CLICK)  done = true;
    }

/****************************************************************************************************************/
/****************************************  set language *********************************************************/
/****************************************************************************************************************/

  char langChoice[2][3] = {"EN","FR"};
  
  void setLanguage(){
  //  Serial.println("setting language");
    P.setInvert(2,true);
    P.displayReset(2);

    done = false;

      P.setTextEffect (2, PA_PRINT, PA_NO_EFFECT);

       // uint8_t A =  Monitor_Encoder();
      
       if  (Monitor_Encoder() == TURN_RIGHT)
       {
         if (lang < 1)  lang += 1; else lang = 0;
          sprintf(P_message2,"Lang: %s", langChoice[lang]);
           P.displayReset(2);
        }
        if ( Monitor_Encoder() == TURN_LEFT){                
         if (lang > 0) lang -= 1; else lang = 1;
            sprintf(P_message2, "Lang: %s",langChoice[lang]);
            P.displayReset(2);
        }
       if ( Monitor_Encoder() == CLICK){ 
            P.setInvert(2,false);
            P.displayReset(2);
            done = true;
       }
    }

/****************************************************************************************************************/
/****************************************  set Hour     *********************************************************/
/****************************************************************************************************************/

//  typedef struct{int yyyy; int mm; int dd; int hh; int ii; int ss;} dateTime;
//  
//  dateTime dtSettings;

  uint8_t hh = 12;
  

  uint16_t ii = 30;
  
  uint16_t ss = 30;
  
  uint16_t dd = 15;
  
  uint16_t mm = 6;
  
  int yyyy = 2020;
  
  int br = 0;
  
  int scl = 30;

  int DTLastState = 1;
  int DTcurState = 0;
  int CLKLastState = 1;
  int CLKcurState = 0;
  int SWLastState = 1;
  int SWcurState = 0;


  uint8_t Monitor_Encoder()
  {
    Encoder_enum A = NONE;
    DTcurState = debounce(pinDT);
    CLKcurState = debounce(pinCLK);
    SWcurState = debounce(pinSW);
      
          if ((DTLastState == 0) && (DTcurState == 1)) 
            {
              if (CLKcurState == 0) {
                A = TURN_RIGHT; 
                Serial.println("right");
                }
              else{ 
                A = TURN_LEFT;
                Serial.println("left");
                }
             }
  
          else if((SWcurState != SWLastState) && (SWcurState == 0)) {  
                A = CLICK;
                Serial.println("click");
                }
          else  A = NONE; 
             
      DTLastState = DTcurState;
      CLKLastState = CLKcurState;
      SWLastState = SWcurState;
 
      return A;
   }

/****************************************************************************************************************/
/****************************************  debounce pins  *******************************************************/
/****************************************************************************************************************/
  int debounce(int pinName)
    {
     static int pinLast;
     static int pinCur;
  
      pinLast = digitalRead(pinName);
      delay(2);
  
      if(digitalRead(pinName) == pinLast)  pinCur = digitalRead(pinName);
        
      else  pinCur = pinLast;
      return pinCur;
     }
