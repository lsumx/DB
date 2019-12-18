package Utils;

import java.io.File;

import dao.InstructorDAO;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import pane.StudentPane;

import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;

public class FileChoose {

//    InstructorDAO instructorDAO =new InstructorDAO();
    public Stage fileStage(String course_id)  {
        // Create a pane to hold a button
        Stage primaryStage =new Stage();
        GridPane pane = new GridPane();
        pane.setStyle("-fx-border-color: green;");
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setHgap(10);
        pane.setVgap(10);

        // Create a button to choose a file
        Button btChooseFile = new Button("Choose File");
        pane.add(btChooseFile, 0, 0);


        final Text actiontarget = new Text();
        pane.add(actiontarget,0,1);

        // Set the primary stage properties
        primaryStage.setScene(new Scene(pane, 400, 200));
        primaryStage.setTitle("Starting...");
        primaryStage.setResizable(false);
        primaryStage.show();

        // 
        btChooseFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Choose File");
//                fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));
//                fileChooser.getExtensionFilters().add(new ExtensionFilter("All Files", "*.*"));
                fileChooser.getExtensionFilters().addAll(new ExtensionFilter( "All Files", "*.*"));
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    System.out.println(file.getAbsolutePath());
                    InstructorDAO instructorDAO =new InstructorDAO();
                    if (instructorDAO.readExcel(file.getAbsolutePath(),course_id)){
                        actiontarget.setText("文件上传成功");
                        actiontarget.setFill(Color.BLUE);
                        primaryStage.close();
                        StudentPane studentPane =new StudentPane();
                        studentPane.studentGrades(course_id).show();

                    }else {
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText("文件有问题");
                    }

                }
            }
        });


        return primaryStage;
    }
}