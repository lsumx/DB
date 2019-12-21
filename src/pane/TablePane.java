package pane;

import dao.ManagerDAO;
import dao.StudentDAO;
import entity.CourseEntity;
import entity.GradesEntity;
import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class TablePane extends Application {

    ManagerDAO managerDAO = new ManagerDAO();
    String id = "root";

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public Stage print_table(String id) {
        Stage primaryStage =new Stage();
        GridPane gridPane =new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25,25,25,25));
        Text title =new Text("welcome");
        gridPane.add(title,0,0,2,1);

        Label name = new Label("table_name");
        gridPane.add(name,0,1);
        TextField name_Field =new TextField();
        gridPane.add(name_Field,1,1);

        Button btn = new Button("submit");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        Text actiontarget = new Text();
        gridPane.add(actiontarget, 1, 3);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String nname = name_Field.getText().trim();
//                System.out.println(course_id+"\n"+id);
                boolean flag = managerDAO.print_table(nname);
                if (flag) {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText(managerDAO.result);
                    table(nname).show();
                }
                else {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText(managerDAO.result);
                }

            }
        });
        gridPane.add(hbBtn, 1, 2);
        Scene scene =new Scene(gridPane,300,275);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setWidth(500);

        primaryStage.setHeight(500);

        primaryStage.setResizable(false);

        return primaryStage;
    }

    public Stage table(String Name) {

        ArrayList<String> header = managerDAO.header;
        ArrayList<ArrayList<String>> alldata = managerDAO.alldata;

        Stage primaryStage =new Stage();
        GridPane gridPane =new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25,25,25,25));
        Text title = new Text(Name);
        gridPane.add(title,0,0,header.size(),1);

        for (int i = 0; i < header.size(); i++) {
            Text temp = new Text(header.get(i));
            gridPane.add(temp, i,1,1,1);
        }

        for (int i = 0; i < alldata.size(); i++) {
            for (int j = 0; j < header.size(); j++) {
                Text temp = new Text(alldata.get(i).get(j));
                gridPane.add(temp, j,i + 2,1,1);
            }
        }

        Scene scene = new Scene(gridPane,300,275);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setWidth(500);
        primaryStage.setHeight(500);
        primaryStage.setResizable(false);
        return primaryStage;
    }

}
