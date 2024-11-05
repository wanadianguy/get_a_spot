import 'package:flutter/material.dart';

class OublieMdpPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Mot de Passe Oublié'),
      ),
      body: Center(
        child: Text('Réinitialisation du mot de passe'),
      ),
    );
  }
}
