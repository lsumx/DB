package pane;

import dao.InstructorDAO;
import entity.CourseEntity;
import entity.StudentEntity;
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

public class StudentPane {

    InstructorDAO instructorDAO =new InstructorDAO();

    public Stage studentStage(String id){
        Stage primaryStage =new Stage();
        Scene scene =new Scene(new Group());
        primaryStage.setTitle("show courses");
        primaryStage.setWidth(450);
        primaryStage.setHeight(500);

        TableView<StudentEntity> tableView =new TableView<>();
        ObservableList<StudentEntity> data = FXCollections.observableArrayList(instructorDAO.see_students(id));
        tableView.setItems(data);
        Label label =new Label("课程表");
        label.setFont(new Font("Arial",20));

//String course_name,String time,String room_number,String exam_time,String exam_type
        TableColumn column =new TableColumn("学生id");
        column.setMinWidth(100);
        column.setCellValueFactory(new PropertyValueFactory<>("id"));


        TableColumn firstCol =new TableColumn("姓名");
        firstCol.setMinWidth(100);
        firstCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn secCol = new TableColumn("院系");
        secCol.setMinWidth(150);
        secCol.setCellValueFactory(new PropertyValueFactory<>("dept_name"));



        tableView.getColumns().addAll(column,firstCol,secCol);
        VBox vBox =new VBox();
        vBox.getChildren().addAll(label,tableView);


        ((Group)scene.getRoot()).getChildren().addAll(vBox);


        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setWidth(630);

        primaryStage.setHeight(500);

//        primaryStage.setResizable(false);
        return  primaryStage;

    }


    public Stage studentGrades(String id){
        Stage primaryStage =new Stage();
        Scene scene =new Scene(new Group());
        primaryStage.setTitle("show courses");
        primaryStage.setWidth(450);
        primaryStage.setHeight(500);

        TableView<StudentEntity> tableView =new TableView<>();
        ObservableList<StudentEntity> data = FXCollections.observableArrayList(instructorDAO.see_students(id));
        tableView.setItems(data);
        Label label =new Label("课程表");
        label.setFont(new Font("Arial",20));

//String course_name,String time,String room_number,String exam_time,String exam_type
        TableColumn column =new TableColumn("学生id");
        column.setMinWidth(100);
        column.setCellValueFactory(new PropertyValueFactory<>("id"));


        TableColumn firstCol =new TableColumn("姓名");
        firstCol.setMinWidth(100);
        firstCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn secCol = new TableColumn("院系");
        secCol.setMinWidth(150);
        secCol.setCellValueFactory(new PropertyValueFactory<>("dept_name"));

        TableColumn thirdCol = new TableColumn("成绩");
        thirdCol.setMinWidth(150);
        thirdCol.setCellValueFactory(new PropertyValueFactory<>("grade"));


        tableView.getColumns().addAll(column,firstCol,secCol,thirdCol);
        VBox vBox =new VBox();
        vBox.getChildren().addAll(label,tableView);


        ((Group)scene.getRoot()).getChildren().addAll(vBox);


        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setWidth(630);

        primaryStage.setHeight(500);

//        primaryStage.setResizable(false);
        return  primaryStage;
    }

}
