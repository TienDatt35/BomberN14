package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import uet.oop.bomberman.Audio.Audio;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;
import javafx.scene.input.KeyEvent;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import uet.oop.bomberman.Map.Map;

public class BombermanGame extends Application {

    public static final int WIDTH = 20;
    public static final int HEIGHT = 15;

    public static GraphicsContext gc;
    private Canvas canvas;

    public static List<Wall> stillObjects = new ArrayList<>();
    public static List<Brick> bricks = new ArrayList<>();
    public static List<Enemy> enemies = new ArrayList<>();
    public static List<Item> items = new ArrayList<>();
    public static List<Flame> flames = new ArrayList<>();
    public static List<Bomb> bombs = new ArrayList<>();

    public static String path = System.getProperty("user.dir") + "/res/";
    static Entity background = new Grass(0, 0, Sprite.grass.getFxImage());
    public static Bomber player;
    public static String[] map = {"Level1.txt", "Level2.txt", "Level3.txt"};

    public static int bomberDirection = -1;
    public static boolean dropBomb = false;
    public static boolean preDropBomb = false;
    public static int curIdBomb = -1;

    public static int curMap = 0;
    public static boolean winGame = false;

    public static void main(String[] args) {
        loadAll();
        Clip soundGame;
        try {
            soundGame = AudioSystem.getClip();
            soundGame.open(AudioSystem.getAudioInputStream(new File(path + "sounds/soundGame.wav")));
            soundGame.start();
            soundGame.loop(soundGame.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Application.launch(BombermanGame.class);
    }


    static void loadAll() {
        Brick.load();
        Balloon.load();
        Bomb.load();
        Bomber.load();
        Flame.load();
        Oneal.load();
        Kondoria.load();
        Minvo.load();
    }

    public void sleep(int milis) {
        try {
            Thread.sleep(milis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * 31, Sprite.SCALED_SIZE * 13);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case RIGHT: {
                    bomberDirection = 1;
                    break;
                }
                case LEFT: {
                    bomberDirection = 3;
                    break;
                }
                case UP: {
                    bomberDirection = 0;
                    break;
                }
                case DOWN: {
                    bomberDirection = 2;
                    break;
                }
                case SPACE: {
                    dropBomb = true;
                    break;
                }
            }
        });

        scene.addEventFilter(KeyEvent.KEY_RELEASED, key -> {
            if (key.getCode() != KeyCode.SPACE) {
                bomberDirection = -1;
            }
            if (key.getCode() == KeyCode.SPACE) {
                dropBomb = false;
            }
        });

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();
        Map.makeMap(map[0]);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (!player.notReallyDie()) {
                    curMap = 0;
                    sleep(1500);
                    Map.makeMap(map[0]);
                } else {
                    if (winGame) {
                        curMap = (curMap + 1) % 3;
                        Map.makeMap(map[curMap]);
                        winGame = false;
                    }
                }
                update(l);
                render();
                sleep(10);
            }
        };
        timer.start();
    }

    public void setBackground(Entity e) {
        background.setX(e.getX());
        background.setY(e.getY());
        background.render(gc);
    }

    public void update(long l) {
        for (int i = 0; i < bombs.size(); ++i) {
            Bomb b = bombs.get(i);
            setBackground(b);
            curIdBomb = i;
            if (b.isKilledByOtherBomb()) {
                b.setDeath(true);
                b.addFlame(l);
            } else {
                b.update(l);
            }
        }

        for (int i = bombs.size() - 1; i >= 0; --i) {
            Bomb b = bombs.get(i);
            if (b.isDeath()) {
                Audio.playSound("sounds/Bomb_Explodes.wav");
                bombs.remove(i);
            }
        }

        for (int i = flames.size() - 1; i >= 0; --i) {
            Flame f = flames.get(i);
            setBackground(f);
            f.update(l);
            if (f.isDeath()) {
                flames.remove(i);
            }
        }

        for (int i = bricks.size() - 1; i >=0 ; --i) {
            Brick e = bricks.get(i);
            setBackground(e);
            e.update(l);
            if (e.isDeath() && e.getCurState() == 3) {
                bricks.remove(i);
                if (e.getContainItem() != -1) {
                    items.get(e.getContainItem()).setOpen(true);
                }
            }
        }

        boolean calcDis = false;
        for (int i = enemies.size() - 1; i >= 0; --i) {
            Enemy e = enemies.get(i);
            if (e instanceof Oneal && calcDis == false) {
                calcDis = true;
                ((Oneal) e).minDis();
            }
            setBackground(e);
            e.update(l);
            if (e.isDeath() && e.getCurState() == 4) {
                enemies.remove(i);
                Audio.playSound("sounds/Enemy_Dead.wav");
            }
        }

        setBackground(player);
        player.update(l);
        if (player.isReborn()) {
            player.letsReborn();
            player.setReborn(false);
        }
    }

    public void render() {
        //Thêm item
        for (int i = 0; i < BombermanGame.items.size(); i++) {
            if (!BombermanGame.items.get(i).isOpen()) {
                continue;
            }
            if (BombermanGame.items.get(i).isDeath()) {
                setBackground(BombermanGame.items.get(i));
            } else {
                BombermanGame.items.get(i).render(gc);
            }
        }

        //Thêm bomb
        for (int i = 0; i < BombermanGame.bombs.size(); i++) {
            BombermanGame.bombs.get(i).render(gc);
        }

        //Thêm flame
        for (int i = 0; i < BombermanGame.flames.size(); i++) {
            BombermanGame.flames.get(i).render(gc);
        }

        //Thêm tường
        for (int i = 0; i < BombermanGame.bricks.size(); i++) {
            BombermanGame.bricks.get(i).render(gc);
        }

        //Thêm quái
        for (int i = 0; i < BombermanGame.enemies.size(); i++) {
            BombermanGame.enemies.get(i).render(gc);
        }

        //Thêm nhân vật
        player.render(gc);
    }
}