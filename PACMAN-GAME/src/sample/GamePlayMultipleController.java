package sample;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class GamePlayMultipleController implements EventHandler<KeyEvent> {
    final private static double FRAMES_PER_SECOND = 5.0;

    @FXML
    private View pacManView;
    @FXML private BorderPane playingBorder;
    @FXML private Text score1;
    @FXML private Text score2;
    private Model pacManModel1;
    private Model pacManModel2;
    Model.Direction direction1 = Model.Direction.NONE;
    Model.Direction direction2 = Model.Direction.NONE;

    private Timer timer;
    private static int ghostEatingModeCounter;
    private boolean paused;

    private boolean isAlertOpen = false;

    public GamePlayMultipleController() {
        this.paused = false;
    }

    /**
     * Initialize and update the model and view from the first txt file and starts the timer.
     */
    public void initialize() throws IOException {
        String file = Model.levelName;
        pacManModel1 = new Model();
        pacManModel2 = new Model();
        this.update(Model.Direction.NONE,Model.Direction.NONE);
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
                            update(pacManModel1.getCurrentDirection(),pacManModel2.getCurrentDirection());

                            if (pacManModel1.isGameOver() || pacManModel2.isGameOver()) {
                                Stage stage = (Stage) playingBorder.getScene().getWindow();
                                stage.close();
                                Parent root = FXMLLoader.load(getClass().getResource("gameOver.fxml"));
                                Scene scene = new Scene(root);
                                stage = new Stage();
                                stage.setScene(scene);
                                stage.show();
                                cancel();
                            }
                            if (pacManModel1.isYouWon()) {
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
     * @param direction1 the most recently inputted direction for PacMan to move in
     */
    private void update(Model.Direction direction1,Model.Direction direction2) throws IOException {
        score1.setText(Integer.toString(pacManModel1.score));
        score2.setText(Integer.toString(pacManModel2.score));

        Model.secondMoves = false;
        pacManModel1.step(direction1);
        Model.secondMoves = true;
        pacManModel2.step(direction2);

        this.pacManView.update(pacManModel1,pacManModel2);
        //when PacMan is in ghostEatingMode, count down the ghostEatingModeCounter to reset ghostEatingMode to false when the counter is 0
        if (Model.isGhostEatingMode()) {
            ghostEatingModeCounter--;
        }
        if (ghostEatingModeCounter == 0 && Model.isGhostEatingMode()) {
            Model.setGhostEatingMode(false);
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


        if (code == KeyCode.LEFT) {
            direction1 = Model.Direction.LEFT;
        } else if (code == KeyCode.RIGHT) {
            direction1 = Model.Direction.RIGHT;
        } else if (code == KeyCode.UP) {
            direction1 = Model.Direction.UP;
        } else if (code == KeyCode.DOWN) {
            direction1 = Model.Direction.DOWN;
        }
        if (keyRecognized) {
            keyEvent.consume();
            pacManModel1.setCurrentDirection(direction1);
        }

        if (code == KeyCode.A) {
            direction2 = Model.Direction.LEFT;
        } else if (code == KeyCode.D) {
            direction2 = Model.Direction.RIGHT;
        } else if (code == KeyCode.W) {
            direction2 = Model.Direction.UP;
        } else if (code == KeyCode.S) {
            direction2 = Model.Direction.DOWN;
        } else {
            keyRecognized = false;
        }
        if (keyRecognized) {
            keyEvent.consume();
            pacManModel2.setCurrentDirection(direction2);
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
