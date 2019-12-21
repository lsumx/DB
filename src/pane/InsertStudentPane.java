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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class InsertStudentPane extends Application {

    ManagerDAO managerDAO = new ManagerDAO();
    String id = "root";

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public Stage insert_student(String id) {
        Stage primaryStage =new Stage();
        GridPane gridPane =new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25,25,25,25));
        Text title =new Text("welcome");
        gridPane.add(title,0,0,2,1);

        Label tid = new Label("id");
        gridPane.add(tid,0,1);
        TextField id_Field =new TextField();
        gridPane.add(id_Field,1,1);

        Label name = new Label("name");
        gridPane.add(name,0,2);
        TextField name_Field =new TextField();
        gridPane.add(name_Field,1,2);

        Label dept = new Label("dept");
        gridPane.add(dept,0,3);
        TextField dept_Field =new TextField();
        gridPane.add(dept_Field,1,3);

        Label credit = new Label("credit");
        gridPane.add(credit,0,4);
        TextField credit_Field =new TextField();
        gridPane.add(credit_Field,1,4);

        Label password = new Label("password");
        gridPane.add(password,0,5);
        TextField password_Field =new TextField();
        gridPane.add(password_Field,1,5);

        Label autofile = new Label("文件名");
        gridPane.add(autofile,0,6);
        TextField autofile_Field =new TextField();
        gridPane.add(autofile_Field,1,6);

        Button btn = new Button("submit");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        Text actiontarget = new Text();
        gridPane.add(actiontarget, 1, 8);

        Button btn1 = new Button("批量导入");
        hbBtn.getChildren().add(btn1);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String i_id = id_Field.getText().trim();
                String i_name = name_Field.getText().trim();
                String dept_name = dept_Field.getText().trim();
                String total_credit = credit_Field.getText().trim();
                String pass_word = password_Field.getText().trim();
//                System.out.println(course_id+"\n"+id);
                boolean flag = managerDAO.insert_student(i_name,i_id,dept_name,total_credit, pass_word);
                if (flag){
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("insert student success");
//                    System.out.println(studentDAO.);
                }else {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("insert student fail");
                }

            }
        });

        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = new File(autofile_Field.getText().trim());
                try {
                    Boolean judge = true;
                    Scanner scanner = new Scanner(file);
                    scanner.nextLine();
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine().trim().replace("\n", "");
                        String[] temp = line.split(",");
                        if (temp.length < 5) {
                            actiontarget.setFill(Color.FIREBRICK);
                            actiontarget.setText("表格错误");
                            break;
                        }
                        String i_id = temp[0];
                        String i_name = temp[1];
                        String dept_name = temp[2];
                        String total_credits = temp[3];
                        String pass_word = temp[4];
                        boolean flag = managerDAO.insert_student(i_name,i_id,dept_name,total_credits, pass_word);
                        if (flag == false) {
                            judge = false;
                        }
                    }
                    if (judge){
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText("insert student success");
//                    System.out.println(studentDAO.);
                    }else {
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText("insert student fail");
                    }
                } catch (FileNotFoundException e) {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("文件不存在");
                    e.printStackTrace();
                }
            }
        });

        gridPane.add(hbBtn, 1, 7);
        Scene scene =new Scene(gridPane,300,275);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setWidth(500);

        primaryStage.setHeight(500);

        primaryStage.setResizable(false);

        return primaryStage;
    }

}
