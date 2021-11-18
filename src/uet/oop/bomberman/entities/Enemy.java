package uet.oop.bomberman.entities;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enemy extends Entity {
    protected int speed; //Tốc độ
    protected int imgDir; //Ảnh hướng

    protected static int[] dirX = {0, 1, 0, -1};
    protected static int[] dirY = {-1, 0, 1, 0};

    List<Integer> randomDir = new ArrayList<Integer>(); //List lưu lại các hướng

    public Enemy(int x, int y, Image img) {
        super(x, y, img);
        this.speed = 1;
        this.imgDir = 1;
        for (int i = 0; i < 4; ++i) {
            randomDir.add(i);
        }
    }

    @Override
    public void update(long l) {

    }
}
