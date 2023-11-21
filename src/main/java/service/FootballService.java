/**
 * 
 */
package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.ButeurDao;
import dao.CompetitionDao;
import dao.EquipeDao;
import dao.JoueurDao;
import dao.MatchDao;
import dao.TirauButDao;
import model.Buteur;
import model.Competition;
import model.Equipe;
import model.Joueur;
import model.Match;
import model.TirauBut;

/**
 * 
 */
public class FootballService {
	
	private final EquipeDao equipeDao = new EquipeDao();
    private final MatchDao matchDao = new MatchDao();
    private final ButeurDao buteurDao = new ButeurDao();
    private final CompetitionDao competitionDao = new CompetitionDao();
    private final TirauButDao tirauButDao = new TirauButDao();
    private final JoueurDao joueurDao = new JoueurDao();
    

    public void saveTeams(List<Equipe> teams) {
        for (Equipe team : teams) {
        	System.out.println(team);
            equipeDao.create(team);
        }
    }
    
    public void savePlayers(List<Joueur> players) {
        for (Joueur player : players) {
            joueurDao.create(player);
        }
    }

    public void saveScorers(List<Buteur> scorers) {
        for (Buteur scorer : scorers) {
            buteurDao.create(scorer);
        }
    }
    
    public void saveMatches(List<Match> matches) {
        // Sauvegardez d'abord les équipes associées aux matchs
        List<Equipe> teams = new ArrayList<>();
        for (Match match : matches) {
            teams.add(match.getEquipeLocal());
            teams.add(match.getEquipeVisiteur());
        }
        saveTeams(teams);

        for (Match match : matches) {
            Date dateMatch = match.getDateMatch();
            Equipe equipeLocal = match.getEquipeLocal();
            Equipe equipeVisiteur = match.getEquipeVisiteur();
            System.out.println(match);

            // Recherchez un match existant
            Match existingMatch = matchDao.findExistingMatch(dateMatch, equipeLocal, equipeVisiteur);

            // Vérifiez si la compétition existe
            Competition competition = match.getCompetition();
            if (competition != null) {
                CompetitionDao competitionDao = new CompetitionDao();
                Competition existingCompetition = competitionDao.findByName(competition.getNom());

                // Sauvegardez la compétition uniquement si elle n'existe pas
                if (existingCompetition == null) {
                    competitionDao.create(competition);
                }
            }

            if (existingMatch == null) {
                matchDao.create(match);
            } else {
                System.out.println("Le match existe déjà.");
            }
        }
    }
    
    public void saveCompetitions(List<Competition> competitions) {
        for (Competition competition : competitions) {
            competitionDao.create(competition);
        }
    }
    
    public void saveTirauButs(List<TirauBut> tirauButs) {
        for (TirauBut tirauBut : tirauButs) {
            tirauButDao.create(tirauBut);
        }
    }
    
    public void displayTopScorers() {
        // Utilisez le DAO des buteurs pour récupérer les meilleurs buteurs de tous les temps
        ButeurDao buteurDao = new ButeurDao();
        List<Buteur> topScorers = buteurDao.findTopScorers(10); // Remplacez 10 par le nombre de meilleurs buteurs que vous voulez afficher

        // Affichez les informations sur les meilleurs buteurs
        System.out.println("Top 10 des meilleurs buteurs de tous les temps :");
        for (Buteur buteur : topScorers) {
            System.out.println("Joueur : " + buteur.getJoueur().getNom());
            System.out.println("Nombre de buts : " + buteur.getMinuteButMarque());
            System.out.println("Penalty : " + buteur.getPenalty());
            System.out.println("But contre son camp : " + buteur.getButContreSansCamp()); 
        }
    }
    
    public void displayTopScorersByCompetition(String competitionName, int n) {
    	ButeurDao buteurDao = new ButeurDao();
        List<Buteur> topScorers = buteurDao.findTopScorersByCompetition(competitionName, n);

        System.out.println("Top " + n + " buteurs de la compétition " + competitionName + ":");
        for (Buteur buteur : topScorers) {
            System.out.println("Joueur : " + buteur.getJoueur().getNom() +
                               ", Minutes de buts marqués : " + buteur.getMinuteButMarque());
        }
    }
    
    public void displayTopScorersByTeam(String teamName, int n) {
    	ButeurDao buteurDao = new ButeurDao();
        List<Buteur> topScorers = buteurDao.findTopScorersByTeam(teamName, n);

        System.out.println("Top Scorers for Team " + teamName + ":");
        for (Buteur buteur : topScorers) {
            System.out.println(buteur.getJoueur().getNom() + " - Minutes: " + buteur.getMinuteButMarque());
        }
    }
    
    public void displayTopWinningTeams(int n) {
    	EquipeDao equipeDao =new EquipeDao();
        List<Equipe> topWinningTeams = equipeDao.findTopWinningTeams(n);

        System.out.println("Top Winning Teams:");
        for (Equipe equipe : topWinningTeams) {
            System.out.println(equipe.getNom() + " - Win Percentage: " + calculateWinPercentage(equipe) + "%");
        }
    }
    private double calculateWinPercentage(Equipe equipe) {
	    int totalMatches = equipe.getMatchesLocaux().size() + equipe.getMatchesVisiteurs().size();
	    if (totalMatches == 0) {
	        return 0.0;
	    }

	    int totalWins = 0;
	    for (Match matchLocal : equipe.getMatchesLocaux()) {
	        if (isTeamWinner(equipe, matchLocal.getScoreFinalLocal(), matchLocal.getScoreFinalVisiteur())) {
	            totalWins++;
	        }
	    }

	    for (Match matchVisiteur : equipe.getMatchesVisiteurs()) {
	        if (isTeamWinner(equipe, matchVisiteur.getScoreFinalVisiteur(), matchVisiteur.getScoreFinalLocal())) {
	            totalWins++;
	        }
	    }

	    return ((double) totalWins / totalMatches) * 100.0;
	}

	private boolean isTeamWinner(Equipe equipe, int teamScore, int opponentScore) {
	    return (teamScore > opponentScore) || ((teamScore == opponentScore) && (Math.random() > 0.5));
	}
	
	
	public void displayMatchResultsBetweenTeams(String equipeLocalNom, String equipeVisiteurNom) {
		MatchDao matchDao =new MatchDao();
	    List<Match> matchResults = matchDao.findMatchResultsBetweenTeams(equipeLocalNom, equipeVisiteurNom);

	    if (matchResults.isEmpty()) {
	        System.out.println("Aucun résultat de match trouvé entre " + equipeLocalNom + " et " + equipeVisiteurNom);
	    } else {
	        System.out.println("Résultats des matchs entre " + equipeLocalNom + " et " + equipeVisiteurNom + ":");

	        for (Match match : matchResults) {
	            System.out.println("Date: " + match.getDateMatch() + ", Score: " + match.getScoreFinalLocal() + " - " + match.getScoreFinalVisiteur());
	        }
	    }
	}


}
