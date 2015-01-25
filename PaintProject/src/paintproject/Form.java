/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paintproject;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import paintcommon.shape.PathModel;
import paintcommon.shape.ShapeModel;

/**
 *
 * @author supeUse
 */
public class Form extends Group {

    public Node shapeFx;
    public ShapeModel shapeModel;
    public double ecartX;
    public double ecartY;

    public Form(Node shapeFx, ShapeModel shapeModel) {
        this.shapeFx = shapeFx;
        this.shapeModel = shapeModel;
        this.getChildren().add(shapeFx);
    }

    public void setEcart(double ecartX, double ecartY) {
        this.ecartX = ecartX;
        this.ecartY = ecartY;
    }

//    public Color getColor() {
//        if (!Util.getClassString(shapeFx).equals("cercle")) {
//            ((Circle) shapeFx).setCenterX(x);
//        }
//    }
    public void setX(double x) {
        if (Util.getClassString(shapeFx).equals("cercle")) {
            ((Circle) shapeFx).setCenterX(x);
        } else if (Util.getClassString(shapeFx).equals("rectangle")) {
            ((Rectangle) shapeFx).setX(x);
        } else if (Util.getClassString(shapeFx).equals("ligne")) {
            ((Line) shapeFx).setStartX(x);
            ((Line) shapeFx).setEndX(x - ecartX);
        } else if (Util.getClassString(shapeFx).equals("blockform")) {
            ((BlockForm) shapeFx).setX(x);
        } else if (Util.getClassString(shapeFx).equals("freehand")) {
            updatePathX((Path) shapeFx, x);
        } else if (Util.getClassString(shapeFx).equals("triangle")) {
            updateTriangleX((Polygon) shapeFx, x);
        }
    }

    public void setY(double y) {
        if (Util.getClassString(shapeFx).equals("cercle")) {
            ((Circle) shapeFx).setCenterY(y);
        } else if (Util.getClassString(shapeFx).equals("rectangle")) {
            ((Rectangle) shapeFx).setY(y);
        } else if (Util.getClassString(shapeFx).equals("ligne")) {
            ((Line) shapeFx).setStartY(y);
            ((Line) shapeFx).setEndY(y - ecartY);
        } else if (Util.getClassString(shapeFx).equals("blockform")) {
            ((BlockForm) shapeFx).setY(y);
        } else if (Util.getClassString(shapeFx).equals("freehand")) {
            updatePathY((Path) shapeFx, y);
        } else if (Util.getClassString(shapeFx).equals("triangle")) {
            updateTriangleY((Polygon) shapeFx, y);
        }
    }
//    
//    public void setX2(double x) {
//        if (Util.getClassString(shapeFx).equals("ligne")) {
//            ((Line) shapeFx).setEndX(x);
//        }
//    }
//
//    public void setY2(double y) {
//        if (Util.getClassString(shapeFx).equals("ligne")) {
//            ((Line) shapeFx).setEndY(y);
//        }
//    }

    public double getX() {
        if (Util.getClassString(shapeFx).equals("cercle")) {
            return ((Circle) shapeFx).getCenterX();
        } else if (Util.getClassString(shapeFx).equals("rectangle")) {
            return ((Rectangle) shapeFx).getX();
        } else if (Util.getClassString(shapeFx).equals("ligne")) {
            return ((Line) shapeFx).getStartX();
        } else if (Util.getClassString(shapeFx).equals("blockform")) {
            return ((BlockForm) shapeFx).getX();
        } else if (Util.getClassString(shapeFx).equals("freehand")) {
            return ((MoveTo) ((Path) shapeFx).getElements().get(0)).getX();
        } else if (Util.getClassString(shapeFx).equals("triangle")) {
            System.out.println("dans le triangle de foussssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
            return ((Polygon) shapeFx).getPoints().get(0);
        } else {
            return 0;
        }
    }

    public double getY() {
        if (Util.getClassString(shapeFx).equals("cercle")) {
            return ((Circle) shapeFx).getCenterY();
        } else if (Util.getClassString(shapeFx).equals("rectangle")) {
            return ((Rectangle) shapeFx).getY();
        } else if (Util.getClassString(shapeFx).equals("ligne")) {
            return ((Line) shapeFx).getStartY();
        } else if (Util.getClassString(shapeFx).equals("blockform")) {
            return ((BlockForm) shapeFx).getY();
        } else if (Util.getClassString(shapeFx).equals("freehand")) {
            return ((MoveTo) ((Path) shapeFx).getElements().get(0)).getY();
        } else if (Util.getClassString(shapeFx).equals("triangle")) {
            System.out.println("dans le triangle de foussssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
            return ((Polygon) shapeFx).getPoints().get(1);
        } else {
            return 0;
        }
    }
//    public double getX2() {
//        if (Util.getClassString(shapeFx).equals("ligne")) {
//            return ((Line) shapeFx).getEndX();
//        } else {
//            return 0;
//        }
//    }
//
//    public double getY2() {
//        if (Util.getClassString(shapeFx).equals("ligne")) {
//            return ((Line) shapeFx).getEndY();
//        } else {
//            return 0;
//        }
//    }

    private void updatePathX(Path path, double x) {
        double reference = x - ((MoveTo) path.getElements().get(0)).getX();
        for (PathElement elem : path.getElements()) {
            if (elem instanceof MoveTo) {
                MoveTo m = (MoveTo) elem;
                m.setX(x);
            } else if (elem instanceof LineTo) {
                LineTo l = (LineTo) elem;
                l.setX(l.getX() + reference);
            }
        }
    }

    private void updatePathY(Path path, double y) {
        double reference = y - ((MoveTo) path.getElements().get(0)).getY();
        for (PathElement elem : path.getElements()) {
            if (elem instanceof MoveTo) {
                MoveTo m = (MoveTo) elem;
                m.setY(y);
            } else if (elem instanceof LineTo) {
                LineTo l = (LineTo) elem;
                l.setY(l.getY() + reference);
            }
        }
    }

    private void updateTriangleX(Polygon polygon, double x) {
        double reference = x - polygon.getPoints().get(0);
        polygon.getPoints().set(0, x);
        polygon.getPoints().set(2, polygon.getPoints().get(2) + reference);
        polygon.getPoints().set(4, polygon.getPoints().get(4) + reference);
    }

    private void updateTriangleY(Polygon polygon, double y) {
        double reference = y - polygon.getPoints().get(1);
        polygon.getPoints().set(1, y);
        polygon.getPoints().set(3, polygon.getPoints().get(3) + reference);
        polygon.getPoints().set(5, polygon.getPoints().get(5) + reference);
    }
}
