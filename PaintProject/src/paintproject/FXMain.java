/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paintproject;

import paintproject.view.EditionView;
import paintproject.view.AbstractView;
import paintproject.view.ReadOnlyView;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import paintproject.view.StatistiqueView;
import paintproject.view.TerminalView;

/**
 *
 * @author supeUse
 */
public class FXMain extends Application {

    private Stage primary;
    private List<AbstractView> views;
    private double startX;
    private double startY;

    @Override
    public void start(Stage primaryStage) {
        Text text = new Text("Entrez une adresse ip ou rien (pour votre machine) et taper enter");
        TextField tField = new TextField();
        tField.setMaxWidth(200);
        tField.setOnAction(ae -> {
            startAction(ae, tField);
        });
        Button but = new Button("OK");
        but.setOnAction(ae -> {
            startAction(ae, tField);
        });
        HBox hbox = new HBox(5, tField, but);
        hbox.setAlignment(Pos.CENTER);
        VBox box = new VBox(10, text, hbox);
        box.setAlignment(Pos.CENTER);
        StackPane root = new StackPane(box);

        Scene startScene = new Scene(root, 500, 250);

        views = new ArrayList<>();
        startX = 50;
        startY = 50;

        primaryStage.setTitle("Entrez une adresse IP");
        primaryStage.setScene(startScene);
        primaryStage.show();
        primary = primaryStage;
    }

    @Override
    public void stop() {
        try {
            stopViews();
            Platform.exit();
            System.exit(0);
        } catch (Exception ex) {
            Stage info = erreur(ex.getMessage());
            info.setOnCloseRequest(we -> {
                Platform.exit();
                System.exit(0);
            });
            info.showAndWait();
        }
    }

    public static Stage erreur(String err) {
        Stage erreur = new Stage();
        erreur.setTitle("ERREUR");
        Text errmsg = new Text(err);
        errmsg.setFont(Font.font(14));
        errmsg.setFill(Color.RED);
        Scene errScene = new Scene(new StackPane(errmsg));
        errScene.setOnKeyPressed(ke -> {
            if (ke.getCode() == KeyCode.ENTER) {
                erreur.close();
            }
        });
        erreur.setScene(errScene);
        return erreur;
    }

    public Stage erreur(String err, ActionEvent event) {
        Stage erreur = this.erreur(err);
        erreur.initModality(Modality.WINDOW_MODAL);
        erreur.initOwner(((Node) event.getSource()).getScene().getWindow());
        return erreur;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //System.out.println(args[1]);
        launch(args);
    }

    private void startViews() {
        primary.close();
        for (AbstractView view : views) {
            view.setOnCloseRequest(ev -> {
                views.remove(view);
                try {
                    view.stop();
                } catch (Exception ex) {
                    //
                }
                view.close();
            });
            view.setX(startX);
            view.setY(startY);
            startX += 10;
            startY += 40;
            view.show();
        }
    }

    private void stopViews() throws Exception {
        for (AbstractView view : views) {
            view.stop();
            view.close();
        }
    }

    private void startAction(ActionEvent event, TextField text) {
        Stage info;
        try {
            views.add(new EditionView(1000, 500, ((TextField) text).getText()));
            views.add(new ReadOnlyView(1000, 500, ((TextField) text).getText()));
            views.add(new StatistiqueView(1000, 500, ((TextField) text).getText()));
            views.add(new TerminalView(1000, 500, ((TextField) text).getText()));
            startViews();
        } catch (RemoteException | NotBoundException | MalformedURLException ex) {
            for (AbstractView view : views) {
                try {
                    view.stop();
                } catch (Exception ex1) {
                    //
                }
            }
            views.clear();
            info = erreur(ex.getMessage(), event);
            info.show();
        }
    }

}
