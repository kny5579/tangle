package backend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/order.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.setTitle("주문 등록");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}