package com.rasmus.game.inventory;

import com.rasmus.game.entity.item.*;
import com.rasmus.game.entity.mob.Player;
import com.rasmus.game.graphics.Sprite;
import com.rasmus.game.graphics.ui.UILabel;
import com.rasmus.game.graphics.ui.UIPanel;
import com.rasmus.game.graphics.ui.UISprite;
import com.rasmus.game.input.Keyboard;
import com.rasmus.game.input.Mouse;
import com.rasmus.game.util.Vector2i;

import java.awt.*;
import java.util.Random;

public class PlayerInventory extends Inventory {

    private Slot[][] slots;

    private UISprite inventorySmall;
    private UISprite itemSprite;
    private UILabel amountOfItems;

    public boolean holdingItem = false;
    private boolean hasRenderedAmount = false;

    private Item item;

    private int amount = 0;
    private int counter;
    private int key = 0;
    private Keyboard input;

    private Random random = new Random();

    public PlayerInventory(Player player, UIPanel panel, Keyboard input) {
        super(player, panel);
        xSlots = 5;
        ySlots = 4;
        slots = new Slot[xSlots][ySlots];
        this.input = input;

        for(int y1 = 0; y1 < ySlots; y1++) {
            for(int x1 = 0; x1 < xSlots; x1++) {
                inventorySmall = new UISprite(new Vector2i(49 * x1 + 110 / 5 * 2, 50 * y1 + 470), "/ui/inventoryBasic.png");
                panel.addComponent(inventorySmall);
            }
        }

        for(int y = 0; y < ySlots; y++) {
            for(int x = 0; x < xSlots; x++) {
                slots[x][y] = new Slot(45 + x * 49, 470 + y * 50, 64, 64, panel, input, entity);
            }
        }

        slots[0][0].setType(0);
        slots[1][0].setType(1);
        slots[2][0].setType(2);
        slots[3][0].setType(3);
        slots[4][0].setType(4);
        slots[0][1].setType(5);
        slots[1][1].setType(6);
        slots[2][1].setType(7);
        slots[3][1].setType(8);
        slots[4][1].setType(9);

        slots[0][0].addItem(new TestItem(Sprite.sword), 1);
        slots[0][0].item.putInInventory((Player) entity);
        slots[0][2].addItem(new Test2Item(Sprite.potion), 60);

        amountOfItems = new UILabel(new Vector2i(Mouse.getX(), Mouse.getY()), amount, false);
        amountOfItems.setColor(0xFFFFFF);
        amountOfItems.setFont(new Font("Helvetica", Font.PLAIN, 12));

        counter = amount;
    }

    public void update() {
        for(int y = 0; y < ySlots; y++) {
            for(int x = 0; x < xSlots; x++) {
                slots[x][y].update();
                if(key != -2) key = Mouse.getButton();

                //Clicking without an item on a slot with an item
                if(!holdingItem && slots[x][y].hasItem && slots[x][y].isClicked) {
                    if(slots[x][y].getType() == 0) {
                        slots[x][y].item.removedFromInventory((Player) entity);
                    }

                    if(slots[x][y].getType() == 1) {
                        slots[x][y].item.removedFromInventory((Player) entity);
                    }

                    if(slots[x][y].getType() == 2) {
                        slots[x][y].item.removedFromInventory((Player) entity);
                    }

                    if(slots[x][y].getType() == 3) {
                        slots[x][y].item.removedFromInventory((Player) entity);
                    }

                    if(slots[x][y].getType() == 4) {
                        slots[x][y].item.removedFromInventory((Player) entity);
                    }


                    amount = slots[x][y].getAmountOfItems();
                    panel.removeComponent(itemSprite);
                    item = slots[x][y].removeItem();
                    itemSprite = new UISprite(new Vector2i(Mouse.getX(), Mouse.getY()), item.getSprite().path);
                    holdingItem = true;
                    continue;
                }

                //CLicking with an item on an empty slot
                if(holdingItem && !slots[x][y].hasItem && slots[x][y].isClicked) {
                    addItem(x, y, item, amount);
                    continue;
                }

                //Clicking with a item on a slot with the same item
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
                    continue;
                }

                //Clicking with an item on a slot with an other item
                if(holdingItem && slots[x][y].hasItem && slots[x][y].isClicked && !item.getClass().equals(slots[x][y].getItemInSlot().getClass())) {
                    if(canSwitchItem(x, y, item)) {
                        if(slots[x][y].getType() == 0) {
                            slots[x][y].item.removedFromInventory((Player) entity);
                        }

                        if(slots[x][y].getType() == 1) {
                            slots[x][y].item.removedFromInventory((Player) entity);
                        }

                        if(slots[x][y].getType() == 2) {
                            slots[x][y].item.removedFromInventory((Player) entity);
                        }

                        if(slots[x][y].getType() == 3) {
                            slots[x][y].item.removedFromInventory((Player) entity);
                        }

                        if(slots[x][y].getType() == 4) {
                            slots[x][y].item.removedFromInventory((Player) entity);
                        }

                        int tempAmount = slots[x][y].getAmountOfItems();
                        Item tempItem = slots[x][y].removeItem();
                        slots[x][y].addItem(item, amount);
                        panel.removeComponent(itemSprite);
                        amount = tempAmount;
                        item = tempItem;
                        itemSprite = new UISprite(new Vector2i(Mouse.getX(), Mouse.getY()), item.getSprite().path);
                        panel.addComponent(itemSprite);
                        continue;
                    }
                }

                //Right Clicking on a slot without an item
                if(!holdingItem && slots[x][y].hasItem && slots[x][y].isRightClicked) {
                    if(slots[x][y].getType() == 0 && item instanceof ItemSword) {
                        slots[x][y].item.removedFromInventory((Player) entity);
                    }

                    if(slots[x][y].getType() == 1 && item instanceof ItemHelmet) {
                        slots[x][y].item.removedFromInventory((Player) entity);
                    }

                    if(slots[x][y].getType() == 2 && item instanceof ItemChestPlate) {
                        slots[x][y].item.removedFromInventory((Player) entity);
                    }

                    if(slots[x][y].getType() == 3 && item instanceof ItemBoots) {
                        slots[x][y].item.removedFromInventory((Player) entity);
                    }

                    if(slots[x][y].getType() == 4 && item instanceof ItemRing) {
                        slots[x][y].item.removedFromInventory((Player) entity);
                    }

                    panel.removeComponent(itemSprite);
                    if(slots[x][y].getAmountOfItems() == 1) {
                        amount = 1;
                        item = slots[x][y].removeItem();
                    } else if((slots[x][y].getAmountOfItems() % 2) == 0) {
                        item = slots[x][y].item;
                        amount = slots[x][y].getAmountOfItems() / 2;
                        slots[x][y].setNumberOfItems(amount);
                    } else {
                        item = slots[x][y].item;
                        amount = slots[x][y].getAmountOfItems() / 2 + 1;
                        slots[x][y].setNumberOfItems(amount - 1);
                    }

                    itemSprite = new UISprite(new Vector2i(Mouse.getX(), Mouse.getY()), item.getSprite().path);
                    panel.addComponent(itemSprite);
                    holdingItem = true;
                    continue;
                }

                //Right clicking on a empty slot with an item
                if(holdingItem && !slots[x][y].hasItem && slots[x][y].isRightClicked) {
                    if(slots[x][y].getType() == 0 && item instanceof ItemSword) {
                        slots[x][y].item.putInInventory((Player) entity);
                        slots[x][y].addItem(item, 1);
                        if(amount > 0) amount--;
                        else amount = 0;
                        continue;
                    }

                    if(slots[x][y].getType() == 1 && item instanceof ItemHelmet) {
                        slots[x][y].item.putInInventory((Player) entity);
                        slots[x][y].addItem(item, 1);
                        if(amount > 0) amount--;
                        else amount = 0;
                        continue;
                    }

                    if(slots[x][y].getType() == 2 && item instanceof ItemChestPlate) {
                        slots[x][y].item.putInInventory((Player) entity);
                        slots[x][y].addItem(item, 1);
                        if(amount > 0) amount--;
                        else amount = 0;
                        continue;
                    }

                    if(slots[x][y].getType() == 3 && item instanceof ItemBoots) {
                        slots[x][y].item.putInInventory((Player) entity);
                        slots[x][y].addItem(item, 1);
                        if(amount > 0) amount--;
                        else amount = 0;
                        continue;
                    }

                    if(slots[x][y].getType() == 4 && item instanceof ItemRing) {
                        slots[x][y].item.putInInventory((Player) entity);
                        slots[x][y].addItem(item, 1);
                        if(amount > 0) amount--;
                        else amount = 0;
                        continue;
                    }

                    if(slots[x][y].getType() > 4) {
                        slots[x][y].addItem(item, 1);
                        if(amount > 0) amount--;
                        else amount = 0;
                        continue;
                    }
                }

                //Right clicking on a slot with the same item
                if(holdingItem && slots[x][y].hasItem && slots[x][y].isRightClicked && item.getClass().equals(slots[x][y].getItemInSlot().getClass())) {
                    if(slots[x][y].getAmountOfItems() >= item.stackSize) continue;
                    slots[x][y].addAmountOfItems(1);
                    if(amount > 0) amount--;
                    else amount = 0;
                }
            }
        }

        if(amount <= 0) {
            holdingItem = false;
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

        if(Mouse.getButton() == -1) key = 0;
    }

    public void addItem(Item item, int amount) {
        for(int y = 0; y < ySlots; y++) {
            for(int x = 0; x < xSlots; x++) {
                if(slots[x][y].hasItem && item.getClass().equals(slots[x][y].getItemInSlot().getClass())) {
                    int items = slots[x][y].getAmountOfItems() + amount;
                    if (items <= item.stackSize) {
                        slots[x][y].addItem(item, amount);
                        return;
                    } else {
                        slots[x][y].addItem(item, item.stackSize);
                        for (int y1 = 0; y1 < ySlots; y1++) {
                            for (int x1 = 0; x1 < xSlots; x1++) {

                                if(slots[x1][y1].getType() == 0 && item instanceof ItemSword) {
                                    if(slots[x1][y1].hasItem) {
                                        continue;
                                    } else {
                                        amount = items - item.stackSize;
                                        slots[x][y].addItem(item, amount);
                                        return;
                                    }
                                }

                                if(slots[x1][y1].getType() == 1 && item instanceof ItemHelmet) {
                                    if(slots[x1][y1].hasItem) {
                                        continue;
                                    } else {
                                        amount = items - item.stackSize;
                                        slots[x1][y1].addItem(item, amount);
                                        return;
                                    }
                                }

                                if(slots[x1][y1].getType() == 2 && item instanceof ItemChestPlate) {
                                    if(slots[x1][y1].hasItem) {
                                        continue;
                                    } else {
                                        amount = items - item.stackSize;
                                        slots[x1][y1].addItem(item, amount);
                                        return;
                                    }
                                }

                                if(slots[x1][y1].getType() == 3 && item instanceof ItemBoots) {
                                    if(slots[x1][y1].hasItem) {
                                        continue;
                                    } else {
                                        amount = items - item.stackSize;
                                        slots[x1][y1].addItem(item, amount);
                                        return;
                                    }
                                }

                                if(slots[x1][y1].getType() == 4 && item instanceof ItemRing) {
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

        for(int y = 0; y < ySlots; y++) {
            for(int x = 0; x < xSlots; x++) {
                if(slots[x][y].getType() == 0 && item instanceof ItemSword) {
                    if(!slots[x][y].hasItem) {
                        slots[x][y].addItem(item, amount);
                        slots[x][y].item.putInInventory((Player) entity);
                        return;
                    } else {
                        continue;
                    }
                }

                if(slots[x][y].getType() == 1 && item instanceof ItemHelmet) {
                    if(!slots[x][y].hasItem) {
                        slots[x][y].addItem(item, amount);
                        slots[x][y].item.putInInventory((Player) entity);
                        return;
                    } else {
                        continue;
                    }
                }

                if(slots[x][y].getType() == 2 && item instanceof ItemChestPlate) {
                    if(!slots[x][y].hasItem) {
                        slots[x][y].addItem(item, amount);
                        slots[x][y].item.putInInventory((Player) entity);
                        return;
                    } else {
                        continue;
                    }
                }

                if(slots[x][y].getType() == 3 && item instanceof ItemBoots) {
                    if(!slots[x][y].hasItem) {
                        slots[x][y].addItem(item, amount);
                        slots[x][y].item.putInInventory((Player) entity);
                        return;
                    } else {
                        continue;
                    }
                }

                if(slots[x][y].getType() == 4 && item instanceof ItemRing) {
                    if(!slots[x][y].hasItem) {
                        slots[x][y].addItem(item, amount);
                        slots[x][y].item.putInInventory((Player) entity);
                        return;
                    } else {
                        continue;
                    }
                }

                if(slots[x][y].getType() > 4) {
                    if(!slots[x][y].hasItem) {
                        slots[x][y].addItem(item, amount);
                        return;
                    }
                }
            }
        }
    }

    public void addItem(int x, int y, Item item, int amount) {
        if(slots[x][y].getType() == 0 && item instanceof ItemSword) {
            slots[x][y].item.putInInventory((Player) entity);
            slots[x][y].addItem(item, amount);
            holdingItem = false;
            panel.removeComponent(itemSprite);
        }

        if(slots[x][y].getType() == 1 && item instanceof ItemHelmet) {
            slots[x][y].item.putInInventory((Player) entity);
            slots[x][y].addItem(item, amount);
            holdingItem = false;
            panel.removeComponent(itemSprite);
        }

        if(slots[x][y].getType() == 2 && item instanceof ItemChestPlate) {
            slots[x][y].item.putInInventory((Player) entity);
            slots[x][y].addItem(item, amount);
            holdingItem = false;
            panel.removeComponent(itemSprite);
        }

        if(slots[x][y].getType() == 3 && item instanceof ItemBoots) {
            slots[x][y].item.putInInventory((Player) entity);
            slots[x][y].addItem(item, amount);
            holdingItem = false;
            panel.removeComponent(itemSprite);
        }

        if(slots[x][y].getType() == 4 && item instanceof ItemRing) {
            slots[x][y].item.putInInventory((Player) entity);
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

    private boolean canSwitchItem(int x, int y, Item item) {

        if(slots[x][y].getType() == 0 && item instanceof ItemSword) {
            return true;
        }

        if(slots[x][y].getType() == 1 && item instanceof ItemHelmet) {
            return true;
        }

        if(slots[x][y].getType() == 2 && item instanceof ItemChestPlate) {
            return true;
        }

        if(slots[x][y].getType() == 3 && item instanceof ItemBoots) {
            return true;
        }

        if(slots[x][y].getType() == 4 && item instanceof ItemRing) {
            return true;
        }

        if(slots[x][y].getType() > 4) {
            return true;
        }

        return false;
    }
}