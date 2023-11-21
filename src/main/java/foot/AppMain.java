/**
 * 
 */
package foot;

import java.util.List;
import java.util.Scanner;

import com.opencsv.exceptions.CsvValidationException;

import model.Buteur;
import model.Competition;
import model.Equipe;
import model.Joueur;
import model.Match;
import model.TirauBut;
import service.FootballService;
import service.LectureFichier;
/**
 * 
 */
public class AppMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws CsvValidationException {
		// Charger et enregistrer les équipes depuis le fichier CSV
		
		FootballService footballService = new FootballService();
		
		List<Equipe> teams = LectureFichier.readTeamsFromCsv("results.csv");
        footballService.saveTeams(teams);

        List<Joueur> players = LectureFichier.readPlayersFromCsv("goalscorers.csv");
        footballService.savePlayers(players);

        List<Competition> competitions = LectureFichier.readCompetitionsFromCsv("results.csv");
        footballService.saveCompetitions(competitions);

        List<Match> matches = LectureFichier.readMatchesFromCsv("results.csv", 50);
        footballService.saveMatches(matches);

        List<Buteur> buteur = LectureFichier.readButeursFromCsv("goalscorers.csv",50);
        footballService.saveScorers(buteur);

        List<TirauBut> tirauButs = LectureFichier.readTirauButFromCsv("shootouts.csv");
        footballService.saveTirauButs(tirauButs);
		
		Scanner scanner = new Scanner(System.in);

        int choice;
        do {
        	
            
            // Afficher le menu
            System.out.println("Menu :");
            System.out.println("1. Afficher les N meilleurs buteurs de tous les temps");
            System.out.println("2. Afficher les N meilleurs buteurs d’une compétition donnée");
            System.out.println("3. Afficher les N meilleurs buteurs d’une équipe donnée");
            System.out.println("4. Afficher les N équipes qui ont gagné le plus de match en %");
            System.out.println("5. Afficher les matchs entre 2 équipes données et afficher le % de victoire de chaque équipe");
            System.out.println("0. Quitter");

            // Lire le choix de l'utilisateur
            System.out.print("Choix : ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Lire la nouvelle ligne pour éviter les problèmes d'entrée

            // Exécuter la méthode correspondante en fonction du choix de l'utilisateur
            switch (choice) {
                case 1:
                    footballService.displayTopScorers();
                    break;
                case 2:
                    footballService.displayTopScorersByCompetition("Friendly",10);
                    break;
                case 3:
                    footballService.displayTopScorersByTeam("Chilie",5);
                    break;
                case 4:
                    footballService.displayTopWinningTeams(10);
                    break;
                case 5:
                    footballService.displayMatchResultsBetweenTeams("Scotland","England");
                    break;
                case 0:
                    // Quitter le programme
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez entrer un nombre entre 0 et 5.");
            }
        } while (choice != 0);

        

        scanner.close();
    }           
		
		

	}


