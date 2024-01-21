package controller;
import java.util.List;
import classes.Cours;
import database.DatabaseManager;

public class EmploiDuTempsController {

    public List<Cours> getCoursPourSemaine(int numeroSemaine) {
        // Appel direct à la méthode de DatabaseManager
        return DatabaseManager.getCoursPourSemaine(numeroSemaine);
    }
}
