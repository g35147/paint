/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package architecturemvc;

/**
 *
 * Interface que les vues doivent présenter au modèle
 *
 * @author Chaabani Nabil
 */
public interface PaintVue {

    /**
     * Permet à la vue de se mettre à jour car le modèle lui notifie le
     * changement (éventuellement par le biais d'un contrôleur).
     */
    public void notifieChangement();

}
