/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package implementation;

import architecturemvc.PaintModel;
import architecturemvc.PaintVue;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import manager.Action;
import paintcommon.EcouteurDistant;
import paintcommon.FigureModel;
import paintcommon.PaintInterface;

/**
 * Une implémentation du modèle de l'additionneur
 */
public class PaintClient implements PaintModel, EcouteurDistant, Remote {

    private int id;
    private List<PaintVue> vues;
    private FigureModel etat;
    private PaintInterface paintRemote;

    public PaintClient(PaintInterface paintRemote) throws RemoteException {
        vues = new ArrayList<>();
        etat = new FigureModel();
        this.paintRemote = paintRemote;
        UnicastRemoteObject.exportObject(this, 0);
        id = paintRemote.getViews().addEcouteurDistant(this);
    }

    public int getId() {
        return id;
    }

    @Override
    public void addPaintListener(PaintVue vue) {
        vues.add(vue);
        fire();
    }

    @Override
    public void setState(Action action) {
        try {
            paintRemote.setState(action);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public FigureModel getState() {
        try {
            FigureModel etat = paintRemote.getState();
            return etat;
        } catch (RemoteException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public void removePaintListener(PaintVue vue) {
        vues.remove(vue);
        fire();
    }

    private void fire() {
        for (PaintVue vue : vues) {
            vue.notifieChangement();
        }
    }

    @Override
    public int getListeners() {
        return 0;
    }

    @Override
    public void notifyChangementDistant() {
        fire();
    }
}
