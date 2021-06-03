package boxgame.javafx.controller;

import boxgame.javafx.model.BoxGameModel;
import boxgame.javafx.model.Square;
import boxgame.javafx.model.Stone;
import com.google.common.cache.RemovalListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import org.tinylog.Logger;

import java.util.Arrays;

public class BoxGameController {

    @FXML
    private AnchorPane board;

    @FXML
    private Label stepsLabel;

    @FXML
    public void initialize() {
        createBoard();
        steps.set(0);
        stepsLabel.textProperty().bind(steps.asString());
    }

    private final IntegerProperty steps = new SimpleIntegerProperty();

    private BoxGameModel model = new BoxGameModel();

    private static final int SQUARE_NUMBER = 16;
    private static final int STONE_NUMBER = 6;

    private static Square[] squares = new Square[SQUARE_NUMBER];
    private static Stone[] stones = new Stone[STONE_NUMBER];

    private static Stone moveStone1;
    private static Stone moveStone2;

    private static boolean canSelect = false;
    private static boolean canMove = false;
    private static boolean canDrag = false;
    private static boolean complete = false;

    public void createBoard() {
        int width = 0;

        for(int i = 0; i < SQUARE_NUMBER; i++) {
            Square square = new Square(width, 150, 75, 75);
            squares[i] = square;
            width = width + 75;
        }

        for(int i = 0; i < STONE_NUMBER; i++) {
            Stone stone = new Stone();
            stones[i] = stone;

            if(i % 2 == 0) {
                stone.setStyle("-fx-background-color: red");
                stone.setStoneColor("RED");
            } else {
                stone.setStyle("-fx-background-color: black");
                stone.setStoneColor("BLACK");
            }
            model.setStones(stone, squares[i]);
            board.getChildren().add(stone);
            stone.setShape(new Circle(30));

            stone.setOnMouseClicked(e -> handleMouseClicked(e, stone));
            stone.setOnMouseDragged(this::handleMouseDragged);
            stone.setOnMouseReleased(this::handleMouseReleased);
        }
    }

    public void handleMouseClicked(MouseEvent e, Stone stone) {
        moveStone1 = null;
        moveStone2 = null;
        canMove = model.isSelectedLegal(stone, stones);

            for(Stone value : stones) {
                if(value.isStoneSelected()) {
                    if(moveStone1 == null) {
                        moveStone1 = value;
                    } else if(moveStone2 == null) {
                        moveStone2 = value;
                    }
                }
            }

        if(canSelect) {
            canSelect = false;
        }
    }

    public void handleMouseDragged(MouseEvent e) {
        if(canMove && moveStone1.isStoneSelected() && moveStone2.isStoneSelected()) {
            Stone selectedStone1 = null;
            Stone selectedStone2 = null;

            for (Stone value : stones) {
                if (value.isStoneSelected()) {
                    if (selectedStone1 == null) {
                        selectedStone1 = value;
                    } else if (selectedStone2 == null) {
                        selectedStone2 = value;
                    }
                }
            }

            if (e.getSource() == selectedStone1) {
                selectedStone1.setLayoutX(e.getSceneX());
                selectedStone1.setLayoutY(e.getSceneY());
                selectedStone2.setLayoutX(e.getSceneX() + selectedStone2.getWidth());
                selectedStone2.setLayoutY(e.getSceneY());
            } else if (e.getSource() == selectedStone2) {
                selectedStone2.setLayoutX(e.getSceneX());
                selectedStone2.setLayoutY(e.getSceneY());
                selectedStone1.setLayoutX(e.getSceneX() - selectedStone1.getWidth());
                selectedStone1.setLayoutY(e.getSceneY());
            }
            canDrag = true;
        }
    }

    public void handleMouseReleased(MouseEvent e) {
        if(canMove && canDrag) {

            Stone newStone1 = null;
            Stone newStone2 = null;

            for(Stone value : stones) {
                if(value.isStoneSelected()) {
                    if(newStone1 == null) {
                        newStone1 = value;
                    } else if(newStone2 == null) {
                        newStone2 = value;
                    }
                }
            }

            Square newSquare1 = model.getMoveSquare(newStone1, squares);
            Square newSquare2 = model.getMoveSquare(newStone2, squares);

            model.setStones(newStone1, newSquare1);
            model.setStones(newStone2, newSquare2);

            System.out.println(newStone1.isStoneSelected());
            System.out.println(newStone2.isStoneSelected());
            //newStone1.stoneSwitch();
            //newStone2.stoneSwitch();
            for(Stone value : stones) {
                if(value.isStoneSelected()) {
                    value.stoneSwitch();
                }
            }
            System.out.println(newStone1.isStoneSelected());
            System.out.println(newStone2.isStoneSelected());

            canSelect = true;
            canMove = false;
            canDrag = false;


            complete = model.isComplete(stones);

            if(complete) {
                Logger.info("Complete!");
            }

            steps.set(steps.get() + 1);
        }

    }

    public void handleResetButton(ActionEvent e) {
        Logger.info("Resetting game");
        board.getChildren().removeAll(stones);
        initialize();
    }

}
