package pane;

import dao.InitDAO;
import dao.InstructorDAO;
import dao.ManagerDAO;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginPane extends Application {
    String id ="";
    String pwd ="";
    StudentDAO studentDAO =new StudentDAO();
    InstructorDAO instructorDAO =new InstructorDAO();
    GradesPane gradesPane =new GradesPane();
    SelectPane selectPane =new SelectPane(id);
    ManagerDAO managerDAO = new ManagerDAO();
    InitDAO initDAO =new InitDAO();
//    InitDAO initDAO = new InitDAO();
    ManagerSelectPane managerSelectPane = new ManagerSelectPane();
//    RootDAO rootDAO =new RootDAO();

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
        Label password =new Label("密码");
        gridPane.add(password,0,2);
        PasswordField pwdTextField =new PasswordField();
        gridPane.add(pwdTextField,1,2);

        boolean consequence = true;
        consequence = initDAO.check_exam_proper();
        if (consequence == false) {
            Text temp = new Text("考试有冲突！");
            gridPane.add(temp, 0, 2, 2, 1);
            consequence = true;
        }
        consequence = initDAO.check_instructor_proper();
        if (consequence == false) {
            Text temp = new Text("教师有冲突！");
            gridPane.add(temp, 0, 3, 2, 1);
            consequence = true;
        }
        consequence = initDAO.check_time_proper();
        if (consequence == false) {
            Text temp = new Text("开课有冲突！");
            gridPane.add(temp, 0, 4, 2, 1);
            consequence = true;
        }
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
                pwd=pwdTextField.getText().trim();
                if (id.contains("S")){
                    boolean flag =studentDAO.login_success(id,pwd);
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
                    boolean flag = managerDAO.login_success(id,pwd);
                    if (flag) {
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText("success");
                        managerSelectPane.select_pane(id).show();
                        primaryStage.close();
                    }
                    else {
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText("fail");
                        System.exit(0);
                    }
                }else if (id.contains("T")){
                    boolean flag =instructorDAO.login_success(id,pwd);
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
