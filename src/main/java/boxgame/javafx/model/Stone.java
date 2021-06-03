package boxgame.javafx.model;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class Stone extends Button {

    private boolean selected = false;

    private String stoneColor;

    public boolean isStoneSelected() {
        return selected;
    }

    public void deselected() {
        this.selected = true;
    }

    public void setStoneColor(String stoneColor) {
        this.stoneColor = stoneColor;
    }

    public String getStoneColor() {
        return stoneColor;
    }

    public void stoneSwitch() {
        if(!selected) {
            setText("✓");
            setTextFill(Color.WHITE);
        } else {
            setText("");
        }

        this.selected = !selected;

    }

}
