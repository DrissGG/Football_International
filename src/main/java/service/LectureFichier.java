/**
 * 
 */
package service;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.google.protobuf.TextFormat.ParseException;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import dao.CompetitionDao;
import dao.EquipeDao;
import dao.JoueurDao;
import dao.MatchDao;
import model.Buteur;
import model.Competition;
import model.Equipe;
import model.Joueur;
import model.Match;
import model.TirauBut;

/**
 * 
 */
public class LectureFichier {
	public static List<Equipe> readTeamsFromCsv(String filePath) {
		List<Equipe> teams = new ArrayList<>();

		try (Reader reader = Files.newBufferedReader(Paths.get(filePath));
				CSVReader csvReader = new CSVReader(reader)) {

			csvReader.readNext(); // Ignorer l'en-tête

			String[] record;
			while ((record = csvReader.readNext()) != null) {

				// Créer une équipe pour l'équipe local
				Equipe teamLocal = new Equipe();
				teamLocal.setNom(record[1]);

				// Créer une équipe pour l'équipe visiteur
				Equipe teamVisiteur = new Equipe();
				teamVisiteur.setNom(record[2]);

				// Ajouter les deux équipes à la liste
				teams.add(teamLocal);
				teams.add(teamVisiteur);
			}

		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}

		return teams;
	}

	public static List<Joueur> readPlayersFromCsv(String filePath) {
		List<Joueur> players = new ArrayList<>();

		try (Reader reader = Files.newBufferedReader(Paths.get(filePath));
				CSVReader csvReader = new CSVReader(reader)) {

			csvReader.readNext(); // Ignorer l'en-tête

			String[] record;
			while ((record = csvReader.readNext()) != null) {
				Joueur player = new Joueur();
				player.setNom(record[4]); // Assurez-vous que l'indice correspond à votre fichier CSV
				// Ajoutez d'autres champs en fonction des colonnes de votre fichier CSV

				players.add(player);
			}

		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}

		return players;
	}

	private static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?"); // Correspond à un nombre entier ou décimal
	}

	public static List<Buteur> readButeursFromCsv(String filePath) {
		List<Buteur> buteurs = new ArrayList<>();
		
		try {
			Reader reader = Files.newBufferedReader(Paths.get(filePath));
			CSVReader csvReader = new CSVReader(reader);

			csvReader.readNext(); // Ignorer l'en-tête

			String[] record;
			while ((record = csvReader.readNext()) != null) {
				// Créer un nouveau buteur avec les données du fichier CSV
				Buteur buteur = new Buteur();

				// Assurez-vous que la chaîne est un nombre valide avant de la convertir
				String minuteButMarqueStr = record[5].trim(); // Supprimez les espaces au début et à la fin
				if (isNumeric(minuteButMarqueStr)) {
					buteur.setMinuteButMarque(Integer.parseInt(minuteButMarqueStr));
				} else {
					System.out.println("Erreur de conversion de minuteButMarque : " + record[5]);
					continue; // Ignorer cette entrée et passer à la suivante
				}
				buteur.setPenalty(Boolean.parseBoolean(record[7]));
				buteur.setButContreSansCamp(Boolean.parseBoolean(record[6]));

				// Récupérer les noms des équipes et la date depuis le fichier CSV
	            String equipeLocalNom = record[1];
	            String equipeVisiteurNom = record[2];
	            String dateString = record[0];

	            // Utiliser la méthode findMatchesByTeamsAndDateString pour obtenir le match correspondant
	            MatchDao matchDao = new MatchDao();
	            List<Match> matches = matchDao.findMatchesByTeamsAndDate(equipeLocalNom, equipeVisiteurNom, dateString);

	            // Vérifier si au moins un match correspondant a été trouvé
	            if (!matches.isEmpty()) {
	                // Prendre le premier match trouvé (vous pouvez ajuster la logique en fonction de vos besoins)
	                Match match = matches.get(0);
	                buteur.setMatch(match);
	               // buteurs.add(buteur);
	            } else {
	                System.out.println("Aucun match correspondant trouvé dans la base de données.");
	            }

				EquipeDao equipeDao = new EquipeDao();
				JoueurDao joueurDao = new JoueurDao();
				
				
				Equipe equipe = equipeDao.findByName(equipeLocalNom);
	            Joueur joueur = joueurDao.findByName(record[4]);
				

				// Vérifiez si les instances existent dans la base de données
				if (equipe != null && joueur != null ) {
					// Ajoutez les IDs à l'objet Buteur
					buteur.setEquipe(equipe);
					buteur.setJoueur(joueur);

					buteurs.add(buteur);
				} else {
					System.out.println("L'équipe, le joueur ou le match n'existe pas dans la base de données.");
				}

			}
		}catch (Exception e) {
			// TODO: handle exception
		}

		
		return buteurs;
	}

	public static List<Match> readMatchesFromCsv(String filePath, int limit) {
		List<Match> matches = new ArrayList<>();

		try (Reader reader = Files.newBufferedReader(Paths.get(filePath));
				CSVReader csvReader = new CSVReader(reader)) {

			csvReader.readNext(); // Ignorer l'en-tête

			String[] record;
			int count = 0;
			while ((record = csvReader.readNext()) != null && count < limit) {
				// Créer une instance de Match et configurer ses propriétés
				Match match = new Match();
				match.setDateMatch(LocalDate.parse(record[0]));
				match.setCity(record[6]);
				match.setNeutre(Boolean.parseBoolean(record[8]));
				match.setPays(record[7]);
				match.setScoreFinalLocal(Integer.parseInt(record[3]));
				match.setScoreFinalVisiteur(Integer.parseInt(record[4]));

				String nomCompetition = record[5];
				String equipeLocalNom = (record[1]);
				String equipeVisiteurNom = (record[2]);

				CompetitionDao competitionDao = new CompetitionDao();
				EquipeDao equipeDao = new EquipeDao();

				Competition competition = competitionDao.findByName(nomCompetition);

				// Ajoutez l'équipe locale à la base de données si elle n'existe pas encore
				Equipe equipeLocal = equipeDao.findByName(equipeLocalNom);
				if (equipeLocal == null) {
					equipeLocal = new Equipe();
					equipeLocal.setNom(equipeLocalNom);
					equipeDao.create(equipeLocal);
				}

				Equipe equipeVisiteur = equipeDao.findByName(equipeVisiteurNom);
				if (equipeVisiteur == null) {
					equipeVisiteur = new Equipe();
					equipeVisiteur.setNom(equipeVisiteurNom);
					equipeDao.create(equipeVisiteur);
				}

				match.setCompetition(competition);
				match.setEquipeLocal(equipeLocal);
				match.setEquipeVisiteur(equipeVisiteur);

				matches.add(match);
				count++;
			}

		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}

		return matches;
	}

	public static List<Competition> readCompetitionsFromCsv(String filePath) {
		List<Competition> competitions = new ArrayList<>();

		try (Reader reader = Files.newBufferedReader(Paths.get(filePath));
				CSVReader csvReader = new CSVReader(reader)) {

			csvReader.readNext(); // Ignorer l'en-tête

			String[] record;
			while ((record = csvReader.readNext()) != null) {
				// Créer une instance de Competition avec les données du fichier CSV
				Competition competition = new Competition();
				competition.setNom(record[5]);

				competitions.add(competition);
			}

		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}

		return competitions;
	}
	
	
	public static List<TirauBut> readTirauButFromCsv(String filePath) {
        List<TirauBut> tirauButList = new ArrayList<>();
        try {
            Reader reader = Files.newBufferedReader(Paths.get(filePath));
            CSVReader csvReader = new CSVReader(reader);

            csvReader.readNext(); // Ignorer l'en-tête

            String[] record;
            while ((record = csvReader.readNext()) != null) {
                // Créer un nouveau TirauBut avec les données du fichier CSV
                TirauBut tirauBut = new TirauBut();

                // Assurez-vous que la chaîne est un nombre valide avant de la convertir
                String dateTirStr = record[0].trim(); // Supprimez les espaces au début et à la fin
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Spécifiez le format de votre date
				java.util.Date parsedDate = null;
				String
				try {
					parsedDate = dateFormat.parse(dateTirStr);
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// Convertir java.util.Date en java.sql.Date
				java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
				
				// Affecter la date à l'objet TirauBut
				tirauBut.setDateTir(sqlDate);

                // Récupérer les IDs des équipes et matchs depuis la base de données
                String equipeNom = record[1];
                String matchId = record[2];

                EquipeDao equipeDao = new EquipeDao();
                MatchDao matchDao = new MatchDao();
                Equipe vainqueur = equipeDao.findByName(equipeNom);
                Match match = matchDao.findById(Long.parseLong(matchId));

                // Vérifier si l'équipe et le match existent dans la base de données
                if (vainqueur != null && match != null) {
                    tirauBut.setVainqueur(vainqueur);
                    tirauBut.setMatch(match);
                    tirauButList.add(tirauBut);
                } else {
                    System.out.println("L'équipe ou le match n'existe pas dans la base de données.");
                }
            }

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return tirauButList;
    }

}
