package uet.oop.bomberman.entities;

import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.Audio.Audio;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;

public class Bomber extends Entity {

    private static int bombLimit = 1;
    private static int speed = 2;
    public static ArrayList<ArrayList<Image>> constImage = new ArrayList<>();
    private boolean upSpeed;
    private int life;
    private boolean reborn;
    public static int k = 0;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        upSpeed = false;
        life = 3;
        reborn = false;
    }

    //Tại hoạt ảnh của bomber
    public static void load() {
        ArrayList<Image> up = new ArrayList<>();
        ArrayList<Image> right = new ArrayList<>();
        ArrayList<Image> down = new ArrayList<>();
        ArrayList<Image> left = new ArrayList<>();
        ArrayList <Image> dead = new ArrayList<>();

        // 0: lên trên
        up.add(Sprite.player_up.getFxImage());
        up.add(Sprite.player_up_1.getFxImage());
        up.add(Sprite.player_up_2.getFxImage());
        constImage.add(up);

        // 1: sang phải
        right.add(Sprite.player_right.getFxImage());
        right.add(Sprite.player_right_1.getFxImage());
        right.add(Sprite.player_right_2.getFxImage());
        constImage.add(right);

        // 2: xuống dưới
        down.add(Sprite.player_down.getFxImage());
        down.add(Sprite.player_down_1.getFxImage());
        down.add(Sprite.player_down_2.getFxImage());
        constImage.add(down);

        // 3: sang trái
        left.add(Sprite.player_left.getFxImage());
        left.add(Sprite.player_left_1.getFxImage());
        left.add(Sprite.player_left_2.getFxImage());
        constImage.add(left);

        //chết
        dead.add(Sprite.player_dead1.getFxImage());
        dead.add(Sprite.player_dead2.getFxImage());
        dead.add(Sprite.player_dead3.getFxImage());
        constImage.add(dead);
    }

    int dirX(int dir) { //Di chuyển trái phải
        switch (dir) {
            case 1:
                return 1;
            case 3:
                return -1;
            default:
                return 0;
        }
    }

    int dirY(int dir) { //Di chuyển lên xuống
        switch (dir) {
            case 0:
                return -1;
            case 2:
                return 1;
            default:
                return 0;
        }
    }

    int moveX(int curDir, Entity entity) {
        switch (curDir) {
            //Nếu đứng giữa ô thì không di chuyển sang 2 bên
            case 1: //Đi sang phải
            case 3: //Đi sang trái
                return 0;
            case 0: //Đi lên trên
                if (x < entity.getX() && entity.getX() - x > 10) {
                    //Nếu vị trí đứng nhỏ hơn vị trí của entity gặp phải thì đi sang trái
                    return -1;
                }
                if (x > entity.getX() && x - entity.getX() > 20) {
                    //Nếu vị trí đứng lớn hơn vị trí của entity gặp phải thì đi sang phải
                    return 1;
                }
                break;
            case 2: //Đi xuống dưới
                if (x < entity.getX() && entity.getX() - x > 10) {
                    //Nếu vị trí đứng nhỏ hơn vị trí của entity gặp phải thì đi sang trái
                    return -1;
                }
                if (x > entity.getX() && x - entity.getX() > 20) {
                    //Nếu vị trí đứng lớn hơn vị trí của entity gặp phải thì đi sang phải
                    return 1;
                }
                break;
        }
        return 0;
    }

    int moveY(int curDir, Entity entity) { //Di chuyển trái, phải
        switch (curDir) {
            //Nếu đứng giữa ô thì không di chuyển sang 2 bên
            case 0: //Đi lên trên
            case 2: //Đi xuống dưới
                return 0;
            case (1): //Đi sang phải
                if (y > entity.getY() && y - entity.getY() > 20) {
                    //Nếu vị trí đứng lớn hơn vị trí của entity gặp phải thì đi xuống
                    return 1;
                }
                if (y < entity.getY() && entity.getY() - y > 20) {
                    //Nếu vị trí đứng nhỏ hơn vị trí của entity gặp phải thì đi lên
                    return -1;
                }
                break;
            case (3): //Đi sang trái
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

    //Kiểm tra di chuyển
    public int canMove(int curDir) {
        int aX = 0;
        int aY = 0;
        int newX = x + dirX(curDir) * speed;
        int newY = y + dirY(curDir) * speed;
        boolean meetBlock = false;
        //Tạo khối tại vị trí di chuyển tới
        Rectangle2D rect = new Rectangle2D(newX, newY, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
        //Tạo khối tại vị trí vừa đi qua
        Rectangle2D initRect = new Rectangle2D(x, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
//      bomb
        for (int i = 0; i < BombermanGame.bombs.size(); i++) {
            if (rect.intersects(BombermanGame.bombs.get(i).getX(), BombermanGame.bombs.get(i).getY(), Sprite.SCALED_SIZE, Sprite.SCALED_SIZE) == false) {
                //Kiểm tra vị trí di chuyển tới
                continue;
            }
            if (initRect.intersects(BombermanGame.bombs.get(i).getX(), BombermanGame.bombs.get(i).getY(), Sprite.SCALED_SIZE, Sprite.SCALED_SIZE) == true) {
                //Kiểm tra vị trí vừa đi qua
                continue;
            }

            if (meetBlock == false) {
                meetBlock = true;
            }
            aX += moveX(curDir, BombermanGame.bombs.get(i));
            aY += moveY(curDir, BombermanGame.bombs.get(i));
        }
//      wall
        for (int i = 0; i < BombermanGame.stillObjects.size(); i++) {
            if (rect.intersects(BombermanGame.stillObjects.get(i).getX(), BombermanGame.stillObjects.get(i).getY(), Sprite.SCALED_SIZE, Sprite.SCALED_SIZE) == false) {
                //Kiểm tra vị trí di chuyển tới
                continue;
            }
            if (meetBlock == false) {
                meetBlock = true;
            }
            aX += moveX(curDir, BombermanGame.stillObjects.get(i));
            aY += moveY(curDir, BombermanGame.stillObjects.get(i));
        }
//      brick
        for (int i = 0; i < BombermanGame.bricks.size(); i++) {
            if (rect.intersects(BombermanGame.bricks.get(i).getX(), BombermanGame.bricks.get(i).getY(), Sprite.SCALED_SIZE, Sprite.SCALED_SIZE) == false) {
                //Kiểm tra vị trí di chuyển tới
                continue;
            }
            if (meetBlock == false) {
                meetBlock = true;
            }
            aX += moveX(curDir, BombermanGame.bricks.get(i));
            aY += moveY(curDir, BombermanGame.bricks.get(i));
        }

        if (meetBlock == false) { /// take bonus later
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
        return 0;
    }

    void dropBomb(long l) {
        if (BombermanGame.bombs.size() == bombLimit) {
            //Nếu bom đạt giới hạn thì không thể đặt
            return;
        }
        //Xử lí sai số do không đứng giữa ô
        int xb;
        int yb;
        if (this.dir == 1) {
            if (x % 32 > 20) {
                xb = x / 32 + 1;
            } else {
                xb = x / 32;
            }
        } else if (this.dir == 3) {
            if (x % 32 <= 20) {
                xb = x / 32;
            } else {
                xb = x / 32 + 1;
            }
        } else {
            xb = x / 32;
        }

        if (this.dir == 0) {
            if (y % 32 > 16) {
                yb = y / 32 + 1;
            } else {
                yb = y / 32;
            }
        } else if (this.dir == 2) {
            if (y % 32 <= 16) {
                yb = y / 32;
            } else {
                yb = y / 32 + 1;
            }
        } else {
            yb = y / 32;
        }

        Rectangle2D rect = new Rectangle2D(xb * Sprite.SCALED_SIZE, yb * Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
        for (int i = 0; i < BombermanGame.bombs.size(); i++) {
            //Kiểm tra nếu tại vị trí có bom thì không thể đặt thêm bom
            if (rect.intersects(BombermanGame.bombs.get(i).getX(), BombermanGame.bombs.get(i).getY(), Sprite.SCALED_SIZE, Sprite.SCALED_SIZE) == true) {
                return;
            }
        }
        //Tạo bom tại vị trí xb, yb với thời gian nổ là l
        Bomb newBomb = new Bomb(xb, yb, Sprite.bomb.getFxImage(), l);
        BombermanGame.bombs.add(newBomb);
        //Âm thanh bom
        Audio.playSound("sounds/Bomb_Set.wav");
    }

    public void getBonus() {
        Rectangle2D rect = new Rectangle2D(this.x, this.y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
        for (int i = 0; i < BombermanGame.items.size(); i++) {
            //Tường bị phá thì item mở và hiện ra
            if (!BombermanGame.items.get(i).isOpen() || BombermanGame.items.get(i).isDeath()) continue;
            if (rect.intersects(BombermanGame.items.get(i).getX(), BombermanGame.items.get(i).getY(), Sprite.SCALED_SIZE, Sprite.SCALED_SIZE)) {
                //Nếu bomber chạm vào sẽ nhận đc item
                if (BombermanGame.items.get(i).getType() == 4) {
                    if (BombermanGame.enemies.size() == 0) {
                        //Nếu enemy bị tiêu diệt hết thì win
                        if (k == 0){
                            k = 1;
                            BombermanGame.winGame = true;
                        }
                        Audio.playSound("sounds/Exit_Opens.wav");
                    }
                    return;
                }
                //Item bị xóa
                BombermanGame.items.get(i).setDeath(true);
                Audio.playSound("sounds/Item_Get.wav");
                int tmpType = BombermanGame.items.get(i).getType();
                if (tmpType == 1) {
                    this.upLimitBomb();
                }
                if (tmpType == 2) {
                    Bomb.upBombLen();
                }
                if (tmpType == 3) {
                    this.setUpSpeed(true);
                }
                return;
//                switch (BombermanGame.items.get(i).getType()) {
//                    case (1):
//                        this.upLimitBomb();
//                        break;
//                    case (2):
//                        Bomb.upBombLen();
//                        break;
//                    case (3):
//                        this.setUpSpeed(true);
//                        break;
//                }
//                return;
            }
        }
    }

    public boolean touchFlameOrEnemy(long l) {
        //Tạo khối để kiểm tra va chạm
        Rectangle2D rect = new Rectangle2D(x, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
        for (int i = 0; i < BombermanGame.flames.size(); i++) {
            //Nếu chạm vào flame thì trả về true
            if (rect.intersects(BombermanGame.flames.get(i).getX(), BombermanGame.flames.get(i).getY(), Sprite.SCALED_SIZE, Sprite.SCALED_SIZE) == true) {
                return true;
            }
        }
        for (int i = 0; i < BombermanGame.enemies.size(); i++) {
            if (!BombermanGame.enemies.get(i).isDeath()) {
                //Nếu chạm vào enemy còn sống thì trả về true
                if (rect.intersects(BombermanGame.enemies.get(i).getX(), BombermanGame.enemies.get(i).getY(), Sprite.SCALED_SIZE, Sprite.SCALED_SIZE) == true) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void resetSpeed() {
        speed = 2;
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

    public static void resetBombLimit() {
        bombLimit = 1;
    }

    public void upLimitBomb() {
        ++bombLimit;
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
        //Nếu chết thì hồi sinh
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

        //Kiểm tra xem có chạm vào quái hay flame hay không
        if (touchFlameOrEnemy(l)) {
            this.setDeath(true);
            this.setCurState(-1);
            this.setTimeChange(l);
            return;
        }

        //Thả bom
        if (BombermanGame.dropBomb == true) {
            dropBomb(l);
        }
//        if (BombermanGame.dropBomb == true && BombermanGame.preDropBomb == false) {
//            dropBomb(l);
//        }

        // SPACE lasts ... ns
//        BombermanGame.preDropBomb = BombermanGame.dropBomb;

        //Kiểm tra xem có upSpeed không
        this.upSpeed();

        //Khi không nhận lệnh di chuyển từ bàn phím thì dừng hoạt ảnh tại lệnh cuối cùng
        if (BombermanGame.bomberDirection == -1) {
            this.setCurState(0);
            setImg(constImage.get(dir).get(curState));
            return;
        }

        //Hướng di chuyển tiếp theo
        int nxtDir = canMove(BombermanGame.bomberDirection);
        //Kiếm tra va chạm
        if (touchFlameOrEnemy(l)) {
            this.setDeath(true);
            this.setCurState(-1);
            this.setTimeChange(l);
            return;
        }

        if (nxtDir == this.getDir()) {
            //Cùng hướng di chuyển cũ
            this.setCurState((this.getCurState() + 1) % 9);
            setImg(constImage.get(dir).get(curState / 3));
        } else {
            //Hướng di chuyển mới
            this.setDir(nxtDir);
            this.setCurState(0);
            setImg(constImage.get(dir).get(curState));
        }

        //Nhặt item
        getBonus();
    }
}