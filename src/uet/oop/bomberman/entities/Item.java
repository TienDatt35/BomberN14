package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class Item extends Entity {
    private int type;
    private boolean open;

    public Item(int x, int y, Image img, int type) {
        super(x, y, img);
        this.type = type;
        open = false;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public int getType() {
        return type;
    }

    @Override
    public void update(long l) {

    }
}
