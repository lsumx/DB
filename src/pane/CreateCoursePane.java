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

public class CreateCoursePane extends Application {

    ManagerDAO managerDAO = new ManagerDAO();
    String id = "root";

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public Stage create_course(String id) {
        Stage primaryStage =new Stage();
        GridPane gridPane =new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25,25,25,25));
        Text title =new Text("welcome");
        gridPane.add(title,0,0,2,1);

        Label tid = new Label("t_id");
        gridPane.add(tid,0,1);
        TextField id_Field =new TextField();
        gridPane.add(id_Field,1,1);

        Label coursename = new Label("course_name");
        gridPane.add(coursename,0,2);
        TextField coursename_Field =new TextField();
        gridPane.add(coursename_Field,1,2);

        Label courseid = new Label("course_id");
        gridPane.add(courseid,0,3);
        TextField courseid_Field =new TextField();
        gridPane.add(courseid_Field,1,3);

        Label deptname = new Label("dept_name");
        gridPane.add(deptname,0,4);
        TextField deptname_Field =new TextField();
        gridPane.add(deptname_Field,1,4);

        Label credits = new Label("credits");
        gridPane.add(credits,0,5);
        TextField credits_Field =new TextField();
        gridPane.add(credits_Field,1,5);

        Label number = new Label("number");
        gridPane.add(number,0,6);
        TextField number_Field =new TextField();
        gridPane.add(number_Field,1,6);

        Label sectionid = new Label("section_id");
        gridPane.add(sectionid,0,7);
        TextField sectionid_Field =new TextField();
        gridPane.add(sectionid_Field,1,7);

        Label timeslotid = new Label("time_slot_id");
        gridPane.add(timeslotid,0,8);
        TextField timeslotid_Field =new TextField();
        gridPane.add(timeslotid_Field,1,8);

        Label roomnmber = new Label("room_number");
        gridPane.add(roomnmber,0,9);
        TextField roomnumber_Field =new TextField();
        gridPane.add(roomnumber_Field,1,9);

        Label examid = new Label("exam_id");
        gridPane.add(examid,0,10);
        TextField examid_Field =new TextField();
        gridPane.add(examid_Field,1,10);

        Label autofile = new Label("文件名");
        gridPane.add(autofile,0,11);
        TextField autofile_Field =new TextField();
        gridPane.add(autofile_Field,1,11);

        Button btn = new Button("submit");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        Text actiontarget = new Text();
        gridPane.add(actiontarget, 1, 13);

        Button btn1 = new Button("批量导入");
        hbBtn.getChildren().add(btn1);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String t_id = id_Field.getText().trim();
                String course_name = coursename_Field.getText().trim();
                String course_id = courseid_Field.getText().trim();
                String dept_name = deptname_Field.getText().trim();
                String credit_s = credits_Field.getText().trim();
                String num_ber = number_Field.getText().trim();
                String section_id = sectionid_Field.getText().trim();
                String time_slot_id = timeslotid_Field.getText().trim();
                String room_number = roomnumber_Field.getText().trim();
                String exam_id = examid_Field.getText().trim();
//                System.out.println(course_id+"\n"+id);
                boolean flag1 = managerDAO.exam_is_prpoer(exam_id, room_number);
                boolean flag2 = managerDAO.instructor_is_proper(t_id, time_slot_id, course_id, section_id);
                boolean flag3 = managerDAO.time_is_proper(time_slot_id, room_number, course_id, section_id);
                if (flag1 && flag2 && flag3) {
                    boolean flag = managerDAO.create_course(t_id, course_name, course_id, dept_name, credit_s,
                            num_ber, section_id, time_slot_id, room_number, exam_id);
                    if (flag == true) {
                        managerDAO.result = "创建课程成功！";
                    }
                    else {
                        managerDAO.result = "未检测到冲突，但创建课程失败";
                    }
                }
                else if (flag1 == false) {
                    managerDAO.result = "考试冲突！";
                }
                else if (flag2 == false) {
                    managerDAO.result = "教师冲突！";
                }
                else if (flag3 == false) {
                    managerDAO.result = "时间冲突！";
                }
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText(managerDAO.result);

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
                        String line = scanner.nextLine().trim().replaceAll("\n", "");
                        String[] temp = line.split(",");
                        if (temp.length < 10) {
                            actiontarget.setFill(Color.FIREBRICK);
                            actiontarget.setText("表格错误");
                            break;
                        }
                        String t_id = temp[0];
                        String course_name = temp[1];
                        String course_id = temp[2];
                        String dept_name = temp[3];
                        String credit_s = temp[4];
                        String num_ber = temp[5];
                        String section_id = temp[6];
                        String time_slot_id = temp[7].replace("|",",");
                        String room_number = temp[8];
                        String exam_id = temp[9];

                        boolean flag1 = managerDAO.exam_is_prpoer(exam_id, room_number);
                        boolean flag2 = managerDAO.instructor_is_proper(t_id, time_slot_id, course_id, section_id);
                        boolean flag3 = managerDAO.time_is_proper(time_slot_id, room_number, course_id, section_id);
                        if (flag1 && flag2 && flag3) {
                            boolean flag = managerDAO.create_course(t_id, course_name, course_id, dept_name, credit_s,
                                    num_ber, section_id, time_slot_id, room_number, exam_id);
                            if (flag == true) {
                                managerDAO.result = "创建课程成功！";
                            }
                            else {
                                managerDAO.result = "未检测到冲突，但创建课程失败";
                            }
                        }
                        else if (flag1 == false) {
                            managerDAO.result = "考试冲突！";
                        }
                        else if (flag2 == false) {
                            managerDAO.result = "教师冲突！";
                        }
                        else if (flag3 == false) {
                            managerDAO.result = "时间冲突！";
                        }
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText(managerDAO.result);
                    }
                } catch (FileNotFoundException e) {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("文件不存在");
                    e.printStackTrace();
                }
            }
        });

        gridPane.add(hbBtn, 1, 12);
        Scene scene =new Scene(gridPane,300,275);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setWidth(500);
        primaryStage.setHeight(500);
//        primaryStage.setResizable(false);

        return primaryStage;
    }
}
