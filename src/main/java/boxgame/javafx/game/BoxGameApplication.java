package boxgame.javafx.game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BoxGameApplication extends Application {

    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainmenu.fxml"));
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle("Box Game");
        stage.show();
    }
}
