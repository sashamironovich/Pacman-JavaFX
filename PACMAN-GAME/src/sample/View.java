package sample;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class View extends Group {
    public final static double CELL_WIDTH = 450/19;

    @FXML
    private int rowCount;
    @FXML private int columnCount;
    private ImageView[][] cellViews;
    private Image pacmanRightImage;
    private Image pacmanUpImage;
    private Image pacmanDownImage;
    private Image pacmanLeftImage;
    private Image ghost1Image;
    private Image ghost2Image;
    private Image ghost3Image;
    private Image ghost4Image;
    private Image blueGhostImage;
    private Image wallImage;
    private Image bigDotImage;
    private Image smallDotImage;
    private Image pacman2RightImage;
    private Image pacman2UpImage;
    private Image pacman2DownImage;
    private Image pacman2LeftImage;

    /**
     * Initializes the values of the image instance variables from files
     */
    public View() {
        this.pacmanRightImage = new Image(getClass().getResourceAsStream("/resources/pacmanRight.gif"));
        this.pacmanUpImage = new Image(getClass().getResourceAsStream("/resources/pacmanUp.gif"));
        this.pacmanDownImage = new Image(getClass().getResourceAsStream("/resources/pacmanDown.gif"));
        this.pacmanLeftImage = new Image(getClass().getResourceAsStream("/resources/pacmanLeft.gif"));
        if(Model.plVsComp == false){
            this.pacman2RightImage = new Image(getClass().getResourceAsStream("/resources/pacman2Right.gif"));
            this.pacman2UpImage = new Image(getClass().getResourceAsStream("/resources/pacman2Up.gif"));
            this.pacman2DownImage = new Image(getClass().getResourceAsStream("/resources/pacman2Down.gif"));
            this.pacman2LeftImage = new Image(getClass().getResourceAsStream("/resources/pacman2Left.gif"));
        }
        this.ghost1Image = new Image(getClass().getResourceAsStream("/resources/redghost.gif"));
        this.ghost2Image = new Image(getClass().getResourceAsStream("/resources/ghost2.gif"));
        if(Model.levelName.equals("level4.txt")||Model.levelName.equals("level5.txt")){
            this.ghost3Image = new Image(getClass().getResourceAsStream("/resources/ghost1.gif"));
        }
        if(Model.levelName.equals("level5.txt")){
            this.ghost4Image = new Image(getClass().getResourceAsStream("/resources/ghost3.gif"));
        }
        this.blueGhostImage = new Image(getClass().getResourceAsStream("/resources/blueghost.gif"));
        this.wallImage = new Image(getClass().getResourceAsStream("/resources/wall.png"));
        this.bigDotImage = new Image(getClass().getResourceAsStream("/resources/whitedot.png"));
        this.smallDotImage = new Image(getClass().getResourceAsStream("/resources/smalldot.png"));
    }

    /**
     * Constructs an empty grid of ImageViews
     */
    private void initializeGrid() {
        if (this.rowCount > 0 && this.columnCount > 0) {
            this.cellViews = new ImageView[this.rowCount][this.columnCount];
            for (int row = 0; row < this.rowCount; row++) {
                for (int column = 0; column < this.columnCount; column++) {
                    ImageView imageView = new ImageView();
                    imageView.setX((double)column * CELL_WIDTH);
                    imageView.setY((double)row * CELL_WIDTH);
                    imageView.setFitWidth(CELL_WIDTH);
                    imageView.setFitHeight(CELL_WIDTH);
                    this.cellViews[row][column] = imageView;
                    this.getChildren().add(imageView);
                }
            }
        }
    }

    // Updates the view to reflect the state of the model
    public void update(Model model) {
        assert model.getRowCount() == this.rowCount && model.getColumnCount() == this.columnCount;
        //for each ImageView, set the image to correspond with the CellValue of that cell
        for (int row = 0; row < this.rowCount; row++){
            for (int column = 0; column < this.columnCount; column++){
                Model.CellValue value = model.getCellValue(row, column);
                if (value == Model.CellValue.WALL) {
                    this.cellViews[row][column].setImage(this.wallImage);
                }
                else if (value == Model.CellValue.BIGDOT) {
                    this.cellViews[row][column].setImage(this.bigDotImage);
                }
                else if (value == Model.CellValue.SMALLDOT) {
                    this.cellViews[row][column].setImage(this.smallDotImage);
                }
                else {
                    this.cellViews[row][column].setImage(null);
                }
                //check which direction PacMan is going in and display the corresponding image
                if (row == model.getPacmanLocation().getX() && column == model.getPacmanLocation().getY() && (model.getLastDirection() == Model.Direction.RIGHT || model.getLastDirection() == Model.Direction.NONE)) {
                    this.cellViews[row][column].setImage(this.pacmanRightImage);
                }
                else if (row == model.getPacmanLocation().getX() && column == model.getPacmanLocation().getY() && model.getLastDirection() == Model.Direction.LEFT) {
                    this.cellViews[row][column].setImage(this.pacmanLeftImage);
                }
                else if (row == model.getPacmanLocation().getX() && column == model.getPacmanLocation().getY() && model.getLastDirection() == Model.Direction.UP) {
                    this.cellViews[row][column].setImage(this.pacmanUpImage);
                }
                else if (row == model.getPacmanLocation().getX() && column == model.getPacmanLocation().getY() && model.getLastDirection() == Model.Direction.DOWN) {
                    this.cellViews[row][column].setImage(this.pacmanDownImage);
                }

                if (row == model.getPacmanLocation().getX() && column == model.getPacmanLocation().getY() && (model.getLastDirection() == Model.Direction.RIGHT || model.getLastDirection() == Model.Direction.NONE)) {
                    this.cellViews[row][column].setImage(this.pacmanRightImage);
                }
                else if (row == model.getPacmanLocation().getX() && column == model.getPacmanLocation().getY() && model.getLastDirection() == Model.Direction.LEFT) {
                    this.cellViews[row][column].setImage(this.pacmanLeftImage);
                }
                else if (row == model.getPacmanLocation().getX() && column == model.getPacmanLocation().getY() && model.getLastDirection() == Model.Direction.UP) {
                    this.cellViews[row][column].setImage(this.pacmanUpImage);
                }
                else if (row == model.getPacmanLocation().getX() && column == model.getPacmanLocation().getY() && model.getLastDirection() == Model.Direction.DOWN) {
                    this.cellViews[row][column].setImage(this.pacmanDownImage);
                }
                //make ghosts "blink" towards the end of ghostEatingMode (display regular ghost images on alternating updates of the counter)
                if (Model.isGhostEatingMode() && (GamePlayAloneController.getGhostEatingModeCounter() == 6 ||GamePlayAloneController.getGhostEatingModeCounter() == 4 || GamePlayAloneController.getGhostEatingModeCounter() == 2)) {
                    if (row == model.getGhost1Location().getX() && column == model.getGhost1Location().getY()) {
                        this.cellViews[row][column].setImage(this.ghost1Image);
                    }
                    if (row == model.getGhost2Location().getX() && column == model.getGhost2Location().getY()) {
                        this.cellViews[row][column].setImage(this.ghost2Image);
                    }
                    if(Model.levelName.equals("level4.txt") || Model.levelName.equals("level5.txt")){
                        if (row == model.getGhost3Location().getX() && column == model.getGhost3Location().getY()) {
                            this.cellViews[row][column].setImage(this.ghost3Image);
                        }
                    }
                    if(Model.levelName.equals("level5.txt")){
                        if (row == model.getGhost4Location().getX() && column == model.getGhost4Location().getY()) {
                            this.cellViews[row][column].setImage(this.ghost4Image);
                        }
                    }
                }
                //display blue ghosts in ghostEatingMode
                else if (Model.isGhostEatingMode()) {
                    if (row == model.getGhost1Location().getX() && column == model.getGhost1Location().getY()) {
                        this.cellViews[row][column].setImage(this.blueGhostImage);
                    }
                    if (row == model.getGhost2Location().getX() && column == model.getGhost2Location().getY()) {
                        this.cellViews[row][column].setImage(this.blueGhostImage);
                    }
                    if(Model.levelName.equals("level4.txt") || Model.levelName.equals("level5.txt")){
                        if (row == model.getGhost3Location().getX() && column == model.getGhost3Location().getY()) {
                            this.cellViews[row][column].setImage(this.blueGhostImage);
                        }
                    }
                    if(Model.levelName.equals("level5.txt")){
                        if (row == model.getGhost4Location().getX() && column == model.getGhost4Location().getY()) {
                            this.cellViews[row][column].setImage(this.blueGhostImage);
                        }
                    }

                }
                //dispaly regular ghost images otherwise
                else {
                    if (row == model.getGhost1Location().getX() && column == model.getGhost1Location().getY()) {
                        this.cellViews[row][column].setImage(this.ghost1Image);
                    }
                    if (row == model.getGhost2Location().getX() && column == model.getGhost2Location().getY()) {
                        this.cellViews[row][column].setImage(this.ghost2Image);
                    }
                    if(Model.levelName.equals("level4.txt") || Model.levelName.equals("level5.txt")){
                        if (row == model.getGhost3Location().getX() && column == model.getGhost3Location().getY()) {
                            this.cellViews[row][column].setImage(this.ghost3Image);
                        }
                    }
                    if(Model.levelName.equals("level5.txt")){
                        if (row == model.getGhost4Location().getX() && column == model.getGhost4Location().getY()) {
                            this.cellViews[row][column].setImage(this.ghost4Image);
                        }
                    }
                }
            }
        }
    }

    public void update(Model model1,Model model2) {
        assert model1.getRowCount() == this.rowCount && model1.getColumnCount() == this.columnCount;
        assert model2.getRowCount() == this.rowCount && model2.getColumnCount() == this.columnCount;

        //for each ImageView, set the image to correspond with the CellValue of that cell
        for (int row = 0; row < this.rowCount; row++){
            for (int column = 0; column < this.columnCount; column++){
                Model.CellValue value = model1.getCellValue(row, column);

                if (value == Model.CellValue.WALL) {
                    this.cellViews[row][column].setImage(this.wallImage);
                }
                else if (value == Model.CellValue.BIGDOT) {
                    this.cellViews[row][column].setImage(this.bigDotImage);
                }
                else if (value == Model.CellValue.SMALLDOT) {
                    this.cellViews[row][column].setImage(this.smallDotImage);
                }
                else {
                    this.cellViews[row][column].setImage(null);
                }
                //check which direction PacMan is going in and display the corresponding image
                if (row == model1.getPacmanLocation().getX() && column == model1.getPacmanLocation().getY() && (model1.getLastDirection() == Model.Direction.RIGHT || model1.getLastDirection() == Model.Direction.NONE)) {
                    this.cellViews[row][column].setImage(this.pacmanRightImage);
                }
                else if (row == model1.getPacmanLocation().getX() && column == model1.getPacmanLocation().getY() && model1.getLastDirection() == Model.Direction.LEFT) {
                    this.cellViews[row][column].setImage(this.pacmanLeftImage);
                }
                else if (row == model1.getPacmanLocation().getX() && column == model1.getPacmanLocation().getY() && model1.getLastDirection() == Model.Direction.UP) {
                    this.cellViews[row][column].setImage(this.pacmanUpImage);
                }
                else if (row == model1.getPacmanLocation().getX() && column == model1.getPacmanLocation().getY() && model1.getLastDirection() == Model.Direction.DOWN) {
                    this.cellViews[row][column].setImage(this.pacmanDownImage);
                }
                //check which direction PacMan2 is going in and display the corresponding image

                if (row == model2.getPacmanLocation().getX() && column == model2.getPacmanLocation().getY() && (model2.getLastDirection() == Model.Direction.RIGHT || model2.getLastDirection() == Model.Direction.NONE)) {
                    this.cellViews[row][column].setImage(this.pacman2RightImage);
                }
                else if (row == model2.getPacmanLocation().getX() && column == model2.getPacmanLocation().getY() && model2.getLastDirection() == Model.Direction.LEFT) {
                    this.cellViews[row][column].setImage(this.pacman2LeftImage);
                }
                else if (row == model2.getPacmanLocation().getX() && column == model2.getPacmanLocation().getY() && model2.getLastDirection() == Model.Direction.UP) {
                    this.cellViews[row][column].setImage(this.pacman2UpImage);
                }
                else if (row == model2.getPacmanLocation().getX() && column == model2.getPacmanLocation().getY() && model2.getLastDirection() == Model.Direction.DOWN) {
                    this.cellViews[row][column].setImage(this.pacman2DownImage);
                }

                //make ghosts "blink" towards the end of ghostEatingMode (display regular ghost images on alternating updates of the counter)
                if (Model.isGhostEatingMode() && (GamePlayMultipleController.getGhostEatingModeCounter() == 6 ||GamePlayMultipleController.getGhostEatingModeCounter() == 4 || GamePlayMultipleController.getGhostEatingModeCounter() == 2)) {
                    if (row == Model.getGhost1Location().getX() && column == Model.getGhost1Location().getY()) {
                        this.cellViews[row][column].setImage(this.ghost1Image);
                    }
                    if (row == Model.getGhost2Location().getX() && column == Model.getGhost2Location().getY()) {
                        this.cellViews[row][column].setImage(this.ghost2Image);
                    }
                    if(Model.levelName.equals("level4.txt") || Model.levelName.equals("level5.txt")){
                        if (row == Model.getGhost3Location().getX() && column == Model.getGhost3Location().getY()) {
                            this.cellViews[row][column].setImage(this.ghost3Image);
                        }
                    }
                    if(Model.levelName.equals("level5.txt")){
                        if (row == Model.getGhost4Location().getX() && column == Model.getGhost4Location().getY()) {
                            this.cellViews[row][column].setImage(this.ghost4Image);
                        }
                    }
                }
                //display blue ghosts in ghostEatingMode
                else if (Model.isGhostEatingMode()) {
                    if (row == Model.getGhost1Location().getX() && column == Model.getGhost1Location().getY()) {
                        this.cellViews[row][column].setImage(this.blueGhostImage);
                    }
                    if (row == Model.getGhost2Location().getX() && column == Model.getGhost2Location().getY()) {
                        this.cellViews[row][column].setImage(this.blueGhostImage);
                    }
                    if (row == Model.getGhost3Location().getX() && column == Model.getGhost3Location().getY()) {
                        this.cellViews[row][column].setImage(this.blueGhostImage);
                    }
                    if (row == Model.getGhost4Location().getX() && column == Model.getGhost4Location().getY()) {
                        this.cellViews[row][column].setImage(this.blueGhostImage);
                    }
                }
                //dispaly regular ghost images otherwise
                else {
                    if (row == Model.getGhost1Location().getX() && column == Model.getGhost1Location().getY()) {
                        this.cellViews[row][column].setImage(this.ghost1Image);
                    }
                    if (row == Model.getGhost2Location().getX() && column == Model.getGhost2Location().getY()) {
                        this.cellViews[row][column].setImage(this.ghost2Image);
                    }
                    if (row == Model.getGhost3Location().getX() && column == Model.getGhost3Location().getY()) {
                        this.cellViews[row][column].setImage(this.ghost3Image);
                    }
                    if (row == Model.getGhost4Location().getX() && column == Model.getGhost4Location().getY()) {
                        this.cellViews[row][column].setImage(this.ghost4Image);
                    }
                }
            }
        }
    }


    public int getRowCount() {
        return this.rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
        this.initializeGrid();
    }

    public int getColumnCount() {
        return this.columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
        this.initializeGrid();
    }

}
