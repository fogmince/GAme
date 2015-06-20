package com.rasmus.game.inventory;

import com.rasmus.game.entity.Entity;
import com.rasmus.game.entity.item.Item;
import com.rasmus.game.entity.item.TestItem;
import com.rasmus.game.graphics.Sprite;
import com.rasmus.game.graphics.ui.UIPanel;
import com.rasmus.game.graphics.ui.UISprite;
import com.rasmus.game.input.Mouse;
import com.rasmus.game.util.Vector2i;

public class PlayerInventory extends Inventory {

    private Slot[][] slotsSmall;
    private Slot[] slotsBig;

    private UISprite inventorySmall;
    private UISprite inventoryBig;
    private UISprite itemSprite;

    private boolean holdingItem = false;
    private Item item;

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

        slotsSmall[1][1].addItem(new TestItem(0, 0, Sprite.sword_icon, true));
        slotsBig[3].addItem(new TestItem(0, 0, Sprite.projectile_laser, true));

    }

    public void update() {
        for(int y = 0; y < 2; y++) {
            for(int x = 0; x < 5; x++) {
                slotsSmall[x][y].update();

                if(!holdingItem && slotsSmall[x][y].hasItem && slotsSmall[x][y].isClicked) {
                    item = slotsSmall[x][y].removeItem();
                    itemSprite = new UISprite(new Vector2i(Mouse.getX(), Mouse.getY()), item.getSprite().path);
                    holdingItem = true;
                    continue;
                }

                if(holdingItem && !slotsSmall[x][y].hasItem && slotsSmall[x][y].isClicked) {
                    slotsSmall[x][y].addItem(item);
                    holdingItem = false;
                    continue;
                }

                if(holdingItem && slotsSmall[x][y].hasItem && slotsSmall[x][y].isClicked) {
                    panel.removeComponent(itemSprite);
                    item = slotsSmall[x][y].switchItem(item);
                    itemSprite = new UISprite(new Vector2i(Mouse.getX(), Mouse.getY()), item.getSprite().path);
                    panel.addComponent(itemSprite);
                    continue;
                }
            }
        }

        for(int i = 0; i < slotsBig.length; i++) {
            slotsBig[i].update();

            if(Mouse.getButton() == 1) {
                if(!holdingItem && slotsBig[i].hasItem && slotsBig[i].isClicked) {
                    item = slotsBig[i].removeItem();
                    itemSprite = new UISprite(new Vector2i(Mouse.getX(), Mouse.getY()), item.getSprite().path);
                    holdingItem = true;
                    continue;
                }

                if(holdingItem && !slotsBig[i].hasItem && slotsBig[i].isClicked) {
                    slotsBig[i].addItem(item);
                    holdingItem = false;
                    continue;
                }

                if(holdingItem && slotsBig[i].hasItem && slotsBig[i].isClicked) {
                    panel.removeComponent(itemSprite);
                    item = slotsBig[i].switchItem(item);
                    itemSprite = new UISprite(new Vector2i(Mouse.getX(), Mouse.getY()), item.getSprite().path);
                    panel.addComponent(itemSprite);
                    continue;
                }

            }
        }

        panel.removeComponent(itemSprite);

        if(holdingItem) {
            itemSprite.setPosition(new Vector2i(Mouse.getX() - 870, Mouse.getY()));
            panel.addComponent(itemSprite);
        }
    }
 }
