package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;

public class Brick extends Entity {
    private int containItem;
    public static ArrayList <ArrayList<Image>> constImage = new ArrayList<>();

    public Brick(int x, int y, Image img) {
        super(x, y, img);
        containItem = -1;
    }

    //Tải hoạt ảnh của tường
    public static void load() {
        ArrayList<Image> brickImage = new ArrayList<>();
        brickImage.add(Sprite.brick_exploded.getFxImage());
        brickImage.add(Sprite.brick_exploded1.getFxImage());
        brickImage.add(Sprite.brick_exploded2.getFxImage());
        constImage.add(brickImage);
    }

    public void setContainItem(int containItem) {
        this.containItem = containItem;
    }

    public int getContainItem() {
        return containItem;
    }

    @Override
    public void update(long l) {
        if (this.isDeath()) {
            if (l >= timeChange) {
                timeChange += 200000000;
                ++curState;
                if (curState == 3) {
                    return;
                }
                this.img = constImage.get(0).get(curState);
            }
            return;
        }
    }
}
