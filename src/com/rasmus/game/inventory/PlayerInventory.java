package com.rasmus.game.inventory;

import com.rasmus.game.entity.Entity;
import com.rasmus.game.entity.item.*;
import com.rasmus.game.graphics.Sprite;
import com.rasmus.game.graphics.ui.UILabel;
import com.rasmus.game.graphics.ui.UIPanel;
import com.rasmus.game.graphics.ui.UISprite;
import com.rasmus.game.input.Mouse;
import com.rasmus.game.util.Vector2i;

import java.awt.*;

public class PlayerInventory extends Inventory {

    private Slot[][] slots;

    private UISprite inventorySmall;
    private UISprite itemSprite;
    private UILabel amountOfItems;

    private boolean holdingItem = false;
    private boolean hasRenderedAmount = false;

    private Item item;

    private int amount = 0;
    private int counter;

    public PlayerInventory(Entity entity, UIPanel panel) {
        super(entity, panel);
        slots = new Slot[5][3];

        for(int y1 = 0; y1 < 3; y1++) {
            for(int x1 = 0; x1 < 5; x1++) {
                inventorySmall = new UISprite(new Vector2i(49 * x1 + 110 / 5 * 2, 50 * y1 + 470), "/ui/inventoryBasic.png");
                panel.addComponent(inventorySmall);
            }
        }

        for(int y = 0; y < 3; y++) {
            for(int x = 0; x < 5; x++) {
                slots[x][y] = new Slot(45 + x * 49, 470 + y * 50, 64, 64, panel);
            }
        }

        slots[0][0].setType(0);
        slots[1][0].setType(1);
        slots[2][0].setType(2);
        slots[3][0].setType(3);
        slots[4][0].setType(4);

        slots[0][0].addItem(new TestItem(Sprite.sword_icon), 1);
        slots[0][2].addItem(new Test2Item(Sprite.potion_icon), 60);

        amountOfItems = new UILabel(new Vector2i(Mouse.getX(), Mouse.getY()), amount);
        amountOfItems.setColor(0xFFFFFF);
        amountOfItems.setFont(new Font("Helvetica", Font.PLAIN, 12));

        counter = amount;
    }

    public void update() {
        for(int y = 0; y < 3; y++) {
            for(int x = 0; x < 5; x++) {
                slots[x][y].update();

                if(!holdingItem && slots[x][y].hasItem && slots[x][y].isClicked) {
                    amount = slots[x][y].getAmountOfItems();
                    panel.removeComponent(itemSprite);
                    item = slots[x][y].removeItem();
                    itemSprite = new UISprite(new Vector2i(Mouse.getX(), Mouse.getY()), item.getSprite().path);
                    holdingItem = true;
                    continue;
                }

                if(holdingItem && !slots[x][y].hasItem && slots[x][y].isClicked) {
                    addItem(x, y, item, amount);
                    continue;
                }

                if(holdingItem && slots[x][y].hasItem && slots[x][y].isClicked && item.getClass().equals(slots[x][y].getItemInSlot().getClass())) {
                    int items = slots[x][y].getAmountOfItems() + amount;
                    if(items <= item.stackSize) {
                        slots[x][y].addAmountOfItems(amount);
                        holdingItem = false;
                        panel.removeComponent(itemSprite);
                    } else {
                        slots[x][y].addItem(item, item.stackSize);
                        amount = items - item.stackSize;
                    }
                }

                if(holdingItem && slots[x][y].hasItem && slots[x][y].isClicked && item.getClass().equals(slots[x][y].getItemInSlot().getClass()) == false) {
                    switchItem(x, y, item, amount);
                    continue;
                }
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
        slots[x][y].addItem(item, amount);
    }


    public boolean isItemInSlot(int x, int y) {
        if(slots[x][y].hasItem) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isHoldingItem() {
        return holdingItem;
    }

    public boolean canAddInSlot(int x, int y, Item item) {
        if(x == 0 && item instanceof ItemSword && !isItemInSlot(0, 0)) {
            return true;
        } else if(x == 1) {
            return true;
        } else if(x == 2) {
            return true;
        } else if(x == 3) {
            return true;
        }

        return false;
    }

    public void addItem(Item item, int amount) {
        for(int y = 0; y < 3; y++) {
            for(int x = 0; x < 5; x++) {
                if(slots[x][y].hasItem && item.getClass().equals(slots[x][y].getItemInSlot().getClass())) {
                    int items = slots[x][y].getAmountOfItems() + amount;
                    if (items <= item.stackSize) {
                        slots[x][y].addItem(item, amount);
                        return;
                    } else {
                        slots[x][y].addItem(item, item.stackSize);
                        for (int y1 = 0; y1 < 3; y1++) {
                            for (int x1 = 0; x1 < 5; x1++) {

                                if(slots[x1][y1].getType() == 0 && item instanceof ItemSword) {
                                    if(slots[x1][y1].hasItem) {
                                        continue;
                                    } else {
                                        amount = items - item.stackSize;
                                        slots[x1][y1].addItem(item, amount);
                                        return;
                                    }
                                }

                                if(slots[x][y].getType() == 1 && item instanceof ItemHelmet) {
                                    if(slots[x1][y1].hasItem) {
                                        continue;
                                    } else {
                                        amount = items - item.stackSize;
                                        slots[x1][y1].addItem(item, amount);
                                        return;
                                    }
                                }

                                if(slots[x][y].getType() == 2 && item instanceof ItemChestPlate) {
                                    if(slots[x1][y1].hasItem) {
                                        continue;
                                    } else {
                                        amount = items - item.stackSize;
                                        slots[x1][y1].addItem(item, amount);
                                        return;
                                    }
                                }

                                if(slots[x][y].getType() == 3 && item instanceof ItemBoots) {
                                    if(slots[x1][y1].hasItem) {
                                        continue;
                                    } else {
                                        amount = items - item.stackSize;
                                        slots[x1][y1].addItem(item, amount);
                                        return;
                                    }
                                }

                                if(slots[x][y].getType() == 4 && item instanceof ItemRing) {
                                    if(slots[x1][y1].hasItem) {
                                        continue;
                                    } else {
                                        amount = items - item.stackSize;
                                        slots[x1][y1].addItem(item, amount);
                                        return;
                                    }
                                }

                                if(slots[x1][y1].getType() > 4) {
                                    if (slots[x1][y1].hasItem) continue;
                                    else {
                                        amount = items - item.stackSize;
                                        slots[x1][y1].addItem(item, amount);
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        for(int y = 0; y < 3; y++) {
            for(int x = 0; x < 5; x++) {
                if(slots[x][y].getType() == 0 && item instanceof ItemSword) {
                    if(!slots[x][y].hasItem) {
                        slots[x][y].addItem(item, amount);
                        return;
                    } else {
                        continue;
                    }
                }

                if(slots[x][y].getType() == 1 && item instanceof ItemHelmet) {
                    if(!slots[x][y].hasItem) {
                        slots[x][y].addItem(item, amount);
                        return;
                    } else {
                        continue;
                    }
                }

                if(slots[x][y].getType() == 2 && item instanceof ItemChestPlate) {
                    if(!slots[x][y].hasItem) {
                        slots[x][y].addItem(item, amount);
                        return;
                    } else {
                        continue;
                    }
                }

                if(slots[x][y].getType() == 3 && item instanceof ItemBoots) {
                    if(!slots[x][y].hasItem) {
                        slots[x][y].addItem(item, amount);
                        return;
                    } else {
                        continue;
                    }
                }

                if(slots[x][y].getType() == 4 && item instanceof ItemRing) {
                    if(!slots[x][y].hasItem) {
                        slots[x][y].addItem(item, amount);
                        return;
                    } else {
                        continue;
                    }
                }

                if(slots[x][y].getType() > 4) {
                    if(!slots[x][y].hasItem) {
                        slots[x][y].addItem(item, amount);
                        return;
                    } else {
                        continue;
                    }
                }
            }
        }
    }

    public void addItem(int x, int y, Item item, int amount) {
        if(slots[x][y].getType() == 0 && item instanceof ItemSword) {
            slots[x][y].addItem(item, amount);
            holdingItem = false;
            panel.removeComponent(itemSprite);
        }

        if(slots[x][y].getType() == 1 && item instanceof ItemHelmet) {
            slots[x][y].addItem(item, amount);
            holdingItem = false;
            panel.removeComponent(itemSprite);
        }

        if(slots[x][y].getType() == 2 && item instanceof ItemChestPlate) {
            slots[x][y].addItem(item, amount);
            holdingItem = false;
            panel.removeComponent(itemSprite);
        }

        if(slots[x][y].getType() == 3 && item instanceof ItemBoots) {
            slots[x][y].addItem(item, amount);
            holdingItem = false;
            panel.removeComponent(itemSprite);
        }

        if(slots[x][y].getType() == 4 && item instanceof ItemRing) {
            slots[x][y].addItem(item, amount);
            holdingItem = false;
            panel.removeComponent(itemSprite);
        }


        if(slots[x][y].getType() > 4) {
            slots[x][y].addItem(item, amount);
            holdingItem = false;
            panel.removeComponent(itemSprite);
        }
    }

    private void switchItem(int x, int y, Item item, int amount) {
        //TODO
        if(slots[x][y].getType() > 4) {
            panel.removeComponent(itemSprite);
            item = slots[x][y].switchItem(item);
            itemSprite = new UISprite(new Vector2i(Mouse.getX(), Mouse.getY()), item.getSprite().path);
        }
    }
}