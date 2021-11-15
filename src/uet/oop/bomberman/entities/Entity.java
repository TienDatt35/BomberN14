package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;
    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;

    protected Image img;
    protected int dir;
    protected int curState;
    protected boolean death;
    protected long timeChange;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity( int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
        this.dir = 1;
        this.curState = 0;
        this.death = false;
        this.timeChange = -1;
    }

    public Entity( int x, int y, Image img, long timeChange) {
        this.x = x * Sprite.SCALED_SIZE;
        this.y = y * Sprite.SCALED_SIZE;
        this.img = img;
        this.dir = 1;
        this.curState = 0;
        this.death = false;
        this.timeChange = timeChange;
    }

    public void moveRight() {
        this.x = this.x + 5;
        this.img = Sprite.player_right.getFxImage();
    }

    public void moveLeft() {
        this.x = this.x - 5;
        this.img = Sprite.player_left.getFxImage();
    }

    public void moveUp() {
        this.y = this.y - 5;
        this.img = Sprite.player_up.getFxImage();
    }

    public void moveDown() {
        this.y = this.y + 5;
        this.img = Sprite.player_down.getFxImage();
    }

    public int getCurState() {
        return curState;
    }

    public void setCurState(int x) {
        this.curState = x;
    }

    public boolean isDeath() {
        return death;
    }

    public void setDeath(boolean death) {
        this.death = death;
    }

    public void setTimeChange(long timeChange) {
        this.timeChange = timeChange;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public int getDir() {
        return dir;
    }

    public long getTimeChange() {
        return timeChange;
    }

    public void addTimeChange(long x) {
        timeChange += x;
    }

    public void setImg(Image newImg) {
        img = newImg;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
    public abstract void update(long l);
}
