package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import uet.oop.bomberman.Audio.Audio;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;
import javafx.scene.input.KeyEvent;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import uet.oop.bomberman.Map.Map;

public class BombermanGame extends Application {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    public static GraphicsContext gc;
    private Canvas canvas;
    private Canvas canvas1;
    public static GraphicsContext gc1;


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
    public static final String TITLE = "Bomberman ";

    public static int index = 0;
    public static int resetGame = 0;

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

    public void winGame() {
        Image winImage;
        try {
            winImage = new javafx.scene.image.Image(new FileInputStream(path + "image/winGame.jpg"));
            gc1.drawImage(winImage, 0, 0, Sprite.SCALED_SIZE * 31, Sprite.SCALED_SIZE * 13);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void gameOver() {
        Image gameOver;
        try {
            gameOver = new javafx.scene.image.Image(new FileInputStream(path + "image/gameOver.png"));
            gc.drawImage(gameOver, 0, 0, Sprite.SCALED_SIZE * 31, Sprite.SCALED_SIZE * 13);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) {
        // Tạo màn hình chơi
        canvas = new Canvas(Sprite.SCALED_SIZE * 31, Sprite.SCALED_SIZE * 13);
        gc = canvas.getGraphicsContext2D();
        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);


        //Tạo level
        canvas1 = new Canvas(Sprite.SCALED_SIZE * 31, Sprite.SCALED_SIZE * 13);
        gc1 = canvas1.getGraphicsContext2D();
        Group root1 = new Group();
        root1.getChildren().add(canvas1);
        Image image = new Image(String.valueOf(new File(path + "image/startGame.png")));
        Image level1 = new Image(String.valueOf(new File(path + "image/level1.png")));
        Image level2 = new Image(String.valueOf(new File(path + "image/level2.png")));
        Image level3 = new Image(String.valueOf(new File(path + "image/level3.png")));
        gc1.drawImage(image, 0, 0, Sprite.SCALED_SIZE * 31, Sprite.SCALED_SIZE * 13);

        Scene scene1 = new Scene(root1);
        // Tao màn hình chính
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
                case SHIFT: {
                    winGame = true;
                    break;
                }
                case BACK_SPACE: {
                    resetGame = 1;
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

        //Màn hình level
        if (index == 0) {
            stage.setScene(scene1);
        } else {
            stage.setScene(scene);
        }
        scene1.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case BACK_SPACE: {
                    resetGame = 1;
                    System.out.println(1);
                }
                case ENTER: {
                    index ++;
                    if (index == 1) {
                        if (curMap == 0) {
                            gc1.drawImage(level1, 0, 0, Sprite.SCALED_SIZE * 31, Sprite.SCALED_SIZE * 13);
                        }
                        if (curMap == 1) {
                            Bomber.k = 0;
                            Map.makeMap(map[curMap]);
                            stage.setScene(scene);
                        }
                        if (curMap == 2) {
                            Bomber.k = 0;
                            Map.makeMap(map[curMap]);
                            stage.setScene(scene);
                        }
                    } else if (index == 2) {
                        stage.setScene(scene);
                    }
                }
            }
        });
        stage.show();
        stage.setTitle(TITLE);
        Map.makeMap(map[curMap]);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (!player.notReallyDie()) {
                    gameOver();
                    return;
                }
                if (resetGame == 1) {
                    curMap = 0;
                    resetGame = 0;
                    winGame = false;
                    Map.makeMap(map[curMap]);
                    gc1.drawImage(level1, 0, 0, Sprite.SCALED_SIZE * 31, Sprite.SCALED_SIZE * 13);
                    stage.setScene(scene1);
//                    curMap--;
//                    stage.setScene(scene);
                }
                if (winGame) {
                    System.out.println(curMap);
                    if (curMap == 2) {
                        winGame();
                        stage.setScene(scene1);
//                            curMap = 0;
//                            stop();
//                            return;
                    }
                    winGame = false;
                    index = 0;
                    if (curMap == 0) {
                        curMap = 1;
                        gc1.drawImage(level2, 0, 0, Sprite.SCALED_SIZE * 31, Sprite.SCALED_SIZE * 13);
                        stage.setScene(scene1);
                    } else if (curMap == 1) {
                        gc1.drawImage(level3, 0, 0, Sprite.SCALED_SIZE * 31, Sprite.SCALED_SIZE * 13);
                        stage.setScene(scene1);
                        curMap = 2;
                    }
                }
                update(l);
                render();
                sleep(10);
            }
        };
        Image logo = new Image(String.valueOf(new File(path + "image/logo.png")));
        stage.getIcons().add(logo);
        MenuBar menuBar = new MenuBar();

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