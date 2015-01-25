/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paintproject;

import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import manager.GroupForms;
import paintcommon.shape.ShapeModel;
import paintproject.view.EditionView;

/**
 *
 * @author supeUse
 */
public class BarreTaches extends FlowPane {

    public Rectangle fond;
    public EditionView window;

    public int modeDessin; // 1 cercle 2 rectanlge 3 line 4 move 5 libre 6 fill 7 grouper
    public DrawMode drawMode;
    public int tailleTrait;
    public Color strokeColor; // contour
    public Color fillColor; // interieure, fill
    public Button loadButton;
    public Button saveButton;
    public Button undoButton;
    public Button redoButton;
    public ToggleButton grouper;
    public ToggleButton degrouper;
    public ComboBox comboMode;
    public ComboBox<Integer> comboTaille;

    public BarreTaches(double w, double h, EditionView window) {
        super();
        initialisationAttributs();
        initElementsGraphic(w, h);
        this.window = window;
    }

    private void initialisationAttributs() {
        setModeDessin(4);
        setTailleTrait(1);
        setStrokeColor(Color.BLACK);
        setFillColor(Color.BLACK);
        drawMode = DrawMode.move;
    }

    private void initElementsGraphic(double w, double h) {
        fond = new Rectangle(w, h);
        fond.setFill(Color.LIGHTGREY);
        HBox box = new HBox(10);
        this.getStyleClass().add("pane");

        loadButton = new Button("Load");
        saveButton = new Button("Save");
        comboMode = new ComboBox();
        comboMode.getItems().addAll("Cercle", "Rectangle", "Triangle", "Ligne", "Move", "Libre", "Fill");
        comboMode.setValue("Move");
        comboTaille = new ComboBox<>();
        comboTaille.getItems().addAll(1, 2, 5, 10, 15, 20);
        comboTaille.setValue(1);
        CheckBox trans1 = new CheckBox("Transparent");
        ColorPicker picker1 = new ColorPicker();
        picker1.getStyleClass().add("Button");
        picker1.setValue(Color.BLACK);
        CheckBox trans2 = new CheckBox("Transparent");
        ColorPicker picker2 = new ColorPicker();
        picker2.getStyleClass().add("Button");
        picker2.setValue(Color.BLACK);
        undoButton = new Button("Undo");
        redoButton = new Button("Redo");
        grouper = new ToggleButton("Grouper");
        degrouper = new ToggleButton("Dégrouper");

        addListeners(comboMode, comboTaille, trans1, picker1, trans2, picker2);

        this.getChildren().add(new VBox(3, new Text("Sauvegarder - Charger"), saveButton, loadButton));
        this.getChildren().add(new VBox(3, new Text("Mode de dessin"), comboMode));
        this.getChildren().add(new VBox(3, new Text("Taille du trait"), comboTaille));
        this.getChildren().add(new VBox(3, new Text("Couleur du trait"), picker1, trans1));
        this.getChildren().add(new VBox(3, new Text("Couleur du fond"), picker2, trans2));
        this.getChildren().add(new VBox(3, new Text("Undo - Redo"), undoButton, redoButton));
        this.getChildren().add(new VBox(3, new Text("Group - Dégrouper"), grouper, degrouper));

        for (Node node : this.getChildren()) {
            ((VBox) node).getStyleClass().add("vbox");
        }
    }

    private void addListeners(ComboBox combo, ComboBox combo2,
            CheckBox trans1, ColorPicker picker1,
            CheckBox trans2, ColorPicker picker2) {
        combo.getSelectionModel().selectedItemProperty().addListener(comboModeListener());
        combo2.getSelectionModel().selectedItemProperty().addListener(comboTailleListener());
        trans1.selectedProperty().addListener(trans1Listener(picker1));
        picker1.setOnAction(picker1Listener(picker1, trans1));
        trans2.selectedProperty().addListener(trans2Listener(picker2));
        picker2.setOnAction(picker2Listener(picker2, trans2));
        grouper.setOnAction(groupListener());
        degrouper.setOnAction(degrouperListener());
    }

    private EventHandler<ActionEvent> degrouperListener() {
        return new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (degrouper.isSelected() && window.getCurrentShapesList() == null) {
                    setDrawMode(DrawMode.ungroup);
                    window.updateListener(drawMode);
                    degrouper.setText("Terminer");
                    for (Node node : getChildren()) {
                        for (Node nodeSon : ((VBox) node).getChildren()) {
                            if (nodeSon != degrouper) {
                                nodeSon.setDisable(true);
                            }
                        }
                    }
                } else {
                    comboMode.setValue("Move");
                    setDrawMode(DrawMode.move);
                    window.updateListener(drawMode);
                    degrouper.setText("Dégrouper");
                    for (Node node : getChildren()) {
                        for (Node nodeSon : ((VBox) node).getChildren()) {
                            if (nodeSon != degrouper) {
                                nodeSon.setDisable(false);
                            }
                        }
                    }
                }
            }
        };
    }

    private EventHandler<ActionEvent> groupListener() {
        return new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                if (grouper.isSelected() && window.getCurrentShapesList() == null) {
                    setDrawMode(DrawMode.group);
                    window.updateListener(drawMode);
                    grouper.setText("Terminer");
                    for (Node node : getChildren()) {
                        for (Node nodeSon : ((VBox) node).getChildren()) {
                            if (nodeSon != grouper) {
                                nodeSon.setDisable(true);
                            }
                        }
                    }
                    window.setCurrentShapesList(new ArrayList<>());
                    window.setCurrentFXShapes(new ArrayList<>());
                } else {
                    comboMode.setValue("Move");
                    setDrawMode(DrawMode.move);
                    window.updateListener(drawMode);
                    grouper.setText("Grouper");
                    for (Node node : getChildren()) {
                        for (Node nodeSon : ((VBox) node).getChildren()) {
                            if (nodeSon != grouper) {
                                nodeSon.setDisable(false);
                            }
                        }
                    }
                    for (Node node : window.getCurrentFXShapes()) {
                        window.getFeuille().getChildren().remove(node);
                    }
                    window.setCurrentFXShapes(null);
                    window.getModel().setState(new GroupForms(window.getCurrentShapesList().toArray(new ShapeModel[0])));
                    window.setCurrentShapesList(null);
                }
            }
        };
    }

    private EventHandler picker2Listener(ColorPicker picker2, CheckBox trans2) {
        return new EventHandler() {
            @Override
            public void handle(Event event) {
                setFillColor(picker2.getValue());
                trans2.setSelected(false);
            }
        };
    }

    private ChangeListener<Boolean> trans2Listener(ColorPicker picker2) {
        return new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    setFillColor(Color.TRANSPARENT);
                } else {
                    setFillColor(picker2.getValue());
                }
            }
        };
    }

    private EventHandler picker1Listener(ColorPicker picker1, CheckBox trans1) {
        return new EventHandler() {
            @Override
            public void handle(Event event) {
                setStrokeColor(picker1.getValue());
                trans1.setSelected(false);
            }
        };
    }

    private ChangeListener<Boolean> trans1Listener(ColorPicker picker1) {
        return new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    setStrokeColor(Color.TRANSPARENT);
                } else {
                    setStrokeColor(picker1.getValue());
                }
            }
        };
    }

    private ChangeListener comboTailleListener() {
        return new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                setTailleTrait((Integer) newValue);
            }
        };
    }

    private ChangeListener comboModeListener() {
        return new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                switch (newValue.toString()) {
                    case "Cercle":
                        setModeDessin(1);
                        setDrawMode(DrawMode.circle);
                        window.updateListener(DrawMode.circle);
                        break;
                    case "Rectangle":
                        setModeDessin(2);
                        setDrawMode(DrawMode.rectangle);
                        window.updateListener(DrawMode.rectangle);
                        break;
                    case "Triangle":
                        setModeDessin(2);
                        setDrawMode(DrawMode.triangle);
                        window.updateListener(DrawMode.triangle);
                        break;
                    case "Ligne":
                        setModeDessin(3);
                        setDrawMode(DrawMode.line);
                        window.updateListener(DrawMode.line);
                        break;
                    case "Move":
                        setModeDessin(4);
                        setDrawMode(DrawMode.move);
                        window.updateListener(DrawMode.move);
                        break;
                    case "Libre":
                        setModeDessin(5);
                        setDrawMode(DrawMode.free);
                        window.updateListener(DrawMode.free);
                        break;
                    case "Fill":
                        setModeDessin(6);
                        setDrawMode(DrawMode.fill);
                        window.updateListener(DrawMode.fill);
                        break;
                }
            }
        };
    }

    public void setFond(Rectangle fond) {
        this.fond = fond;
    }

    public void setModeDessin(int modeDessin) {
        this.modeDessin = modeDessin;
    }

    public void setDrawMode(DrawMode drawMode) {
        this.drawMode = drawMode;
    }

    public void setTailleTrait(int tailleTrait) {
        this.tailleTrait = tailleTrait;
    }

    public void setStrokeColor(Color couleur1) {
        this.strokeColor = couleur1;
    }

    public void setFillColor(Color couleur2) {
        this.fillColor = couleur2;
    }
}
