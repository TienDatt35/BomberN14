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

    public boolean canMove(int newX, int newY) {
        Rectangle2D rect = new Rectangle2D(newX, newY, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
        Rectangle2D initRect = new Rectangle2D(this.x, this.y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);

        for(int i = 0; i < BombermanGame.bombs.size(); i++) {
            double tmpX = BombermanGame.bombs.get(i).getX();
            double tmpY = BombermanGame.bombs.get(i).getY();
            if (initRect.intersects(tmpX, tmpY, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE)) {
                continue;
            }
            if (rect.intersects(tmpX, tmpY, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE)) {
                return false;
            }
        }

        for(int i = 0; i < BombermanGame.stillObjects.size(); i++) {
            double tmpX = BombermanGame.stillObjects.get(i).getX();
            double tmpY = BombermanGame.stillObjects.get(i).getY();
            if (rect.intersects(tmpX, tmpY, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE)) {
                return false;
            }
        }

        for(int i = 0; i < BombermanGame.bricks.size(); i++) {
            double tmpX = BombermanGame.bricks.get(i).getX();
            double tmpY = BombermanGame.bricks.get(i).getY();
            if (rect.intersects(tmpX, tmpY, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void update(long l) {

    }
}
