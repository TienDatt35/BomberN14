package uet.oop.bomberman;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;
import javafx.scene.input.KeyEvent;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BombermanGame extends Application {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    private GraphicsContext gc;
    private Canvas canvas;
    public static List<Brick> bricks = new ArrayList<>();
    public static List<Enemy> enemies = new ArrayList<>();
    public static List<Wall> stillObjects = new ArrayList<>();
    public static List<Bomb> bombs = new ArrayList<>();
    static String path = System.getProperty("user.dir") + "/res/";
    static Entity background = new Grass(0, 0, Sprite.grass.getFxImage());
    public static int bomberDirection = -1;
    public static Bomber player;
    public static boolean dropBomb = false;
    public static boolean predropBomb = false;
    public static int curIdBomb = -1;
    public static String[] map = {"Level1.txt", "Level2.txt", "Level3.txt"};
    public static int curMap = 0;
    public static boolean winGame = false;
    public static Clip clipBombExploydes;
    public static Clip clipBombSet;
    public static Clip clipEnemyDead;
    public static Clip clipExitOpen;
    public static Clip clipitemGet;

    @Override
    public void start(Stage stage) {
    }

}
