package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;

public class Bomber extends Entity {

    private static int bombLimit = 1;
    private static int speed = 2;
    public static ArrayList<ArrayList<Image>> constImage = new ArrayList<>();


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
    }

    @Override
    public void update(long l) {

    }
}
