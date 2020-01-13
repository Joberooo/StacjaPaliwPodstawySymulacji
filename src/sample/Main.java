package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        //Controller controller = loader.getController();

        primaryStage.setTitle("Stacja Paliw - Patryk BARCZAK - I7B1S1");

        primaryStage.setScene(new Scene(root, 1000, 500));

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
