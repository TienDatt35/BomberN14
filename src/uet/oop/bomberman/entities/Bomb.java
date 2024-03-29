package uet.oop.bomberman.entities;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;

public class Bomb extends Entity {
    public static int len = 1;
    private static int sta[] = {0, 1, 2, 1, 0};
    public static ArrayList<ArrayList<Image>> constImage = new ArrayList<>();
    public boolean killedByOtherBomb;

    public Bomb(int x, int y, Image img, long timeChange) {
        super(x, y, img);
        this.timeChange = timeChange;
        this.curState = -1;
        this.killedByOtherBomb = false;
    }

    //Tải hoạt ảnh của bom
    public static void load() {
        ArrayList<Image> bomImage = new ArrayList<>();
        bomImage.add(Sprite.bomb.getFxImage());
        bomImage.add(Sprite.bomb_1.getFxImage());
        bomImage.add(Sprite.bomb_2.getFxImage());
        constImage.add(bomImage);
    }

    private boolean canMove(int x, int y, long l) {
        //Kiểm tra xung quanh bomb để tạo flame
        Rectangle2D rect = new Rectangle2D(x, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
        for (int i = 0; i < BombermanGame.stillObjects.size(); i++) {
            //Nếu gặp tường thì false
            if (rect.intersects(BombermanGame.stillObjects.get(i).getX(), BombermanGame.stillObjects.get(i).getY(), Sprite.SCALED_SIZE, Sprite.SCALED_SIZE)) {
                return false;
            }
        }
        for (int i = 0; i < BombermanGame.bricks.size(); i++) {
            //Nếu gặp brick thì phá
            if (BombermanGame.bricks.get(i).isDeath()) {
                continue;
            }
            if (rect.intersects(BombermanGame.bricks.get(i).getX(), BombermanGame.bricks.get(i).getY(), Sprite.SCALED_SIZE,Sprite.SCALED_SIZE)) {
                BombermanGame.bricks.get(i).death = true;
                BombermanGame.bricks.get(i).curState = -1;
                BombermanGame.bricks.get(i).timeChange = l;
                return false;
            }
        }
        for (int j = BombermanGame.curIdBomb + 1; j < BombermanGame.bombs.size(); ++j) {
            //Nếu gặp bom thì cho nổ
            Bomb b = BombermanGame.bombs.get(j);
            if (rect.intersects(b.getX(), b.getY(), Sprite.SCALED_SIZE, Sprite.SCALED_SIZE)) {
                b.killedByOtherBomb = true;
            }
        }
        return true;
    }

    public void addFlame(long l) {
        int newX, newY;
        //Tạo flame tại vị trí bom
        Flame flameB = new Flame(this.x / Sprite.SCALED_SIZE, this.y / Sprite.SCALED_SIZE, Sprite.oneal_right1.getFxImage(), 0, l);
        BombermanGame.flames.add(flameB);
        //Kiểm tra vật thể bên trái bom
        for (int i = 1; i <= len; ++i) {
            newX = this.x - i * Sprite.SCALED_SIZE;
            newY = this.y;
            if (canMove(newX, newY, l) == false) {
                //Kiểm tra nếu có vật thể thì không tạo flame
                break;
            }
            Flame flame;
            if (i == len) {
                flame = new Flame(newX / Sprite.SCALED_SIZE, newY / Sprite.SCALED_SIZE, Sprite.oneal_right1.getFxImage(), 4, l);
            }
            else {
                flame = new Flame(newX / Sprite.SCALED_SIZE, newY / Sprite.SCALED_SIZE, Sprite.oneal_right1.getFxImage(), 5, l);
            }
            BombermanGame.flames.add(flame);
        }
        //Kiểm tra vật thể bên phải bom
        for (int i = 1; i <= len; ++i) {
            newX = this.x + i * Sprite.SCALED_SIZE;
            newY = this.y;
            if (canMove(newX, newY, l) == false) {
                //Kiểm tra nếu có vật thể thì không tạo flame
                break;
            }
            Flame flame;
            if (i == len) {
                flame = new Flame(newX / Sprite.SCALED_SIZE, newY / Sprite.SCALED_SIZE, Sprite.oneal_right1.getFxImage(), 6, l);
            }
            else {
                flame = new Flame(newX / Sprite.SCALED_SIZE, newY / Sprite.SCALED_SIZE, Sprite.oneal_right1.getFxImage(), 5, l);
            }
            BombermanGame.flames.add(flame);
        }
        //Kiểm tra vật thể bên trên bom
        for (int i = 1; i <= len; ++i) {
            newX = this.x;
            newY = this.y - i * Sprite.SCALED_SIZE;
            if (canMove(newX, newY, l) == false) {
                //Kiểm tra nếu có vật thể thì không tạo flame
                break;
            }
            Flame flame;
            if (i == len) {
                flame = new Flame(newX / Sprite.SCALED_SIZE, newY / Sprite.SCALED_SIZE, Sprite.oneal_right1.getFxImage(), 1, l);
            }
            else {
                flame = new Flame(newX / Sprite.SCALED_SIZE, newY / Sprite.SCALED_SIZE, Sprite.oneal_right1.getFxImage(), 2, l);
            }
            BombermanGame.flames.add(flame);
        }
        //Kiểm tra vật thể bên dưới bom
        for (int i = 1; i <= len; ++i) {
            newX = this.x;
            newY = this.y + i * Sprite.SCALED_SIZE;
            if (canMove(newX, newY, l) == false) {
                //Kiểm tra nếu có vật thể thì không tạo flame
                break;
            }
            Flame flame;
            if (i == len) {
                flame = new Flame(newX / Sprite.SCALED_SIZE, newY / Sprite.SCALED_SIZE, Sprite.oneal_right1.getFxImage(), 3, l);
            }
            else {
                flame = new Flame(newX / Sprite.SCALED_SIZE, newY / Sprite.SCALED_SIZE, Sprite.oneal_right1.getFxImage(), 2, l);
            }
            BombermanGame.flames.add(flame);
        }
    }

    public static void resetLen() {
        len = 1;
    }

    public static void upBombLen() {
        ++len;
    }

    public boolean isKilledByOtherBomb() {
        return killedByOtherBomb;
    }

    @Override
    public void update(long l) {
        if (this.death) {
            return;
        }
        if (l >= this.timeChange) {
            ++curState;
            //Sau mỗi lần update thì tăng curState để đổi hoạt ảnh của bom
            this.timeChange += 400000000;
        }
        if (curState == 5) {
            //Chạy hết 5 hoạt ảnh thì cho nổ
            curState = -1;
            this.death = true;
            addFlame(l);
            return;
        }
        this.img = constImage.get(0).get(sta[curState]);
    }
}