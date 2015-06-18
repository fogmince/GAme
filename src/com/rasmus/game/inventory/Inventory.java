package com.rasmus.game.inventory;

import com.rasmus.game.entity.Entity;
import com.rasmus.game.graphics.ui.UIPanel;

public abstract class Inventory {

    protected Entity entity;
    protected UIPanel panel;

    public Inventory(Entity entity, UIPanel panel) {
        this.entity = entity;
        this.panel = panel;
    }

    public abstract void update();
}
