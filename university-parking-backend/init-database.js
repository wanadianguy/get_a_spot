db = new Mongo().getDB("university-parking");



db.createCollection('sites', { capped: false });
db.sites.insert([
  {
    "_id": ObjectId("65804f97dcc5eb02682810bd"),
    "name": "Campus du Mont Houy",
    "_class": "com.universityparking.backend.model.Site",
    "latitude": "50.324321070699696",
    "longitude": "3.5132326432161243"
  },
  {
    "_id": ObjectId("65804fb5dcc5eb02682810be"),
    "name": "Campus des Tertiales",
    "_class": "com.universityparking.backend.model.Site",
    "latitude": "50.36505496269195",
    "longitude": "3.529261530820378"
  }
]);



db.createCollection('parkings', { capped: false });
db.parkings.insert([
  {
    "_id": ObjectId("6580522bdcc5eb02682810bf"),
    "name": "Parking Abel de Pujol 2 et 3",
    "site": new DBRef("sites", ObjectId("65804f97dcc5eb02682810bd")),
    "latitude": 50.321779293237356,
    "longitude": 3.514573009168074,
    "have_captors_installed": true,
    "number_of_places": 300,
    "_class": "com.universityparking.backend.model.Parking"
  },
  {
    "_id": ObjectId("65805235dcc5eb02682810c0"),
    "name": "Parking Abel de Pujol 1",
    "site": new DBRef("sites", ObjectId("65804f97dcc5eb02682810bd")),
    "latitude": 50.32299346577428,
    "longitude": 3.515120468057895,
    "have_captors_installed": true,
    "number_of_places": null,
    "_class": "com.universityparking.backend.model.Parking"
  },
  {
    "_id": ObjectId("6580524ddcc5eb02682810c1"),
    "name": "Parking Herbin",
    "site": new DBRef("sites", ObjectId("65804f97dcc5eb02682810bd")),
    "latitude": 50.32796940946332,
    "longitude": 3.513298006226808,
    "have_captors_installed": false,
    "number_of_places": 70,
    "_class": "com.universityparking.backend.model.Parking"
  },
  {
    "_id": ObjectId("658052f7dcc5eb02682810c3"),
    "name": "Parking Tertiales 1",
    "site": new DBRef("sites", ObjectId("65804fb5dcc5eb02682810be")),
    "latitude": 50.36520917152458,
    "longitude": 3.5298702913660307,
    "have_captors_installed": false,
    "number_of_places": 130,
    "_class": "com.universityparking.backend.model.Parking"
  },
  {
    "_id": ObjectId("6580532ddcc5eb02682810c4"),
    "name": "Parking Tertiales 2",
    "site": new DBRef("sites", ObjectId("65804fb5dcc5eb02682810be")),
    "latitude": 50.365606571483156,
    "longitude": 3.526250931842023,
    "have_captors_installed": false,
    "number_of_places": null,
    "_class": "com.universityparking.backend.model.Parking"
  },
  {
    "_id": ObjectId("65816724bb1bfa39969f191c"),
    "name": "Parking Claudin Lejeune",
    "site": new DBRef("sites", ObjectId("65804f97dcc5eb02682810bd")),
    "latitude": 50.32375690686536,
    "longitude": 3.5147530943289373,
    "have_captors_installed": false,
    "number_of_places": 93,
    "_class": "com.universityparking.backend.model.Parking"
  }
]);



db.createCollection('buildings', { capped: false });
db.buildings.insert([
  {
    "_id": ObjectId("6580576ae9abcc22f8f276ee"),
    "name": "Abel de Pujol 1",
    "site": new DBRef("sites", ObjectId("65804f97dcc5eb02682810bd")),
    "latitude": 50.32315928114852,
    "longitude": 3.5142140923841003,
    "_class": "com.universityparking.backend.model.Building"
  },
  {
    "_id": ObjectId("6580577ce9abcc22f8f276ef"),
    "name": "Abel de Pujol 2",
    "site": new DBRef("sites", ObjectId("65804f97dcc5eb02682810bd")),
    "latitude": 50.322034403178876,
    "longitude": 3.513877354675583,
    "_class": "com.universityparking.backend.model.Building"
  },
  {
    "_id": ObjectId("65805782e9abcc22f8f276f0"),
    "name": "Abel de Pujol 3",
    "site": new DBRef("sites", ObjectId("65804f97dcc5eb02682810bd")),
    "latitude": 50.32082380630177,
    "longitude": 3.513480504754778,
    "_class": "com.universityparking.backend.model.Building"
  },
  {
    "_id": ObjectId("658057c0e9abcc22f8f276f1"),
    "name": "Herbin",
    "site": new DBRef("sites", ObjectId("65804f97dcc5eb02682810bd")),
    "latitude": 50.32715924057214,
    "longitude": 3.5134915750770768,
    "_class": "com.universityparking.backend.model.Building"
  },
  {
    "_id": ObjectId("658057cce9abcc22f8f276f2"),
    "name": "Claudin Lejeune",
    "site": new DBRef("sites", ObjectId("65804f97dcc5eb02682810bd")),
    "latitude": 50.32483883058343,
    "longitude": 3.51465680742157,
    "_class": "com.universityparking.backend.model.Building"
  },
  {
    "_id": ObjectId("65805869e9abcc22f8f276f3"),
    "name": "Bâtiment principal",
    "site": new DBRef("sites", ObjectId("65804fb5dcc5eb02682810be")),
    "latitude": 50.36477660183851,
    "longitude": 3.5295300185802025,
    "_class": "com.universityparking.backend.model.Building"
  },
  {
    "_id": ObjectId("65805874e9abcc22f8f276f4"),
    "name": "Gymnase",
    "site": new DBRef("sites", ObjectId("65804fb5dcc5eb02682810be")),
    "latitude": 50.36587195963323,
    "longitude": 3.5258801059994704,
    "_class": "com.universityparking.backend.model.Building"
  }
]);



/*db.createCollection('images', { capped: false });
db.images.insert([
  {
    "_id": ObjectId("65a6edbe469d9a62488a70dc"),
    "data": new Binary(new Base64().decode("/9..."), 0)
  }
])*/
