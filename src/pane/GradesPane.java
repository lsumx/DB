package pane;

import dao.StudentDAO;
import entity.GradesEntity;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GradesPane  {

    StudentDAO studentDAO =new StudentDAO();

    public Stage grade_pane(String id){
        Stage primaryStage =new Stage();
        Scene scene =new Scene(new Group());
        primaryStage.setTitle("show grade");
        primaryStage.setWidth(450);
        primaryStage.setHeight(500);

        String grade ="";
        if (studentDAO.totalGrades(id)>-1){
            grade+=studentDAO.totalGrades(id);

        }
        else
            grade+="成绩未出";
        System.out.println(grade);

        ObservableList<GradesEntity> data = FXCollections.observableArrayList(studentDAO.get_grades(id));
        TableView<GradesEntity> tableView =new TableView<>();
        tableView.setItems(data);
        Label label =new Label("成绩单  总绩点为"+grade);
        label.setFont(new Font("Arial",20));
        tableView.setEditable(true);

        TableColumn firstCol =new TableColumn("课程编号");
        firstCol.setMinWidth(100);
        firstCol.setCellValueFactory(new PropertyValueFactory<>("course_id"));
        TableColumn secCol = new TableColumn("课程名称");
        secCol.setMinWidth(100);
        secCol.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        TableColumn thirdCol =new TableColumn("课程等地");
        thirdCol.setCellValueFactory(new PropertyValueFactory<>("grades"));
        thirdCol.setMinWidth(100);
        tableView.getColumns().addAll(firstCol,secCol,thirdCol);


        VBox vBox =new VBox();
        vBox.getChildren().addAll(label,tableView);


        ((Group)scene.getRoot()).getChildren().addAll(vBox);
        primaryStage.setScene(scene);

        primaryStage.setWidth(600);

        primaryStage.setHeight(600);

        primaryStage.setResizable(false);
//        primaryStage.show();
        return primaryStage;
    }




}
