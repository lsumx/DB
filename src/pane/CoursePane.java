package pane;

import Utils.FileChoose;
import dao.InstructorDAO;
import dao.StudentDAO;
import entity.CourseEntity;
import entity.GradesEntity;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CoursePane  {
    StudentDAO studentDAO =new StudentDAO();
    InstructorDAO instructorDAO =new InstructorDAO();

    public Stage course_pane(String id){
        Stage primaryStage =new Stage();
        Scene scene =new Scene(new Group());
        primaryStage.setTitle("show courses");
        primaryStage.setWidth(450);
        primaryStage.setHeight(500);

        TableView<CourseEntity> tableView =new TableView<>();
        ObservableList<CourseEntity> data = FXCollections.observableArrayList(studentDAO.getCourses(id));
        tableView.setItems(data);
        Label label =new Label("课程表");
        label.setFont(new Font("Arial",20));

//String course_name,String time,String room_number,String exam_time,String exam_type
        TableColumn column =new TableColumn("课程id");
        column.setMinWidth(100);
        column.setCellValueFactory(new PropertyValueFactory<>("course_id"));


        TableColumn firstCol =new TableColumn("课程名称");
        firstCol.setMinWidth(100);
        firstCol.setCellValueFactory(new PropertyValueFactory<>("course_name"));

        TableColumn secCol = new TableColumn("上课时间");
        secCol.setMinWidth(150);
        secCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn thirdCol =new TableColumn("上课地点");
        thirdCol.setMinWidth(100);
        thirdCol.setCellValueFactory(new PropertyValueFactory<>("room_number"));

        TableColumn fourthCol =new TableColumn("考试时间");
        fourthCol.setMinWidth(150);
        fourthCol.setCellValueFactory(new PropertyValueFactory<>("exam_time"));

        TableColumn fifthCol =new TableColumn("考试方式");
        fifthCol.setMinWidth(100);
        fifthCol.setCellValueFactory(new PropertyValueFactory<>("exam_type"));
        fifthCol.setMinWidth(100);

        TableColumn del =new TableColumn("删除课程");
        del.setCellValueFactory(new PropertyValueFactory<>("Del"));

        Callback<TableColumn<CourseEntity,String>,TableCell<CourseEntity,String>> Del =new Callback<TableColumn<CourseEntity,String>, TableCell<CourseEntity,String>>() {
            @Override
            public TableCell call(final TableColumn<CourseEntity,String> param) {
                final TableCell <CourseEntity,String> cell=new TableCell<CourseEntity,String>(){
                    final Button button =new Button("Del");
                    public void updateItem(String item,boolean empty){
                       super.updateItem(item,empty);
                       if (empty){
                           setGraphic(null);
                           setText(null);
                       }else {
                           button.setOnAction(new EventHandler<ActionEvent>() {
                               @Override
                               public void handle(ActionEvent event) {
                                   CourseEntity courseEntity =  getTableView().getItems().get(getIndex());
                                   data.remove(courseEntity);
                                   studentDAO.quit(id,courseEntity.getCourse_id());
                               }
                           });
                           setText(null);
                           setGraphic(button);
                       }
                    }
                };
                return cell;
            }
        };
        del.setCellFactory(Del);
//        TableColumn button =new TableColumn("删除课程");
//        button.setMinWidth(100);
//        button.setCellValueFactory(new PropertyValueFactory<>("Dummy"));
        if(studentDAO.is_time_valid())
            tableView.getColumns().addAll(column,firstCol,secCol,thirdCol,fourthCol,fifthCol,del);
        else
            tableView.getColumns().addAll(column,firstCol,secCol,thirdCol,fourthCol,fifthCol);

//        TextField courseField =new TextField();
//        courseField.setPromptText("输入课程id");
//        Button button =new Button("删除课程");
//        button.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                String course_id =courseField.getText().trim();
//                CourseEntity courseEntity =tableView.getItems().remove()
//                studentDAO.quit(id,course_id);
//
//            }
//        });
//        HBox hBox =new HBox();
//        hBox.getChildren().addAll(courseField,button);
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

    public Stage course_pane_instructor(String id){
        Stage primaryStage =new Stage();
        Scene scene =new Scene(new Group());
        primaryStage.setTitle("show courses");
        primaryStage.setWidth(450);
        primaryStage.setHeight(500);

        TableView<CourseEntity> tableView =new TableView<>();
        ObservableList<CourseEntity> data = FXCollections.observableArrayList(instructorDAO.see_course(id));
        tableView.setItems(data);
        Label label =new Label("课程表");
        label.setFont(new Font("Arial",20));

//String course_name,String time,String room_number,String exam_time,String exam_type
        TableColumn column =new TableColumn("课程id");
        column.setMinWidth(100);
        column.setCellValueFactory(new PropertyValueFactory<>("course_id"));

        TableColumn firstCol =new TableColumn("课程名称");
        firstCol.setMinWidth(100);
        firstCol.setCellValueFactory(new PropertyValueFactory<>("course_name"));

        TableColumn del =new TableColumn("查看花名册");
        del.setCellValueFactory(new PropertyValueFactory<>("look"));

        Callback<TableColumn<CourseEntity,String>,TableCell<CourseEntity,String>> Del =new Callback<TableColumn<CourseEntity,String>, TableCell<CourseEntity,String>>() {
            @Override
            public TableCell call(final TableColumn<CourseEntity,String> param) {
                final TableCell <CourseEntity,String> cell=new TableCell<CourseEntity,String>(){
                    final Button button =new Button("查看");
                    public void updateItem(String item,boolean empty){
                        super.updateItem(item,empty);
                        if (empty){
                            setGraphic(null);
                            setText(null);
                        }else {
                            button.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    CourseEntity courseEntity =  getTableView().getItems().get(getIndex());
//                                    instructorDAO.see_students(courseEntity.getCourse_id());
                                    StudentPane studentPane =new StudentPane();
                                    studentPane.studentStage(courseEntity.getCourse_id()).show();

//                                    data.remove(courseEntity);
//                                    studentDAO.quit(id,courseEntity.getCourse_id());

                                }
                            });
                            setText(null);
                            setGraphic(button);
                        }
                    }
                };
                return cell;
            }
        };
        del.setCellFactory(Del);
//        TableColumn button =new TableColumn("删除课程");
//        button.setMinWidth(100);
//        button.setCellValueFactory(new PropertyValueFactory<>("Dummy"));
        tableView.getColumns().addAll(column,firstCol,del);
        VBox vBox =new VBox();
        vBox.getChildren().addAll(label,tableView);


        ((Group)scene.getRoot()).getChildren().addAll(vBox);


        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setWidth(630);

        primaryStage.setHeight(500);
        return primaryStage;

    }

    public Stage gradesPane(String id){

        Stage primaryStage =new Stage();
        Scene scene =new Scene(new Group());
        primaryStage.setTitle("show courses");
        primaryStage.setWidth(450);
        primaryStage.setHeight(500);

        TableView<CourseEntity> tableView =new TableView<>();
        ObservableList<CourseEntity> data = FXCollections.observableArrayList(instructorDAO.see_course(id));
        tableView.setItems(data);
        Label label =new Label("课程表");
        label.setFont(new Font("Arial",20));

//String course_name,String time,String room_number,String exam_time,String exam_type
        TableColumn column =new TableColumn("课程id");
        column.setMinWidth(100);
        column.setCellValueFactory(new PropertyValueFactory<>("course_id"));

        TableColumn firstCol =new TableColumn("课程名称");
        firstCol.setMinWidth(100);
        firstCol.setCellValueFactory(new PropertyValueFactory<>("course_name"));

        TableColumn del =new TableColumn("给分");
        del.setCellValueFactory(new PropertyValueFactory<>("给分"));

        Callback<TableColumn<CourseEntity,String>,TableCell<CourseEntity,String>> Del =new Callback<TableColumn<CourseEntity,String>, TableCell<CourseEntity,String>>() {
            @Override
            public TableCell call(final TableColumn<CourseEntity,String> param) {
                final TableCell <CourseEntity,String> cell=new TableCell<CourseEntity,String>(){
                    final Button button =new Button("给分");
                    public void updateItem(String item,boolean empty){
                        super.updateItem(item,empty);
                        if (empty){
                            setGraphic(null);
                            setText(null);
                        }else {
                            button.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    CourseEntity courseEntity =  getTableView().getItems().get(getIndex());
//                                    instructorDAO.see_students(courseEntity.getCourse_id());

                                    FileChoose fileChoose =new FileChoose();
                                    fileChoose.fileStage(courseEntity.getCourse_id()).show();
//                                    instructorDAO.readExcel("C:\\Users\\bhlb\\Desktop\\DB\\src\\1.xls",courseEntity.getCourse_id());
//                                    if ()

//                                    studentPane.studentStage(courseEntity.getCourse_id()).show();

//                                    data.remove(courseEntity);
//                                    studentDAO.quit(id,courseEntity.getCourse_id());

                                }
                            });
                            setText(null);
                            setGraphic(button);
                        }
                    }
                };
                return cell;
            }
        };
        del.setCellFactory(Del);
//        TableColumn button =new TableColumn("删除课程");
//        button.setMinWidth(100);
//        button.setCellValueFactory(new PropertyValueFactory<>("Dummy"));
        tableView.getColumns().addAll(column,firstCol,del);
        VBox vBox =new VBox();
        vBox.getChildren().addAll(label,tableView);


        ((Group)scene.getRoot()).getChildren().addAll(vBox);


        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setWidth(630);

        primaryStage.setHeight(500);
        return primaryStage;
    }

}
