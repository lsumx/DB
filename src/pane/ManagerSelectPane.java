package pane;

import dao.InitDAO;
import dao.ManagerDAO;
import dao.StudentDAO;
import dao.InstructorDAO;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ManagerSelectPane {

    String id = "root";

    InsertInstructorPane insertInstructorPane = new InsertInstructorPane();
    InsertStudentPane insertStudentPane = new InsertStudentPane();
    InsertTimePane insertTimePane = new InsertTimePane();
    InsertExamPane insertExamPane = new InsertExamPane();
    TablePane tablePane = new TablePane();
    CreateCoursePane createCoursePane = new CreateCoursePane();
    DeleteCoursePane deleteCoursePane = new DeleteCoursePane();

    public Stage select_pane (String id) {

        GridPane gridPane =new GridPane();
        gridPane.setPadding(new Insets(11.5,12.5,13.5,14.5));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5.5);
        gridPane.setVgap(5.5);

        Button insertInstructor = new Button("加入老师");
        Button insertStudent = new Button("加入学生");
        Button insertTime = new Button("加入时间");
        Button insertExam = new Button("加入考试");
        Button printTable = new Button("查看表格");
        Button createCourse = new Button("开设课程");
        Button deleteCourse = new Button("删除课程");

        gridPane.add(insertInstructor, 0, 0);
        gridPane.add(insertStudent, 0, 1);
        gridPane.add(insertTime, 0, 2);
        gridPane.add(insertExam, 0, 3);
        gridPane.add(printTable, 1, 0);
        gridPane.add(createCourse, 1, 1);
        gridPane.add(deleteCourse, 1, 2);

        Scene scene =new Scene(gridPane);
        Stage primaryStage =new Stage();
        primaryStage.setScene(scene);

        insertInstructor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                insertInstructorPane.insert_instructor(id).show();
            }
        });

        insertStudent.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                insertStudentPane.insert_student(id).show();
            }
        });

        insertTime.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                insertTimePane.insert_time(id).show();
            }
        });

        insertExam.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                insertExamPane.insert_exam(id).show();
            }
        });

        printTable.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tablePane.print_table(id).show();
            }
        });

        createCourse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createCoursePane.create_course(id).show();
            }
        });

        deleteCourse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteCoursePane.delete_course(id).show();
            }
        });

        primaryStage.setWidth(500);

        primaryStage.setHeight(500);

        primaryStage.setResizable(false);
        return primaryStage;
    }
}
