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
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import paintcommon.PointModel;
import paintcommon.ShapeEnum;
import paintcommon.shape.CircleModel;
import paintcommon.shape.LineModel;
import paintcommon.shape.PathModel;
import paintcommon.shape.RectangleModel;
import paintcommon.shape.ShapeBlockModel;
import paintcommon.shape.ShapeModel;
import paintcommon.shape.TriangleModel;
import paintproject.view.EditionView;

/**
 *
 * @author supeUse
 */
public class BlockForm extends Group {

    private double x;
    private double y;
    private List<ShapeModel> tempShapes;

    public BlockForm(ShapeBlockModel shapeModel) {
        update(shapeModel);
    }

    public BlockForm(ShapeModel shapeModel) {
        tempShapes = new ArrayList<>();
        tempShapes.add(shapeModel);
    }

    public void add(ShapeModel shapeModel) {
        tempShapes.add(shapeModel);
    }

    public void update(ShapeBlockModel shapeModel) {
        x = shapeModel.getX();
        y = shapeModel.getY();
        updateShapes(shapeModel.getShapes());
    }

    private void updateShapes(List<ShapeModel> shapes) {
        for (ShapeModel shape : shapes) {
            if (shape.getType() == ShapeEnum.ShapeBlock) {
                drawShapeBlock(((ShapeBlockModel) shape));
            } else if (shape.getType() == ShapeEnum.Rectangle) {
                drawRectangle((RectangleModel) shape);
            } else if (shape.getType() == ShapeEnum.Circle) {
                drawCircle((CircleModel) shape);
            } else if (shape.getType() == ShapeEnum.Line) {
                drawLine((LineModel) shape);
            } else if (shape.getType() == ShapeEnum.FreeHand) {
                drawFreeHand((PathModel) shape);
            } else if (shape.getType() == ShapeEnum.Triangle) {
                drawTriangle((TriangleModel) shape);
            }
        }
    }

    private void drawRectangle(RectangleModel rectangle) {
        System.out.println("PAINT_PROJECT CLIENT DRAW_RECTANGLE");
        System.out.println("" + rectangle.getId() + "++++++++++++++++++++++++++++++++++++++++++++");
//        if (shapeMap.containsKey(rectangle.getId())) {
//            Form form = shapeMap.get(rectangle.getId());
//            form.shapeModel = rectangle;
//            Rectangle rectangleFX = (Rectangle) form.shapeFx;
//            rectangleFX.setX(rectangle.getX());
//            rectangleFX.setY(rectangle.getY());
//            rectangleFX.setWidth(rectangle.getWidth());
//            rectangleFX.setHeight(rectangle.getHeight());
//            System.out.println("PAINT_PROJECT CLIENT DRAW_RECTANGLE contains");
//        } else {
        Rectangle rectangleFX = new Rectangle(rectangle.getX(),
                rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        rectangleFX.setFill(Color.web(rectangle.getFillColor()));
        rectangleFX.setStroke(Color.web(rectangle.getStrokeColor()));
        rectangleFX.setStrokeWidth(rectangle.getStrokeWidth());
        FormT form = new FormT(rectangleFX, rectangle,
                rectangle.getPoints().get(0).getXGap(), rectangle.getPoints().get(0).getYGap());
        this.getChildren().add(form);
        System.out.println("PAINT_PROJECT CLIENT DRAW_RECTANGLE not contains");
//        }
    }
    
    private void drawTriangle(TriangleModel triangle) {
        System.out.println("PAINT_PROJECT CLIENT DRAW_RECTANGLE");
        System.out.println("" + triangle.getId() + "++++++++++++++++++++++++++++++++++++++++++++");
        Polygon triangleFX = new Polygon(triangle.getDoubles());
        triangleFX.setFill(Color.web(triangle.getFillColor()));
        triangleFX.setStroke(Color.web(triangle.getStrokeColor()));
        triangleFX.setStrokeWidth(triangle.getStrokeWidth());
        FormT form = new FormT(triangleFX, triangle,
                triangle.getPoints().get(0).getXGap(), triangle.getPoints().get(0).getYGap());
        this.getChildren().add(form);
    }

    private void drawCircle(CircleModel circle) {
        System.out.println("PAINT_PROJECT CLIENT DRAW_Circle");
//        if (shapeMap.containsKey(circle.getId())) {
//            Form form = shapeMap.get(circle.getId());
//            form.shapeModel = circle;
//            Circle circleFX;
//            circleFX = (Circle) form.shapeFx;
//            circleFX.setCenterX(circle.getX());
//            circleFX.setCenterY(circle.getY());
//            System.out.println("PAINT_PROJECT CLIENT DRAW_CIRCLE contains");
//        } else {
        // crée un cercle 
        Circle circleFX = new Circle(circle.getX(),
                circle.getY(), circle.getRadiuslenght());
        circleFX.setFill(Color.web(circle.getFillColor()));
        circleFX.setStroke(Color.web(circle.getStrokeColor()));
        circleFX.setStrokeWidth(circle.getStrokeWidth());
        FormT form = new FormT(circleFX, circle,
                circle.getPoints().get(0).getXGap(), circle.getPoints().get(0).getYGap());
        // la dessine sur la feuille 
        this.getChildren().add(form);
        // ajoute associe  la forme au fx 
        System.out.println("PAINT_PROJECT CLIENT DRAW_CIRCLE not contains");
//        }
    }

    private void drawLine(LineModel line) {
        System.out.println("PAINT_PROJECT CLIENT DRAW_Line");
//        if (shapeMap.containsKey(line.getId())) {
//            Form form = shapeMap.get(line.getId());
//            form.shapeModel = line;
//            Line lineFX;
//            lineFX = (Line) form.shapeFx;
//            lineFX.setStartX(line.getStart().getX());
//            lineFX.setStartY(line.getStart().getY());
//            lineFX.setEndX(line.getEnd().getX());
//            lineFX.setEndY(line.getEnd().getY());
//            System.out.println("PAINT_PROJECT CLIENT DRAW_CIRCLE contains");
//        } else {
        // crée un cercle 
        Line lineFX = new Line(line.getStart().getX(),
                line.getStart().getY(),
                line.getEnd().getX(), line.getEnd().getY());
        lineFX.setFill(Color.web(line.getFillColor()));
        lineFX.setStroke(Color.web(line.getStrokeColor()));
        lineFX.setStrokeWidth(line.getStrokeWidth());
        lineFX.setFill(Color.BLACK);
        FormT form = new FormT(lineFX, line,
                line.getPoints().get(0).getXGap(), line.getPoints().get(0).getYGap(),
                line.getPoints().get(1).getXGap(), line.getPoints().get(1).getYGap());
        // la dessine sur la feuille 
        this.getChildren().add(form);
        // ajoute associe  la forme au fx 
        System.out.println("PAINT_PROJECT CLIENT DRAW_LINE not contains");
//        }
    }

    private void drawShapeBlock(ShapeBlockModel shapeBlock) {
//        if (shapeMap.containsKey(shapeBlock.getId())) {
//            Form form = shapeMap.get(shapeBlock.getId());
//            form.shapeModel = shapeBlock;
//            BlockForm shapeBlockFX = (BlockForm) form.shapeFx;
//            shapeBlockFX.update(shapeBlock);
//            System.out.println("PAINT_PROJECT CLIENT DRAW_RECTANGLE contains");
//        } else {
        BlockForm shapeBlockFX = new BlockForm(shapeBlock);
        //shapeBlockFX.setFill(Color.BLACK);
        FormT form = new FormT(shapeBlockFX, shapeBlock,
                shapeBlock.getPoints().get(0).getXGap(), shapeBlock.getPoints().get(0).getYGap());
        this.getChildren().add(form);
        System.out.println("PAINT_PROJECT CLIENT DRAW_RECTANGLE not contains");
//        }
    }

    private void drawFreeHand(PathModel freeHand) {
        int i = 0;
        Path pathFX = new Path();
        for (PointModel elem : freeHand.getPoints()) {
            if (i == 0) {
                MoveTo m = new MoveTo(elem.getX(), elem.getY());
                pathFX.getElements().add(m);
            } else {
                LineTo l = new LineTo(elem.getX(), elem.getY());
                pathFX.getElements().add(l);
            }
            i++;
        }
        i = 0;
        pathFX.setStroke(Color.web(freeHand.getStrokeColor()));
        pathFX.setStrokeWidth(freeHand.getStrokeWidth());
        FormT form = new FormT(pathFX, freeHand,
                freeHand.getPoints().get(0).getXGap(), freeHand.getPoints().get(0).getYGap());
        this.getChildren().add(form);
    }

//    private void drawFreeHand(PathModel freeHand) {
//        if (shapeMap.containsKey(freeHand.getId())) {
//            Form form = shapeMap.get(freeHand.getId());
//            form.shapeModel = freeHand;
//            FreeHandForm freeHandModelFX = (FreeHandForm) form.shapeFx;
//            freeHandModelFX.update(freeHand);
//            System.out.println("PAINT_PROJECT CLIENT DRAW_RECTANGLE contains");
//        } else {
//            FreeHandForm freeHandModelFX = new FreeHandForm(freeHand);
//            //freeHandModelFX.setFill(Color.BLACK);
//            Form form = new Form(freeHandModelFX, freeHand);
//            this.getChildren().add(form);
//            shapeMap.put(freeHand.getId(), form);
//            System.out.println("PAINT_PROJECT CLIENT DRAW_RECTANGLE not contains");
//        }
//    }
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
        for (Node node : this.getChildren()) {
            ((FormT) node).setX(x);
        }
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
        for (Node node : this.getChildren()) {
            ((FormT) node).setY(y);
        }
    }

}
