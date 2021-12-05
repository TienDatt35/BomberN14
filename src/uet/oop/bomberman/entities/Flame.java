package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;

public class Flame extends Entity{

    private int type;
    private final int sta[] = {0, 1, 2, 1, 0};
    public static ArrayList <ArrayList<Image>> constImage = new ArrayList<>();

    public Flame(int x, int y, Image img, int type, long timeChange) {
        super(x, y, img);
        this.timeChange = timeChange;
        this.type = type;
        this.curState = -1;
    }

    //Tải hoạt ảnh của flame
    public static void load() {
        // center
        constImage.add(new ArrayList<Image>());
        constImage.get(0).add(Sprite.bomb_exploded.getFxImage());
        constImage.get(0).add(Sprite.bomb_exploded1.getFxImage());
        constImage.get(0).add(Sprite.bomb_exploded2.getFxImage());
        // ver 0
        constImage.add(new ArrayList<Image>());
        constImage.get(1).add(Sprite.explosion_vertical_top_last.getFxImage());
        constImage.get(1).add(Sprite.explosion_vertical_top_last1.getFxImage());
        constImage.get(1).add(Sprite.explosion_vertical_top_last2.getFxImage());
        // ver 1
        constImage.add(new ArrayList<Image>());
        constImage.get(2).add(Sprite.explosion_vertical.getFxImage());
        constImage.get(2).add(Sprite.explosion_vertical1.getFxImage());
        constImage.get(2).add(Sprite.explosion_vertical2.getFxImage());
        // ver 2
        constImage.add(new ArrayList<Image>());
        constImage.get(3).add(Sprite.explosion_vertical_down_last.getFxImage());
        constImage.get(3).add(Sprite.explosion_vertical_down_last1.getFxImage());
        constImage.get(3).add(Sprite.explosion_vertical_down_last2.getFxImage());
        // hor 0
        constImage.add(new ArrayList<Image>());
        constImage.get(4).add(Sprite.explosion_horizontal_left_last.getFxImage());
        constImage.get(4).add(Sprite.explosion_horizontal_left_last1.getFxImage());
        constImage.get(4).add(Sprite.explosion_horizontal_left_last2.getFxImage());
        // hor 1
        constImage.add(new ArrayList<Image>());
        constImage.get(5).add(Sprite.explosion_horizontal.getFxImage());
        constImage.get(5).add(Sprite.explosion_horizontal1.getFxImage());
        constImage.get(5).add(Sprite.explosion_horizontal2.getFxImage());
        // hor 2
        constImage.add(new ArrayList<Image>());
        constImage.get(6).add(Sprite.explosion_horizontal_right_last.getFxImage());
        constImage.get(6).add(Sprite.explosion_horizontal_right_last1.getFxImage());
        constImage.get(6).add(Sprite.explosion_horizontal_right_last2.getFxImage());
    }

    @Override
    public void update(long l) {
        if (this.death) {
            return;
        }
        if (l >= this.timeChange) {
            ++curState;
            //Sau mỗi lần update thì tăng curState để đổi hoạt ảnh của bom
            this.timeChange += 100000000;
        }

        if (curState == 5) {
            //Chạy hết 5 hoạt ảnh thì dừng
            this.death = true;
            return;
        }
        this.img = constImage.get(type).get(sta[curState]);
    }
}
