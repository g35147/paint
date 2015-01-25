/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paintproject.view;

import java.io.File;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import paintcommon.FigureModel;
import paintcommon.ShapeEnum;
import paintcommon.shape.ShapeModel;
import paintproject.FeuilleDessin;

/**
 *
 * @author g38396
 */
public class StatistiqueView extends AbstractView {

    private FeuilleDessin feuille;
    private File file;
    private FigureModel state;
    private ObservableList<PieChart.Data> pieChartData;
    private PieChart chart;

    public StatistiqueView(double width, double height, String ip) throws RemoteException, NotBoundException, MalformedURLException {
        this(width, height);
        super.initModel(ip);
    }

    public StatistiqueView(double width, double height) {
        super("Statistique View", 500, height);
        //initElements
        feuille = new FeuilleDessin(500, 500);
        // ajout des panel à la racine dans l'ordre !!
        root.getChildren().add(feuille);
        //initialisation model
        sceneListeners();
        initCharts();
    }

    private void sceneListeners() {
        this.getScene().widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                getFeuille().fond.setWidth(newSceneWidth.doubleValue());
            }
        });

        this.getScene().heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                getFeuille().fond.setHeight(newValue.doubleValue());
            }
        });
    }

    private void initCharts() {
        pieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data("Cercles", 0),
                        new PieChart.Data("Rectangles", 0),
                        new PieChart.Data("Triangle", 0),
                        new PieChart.Data("Lignes", 0),
                        new PieChart.Data("Mains Libres", 0),
                        new PieChart.Data("Groupes", 0));
        chart = new PieChart(pieChartData);
        chart.setTitle("Pourcentages de distribution des formes");
        getFeuille().getChildren().add(chart);
        System.out.println(""+chart.styleProperty().getValue());

        Label caption = new Label("");
        caption.setTextFill(Color.DARKORANGE);
        caption.setStyle("-fx-font: 20 arial;");
        getFeuille().getChildren().add(caption);

        for (final PieChart.Data data : chart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            caption.setTranslateX(e.getSceneX());
                            caption.setTranslateY(e.getSceneY());
                            caption.setText(String.valueOf(data.getPieValue()) + "5 "+data.getName());
                        }
                    });
        }

//        for (Node node : chart.lookupAll("Text.chart-pie-label")) {
//            if (node instanceof Text) {
//                for (PieChart.Data data : pieChartData) {
//                    if (data.getName().equals(((Text) node).getText())) {
//                        ((Text) node).setText(((Text) node).getText()+" : "+String.format("%,.0f", data.getPieValue()));
//                    }
//                }
//            }
//        }
//        for (Data data : pieChartData) {
//            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED,
//                new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent e) {
//                        System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeestat");
//                        caption.setTranslateX(e.getSceneX());
//                        caption.setTranslateY(e.getSceneY());
//                        caption.setText(data.getPieValue() + "%");
//                    }
//                });
//        }
    }

    private void applyCustomColorSequence(
            ObservableList<PieChart.Data> pieChartData,
            String... pieColors) {
        int i = 0;
        for (PieChart.Data data : pieChartData) {
            data.getNode().setStyle(
                    "-fx-pie-color: " + pieColors[i % pieColors.length] + ";"
            );
            i++;
        }
    }

    public PieChart getChart() {
        return chart;
    }

    public void setChart(PieChart chart) {
        this.chart = chart;
    }

    public ObservableList<PieChart.Data> getPieChartData() {
        return pieChartData;
    }

    public void setPieChartData(ObservableList<PieChart.Data> pieChartData) {
        this.pieChartData = pieChartData;
    }

    public FeuilleDessin getFeuille() {
        return feuille;
    }

    public void setFeuille(FeuilleDessin feuille) {
        this.feuille = feuille;
    }

    public FigureModel getState() {
        return state;
    }

    public void setState(FigureModel state) {
        this.state = state;
    }

    @Override
    public void notifieChangement() {
        chartsUpdate(this.getModel().getState());
    }

    private void chartsUpdate(FigureModel figure) {
        System.out.println("UPDATE DUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUR" + figure);
        double nb;
        this.pieChartData.stream().forEach((d) -> {
            d.setPieValue(0);
        });
        for (ShapeModel shape : figure.shapes) {
            if (shape.getType() == ShapeEnum.Circle) {
                nb = pieChartData.get(0).getPieValue();
                pieChartData.get(0).setPieValue(nb + 1);
            } else if (shape.getType() == ShapeEnum.Rectangle) {
                nb = pieChartData.get(1).getPieValue();
                pieChartData.get(1).setPieValue(nb + 1);
            } else if (shape.getType() == ShapeEnum.Triangle) {
                nb = pieChartData.get(2).getPieValue();
                pieChartData.get(2).setPieValue(nb + 1);
            } else if (shape.getType() == ShapeEnum.Line) {
                nb = pieChartData.get(3).getPieValue();
                pieChartData.get(3).setPieValue(nb + 1);
            } else if (shape.getType() == ShapeEnum.FreeHand) {
                nb = pieChartData.get(4).getPieValue();
                pieChartData.get(4).setPieValue(nb + 1);
            } else if (shape.getType() == ShapeEnum.ShapeBlock) {
                nb = pieChartData.get(5).getPieValue();
                pieChartData.get(5).setPieValue(nb + 1);
            }
        }
//        System.out.println("Circle " + pieChartData.get(0).getName() + "-"
//                + pieChartData.get(0).toString());
//        System.out.println("Rectangle " + pieChartData.get(1).getPieValue() + "-"
//                + pieChartData.get(1).getNode().getStyle());
//        System.out.println("Line " + pieChartData.get(2).getPieValue() + "-"
//                + pieChartData.get(2).getNode().getStyle());
//        System.out.println("FreeHand " + pieChartData.get(3).getPieValue() + "-"
//                + pieChartData.get(3).getNode().getStyle());
//        System.out.println("ShapeBlock " + pieChartData.get(4).getPieValue() + "-"
//                + pieChartData.get(4).getNode().getStyle());
//
//        System.out.println("-----------------------------------------------------------------");
//        for (Node node : chart.lookupAll(".text.chart-pie-label")) {
//            //if (node instanceof Text) {
//                for (PieChart.Data data : pieChartData) {
//                    System.out.println("------------");
//                    System.out.println("data : " + data.getNode() + " - ");
//                    System.out.println("node : " + node + " - ");
//                    if (data.getNode() == node) {
//                        //((Text) node).setText(((Text) node).getText()+" : "+String.format("%,.0f", data.getPieValue()));
//                        //((Text) node).setText(((Text) node).getText() + " : " + String.format("%,.0f", data.getPieValue()));
//                        System.out.println("valeur a ajouter : " + String.format("%,.0f", data.getPieValue()));
//                    } else {
//                    }
//                    System.out.println("------------");
//                }
//           //}
//        }
//        System.out.println("------------------------------------------------------------------");
    }
}
