package uet.oop.bomberman.entities;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.Collections;


public class Balloon extends Enemy {
    public static ArrayList<ArrayList<Image>> constImage = new ArrayList<>();

    public static void load() {
        // lên
        constImage.add(new ArrayList<Image>());
        // phải
        constImage.add(new ArrayList<Image>());
        constImage.get(1).add(Sprite.balloom_right1.getFxImage());
        constImage.get(1).add(Sprite.balloom_right2.getFxImage());
        constImage.get(1).add(Sprite.balloom_right3.getFxImage());
        // xuống
        constImage.add(new ArrayList<Image>());
        // trái
        constImage.add(new ArrayList<Image>());
        constImage.get(3).add(Sprite.balloom_left1.getFxImage());
        constImage.get(3).add(Sprite.balloom_left2.getFxImage());
        constImage.get(3).add(Sprite.balloom_left3.getFxImage());
        // chết
        constImage.add(new ArrayList<Image>());
        constImage.get(4).add(Sprite.balloom_dead.getFxImage());
        constImage.get(4).add(Sprite.mob_dead1.getFxImage());
        constImage.get(4).add(Sprite.mob_dead2.getFxImage());
        constImage.get(4).add(Sprite.mob_dead3.getFxImage());
    }

    public Balloon(int x, int y, Image img) {
        super(x, y, img);
    }

    public boolean canMove(int newX, int newY) {
        Rectangle2D rect = new Rectangle2D(newX, newY, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
        Rectangle2D initRect = new Rectangle2D(this.x, this.y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);

        for(int i = 0; i < BombermanGame.stillObjects.size(); i++) {
            double tmpX = BombermanGame.stillObjects.get(i).getX();
            double tmpY = BombermanGame.stillObjects.get(i).getY();
            if (rect.intersects(tmpX, tmpY, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE)) {
                return false;
            }
        }

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
        //Kiểm tra trạng thái quái
        if (this.isDeath() == true) {
            if (l >= timeChange) {
                timeChange += 100000000;
                curState += 1;
                if (curState == 4) {
                    return;
                }
                // Hiển thị hình ảnh quái chết
                this.img = constImage.get(4).get(curState);
            }
            return;
        }


        //Lấy random hướng cho quái ở mỗi ô
        if (this.x % 32 == 0 && this.y % 32 == 0) {
            this.dir = (int) (Math.random() * 4);
        }

        int newX = this.x + dirX[this.dir] * this.speed;
        int newY = this.y + dirY[this.dir] * this.speed;

        if (canMove(newX, newY) == false) {
            // Đảo các hướng có sẵn của quái
            Collections.shuffle(randomDir);
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
        curState %= 9; // Đây là xử lý mượt

        if (this.dir == 1) {
            this.imgDir = 1;
        }
        if (this.dir == 3) {
            this.imgDir = 3;
        }
        this.img = constImage.get(this.imgDir).get(this.curState / 3);

        //Xử lý bom nổ quái
        Rectangle2D rect = new Rectangle2D(this.x, this.y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
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
    }
}