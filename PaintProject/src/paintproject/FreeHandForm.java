/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paintproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.effect.Light;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import paintcommon.PointModel;
import paintcommon.ShapeEnum;
import paintcommon.shape.CircleModel;
import paintcommon.shape.PathModel;
import paintcommon.shape.LineModel;
import paintcommon.shape.RectangleModel;
import paintcommon.shape.ShapeBlockModel;
import paintcommon.shape.ShapeModel;

/**
 *
 * @author supeUse
 */
public class FreeHandForm extends Group {
    
    private HashMap<Long, Rectangle> pointMap;
    private double x;
    private double y;
    private List<PointModel> tempPoints;
    
    public FreeHandForm(PathModel shapeModel) {
        pointMap = new HashMap<>();
        update(shapeModel);
    }
    
    public FreeHandForm(double x, double y) {
        tempPoints = new ArrayList<>();
        tempPoints.add(new PointModel(x, y));
    }
    
    public void add(double x, double y) {
        tempPoints.add(new PointModel(x, y));
    }
    
    public void update(PathModel shapeModel) {
        x = shapeModel.getX();
        y = shapeModel.getY();
        updateShapes(shapeModel.getPoints());
    }

    private void updateShapes(List<PointModel> points) {
        for (PointModel point : points) {
            drawPoint(point);
        }
    }
    
    private void drawPoint(PointModel point) {
        System.out.println("PAINT_PROJECT CLIENT DRAW_RECTANGLE");
        System.out.println("" + point.getId() + "++++++++++++++++++++++++++++++++++++++++++++");
        if (pointMap.containsKey(point.getId())) {
            Rectangle rectangleFX = pointMap.get(point.getId());
            rectangleFX.setX(point.getX());
            rectangleFX.setY(point.getY());
            System.out.println("PAINT_PROJECT CLIENT DRAW_RECTANGLE contains");
        } else {
            Rectangle rectangleFX = new Rectangle(point.getX(),
                    point.getY(), 1, 1);
            rectangleFX.setFill(Color.BLACK);
            this.getChildren().add(rectangleFX);
            pointMap.put(point.getId(), rectangleFX);
            System.out.println("PAINT_PROJECT CLIENT DRAW_RECTANGLE not contains");
        }
    }

    /**
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(double y) {
        this.y = y;
    }

}
