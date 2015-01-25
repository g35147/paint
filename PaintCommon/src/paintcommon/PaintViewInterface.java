package paintcommon;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Chaabani Nabil
 */
public interface PaintViewInterface extends Remote {

    public int addEcouteurDistant(EcouteurDistant ecouteur) throws RemoteException;

    public void removeEcouteurDistant(int id) throws RemoteException;
}
