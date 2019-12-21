package pane;

import dao.ManagerDAO;
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

public class DeleteCoursePane extends Application {

    ManagerDAO managerDAO = new ManagerDAO();
    String id = "root";

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public Stage delete_course(String id) {
        Stage primaryStage =new Stage();
        GridPane gridPane =new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25,25,25,25));
        Text title =new Text("welcome");
        gridPane.add(title,0,0,2,1);

        Label courseid = new Label("course_id");
        gridPane.add(courseid,0,1);
        TextField course_Field =new TextField();
        gridPane.add(course_Field,1,1);

        Label sectionid = new Label("sec_id");
        gridPane.add(sectionid,0,2);
        TextField section_Field =new TextField();
        gridPane.add(section_Field,1,2);

        Button btn = new Button("submit");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        Text actiontarget = new Text();
        gridPane.add(actiontarget, 1, 4);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String course_id = course_Field.getText().trim();
                String sec_id = section_Field.getText().trim();

                boolean flag = managerDAO.delete_course(course_id, sec_id);

                if (flag) {
                    managerDAO.result = "删除课程成功";
                }
                else {
                    if (managerDAO.error == 6) {
                        managerDAO.result = "有人选课或课程不存在，无法删除";
                    }
                    else {
                        managerDAO.result = "删除课程出现错误";
                    }
                }
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText(managerDAO.result);

            }
        });
        gridPane.add(hbBtn, 1, 11);
        Scene scene =new Scene(gridPane,300,275);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setWidth(500);
        primaryStage.setHeight(500);
        primaryStage.setResizable(false);
        return primaryStage;
    }
}
