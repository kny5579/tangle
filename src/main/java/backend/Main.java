package backend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        Label label = new Label("주문 관리 프로그램 시작!");
        Scene scene = new Scene(label, 400, 200);
        stage.setScene(scene);
        stage.setTitle("Tangle - 주문 관리");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}