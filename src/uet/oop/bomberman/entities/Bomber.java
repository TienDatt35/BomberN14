package uet.oop.bomberman.entities;

import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;

public class Bomber extends Entity {

    private static int bombLimit = 1;
    private static int speed = 1;
    public static ArrayList<ArrayList<Image>> constImage = new ArrayList<>();
    private boolean upSpeed;
    private int life;
    private boolean reborn;

    public static void resetBombLimit() {
        bombLimit = 1;
    }

    public static void resetSpeed() {
        speed = 1;
    }


    public static void load() {
        ArrayList<Image> up = new ArrayList<>();
        ArrayList<Image> right = new ArrayList<>();
        ArrayList<Image> down = new ArrayList<>();
        ArrayList<Image> left = new ArrayList<>();
        ArrayList <Image> dead = new ArrayList<>();

        // 0: up
        up.add(Sprite.player_up.getFxImage());
        up.add(Sprite.player_up_1.getFxImage());
        up.add(Sprite.player_up_2.getFxImage());
        constImage.add(up);

        // 1: right
        right.add(Sprite.player_right.getFxImage());
        right.add(Sprite.player_right_1.getFxImage());
        right.add(Sprite.player_right_2.getFxImage());
        constImage.add(right);

        // 2: down
        down.add(Sprite.player_down.getFxImage());
        down.add(Sprite.player_down_1.getFxImage());
        down.add(Sprite.player_down_2.getFxImage());
        constImage.add(down);

        // 3: left
        left.add(Sprite.player_left.getFxImage());
        left.add(Sprite.player_left_1.getFxImage());
        left.add(Sprite.player_left_2.getFxImage());
        constImage.add(left);

        //dead
        dead.add(Sprite.player_dead1.getFxImage());
        dead.add(Sprite.player_dead2.getFxImage());
        dead.add(Sprite.player_dead3.getFxImage());
        constImage.add(dead);
    }
    public Bomber(int x, int y, Image img) {
        super( x, y, img);
        upSpeed = false;
        life = 3;
        reborn = false;
    }

    int dirX(int dir) {
        if (dir == 1) {
            return 1;
        }
        if (dir == 3) {
            return -1;
        }
        return 0;
    }

    int dirY(int dir) {
        if (dir == 0) {
            return -1;
        }
        if (dir == 2) {
            return 1;
        }
        return 0;
    }

    int moveX(int curDir, Entity entity) {
        switch (curDir) {
            case 1:
            case 3:
                return 0;
            case 0:
                if (x < entity.getX() && entity.getX() - x > 10) {
                    return -1;
                }
                if (x > entity.getX() && x - entity.getX() > 20) {
                    return 1;
                }
                break;
            case 2:
                if (x < entity.getX() && entity.getX() - x > 10) {
                    return -1;
                }
                if (x > entity.getX() && x - entity.getX() > 20) {
                    return 1;
                }
                break;
        }
        return 0;
    }

    int moveY(int curDir, Entity entity) {
        switch (curDir) {
            case 0:
            case 2:
                return 0;
            case (1):
                if (y > entity.getY() && y - entity.getY() > 20) {
                    return 1;
                }
                if (y < entity.getY() && entity.getY() - y > 20) {
                    return -1;
                }
                break;
            case (3):
                if (y > entity.getY() && y - entity.getY() > 20) {
                    return 1;
                }
                if (y < entity.getY() && entity.getY() - y > 20) {
                    return -1;
                }
                break;
        }
        return 0;
    }

    public int canMove(int curDir) {
        int aX = 0;
        int aY = 0;
        int newX = x + dirX(curDir) * speed;
        int newY = y + dirY(curDir) * speed;
        boolean meetBlock = false;
        Rectangle2D rect = new Rectangle2D(newX, newY, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
        Rectangle2D initRect = new Rectangle2D(x, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
//      bomb
//        for (Bomb bomb : BombermanGame.bombs) {
//            if (!rect.intersects(bomb.getX(), bomb.getY(), Sprite.SCALED_SIZE, Sprite.SCALED_SIZE)) {
//                continue;
//            }
//            if (initRect.intersects(bomb.getX(), bomb.getY(), Sprite.SCALED_SIZE, Sprite.SCALED_SIZE)) {
//                continue;
//            }
//
//            if (!meetBlock) {
//                meetBlock = true;
//            }
//            aX += moveX(curDir, bomb);
//            aY += moveY(curDir, bomb);
//        }
//      wall
        for (Wall wall : BombermanGame.stillObjects) {
            if (!rect.intersects(wall.getX(), wall.getY(), Sprite.SCALED_SIZE, Sprite.SCALED_SIZE)) {
                continue;
            }
            if (meetBlock == false) {
                meetBlock = true;
            }
            aX += moveX(curDir, wall);
            aY += moveY(curDir, wall);
        }
//      brick
        for (Brick brick : BombermanGame.bricks) {
            if (!rect.intersects(brick.getX(), brick.getY(), Sprite.SCALED_SIZE, Sprite.SCALED_SIZE)) {
                continue;
            }
            if (!meetBlock) {
                meetBlock = true;
            }
            aX += moveX(curDir, brick);
            aY += moveY(curDir, brick);
        }

        if (!meetBlock) { /// take bonus later
            x = newX;
            y = newY;
            return curDir;
        }
        if (aX == 0 && aY == 0) {
            return curDir;
        }
        x = x + aX * speed;
        y = y + aY * speed;
        if (aX == 1) {
            return 1;
        }
        if (aX == -1) {
            return 3;
        }
        if (aY == 1) {
            return 2;
        }
//        if (aY == -1) {
        return 0;
//        }
    }

    void dropBomb(long l) {
//        if (BombermanGame.bombs.size() == bombLimit) return;
//        int xb = (x / 32);
//        int yb = (y / 32);
//        Rectangle2D rect = new Rectangle2D(xb * Sprite.SCALED_SIZE, yb * Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
//        for (Bomb b : BombermanGame.bombs) {
//            if (rect.intersects(b.getX(), b.getY(), Sprite.SCALED_SIZE, Sprite.SCALED_SIZE)) {
//                return;
//            }
//        }
//        Bomb newBomb = new Bomb(xb, yb, Sprite.bomb.getFxImage(), l);
//        BombermanGame.bombs.add(newBomb);
//        BombermanGame.playSound(BombermanGame.clipBombSet);
    }

    public void setUpSpeed(boolean upSpeed) {
        this.upSpeed = upSpeed;
    }

    public void upSpeed() {
        if ((this.upSpeed == false) || (x % 32 != 0) || (y % 32) != 0) {
            return;
        }
        this.setUpSpeed(false);
        speed = Math.min(speed * 2, 32);
    }

    public void upLimitBomb() {
        ++bombLimit;
    }

    public void getBonus() {
        Rectangle2D rect = new Rectangle2D(this.x, this.y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
//        for (Item i : BombermanGame.items) {
//            if (!i.isOpen() || i.isDeath()) continue;
//            if (rect.intersects(i.getX(), i.getY(), Sprite.SCALED_SIZE, Sprite.SCALED_SIZE)) {
//                if (i.getType() == 4) {
//                    if (BombermanGame.enemies.size() == 0) {
//                        BombermanGame.winGame = true;
//                        BombermanGame.playSound(BombermanGame.clipExitOpen);
//                    }
//                    return;
//                }
//
//                i.setDeath(true);
//                BombermanGame.playSound(BombermanGame.clipitemGet);
//                switch (i.getType()) {
//                    case (3):
//                        this.setUpSpeed(true);
//                        break;
//                    case (2):
//                        Bomb.upBombLen();
//                        break;
//                    case (1):
//                        this.upLimitBomb();
//                        break;
//                }
//                return;
//            }
//        }
    }

    public void decLife() {
        --this.life;
    }

    public boolean notReallyDie() {
        return (this.life > 0);
    }

    public void setReborn(boolean x) {
        this.reborn = x;
    }

    public boolean isReborn() {
        return this.reborn;
    }

    public void letsReborn() {
        this.setX(Sprite.SCALED_SIZE);
        this.setY(Sprite.SCALED_SIZE);
        this.setImg(Sprite.player_right.getFxImage());
        this.dir = 1;
        this.curState = 0;
        this.death = false;
        this.timeChange = -1;
    }

    @Override
    public void update(long l) {
        if (this.isDeath()) {
            if (l >= this.getTimeChange()) {
                this.addTimeChange(400000000);
                this.setCurState(this.getCurState() + 1);
                if (this.getCurState() == 3) {
                    decLife();
                    if (this.notReallyDie()) {
                        this.setReborn(true);
                    }
                    return;
                }
                this.setImg(constImage.get(4).get(curState));
            }
            return;
        }

//        if (touchFlameOrEnemy(l)) {
//            this.setDeath(true);
//            this.setCurState(-1);
//            this.setTimeChange(l);
//            return;
//        }

        /*Drop the bomb*/
        if (BombermanGame.dropBomb == true && BombermanGame.preDropBomb == false) {
            dropBomb(l);
        }
        // SPACE lasts ... ns
        BombermanGame.preDropBomb = BombermanGame.dropBomb;

        this.upSpeed();

        /* not move */
        if (BombermanGame.bomberDirection == -1) {
            this.setCurState(0);
            setImg(constImage.get(dir).get(curState));
            return;
        }

        /* cal next direction */
        int nxtDir = canMove(BombermanGame.bomberDirection);
//        if (touchFlameOrEnemy(l)) {
//            this.setDeath(true);
//            this.setCurState(-1);
//            this.setTimeChange(l);
//            return;
//        }

        if (nxtDir == this.getDir()) {
            this.setCurState((this.getCurState() + 1) % 9);
            setImg(constImage.get(dir).get(curState / 3));
        } else {
            this.setDir(nxtDir);
            this.setCurState(0);
            setImg(constImage.get(dir).get(curState));
        }

        /* get item */
        getBonus();
    }
}