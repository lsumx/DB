package pane;

import dao.InstructorDAO;
import dao.RootDAO;
import dao.StudentDAO;
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

public class LoginPane extends Application {
    String id ="";
    StudentDAO studentDAO =new StudentDAO();
    InstructorDAO instructorDAO =new InstructorDAO();
    GradesPane gradesPane =new GradesPane();
    SelectPane selectPane =new SelectPane(id);
    RootDAO rootDAO =new RootDAO();

    public void start(Stage primaryStage){
        GridPane gridPane =new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25,25,25,25));
        Text title =new Text("welcome");
        gridPane.add(title,0,0,2,1);
        Label username =new Label("工号");
        gridPane.add(username,0,1);
        TextField userTextField =new TextField();
        gridPane.add(userTextField,1,1);

        Button btn = new Button("Sign in");

        HBox hbBtn = new HBox(10);

        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);

        hbBtn.getChildren().add(btn);

        final Text actiontarget = new Text();

        gridPane.add(actiontarget, 1, 6);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                id =userTextField.getText().trim();
                if (id.contains("S")){
                    boolean flag =studentDAO.login_success(id);
                    if (flag){
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText("success");
                        selectPane.select_pane(id).show();
                        primaryStage.close();

//                        primaryStage.setScene(selectPane.select_pane(id));
//                    System.out.println(studentDAO.);

                    }else {
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText("fail");
                        System.exit(0);
                    }
                }else if (id.contains("r")){
                    if (rootDAO.login_success(id)){

                    }else {
                        System.exit(0);
                    }
                }else if (id.contains("T")){
                    boolean flag =instructorDAO.login_success(id);
                    if (flag){
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText("success");
                        selectPane.select_pane_instructor(id).show();
                        primaryStage.close();

//                        primaryStage.setScene(selectPane.select_pane(id));
//                    System.out.println(studentDAO.);

                    }else {
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText("fail");
                        System.exit(0);
                    }
                }


            }
        });

        gridPane.add(hbBtn, 1, 4);
        Scene scene =new Scene(gridPane);
        primaryStage.setScene(scene);

        primaryStage.setWidth(500);

        primaryStage.setHeight(500);

        primaryStage.setResizable(false);
        primaryStage.show();

    }



    public static void main(String[] args) {
     launch(args);

    }
}
