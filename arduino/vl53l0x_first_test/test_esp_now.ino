#include <esp_now.h>
#include <WiFi.h>

// Adresse MAC du récepteur ESP32
uint8_t receiverAddress[] = {0xF0, 0x08, 0xD1, 0xC8, 0x78, 0x44};

void OnDataSent(const uint8_t *mac_addr, esp_now_send_status_t status) {
  if (status == ESP_NOW_SEND_SUCCESS) {
    Serial.println("Message envoyé avec succès");
  } else {
    Serial.println("Échec de l'envoi du message");
  }
}

void setup() {
  Serial.begin(115200);
  
  // Initialisation d'ESP-NOW
  if (esp_now_init() != ESP_OK) {
    Serial.println("Erreur lors de l'initialisation d'ESP-NOW");
    return;
  }

  // Enregistrement de la fonction de rappel pour la confirmation d'envoi
  esp_now_register_send_cb(OnDataSent);

  // Ajout du récepteur à la liste des partenaires
  esp_now_peer_info_t peerInfo;
  memcpy(peerInfo.peer_addr, receiverAddress, 6);
  peerInfo.channel = 0;  
  peerInfo.encrypt = false;

  if (esp_now_add_peer(&peerInfo) != ESP_OK) {
    Serial.println("Erreur lors de l'ajout du récepteur");
    return;
  }
}

void loop() {
  // Message à envoyer
  String message = "Hello ESP-NOW";

  // Envoi du message
  esp_err_t result = esp_now_send(receiverAddress, (uint8_t *)message.c_str(), message.length());

  if (result == ESP_OK) {
    Serial.println("Envoi du message : " + message);
  } else {
    Serial.println("Erreur lors de l'envoi du message");
  }

  delay(5000); // Attente avant d'envoyer un nouveau message
}
