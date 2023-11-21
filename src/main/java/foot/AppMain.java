/**
 * 
 */
package foot;

import java.util.List;

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
//			List<Equipe> teams = LectureFichier.readTeamsFromCsv("goalscorers.csv");
		List<Equipe> teams2 = LectureFichier.readTeamsFromCsv("results.csv");
//			List<Equipe> teams3 = LectureFichier.readTeamsFromCsv("shootouts.csv");
		List<Joueur> players = LectureFichier.readPlayersFromCsv("goalscorers.csv");
		List<Buteur> buteur = LectureFichier.readButeursFromCsv("goalscorers.csv");
		List<Match> matches = LectureFichier.readMatchesFromCsv("results.csv",50);
		List<Competition> competitions = LectureFichier.readCompetitionsFromCsv("results.csv");
		List<TirauBut> tirauButs = LectureFichier.readTirauButFromCsv("shootouts.csv");
		FootballService footballService = new FootballService();
		
//	        footballService.saveTeams(teams);
		footballService.saveTeams(teams2);
//	        footballService.saveTeams(teams3);
		
		footballService.saveTirauButs(tirauButs);
		footballService.savePlayers(players); 
		footballService.saveCompetitions(competitions);
		footballService.saveScorers(buteur);
		footballService.saveMatches(matches);             
		
		

	}

}
