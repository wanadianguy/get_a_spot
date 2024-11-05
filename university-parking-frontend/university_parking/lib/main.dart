import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

import 'screens/home/dashboard_page.dart';
import 'screens/parking/campus_page.dart';
import 'screens/parking/parking_page.dart';
import 'screens/settings/profil_settings_page.dart';
import 'screens/authentication/change_mdp_page.dart';
import 'screens/authentication/oublie_mdp_page.dart';
import 'screens/authentication/sign_in_page.dart';
import 'screens/authentication/sign_up_page.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  // Instancier SharedPreferences
  SharedPreferences prefs = await SharedPreferences.getInstance();
  runApp(MyApp(prefs: prefs));
}

// Déclaration des constantes de couleur
const Color couleur1 = Color(0xFF264653); // Bleu très foncé
const Color couleur2 = Color(0xFF2a9d8f); // Vert bizarre
const Color couleur3 = Color(0xFFe9c46a); // Jaune
const Color couleur4 = Color(0xFFf4a261); // Jaune-Orange
const Color couleur5 = Color(0xFFe76f51); // Orange

class MyApp extends StatelessWidget {
  final SharedPreferences prefs;

  MyApp({required this.prefs});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: ThemeData(primaryColor: const Color.fromARGB(255, 199, 199, 199)),
      debugShowCheckedModeBanner: false,
      initialRoute: '/', // La route initiale pour l'application
      routes: {
        '/': (context) => const DashboardPage(),
        '/campus': (context) => CampusPage(),
        '/parking': (context) => ParkingPage(),
        '/settings': (context) => ProfileSettingsPage(),
        '/auth/changeMdp': (context) => ChangeMdpPage(),
        '/auth/oublieMdp': (context) => OublieMdpPage(),
        '/auth/signIn': (context) => SignInPage(),
        '/auth/signUp': (context) => SignUpPage(),
      },
    );
  }
}
