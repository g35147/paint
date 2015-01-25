/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paintproject;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author supeUse
 */
public class FeuilleDessin extends Group {

    public Rectangle fond;

    public int lastAction;

    //Elements temporaires
    public Circle tempC;
    public Rectangle tempP;

    public Rectangle currentP2;
    public Line tempL;
    public Line tempLHT;
    public Line tempLHB;
    public Line tempLVR;
    public Line tempLVL;
    public double ecartX;
    public double ecartY;
    public double ecartX2;
    public double ecartY2;

    // Global temporaire variable
    public double tempX;
    public double tempY;

    // Variables de rectangle
    public Rectangle rectTemp;
    
    // Variable de triangle
    public Polygon triTemp;

    // Variable de cercle
    public Circle circleTemp;
    public int radius;

    // Variable de ligne
    public Line lineTemp;

    // Variable de déssin libre
    public Path path;

    public FeuilleDessin(double w, double h) {
        super();
        initialisationAttributs();
        initFond(w, h);
    }

    private void initialisationAttributs() {
        lastAction = -1;
        tempC = null;
        tempP = currentP2 = null;
        tempLHT = tempLHB = tempLVR = tempLVL = null;
    }

    private void initFond(double w, double h) {
        fond = new Rectangle(w, h); //ce sur quoi tout sera dessiné
        fond.setFill(Color.LIGHTGREEN);
        this.getChildren().add(fond);
    }

    public void resetTempCircle(double x, double y, int rayon,
            Color trait, Color fill, int tailleTrait) {
        circleTemp = new Circle(x, y, rayon, fill);
        circleTemp.setStroke(trait);
        circleTemp.setStrokeWidth(tailleTrait);
    }

    public void resetTempRectangle(double x, double y,
            Color trait, Color fill, int tailleTrait) {
        rectTemp = new Rectangle(x, y, 0, 0);
        rectTemp.setFill(fill);
        rectTemp.setStroke(trait);
        rectTemp.setStrokeWidth(tailleTrait);
    }
    
    
    public void resetTempTriangle(double x, double y,
            Color stroke, Color fill, int tailleTrait) {
        triTemp = new Polygon(x,y,x,y,x,y);
        triTemp.setFill(fill);
        triTemp.setStroke(stroke);
        triTemp.setStrokeWidth(tailleTrait);
    }

    public void resetTempLine(double x, double y,
            Color trait, int tailleTrait) {
        lineTemp = new Line(x, y, x, y);
        lineTemp.setStroke(trait);
        lineTemp.setStrokeWidth(tailleTrait);
    }

    public void resetTempPath(Color trait, int tailleTrait) {
        path = new Path();
        path.setStroke(trait);
        path.setStrokeWidth(tailleTrait);
    }

}
