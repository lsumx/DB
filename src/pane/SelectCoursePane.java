package pane;

import dao.StudentDAO;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SelectCoursePane  {
    StudentDAO studentDAO =new StudentDAO();
    String id ="";



    public Stage select_course_pane(String id){
        Stage primaryStage =new Stage();
        GridPane gridPane =new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25,25,25,25));
        Text title =new Text("welcome");
        gridPane.add(title,0,0,2,1);
        Label course_id =new Label("课程id");
        gridPane.add(course_id,0,1);
        TextField courseField =new TextField();
        gridPane.add(courseField,1,1);
        Button btn = new Button("submit");

        HBox hbBtn = new HBox(10);

        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);

        hbBtn.getChildren().add(btn);

        final Text actiontarget = new Text();

        gridPane.add(actiontarget, 1, 6);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String course_id =courseField.getText().trim();
//                System.out.println(course_id+"\n"+id);
                boolean flag =studentDAO.select_course(id,course_id);
                if (flag){
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("select course success");
//                    System.out.println(studentDAO.);
                }else {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("select course fail");
                    if (!studentDAO.select_valid(id,course_id)){
                        actiontarget.setText("选课人数已满");
                        actiontarget.setFill(Color.FIREBRICK);
                    }else
                    if (!studentDAO.is_time_valid()){
                        actiontarget.setText("不在选课时间段内");
                        actiontarget.setFill(Color.FIREBRICK);
                    }else {
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText("时间冲突");
                    }
                }

            }
        });

        gridPane.add(hbBtn, 1, 4);
        Scene scene =new Scene(gridPane,300,275);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setWidth(500);

        primaryStage.setHeight(500);

        primaryStage.setResizable(false);
        return primaryStage;
    }


}
