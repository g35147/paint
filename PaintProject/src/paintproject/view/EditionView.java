/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this feuille.template file, choose Tools | Templates
 * and open the feuille.template in the editor.
 */
package paintproject.view;

import com.sun.prism.GraphicsPipeline;
import exception.PaintException;
import java.io.File;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import manager.Draw;
import manager.FigureList;
import manager.Fill;
import manager.Move;
import manager.Redo;
import manager.Save;
import manager.UnGroup;
import manager.Undo;
import paintcommon.FigureModel;
import paintcommon.PointModel;
import paintcommon.ShapeEnum;
import paintcommon.shape.CircleModel;
import paintcommon.shape.LineModel;
import paintcommon.shape.PathModel;
import paintcommon.shape.RectangleModel;
import paintcommon.shape.ShapeBlockModel;
import paintcommon.shape.ShapeModel;
import paintcommon.shape.TriangleModel;
import paintproject.DrawMode;
import paintproject.BarreTaches;
import paintproject.FeuilleDessin;
import paintproject.Form;
import paintproject.draw.util.DrawUtil;

/**
 *
 * @author G38772
 */
public class EditionView extends AbstractView /*Application implements PaintVue*/ /*Application implements PaintVue*/ {

    private FeuilleDessin feuille;
    private BarreTaches barre;
    private File file;
    private Move currentMove;
    private List<ShapeModel> currentShapesList;
    private List<Node> currentFXShapes;

    public EditionView(double width, double height) {
        super("Edition View", width, height);
        // initElements
        feuille = new FeuilleDessin(1000, 450);
        barre = new BarreTaches(1000, 50, this);
        // ajout listeners
        addBarreListeners();
        //addFeuilleListeners();
        // ajout des panel à la racine dans l'ordre !!!
        root.getChildren().add(feuille);
        root.getChildren().add(barre);
        // repositionnement de la barre
        barre.setTranslateY(450);
        // initialisation model
        sceneListeners();
        this.setOnShown(onShownListener());
        // switch d'évenement ( le prof m'a dit de faire comme ca
        updateListener(DrawMode.move);
    }

    private EventHandler<WindowEvent> onShownListener() {
        return new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent event) {
                feuille.fond.setWidth(scene.getWidth());
                barre.setPrefWrapLength(scene.getWidth());
                feuille.fond.setHeight(scene.getHeight() - (barre.getHeight() / 2) + 5);
                barre.setTranslateY(scene.getHeight() - (barre.getHeight() / 2) + 5);
            }
        };
    }

    public List<ShapeModel> getCurrentShapesList() {
        return currentShapesList;
    }

    public void setCurrentShapesList(List<ShapeModel> currentShapesList) {
        this.currentShapesList = currentShapesList;
    }

    private void sceneListeners() {
        this.getScene().widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                getFeuille().fond.setWidth(newSceneWidth.doubleValue());
//                  barre.fond.setWidth(newSceneWidth.doubleValue());
                barre.setPrefWrapLength(newSceneWidth.doubleValue());
                getFeuille().fond.setHeight(scene.getHeight() - barre.getHeight());
                barre.setTranslateY(scene.getHeight() - barre.getHeight());
                System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBb");
            }
        });

        this.getScene().heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println(newValue);
                getFeuille().fond.setHeight(newValue.doubleValue() - barre.getHeight());
                barre.setTranslateY(newValue.doubleValue() - barre.getHeight());

                System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa");
            }
        });
    }

    public EditionView(double width, double height, String ip) throws RemoteException, NotBoundException, MalformedURLException {
        this(width, height);
        super.initModel(ip);
    }

//    public void widthChange(double width) {
//        feuille.fond.setWidth(width);
//        barre.fond.setWidth(width);
//    }
//
//    public void heightChange(double height) {
//        feuille.fond.setHeight(height - 50);
//        barre.setTranslateY(height - 49);
//    }
    @Override
    public void notifieChangement() {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        new DrawUtil(this, feuille, true).run();
        charged = true;
    }

    public void formListeners(Form form) {
        form.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                if (barre.drawMode == DrawMode.fill) {
                    System.out.println("fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff" + form.shapeModel.getFillColor() + "-" + barre.fillColor.toString());
                    getModel().setState(new Fill(form.shapeModel, form.shapeModel.getFillColor(), barre.fillColor.toString()));
                    System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm" + form.shapeModel.getFillColor() + "-" + barre.fillColor.toString());
                } else if (barre.drawMode == DrawMode.group && currentShapesList != null) {
                    currentShapesList.add(form.shapeModel);
                    currentFXShapes.add(form);
                    form.setEffect(new DropShadow(10, Color.ORANGERED));
                } else if (barre.drawMode == DrawMode.ungroup) {
                    List<ShapeModel> shapesList = ((ShapeBlockModel) form.shapeModel).getShapes();
                    getModel().setState(new UnGroup(form.shapeModel, shapesList.toArray(new ShapeModel[0])));
                }
            }
        });

        form.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (barre.drawMode == DrawMode.move) {
                    if (form.shapeModel.getType() == ShapeEnum.ShapeBlock) {
                        form.setEffect(new DropShadow(10, Color.ORANGERED));
                    }
                    currentMove = new Move(form.shapeModel);
                    feuille.ecartX = event.getX() - form.getX();
                    feuille.ecartY = event.getY() - form.getY();
                    feuille.getChildren().remove(form);
                    feuille.getChildren().add(form);
                }
            }
        });

        form.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (barre.drawMode == DrawMode.move) {
                    form.setX(event.getX() - feuille.ecartX);
                    form.setY(event.getY() - feuille.ecartY);
                    if (form.shapeFx instanceof Polygon) {
                        System.out.println(form.shapeFx);
                    }
                }
            }
        });

        form.setOnMouseReleased(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (barre.drawMode == DrawMode.move) {
                    currentMove.setNewPosition(form.getX(), form.getY());
                    getModel().setState(currentMove);
                    System.out.println("PAINT_PROJECT CLIENT HANDLE MOUSE_RELEASED");
                    System.out.println("PAINT_PROJECT CLIENT HANDLE MOUSE_RELEASED " + form.shapeModel.getX() + " " + form.shapeModel.getY());
                    System.out.println("PAINT_PROJECT CLIENT HANDLE MOUSE_RELEASED " + form.getX() + " " + form.getY());
                    System.out.println("PAINT_PROJECT CLIENT HANDLE MOUSE_RELEASED " + form.shapeModel.getX() + " " + form.shapeModel.getY());
                }
            }
        });
    }

//    private void addFeuilleListeners() {
//        feuille.setOnMousePressed(pressedListener());
//        feuille.setOnMouseDragged(draggedListener());
//        feuille.setOnMouseReleased(releasedListener());
//    }
    public final void updateListener(DrawMode action) {
        feuille.setOnMousePressed(null);
        feuille.setOnMouseDragged(null);
        feuille.setOnMouseReleased(null);
        switch (action) {
            case circle:
                System.out.println("UPDATELISTENER action circle --------------------------------------------------");
                feuille.setOnMousePressed(pressedListenerCircle());
                feuille.setOnMouseDragged(draggedListenerCircle());
                feuille.setOnMouseReleased(releasedListenerCircle());
                break;
            case rectangle:
                System.out.println("UPDATELISTENER action rectangle --------------------------------------------------");
                feuille.setOnMousePressed(pressedListenerRectangle());
                feuille.setOnMouseDragged(draggedListenerRectangle());
                feuille.setOnMouseReleased(releasedListenerRectangle());
                break;
            case triangle:
                System.out.println("UPDATELISTENER action rectangle --------------------------------------------------");
                feuille.setOnMousePressed(pressedListenerTriangle());
                feuille.setOnMouseDragged(draggedListenerTriangle());
                feuille.setOnMouseReleased(releasedListenerTriangle());
                break;
            case line:
                System.out.println("UPDATELISTENER action line --------------------------------------------------");
                feuille.setOnMousePressed(pressedListenerLine());
                feuille.setOnMouseDragged(draggedListenerLine());
                feuille.setOnMouseReleased(releasedListenerLine());
                break;
            case move:
                System.out.println("UPDATELISTENER action move --------------------------------------------------");
                feuille.setOnMousePressed(null);
                feuille.setOnMouseDragged(null);
                feuille.setOnMouseReleased(null);
                break;
            case free:
                feuille.setOnMousePressed(pressedListenerFreeHand());
                feuille.setOnMouseDragged(draggedListenerFreeHand());
                feuille.setOnMouseReleased(releasedListenerFreeHand());
                break;
            case fill:
                feuille.setOnMousePressed(null);
                feuille.setOnMouseDragged(null);
                feuille.setOnMouseReleased(null);
                break;
            case group:
                feuille.setOnMousePressed(null);
                feuille.setOnMouseDragged(null);
                feuille.setOnMouseReleased(null);
                break;
        }
    }

    // Ecouteur de cercle
    private EventHandler<MouseEvent> pressedListenerCircle() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                feuille.tempX = event.getX();
                feuille.tempY = event.getY();
                if (getFeuille().circleTemp == null) {
                    feuille.resetTempCircle(event.getX(), event.getY(), 0, barre.strokeColor, barre.fillColor, barre.tailleTrait);
                    getFeuille().getChildren().add(getFeuille().circleTemp);
                }
            }
        };
    }

    private EventHandler<MouseEvent> draggedListenerCircle() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (getFeuille().circleTemp != null) {
                    getFeuille().radius = (int) Math.max(Math.abs(event.getX() - getFeuille().circleTemp.getCenterX()), Math.abs(event.getY() - getFeuille().circleTemp.getCenterY()));
                    getFeuille().circleTemp.setRadius(getFeuille().radius);
                }
            }
        };
    }

    private EventHandler<MouseEvent> releasedListenerCircle() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    CircleModel circle = new CircleModel(new PointModel(getFeuille().tempX, getFeuille().tempY), getFeuille().radius);
                    
                    circle.setFillColor(barre.fillColor.toString().substring(2, 8));
                    System.out.println("couleur : ");
                    System.out.println(barre.fillColor);
                    System.out.println(barre.fillColor.toString());
                    circle.setStrokeColor(barre.strokeColor.toString().substring(2, 8));
                    circle.setStrokeWidth(barre.tailleTrait);
                    getFeuille().getChildren().remove(getFeuille().circleTemp);
                    getModel().setState(new Draw(circle));
                    feuille.circleTemp = null;
                    feuille.tempX = 0;
                    feuille.tempY = 0;
                } catch (PaintException ex) {
                    Logger.getLogger(EditionView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
    }

    // Ecouteur de rectangle
    private EventHandler<MouseEvent> pressedListenerRectangle() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("RECEUH");
                feuille.tempX = event.getX();
                feuille.tempY = event.getY();
                if (getFeuille().rectTemp == null) {
                    getFeuille().resetTempRectangle(getFeuille().tempX, getFeuille().tempY, barre.strokeColor, barre.fillColor, barre.tailleTrait);
                    getFeuille().getChildren().add(getFeuille().rectTemp);
                }
            }
        };
    }

    private EventHandler<MouseEvent> draggedListenerRectangle() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (getFeuille().rectTemp != null) {
                    // En haut à gauche
                    if (event.getX() - getFeuille().tempX <= 0 && event.getY() - getFeuille().tempY <= 0) {
                        getFeuille().rectTemp.setX(event.getX());
                        getFeuille().rectTemp.setY(event.getY());
                        getFeuille().rectTemp.setWidth(Math.abs(event.getX() - getFeuille().tempX));
                        getFeuille().rectTemp.setHeight(Math.abs(event.getY() - getFeuille().tempY));
                        // En haut à droite
                    } else if (event.getX() - getFeuille().tempX >= 0 && event.getY() - getFeuille().tempY <= 0) {
                        getFeuille().rectTemp.setY(event.getY());
                        getFeuille().rectTemp.setWidth(Math.abs(event.getX() - getFeuille().tempX));
                        getFeuille().rectTemp.setHeight(Math.abs(event.getY() - getFeuille().tempY));
                        // En bas à gauche
                    } else if (event.getX() - getFeuille().tempX <= 0 && event.getY() - getFeuille().tempY >= 0) {
                        getFeuille().rectTemp.setX(event.getX());
                        getFeuille().rectTemp.setWidth(Math.abs(event.getX() - getFeuille().tempX));
                        getFeuille().rectTemp.setHeight(Math.abs(event.getY() - getFeuille().tempY));
                        // En bas à droite
                    } else if (event.getX() - getFeuille().tempX >= 0 && event.getY() - getFeuille().tempY >= 0) {
                        getFeuille().rectTemp.setWidth(Math.abs(event.getX() - getFeuille().tempX));
                        getFeuille().rectTemp.setHeight(Math.abs(event.getY() - getFeuille().tempY));
                    }
                }
            }
        };
    }

    private EventHandler<MouseEvent> releasedListenerRectangle() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    if (getFeuille().rectTemp != null) {
                        RectangleModel rectangle = new RectangleModel(new PointModel(getFeuille().rectTemp.getX(),
                                getFeuille().rectTemp.getY()),
                                getFeuille().rectTemp.getWidth(),
                                getFeuille().rectTemp.getHeight());
                        rectangle.setFillColor(barre.fillColor.toString().substring(2, 8));
                        rectangle.setStrokeColor(barre.strokeColor.toString().substring(2, 8));
                        rectangle.setStrokeWidth(barre.tailleTrait);
                        getFeuille().getChildren().remove(getFeuille().rectTemp);
                        feuille.rectTemp = null;
                        feuille.tempX = 0;
                        feuille.tempY = 0;
                        getModel().setState(new Draw(rectangle));
                    }
                } catch (PaintException ex) {
                    System.out.println(ex.getMessage() + " oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
                    Logger.getLogger(EditionView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
    }

    // Ecouteur de triangle
    private EventHandler<MouseEvent> pressedListenerTriangle() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                feuille.tempX = event.getX();
                feuille.tempY = event.getY();
                if (getFeuille().triTemp == null) {
                    getFeuille().resetTempTriangle(getFeuille().tempX, getFeuille().tempY, barre.strokeColor, barre.fillColor, barre.tailleTrait);
                    getFeuille().getChildren().add(getFeuille().triTemp);
                }
            }
        };
    }

    private EventHandler<MouseEvent> draggedListenerTriangle() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (getFeuille().triTemp != null) {
                    // En haut à gauche
                    ObservableList<Double> points = getFeuille().triTemp.getPoints();
                    double ecart;
                    System.out.println("dedant");
                    ecart = event.getX() - points.get(0);
                    points.clear();
                    points.add(0, getFeuille().tempX);
                    points.add(1, getFeuille().tempY);
                    points.add(2, event.getX());
                    points.add(3, event.getY());
                    points.add(4, event.getX() - (ecart * 2));
                    points.add(5, event.getY());
                }
            }
        };
    }

    private EventHandler<MouseEvent> releasedListenerTriangle() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    if (getFeuille().triTemp != null) {
                        TriangleModel triangle = new TriangleModel(
                                new PointModel(getFeuille().triTemp.getPoints().get(0), getFeuille().triTemp.getPoints().get(1)),
                                new PointModel(getFeuille().triTemp.getPoints().get(2), getFeuille().triTemp.getPoints().get(3)),
                                new PointModel(getFeuille().triTemp.getPoints().get(4), getFeuille().triTemp.getPoints().get(5)));
                        System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
                        System.out.println("" + getFeuille().triTemp.getPoints().get(0));
                        System.out.println("" + getFeuille().triTemp.getPoints().get(1));
                        System.out.println("" + getFeuille().triTemp.getPoints().get(2));
                        System.out.println("" + getFeuille().triTemp.getPoints().get(3));
                        System.out.println("" + getFeuille().triTemp.getPoints().get(4));
                        System.out.println("" + getFeuille().triTemp.getPoints().get(5));
                        System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
                        triangle.setFillColor(barre.fillColor.toString().substring(2, 8));
                        triangle.setStrokeColor(barre.strokeColor.toString().substring(2, 8));
                        triangle.setStrokeWidth(barre.tailleTrait);
                        getFeuille().getChildren().remove(getFeuille().triTemp);
                        feuille.triTemp = null;
                        feuille.tempX = 0;
                        feuille.tempY = 0;
                        getModel().setState(new Draw(triangle));
                    }
                } catch (PaintException ex) {
                    System.out.println(ex.getMessage() + " oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
                    Logger.getLogger(EditionView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
    }

    // Ecouteur de ligne
    private EventHandler<MouseEvent> pressedListenerLine() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                feuille.tempX = event.getX();
                feuille.tempY = event.getY();
                if (feuille.tempL == null) {
                    feuille.resetTempLine(event.getX(), event.getY(), barre.strokeColor, barre.tailleTrait);
                    getFeuille().getChildren().add(getFeuille().lineTemp);
                }
            }
        };
    }

    private EventHandler<MouseEvent> draggedListenerLine() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (feuille.lineTemp != null) {
                    feuille.lineTemp.setEndX(event.getX());
                    feuille.lineTemp.setEndY(event.getY());
                }
            }
        };
    }

    private EventHandler<MouseEvent> releasedListenerLine() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    LineModel line = new LineModel(new PointModel(getFeuille().lineTemp.getStartX(), getFeuille().lineTemp.getStartY()),
                            new PointModel(getFeuille().lineTemp.getEndX(), getFeuille().lineTemp.getEndY()));
                    line.setStrokeColor(barre.strokeColor.toString().substring(2, 8));
                    line.setStrokeWidth(barre.tailleTrait);
                    getFeuille().getChildren().remove(getFeuille().lineTemp);
                    getModel().setState(new Draw(line));
                    feuille.lineTemp = null;
                    feuille.tempX = 0;
                    feuille.tempY = 0;
                } catch (PaintException ex) {
                    Logger.getLogger(EditionView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
    }

    // Ecouteur de dessin libre
    private EventHandler<MouseEvent> pressedListenerFreeHand() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (feuille.path == null) {
                    feuille.resetTempPath(barre.strokeColor, barre.tailleTrait);
                    feuille.path.getElements().clear();
                    feuille.getChildren().add(feuille.path);
                    feuille.path.getElements().add(new MoveTo(event.getX(), event.getY()));
                }
            }
        };
    }

    private EventHandler<MouseEvent> draggedListenerFreeHand() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (feuille.path != null) {
                    feuille.path.getElements().add(new LineTo(event.getX(), event.getY()));
                }
            }
        };
    }

    private EventHandler<MouseEvent> releasedListenerFreeHand() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Path p = feuille.path;
                    PointModel point = null;
                    List<PointModel> pointsList;
                    pointsList = new ArrayList<>();

                    //Modelisation
                    for (PathElement elem : p.getElements()) {
                        if (elem instanceof MoveTo) {
                            MoveTo m = (MoveTo) elem;
                            System.out.println("Moveto x :" + m.getX() + " - y :" + m.getY());
                            point = new PointModel(m.getX(), m.getY());
                        } else if (elem instanceof LineTo) {
                            LineTo l = (LineTo) elem;
                            System.out.println("Lineto x :" + l.getX() + " - y :" + l.getY());
                            point = new PointModel(l.getX(), l.getY());
                        }
                        pointsList.add(point);
                    }

                    PathModel path = new PathModel(pointsList);
                    path.setStrokeColor(barre.strokeColor.toString().substring(2, 8));
                    path.setStrokeWidth(barre.tailleTrait);
                    getFeuille().getChildren().remove(getFeuille().path);
                    getModel().setState(new Draw(path));
                    feuille.path = null;
                } catch (PaintException ex) {
                    Logger.getLogger(EditionView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
    }

    // Ecouteur sur le déplacement de forme
    private EventHandler<MouseEvent> pressedListenerMove() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
//                if (barre.drawMode == DrawMode.move) {
//                    currentMove = new Move(form.shapeModel);
//                    System.out.println("PAINT_PROJECT CLIENT HANDLE MOUSE_PRESSED " + form.shapeModel.getX());
//                    System.out.println("PAINT_PROJECT CLIENT HANDLE MOUSE_PRESSED " + form.shapeModel.getX());
//                    feuille.ecartX = event.getX() - form.shapeModel.getX();
//                    feuille.ecartY = event.getY() - form.shapeModel.getY();
//                }
            }
        };
    }

    private EventHandler<MouseEvent> draggedListenerMove() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
//                if (barre.drawMode == DrawMode.move) {
//                    form.setX(event.getX() - getFeuille().ecartX);
//                    form.setY(event.getY() - getFeuille().ecartY);
//                    currentMove.setNewPosition(form.getX(), form.getY());
//                    getModel().setState(currentMove);
//                }
            }
        };
    }

    private EventHandler<MouseEvent> releasedListenerMove() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
//                if (barre.drawMode == DrawMode.move) {
//                    currentMove.setNewPosition(form.getX(), form.getY());
//                    getModel().setState(currentMove);
//                    System.out.println("PAINT_PROJECT CLIENT HANDLE MOUSE_RELEASED");
//                    System.out.println("PAINT_PROJECT CLIENT HANDLE MOUSE_RELEASED " + form.shapeModel.getX() + " " + form.shapeModel.getY());
//                    System.out.println("PAINT_PROJECT CLIENT HANDLE MOUSE_RELEASED " + form.getX() + " " + form.getY());
//                    System.out.println("PAINT_PROJECT CLIENT HANDLE MOUSE_RELEASED " + form.shapeModel.getX() + " " + form.shapeModel.getY());
//                }
            }
        };
    }
    /*
     // Y a moyen de faire quelque chose avec LES 3 LISTENER EN DESOUS JVOUS LAISSERAI TESTER jvous jure xD
     private EventHandler<MouseEvent> pressedListenerFreeHand() {
     return new EventHandler<MouseEvent>() {
     @Override
     public void handle(MouseEvent event) {
     feuille.tempX = event.getX();
     feuille.tempY = event.getY();
     if (feuille.path == null) {
     feuille.resetTempPath(barre.strokeColor, barre.tailleTrait);
     feuille.path.getElements().clear();
     feuille.getChildren().add(feuille.path);
     feuille.path.getElements().add(new MoveTo(feuille.tempX, feuille.tempY));
     }
     }
     };
     }

     private EventHandler<MouseEvent> draggedListenerFreeHand() {
     return new EventHandler<MouseEvent>() {
     @Override
     public void handle(MouseEvent event) {
     if (feuille.path != null) {
     feuille.path.getElements().add(new MoveTo(event.getX(), event.getY()));
     }
     }
     };
     }

     private EventHandler<MouseEvent> releasedListenerFreeHand() {
     return new EventHandler<MouseEvent>() {
     @Override
     public void handle(MouseEvent event) {
     if (feuille.path != null) {
     feuille.path.getElements().add(new LineTo(event.getX(), event.getY()));
     }
     }
     };
     }
     */

    // Ecouteur vide sur le fond
    private EventHandler<MouseEvent> pressedListenerNull() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
            }
        };
    }

    private EventHandler<MouseEvent> draggedListenerNull() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
            }
        };
    }

    private EventHandler<MouseEvent> releasedListenerNull() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
            }
        };
    }

//    private EventHandler<? super MouseEvent> clickedListener() {
//        return new EventHandler<MouseEvent>() {
//
//            @Override
//            public void handle(MouseEvent event) {
//                if (barre.modeDessin == 6) {
//                    remplir(feuille.fond, false);
//                }
//            }
//        };
//    }
//
//    private void remplir(Rectangle rec, boolean strokeFill) {
//        remplir(new Forme(rec), strokeFill);
//    }
//
//    private void remplir(Forme forme, boolean strokeFill) {
//        feuille.ajouterAction(new Fill(feuille, forme, (Color) forme.getFill(),
//                barre.couleur1, strokeFill));
//        forme.setFill(barre.couleur1);
//    }
//
//    private EventHandler<MouseEvent> pressedListener() {
//        return new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                if (barre.modeDessin == 1) {
//                    if (feuille.tempC == null) {
//                        feuille.resetTempCircle(event.getX(), event.getY(), 10, barre.couleur1, barre.couleur2, barre.tailleTrait);
//                        feuille.getChildren().add(feuille.tempC);
//                    }
//                } else if (barre.modeDessin == 2) {
//                    if (feuille.tempLHT == null && feuille.tempLHB == null && feuille.tempLVL == null && feuille.tempLVR == null) {
//                        feuille.resetTempRectangle(event.getX(), event.getY(), barre.couleur1, barre.couleur2, barre.tailleTrait);
//                        feuille.getChildren().add(feuille.tempLHT);
//                        feuille.getChildren().add(feuille.tempLHB);
//                        feuille.getChildren().add(feuille.tempLVR);
//                        feuille.getChildren().add(feuille.tempLVL);
//                    }
//                } else if (barre.modeDessin == 3) {
//                    if (feuille.tempL == null) {
//                        feuille.resetTempLine(event.getX(), event.getY(), barre.couleur1, barre.couleur2, barre.tailleTrait);
//                        feuille.getChildren().add(feuille.tempL);
//                    }
//                } else if (barre.modeDessin == 5) {
//                    if (feuille.tempQ == null) {
//                        feuille.resetTempQuelconque(event.getX(), event.getY(), barre.couleur1, barre.couleur2, barre.tailleTrait);
//                        feuille.getChildren().add(feuille.tempQ);
//                    }
//                }
//            }
//        };
//    }
//
//    private EventHandler<MouseEvent> draggedListener() {
//        return new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                if (barre.modeDessin == 1) {
//                    if (feuille.tempC != null) {
//                        int radius = (int) Math.max(Math.abs(event.getX() - feuille.tempC.getCenterX()), Math.abs(event.getY() - feuille.tempC.getCenterY()));
//                        feuille.tempC.setRadius(radius);
//                    }
//                } else if (barre.modeDessin == 2) {
//                    if (feuille.tempLHT != null && feuille.tempLHB != null && feuille.tempLVL != null && feuille.tempLVR != null) {
//                        feuille.tempLHB.setEndX(event.getX());
//                        feuille.tempLHB.setEndY(event.getY());
//                        feuille.tempLHB.setStartY(event.getY());
//
//                        feuille.tempLVR.setEndX(event.getX());
//                        feuille.tempLVR.setEndY(event.getY());
//                        feuille.tempLVR.setStartX(event.getX());
//
//                        feuille.tempLHT.setEndX(event.getX());
//                        feuille.tempLVL.setEndY(event.getY());
//                    }
//                } else if (barre.modeDessin == 3) {
//                    if (feuille.tempL != null) {
//                        feuille.tempL.setEndX(event.getX());
//                        feuille.tempL.setEndY(event.getY());
//                    }
//                } else if (barre.modeDessin == 5) {
//                    if (feuille.tempQ != null) {
//                        feuille.tempQ.add(event.getX(), event.getY());
//                    }
//                }
//            }
//        };
//    }
//
//    private EventHandler<MouseEvent> releasedListener() {
//        return new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                if (barre.modeDessin == 1) {
//                    if (feuille.tempC != null) {
//                        Circle cer1 = new Circle(feuille.tempC.getCenterX(), feuille.tempC.getCenterY(), feuille.tempC.getRadius());
//                        cer1.setFill(barre.couleur2);
//                        cer1.setStroke(barre.couleur1);
//                        cer1.setStrokeWidth(barre.tailleTrait);
//                        Forme forme = new Forme(cer1);
//                        feuille.ajouterAction(new Draw(feuille, forme));
//                        listenersForme(forme);
//                        feuille.getChildren().remove(feuille.tempC);
//                        feuille.tempC = null;
//                        feuille.getChildren().add(forme);
//                    }
//                } else if (barre.modeDessin == 2) {
//                    if (feuille.tempLHT != null && feuille.tempLHB != null && feuille.tempLVL != null && feuille.tempLVR != null) {
//                        int leX = (int) Math.min(feuille.tempLHT.getStartX(), feuille.tempLVR.getEndX());
//                        int leY = (int) Math.min(feuille.tempLHT.getStartY(), feuille.tempLVR.getEndY());
//                        int width = (int) Math.abs(feuille.tempLHT.getStartX() - feuille.tempLHT.getEndX());
//                        int height = (int) Math.abs(feuille.tempLVL.getStartY() - feuille.tempLVL.getEndY());
//                        Rectangle rec1 = new Rectangle(leX, leY, width, height);
//                        //RectangleModel rectModel = new RectangleModel(new PointModel(rec1.getX(), rec1.getY()), rec1.getWidth(), rec1.getHeight());
//                        //model.shapes.add(rectModel);
//                        rec1.setFill(barre.couleur2);
//                        rec1.setStroke(barre.couleur1);
//                        rec1.setStrokeWidth(barre.tailleTrait);
//
//                        //ShapeModel s = model.shapes.get(model.shapes.size() - 1);
//
////                        if (s.getType() == ShapeEnum.Rectangle) {
////                            RectangleModel se = (RectangleModel) s;
////                            Rectangle r = new Rectangle(se.getX(), se.getY(), se.getWidth(), se.getHeight());
////                            feuille.getChildren().add(r);
////                        }
//
//                        Forme forme = new Forme(rec1);
//                        feuille.ajouterAction(new Draw(feuille, forme));
//                        listenersForme(forme);
//                        feuille.getChildren().remove(feuille.tempLHT);
//                        feuille.getChildren().remove(feuille.tempLHB);
//                        feuille.getChildren().remove(feuille.tempLVR);
//                        feuille.getChildren().remove(feuille.tempLVL);
//                        feuille.tempLHT = null;
//                        feuille.tempLHB = null;
//                        feuille.tempLVR = null;
//                        feuille.tempLVL = null;
//                        feuille.getChildren().add(forme);
//                    }
//                } else if (barre.modeDessin == 3) {
//                    if (feuille.tempL != null) {
//                        Line line1 = new Line(feuille.tempL.getStartX(), feuille.tempL.getStartY(), feuille.tempL.getEndX(), feuille.tempL.getEndY());
//                        line1.setFill(barre.couleur2);
//                        line1.setStroke(barre.couleur1);
//                        line1.setStrokeWidth(barre.tailleTrait);
//                        Forme forme = new Forme(line1);
//                        feuille.ajouterAction(new Draw(feuille, forme));
//                        listenersForme(forme);
//                        feuille.getChildren().remove(feuille.tempL);
//                        feuille.tempL = null;
//                        feuille.getChildren().add(forme);
//                    }
//                } else if (barre.modeDessin == 5) {
//                    if (feuille.tempQ != null) {
//                        Forme forme = new Forme(feuille.tempQ);
//                        feuille.ajouterAction(new Draw(feuille, forme));
//                        listenersForme(forme);
//                        feuille.getChildren().remove(feuille.tempQ);
//                        feuille.tempQ = null;
//                        feuille.getChildren().add(forme);
//                    }
//                }
//            }
//
//        };
//    }
//
//    private void listenersForme(Forme forme) {
//        forme.setOnMouseClicked(new EventHandler<MouseEvent>() {
//
//            @Override
//            public void handle(MouseEvent event) {
//                if (barre.modeDessin == 7) {
//                    feuille.getChildren().remove(forme);
//                    forme.setFormeEffect(Util.highlight);
//                    feuille.tempQ.add(forme);
//                    forme.setOnMouseClicked(null);
//                    forme.setOnMousePressed(null);
//                    forme.setOnMouseDragged(null);
//                    forme.setOnMouseReleased(null);
//                }
//            }
//        });
//        forme.setOnMousePressed(new EventHandler<MouseEvent>() {
//
//            @Override
//            public void handle(MouseEvent event) {
//                if (barre.modeDessin == 4) {
//                    feuille.currentMove = new Move(feuille, forme);
//                    feuille.ecartX = event.getX() - forme.getX();
//                    feuille.ecartY = event.getY() - forme.getY();
//                    if (forme.getType().equals("ligne")) {
//                        feuille.ecartX2 = event.getX() - forme.getX2();
//                        feuille.ecartY2 = event.getY() - forme.getY2();
//                    }
//                    feuille.getChildren().remove(forme);
//                    feuille.getChildren().add(forme);
//                    if (forme.getType().equals("quelconque") && ((Quelconque) forme.objet).isGroupe) {
//                        ((Quelconque) forme.objet).setFormeEffect(Util.highlight);
//                    }
//                }
//            }
//        });
//
//        forme.setOnMouseDragged(new EventHandler<MouseEvent>() {
//
//            @Override
//            public void handle(MouseEvent event) {
//                if (barre.modeDessin == 4) {
//                    forme.setX(event.getX() - feuille.ecartX);
//                    forme.setY(event.getY() - feuille.ecartY);
//                    if (forme.getType().equals("ligne")) {
//                        forme.setX2(event.getX() - feuille.ecartX2);
//                        forme.setY2(event.getY() - feuille.ecartY2);
//                    }
//                }
//            }
//        });
//
//        forme.setOnMouseReleased(new EventHandler<MouseEvent>() {
//
//            @Override
//            public void handle(MouseEvent event) {
//                if (barre.modeDessin == 4) {
//                    if (!forme.getType().equals("ligne")) {
//                        feuille.currentMove.setNewPosition(forme.getX(), forme.getY());
//                    } else {
//                        feuille.currentMove.setNewPosition(forme.getX(), forme.getY(), forme.getX2(), forme.getY2());
//                    }
//                    feuille.ajouterAction(feuille.currentMove);
//                    feuille.currentMove = null;
//                    if (forme.getType().equals("quelconque") && ((Quelconque) forme.objet).isGroupe) {
//                        ((Quelconque) forme.objet).setFormeEffect(null);
//                    }
//                }
//            }
//        });
//    }
    private void addBarreListeners() {
        //barre.save.setOnMouseClicked(saveListener());
        barre.undoButton.setOnAction(undoListener());
        barre.redoButton.setOnAction(redoListener());
        barre.saveButton.setOnAction(saveListener());
        barre.loadButton.setOnAction(loadListener());
        //barre.grouper.setOnAction(grouperListener());
    }

    private EventHandler<ActionEvent> redoListener() {
        return new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("REDO");
                getModel().setState(new Redo());
            }
        };
    }

    private EventHandler<ActionEvent> undoListener() {
        return new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("UNDO");
                getModel().setState(new Undo());
            }
        };
    }

    private EventHandler<ActionEvent> saveListener() {
        return new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("SAVE");
                Stage stage = new Stage();
                TextField txt = new TextField();
                txt.setOnAction(ae -> {
                    getModel().setState(new Save(txt.getText()));
                    stage.close();
                });

                stage.setScene(new Scene(new StackPane(new VBox(10, new Text("Entrer le nom de la figure à sauvegarder"), txt)), 200, 100));
                stage.show();
            }
        };
    }

    private boolean charged;

    private EventHandler<ActionEvent> loadListener() {
        return new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("LOad");
                charged = false;
                getModel().setState(new FigureList());
                while (!charged);
                charged = false;
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        LoadTable t = new LoadTable(getModel().getState().getFigureNameList(), getModel());
                    }
                });
            }
        };
    }

//
//    private EventHandler<ActionEvent> grouperListener() {
//        return new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent event) {
//                if (barre.grouper.isSelected() && feuille.tempQ == null) {
//                    barre.grouper.setText("fin");
//                    barre.modeDessin = 7;
//                    feuille.tempQ = new Quelconque();
//                    feuille.getChildren().add(feuille.tempQ);
//                } else if (!barre.grouper.isSelected()) {
//                    barre.grouper.setText("grouper");
//                    barre.modeDessin = 4;
//                    Forme forme = new Forme(feuille.tempQ);
//                    listenersForme(forme);
//                    forme.setFormeEffect(null);
//                    System.out.println("stouZZ");
//                    feuille.getChildren().remove(feuille.tempQ);
//                    feuille.getChildren().add(forme);
//                    feuille.tempQ = null;
//                }
//            }
//        };
//    }
//
//    private EventHandler<ActionEvent> redoListener() {
//        return new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent event) {
//                if (feuille.lastAction + 1 < feuille.actions.size()) {
//                    feuille.lastAction++;
//                    feuille.actions.get(feuille.lastAction).execute();
//                }
//            }
//        };
//    }
//
//    private EventHandler<ActionEvent> undoListener() {
//        return new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent event) {
//                if (feuille.lastAction >= 0) {
//                    feuille.actions.get(feuille.lastAction).undo();
//                    feuille.lastAction--;
//                }
//            }
//        };
//    }
    private EventHandler<MouseEvent> pressedListener() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                feuille.tempX = event.getX();
                feuille.tempY = event.getY();
                if (barre.drawMode == DrawMode.circle) {
                    if (getFeuille().circleTemp == null) {
                        feuille.resetTempCircle(event.getX(), event.getY(), 0, barre.strokeColor, barre.fillColor, barre.tailleTrait);
                        getFeuille().getChildren().add(getFeuille().circleTemp);
                    }
                } else if (barre.drawMode == DrawMode.rectangle) {
                    if (getFeuille().rectTemp == null) {
                        getFeuille().resetTempRectangle(getFeuille().tempX, getFeuille().tempY, barre.strokeColor, barre.fillColor, barre.tailleTrait);
                        getFeuille().getChildren().add(getFeuille().rectTemp);
                    }
//                    if (feuille.tempLHT == null && feuille.tempLHB == null && feuille.tempLVL == null && feuille.tempLVR == null) {
//                        feuille.resetTempRectangle(event.getX(), event.getY(), barre.couleur1, barre.couleur2, barre.tailleTrait);
//                        feuille.getChildren().add(feuille.tempLHT);
//                        feuille.getChildren().add(feuille.tempLHB);
//                        feuille.getChildren().add(feuille.tempLVR);
//                        feuille.getChildren().add(feuille.tempLVL);
//                    }
                } else if (barre.drawMode == DrawMode.line) {
                    if (feuille.tempL == null) {
                        feuille.resetTempLine(event.getX(), event.getY(), barre.strokeColor, barre.tailleTrait);
                        getFeuille().getChildren().add(getFeuille().lineTemp);
                    }
                } else if (barre.drawMode == DrawMode.free) {
//                    if (feuille.tempQ == null) {
//                        //feuille.resetTempQuelconque(event.getX(), event.getY(), barre.couleur1, barre.couleur2, barre.tailleTrait);
//                        feuille.getChildren().add(feuille.tempQ);
//                    }
                }
            }
        };
    }

    private EventHandler<? super MouseEvent> draggedListener() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (barre.drawMode == DrawMode.circle) {
                    if (getFeuille().circleTemp != null) {
                        getFeuille().radius = (int) Math.max(Math.abs(event.getX() - getFeuille().circleTemp.getCenterX()), Math.abs(event.getY() - getFeuille().circleTemp.getCenterY()));
                        getFeuille().circleTemp.setRadius(getFeuille().radius);
                    }
                } else if (barre.drawMode == DrawMode.rectangle) {
                    if (getFeuille().rectTemp != null) {
                        // En haut à gauche
                        if (event.getX() - getFeuille().tempX <= 0 && event.getY() - getFeuille().tempY <= 0) {
                            getFeuille().rectTemp.setX(event.getX());
                            getFeuille().rectTemp.setY(event.getY());
                            getFeuille().rectTemp.setWidth(Math.abs(event.getX() - getFeuille().tempX));
                            getFeuille().rectTemp.setHeight(Math.abs(event.getY() - getFeuille().tempY));
                            // En haut à droite
                        } else if (event.getX() - getFeuille().tempX >= 0 && event.getY() - getFeuille().tempY <= 0) {
                            getFeuille().rectTemp.setY(event.getY());
                            getFeuille().rectTemp.setWidth(Math.abs(event.getX() - getFeuille().tempX));
                            getFeuille().rectTemp.setHeight(Math.abs(event.getY() - getFeuille().tempY));
                            // En bas à gauche
                        } else if (event.getX() - getFeuille().tempX <= 0 && event.getY() - getFeuille().tempY >= 0) {
                            getFeuille().rectTemp.setX(event.getX());
                            getFeuille().rectTemp.setWidth(Math.abs(event.getX() - getFeuille().tempX));
                            getFeuille().rectTemp.setHeight(Math.abs(event.getY() - getFeuille().tempY));
                            // En bas à droite
                        } else if (event.getX() - getFeuille().tempX >= 0 && event.getY() - getFeuille().tempY >= 0) {
                            getFeuille().rectTemp.setWidth(Math.abs(event.getX() - getFeuille().tempX));
                            getFeuille().rectTemp.setHeight(Math.abs(event.getY() - getFeuille().tempY));
                        }
//                        if (feuille.tempLHT != null && feuille.tempLHB != null && feuille.tempLVL != null && feuille.tempLVR != null) {
//                            feuille.tempLHB.setEndX(event.getX());
//                            feuille.tempLHB.setEndY(event.getY());
//                            feuille.tempLHB.setStartY(event.getY());
//
//                            feuille.tempLVR.setEndX(event.getX());
//                            feuille.tempLVR.setEndY(event.getY());
//                            feuille.tempLVR.setStartX(event.getX());
//
//                            feuille.tempLHT.setEndX(event.getX());
//                            feuille.tempLVL.setEndY(event.getY());
//                        }
                    }
                } else if (barre.drawMode == DrawMode.line) {
                    if (feuille.lineTemp != null) {
                        feuille.lineTemp.setEndX(event.getX());
                        feuille.lineTemp.setEndY(event.getY());
                    }
                }
            }
        };
    }

    private EventHandler<? super MouseEvent> releasedListener() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (barre.drawMode == DrawMode.circle) {
                    try {
                        CircleModel circle = new CircleModel(new PointModel(getFeuille().tempX, getFeuille().tempY), getFeuille().radius);
                        //Circle circleFX = getFeuille().circleTemp;
                        //circleFX.setFill(barre.couleur1);
                        //circleFX.setStroke(barre.couleur2);
                        //circleFX.setStrokeWidth(barre.tailleTrait);
                        getFeuille().getChildren().remove(getFeuille().circleTemp);
                        //Form form = new Form(rectangleFX, rectangle);
                        //feuille.getChildren().add(form);
                        //shapeMap.put(rectangle.getId(), form);
                        //formListeners(form);
                        getModel().setState(new Draw(circle));
                        feuille.circleTemp = null;
                        feuille.tempX = 0;
                        feuille.tempY = 0;
                    } catch (PaintException ex) {
                        Logger.getLogger(EditionView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (barre.drawMode == DrawMode.rectangle) {
                    try {
                        RectangleModel rectangle = new RectangleModel(new PointModel(getFeuille().rectTemp.getX(),
                                getFeuille().rectTemp.getY()),
                                getFeuille().rectTemp.getWidth(),
                                getFeuille().rectTemp.getHeight());
                        //Rectangle rectangleFX = getFeuille().rectTemp;
                        //Rectangle rectangleFX = new Rectangle(feuille.tempX, feuille.tempY, feuille.rectTemp.getWidth(), feuille.rectTemp.getHeight());
                        //rectangleFX.setFill(barre.couleur1);
                        //rectangleFX.setStroke(barre.couleur2);
                        //rectangleFX.setStrokeWidth(barre.tailleTrait);
                        getFeuille().getChildren().remove(getFeuille().rectTemp);
                        //Form form = new Form(rectangleFX, rectangle);
                        //feuille.getChildren().add(form);
                        //shapeMap.put(rectangle.getId(), form);
                        //formListeners(form);
                        getModel().setState(new Draw(rectangle));
                        feuille.rectTemp = null;
                        feuille.tempX = 0;
                        feuille.tempY = 0;
                    } catch (PaintException ex) {
                        Logger.getLogger(EditionView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (barre.drawMode == DrawMode.line) {
                    try {
                        LineModel line = new LineModel(new PointModel(getFeuille().lineTemp.getStartX(), getFeuille().lineTemp.getStartY()),
                                new PointModel(getFeuille().lineTemp.getEndX(), getFeuille().lineTemp.getEndY()));
                        getFeuille().getChildren().remove(getFeuille().lineTemp);
                        getModel().setState(new Draw(line));
                        feuille.lineTemp = null;
                        feuille.tempX = 0;
                        feuille.tempY = 0;
                    } catch (PaintException ex) {
                        Logger.getLogger(EditionView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        };
    }

    //feuille.rectTemp = null;
    //Form form = new Form(rectangleFX, rectangle);
    //feuille.getChildren().add(form);
    //shapeMap.put(rectangle.getId(), form);
    //System.out.println("PAINT_PROJECT CLIENT DRAW_RECTANGLE not contains");
    //formListeners(form);
//                    if (feuille.tempLHT != null && feuille.tempLHB != null && feuille.tempLVL != null && feuille.tempLVR != null) {
//                        int leX = (int) Math.min(feuille.tempLHT.getStartX(), feuille.tempLVR.getEndX());
//                        int leY = (int) Math.min(feuille.tempLHT.getStartY(), feuille.tempLVR.getEndY());
//                        int width = (int) Math.abs(feuille.tempLHT.getStartX() - feuille.tempLHT.getEndX());
//                        int height = (int) Math.abs(feuille.tempLVL.getStartY() - feuille.tempLVL.getEndY());
//                        Rectangle rec1 = new Rectangle(leX, leY, width, height);
    //RectangleModel rectModel = new RectangleModel(new PointModel(rec1.getX(), rec1.getY()), rec1.getWidth(), rec1.getHeight());
    //model.shapes.add(rectModel);
//                        rec1.setFill(barre.couleur2);
//                        rec1.setStroke(barre.couleur1);
//                        rec1.setStrokeWidth(barre.tailleTrait);
//                      ShapeModel s = model.shapes.get(model.shapes.size() - 1);
//                      if (s.getType() == ShapeEnum.Rectangle) {
//                          RectangleModel se = (RectangleModel) s;
//                          Rectangle r = new Rectangle(se.getX(), se.getY(), se.getWidth(), se.getHeight());
//                          feuille.getChildren().add(r);
//                      }
    //Forme forme = new Forme(rec1);
    //feuille.ajouterAction(new Draw(feuille, forme));
    //listenersForme(forme);
    //feuille.getChildren().remove(feuille.tempLHT);
    //feuille.getChildren().remove(feuille.tempLHB);
    //feuille.getChildren().remove(feuille.tempLVR);
    //feuille.getChildren().remove(feuille.tempLVL);
    //feuille.tempLHT = null;
    //feuille.tempLHB = null;
    //feuille.tempLVR = null;
    //feuille.tempLVL = null;
    //feuille.getChildren().add(forme);
    //}
    //feuille.rectTemp = null;
    //Form form = new Form(rectangleFX, rectangle);
    //feuille.getChildren().add(form);
    //shapeMap.put(rectangle.getId(), form);
    //System.out.println("PAINT_PROJECT CLIENT DRAW_RECTANGLE not contains");
    //formListeners(form);
//                    if (feuille.tempLHT != null && feuille.tempLHB != null && feuille.tempLVL != null && feuille.tempLVR != null) {
//                        int leX = (int) Math.min(feuille.tempLHT.getStartX(), feuille.tempLVR.getEndX());
//                        int leY = (int) Math.min(feuille.tempLHT.getStartY(), feuille.tempLVR.getEndY());
//                        int width = (int) Math.abs(feuille.tempLHT.getStartX() - feuille.tempLHT.getEndX());
//                        int height = (int) Math.abs(feuille.tempLVL.getStartY() - feuille.tempLVL.getEndY());
//                        Rectangle rec1 = new Rectangle(leX, leY, width, height);
    //RectangleModel rectModel = new RectangleModel(new PointModel(rec1.getX(), rec1.getY()), rec1.getWidth(), rec1.getHeight());
    //model.shapes.add(rectModel);
//                        rec1.setFill(barre.couleur2);
//                        rec1.setStroke(barre.couleur1);
//                        rec1.setStrokeWidth(barre.tailleTrait);
//                      ShapeModel s = model.shapes.get(model.shapes.size() - 1);
//                      if (s.getType() == ShapeEnum.Rectangle) {
//                          RectangleModel se = (RectangleModel) s;
//                          Rectangle r = new Rectangle(se.getX(), se.getY(), se.getWidth(), se.getHeight());
//                          feuille.getChildren().add(r);
//                      }
    //Forme forme = new Forme(rec1);
    //feuille.ajouterAction(new Draw(feuille, forme));
    //listenersForme(forme);
    //feuille.getChildren().remove(feuille.tempLHT);
    //feuille.getChildren().remove(feuille.tempLHB);
    //feuille.getChildren().remove(feuille.tempLVR);
    //feuille.getChildren().remove(feuille.tempLVL);
    //feuille.tempLHT = null;
    //feuille.tempLHB = null;
    //feuille.tempLVR = null;
    //feuille.tempLVL = null;
    //feuille.getChildren().add(forme);
    //}
//                } else if (barre.modeDessin == 3) {
//
//                } else if (barre.modeDessin == 5) {
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
     * @return the currentFXShapes
     */
    public List<Node> getCurrentFXShapes() {
        return currentFXShapes;
    }

    /**
     * @param currentFXShapes the currentFXShapes to set
     */
    public void setCurrentFXShapes(List<Node> currentFXShapes) {
        this.currentFXShapes = currentFXShapes;
    }

}
