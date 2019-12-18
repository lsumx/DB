package pane;

import dao.StudentDAO;
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

public class SelectForCoursePane {
    StudentDAO studentDAO =new StudentDAO();
    public Stage selectForCoursePane(String id){
        Stage primaryStage =new Stage();
        GridPane gridPane =new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25,25,25,25));
        Text title =new Text("welcome");
        gridPane.add(title,0,0,2,1);
        Label courseId=new Label("课程序号");
        gridPane.add(courseId,0,1);
        TextField courseField =new TextField();
        gridPane.add(courseField,1,1);
        Label courseText =new Label("申请理由");
        gridPane.add(courseText,0,2);
        TextField textField =new TextField();
        gridPane.add(textField,1,2);

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
                String text =textField.getText().trim();
                if (text.equals("") ||text ==null){
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("申请理由不能为空");

                }
                if (course_id.equals("")||course_id ==null){
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("课程号不能为空");
                }else {
                    if (studentDAO.select_for_course(id,course_id,text)){
                        actiontarget.setFill(Color.BLUE);
                        actiontarget.setText("你已经完成该选课申请");
                    }else {
                        if (!studentDAO.is_time_valid()){
                            actiontarget.setText("不在选课申请时间段内");
                            actiontarget.setFill(Color.FIREBRICK);
                        }else if (!studentDAO.can_select(id,course_id)){
                            actiontarget.setFill(Color.FIREBRICK);
                            actiontarget.setText("不符合选课申请条件");
                        }else {
                            actiontarget.setText("你已经打过该课程的选课申请或者你已退过该课程");
                        }
                    }
                }

            }
        });

        gridPane.add(hbBtn, 1, 4);
        Scene scene =new Scene(gridPane,300,275);
        primaryStage.setScene(scene);

        primaryStage.setWidth(500);

        primaryStage.setHeight(500);

        primaryStage.setResizable(false);
        primaryStage.show();
        return primaryStage;
    }
}
