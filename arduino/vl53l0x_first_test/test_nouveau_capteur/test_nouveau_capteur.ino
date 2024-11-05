const int pinCapteur = 13; // Connectez la broche OUT du capteur à cette broche d'entrée analogique

void setup() {
  Serial.begin(9600); // Initialise la communication série
}

void loop() {
  int valeurCapteur = analogRead(pinCapteur); // Lit la valeur du capteur

  Serial.print("Valeur du capteur : ");
  Serial.println(valeurCapteur);

  // Ajoutez votre logique de traitement ici en fonction de la valeur lue

  delay(1000); // Attendez un moment avant de lire à nouveau
}
