package metier;

import db.DBManager;
import db.PaintPersistanceException;

/**
 * Classe utilitaire d'outils d'aide à la rédaction de méthodes 'façade'
 *
 * @author nabil
 */
class OutilsBL {

    static void gereAnnulation(String msg) throws PaintPersistanceException {
        try {
            DBManager.annuleTransaction();
        } catch (PaintPersistanceException ex) {
            msg = msg + "\n" + ex.getMessage();
        }
        throw new PaintPersistanceException(msg);
    }

}
