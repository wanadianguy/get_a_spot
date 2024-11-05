import 'package:flutter/material.dart';

class MeteoWidget extends StatelessWidget {
  const MeteoWidget({super.key});

  @override
  Widget build(BuildContext context) {
    return const Card(
      child: Padding(
        padding: EdgeInsets.all(16.0),
        child: Text('Ici bientôt la météo'),
      ),
    );
  }
}
