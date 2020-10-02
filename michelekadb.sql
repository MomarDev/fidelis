-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  lun. 15 jan. 2018 à 21:51
-- Version du serveur :  5.7.19
-- Version de PHP :  5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES latin1 */;

--
-- Base de données :  `michelekadb`
--
CREATE DATABASE IF NOT EXISTS `fidelis` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `fidelis`;

-- --------------------------------------------------------

--
-- Structure de la table `achat`
--

DROP TABLE IF EXISTS `achat`;
CREATE TABLE IF NOT EXISTS `achat` (
  `id_achat` int(11) NOT NULL AUTO_INCREMENT,
  `date_achat` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id_client` int(11) NOT NULL,
  `montant_avant_remise` int(11) NOT NULL DEFAULT '0',
  `remise_achat` float NOT NULL DEFAULT '0',
  `montant_apres_remise` int(11) NOT NULL DEFAULT '0',
  `utilise_fidelite` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_achat`),
  KEY `achat_id_client` (`id_client`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `carte`
--

DROP TABLE IF EXISTS `carte`;
CREATE TABLE IF NOT EXISTS `carte` (
  `id_carte` int(11) NOT NULL AUTO_INCREMENT,
  `reference_carte` int(11) NOT NULL,
  `nombre_utilisateur_carte` int(11) DEFAULT NULL,
  `nb_pts_fidelite_achat` int(11) DEFAULT '0',
  `nombre_fidelite_parrainage` int(11) DEFAULT '0',
  `nombre_fidelite_prestation` int(11) DEFAULT '0',
  `etat_carte` tinyint(4) DEFAULT '0',
  `date_reception` date NOT NULL,
  `date_attribution` date DEFAULT NULL,
  `date_expiration` date DEFAULT NULL,
  PRIMARY KEY (`id_carte`),
  UNIQUE KEY `reference_carte` (`reference_carte`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `categorie_prestation`
--

DROP TABLE IF EXISTS `categorie_prestation`;
CREATE TABLE IF NOT EXISTS `categorie_prestation` (
  `id_categorie` int(11) NOT NULL AUTO_INCREMENT,
  `name_categorie` varchar(100) NOT NULL,
  `id_type_prestation` int(11) NOT NULL,
  `code_categorie` varchar(6) DEFAULT NULL,
  `est_une_sous_categorie` tinyint(1) NOT NULL DEFAULT '0',
  `est_une_prestation` tinyint(1) NOT NULL DEFAULT '0',
  `est_technique` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_categorie`),
  KEY `categorie_prestation_id_type_prestation` (`id_type_prestation`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `categorie_prestation`
--

INSERT INTO `categorie_prestation` (`id_categorie`, `name_categorie`, `id_type_prestation`, `code_categorie`, `est_une_sous_categorie`, `est_une_prestation`, `est_technique`) VALUES
(7, 'Coupe', 4, 'COUP', 0, 0, 0),
(13, 'Brush', 4, 'BRUSH', 0, 0, 0),
(16, 'Tresse/Chignons', 4, 'TRESCH', 0, 0, 0),
(17, 'Tissage', 4, 'TISG', 0, 0, 0),
(20, 'Coloration', 3, 'COLOR', 0, 0, 0),
(21, 'Déco nettoyage', 3, 'DECONE', 0, 0, 0),
(26, 'Lissage', 1, 'LISSGE', 0, 0, 0),
(27, 'Défrisage', 1, 'DERFRI', 0, 0, 0),
(28, 'Permanente', 1, 'PERMNT', 0, 0, 0),
(29, 'Kérastase', 2, 'KERAS', 0, 0, 0),
(30, 'Rlizz', 2, 'RLIZZ', 0, 0, 0),
(31, 'Dayna', 2, 'DAYNA', 0, 0, 0),
(32, 'Mizani', 2, 'MIZANI', 0, 0, 0),
(37, 'Maquillage', 5, 'MAQGE', 0, 0, 0),
(38, 'Manucure', 5, 'MANURE', 0, 0, 0),
(39, 'Pose vernis', 5, 'POSEVE', 0, 0, 0),
(40, 'Pédicure', 5, 'PEDICU', 0, 0, 0),
(41, 'Forfait Manucure + Pédicure', 5, 'FFMUPD', 0, 0, 0);

-- --------------------------------------------------------

--
-- Structure de la table `categorie_prestation_technique`
--

DROP TABLE IF EXISTS `categorie_prestation_technique`;
CREATE TABLE IF NOT EXISTS `categorie_prestation_technique` (
  `id_categorie_prestation_technique` int(11) NOT NULL AUTO_INCREMENT,
  `id_type_prestation` int(11) NOT NULL,
  `nom_categorie_prestation_technique` varchar(60) NOT NULL DEFAULT 'non défini',
  PRIMARY KEY (`id_categorie_prestation_technique`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `categorie_prestation_technique`
--

INSERT INTO `categorie_prestation_technique` (`id_categorie_prestation_technique`, `id_type_prestation`, `nom_categorie_prestation_technique`) VALUES
(1, 1, 'Permanente'),
(2, 1, 'Défrisage'),
(3, 1, 'Lissage'),
(4, 2, 'Soins rituel'),
(5, 3, 'Colorations'),
(6, 3, 'Mèches');

-- --------------------------------------------------------

--
-- Structure de la table `champ_utilisation`
--

DROP TABLE IF EXISTS `champ_utilisation`;
CREATE TABLE IF NOT EXISTS `champ_utilisation` (
  `id_champ_utilisation` int(11) NOT NULL AUTO_INCREMENT,
  `nom_champ_utilisation` varchar(100) NOT NULL,
  PRIMARY KEY (`id_champ_utilisation`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `champ_utilisation`
--

INSERT INTO `champ_utilisation` (`id_champ_utilisation`, `nom_champ_utilisation`) VALUES
(1, 'Permanente'),
(2, 'Défrisage'),
(3, 'Lissage'),
(4, 'Soins rituels'),
(5, 'Coloration'),
(6, 'Mèches');

-- --------------------------------------------------------

--
-- Structure de la table `champ_utilisation_marque`
--

DROP TABLE IF EXISTS `champ_utilisation_marque`;
CREATE TABLE IF NOT EXISTS `champ_utilisation_marque` (
  `id_champ_utilisation_marque` int(11) NOT NULL AUTO_INCREMENT,
  `id_marque` int(11) NOT NULL,
  `id_champ_utilisation` int(11) NOT NULL,
  PRIMARY KEY (`id_champ_utilisation_marque`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
  `id_client` int(11) NOT NULL AUTO_INCREMENT,
  `utilise_carte` tinyint(1) NOT NULL DEFAULT '0',
  `est_proprietaire_carte` tinyint(1) NOT NULL DEFAULT '0',
  `reference_carte` int(11) DEFAULT NULL,
  `name_client` varchar(60) DEFAULT NULL,
  `lastname_client` varchar(60) DEFAULT NULL,
  `anniversaire_client` varchar(20) DEFAULT NULL,
  `mobile1` varchar(60) DEFAULT NULL,
  `mobile2` varchar(60) DEFAULT NULL,
  `tel_bureau` varchar(60) DEFAULT NULL,
  `tel_domicile` varchar(60) DEFAULT NULL,
  `email_client` varchar(60) DEFAULT NULL,
  `adresse_client` varchar(90) DEFAULT NULL,
  `id_profession` int(11) DEFAULT NULL,
  `id_type_de_cheveux` int(11) DEFAULT NULL,
  `id_cuir_chevelu` int(11) DEFAULT NULL,
  `id_densite` int(11) DEFAULT NULL,
  `id_texture_cheveux` int(11) DEFAULT NULL,
  `id_etat_cheveux` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_client`),
  KEY `client_id_type_de_cheveux` (`id_type_de_cheveux`),
  KEY `client_id_cuir_chevelu` (`id_cuir_chevelu`),
  KEY `client_id_densite` (`id_densite`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `conseil`
--

DROP TABLE IF EXISTS `conseil`;
CREATE TABLE IF NOT EXISTS `conseil` (
  `id_conseil` int(11) NOT NULL AUTO_INCREMENT,
  `date_conseil` date DEFAULT NULL,
  `id_client` int(11) NOT NULL,
  `conseil` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_conseil`),
  KEY `conseil_id_client` (`id_client`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `cuir_chevelu`
--

DROP TABLE IF EXISTS `cuir_chevelu`;
CREATE TABLE IF NOT EXISTS `cuir_chevelu` (
  `id_cuir_chevelu` int(11) NOT NULL AUTO_INCREMENT,
  `name_cuir_chevelu` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id_cuir_chevelu`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `cuir_chevelu`
--

INSERT INTO `cuir_chevelu` (`id_cuir_chevelu`, `name_cuir_chevelu`) VALUES
(1, 'Sain'),
(2, 'Sensible'),
(3, 'Sec, Pelliculeux');

-- --------------------------------------------------------

--
-- Structure de la table `declinaison_marque`
--

DROP TABLE IF EXISTS `declinaison_marque`;
CREATE TABLE IF NOT EXISTS `declinaison_marque` (
  `id_declinaison_marque` int(11) NOT NULL AUTO_INCREMENT,
  `nom_declinaison_marque` varchar(100) NOT NULL,
  `id_marque` int(11) NOT NULL,
  PRIMARY KEY (`id_declinaison_marque`),
  KEY `declinaison_marque_id_marque` (`id_marque`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `declinaison_marque`
--

INSERT INTO `declinaison_marque` (`id_declinaison_marque`, `nom_declinaison_marque`, `id_marque`) VALUES
(1, 'Scalp', 1),
(2, 'Fin/Coloré', 1),
(3, 'Médium/Naturel', 1),
(4, 'Résistant', 2),
(5, 'Naturel', 2),
(6, 'Sensibilisé', 2),
(7, 'Richesse', 10),
(8, 'Light', 10),
(9, 'Platinium +', 15),
(10, 'Platinium Samnonique', 15),
(11, 'Multitechniques', 15),
(12, 'Blond me', 16);

-- --------------------------------------------------------

--
-- Structure de la table `densite`
--

DROP TABLE IF EXISTS `densite`;
CREATE TABLE IF NOT EXISTS `densite` (
  `id_densite` int(11) NOT NULL AUTO_INCREMENT,
  `densite` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_densite`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `densite`
--

INSERT INTO `densite` (`id_densite`, `densite`) VALUES
(1, 'Faible'),
(2, 'Moyenne'),
(3, 'Forte');

-- --------------------------------------------------------

--
-- Structure de la table `destinaire`
--

DROP TABLE IF EXISTS `destinaire`;
CREATE TABLE IF NOT EXISTS `destinaire` (
  `id_destinataire` int(11) NOT NULL,
  `id_mail_envoye` int(11) NOT NULL,
  `mail` varchar(255) NOT NULL,
  `destinataire` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `detail_inventaire`
--

DROP TABLE IF EXISTS `detail_inventaire`;
CREATE TABLE IF NOT EXISTS `detail_inventaire` (
  `id_detail_inventaire` int(11) NOT NULL AUTO_INCREMENT,
  `id_inventaire` int(11) NOT NULL,
  `id_produit` int(11) NOT NULL,
  `qt_reel_vente` int(11) NOT NULL,
  `qt_theorique_vente` int(11) NOT NULL,
  `qt_reel_service` int(11) NOT NULL,
  `qt_theorique_service` int(11) NOT NULL,
  `commentaire` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_detail_inventaire`),
  KEY `detail_inventaire_id_inventaire` (`id_inventaire`),
  KEY `detail_inventaire_id_produit` (`id_produit`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `detail_prestation_fourni`
--

DROP TABLE IF EXISTS `detail_prestation_fourni`;
CREATE TABLE IF NOT EXISTS `detail_prestation_fourni` (
  `id_detail_prestation_fourni` int(11) NOT NULL AUTO_INCREMENT,
  `id_prestation_fourni` int(11) NOT NULL,
  `id_prestation` int(11) NOT NULL,
  `technique` varchar(100) DEFAULT NULL,
  `montage` varchar(100) DEFAULT NULL,
  `temps_de_pause` int(11) DEFAULT '0',
  `observations` varchar(255) DEFAULT NULL,
  `cout_prestation` int(11) DEFAULT '0',
  PRIMARY KEY (`id_detail_prestation_fourni`),
  KEY `detail_prestation_fourni_id_prestation_fourni` (`id_prestation_fourni`),
  KEY `detail_prestation_fourni_id_prestation` (`id_prestation`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `etat_cheveux`
--

DROP TABLE IF EXISTS `etat_cheveux`;
CREATE TABLE IF NOT EXISTS `etat_cheveux` (
  `id_etat_cheveux` int(11) NOT NULL AUTO_INCREMENT,
  `name_etat_cheveux` varchar(100) NOT NULL,
  PRIMARY KEY (`id_etat_cheveux`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `etat_cheveux`
--

INSERT INTO `etat_cheveux` (`id_etat_cheveux`, `name_etat_cheveux`) VALUES
(1, 'Naturels'),
(2, 'Sensibilisés par des outils chauffants'),
(3, 'Traités chimiquement (défrisage, couleur, mèches)'),
(4, 'En transition');

-- --------------------------------------------------------

--
-- Structure de la table `gamme_produit`
--

DROP TABLE IF EXISTS `gamme_produit`;
CREATE TABLE IF NOT EXISTS `gamme_produit` (
  `id_gamme_produit` int(11) NOT NULL AUTO_INCREMENT,
  `nom_gamme_produit` varchar(100) NOT NULL,
  PRIMARY KEY (`id_gamme_produit`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `gamme_produit`
--

INSERT INTO `gamme_produit` (`id_gamme_produit`, `nom_gamme_produit`) VALUES
(1, 'nutritive');

-- --------------------------------------------------------

--
-- Structure de la table `inventaire`
--

DROP TABLE IF EXISTS `inventaire`;
CREATE TABLE IF NOT EXISTS `inventaire` (
  `id_inventaire` int(11) NOT NULL AUTO_INCREMENT,
  `date_inventaire` date NOT NULL,
  `commentaire` varchar(255) NOT NULL,
  PRIMARY KEY (`id_inventaire`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Déclencheurs `inventaire`
--
DROP TRIGGER IF EXISTS `delete_detail_inventaire`;
DELIMITER $$
CREATE TRIGGER `delete_detail_inventaire` BEFORE DELETE ON `inventaire` FOR EACH ROW DELETE FROM detail_inventaire WHERE detail_inventaire.id_inventaire = OLD.id_inventaire
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `mail_envoye`
--

DROP TABLE IF EXISTS `mail_envoye`;
CREATE TABLE IF NOT EXISTS `mail_envoye` (
  `id_mail` int(11) NOT NULL AUTO_INCREMENT,
  `date_email` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `expediteur` varchar(255) NOT NULL,
  `destinataire` varchar(255) NOT NULL,
  `objet` varchar(255) NOT NULL,
  `texte` text NOT NULL,
  `fichier_attache` varchar(255) DEFAULT NULL,
  `etat` tinyint(1) NOT NULL,
  PRIMARY KEY (`id_mail`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `marque`
--

DROP TABLE IF EXISTS `marque`;
CREATE TABLE IF NOT EXISTS `marque` (
  `id_marque` int(11) NOT NULL AUTO_INCREMENT,
  `nom_marque` varchar(100) NOT NULL,
  `id_champ_utilisation_marque` int(11) NOT NULL,
  PRIMARY KEY (`id_marque`),
  KEY `fk_champ_utilisation` (`id_champ_utilisation_marque`)
) ENGINE=MyISAM AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `marque`
--

INSERT INTO `marque` (`id_marque`, `nom_marque`, `id_champ_utilisation_marque`) VALUES
(1, 'Mizani', 2),
(2, 'XTenso', 3),
(3, 'Myriam K', 3),
(4, 'Mizani', 4),
(5, 'Myriam K', 4),
(6, 'Viérastose', 4),
(7, 'Autres', 4),
(8, 'Majérel', 5),
(9, 'Inoa', 5),
(10, 'Ria', 5),
(11, 'Cool-Cover', 5),
(12, 'Chroma-active', 5),
(13, 'Schwouzkop', 5),
(14, 'Autres', 5),
(15, 'ORéal', 6),
(16, 'Schwarzkorf', 6);

-- --------------------------------------------------------

--
-- Structure de la table `mouvement_stock`
--

DROP TABLE IF EXISTS `mouvement_stock`;
CREATE TABLE IF NOT EXISTS `mouvement_stock` (
  `id_mouvement` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_mouvement` datetime DEFAULT NULL,
  `id_produit` int(11) NOT NULL,
  `sens` varchar(20) DEFAULT NULL,
  `quantite` bigint(11) DEFAULT NULL,
  `motif` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`id_mouvement`),
  KEY `mouvement_stock_id_produit` (`id_produit`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `parametre`
--

DROP TABLE IF EXISTS `parametre`;
CREATE TABLE IF NOT EXISTS `parametre` (
  `cle` varchar(100) NOT NULL,
  `valeur_parametre` varchar(100) NOT NULL,
  `affiche` tinyint(1) NOT NULL,
  `explications` varchar(255) NOT NULL,
  `suplementaire` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cle`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `parametre`
--

INSERT INTO `parametre` (`cle`, `valeur_parametre`, `affiche`, `explications`, `suplementaire`) VALUES
('MAX_FENETRE', '5', 1, 'Max Fenêtres permet de définir le nombre maximale de fenêtre que l\'on peut ouvrir en même temps. La Valeur max est de 10 et la valeur minimal 3; il aussi la valeur par défaut. les valeurs inférieurs à 3 ne sont pas autorisées.', NULL),
('FIDELITE_VISITE', '10', 1, 'Permet de définir le nombre de visite à laquelle le client ayant la carte fidélité a droit à un bonus ou remise.', NULL),
('FIDELITE_ACHAT', '10', 1, 'Permet de définir le nombre de d\'achat à effectuer pour un client pour avoir droit à un bonus ou remise.', NULL),
('EMAIL', 'sallonmicheleka@gmail.com', 1, 'L\'email permettant d\'envoyer des mail aux clients.', 'blackstartech'),
('FIDELITE_PARRAINAGE', '10', 1, 'La quantité de de parrainage qu\'il faut faire pour aspirer à un cadeau du Salon Michele Ka.', NULL),
('BONUS_ACHAT', '5', 1, 'Le bonus accordé au client suite aux nombres d\\\'achats prédéfinie', NULL),
('BONUS_VISITE', '5', 1, 'Le bonus accordé au client suite à un nombre de visites prédéfinis.', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `parrainage`
--

DROP TABLE IF EXISTS `parrainage`;
CREATE TABLE IF NOT EXISTS `parrainage` (
  `id_parrainage` int(11) NOT NULL AUTO_INCREMENT,
  `reference_carte` int(11) NOT NULL,
  `date` date NOT NULL,
  `telephone` varchar(60) DEFAULT NULL,
  `nom_parraine` varchar(80) NOT NULL,
  `prenom_parraine` varchar(80) NOT NULL,
  PRIMARY KEY (`id_parrainage`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `prestation`
--

DROP TABLE IF EXISTS `prestation`;
CREATE TABLE IF NOT EXISTS `prestation` (
  `id_prestation` int(11) NOT NULL AUTO_INCREMENT,
  `nom_prestation` varchar(100) DEFAULT NULL,
  `cout_prestation` int(11) NOT NULL,
  `id_sous_categorie` int(11) NOT NULL,
  `cout_fixe` tinyint(1) NOT NULL DEFAULT '1',
  `borne_inferieur_prix` int(11) NOT NULL DEFAULT '0',
  `borne_superieur_prix` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_prestation`),
  KEY `prestation_id_sous_categorie` (`id_sous_categorie`)
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `prestation`
--

INSERT INTO `prestation` (`id_prestation`, `nom_prestation`, `cout_prestation`, `id_sous_categorie`, `cout_fixe`, `borne_inferieur_prix`, `borne_superieur_prix`) VALUES
(3, 'Sculptée', 5000, 1, 1, 0, 0),
(4, 'Tondue', 3000, 1, 1, 0, 0),
(5, 'Bébé -5 ans', 7000, 2, 1, 0, 0),
(6, 'Garcon -10 ans', 10000, 2, 1, 0, 0),
(7, 'Garçon -20 ans', 13000, 2, 1, 0, 0),
(8, 'Tondeuse -10 ans', 5000, 2, 1, 0, 0),
(9, 'Tondeuse', 8000, 2, 1, 0, 0),
(10, 'Coupe couronne', 13000, 2, 1, 0, 0),
(11, 'Coupe homme', 16000, 2, 1, 0, 0),
(12, 'Formule bébé -5 ans', 7000, 3, 1, 0, 0),
(13, 'Formule -5 ans', 13000, 3, 1, 0, 0),
(14, 'sh + coupe + brushing très long', 29000, 4, 1, 0, 0),
(15, 'sh + coupe + brushing long', 28000, 4, 1, 0, 0),
(16, 'sh + coupe + brushing mi-long', 27000, 4, 1, 0, 0),
(17, 'sh + coupe + brushing court', 26000, 4, 1, 0, 0),
(18, 'sh + coupe + séchage', 22000, 4, 1, 0, 0),
(19, 'SH + Brushing très long bouclés', 15000, 5, 1, 0, 0),
(20, 'SH + Brushing très long', 14000, 5, 1, 0, 0),
(21, 'SH + Brushing long', 13000, 5, 1, 0, 0),
(22, 'SH + Brushing mi-long', 12000, 5, 1, 0, 0),
(23, 'SH + Brushing court', 11000, 5, 1, 0, 0),
(24, 'HOMME SH + Séchage', 5000, 5, 1, 0, 0),
(25, 'SH + Séchage', 7500, 5, 1, 0, 0),
(26, 'Deux tresses', 15000, 7, 1, 0, 0),
(27, 'Trois tresses', 20000, 7, 1, 0, 0),
(28, 'Cinq tresses', 25000, 7, 1, 0, 0),
(29, 'SH + Soin + Coiffage sans séchage (exp: vanilles)', 20000, 8, 1, 0, 0),
(30, 'sh + Soin +coiffage (exp: vanilles) + séchage', 0, 8, 0, 22500, 25000),
(31, 'SH + Tresses bandeau', 15000, 8, 1, 0, 0),
(32, 'SH + Séchage + Punk + Mèches', 8, 0, 0, 0, 0),
(33, '5 minutes', 5000, 9, 1, 0, 0),
(34, '10 minutes', 10000, 9, 1, 0, 0),
(35, '15 minutes', 15000, 9, 1, 0, 0),
(36, '20-30 minutes', 20000, 9, 1, 0, 0),
(37, '45 minutes', 25000, 9, 1, 0, 0),
(38, '1 heures', 30000, 9, 1, 0, 0),
(39, 'Formule mariée + Essai', 45000, 9, 1, 0, 0),
(40, 'Ouvert', 25000, 12, 1, 0, 0),
(41, 'Fermé', 30000, 12, 1, 0, 0),
(42, '1/4 tête', 30000, 13, 1, 0, 0),
(43, '1/2 tête', 40000, 13, 1, 0, 0),
(44, 'Tête entière', 50000, 13, 1, 0, 0),
(45, 'Tissage court', 13000, 14, 1, 0, 0),
(46, 'Tissage moyen', 18000, 14, 1, 0, 0),
(47, 'Tissage long', 20000, 14, 1, 0, 0),
(48, 'Racines', 17000, 15, 1, 0, 0),
(49, 'Dose supplémentaire', 4000, 15, 1, 0, 0),
(50, 'Racines', 17000, 16, 1, 0, 0),
(51, 'Dose supplémentaire', 4000, 16, 1, 0, 0),
(52, 'Sur cheveux pour neutraliser', 17000, 17, 1, 0, 0),
(53, 'Dose suplémentaire', 17, 4000, 1, 0, 0),
(54, 'Racines', 20000, 18, 1, 0, 0),
(55, 'Dose suplémentaire', 4000, 18, 1, 0, 0),
(56, 'Sur longueur', 10000, 19, 1, 0, 0),
(57, 'Dose suplémentaire', 4000, 19, 1, 0, 0),
(58, 'Sur Longueur', 20000, 20, 1, 0, 0),
(59, 'Dose suplémentaire', 4000, 20, 1, 0, 0),
(60, 'Hairchalk (craie)', 0, 21, 0, 5000, 10000),
(61, 'Homme Cover5', 10000, 22, 1, 0, 0),
(62, 'Racines', 17000, 23, 1, 0, 0),
(63, 'Dose suplémentaire', 4000, 23, 1, 0, 0),
(64, 'Mèches / Balayage', 0, 24, 0, 22000, 60000),
(65, 'Racines', 17000, 25, 1, 0, 0),
(66, 'Tête entière', 25000, 25, 1, 0, 0),
(67, 'Un sachet', 7500, 26, 1, 0, 0),
(68, 'Dose supplémentaire', 5000, 26, 1, 0, 0),
(69, 'Sans lisseur', 0, 27, 0, 28000, 68000),
(70, 'Avec lisseur', 0, 27, 0, 56000, 136000),
(71, 'Dose suplémentaire', 5000, 27, 1, 0, 0),
(72, 'Cheveux court sans lisseur', 20000, 28, 1, 0, 0),
(73, 'Cheveux court avec lisseur', 40000, 28, 1, 0, 0),
(74, 'Cheveux mi-long sans lisseur', 35000, 28, 1, 0, 0),
(75, 'Cheveux mi-long avec lisseur', 70000, 28, 1, 0, 0),
(76, 'Cheveux long sans lisseur', 50000, 28, 1, 0, 0),
(77, 'Cheveux long avec lisseur', 100000, 28, 1, 0, 0),
(78, 'Cheveux courts', 130000, 29, 1, 0, 0),
(79, 'Cheuveux mi-longs', 165000, 30, 1, 0, 0),
(80, 'Cheuveux longs', 190000, 31, 1, 0, 0),
(81, 'Cheuveux très long', 225000, 32, 1, 0, 0),
(82, 'Butter blend', 25000, 33, 1, 0, 0),
(83, 'Dose supplémentaire', 5000, 33, 1, 0, 0),
(84, 'Sensitive Scalp', 25000, 33, 1, 0, 0),
(85, 'Soin strenght fusion', 0, 33, 0, 0, 0),
(86, 'Permanente dulcia l\'Oréal', 0, 35, 0, 22000, 60000),
(87, 'Option Présifon', 4000, 36, 1, 0, 0),
(88, 'Bain (shampooing)', 4000, 37, 1, 0, 0),
(89, 'Masque spécifique', 0, 38, 0, 4000, 6000),
(90, 'Simple', 10000, 39, 1, 0, 0),
(91, 'Supp Booster', 2500, 38, 1, 0, 0),
(92, 'Masque & Fusiodose', 15000, 41, 1, 0, 0),
(93, 'Rituel Kérastase', 0, 42, 0, 10000, 15000),
(94, 'Kera shot', 0, 43, 0, 10000, 15000),
(95, 'Keratine color', 0, 44, 0, 10000, 15000),
(105, 'Massage huile ayurvédique dayna', 10000, 45, 1, 0, 0),
(106, 'bain (shampooing)', 4000, 46, 1, 0, 0),
(107, 'True texture', 0, 47, 0, 10000, 12000),
(108, 'NAPPY  thermasmooth + Steampood', 0, 47, 0, 20000, 25000),
(109, 'Scalp care', 10000, 47, 1, 0, 0),
(110, 'Nutrition', 15000, 47, 1, 0, 0),
(111, 'Hydratation', 15000, 47, 1, 0, 0),
(112, 'Strenght fusion', 20000, 47, 1, 0, 0),
(113, 'blackstar', 232322, 27, 1, 0, 0),
(114, 'blackstar', 26767, 43, 1, 0, 0),
(115, 'tst', 1000, 27, 1, 0, 0),
(116, 'prestation 1', 2500, 27, 1, 0, 0);

-- --------------------------------------------------------

--
-- Structure de la table `prestation_fournie`
--

DROP TABLE IF EXISTS `prestation_fournie`;
CREATE TABLE IF NOT EXISTS `prestation_fournie` (
  `id_prestation_fourni` int(20) NOT NULL AUTO_INCREMENT,
  `date_prestation_fourni` datetime DEFAULT CURRENT_TIMESTAMP,
  `id_client` int(11) NOT NULL DEFAULT '0',
  `remise_prestation` int(11) DEFAULT '0',
  `montant_paye` int(11) DEFAULT '0',
  `utilise_fidelite` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id_prestation_fourni`,`id_client`),
  KEY `prestation_fournie_id_client` (`id_client`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `prestation_technique`
--

DROP TABLE IF EXISTS `prestation_technique`;
CREATE TABLE IF NOT EXISTS `prestation_technique` (
  `id_prestation_technique` int(11) NOT NULL AUTO_INCREMENT,
  `id_categorie_prestation_technique` int(11) NOT NULL,
  `id_client` int(11) NOT NULL,
  `date` varchar(11) NOT NULL,
  `montage` varchar(255) DEFAULT 'non spécifié',
  `technique` varchar(255) DEFAULT 'non spécifié',
  `temps_pause` int(11) DEFAULT '0',
  `observations` varchar(255) DEFAULT 'sans commentaires',
  PRIMARY KEY (`id_prestation_technique`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

DROP TABLE IF EXISTS `produit`;
CREATE TABLE IF NOT EXISTS `produit` (
  `id_produit` int(11) NOT NULL AUTO_INCREMENT,
  `name_produit` varchar(100) DEFAULT NULL,
  `code_bar_produit` int(100) NOT NULL,
  `id_marque` int(11) NOT NULL,
  `utilisation_produit` tinyint(1) NOT NULL,
  `id_sous_famille` int(11) NOT NULL,
  `id_gamme_produit` int(11) NOT NULL,
  `prix_unitaire` int(11) DEFAULT NULL,
  `qt_stock_service` int(11) DEFAULT NULL,
  `qt_stock_vente` int(11) DEFAULT NULL,
  `qt_seuil_alert_service` int(3) NOT NULL,
  `qt_seuil_alert_vente` int(3) NOT NULL,
  `info_complementaire` varchar(255) NOT NULL,
  PRIMARY KEY (`id_produit`),
  UNIQUE KEY `code_bar_produit` (`code_bar_produit`),
  KEY `produit_id_marque` (`id_marque`),
  KEY `produit_id_sous_famille` (`id_sous_famille`),
  KEY `produit_id_gamme_produit` (`id_gamme_produit`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `produit_achete`
--

DROP TABLE IF EXISTS `produit_achete`;
CREATE TABLE IF NOT EXISTS `produit_achete` (
  `id_produit_achete` int(11) NOT NULL AUTO_INCREMENT,
  `id_achat` int(11) NOT NULL,
  `id_produit` int(11) NOT NULL,
  `quantite` int(11) NOT NULL,
  PRIMARY KEY (`id_produit_achete`),
  KEY `produit_achete_id_achat` (`id_achat`),
  KEY `produit_id_produit` (`id_produit`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `produit_utilise`
--

DROP TABLE IF EXISTS `produit_utilise`;
CREATE TABLE IF NOT EXISTS `produit_utilise` (
  `id_produit_utilise` int(11) NOT NULL AUTO_INCREMENT,
  `id_prestation_technique` int(11) NOT NULL,
  `id_produit` int(11) NOT NULL,
  PRIMARY KEY (`id_produit_utilise`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `profession`
--

DROP TABLE IF EXISTS `profession`;
CREATE TABLE IF NOT EXISTS `profession` (
  `id_profession` int(11) NOT NULL AUTO_INCREMENT,
  `name_profession` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_profession`)
) ENGINE=InnoDB AUTO_INCREMENT=205 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `profession`
--

INSERT INTO `profession` (`id_profession`, `name_profession`) VALUES
(1, 'Abatteur(euse) manuel(le)'),
(2, 'Accessoiriste'),
(3, 'Accompagnateur(trice)'),
(4, 'Actuaire'),
(5, 'Adjoint(e) à la direction / à la coordination'),
(6, 'Adjoint(e) administratif'),
(7, 'Administrateur(trice) de réseaux informatiques'),
(8, 'Agent de recherche de financement'),
(9, 'Agent(e) d\'affectation'),
(10, 'Agent(e) d\'information'),
(11, 'Agent(e) d\'intégration'),
(12, 'Agent(e) de bureau'),
(13, 'Agent(e) de communication / Responsable des communications'),
(14, 'Agent(e) de conformité'),
(15, 'Agent(e) de développement'),
(16, 'Agent(e) de francisation'),
(17, 'Agent(e) de jumelage'),
(18, 'Agent(e) de liaison'),
(19, 'Agent(e) de programmation'),
(20, 'Agent(e) de promotion'),
(21, 'Agent(e) de recherche'),
(22, 'Agent(e) de relations publiques'),
(23, 'Agent(e) de sensibilisation'),
(24, 'Agent(e) de voyage'),
(25, 'Agent(e)-conseil en soutien pédagogique et technique'),
(26, 'Aide-cuisinier(ère)'),
(27, 'Analyste financier'),
(28, 'Animateur(trice)'),
(29, 'Animateur(trice) en radiotélédiffusion'),
(30, 'Architecte'),
(31, 'Architecte paysagiste'),
(32, 'Artisan(e) récupérateur(trice)'),
(33, 'Assistante périnatale'),
(34, 'Auteur(e)'),
(35, 'Avocat(e)'),
(36, 'Biologiste'),
(37, 'Brasseur'),
(38, 'Caissier(ère)'),
(39, 'Caméraman'),
(40, 'Camionneur(euse)'),
(41, 'Chargé(e) de programmes'),
(42, 'Chargé(e) de projets'),
(43, 'Chauffeur(euse)'),
(44, 'Chef cuisinier(ère)'),
(45, 'Chef de camp'),
(46, 'Chef de service'),
(47, 'Chef machiniste'),
(48, 'Comédien(ne)'),
(49, 'Commis comptable'),
(50, 'Commis de magasin'),
(51, 'Commis expédition/réception'),
(52, 'Comptable'),
(53, 'Concepteur(trice) costumes'),
(54, 'Concepteur(trice) de décor'),
(55, 'Concepteur(trice) musical'),
(56, 'Concepteur(trice) publicitaire'),
(57, 'Concierge'),
(58, 'Conseiller(ère) aux entreprises, coopératives et/ou aux OBNL'),
(59, 'Conseiller(ère) aux familles - services funéraires'),
(60, 'Conseiller(ère) budgétaire'),
(61, 'Conseiller(ère) d\'orientation'),
(62, 'Conseiller(ère) en défense des droits'),
(63, 'Conseiller(ère) en efficacité énergétique'),
(64, 'Conseiller(ère) en évaluation'),
(65, 'Conseiller(ère) en formation'),
(66, 'Conseiller(ère) en insertion'),
(67, 'Conseiller(ère) en main-d\'oeuvre'),
(68, 'Conseiller(ère) en réadaptation'),
(69, 'Conseiller(ère) en ressources humaines'),
(70, 'Conseiller(ère) en sports et loisirs'),
(71, 'Conseiller(ère) marketing'),
(72, 'Contremaître'),
(73, 'Coordonnateur(rice) à la vie politique'),
(74, 'Coordonnateur(trice) à la formation'),
(75, 'Coordonnateur(trice) à la mobilisation'),
(76, 'Coordonnateur(trice) à la vie associative'),
(77, 'Coordonnateur(trice) de funérailles'),
(78, 'Coordonnateur(trice) de services sociaux et communautaires'),
(79, 'Coordonnateur(trice) des bénévoles'),
(80, 'Coordonnateur(trice) des services à la petite enfance'),
(81, 'Cuisinier(ère)'),
(82, 'Danseur(se)'),
(83, 'Directeur(trice) / Coordonnateur(trice) à l\'animation'),
(84, 'Directeur(trice) / Coordonnateur(trice) administratif(ve)'),
(85, 'Directeur(trice) / Coordonnateur(trice) artistique'),
(86, 'Directeur(trice) / Coordonnateur(trice) au développement'),
(87, 'Directeur(trice) / Coordonnateur(trice) aux événements'),
(88, 'Directeur(trice) / Coordonnateur(trice) de l\'intervention'),
(89, 'Directeur(trice) / Coordonnateur(trice) de la programmation'),
(90, 'Directeur(trice) / Coordonnateur(trice) de programmes'),
(91, 'Directeur(trice) / Coordonnateur(trice) de projet et/ou de dossiers'),
(92, 'Directeur(trice) / Coordonnateur(trice) de services'),
(93, 'Directeur(trice) / Coordonnateur(trice) des communications'),
(94, 'Directeur(trice) / Coordonnateur(trice) des loisirs'),
(95, 'Directeur(trice) / Coordonnateur(trice) des relations publiques'),
(96, 'Directeur(trice) / Coordonnateur(trice) des ressources humaines'),
(97, 'Directeur(trice) / Coordonnateur(trice) du financement'),
(98, 'Directeur(trice) / Coordonnateur(trice) du service à la clientèle'),
(99, 'Directeur(trice) / Coordonnateur(trice) informatique'),
(100, 'Directeur(trice) / Coordonnateur(trice) marketing'),
(101, 'Directeur(trice) / Coordonnateur(trice) technique'),
(102, 'Directeur(trice) de département'),
(103, 'Directeur(trice) de l\'entretien ménager'),
(104, 'Directeur(trice) de l\'information'),
(105, 'Directeur(trice) de production'),
(106, 'Directeur(trice) des finances'),
(107, 'Directeur(trice) des opérations'),
(108, 'Directeur(trice) général / Coordonnateur (trice)'),
(109, 'Directeur(trice)/ Coordonnateur(trice) clinique'),
(110, 'Direction adjointe /Coordonnateur(trice) adjoint(e)'),
(111, 'Eco-conseiller(ère)'),
(112, 'Educateur(trice) en CPE'),
(113, 'Educateur(trice) en halte-garderie communautaire'),
(114, 'Educateur(trice) spécialisé(e)'),
(115, 'Formateur(trice)'),
(116, 'Géographe'),
(117, 'Gérant(e)'),
(118, 'Gestionnaire immobilier'),
(119, 'Graphiste'),
(120, 'Guide-animateur'),
(121, 'Illustrateur(trice)'),
(122, 'Infirmier(ères)'),
(123, 'Infographiste'),
(124, 'Ingénieur(e) forestier(ère)'),
(125, 'Intervenant(e)'),
(126, 'Intervenant(e) en loisir'),
(127, 'Intervenant(e) périnatale'),
(128, 'Jardinier(ère)'),
(129, 'Journaliste'),
(130, 'Libraire'),
(131, 'Magasinier(ère)'),
(132, 'Manoeuvre'),
(133, 'Manutentionnaire'),
(134, 'Marionnettiste'),
(135, 'Marteleur(euse)'),
(136, 'Médecin'),
(137, 'Meneur de jeu'),
(138, 'Mesureur(euse)'),
(139, 'Metteur en onde'),
(140, 'Metteur(e) en scène'),
(141, 'Moniteur(trice) de camps'),
(142, 'Musicien(ne)'),
(143, 'Nutritionniste'),
(144, 'Opérateur(trice)'),
(145, 'Opérateur(trice) de débardeur'),
(146, 'Opérateur(trice) de machinerie d\'abattage mécanisé'),
(147, 'Opérateur(trice) de machinerie lourde en voirie forestière'),
(148, 'Organisateur(trice) communautaire'),
(149, 'Orthopédagogue'),
(150, 'Ouvrier(ère) sylvicole'),
(151, 'Paramédical'),
(152, 'Pâtissiers'),
(153, 'Porteur - services funéraires'),
(154, 'Préposé(e) à l\'aménagement du territoire'),
(155, 'Préposé(e) au service à la clientèle'),
(156, 'Préposé(e) d\'aide à domicile'),
(157, 'Pressier(ère) en imprimerie sérigraphique'),
(158, 'Producteur(trice) multimédia'),
(159, 'Professeur de musique'),
(160, 'Programmeur(e) analyste'),
(161, 'Projectionniste'),
(162, 'Psycho-éducateur(trice)'),
(163, 'Psychologue'),
(164, 'Psychothérapeute'),
(165, 'Réalisateur(trice)'),
(166, 'Rédacteur(trice)'),
(167, 'Rédacteur(trice) en chef'),
(168, 'Régisseur'),
(169, 'Répartiteur(trice)'),
(170, 'Représentant(e) des ventes'),
(171, 'Représentant(e) publicitaire'),
(172, 'Responsable de l\'animation'),
(173, 'Responsable de service de garde'),
(174, 'Sauveteur(e)'),
(175, 'Secrétaire'),
(176, 'Secrétaire-réceptionniste'),
(177, 'Serveur(euse)'),
(178, 'Superviseur(e)'),
(179, 'Surveillant(e)'),
(180, 'Technicien(ne)'),
(181, 'Technicien(ne) caméraman - monteur'),
(182, 'Technicien(ne) comptable'),
(183, 'Technicien(ne) d\'atelier'),
(184, 'Technicien(ne) de scène'),
(185, 'Technicien(ne) de son'),
(186, 'Technicien(ne) éclairagiste'),
(187, 'Technicien(ne) en câblodistribution'),
(188, 'Technicien(ne) en diététique'),
(189, 'Technicien(ne) en documentation'),
(190, 'Technicien(ne) en informatique'),
(191, 'Technicien(ne) en radiodiffusion'),
(192, 'Technicien(ne) en travail social'),
(193, 'Technicien(ne) forestier(ère)'),
(194, 'Thanatopracteur'),
(195, 'Traducteur(trice)'),
(196, 'Travailleur(se) de milieu'),
(197, 'Travailleur(se) de rue'),
(198, 'Travailleur(se) social(e)'),
(199, 'Trieur(se)'),
(200, 'Valoriste'),
(201, 'Vendeur(euse)'),
(202, 'Webmestre'),
(203, 'Coiffeur(se)'),
(204, 'Pilote');

-- --------------------------------------------------------

--
-- Structure de la table `profil`
--

DROP TABLE IF EXISTS `profil`;
CREATE TABLE IF NOT EXISTS `profil` (
  `id_profil` int(11) NOT NULL AUTO_INCREMENT,
  `name_profil` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id_profil`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `profil`
--

INSERT INTO `profil` (`id_profil`, `name_profil`) VALUES
(1, 'Administrateur'),
(2, 'Operateur');

-- --------------------------------------------------------

--
-- Structure de la table `sous_categorie_prestation`
--

DROP TABLE IF EXISTS `sous_categorie_prestation`;
CREATE TABLE IF NOT EXISTS `sous_categorie_prestation` (
  `id_sous_categorie` int(11) NOT NULL AUTO_INCREMENT,
  `nom_sous_categorie` varchar(100) NOT NULL,
  `id_categorie` int(11) NOT NULL,
  `code_categorie` varchar(6) NOT NULL,
  `est_une_prestation` tinyint(1) NOT NULL DEFAULT '0',
  `est_technique` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_sous_categorie`),
  KEY `sous_categorie_prestation_id_categorie` (`id_categorie`)
) ENGINE=MyISAM AUTO_INCREMENT=53 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `sous_categorie_prestation`
--

INSERT INTO `sous_categorie_prestation` (`id_sous_categorie`, `nom_sous_categorie`, `id_categorie`, `code_categorie`, `est_une_prestation`, `est_technique`) VALUES
(1, 'Barbe', 7, 'COUP', 0, 0),
(2, 'Coupes Homme', 7, 'COUP', 0, 0),
(3, 'Formules coupe enfants', 7, 'COUP', 0, 0),
(4, 'Formules coupe', 7, 'COUP', 0, 0),
(5, 'Formules brushing/MSP', 13, 'BRUSH', 0, 0),
(6, 'PUNK (GROSSE TRESSE)', 16, 'TRESCH', 0, 0),
(7, 'Tresses Chouchées', 16, 'TRESCH', 0, 0),
(8, 'Formules cheveux naturel afro', 16, 'TRESCH', 0, 0),
(9, 'Bar à chignon', 16, 'TRESCH', 0, 0),
(15, 'Majirel', 20, 'COLOR', 0, 0),
(11, 'Dépose avec shampoing', 17, 'TISG', 0, 0),
(12, 'Pose tissage', 17, 'TISG', 0, 0),
(13, 'Pose tissage technique invisible', 17, 'TISG', 0, 0),
(14, 'Formule sh/Soin/Brush sur', 17, 'TISG', 0, 0),
(16, 'Diarichesse', 20, 'COLOR', 0, 0),
(17, 'Dialight', 20, 'COLOR', 0, 0),
(18, 'Inoa', 20, 'COLOR', 0, 0),
(19, 'Chromatine', 20, 'COLOR', 0, 0),
(20, 'Hénné', 20, 'COLOR', 0, 0),
(21, 'Hairchalk (craie)', 20, 'COLOR', 0, 0),
(22, 'HOMME Cover5)', 20, 'COLOR', 0, 0),
(23, 'Bain de charme', 21, 'DECONE', 0, 0),
(24, 'Mèches / Balayage', 21, 'DECONE', 0, 0),
(25, 'Décoloration', 21, 'DECONE', 0, 0),
(26, 'Effassor', 21, 'DECONE', 0, 0),
(27, 'Xtenso l\'Oréal', 26, 'LISSGE', 0, 0),
(28, 'Smooth Express Rlizz', 26, 'LISSGE', 0, 0),
(29, 'Cheveux courts', 26, 'LISSGE', 0, 0),
(30, 'Cheveux mi-longs', 26, 'LISSGE', 0, 0),
(31, 'Cheveux longs', 26, 'LISSGE', 0, 0),
(32, 'Cheveux très longs', 26, 'LISSGE', 0, 0),
(33, 'Protocole Mizani', 27, 'DERFRI', 0, 0),
(34, '', 27, 'DERFRI', 0, 0),
(35, 'Permanente Dulcia l\'Oréal', 28, 'PERMNT', 0, 0),
(36, 'Option Présifon', 28, 'PERMNT', 0, 0),
(37, 'Bain (shampooing)', 29, 'KERAS', 0, 0),
(38, 'Masque spécifique', 29, 'KERAS', 0, 0),
(39, 'Fusio dose', 29, 'KERAS', 0, 0),
(40, 'Masque spécifique', 29, 'KERAS', 0, 0),
(41, 'Masque & Fusiodose', 29, 'KERAS', 0, 0),
(42, 'Rituel Kérastase', 29, 'KERAS', 0, 0),
(43, 'Kera shot', 30, 'RLIZZ', 0, 0),
(44, 'Keratine color', 30, 'RLIZZ', 0, 0),
(45, 'Massage huile ayurvédique Dayna', 31, 'DAYNA', 0, 0),
(46, 'Bain (shampooing)', 32, 'MIZANI', 0, 0),
(47, 'Rituel Mizani', 32, 'MIZINI', 0, 0),
(48, 'Maquillage', 37, 'MAQGE', 0, 0),
(49, 'Manucure', 38, 'MANU', 0, 0),
(50, 'Pose vernis', 39, 'POSE', 0, 0),
(51, 'Pédicure', 40, 'PEDI', 0, 0),
(52, 'Forfait Manucure + Pédicure', 41, 'FFMANU', 0, 0);

-- --------------------------------------------------------

--
-- Structure de la table `sous_famille_produit`
--

DROP TABLE IF EXISTS `sous_famille_produit`;
CREATE TABLE IF NOT EXISTS `sous_famille_produit` (
  `id_sous_famille` int(11) NOT NULL AUTO_INCREMENT,
  `nom_sous_famille` varchar(60) NOT NULL,
  PRIMARY KEY (`id_sous_famille`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `sous_famille_produit`
--

INSERT INTO `sous_famille_produit` (`id_sous_famille`, `nom_sous_famille`) VALUES
(1, 'kerastase');

-- --------------------------------------------------------

--
-- Structure de la table `texture_cheveux`
--

DROP TABLE IF EXISTS `texture_cheveux`;
CREATE TABLE IF NOT EXISTS `texture_cheveux` (
  `id_texture_cheveux` int(11) NOT NULL AUTO_INCREMENT,
  `texture_cheveux` varchar(60) NOT NULL,
  PRIMARY KEY (`id_texture_cheveux`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `texture_cheveux`
--

INSERT INTO `texture_cheveux` (`id_texture_cheveux`, `texture_cheveux`) VALUES
(1, 'Bouclés'),
(2, 'crépus'),
(3, 'Frizés'),
(4, 'Raides');

-- --------------------------------------------------------

--
-- Structure de la table `trace`
--

DROP TABLE IF EXISTS `trace`;
CREATE TABLE IF NOT EXISTS `trace` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `action` varchar(90) NOT NULL,
  `objet` varchar(100) DEFAULT NULL,
  `id_user` bigint(11) NOT NULL,
  `valeur` varchar(100) DEFAULT NULL,
  `date_trace` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_trace_id_user` (`id_user`)
) ENGINE=InnoDB AUTO_INCREMENT=156 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `trace`
--

INSERT INTO `trace` (`id`, `action`, `objet`, `id_user`, `valeur`, `date_trace`) VALUES
(1, 'Vider', 'Trace', 3, 'ok', '2018-01-15 12:29:26'),
(2, 'insertion', 'prestation technique', 3, NULL, '2018-01-15 12:36:05'),
(3, 'insertion', 'prestation technique', 3, NULL, '2018-01-15 12:38:23'),
(4, 'Ajout', 'Suivi maison', 3, 'Shampoing quotidien', '2018-01-15 12:39:45'),
(5, 'Ajout', 'Achat', 3, 'carte: 2', '2018-01-15 12:41:23'),
(6, 'Mise à jour', 'Achat', 3, 'montant: 100520', '2018-01-15 12:41:23'),
(7, 'Logout', 'Application', 3, 'Succée', '2018-01-15 12:47:00'),
(8, 'Login', 'Application', 3, 'Succès', '2018-01-15 12:52:17'),
(9, 'Ajout', 'Suivi maison', 3, 'hghg', '2018-01-15 12:52:48'),
(10, 'Logout', 'Application', 3, 'Succès', '2018-01-15 12:53:02'),
(11, 'Login', 'Application', 3, 'Succès', '2018-01-15 12:53:48'),
(12, 'Logout', 'Application', 3, 'Succès', '2018-01-15 12:56:29'),
(13, 'Login', 'Application', 3, 'Succès', '2018-01-15 13:06:07'),
(14, 'Logout', 'Application', 3, 'Succès', '2018-01-15 13:09:38'),
(15, 'Login', 'Application', 3, 'Succès', '2018-01-15 13:10:00'),
(16, 'Logout', 'Application', 3, 'Succès', '2018-01-15 13:12:03'),
(17, 'Login', 'Application', 3, 'Succès', '2018-01-15 13:13:46'),
(18, 'Logout', 'Application', 3, 'Succès', '2018-01-15 13:17:09'),
(19, 'Login', 'Application', 3, 'Succès', '2018-01-15 13:17:35'),
(20, 'Logout', 'Application', 3, 'Succès', '2018-01-15 13:21:24'),
(21, 'Login', 'Application', 3, 'Succès', '2018-01-15 13:21:45'),
(22, 'Logout', 'Application', 3, 'Succès', '2018-01-15 13:22:54'),
(23, 'Login', 'Application', 3, 'Succès', '2018-01-15 13:24:25'),
(24, 'Logout', 'Application', 3, 'Succès', '2018-01-15 13:25:47'),
(25, 'Login', 'Application', 3, 'Succès', '2018-01-15 13:26:15'),
(26, 'Logout', 'Application', 3, 'Succès', '2018-01-15 13:28:29'),
(27, 'Login', 'Application', 3, 'Succès', '2018-01-15 13:28:57'),
(28, 'Logout', 'Application', 3, 'Succès', '2018-01-15 14:31:37'),
(29, 'Login', 'Application', 3, 'Succès', '2018-01-15 14:32:01'),
(30, 'Logout', 'Application', 3, 'Succès', '2018-01-15 14:34:29'),
(31, 'Login', 'Application', 3, 'Succès', '2018-01-15 14:34:48'),
(32, 'Logout', 'Application', 3, 'Succès', '2018-01-15 14:40:31'),
(33, 'Login', 'Application', 3, 'Succès', '2018-01-15 14:41:18'),
(34, 'Logout', 'Application', 3, 'Succès', '2018-01-15 14:42:53'),
(35, 'Login', 'Application', 3, 'Succès', '2018-01-15 14:48:56'),
(36, 'Ajout', 'Achat', 3, 'carte: 1', '2018-01-15 14:51:22'),
(37, 'Mise à jour', 'Achat', 3, 'montant: 90600', '2018-01-15 14:51:22'),
(38, 'Login', 'Application', 3, 'Succès', '2018-01-15 15:05:02'),
(39, 'Logout', 'Application', 3, 'Succès', '2018-01-15 15:06:16'),
(40, 'Logout', 'Application', 3, 'Succès', '2018-01-15 15:06:19'),
(41, 'Login', 'Application', 3, 'Succès', '2018-01-15 15:07:12'),
(42, 'Logout', 'Application', 3, 'Succès', '2018-01-15 15:08:09'),
(43, 'Login', 'Application', 3, 'Succès', '2018-01-15 15:09:27'),
(44, 'Logout', 'Application', 3, 'Succès', '2018-01-15 15:11:22'),
(45, 'Login', 'Application', 3, 'Succès', '2018-01-15 15:11:50'),
(46, 'Logout', 'Application', 3, 'Succès', '2018-01-15 15:12:41'),
(47, 'Login', 'Application', 3, 'Succès', '2018-01-15 15:15:13'),
(48, 'Logout', 'Application', 3, 'Succès', '2018-01-15 15:17:25'),
(49, 'Login', 'Application', 3, 'Succès', '2018-01-15 15:17:55'),
(50, 'Logout', 'Application', 3, 'Succès', '2018-01-15 15:20:51'),
(51, 'Login', 'Application', 3, 'Succès', '2018-01-15 15:21:29'),
(52, 'Logout', 'Application', 3, 'Succès', '2018-01-15 15:25:41'),
(53, 'Login', 'Application', 3, 'Succès', '2018-01-15 15:27:37'),
(54, 'Logout', 'Application', 3, 'Succès', '2018-01-15 15:28:26'),
(55, 'Login', 'Application', 3, 'Succès', '2018-01-15 15:29:37'),
(56, 'Logout', 'Application', 3, 'Succès', '2018-01-15 15:30:53'),
(57, 'Login', 'Application', 3, 'Succès', '2018-01-15 15:32:01'),
(58, 'Logout', 'Application', 3, 'Succès', '2018-01-15 15:32:54'),
(59, 'Login', 'Application', 3, 'Succès', '2018-01-15 15:33:58'),
(60, 'Logout', 'Application', 3, 'Succès', '2018-01-15 15:35:41'),
(61, 'Login', 'Application', 3, 'Succès', '2018-01-15 15:36:09'),
(62, 'Logout', 'Application', 3, 'Succès', '2018-01-15 15:37:00'),
(63, 'Login', 'Application', 3, 'Succès', '2018-01-15 15:37:53'),
(64, 'Ajout', 'Achat', 3, 'carte: 1', '2018-01-15 15:38:32'),
(65, 'Mise à jour', 'Achat', 3, 'montant: 117116', '2018-01-15 15:38:33'),
(66, 'Logout', 'Application', 3, 'Succès', '2018-01-15 15:38:44'),
(67, 'Login', 'Application', 3, 'Succès', '2018-01-15 15:40:38'),
(68, 'Logout', 'Application', 3, 'Succès', '2018-01-15 15:47:19'),
(69, 'Login', 'Application', 3, 'Succès', '2018-01-15 15:47:44'),
(70, 'Logout', 'Application', 3, 'Succès', '2018-01-15 15:51:38'),
(71, 'Login', 'Application', 3, 'Succès', '2018-01-15 15:52:09'),
(72, 'Logout', 'Application', 3, 'Succès', '2018-01-15 15:55:18'),
(73, 'Login', 'Application', 3, 'Succès', '2018-01-15 15:55:58'),
(74, 'insertion', 'prestation technique', 3, NULL, '2018-01-15 15:56:31'),
(75, 'Logout', 'Application', 3, 'Succès', '2018-01-15 15:57:07'),
(76, 'Login', 'Application', 3, 'Succès', '2018-01-15 15:57:54'),
(77, 'Logout', 'Application', 3, 'Succès', '2018-01-15 15:59:32'),
(78, 'Login', 'Application', 3, 'Succès', '2018-01-15 16:00:23'),
(79, 'insertion', 'prestation technique', 3, NULL, '2018-01-15 16:01:53'),
(80, 'Logout', 'Application', 3, 'Succès', '2018-01-15 16:02:03'),
(81, 'Login', 'Application', 3, 'Succès', '2018-01-15 16:02:43'),
(82, 'Logout', 'Application', 3, 'Succès', '2018-01-15 16:04:16'),
(83, 'Login', 'Application', 3, 'Succès', '2018-01-15 16:04:39'),
(84, 'insertion', 'prestation technique', 3, NULL, '2018-01-15 16:05:03'),
(85, 'Ajout', 'Achat', 3, 'carte: 1', '2018-01-15 16:06:44'),
(86, 'Mise à jour', 'Achat', 3, 'montant: 175600', '2018-01-15 16:06:44'),
(87, 'Logout', 'Application', 3, 'Succès', '2018-01-15 16:07:13'),
(88, 'Login', 'Application', 3, 'Succès', '2018-01-15 16:33:03'),
(89, 'Logout', 'Application', 3, 'Succès', '2018-01-15 16:43:00'),
(90, 'Login', 'Application', 3, 'Succès', '2018-01-15 16:43:24'),
(91, 'Ajout', 'Suivi maison', 3, 'fdfdffdfdfd vdvdvdvddvdvd', '2018-01-15 16:43:55'),
(92, 'Ajout', 'Achat', 3, 'carte: 1', '2018-01-15 16:44:09'),
(93, 'Mise à jour', 'Achat', 3, 'montant: 166800', '2018-01-15 16:44:09'),
(94, 'insertion', 'prestation technique', 3, NULL, '2018-01-15 16:45:15'),
(95, 'mise à jour ', 'carte', 3, 'etat_carte =1', '2018-01-15 16:46:16'),
(96, 'Mise à jour', 'Client Technique', 3, 'Ly Lamine', '2018-01-15 16:46:16'),
(97, 'mise à jour ', 'carte', 3, 'etat_carte =1', '2018-01-15 16:46:53'),
(98, 'Mise à jour', 'Client Technique', 3, 'Faye Fallou', '2018-01-15 16:46:53'),
(99, 'mise à jour ', 'carte', 3, 'etat_carte =1', '2018-01-15 16:47:29'),
(100, 'Mise à jour', 'Client Technique', 3, 'Sall Seydou', '2018-01-15 16:47:29'),
(101, 'mise à jour ', 'carte', 3, 'etat_carte =1', '2018-01-15 16:49:25'),
(102, 'Mise à jour', 'Client Technique', 3, 'Mbaye Pape Babacar', '2018-01-15 16:49:25'),
(103, 'Logout', 'Application', 3, 'Succès', '2018-01-15 16:55:16'),
(104, 'Login', 'Application', 3, 'Succès', '2018-01-15 16:55:37'),
(105, 'mise à jour ', 'carte', 3, 'etat_carte =1', '2018-01-15 16:56:00'),
(106, 'Mise à jour', 'Client Technique', 3, 'Mal baye mbaye', '2018-01-15 16:56:00'),
(107, 'insertion', 'prestation technique', 3, NULL, '2018-01-15 16:56:20'),
(108, 'Logout', 'Application', 3, 'Succès', '2018-01-15 16:56:57'),
(109, 'Login', 'Application', 3, 'Succès', '2018-01-15 17:01:27'),
(110, 'Logout', 'Application', 3, 'Succès', '2018-01-15 17:02:09'),
(111, 'Login', 'Application', 3, 'Succès', '2018-01-15 17:02:59'),
(112, 'Logout', 'Application', 3, 'Succès', '2018-01-15 17:04:19'),
(113, 'Login', 'Application', 3, 'Succès', '2018-01-15 17:05:22'),
(114, 'Ajout', 'Client Technique', 3, 'sall Soufy', '2018-01-15 17:06:17'),
(115, 'Ajout', 'Client Technique', 3, 'Ka Lamine', '2018-01-15 17:06:50'),
(116, 'Envoi d\'email', 'Email', 3, 'teste ', '2018-01-15 17:07:26'),
(117, 'Envoi d\'email', 'Email', 3, 'teste', '2018-01-15 17:08:44'),
(118, 'Logout', 'Application', 3, 'Succès', '2018-01-15 17:09:24'),
(119, 'Login', 'Application', 3, 'Succès', '2018-01-15 17:11:43'),
(120, 'Envoi d\'email', 'Email', 3, 'teste', '2018-01-15 17:12:09'),
(121, 'Logout', 'Application', 3, 'Succès', '2018-01-15 17:13:19'),
(122, 'Login', 'Application', 3, 'Succès', '2018-01-15 17:18:23'),
(123, 'Logout', 'Application', 3, 'Succès', '2018-01-15 17:19:33'),
(124, 'Login', 'Application', 3, 'Succès', '2018-01-15 17:19:57'),
(125, 'Logout', 'Application', 3, 'Succès', '2018-01-15 17:20:26'),
(126, 'Login', 'Application', 3, 'Succès', '2018-01-15 17:43:06'),
(127, 'Logout', 'Application', 3, 'Succès', '2018-01-15 17:43:44'),
(128, 'Login', 'Application', 3, 'Succès', '2018-01-15 17:48:36'),
(129, 'Logout', 'Application', 3, 'Succès', '2018-01-15 17:49:50'),
(130, 'Login', 'Application', 3, 'Succès', '2018-01-15 17:50:50'),
(131, 'Logout', 'Application', 3, 'Succès', '2018-01-15 17:57:04'),
(132, 'Login', 'Application', 3, 'Succès', '2018-01-15 17:57:29'),
(133, 'Logout', 'Application', 3, 'Succès', '2018-01-15 17:58:24'),
(134, 'Login', 'Application', 3, 'Succès', '2018-01-15 18:08:06'),
(135, 'Logout', 'Application', 3, 'Succès', '2018-01-15 18:08:40'),
(136, 'Login', 'Application', 3, 'Succès', '2018-01-15 20:54:51'),
(137, 'Logout', 'Application', 3, 'Succès', '2018-01-15 20:55:49'),
(138, 'Login', 'Application', 3, 'Succès', '2018-01-15 21:05:59'),
(139, 'Logout', 'Application', 3, 'Succès', '2018-01-15 21:08:16'),
(140, 'Login', 'Application', 3, 'Succès', '2018-01-15 21:09:19'),
(141, 'Ajout', 'Client Technique', 3, 'Sy Salimata', '2018-01-15 21:09:57'),
(142, 'Ajout', 'Client Technique', 3, 'Diop Fatou', '2018-01-15 21:10:36'),
(143, 'Logout', 'Application', 3, 'Succès', '2018-01-15 21:14:13'),
(144, 'Login', 'Application', 3, 'Succès', '2018-01-15 21:14:37'),
(145, 'Mise à jour', 'Client Technique', 3, 'Diop Fatou', '2018-01-15 21:15:47'),
(146, 'Logout', 'Application', 3, 'Succès', '2018-01-15 21:29:35'),
(147, 'Login', 'Application', 3, 'Succès', '2018-01-15 21:30:11'),
(148, 'Mise à jour', 'Client Technique', 3, 'Diop Fatou', '2018-01-15 21:30:43'),
(149, 'Mise à jour', 'Client Technique', 3, 'Diop Fatou', '2018-01-15 21:31:24'),
(150, 'Logout', 'Application', 3, 'Succès', '2018-01-15 21:34:37'),
(151, 'Login', 'Application', 3, 'Succès', '2018-01-15 21:35:08'),
(152, 'Mise à jour', 'Client Technique', 3, 'Sy Salimata', '2018-01-15 21:35:46'),
(153, 'Logout', 'Application', 3, 'Succès', '2018-01-15 21:39:10'),
(154, 'Login', 'Application', 3, 'Succès', '2018-01-15 21:39:51'),
(155, 'Logout', 'Application', 3, 'Succès', '2018-01-15 21:41:33');

-- --------------------------------------------------------

--
-- Structure de la table `type_de_cheveux`
--

DROP TABLE IF EXISTS `type_de_cheveux`;
CREATE TABLE IF NOT EXISTS `type_de_cheveux` (
  `id_type_de_cheveux` int(11) NOT NULL AUTO_INCREMENT,
  `name_type_de_cheveux` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id_type_de_cheveux`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `type_de_cheveux`
--

INSERT INTO `type_de_cheveux` (`id_type_de_cheveux`, `name_type_de_cheveux`) VALUES
(1, 'Secs'),
(2, 'Très secs, Rêches'),
(3, 'Affaiblis, Cassants'),
(4, 'Indociles, Volumineux');

-- --------------------------------------------------------

--
-- Structure de la table `type_prestation`
--

DROP TABLE IF EXISTS `type_prestation`;
CREATE TABLE IF NOT EXISTS `type_prestation` (
  `id_type_prestation` int(11) NOT NULL AUTO_INCREMENT,
  `nom_type_prestation` varchar(100) NOT NULL,
  `est_technique` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_type_prestation`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `type_prestation`
--

INSERT INTO `type_prestation` (`id_type_prestation`, `nom_type_prestation`, `est_technique`) VALUES
(1, 'Forme texture', 1),
(2, 'Soins Rituels', 1),
(3, 'Couleurs', 1),
(4, 'Coiffures', 1),
(5, 'Esthétique', 1);

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id_user` bigint(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(60) DEFAULT NULL,
  `name` varchar(60) DEFAULT NULL,
  `lastname` varchar(60) DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `telephone` varchar(20) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `id_profil` int(11) NOT NULL,
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id_user`, `username`, `name`, `lastname`, `email`, `telephone`, `password`, `id_profil`) VALUES
(1, 'david', 'david', 'david', 'blackstar@gmail.com', '+221777008494', '6c97cd07663b099253bc569fe8d342bb', 2),
(3, 'michele', 'michele', 'michele', 'laylay@gmail.com', '777826604', 'c2ba1bc54b239208cb37b901c0d3b363', 1);

-- --------------------------------------------------------

--
-- Structure de la table `visite`
--

DROP TABLE IF EXISTS `visite`;
CREATE TABLE IF NOT EXISTS `visite` (
  `id_visite` int(11) NOT NULL AUTO_INCREMENT,
  `date_visite` date NOT NULL,
  `reference_carte` int(11) NOT NULL,
  `nombre_points` int(11) NOT NULL,
  PRIMARY KEY (`id_visite`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `achat`
--
ALTER TABLE `achat`
  ADD CONSTRAINT `achat_id_client` FOREIGN KEY (`id_client`) REFERENCES `client` (`id_client`);

--
-- Contraintes pour la table `conseil`
--
ALTER TABLE `conseil`
  ADD CONSTRAINT `conseil_id_client` FOREIGN KEY (`id_client`) REFERENCES `client` (`id_client`);

--
-- Contraintes pour la table `mouvement_stock`
--
ALTER TABLE `mouvement_stock`
  ADD CONSTRAINT `mouvement_stock_id_produit` FOREIGN KEY (`id_produit`) REFERENCES `produit` (`id_produit`);

--
-- Contraintes pour la table `prestation_fournie`
--
ALTER TABLE `prestation_fournie`
  ADD CONSTRAINT `prestation_fournie_id_client` FOREIGN KEY (`id_client`) REFERENCES `client` (`id_client`);

--
-- Contraintes pour la table `trace`
--
ALTER TABLE `trace`
  ADD CONSTRAINT `FK_trace_id_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
