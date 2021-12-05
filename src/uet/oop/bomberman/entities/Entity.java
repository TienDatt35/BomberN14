package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {
    protected int x;
    protected int y;
    protected Image img;
    protected int dir; //Hướng
    protected int curState; //Trạng thái của ảnh
    protected boolean death;
    protected long timeChange; //Thời điểm update

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity(int x, int y, Image img) {
        this.x = x * Sprite.SCALED_SIZE;
        this.y = y * Sprite.SCALED_SIZE;
        this.img = img;
        this.dir = 1;
        this.curState = 0;
        this.death = false;
        this.timeChange = -1;
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
