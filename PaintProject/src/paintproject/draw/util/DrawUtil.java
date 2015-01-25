/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paintproject.draw.util;

import java.util.List;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import paintcommon.FigureModel;
import paintcommon.PointModel;
import paintcommon.ShapeEnum;
import paintcommon.shape.CircleModel;
import paintcommon.shape.PathModel;
import paintcommon.shape.LineModel;
import paintcommon.shape.RectangleModel;
import paintcommon.shape.ShapeBlockModel;
import paintcommon.shape.ShapeModel;
import paintcommon.shape.TriangleModel;
import paintproject.BlockForm;
import paintproject.FeuilleDessin;
import paintproject.view.EditionView;
import paintproject.Form;
import paintproject.view.AbstractView;

/**
 *
 * @author supeUse
 */
public class DrawUtil {

    public AbstractView view;
    public FeuilleDessin feuille;
    public boolean isEdition;

    public DrawUtil(AbstractView view, FeuilleDessin feuille, boolean isEdition) {
        this.view = view;
        this.feuille = feuille;
        this.isEdition = isEdition;
    }

    
    public void run() {
        
        if (Platform.isFxApplicationThread()) {
            // si c'est déjà en FX 
            clientUpDate(view.getModel().getState());
        } else {
            // sinon le transfome en Fx 
            Platform.runLater(new Runnable() {

                @Override
                public void run() {
                    clientUpDate(view.getModel().getState());
                }
            });
        }
    }

    private void clientUpDate(FigureModel figure) {
        System.out.println("PAINT_PROJECT CLIENT CLIENT_UPDATE" + figure);
        feuille.getChildren().clear();
        feuille.getChildren().add(feuille.fond);
        drawShape(figure.shapes);
    }

    private void drawShape(List<ShapeModel> shapes) {
        System.out.println("PAINT_PROJECT CLIENT DRAW_SHAPE");
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
        Rectangle rectangleFX = new Rectangle(rectangle.getX(),
                rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        rectangleFX.setFill(Color.web(rectangle.getFillColor()));
        rectangleFX.setStroke(Color.web(rectangle.getStrokeColor()));
        rectangleFX.setStrokeWidth(rectangle.getStrokeWidth());
        Form form = new Form(rectangleFX, rectangle);
        feuille.getChildren().add(form);
        System.out.println("PAINT_PROJECT CLIENT DRAW_RECTANGLE not contains");
        if (isEdition) {
            ((EditionView) view).formListeners(form);
        }
    }
    
    private void drawTriangle(TriangleModel triangle) {
        System.out.println("PAINT_PROJECT CLIENT DRAW_RECTANGLE");
        System.out.println("" + triangle.getId() + "++++++++++++++++++++++++++++++++++++++++++++");
        Polygon triangleFX = new Polygon(triangle.getDoubles());
        triangleFX.setFill(Color.web(triangle.getFillColor()));
        triangleFX.setStroke(Color.web(triangle.getStrokeColor()));
        triangleFX.setStrokeWidth(triangle.getStrokeWidth());
        Form form = new Form(triangleFX, triangle);
        feuille.getChildren().add(form);
        System.out.println("PAINT_PROJECT CLIENT DRAW_RECTANGLE not contains");
        if (isEdition) {
            ((EditionView) view).formListeners(form);
        }
    }

    private void drawCircle(CircleModel circle) {
        System.out.println("PAINT_PROJECT CLIENT DRAW_Circle");
        // crée un cercle 
        Circle circleFX = new Circle(circle.getX(),
                circle.getY(), circle.getRadiuslenght());
        circleFX.setFill(Color.web(circle.getFillColor()));
        circleFX.setStroke(Color.web(circle.getStrokeColor()));
        circleFX.setStrokeWidth(circle.getStrokeWidth());
        Form form = new Form(circleFX, circle);
        // la dessine sur la feuille 
        feuille.getChildren().add(form);
        // ajoute associe  la forme au fx 
        System.out.println("PAINT_PROJECT CLIENT DRAW_CIRCLE not contains");
        if (isEdition) {
            ((EditionView) view).formListeners(form);
        }
    }

    private void drawLine(LineModel line) {
        System.out.println("PAINT_PROJECT CLIENT DRAW_Line");
        // crée un cercle 
        Line lineFX = new Line(line.getX(),
                line.getY(),
                line.getEnd().getX(), line.getEnd().getY());
        lineFX.setFill(Color.web(line.getFillColor()));
        lineFX.setStroke(Color.web(line.getStrokeColor()));
        lineFX.setStrokeWidth(line.getStrokeWidth());
        Form form = new Form(lineFX, line);
        form.setEcart(line.getEnd().getXGap(), line.getEnd().getYGap());
        // la dessine sur la feuille 
        feuille.getChildren().add(form);
        // ajoute associe  la forme au fx 
        System.out.println("PAINT_PROJECT CLIENT DRAW_LINE not contains");
        if (isEdition) {
            ((EditionView) view).formListeners(form);
        }

    }

    private void drawShapeBlock(ShapeBlockModel shapeBlock) {
        BlockForm shapeBlockFX = new BlockForm(shapeBlock);
        Form form = new Form(shapeBlockFX, shapeBlock);
        feuille.getChildren().add(form);
        System.out.println("PAINT_PROJECT CLIENT DRAW_RECTANGLE not contains");
        if (isEdition) {
            ((EditionView) view).formListeners(form);
        }
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
        Form form = new Form(pathFX, freeHand);
        feuille.getChildren().add(form);
        System.out.println("PAINT_PROJECT CLIENT DRAW_RECTANGLE not contains");
        if (isEdition) {
            ((EditionView) view).formListeners(form);
        }
    }
//    public static void run() {
//        view.setState(view.getModel().getState());
//        if (Platform.isFxApplicationThread()) {
//            // si c deja en  FX 
//            clientUpDate(view.getState());
//        } else {
//            // transfome en Fx 
//            Platform.runLater(new Runnable() {
//
//                @Override
//                public void run() {
//                    clientUpDate(view.getState());
//                }
//            });
//        }
//    }
//
//    private static void clientUpDate(FigureModel figure) {
//        System.out.println("PAINT_PROJECT CLIENT CLIENT_UPDATE" + figure);
//        feuille.getChildren().clear();
//        drawShapes(figure.shapes);
//        removeShapes();
//    }
//
//    private static void drawShapes(List<ShapeModel> shapes) {
//        System.out.println("PAINT_PROJECT CLIENT DRAW_SHAPE");
//        for (ShapeModel shape : shapes) {
//            if (shape.getType() == ShapeEnum.ShapeBlock) {
//                drawShapeBlock(((ShapeBlockModel) shape));
//            } else if (shape.getType() == ShapeEnum.Rectangle) {
//                drawRectangle((RectangleModel) shape);
//            } else if (shape.getType() == ShapeEnum.Circle) {
//                drawCircle((CircleModel) shape);
//            } else if (shape.getType() == ShapeEnum.Line) {
//                drawLine((LineModel) shape);
//            } else if (shape.getType() == ShapeEnum.FreeHand) {
//                drawFreeHand((PathModel) shape);
//            }
//        }
//    }
//
//    private static void removeShapes() {
//        for (Long key : view.getDrawed().keySet()) {
//            if (!view.getDrawed().get(key)) {
//                view.getDrawed().remove(key);
//                feuille.getChildren().remove(view.getShapeMap().get(key));
//                view.getShapeMap().remove(key);
//            }
//        }
//        drawedReset();
//    }
//
//    private static void drawedReset() {
//        for (Long key : view.getDrawed().keySet()) {
//            view.getDrawed().put(key, false);
//        }
//    }
//
//    private static void drawRectangle(RectangleModel rectangle) {
//        System.out.println("PAINT_PROJECT CLIENT DRAW_RECTANGLE");
//        if (view.getShapeMap().containsKey(rectangle.getId())) {
//            Form form = view.getShapeMap().get(rectangle.getId());
//            form.shapeModel = rectangle;
//            Rectangle rectangleFX = (Rectangle) form.shapeFx;
//            rectangleFX.setX(rectangle.getX());
//            rectangleFX.setY(rectangle.getY());
//            rectangleFX.setWidth(rectangle.getWidth());
//            rectangleFX.setHeight(rectangle.getHeight());
//            rectangleFX.setFill(Color.web(rectangle.getFillColor()));
//            rectangleFX.setStroke(Color.web(rectangle.getStrokeColor()));
//            rectangleFX.setStrokeWidth(rectangle.getStrokeWidth());
//            view.getDrawed().put(rectangle.getId(), true);
//            System.out.println("PAINT_PROJECT CLIENT DRAW_RECTANGLE contains");
//        } else {
//            Rectangle rectangleFX = new Rectangle(rectangle.getX(),
//                    rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
//            System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG" + rectangle.getFillColor().toString());
//            rectangleFX.setFill(Color.web(rectangle.getFillColor()));
//            rectangleFX.setStroke(Color.web(rectangle.getStrokeColor()));
//            rectangleFX.setStrokeWidth(rectangle.getStrokeWidth());
//            Form form = new Form(rectangleFX, rectangle);
//            feuille.getChildren().add(form);
//            view.getShapeMap().put(rectangle.getId(), form);
//            view.getDrawed().put(rectangle.getId(), true);
//            System.out.println("PAINT_PROJECT CLIENT DRAW_RECTANGLE not contains");
//            System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG");
//            view.formListeners(form);
//        }
//    }
//
//    private static void drawCircle(CircleModel circle) {
//        System.out.println("PAINT_PROJECT CLIENT DRAW_Circle");
//        if (view.getShapeMap().containsKey(circle.getId())) {
//            Form form = view.getShapeMap().get(circle.getId());
//            form.shapeModel = circle;
//            Circle circleFX;
//            circleFX = (Circle) form.shapeFx;
//            circleFX.setCenterX(circle.getX());
//            circleFX.setCenterY(circle.getY());
//            circleFX.setFill(Color.web(circle.getFillColor()));
//            circleFX.setStroke(Color.web(circle.getStrokeColor()));
//            circleFX.setStrokeWidth(circle.getStrokeWidth());
//            view.getDrawed().put(circle.getId(), true);
//            System.out.println("PAINT_PROJECT CLIENT DRAW_CIRCLE contains");
//        } else {
//            // crée un cercle 
//            Circle circleFX = new Circle(circle.getX(),
//                    circle.getY(), circle.getRadiuslenght());
//            circleFX.setFill(Color.web(circle.getFillColor()));
//            circleFX.setStroke(Color.web(circle.getStrokeColor()));
//            circleFX.setStrokeWidth(circle.getStrokeWidth());
//            Form form = new Form(circleFX, circle);
//            // la dessine sur la feuille 
//            feuille.getChildren().add(form);
//            // ajoute associe  la forme au fx 
//            view.getShapeMap().put(circle.getId(), form);
//            view.getDrawed().put(circle.getId(), true);
//            System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG");
//            System.out.println("PAINT_PROJECT CLIENT DRAW_CIRCLE not contains");
//            view.formListeners(form);
//        }
//    }
//
//    private static void drawLine(LineModel line) {
//        System.out.println("PAINT_PROJECT CLIENT DRAW_Line");
//        if (view.getShapeMap().containsKey(line.getId())) {
//            Form form = view.getShapeMap().get(line.getId());
//            form.shapeModel = line;
//            Line lineFX;
//            lineFX = (Line) form.shapeFx;
//            lineFX.setStartX(line.getStart().getX());
//            lineFX.setStartY(line.getStart().getY());
//            lineFX.setEndX(line.getEnd().getX());
//            lineFX.setEndY(line.getEnd().getY());
//            lineFX.setStroke(Color.web(line.getStrokeColor()));
//            lineFX.setStrokeWidth(line.getStrokeWidth());
//            view.getDrawed().put(line.getId(), true);
//            System.out.println("PAINT_PROJECT CLIENT DRAW_CIRCLE contains");
//        } else {
//            // crée un cercle 
//            Line LineFX = new Line(line.getStart().getX(),
//                    line.getStart().getY(),
//                    line.getEnd().getX(), line.getEnd().getY());
//            LineFX.setStroke(Color.web(line.getStrokeColor()));
//            LineFX.setStrokeWidth(line.getStrokeWidth());
//            Form form = new Form(LineFX, line);
//            // la dessine sur la feuille 
//            feuille.getChildren().add(form);
//            // ajoute associe  la forme au fx 
//            view.getShapeMap().put(line.getId(), form);
//            view.getDrawed().put(line.getId(), true);
//            System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG");
//            System.out.println("PAINT_PROJECT CLIENT DRAW_LINE not contains");
//            view.formListeners(form);
//        }
//    }
//
//    private static void drawShapeBlock(ShapeBlockModel shapeBlock) {
//        if (view.getShapeMap().containsKey(shapeBlock.getId())) {
//            Form form = view.getShapeMap().get(shapeBlock.getId());
//            form.shapeModel = shapeBlock;
//            BlockForm shapeBlockFX = (BlockForm) form.shapeFx;
//            shapeBlockFX.update(shapeBlock);
//            view.getDrawed().put(shapeBlock.getId(), true);
//            System.out.println("PAINT_PROJECT CLIENT DRAW_RECTANGLE contains");
//        } else {
//            BlockForm shapeBlockFX = new BlockForm(shapeBlock);
//            //shapeBlockFX.setFill(Color.BLACK);
//            Form form = new Form(shapeBlockFX, shapeBlock);
//            feuille.getChildren().add(form);
//            view.getShapeMap().put(shapeBlock.getId(), form);
//            view.getDrawed().put(shapeBlock.getId(), true);
//            System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG");
//            System.out.println("PAINT_PROJECT CLIENT DRAW_RECTANGLE not contains");
//            view.formListeners(form);
//        }
//    }
//
//    private static void drawFreeHand(PathModel freeHand) {
//        if (view.getShapeMap().containsKey(freeHand.getId())) {
//            int i = 0;
//            Path pathFX = new Path();
//            for (PointModel elem : freeHand.getPoints()) {
//                if (i == 0) {
//                    MoveTo m = new MoveTo(elem.getX(), elem.getY());
//                    pathFX.getElements().add(m);
//                } else {
//                    LineTo l = new LineTo(elem.getX(), elem.getY());
//                    pathFX.getElements().add(l);
//                }
//                i++;
//            }
//            i = 0;
//            pathFX.setStroke(Color.web(freeHand.getStrokeColor()));
//            pathFX.setStrokeWidth(freeHand.getStrokeWidth());
//            Form form = view.getShapeMap().get(freeHand.getId());
//            form.getChildren().remove(form.shapeFx);
//            form.shapeModel = freeHand;
//            form.shapeFx = pathFX;
//            form.getChildren().add(form.shapeFx);
//            view.getDrawed().put(freeHand.getId(), true);
//            System.out.println("PAINT_PROJECT CLIENT DRAW_RECTANGLE contains");
//        } else {
////            FreeHandForm freeHandModelFX = new FreeHandForm(freeHand);
////            freeHandModelFX.setFill(Color.BLACK);
////            Form form = new Form(freeHandModelFX, freeHand);
////            feuille.getChildren().add(form);
////            view.getShapeMap().put(freeHand.getId(), form);
////            view.getDrawed().put(freeHand.getId(), true);
////            System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG");
////            System.out.println("PAINT_PROJECT CLIENT DRAW_RECTANGLE not contains");
////            view.formListeners(form);
//            //JavaFXation
//            int i = 0;
//            Path pathFX = new Path();
//            for (PointModel elem : freeHand.getPoints()) {
//                if (i == 0) {
//                    MoveTo m = new MoveTo(elem.getX(), elem.getY());
//                    pathFX.getElements().add(m);
//                } else {
//                    LineTo l = new LineTo(elem.getX(), elem.getY());
//                    pathFX.getElements().add(l);
//                }
//                i++;
//            }
//            i = 0;
//            pathFX.setStroke(Color.web(freeHand.getStrokeColor()));
//            pathFX.setStrokeWidth(freeHand.getStrokeWidth());
//            Form form = new Form(pathFX, freeHand);
//            feuille.getChildren().add(form);
//            view.getShapeMap().put(freeHand.getId(), form);
//            view.getDrawed().put(freeHand.getId(), true);
//            System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG");
//            System.out.println("PAINT_PROJECT CLIENT DRAW_RECTANGLE not contains");
//            view.formListeners(form);
//        }
//    }

}
