package com.rasmus.game.inventory;

import com.rasmus.game.entity.Entity;
import com.rasmus.game.entity.item.Item;
import com.rasmus.game.graphics.ui.UILabel;
import com.rasmus.game.graphics.ui.UIPanel;
import com.rasmus.game.graphics.ui.UISprite;
import com.rasmus.game.input.Mouse;
import com.rasmus.game.util.Vector2i;

import java.awt.*;

public class PlayerInventory extends Inventory {

    private Slot[][] slotsSmall;
    private Slot[] slotsBig;

    private UISprite inventorySmall;
    private UISprite inventoryBig;
    private UISprite itemSprite;
    private UILabel amountOfItems;

    private boolean holdingItem = false;
    private boolean hasRenderedAmount = false;

    private Item item;

    private int amount = 0;
    private int counter;

    public PlayerInventory(Entity entity, UIPanel panel) {
        super(entity, panel);
        slotsSmall = new Slot[5][2];
        slotsBig = new Slot[4];

        //inventory
        for(int i = 0; i < 4; i++) {
            inventoryBig = new UISprite(new Vector2i(57 * i + 110 / 4 * 2, 400), "/ui/inventoryBig.png");
            panel.addComponent(inventoryBig);
        }

        for(int y1 = 0; y1 < 2; y1++) {
            for(int x1 = 0; x1 < 5; x1++) {
                inventorySmall = new UISprite(new Vector2i(49 * x1 + 110 / 5 * 2, 50 * y1 + 470), "/ui/inventoryBasic.png");
                panel.addComponent(inventorySmall);
            }
        }

        for(int i = 0; i < slotsBig.length; i++) {
            slotsBig[i] = new Slot(55 + i * 57, 400, 64, 64, panel);
        }

        for(int y = 0; y < 2; y++) {
            for(int x = 0; x < 5; x++) {
                slotsSmall[x][y] = new Slot(45 + x * 49, 470 + y * 50, 64, 64, panel);
            }
        }

        amountOfItems = new UILabel(new Vector2i(Mouse.getX(), Mouse.getY()), amount);
        amountOfItems.setColor(0xFFFFFF);
        amountOfItems.setFont(new Font("Helvetica", Font.PLAIN, 12));

        counter = amount;
    }

    public void update() {
        for(int y = 0; y < 2; y++) {
            for(int x = 0; x < 5; x++) {
                slotsSmall[x][y].update();

                if (!holdingItem && slotsSmall[x][y].hasItem && slotsSmall[x][y].isClicked) {
                    amount = slotsSmall[x][y].getAmountOfItems();
                    panel.removeComponent(itemSprite);
                    item = slotsSmall[x][y].removeItem();
                    itemSprite = new UISprite(new Vector2i(Mouse.getX(), Mouse.getY()), item.getSprite().path);
                    holdingItem = true;
                    continue;
                }

                if (holdingItem && !slotsSmall[x][y].hasItem && slotsSmall[x][y].isClicked) {
                    slotsSmall[x][y].addItem(item, amount);
                    holdingItem = false;
                    panel.removeComponent(itemSprite);
                    continue;
                }

                if (holdingItem && slotsSmall[x][y].hasItem && slotsSmall[x][y].isClicked) {
                    panel.removeComponent(itemSprite);
                    item = slotsSmall[x][y].switchItem(item);
                    itemSprite = new UISprite(new Vector2i(Mouse.getX(), Mouse.getY()), item.getSprite().path);
                    continue;
                }
            }
        }

        for(int i = 0; i < slotsBig.length; i++) {
            slotsBig[i].update();

            if(!holdingItem && slotsBig[i].hasItem && slotsBig[i].isClicked) {
                amount = slotsBig[i].getAmountOfItems();
                panel.removeComponent(itemSprite);
                item = slotsBig[i].removeItem();
                itemSprite = new UISprite(new Vector2i(Mouse.getX(), Mouse.getY()), item.getSprite().path);
                holdingItem = true;
                continue;
            }

            if(holdingItem && !slotsBig[i].hasItem && slotsBig[i].isClicked) {
                slotsBig[i].addItem(item, amount);
                holdingItem = false;
                panel.removeComponent(itemSprite);
                continue;
            }

            if(holdingItem && slotsBig[i].hasItem && slotsBig[i].isClicked) {
                panel.removeComponent(itemSprite);
                item = slotsBig[i].switchItem(item);
                itemSprite = new UISprite(new Vector2i(Mouse.getX(), Mouse.getY()), item.getSprite().path);
                continue;
            }
        }

        if(counter != amount) {
            counter = amount;
            amountOfItems.setNumber(amount);
            hasRenderedAmount = false;
        }

        if(amountOfItems != null && !hasRenderedAmount) {
            panel.addComponent(amountOfItems);
            hasRenderedAmount = true;
        }

        panel.removeComponent(itemSprite);
        panel.removeComponent(amountOfItems);

        if(holdingItem) {

            if(amount <= 10) {
                amountOfItems.setPosition(new Vector2i(Mouse.getX() - 870 + 37, Mouse.getY() + 45));
            } else {
                amountOfItems.setPosition(new Vector2i(Mouse.getX() - 870 + 30, Mouse.getY() + 45));
            }

            itemSprite.setPosition(new Vector2i(Mouse.getX() - 870, Mouse.getY()));
            panel.addComponent(itemSprite);
            panel.addComponent(amountOfItems);
        }
    }

    public void addSmallSlot(int x, int y, Item item, int amount) {
        slotsSmall[x][y].addItem(item, amount);
    }

    public void addBigSlot(int x, Item item, int amount) {
        slotsBig[x].addItem(item, amount);
    }

    public boolean isItemInSlot(int x, int y) {
        if(slotsSmall[x][y].hasItem) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isItemInSlot(int x) {
        if(slotsBig[x].hasItem) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isHoldingItem() {
        return holdingItem;
    }

 }
