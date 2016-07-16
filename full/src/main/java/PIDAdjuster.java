/**
 * Created by surface on 16/07/2016.
 */

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class PIDAdjuster extends Application implements Runnable {

    HBox root;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        root = new HBox();

        setUPSliders();

        Scene theScene = new Scene(root,400,400);
        primaryStage.setScene(theScene);
        primaryStage.show();

    }

    private void setUPSliders() {
        Slider pSlider = new Slider();
        Slider iSlider = new Slider();
        Slider dSlider = new Slider();
        pSlider.setOrientation(Orientation.VERTICAL);
        iSlider.setOrientation(Orientation.VERTICAL);
        dSlider.setOrientation(Orientation.VERTICAL);
        root.getChildren().addAll(pSlider,iSlider,dSlider);
    }

    @Override
    public void run() {
        System.out.println("run called");
        launch(null);
    }
}
