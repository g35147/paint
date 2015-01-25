package rmiAdd;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import paintcommon.EcouteurDistant;
import paintcommon.PaintViewInterface;

/**
 *
 * @author supeUse
 */
public class PaintView extends UnicastRemoteObject
    implements PaintViewInterface {
    private static int nextId = 1;

    private HashMap<Integer, EcouteurDistant> ecouteurs;
    
    public PaintView() throws RemoteException {
        ecouteurs = new HashMap<>();
    }

    @Override
    public int addEcouteurDistant(EcouteurDistant ecouteur){
        int id = nextId++;
        ecouteurs.put(id, ecouteur);
        return id;
    }
    
    @Override
    public void removeEcouteurDistant(int id) {
        ecouteurs.remove(id);
    }
    
    public void fire() throws RemoteException {
        for (Integer id : ecouteurs.keySet()) {
            ecouteurs.get(id).notifyChangementDistant();
        }
    }
    
}
