/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package architecturemvc;

import java.rmi.Remote;
import java.util.List;
import manager.Action;
import paintcommon.FigureModel;
import paintcommon.shape.ShapeModel;

/**
 * Interface que doit présenter le modèle de l'additionneur aux vues et aux
 * contrôleurs.
 *
 * Cette interface pourrait être séparé en deux:
 * <ul>
 * <li>Interface pour vues : reprenant les 4 premières méthodes</li>
 * <li>Interface pour contrôleurs : reprenant la dernière méthode</li>
 * </ul>
 *
 * @author Chaabani Nabil
 */
public interface PaintModel {

    public int getId();

    /**
     * retourne à la vue l'état du modèle
     */
    public FigureModel getState();

    /**
     * Réalise l'addition des termes fournis
     */
    public void setState(Action action);

    /**
     * permet à la vue de s'abonner en tant qu'observateur du modèle
     */
    public void addPaintListener(PaintVue add);

    /**
     * permet à la vue de se désabonner en tant qu'observateur du modèle
     */
    public void removePaintListener(PaintVue add);

    /**
     * retourne le nombre de listeners inscrits
     */
    public int getListeners();
}
