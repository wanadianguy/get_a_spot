#include <esp_now.h>
//#include <WiFi.h>
#include <WebServer.h>            // needed to create a simple webserver (make sure tools -> board is set to ESP32, otherwise you will get a "WebServer.h: No such file or directory" error)
#include <WebSocketsServer.h>    // needed for instant communication between client and server through Websockets
#include <ArduinoJson.h>        // needed for JSON encapsulation (send multiple variables with one string)
#include <WiFi.h>
#include <HTTPClient.h>

#include <SPI.h>

#include "index.h"
//#include "webpage.h" 
#define CHANNEL 1

WebServer server;
WebSocketsServer webSocket = WebSocketsServer(81);

IPAddress local_ip(192, 168, 1, 1);
IPAddress gateway(192, 168, 1, 0);
IPAddress subnet(255, 255, 255, 0);

// Structure example to receive data
// Must match the sender structure
typedef struct struct_message {
  int id;
  uint16_t distance; // Chaîne de caractères pour stocker le message
} struct_message;

// Create a struct_message called myData
struct_message myData;

// Create a structure to hold the readings from each board
struct_message board1;
struct_message board2;
struct_message board3;
struct_message board4;
struct_message board5;
struct_message board6;

// Create an array with all the structures
struct_message boardsStruct[] = {board1, board2, board3, board4, board5, board6};

//isResetCapteur1
//entrée == true -> va dans le parking | false -> sors du parking
struct Capteur {
  int capteur1;
  int capteur2;
  bool entree;
  bool ajouter;
  bool checkIO;
};

const int nombreDeCoupleCapteurs = 1; // Définissez le nombre de capteurs selon vos besoins
Capteur listeCapteurs[nombreDeCoupleCapteurs];

void updateCapteur(int position, int capteurNum, int nouvelleValeur) {
  Capteur* capteurActuel = &listeCapteurs[position];

  switch (capteurNum) {
    case 1:
      capteurActuel->capteur1 = nouvelleValeur;
      break;
    case 2:
      capteurActuel->capteur2 = nouvelleValeur;
      break;
    default:
      // Gérer le cas où le numéro du capteur n'est ni 1 ni 2
      break;
  }

  verifData(position);
}


void verifData(int position) {
  Capteur* capteurActuel = &listeCapteurs[position];

  if (capteurActuel->checkIO && capteurActuel->capteur1 == 1 && capteurActuel->capteur2 == 0) {
    capteurActuel->entree = true;
  } else if (capteurActuel->checkIO && capteurActuel->capteur1 == 0 && capteurActuel->capteur2 == 1) {
    capteurActuel->entree = false;
  }

  if (capteurActuel->capteur1 == 0 && capteurActuel->capteur2 == 0) {
    capteurActuel->checkIO = true;
  } else {
    capteurActuel->checkIO = false;
  }

  if (capteurActuel->entree && capteurActuel->capteur2 == 0) {
    capteurActuel->ajouter = true;
  } else if (!capteurActuel->entree && capteurActuel->capteur1 == 0) {
    capteurActuel->ajouter = true;
  }

  if (capteurActuel->ajouter && capteurActuel->capteur1 == 1 && capteurActuel->capteur2 == 1) {
    capteurActuel->ajouter = false;
    addDataToDatabase();
  }
}


void addDataToDatabase(){
  // Créez un objet HTTPClient
  HTTPClient http;

  // Spécifiez l'adresse de l'endpoint
  http.begin("http://92.148.25.163:8080/parkingMovementController/add");

  // Spécifiez les en-têtes HTTP
  http.addHeader("Content-Type", "application/json");

  // Créez le corps de la requête JSON
  String json = "{\"id\":\"babaa6\",\"idParking\":\"6580522bdcc5eb02682810bf\",\"timestamp\":\"2018-02-05T12:59:11.332\",\"type\":\"ENTRY\"}";

  // Envoyez la requête POST avec le corps JSON
  int httpCode = http.POST(json);

  // Vérifiez le code de réponse
  if (httpCode > 0) {
    Serial.printf("[HTTP] POST... code: %d\n", httpCode);

    // Si la requête a réussi, vous pouvez imprimer la réponse du serveur
    String payload = http.getString();
    Serial.println(payload);
  } else {
    Serial.printf("[HTTP] POST... failed, error: %s\n", http.errorToString(httpCode).c_str());
  }

  // Libérez les ressources
  http.end();
}

// Fonction de rappel exécutée lors de la réception des données
void OnDataRecv(const uint8_t *mac, const uint8_t *data, int len) {
  char macStr[18];
  Serial.print("Paquet reçu de : ");
  snprintf(macStr, sizeof(macStr), "%02x:%02x:%02x:%02x:%02x:%02x", mac[0], mac[1], mac[2], mac[3], mac[4], mac[5]);
  Serial.println(macStr);

  // Vérification de la taille des données reçues
  if (len != sizeof(struct_message)) {
    Serial.println("Taille de données incorrecte");
    return;
  }

  // Interprétation des données reçues comme une struct_message
  struct_message receivedData;
  memcpy(&receivedData, data, sizeof(struct_message));

  // Affichage des données reçues
  Serial.print("ID: ");
  Serial.println(receivedData.id);
  Serial.print("Message: ");
  Serial.println(receivedData.distance);

  if(receivedData.distance == 1 ){
    addDataToDatabase();
  }
}

void setup() {
  Serial.begin(115200);
  WiFi.mode(WIFI_MODE_APSTA);

  //const char *ssid = "Basile"; // Remplacez "NomDuWifi" par le SSID de votre point d'accès WiFi
  //const char *password = "JulesLeCha"; // Remplacez "MotDePasse" par le mot de passe de votre point d'accès WiFi

  const char* ssid = "Livebox-7414";
  const char* password = "vn9cNtCN6NXvh3NzS5";

  // Initialisez la connexion WiFi en mode Station (STA)
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
      delay(1000);
      Serial.println("Connecting to WiFi...");
  }

  Serial.println("Connected to WiFi!");
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());

  webSocket.begin();
  webSocket.onEvent(webSocketEvent);

  if (esp_now_init() == ESP_OK) {
    Serial.println("ESPNow Init Success");
  } else {
    Serial.println("ESPNow Init Failed");
    ESP.restart();
  }

  esp_now_register_recv_cb(OnDataRecv);
}
 
void loop() {
  server.handleClient();
  webSocket.loop();
}

void webSocketEvent(byte num, WStype_t type, uint8_t * payload, size_t length) {      // the parameters of this callback function are always the same -> num: id of the client who send the event, type: type of message, payload: actual data sent and length: length of payload

  switch (type) {                                     // switch on the type of information sent
    case WStype_DISCONNECTED:                         // if a client is disconnected, then type == WStype_DISCONNECTED
      Serial.println("Client " + String(num) + " disconnected");
      //tft.println("Client " + String(num) + " disconnected");
      break;
    case WStype_CONNECTED:                            // if a client is connected, then type == WStype_CONNECTED
      Serial.println("Client " + String(num) + " connected");
     // tft.println("Client " + String(num) + " connected");
      // optionally you can add code here what to do when connected
      break;
    case WStype_TEXT:                                 // if a client has sent data, then type == WStype_TEXT
      // try to decipher the JSON string received
      StaticJsonDocument<200> doc;                    // create a JSON container
      DeserializationError error = deserializeJson(doc, payload);
      if (error) {
        Serial.print(F("deserializeJson() failed: "));
        Serial.println(error.f_str());
        //tft.print(F("deserializeJson() failed: "));
        //tft.println(error.f_str());
        return;
      }
      else {
        // JSON string was received correctly, so information can be retrieved:
        uint8_t ID = doc["ID"];
        const char* message = doc["Message"];
      // int tft_y = (ID == 1 ? 1 : 22);
       //if (ID == 1) tft_y = 0; else tft_y = 22;
    //    tft.setCursor(0, (ID-1)*tft_y, 2);
        String info = "Place " + String(ID) + ":  " + message;
        Serial.println(info);
     //   tft.println(info);
      }
      Serial.println("");
     // tft.println("");
    
      break;
  }
  
}
