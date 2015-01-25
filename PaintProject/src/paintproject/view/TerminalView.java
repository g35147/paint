/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paintproject.view;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Transform;
import javafx.stage.Screen;
import paintcommon.FigureModel;
import paintcommon.PointModel;
import paintcommon.ShapeEnum;
import paintcommon.shape.ShapeBlockModel;
import paintcommon.shape.ShapeModel;

/**
 *
 * @author supeUse
 */
public class TerminalView extends AbstractView {

    //TextArea text;
    ScrollPane scroll;
    TextFlow text;

    public TerminalView(double width, double height) throws NotBoundException, MalformedURLException, RemoteException {
        super("Terminal", width, height);
//        text = new TextArea();
        text = new TextFlow();
        scroll = new ScrollPane(text);
        text.getStyleClass().add("text-flow");
        //text.setEditable(false);
        scroll.setPrefSize(width, height);
        text.setPrefWidth(Region.USE_COMPUTED_SIZE);
        if (text.getWidth() < scroll.getWidth()) {
            text.setPrefWidth(scroll.getWidth());
        }
        //text.setPrefSize(scroll   .getPrefViewportWidth(), scroll.getPrefViewportHeight());
        root.getChildren().add(scroll);
        sceneListeners();
    }

    public TerminalView(double width, double height, String ip) throws RemoteException, NotBoundException, MalformedURLException {
        this(width, height);
        super.initModel(ip);
    }

    private void sceneListeners() {
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                scroll.setPrefWidth(newSceneWidth.doubleValue());
                text.setPrefWidth(Region.USE_COMPUTED_SIZE);
                if (text.getWidth() < scroll.getWidth()) {
                    text.setPrefWidth(scroll.getWidth());
                }
            }
        });

        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                scroll.setPrefHeight(newValue.doubleValue());
            }
        });
    }

    @Override
    public void notifieChangement() {

        if (Platform.isFxApplicationThread()) {
            draw();
        } else {
            Platform.runLater(new Runnable() {

                @Override
                public void run() {
                    draw();
                }
            });
        }
    }

//    public void draw() {
//        text.clear();
//        clientUpDate(getModel().getState());
//    }
    public void draw() {
        text.getChildren().clear();
        clientUpDate(getModel().getState());
        for (Node tex : text.getChildren()) {
            ((Text) tex).getStyleClass().add("text");
            if (((Text) tex).getFill() == Color.BLACK) {
                ((Text) tex).setFill(Color.WHITE);
            }
        }
    }

//    private void clientUpDate(FigureModel figure) {
//        text.appendText("Figure : " + figure.name + "\n");
//        drawShapes(figure.shapes, "");
//        text.appendText("Fin");
//    }
    private void clientUpDate(FigureModel figure) {
        text.getChildren().add(new Text("Figure : " + figure.name + "\n"));
        drawShapes(figure.shapes, "");
        text.getChildren().add(new Text("Fin Figure"));
    }

    private void drawShapes(List<ShapeModel> shapes, String indent) {
        for (ShapeModel shape : shapes) {
            drawShape(shape, indent);
        }
    }

//    private void drawShape(ShapeModel shape, String indent) {
//        text.appendText(indent + " |\n");
//        text.appendText(indent + " |  " + shape.getType() + " :\n");
//        text.appendText(indent + " |   |  Id : " + shape.getId() + "\n");
//        text.appendText(indent + " |   |  \n");
//        text.appendText(indent + " |   |  gap : " + shape.getPoints().get(0).tiedPoint + shape.getPoints().get(0).getXGap() + "," + shape.getPoints().get(0).getYGap() + "\n");
//        drawPoints(shape.getPoints(), (indent + " |   |  "));
//        if (shape.getType() == ShapeEnum.ShapeBlock) {
//            text.appendText(indent + " |   |  Liste des formes dans ce block :\n");
//            drawShapes(((ShapeBlockModel) shape).getShapes(), (indent + " |   |  "));
//            text.appendText(indent + " |   |  Fin\n");
//        }
//        text.appendText(indent + " |  Fin\n");
//    }
    private void drawShape(ShapeModel shape, String indent) {
        //text.
        text.getChildren().add(new Text(indent + " |\n"));
        text.getChildren().add(new Text(indent + " |  "));
        Text nom = new Text(shape.getType().toString());
        nom.setFill(getColor(shape.getType()));
        text.getChildren().add(nom);
        text.getChildren().add(new Text(" :\n"));
        text.getChildren().add(new Text(indent + " |   |  Id : " + shape.getId() + "\n"));
        text.getChildren().add(new Text(indent + " |   |  gap "+ shape.getPoints().get(0).getXGap() + ", " +shape.getPoints().get(0).getYGap() + "\n"));
        drawPoints(shape.getPoints(), (indent + " |   |  "));
        if (shape.getType() == ShapeEnum.ShapeBlock) {
            text.getChildren().add(new Text(indent + " |   |  Liste des formes dans ce block :\n"));
            drawShapes(((ShapeBlockModel) shape).getShapes(), (indent + " |   |  "));
            text.getChildren().add(new Text(indent + " |   |  Fin Liste formes\n"));
        }
        text.getChildren().add(new Text(indent + " |  "));
        Text fin = new Text("Fin " + shape.getType() + "\n");
        fin.setFill(getColor(shape.getType()));
        text.getChildren().add(fin);
    }

    private Color getColor(ShapeEnum anEnum) {
        switch (anEnum) {
            //case rectanl cer line free 
            case Rectangle:
                return Color.web("ffb366");
            case Circle:
                return Color.SALMON;
            case Line:
                return Color.LIGHTGREEN;
            case FreeHand:
                return Color.LAVENDER;
            case Triangle:
               return Color.web("b399ff");
            case ShapeBlock:
                return Color.LIGHTBLUE;
            default:
                return null;
        }
    }

//    private void drawPoints(List<PointModel> points, String indent) {
//        text.appendText(indent + "Liste des points (id, (x, y)) :\n"));
//        text.appendText(indent + " |  ");
//        for (PointModel point : points) {
//            text.appendText(" (" + point.getId() + ", " + point + ", " + point.tiedPoint + ", "+ point.getXGap() + ", " + point.getYGap() + ") ");
//        }
//        text.appendText("\n");
//        text.appendText(indent + "Fin\n");
//    }
    private void drawPoints(List<PointModel> points, String indent) {
        text.getChildren().add(new Text(indent + "Liste des points (id, (x, y)) :\n"));
        text.getChildren().add(new Text(indent + " |  ["));
        for (PointModel point : points) {
            text.getChildren().add(new Text(" (" + point + ") "));
        }
        text.getChildren().add(new Text("]\n"));
        text.getChildren().add(new Text(indent + "Fin Liste points\n"));
    }
}
