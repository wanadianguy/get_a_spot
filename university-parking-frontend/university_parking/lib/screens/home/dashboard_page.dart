import 'package:flutter/material.dart';
import '../../widgets/meteo_widget.dart';

class DashboardPage extends StatelessWidget {
  const DashboardPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          backgroundColor: const Color.fromARGB(255, 234, 234, 234),
          shadowColor: const Color(0x00000000),
          foregroundColor: const Color(0xFF264653),
          centerTitle: true,
          title: const Text('Get A Spot'),
          leading: IconButton(
            icon: Image.asset(
              'lib/assets/images/app_icon.png',
              width: 32,
              height: 32,
            ),
            onPressed: () {
              Navigator.pushNamed(context, '/');
            },
          ),
          actions: <Widget>[
            IconButton(
              icon: const Icon(Icons.settings),
              onPressed: () {
                Navigator.pushNamed(context, '/settings');
              },
            ),
          ],
        ),
        body: Column(
          children: [
            const Padding(
              padding: EdgeInsets.all(20),
              child: Center(
                  child: Text('Campus Mont-Houy',
                      style:
                          TextStyle(fontSize: 35, color: Color(0xFF264653)))),
            ),
            GestureDetector(
              onTap: () => {Navigator.pushNamed(context, '/campus')},
              child: const Card(
                child: Padding(
                  padding: EdgeInsets.all(16.0),
                  child: Text('Cliquez ici pour plus d\'informations'),
                ),
              ),
            ),
            const MeteoWidget()
          ],
        ));
  }
}
