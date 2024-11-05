import 'package:flutter/material.dart';

class ProfileSettingsPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Paramètres du Profil'),
      ),
      body: Center(
        child: Text('Modifier les Paramètres du Profil'),
      ),
    );
  }
}
