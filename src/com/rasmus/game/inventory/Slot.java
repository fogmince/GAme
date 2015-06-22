package com.rasmus.game.inventory;

import com.rasmus.game.entity.item.Item;
import com.rasmus.game.graphics.Sprite;
import com.rasmus.game.graphics.ui.UILabel;
import com.rasmus.game.graphics.ui.UIPanel;
import com.rasmus.game.graphics.ui.UISprite;
import com.rasmus.game.graphics.ui.UISquare;
import com.rasmus.game.input.Mouse;
import com.rasmus.game.util.Vector2i;

import java.awt.*;


public class Slot {

    public double x, y;
    public double width, height;

    public boolean isMousedOver = false;
    public boolean isClicked = false;

    private UIPanel panel;
    private UISquare square;
    private UISprite itemSprite;
    private UILabel stackSize;
    private Item item;

    public boolean hasSquare = false;
    public boolean hasItem = false;
    private boolean hasRendered = false;
    private boolean hasRenderStack = false;

    private int key = 0;
    private int numberOfItems = 0;
    private int counter = 0;

    public Slot(double x, double y, double width, double height, UIPanel panel) {
        this.x = x + 870;
        this.y = y;
        this.width = width;
        this.height = height;
        this.panel = panel;

        square = new UISquare(new Vector2i((int) x + 3, (int) y + 3), new Vector2i(42, 42));
        square.setColor(new Color(0x7FB8B2B2, true));

        stackSize = new UILabel(new Vector2i((int) x + 30, (int) y + 45), numberOfItems);
        stackSize.setColor(0xFFFFFF);
        stackSize.setFont(new Font("Helvetica", Font.PLAIN, 12));
        counter = numberOfItems;
    }

    public void update() {
        square.update();
        if(key != -2) key = Mouse.getButton();

        if(Mouse.getX() > x && Mouse.getX() < x + width - 16 && Mouse.getY() > y && Mouse.getY() < y + height - 16){
            isMousedOver = true;
            isClicked = false;
        } else {
            isMousedOver = false;
            isClicked = false;
        }

        if (Mouse.getX() > x && Mouse.getX() < x + width - 16 && Mouse.getY() > y && Mouse.getY() < y + height - 16 && !isClicked) {
            if(key == 1) {
                isClicked = true;
                key = -2;
            } else {
                isClicked = false;
            }
        }

        if(isMousedOver || isMousedOver) {
            if(!hasSquare) {
                panel.addComponent(square);
                hasSquare = true;
            }
        } else {
            panel.removeComponent(square);
            hasSquare = false;
        }

        if(hasItem && !hasRendered) {
            itemSprite = new UISprite(new Vector2i((int) x - 870, (int) y), item.getSprite().path);
            panel.addComponent(itemSprite);
            hasRendered = true;
        }


        if(!hasItem && hasRendered) {
            panel.removeComponent(itemSprite);
            hasRendered = false;
        }

        if(Mouse.getButton() == -1) key = 0;

        if(stackSize != null && !hasRenderStack) {
            if(numberOfItems <= 10) {
                stackSize.setPosition(new Vector2i((int) x + 37 - 870, (int) y + 45));
            } else {
                stackSize.setPosition(new Vector2i((int) x + 30 - 870, (int) y + 45));
            }

            panel.removeComponent(stackSize);
            stackSize.setNumber(numberOfItems);
            panel.addComponent(stackSize);
            hasRenderStack = true;
        }

        if(counter != numberOfItems && hasRenderStack) {
            counter = numberOfItems;
            hasRenderStack = false;
        }
    }

    public void addItem(Item item, int amount) {
        this.item = item;
        hasItem = true;
        if(numberOfItems + amount <= item.stackSize) {
            numberOfItems += amount;
        } else {
            int itemsLeft = amount - item.stackSize;
            numberOfItems = amount - itemsLeft;
        }
    }

    public int getAmountOfItems() {
        return numberOfItems;
    }

    public Item removeItem() {
        hasItem = false;
        numberOfItems = 0;
        return item;
    }

    public Item getItemInSlot() {
        if(item != null) {
            return item;
        }

        return new Item(0, 0, Sprite.voidSprite, true);
    }

    public Item switchItem(Item item) {
        Item itemTemp = this.item;
        panel.removeComponent(itemSprite);
        this.item = item;
        itemSprite = new UISprite(new Vector2i((int) x - 870, (int) y), this.item.getSprite().path);
        panel.addComponent(itemSprite);
        hasItem = true;

        return itemTemp;
    }
}
