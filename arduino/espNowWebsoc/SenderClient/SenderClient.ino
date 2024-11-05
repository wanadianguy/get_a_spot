#include <esp_now.h>
#include <WiFi.h>
#include "Adafruit_VL53L0X.h"

Adafruit_VL53L0X lox = Adafruit_VL53L0X();


// Replace with the MAC Address of your receiver ESP32
uint8_t receiverMacAddress[] = {0xF0, 0x08, 0xD1, 0xC8, 0x78, 0x44};

// Structure to send data
typedef struct struct_message {
  int id;
  //char x[50];
  uint16_t distance;
} struct_message;

// Create a struct_message for sending data
struct_message myData;


// Create peer interface
esp_now_peer_info_t peerInfo;


void OnDataSent(const uint8_t *mac, esp_now_send_status_t status) {
  if (status == ESP_NOW_SEND_SUCCESS) {
    Serial.println("Delivery success");
  } else {
    Serial.println("Delivery failed");
  }
}

void setup() {
  Serial.begin(115200);

  lox.begin();

  WiFi.mode(WIFI_STA);

  Serial.print("STA MAC: "); Serial.println(WiFi.macAddress());

  // Initialize ESP-NOW
  if (esp_now_init() == ESP_OK) {
    Serial.println("ESPNow Init Success");
  }
  else {
    Serial.println("ESPNow Init Failed");
    // Retry InitESPNow, add a counte and then restart?
    // InitESPNow();
    // or Simply Restart
    ESP.restart();
  }

  // Once ESPNow is successfully Init, we will register for Send CB to
  // get the status of Trasnmitted packet
  esp_now_register_send_cb(OnDataSent);

  // Register peer (receiver)
  memcpy(peerInfo.peer_addr, receiverMacAddress, 6);
  peerInfo.channel = 1;  // Change this to your desired channel
  peerInfo.encrypt = false;

  if (esp_now_add_peer(&peerInfo) != ESP_OK) {
    Serial.println("Failed to add peer");
    return;
  }

}

void loop() {

  VL53L0X_RangingMeasurementData_t measure;

 // Serial.print("Reading a measurement... ");
  lox.rangingTest(&measure, true); // pass in 'true' to get debug data printout!
  if (measure.RangeStatus != 4 && measure.RangeMilliMeter <= 1000) {  // phase failures have incorrect data
    myData.distance = 1 ;
    Serial.print("Detection");
  } else {
    myData.distance = 0;
    Serial.println("Out of range");
  }

  // Prepare data to send
  myData.id = 1;  // Change the ID as needed
  
  //strncpy(myData.x, "Hello from sender", sizeof(myData.x)); // Copie de la chaîne de caractères dans x

  // Send data
  esp_err_t result = esp_now_send(receiverMacAddress, (uint8_t *) &myData, sizeof(myData));


  if (result == ESP_OK) {
    Serial.println("Data sent");
  } else {
    Serial.println("Error sending data");
  }

  delay(1000);  // Change delay as needed

}

