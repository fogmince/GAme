package com.rasmus.game.graphics.ui;



import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UIManager {

    private List<UIPanel> panels = new ArrayList<UIPanel>();

    public UIManager() {

    }

    public void addPanel(UIPanel panel) {
        panels.add(panel);
    }

    public void update() {
        for(UIPanel panel : panels) {
            panel.update();
        }
    }

    public void render(Graphics g) {
        for(UIPanel panel : panels) {
            panel.render(g);
        }
    }

    public void clear() {
       for(int i = 0; i < panels.size(); i++) {
           panels.remove(i);
       }
    }

    public void removePanel(UIPanel panel) {
        panels.remove(panel);
    }
}
