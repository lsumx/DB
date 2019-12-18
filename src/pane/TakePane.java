package pane;

import dao.InstructorDAO;
import dao.StudentDAO;
import entity.CourseEntity;
import entity.TakeEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

public class TakePane {
    InstructorDAO instructorDAO =new InstructorDAO();
    StudentDAO studentDAO =new StudentDAO();
    public Stage takePane(String id){
        Stage primaryStage =new Stage();
        Scene scene =new Scene(new Group());
        primaryStage.setTitle("show courses");
        primaryStage.setWidth(450);
        primaryStage.setHeight(500);

        TableView<TakeEntity> tableView =new TableView<>();
        ObservableList<TakeEntity> data = FXCollections.observableArrayList(instructorDAO.see_selections(id));
        tableView.setItems(data);
        Label label =new Label("选课申请");
        label.setFont(new Font("Arial",20));

//String course_name,String time,String room_number,String exam_time,String exam_type
        TableColumn column =new TableColumn("课程id");
        column.setMinWidth(100);
        column.setCellValueFactory(new PropertyValueFactory<>("course_id"));


        TableColumn firstCol =new TableColumn("课程名称");
        firstCol.setMinWidth(100);
        firstCol.setCellValueFactory(new PropertyValueFactory<>("course_name"));

        TableColumn secCol = new TableColumn("学生id");
        secCol.setMinWidth(150);
        secCol.setCellValueFactory(new PropertyValueFactory<>("s_id"));

        TableColumn thirdCol =new TableColumn("学生姓名");
        thirdCol.setMinWidth(100);
        thirdCol.setCellValueFactory(new PropertyValueFactory<>("s_name"));

        TableColumn fourthCol =new TableColumn("申请理由");
        thirdCol.setMinWidth(100);
        thirdCol.setCellValueFactory(new PropertyValueFactory<>("reasons"));

        TableColumn del =new TableColumn("同意申请");
        del.setCellValueFactory(new PropertyValueFactory<>("同意"));

        TableColumn add =new TableColumn("拒绝");
        add.setCellValueFactory(new PropertyValueFactory<>("拒绝"));

        Callback<TableColumn<TakeEntity,String>, TableCell<TakeEntity,String>> Del =new Callback<TableColumn<TakeEntity,String>, TableCell<TakeEntity,String>>() {
            @Override
            public TableCell call(final TableColumn<TakeEntity,String> param) {
                final TableCell <TakeEntity,String> cell=new TableCell<TakeEntity,String>(){
                    final Button button =new Button("同意");
                    public void updateItem(String item,boolean empty){
                        super.updateItem(item,empty);
                        if (empty){
                            setGraphic(null);
                            setText(null);
                        }else {
                            button.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    TakeEntity takeEntity =  getTableView().getItems().get(getIndex());
                                    data.remove(takeEntity);
                                    instructorDAO.deal_selections(takeEntity.getS_id(),takeEntity.getCourse_id());
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


        Callback<TableColumn<TakeEntity,String>, TableCell<TakeEntity,String>> Add =new Callback<TableColumn<TakeEntity,String>, TableCell<TakeEntity,String>>() {
            @Override
            public TableCell call(final TableColumn<TakeEntity,String> param) {
                final TableCell <TakeEntity,String> cell=new TableCell<TakeEntity,String>(){
                    final Button button =new Button("拒绝");
                    public void updateItem(String item,boolean empty){
                        super.updateItem(item,empty);
                        if (empty){
                            setGraphic(null);
                            setText(null);
                        }else {
                            button.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    TakeEntity takeEntity =  getTableView().getItems().get(getIndex());
                                    data.remove(takeEntity);
                                    instructorDAO.refuse(takeEntity.getS_id(),takeEntity.getCourse_id());
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
        add.setCellFactory(Del);

        tableView.getColumns().addAll(column,firstCol,secCol,thirdCol,fourthCol,del,add);

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
