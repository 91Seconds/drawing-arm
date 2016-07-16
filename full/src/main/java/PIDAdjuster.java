/**
 * Created by surface on 16/07/2016.
 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PIDAdjuster extends Application implements Runnable {

    Pane root;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        root = new GridPane();
        GridPane g=(GridPane)root;
        g.setAlignment(Pos.CENTER);
        //root.getChildren().add(g);
        g.setPadding(new Insets(10,10,10,10));
        g.setVgap(10);
        g.setHgap(10);
        setUpSliders(g);
        setUpText(g);

        Scene theScene = new Scene(root,400,400);
        primaryStage.setScene(theScene);
        primaryStage.setTitle("Adjustments");
        primaryStage.show();

    }

    private void setUpSliders(GridPane g) {
        Slider p = new Slider();
        Slider i = new Slider();
        Slider d = new Slider();
        configureSliders(p);
        configureSliders(i);
        configureSliders(d);
        g.add(p,0,0);
        g.add(i,1,0);
        g.add(d,2,0);
        p.setOnMouseReleased(e-> {
            Main.kP=p.getValue();
        });
        i.setOnMouseReleased(e-> {
            Main.kI=i.getValue();
        });
        d.setOnMouseReleased(e-> {
            Main.kD=d.getValue();
        });
    }

    private void setUpText(GridPane g) {
        Text kp = new Text("kP");
        Text ki = new Text("kI");
        Text kd = new Text("kD");
        g.add(kp,0,1);
        g.add(ki,1,1);
        g.add(kd,2,1);
    }

    private void configureSliders(Slider sl) {
        sl.setOrientation(Orientation.VERTICAL);
        sl.setMin(-10);
        sl.setMax(10);
        sl.setMinorTickCount(10);
        sl.setMajorTickUnit(1);
        sl.setShowTickLabels(true);
        sl.setShowTickMarks(true);
        sl.setPrefHeight(600);
    }

    @Override
    public void run() {
        System.out.println("run called");
        launch(null);
    }
}
