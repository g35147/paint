package paintcommon;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface EcouteurDistant extends Remote {

    void notifyChangementDistant() throws RemoteException;

}
