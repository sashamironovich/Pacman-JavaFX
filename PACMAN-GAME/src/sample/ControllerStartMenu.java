package sample;
import com.sun.org.apache.xpath.internal.operations.Mod;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerStartMenu {

    @FXML
    private ImageView PlVSCompButton;

    @FXML
    private ImageView PlVSPlButton;

    @FXML
    void PlVSCompHandle(MouseEvent event) throws IOException {
        changeScene(event,"levels.fxml");
        Model.plVsComp = true;
    }

    @FXML
    void PlVSPlHandle(MouseEvent event) throws IOException {
        changeScene(event,"levels.fxml");
        Model.plVsComp = false;
    }

    void changeScene(MouseEvent event, String newScene) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(newScene));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
