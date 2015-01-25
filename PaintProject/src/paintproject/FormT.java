/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paintproject;

import javafx.scene.Node;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import paintcommon.shape.ShapeModel;

/**
 *
 * @author supeUse
 */
public class FormT extends Form {

    private double tecartX;
    private double tecartY;
    private double ecartX2;
    private double ecartY2;

//    public FormT(Node shapeFx, ShapeModel shapeModel) {
//        super(shapeFx, shapeModel);
//    }
    public FormT(Node objet, ShapeModel shapeModel, double ecartX, double ecartY) {
        super(objet, shapeModel);
        this.tecartX = ecartX;
        this.tecartY = ecartY;
        this.ecartX2 = 0;
        this.ecartY2 = 0;
    }

    public FormT(Node objet, ShapeModel shapeModel, double ecartX, double ecartY, double ecartX2, double ecartY2) {
        this(objet, shapeModel, ecartX, ecartY);
        this.ecartX = ecartX2;
        this.ecartY = ecartY2;
    }

    @Override
    public void setX(double x) {
        super.setX(x - tecartX);
    }

    @Override
    public void setY(double y) {
        super.setY(y - tecartY);
    }
//
//    @Override
//    public void setX2(double x) {
//        super.setX2(x - ecartX2);
//    }
//
//    @Override
//    public void setY2(double y) {
//        super.setY2(y - ecartY2);
//    }
}
