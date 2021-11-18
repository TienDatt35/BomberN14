package uet.oop.bomberman.entities;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Kondoria extends Enemy {
    public static ArrayList <ArrayList<Image> > constImage = new ArrayList<>();
    public static void load() {
        //up
        constImage.add(new ArrayList<Image>());
        //right
        constImage.add(new ArrayList<Image>());
        constImage.get(1).add(Sprite.kondoria_right1.getFxImage());
        constImage.get(1).add(Sprite.kondoria_right2.getFxImage());
        constImage.get(1).add(Sprite.kondoria_right3.getFxImage());
        //down
        constImage.add(new ArrayList<Image>());
        //left
        constImage.add(new ArrayList<Image>());
        constImage.get(3).add(Sprite.kondoria_left1.getFxImage());
        constImage.get(3).add(Sprite.kondoria_left2.getFxImage());
        constImage.get(3).add(Sprite.kondoria_left3.getFxImage());
        /// dead
        constImage.add(new ArrayList<Image>());
        constImage.get(4).add(Sprite.kondoria_dead.getFxImage());
        constImage.get(4).add(Sprite.mob_dead1.getFxImage());
        constImage.get(4).add(Sprite.mob_dead2.getFxImage());
        constImage.get(4).add(Sprite.mob_dead3.getFxImage());
    }

    public Kondoria(int x, int y, Image img) {
        super( x, y, img);
    }


    public boolean canMove(int newX, int newY) {
        Rectangle2D rect = new Rectangle2D(newX, newY, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);

        for (Wall wall : BombermanGame.stillObjects) {
            if (rect.intersects(wall.getX(), wall.getY(), Sprite.SCALED_SIZE, Sprite.SCALED_SIZE)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void update(long l) {
        if (this.isDeath()) {
            if (l >= timeChange) {
                timeChange += 100000000;
                ++curState;
                if (curState == 4) {
                    return;
                }
                this.img = constImage.get(4).get(curState);
            }
            return;
        }
        Rectangle2D rect = new Rectangle2D(x, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);

        for (int i = 0; i < BombermanGame.flames.size(); i++) {
            double tmpX = BombermanGame.flames.get(i).getX();
            double tmpY = BombermanGame.flames.get(i).getY();
            if (rect.intersects(tmpX, tmpY, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE)) {
                this.setDeath(true);
                this.curState = -1;
                this.timeChange = l;
                return;
            }
        }

        Collections.shuffle(randomDir);

        if (this.x % 32 == 0 && this.y % 32 == 0) {
            this.dir = (int) (Math.random() * 4);
        }
        int newX = this.x + dirX[this.dir] * this.speed;
        int newY = this.y + dirY[this.dir] * this.speed;
        if (!canMove(newX, newY)) {
            for (int id = 0; id < 4; ++id) {
                int i = randomDir.get(id);
                newX = this.x + dirX[i] * this.speed;
                newY = this.y + dirY[i] * this.speed;
                if (canMove(newX, newY)) {
                    this.dir = i;
                    break;
                }
            }
        }

        this.setX(newX);
        this.setY(newY);

        curState += 1;
        curState %= 9;
        if (this.dir == 1) {
            this.imgDir = 1;
        }
        if (this.dir == 3) {
            this.imgDir = 3;
        }
        this.img = constImage.get(this.imgDir).get(this.curState / 3);
    }
}