package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class lvlController {

    @FXML
    private Pane lvl1Button;

    @FXML
    private Pane lvl3Button;

    @FXML
    private Pane lvl5Button;

    @FXML
    private Pane lvl2Button;

    @FXML
    private Pane lvl4Button;


    @FXML
    void goToLevels(MouseEvent event) throws Exception {
        String choice = ((Pane) event.getSource()).getId();
        if (choice.equals("lvl1Button")){
            Model.levelName = "level1.txt";
        }else if(choice.equals("lvl2Button")){
            Model.levelName = "level2.txt";
        }else if(choice.equals("lvl3Button")){
            Model.levelName = "level3.txt";
        }else if(choice.equals("lvl4Button")){
            Model.levelName = "level4.txt";
        }else if(choice.equals("lvl5Button")){
            Model.levelName = "level5.txt";
        }
        if(Model.plVsComp == true){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gamePlayAlone.fxml"));
            Parent root = loader.load();
            GamePlayAloneController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            root.setOnKeyPressed(controller);
            root.requestFocus();
        }else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gamePlayMultiple.fxml"));
            Parent root = loader.load();
            GamePlayMultipleController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            root.setOnKeyPressed(controller);
            root.requestFocus();
        }
    }

//    void changeScene(MouseEvent event, String newScene) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource(newScene));
//        Parent root = loader.load();
//        Controller controller = loader.getController();
//        Controle
//        Scene scene = new Scene(root);
//        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        stage.setScene(scene);
//        stage.show();
//        root.setOnKeyPressed(controller);
//        root.requestFocus();
//    }

}
