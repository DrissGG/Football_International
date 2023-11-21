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

}
