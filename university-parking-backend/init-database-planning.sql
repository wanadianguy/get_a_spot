-- Table des cours
CREATE TABLE Cours (
    id_cours SERIAL PRIMARY KEY,
    nom_cours VARCHAR(100) NOT NULL
);

-- Table des enseignants
CREATE TABLE Enseignant (
    id_enseignant SERIAL PRIMARY KEY,
    nom_enseignant VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

-- Table de liaison entre les cours et les enseignants (relation plusieurs à plusieurs)
CREATE TABLE Cours_Enseignant (
    id_cours INT,
    id_enseignant INT,
    PRIMARY KEY (id_cours, id_enseignant),
    FOREIGN KEY (id_cours) REFERENCES Cours(id_cours),
    FOREIGN KEY (id_enseignant) REFERENCES Enseignant(id_enseignant)
);

-- Table des séances
CREATE TABLE Seance (
    id_seance SERIAL PRIMARY KEY,
    id_cours INT,
    date_debut TIMESTAMP NOT NULL,
    date_fin TIMESTAMP NOT NULL,
    FOREIGN KEY (id_cours) REFERENCES Cours(id_cours)
);

-- Table des salles
CREATE TABLE Salle (
    id_salle SERIAL PRIMARY KEY,
    batiment VARCHAR(100) NOT NULL,
    nom_salle VARCHAR(100) NOT NULL,
    capacite INT
);

-- Table des groupes
CREATE TABLE Groupe (
    id_groupe SERIAL PRIMARY KEY,
    nom_groupe VARCHAR(100) NOT NULL
);

-- Table des étudiants
CREATE TABLE Etudiant (
    id_etudiant SERIAL PRIMARY KEY,
    nom_etudiant VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

-- Table de liaison entre les étudiants et les groupes d'étudiants (relation plusieurs à plusieurs)
CREATE TABLE Etudiant_Groupe (
     id_etudiant INT,
     id_groupe INT,
     PRIMARY KEY (id_etudiant, id_groupe),
     FOREIGN KEY (id_etudiant) REFERENCES Etudiant(id_etudiant),
     FOREIGN KEY (id_groupe) REFERENCES Groupe(id_groupe)
);

-- Table de liaison entre les étudiants, les groupes d'étudiants et les cours (relation plusieurs à plusieurs)
CREATE TABLE Inscription (
     id_groupe INT,
     id_cours INT,
     PRIMARY KEY (id_groupe, id_cours),
     FOREIGN KEY (id_groupe) REFERENCES Groupe(id_groupe),
     FOREIGN KEY (id_cours) REFERENCES Cours(id_cours)
);



-- Insertion des enseignants
INSERT INTO Enseignant (nom_enseignant, email) VALUES
('Professeur1', 'professeur1@email.com'),
('Professeur2', 'professeur2@email.com');

-- Insertion des cours
INSERT INTO Cours (nom_cours) VALUES
('Cours1'),
('Cours2');

-- Insertion des cours enseignés par les enseignants
INSERT INTO Cours_Enseignant (id_cours, id_enseignant) VALUES
(1, 1),
(1, 2),
(2, 1);

-- Insertion des salles
INSERT INTO Salle (batiment, nom_salle, capacite) VALUES
('Batiment1', 'Salle1', 50),
('Batiment2', 'Salle2', 30);

-- Insertion des groupes d'étudiants
INSERT INTO Groupe (nom_groupe) VALUES
('Groupe1'),
('Groupe2');

-- Insertion des étudiants
INSERT INTO Etudiant (nom_etudiant, email) VALUES
('Etudiant1', 'etudiant1@email.com'),
('Etudiant2', 'etudiant2@email.com');

-- Insertion des étudiants dans les groupes
INSERT INTO Etudiant_Groupe (id_etudiant, id_groupe) VALUES
(1, 1),
(2, 1),
(2, 2);

-- Insertion des cours auxquels les groupes sont inscrits
INSERT INTO Inscription (id_groupe, id_cours) VALUES
(1, 1),
(2, 1),
(1, 2);

-- Insertion des séances
INSERT INTO Seance (id_cours, date_debut, date_fin) VALUES
(1, '2024-01-15 08:00:00', '2024-01-15 10:00:00'),
(2, '2024-01-16 09:00:00', '2024-01-16 11:00:00');
