import 'package:shared_preferences/shared_preferences.dart';

class SharedPreferencesService {
  static SharedPreferencesService? _instance;
  static SharedPreferences? _preferences;

  static Future<SharedPreferencesService> getInstance() async {
    if (_instance == null) {
      _instance = SharedPreferencesService();
      _preferences = await SharedPreferences.getInstance();
    }
    return _instance!;
  }

  // Stocker le site préféré
  Future<void> setSitePrefere(String site) async {
    if (_preferences != null) {
      await _preferences!.setString('site_prefere', site);
    }
  }

  // Récupérer le site préféré
  String? getSitePrefere() {
    return _preferences?.getString('site_prefere');
  }

  // Vérifier si un site préféré est stocké
  bool aSitePrefere() {
    return _preferences?.getString('site_prefere') != null;
  }

  // Modifier le site préféré
  Future<void> modifierSitePrefere(String nouveauSite) async {
    if (_preferences != null) {
      await _preferences!.setString('site_prefere', nouveauSite);
    }
  }
}


// Pour stocker un site préféré :
// await SharedPreferencesService.getInstance().setSitePrefere('Mont HOUY');

// Pour récupérer le site préféré :
// String? sitePrefere = SharedPreferencesService.getInstance().getSitePrefere();

// Pour vérifier si un site préféré est stocké :
// bool siteExiste = SharedPreferencesService.getInstance().aSitePrefere();

// Pour modifier le site préféré :
// await SharedPreferencesService.getInstance().modifierSitePrefere('Mont HOUY');