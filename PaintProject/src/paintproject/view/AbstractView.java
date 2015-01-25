/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paintproject.view;

import architecturemvc.PaintModel;
import architecturemvc.PaintVue;
import implementation.PaintClient;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import paintcommon.PaintInterface;

/**
 *
 * @author supeUse
 */
public abstract class AbstractView extends Stage implements PaintVue {

    public PaintInterface serverModel;
    private PaintModel model;

    protected Group root;
    protected Scene scene;

    public AbstractView(String title, double width, double height) {
        this.setTitle(title);
        root = new Group();
        scene = new Scene(root, width, height);
        this.setScene(scene);
        
        scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
    }

    public void initModel(String ip) throws NotBoundException, MalformedURLException, RemoteException {
        if (!ip.isEmpty()) {
            ip = "rmi://" + ip + "/";
        }
        serverModel = (PaintInterface) Naming.lookup(ip + "Paint");
        model = new PaintClient(serverModel);
        setModel(model);
    }

    public void stop() throws Exception {
        System.out.println("FFFFFFFFFFFFFFFFFmlkmlkFFFFFFFFFFFFFFFFFFFFFFFFFF");
        if (model != null) {
            model.removePaintListener(this);
            serverModel.getViews().removeEcouteurDistant(model.getId());
            model = null;
            serverModel = null;
        }
    }

    /**
     * fournit le modèle à la vue
     */
    private void setModel(PaintModel modele) throws RemoteException {
        if (this.model != null) {
            this.model.removePaintListener(this);
        }
        this.model = modele;
        this.model.addPaintListener(this);
    }

    /**
     * retourne le modèle actuel
     *
     * @return
     */
    public PaintModel getModel() {
        return model;
    }
}
