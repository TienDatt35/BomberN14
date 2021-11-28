package uet.oop.bomberman.entities;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.*;

public class Oneal extends Enemy {
    final private int maxSpeed = 3;
    public static int[][] d = new int[Sprite.SCALED_SIZE * BombermanGame.WIDTH][Sprite.SCALED_SIZE * BombermanGame.HEIGHT];
    public static ArrayList <ArrayList<Image> > constImage = new ArrayList<>();

    public static void load() {
        // lên
        constImage.add(new ArrayList<>());
        // phải
        constImage.add(new ArrayList<>());
        constImage.get(1).add(Sprite.oneal_right1.getFxImage());
        constImage.get(1).add(Sprite.oneal_right2.getFxImage());
        constImage.get(1).add(Sprite.oneal_right3.getFxImage());
        // xuống
        constImage.add(new ArrayList<>());
        // trái
        constImage.add(new ArrayList<>());
        constImage.get(3).add(Sprite.oneal_left1.getFxImage());
        constImage.get(3).add(Sprite.oneal_left2.getFxImage());
        constImage.get(3).add(Sprite.oneal_left3.getFxImage());
        // chết
        constImage.add(new ArrayList<Image>());
        constImage.get(4).add(Sprite.oneal_dead.getFxImage());
        constImage.get(4).add(Sprite.mob_dead1.getFxImage());
        constImage.get(4).add(Sprite.mob_dead2.getFxImage());
        constImage.get(4).add(Sprite.mob_dead3.getFxImage());
    }

    public Oneal(int x, int y, Image img) {
        super(x, y, img);
    }

    public boolean canMove(int newX, int newY) {
        Rectangle2D rect = new Rectangle2D(newX, newY, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
        Rectangle2D initRect = new Rectangle2D(this.x, this.y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);

        for(int i = 0; i < BombermanGame.bricks.size(); i++) {
            double tmpX = BombermanGame.bricks.get(i).getX();
            double tmpY = BombermanGame.bricks.get(i).getY();
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
        return true;
    }

    public void minDis() {
        int n = Sprite.SCALED_SIZE * BombermanGame.WIDTH;
        int m = Sprite.SCALED_SIZE * BombermanGame.HEIGHT;

        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < m; j++) {
                d[i][j] = 100000000;
            }
        }

        d[BombermanGame.player.getX()][BombermanGame.player.getY()] = 0;
        // Loang để tìm trọng số tại những điểm từ player có thể đến được
        // Càng gần player thì trọng số càng nhỏ
        int[] qX = new int[n * m];
        int[] qY = new int[n * m];
        int top = 0;
        int bot = 0;
        qX[bot] = BombermanGame.player.getX();
        qY[bot++] = BombermanGame.player.getY();
        while (top < bot) {
            int u = qX[top];
            int v = qY[top++];
            for (int i = 3; i >= 0; i--) {
                int x = u + dirX[i];
                int y = v + dirY[i];
                if (!canMove(x, y) || d[x][y] != 100000000 || x < 0 || y < 0 || x >= n || y >= m) {
                    continue;
                }
                d[x][y] = d[u][v] + 1;
                qX[bot] = x;
                qY[bot++] = y;
            }
        }
    }

    @Override
    public void update(long l) {
        if (this.isDeath()) {
            if (l >= timeChange) {
                timeChange += 100000000;
                curState += 1;
                if (curState == 4) {
                    return;
                }
                this.img = constImage.get(4).get(curState);
            }
            return;
        }

        int curDis = 100000000;
        int newX = this.x;
        int newY = this.y;

        this.speed =(int) (Math.random()*(maxSpeed + 1));

        Collections.shuffle(randomDir);

        for (int id = 3; id >= 0; id--) {
            int i = randomDir.get(id);
            int tempX = this.x + this.speed * dirX[i];
            int tempY = this.y + this.speed * dirY[i];

            if (curDis > d[tempX][tempY]) {
                curDis = d[tempX][tempY];
                this.dir = i;
                newX = tempX;
                newY = tempY;
            }
        }

        if (curDis == 100000000) {
            if (this.x % 32 == 0 && this.y % 32 == 0) {
                this.dir = (int) (Math.random() * 4);
            }
            newX = this.x + dirX[this.dir] * this.speed;
            newY = this.y + dirY[this.dir] * this.speed;
            if (canMove(newX, newY) == false) {
                Collections.shuffle(randomDir);
                for (int id = 3; id >= 0; id--) {
                    int i = randomDir.get(id);
                    newX = this.x + dirX[i] * this.speed;
                    newY = this.y + dirY[i] * this.speed;
                    if (canMove(newX, newY) == true) {
                        this.dir = i;
                        break;
                    }
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
