/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this feuille.template file, choose Tools | Templates
 * and open the feuille.template in the editor.
 */
package paintproject.view;

import java.io.File;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import manager.Move;
import paintcommon.FigureModel;
import paintproject.FeuilleDessin;
import paintproject.draw.util.DrawUtil;

/**
 *
 * @author G38772
 */
public class ReadOnlyView extends AbstractView /*Application implements PaintVue*/ /*Application implements PaintVue*/ {

    private FeuilleDessin feuille;
    private File file;
    private FigureModel state;

    public ReadOnlyView(double width, double height) {
        super("Read Only View", width, height);
        //initElements
        feuille = new FeuilleDessin(1000, 500);
        // ajout des panel à la racine dans l'ordre !!
        root.getChildren().add(feuille);
        //initialisation model
        sceneListeners();
    }

    public ReadOnlyView(double width, double height, String ip) throws RemoteException, NotBoundException, MalformedURLException {
        this(width, height);
        super.initModel(ip);
    }

    public void widthChange(double width) {
        feuille.fond.setWidth(width);
    }

    public void heightChange(double height) {
        feuille.fond.setHeight(height);
    }

    @Override
    public void notifieChangement() {
        System.out.println("UPDATE DUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUR" + this.state);
        new DrawUtil(this, feuille, false).run();
    }

    
    private void sceneListeners() {
        this.getScene().widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                getFeuille().fond.setWidth(newSceneWidth.doubleValue());
            }
        });

        this.getScene().heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                getFeuille().fond.setHeight(newValue.doubleValue());
            }
        });
    }

    /**
     * @return the feuille
     */
    public FeuilleDessin getFeuille() {
        return feuille;
    }

    /**
     * @param feuille the feuille to set
     */
    public void setFeuille(FeuilleDessin feuille) {
        this.feuille = feuille;
    }

    /**
     * @return the state
     */
    public FigureModel getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(FigureModel state) {
        this.state = state;
    }

}
