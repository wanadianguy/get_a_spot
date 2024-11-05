# university_parking

# Changer le nom de l'application affiché sur le téléphone :

Modifiez le nom de l'application dans le fichier AndroidManifest.xml pour Android, situé dans android/app/src/main:

<application
  android:label="Mon Application de Parking">

  
Pour iOS, changez le CFBundleName dans le fichier Info.plist, situé dans ios/Runner:

<key>CFBundleName</key>
<string>Mon Application de Parking</string>
