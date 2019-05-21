package sample;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class GamePlayAloneController implements EventHandler<KeyEvent> {
    final private static double FRAMES_PER_SECOND = 5.0;

    @FXML private View pacManView;
    @FXML private BorderPane playingBorder;
    @FXML private Text showScore;
    private Model pacManModel;

    private Timer timer;
    private static int ghostEatingModeCounter;
    private boolean paused;

    private boolean isAlertOpen = false;

    public GamePlayAloneController() {
        this.paused = false;
    }

    /**
     * Initialize and update the model and view from the first txt file and starts the timer.
     */
    public void initialize() throws IOException {
        String file = Model.levelName;
        this.pacManModel = new Model();
        this.update(Model.Direction.NONE);
        ghostEatingModeCounter = 25;
        this.startTimer();
    }

    /**
     * Schedules the model to update based on the timer.
     */
    private void startTimer() {
        this.timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        try {
                            update(pacManModel.getCurrentDirection());
                            if (pacManModel.isGameOver()) {
                                Stage stage = (Stage) playingBorder.getScene().getWindow();
                                stage.close();
                                Parent root = FXMLLoader.load(getClass().getResource("gameOver.fxml"));
                                Scene scene = new Scene(root);
                                stage = new Stage();
                                stage.setScene(scene);
                                stage.show();
                                cancel();
                            }
                            if (pacManModel.isYouWon()) {
                                Stage stage = (Stage) playingBorder.getScene().getWindow();
                                stage.close();
                                Parent root = FXMLLoader.load(getClass().getResource("youWon.fxml"));
                                Scene scene = new Scene(root);
                                stage = new Stage();
                                stage.setScene(scene);
                                stage.show();
                                cancel();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        long frameTimeInMilliseconds = (long)(1000.0 / FRAMES_PER_SECOND);
        this.timer.schedule(timerTask, 0, frameTimeInMilliseconds);
    }

    /**
     * Steps the PacManModel, updates the view, updates score and level, displays Game Over/You Won, and instructions of how to play
     * @param direction the most recently inputted direction for PacMan to move in
     */
    private void update(Model.Direction direction) throws IOException {
        this.showScore.setText(Integer.toString(pacManModel.score));
        this.pacManModel.step(direction);
        this.pacManView.update(pacManModel);
        //when PacMan is in ghostEatingMode, count down the ghostEatingModeCounter to reset ghostEatingMode to false when the counter is 0
        if (pacManModel.isGhostEatingMode()) {
            ghostEatingModeCounter--;
        }
        if (ghostEatingModeCounter == 0 && pacManModel.isGhostEatingMode()) {
            pacManModel.setGhostEatingMode(false);
        }
    }

    /**
     * Takes in user keyboard input to control the movement of PacMan and start new games
     * @param keyEvent user's key click
     */
    @Override
    public void handle(KeyEvent keyEvent) {
        boolean keyRecognized = true;
        KeyCode code = keyEvent.getCode();
        Model.Direction direction = Model.Direction.NONE;
        if (code == KeyCode.LEFT) {
            direction = Model.Direction.LEFT;
        } else if (code == KeyCode.RIGHT) {
            direction = Model.Direction.RIGHT;
        } else if (code == KeyCode.UP) {
            direction = Model.Direction.UP;
        } else if (code == KeyCode.DOWN) {
            direction = Model.Direction.DOWN;
        } else if (code == KeyCode.G) {
            pause();
            this.pacManModel.startNewGame();
            paused = false;
            this.startTimer();
        } else {
            keyRecognized = false;
        }
        if (keyRecognized) {
            keyEvent.consume();
            pacManModel.setCurrentDirection(direction);
        }
    }

   //Pause the timer
    public void pause() {
        this.timer.cancel();
        this.paused = true;
    }

    public static void setGhostEatingModeCounter() {
        ghostEatingModeCounter = 25;
    }

    public static int getGhostEatingModeCounter() {
        return ghostEatingModeCounter;
    }

}
