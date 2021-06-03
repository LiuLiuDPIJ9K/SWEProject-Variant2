package boxgame.javafx.model;

public class BoxGameModel {

    public void setStones(Stone stone, Square square) {
        stone.setLayoutX(square.getSquareX());
        stone.setLayoutY(square.getSquareY());
        stone.setPrefWidth(square.getWidth());
        stone.setPrefHeight(square.getHeight());
    }

    public boolean isSelectedLegal(Stone newStone, Stone[] stone) {
        int selectedNumber = 0;

        for (Stone value : stone) {
            if (value.isStoneSelected()) {
                if (value == newStone) {
                    //newStone.stoneSwitch();
                }
                selectedNumber++;
            }
        }

        if(selectedNumber == 0) {
            newStone.stoneSwitch();
        } else if(selectedNumber == 1) {
            /*Stone firstStone = new Stone();

            for(Stone value : stone) {
                if(value.isStoneSelected()) {
                    firstStone = value;
                    break;
                }
            }

            if(Math.abs(newStone.getLayoutX() - firstStone.getLayoutX()) >= newStone.getWidth()) {
                newStone.stoneSwitch();
                return true;
            } else {
                Logger.info("Can't select this stone, because they are not adjacent!");
                return false;
            }*/
            newStone.stoneSwitch();
            return true;
        }

        return false;
    }

    public Position getStonePosition(Stone stone) {
        Position stonePosition = new Position((int) stone.getLayoutX() + 40, (int) stone.getLayoutY() + 40);
        return stonePosition;
    }

    public Square getMoveSquare(Stone stone, Square[] square) {

        for(Square value : square) {
            if(value.isOnBoard(getStonePosition(stone))) {
                return value;
            }
        }
        return null;
    }

    public boolean isComplete(Stone[] stone) {
        for(int i = 0; i < 3; i++){
            if(stone[i].getStoneColor().equals("BLACK")) {
                return false;
            }
        }
        return true;
    }

}
