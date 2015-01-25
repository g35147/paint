/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paintproject.view;

import architecturemvc.PaintModel;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import manager.Load;
import paintproject.view.LoadTable.loadFigure;

/**
 *
 * @author Chaabani Nabil
 */
public class LoadTable extends Stage {

    protected Group root;
    protected Scene scene;
    private PaintModel model;

    public LoadTable(List<String> Drawlist, PaintModel model) {
        this.model = model;
        //Stage stage = new Stage();
        this.setTitle("notre table");
        root = new Group();
        scene = new Scene(root, 200, 300);
        this.setScene(scene);
//        Sequence children = (Sequence) root.getChildren();
        //children.add (getTableView()); 
        root.getChildren().add(getTableView(Drawlist));
        this.show();
    }

    public TableView getTableView(List<String> Drawlist) {

        TableView<loadFigure> personTable;

        ObservableList<loadFigure> data = FXCollections.observableArrayList();
        for (String value : Drawlist) {
             data.add(new loadFigure(new SimpleStringProperty(value)));
        }
        data.add(new loadFigure(new SimpleStringProperty("Duke")));
        data.add(new loadFigure(new SimpleStringProperty("villme")));
        personTable = new TableView<loadFigure>();
        personTable.setItems(data);
        TableColumn<loadFigure, String> firstNameCol = new TableColumn("Nom du Dessin ");
        firstNameCol.setCellValueFactory(cellData -> cellData.getValue().FigureName);
            personTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> Selectionne(newValue));
        personTable.getColumns().addAll(firstNameCol);
        return personTable;

    }

    private void Selectionne(loadFigure newValue) {
        System.out.println("est selectionné  + nom selectionné");
        System.out.println(newValue.FigureName.toString());
        model.setState(new Load(newValue.FigureName.get()));
    }

    public class loadFigure extends Group {

        private StringProperty FigureName;

        public loadFigure(StringProperty firstName) {
            this.FigureName = firstName;
        }

    }
}
