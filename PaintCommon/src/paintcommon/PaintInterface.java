package paintcommon;

import java.rmi.Remote;
import java.rmi.RemoteException;
import manager.Action;

/**
 *
 * @author Chaabani Nabil
 */
public interface PaintInterface extends Remote {

    /**
     * retourne à la vue l'état du modèle
     */
    public FigureModel getState() throws RemoteException;

    public PaintViewInterface getViews() throws RemoteException;

    /**
     * Réalise l'addition des termes fournis
     */
    public void setState(Action action) throws RemoteException;
}
