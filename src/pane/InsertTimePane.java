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

public class InsertTimePane extends Application {

    ManagerDAO managerDAO = new ManagerDAO();
    String id = "root";

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public Stage insert_time(String id) {
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

        Label day = new Label("day");
        gridPane.add(day,0,2);
        TextField day_Field =new TextField();
        gridPane.add(day_Field,1,2);

        Label start = new Label("start");
        gridPane.add(start,0,3);
        TextField start_Field =new TextField();
        gridPane.add(start_Field,1,3);

        Label end = new Label("end");
        gridPane.add(end,0,4);
        TextField end_Field =new TextField();
        gridPane.add(end_Field,1,4);

        Label autofile = new Label("文件名");
        gridPane.add(autofile,0,5);
        TextField autofile_Field =new TextField();
        gridPane.add(autofile_Field,1,5);

        Button btn = new Button("submit");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        Text actiontarget = new Text();
        gridPane.add(actiontarget, 1, 7);

        Button btn1 = new Button("批量导入");
        hbBtn.getChildren().add(btn1);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String timeid = id_Field.getText().trim();
                String tday = day_Field.getText().trim();
                String tstart = start_Field.getText().trim();
                String tend = end_Field.getText().trim();
//                System.out.println(course_id+"\n"+id);
                if (timeid.trim().length() == 0 || !timeid.matches("[0-9A-Za-z_]*")) {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("exam_id错误");
                }
                else {
                    boolean flag = managerDAO.insert_time(timeid, tday, tstart, tend);
                    if (flag) {
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText("insert time_slot_id success");
//                    System.out.println(studentDAO.);
                    } else {
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText("insert time_slot_id fail");
                    }
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
                        if (temp.length < 4) {
                            actiontarget.setFill(Color.FIREBRICK);
                            actiontarget.setText("表格错误");
                            break;
                        }
                        String i_id = temp[0];
                        String i_day = temp[1];
                        String i_start = temp[2];
                        String i_end = temp[3];

                        boolean flag = managerDAO.insert_time(i_id,i_day,i_start,i_end);
                        if (flag == false) {
                            judge = false;
                        }
                    }
                    if (judge){
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText("insert time success");
//                    System.out.println(studentDAO.);
                    }else {
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText("insert time fail");
                    }
                } catch (FileNotFoundException e) {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("文件不存在");
                    e.printStackTrace();
                }
            }
        });

        gridPane.add(hbBtn, 1, 6);
        Scene scene =new Scene(gridPane,300,275);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setWidth(500);

        primaryStage.setHeight(500);

        primaryStage.setResizable(false);

        return primaryStage;
    }

}
