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

    public static final int WIDTH = 20;
    public static final int HEIGHT = 15;

    private GraphicsContext gc;
    private Canvas canvas;

    //    private List<Entity> entities = new ArrayList<>();
//    private List<Entity> stillObjects = new ArrayList<>();
    public static List<Wall> stillObjects = new ArrayList<>();
    public static List<Brick> bricks = new ArrayList<>();
//    public static List<Enemy> enemies = new ArrayList<>();
//    public static List<Item> items = new ArrayList<>();
//    public static List<Wall> stillObjects = new ArrayList<>();
    public static List<Flame> flames = new ArrayList<>();
    public static List<Bomb> bombs = new ArrayList<>();

    static String path = System.getProperty("user.dir") + "/res/";
    static Entity background = new Grass(0, 0, Sprite.grass.getFxImage());
    public static Bomber player;
    public static String[] map = {"Level1.txt", "Level2.txt", "Level3.txt"};

    //    char[][] mapMatrix;
//    int numberLevel = 0;
//    int numberRow = 0;
//    int numberColumn = 0;
    public static int bomberDirection = -1;
    public static boolean dropBomb = false;
    public static boolean preDropBomb = false;
    public static int curIdBomb = -1;

    public static int curMap = 0;
    public static boolean winGame = false;


    public static void main(String[] args) {
        loadAll();
        Application.launch(BombermanGame.class);
    }

    static void loadAll() {
        Brick.load();
//        Balloon.load();
//        Bomb.load();
        Bomber.load();
//        Flame.load();
//        Oneal.load();
//        Kondoria.load();
//        Minvo.load();
    }

//    private void createMapFromFile() {
////        String filePath = "C:\\Users\\Huyen\\Desktop\\Hai\\UET\\hoc\\OOP\\bomberman-starter-starter-2\\res\\levels\\level1.txt";
//        String filePath = "res/levels/Level1.txt";
//        try {
//            File file = new File(filePath);
//            FileReader fr = new FileReader(file);
//            BufferedReader br = new BufferedReader(fr);
////            StringBuffer sb = new StringBuffer();
//            String line;
//
//            if ((line = br.readLine()) != null) {
//                String[] tokens = line.split("\\s");
//                numberLevel = Integer.parseInt(tokens[0]);
//                numberRow = Integer.parseInt(tokens[1]);
//                numberColumn = Integer.parseInt(tokens[2]);
//            }
//            if (numberLevel < 1 || (numberRow < 1 && numberColumn < 1)) {
//                return;
//            }
//
//            mapMatrix = new char[numberRow][numberColumn];
//            int countRow = -1;
//            while ((line = br.readLine()) != null && line.startsWith("#")) {
//                countRow = countRow + 1;
//                for (int i = 0; i < line.length(); i++) {
//                    mapMatrix[countRow][i] = line.charAt(i);
//                }
//            }
//            for (int i = 0; i < numberRow; i++) {
//                for (int j = 0; j < numberColumn; j++) {
//                    System.out.print(mapMatrix[i][j]);
//                }
//                System.out.print("\n");
//            }
//
//            fr.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void makeMap(String fileName) {
        try {
            createMap(fileName);
            if (fileName.compareTo("Level1.txt") == 0) {
                Bomber.resetBombLimit();
                Bomber.resetSpeed();
//                Bomb.resetLen();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
//                    bomberman.moveRight();
                    bomberDirection = 1;
                    break;
                }
                case LEFT: {
//                    bomberman.moveLeft();
                    bomberDirection = 3;
                    break;
                }
                case UP: {
//                    bomberman.moveUp();
                    bomberDirection = 0;
                    break;
                }
                case DOWN: {
//                    bomberman.moveDown();
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
        makeMap(map[0]);

//        createMap();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (!player.notReallyDie()) {
                    curMap = 0;
                    sleep(1500);
                    makeMap(map[0]);
                } else {
                    if (winGame) {
                        curMap = (curMap + 1) % 3;
                        makeMap(map[curMap]);
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

    public void clearAll() {
        bricks.clear();
//        enemies.clear();
//        items.clear();
        stillObjects.clear();
//        flames.clear();
//        bombs.clear();
        bomberDirection = -1;
        dropBomb = false;
        preDropBomb = false;
//        curIdBomb = -1;
    }

//    public void createMap() {
////        Scanner objReader = new Scanner(new File(path + "levels/" +fileName));
////        numberLevel = objReader.nextInt();
////        numberRow = objReader.nextInt();
////        numberColumn = objReader.nextInt();
////        objReader.nextLine();
////        for (int i = 0; i < numberRow; i++) {
//////            String line = objReader.nextLine();
////            for (int j = 0; j < numberColumn; i++) {
////
////            }
////        }
//        createMapFromFile();
//        for (int i = 0; i < numberRow; i++) {
//            for (int j = 0; j < numberColumn; j++) {
//                if (mapMatrix[i][j] == '#') {
//                    Wall wall = new Wall(j, i, Sprite.wall.getFxImage());
//                    stillObjects.add(wall);
//                } else {
//                    Grass grass = new Grass(j, i, Sprite.grass.getFxImage());
//                    grass.render(gc);
//                    entities.add(grass);
//
//                    switch (mapMatrix[i][j]) {
//                        case '*':
//                            Brick brick = new Brick(j, i, Sprite.brick.getFxImage());
//                            bricks.add(brick);
//                            break;
//                        case 'p':
//                            player = new Bomber(j, i, Sprite.player_right.getFxImage());
//                            entities.add(player);
//                            break;
//                        case '1':
//                            break;
//                        case '2':
//                            break;
//                        case 'x':
//                            break;
//                    }
//                }
////                Entity object1 = new Grass(j, i, Sprite.grass.getFxImage());
////                object1.render(gc);
////                stillObjects.add(object1);
////                switch (mapMatrix[i][j]) {
////                    case '#': {
////                        Entity object = new Wall(j, i, Sprite.wall.getFxImage());
//////                        stillObjects.add(object);
////                        break;
////                    }
////                    case 'p': {
//////                        Entity object = new Bomber(j, i, Sprite.player_right.getFxImage());
////                        bomberman = new Bomber(j, i, Sprite.player_right.getFxImage());
//////                        stillObjects.add(bomberman);
////                        break;
////                    }
////                    case '*': {
////                        Entity object = new Bomber(j, i, Sprite.brick.getFxImage());
//////                        stillObjects.add(object);
////                        break;
////                    }
////                    case '1': {
////                        Entity object = new Bomber(j, i, Sprite.balloom_left1.getFxImage());
//////                        stillObjects.add(object);
////                        break;
////                    }
////                    case '2': {
////                        Entity object = new Bomber(j, i, Sprite.oneal_left1.getFxImage());
//////                        stillObjects.add(object);
////                        break;
////                    }
////                    case 'x': {
////                        Entity object = new Bomber(j, i, Sprite.portal.getFxImage());
//////                        stillObjects.add(object);
////                        break;
////                    }
////                    case 'f': {
////                        Entity object = new Bomber(j, i, Sprite.powerup_flames.getFxImage());
//////                        stillObjects.add(object);
////                        break;
////                    }
////                }
//            }
//        }
//        stillObjects.forEach(g -> g.render(gc));
//    }

    public void createMap(String fileName) throws IOException {
        clearAll();
        Scanner objReader = new Scanner(new File(path + "levels/" +fileName));
        int stage = objReader.nextInt();
        int m = objReader.nextInt();
        int n = objReader.nextInt();
        objReader.nextLine();
        for (int i = 0; i < m; i++) {
            String S = objReader.nextLine();
            for (int j = 0; j < n; j++) {
//                Entity object;
                char c = S.charAt(j);
                if (Character.compare(c, '#') == 0) {
                    stillObjects.add(new Wall(j, i, Sprite.wall.getFxImage()));
                } else {
                    Grass newGrass = new Grass(j, i, Sprite.grass.getFxImage());
                    newGrass.render(gc);

                    switch (c) {
                        case ('p'):
                            player = new Bomber(j, i, Sprite.player_right.getFxImage());
                            break;
//                        case ('1'):
//                            enemies.add(new Balloon(j, i, Sprite.balloom_left1.getFxImage()));
//                            break;
//                        case ('2'):
//                            enemies.add(new Oneal(j, i, Sprite.oneal_left1.getFxImage()));
//                            break;
//                        case ('3'):
//                            enemies.add(new Minvo(j, i, Sprite.minvo_left1.getFxImage()));
//                            break;
//                        case ('4'):
//                            enemies.add(new Kondoria(j, i, Sprite.kondoria_left1.getFxImage()));
//                            break;
                        case ('*'):
                            bricks.add(new Brick(j, i, Sprite.brick.getFxImage()));
                            break;
//                        case ('x'):
//                            items.add(new Item(j, i, Sprite.portal.getFxImage(), 4));
//                            Brick X = new Brick(j, i, Sprite.brick.getFxImage());
//                            X.setContainItem(items.size() - 1);
//                            bricks.add(X);
//                            break;
//                        case ('s'):
//                            items.add(new Item(j, i, Sprite.powerup_speed.getFxImage(), 3));
//                            Brick Y = new Brick(j, i, Sprite.brick.getFxImage());
//                            Y.setContainItem(items.size() - 1);
//                            bricks.add(Y);
//                            break;
//                        case ('f'):
//                            items.add(new Item(j, i, Sprite.powerup_flames.getFxImage(), 2));
//                            Brick Z = new Brick(j, i, Sprite.brick.getFxImage());
//                            Z.setContainItem(items.size() - 1);
//                            bricks.add(Z);
//                            break;
//                        case ('b'):
//                            items.add(new Item(j, i, Sprite.powerup_bombs.getFxImage(), 1));
//                            Brick T = new Brick(j, i, Sprite.brick.getFxImage());
//                            T.setContainItem(items.size() - 1);
//                            bricks.add(T);
//                            break;
                    }
                }
            }
        }
        stillObjects.forEach(g -> g.render(gc));
    }

    public void setBackground(Entity e) {
        background.setX(e.getX());
        background.setY(e.getY());
        background.render(gc);
    }

    public void update(long l) {
//        for (int i = 0; i < bombs.size(); ++i) {
//            Bomb b = bombs.get(i);
//            setBackground(b);
//            curIdBomb = i;
//            if (b.isKilledByOtherBomb()) {
//                b.setDeath(true);
//                b.addFlame(l);
//            } else {
//                b.update(l);
//            }
//        }

//        for (int i = bombs.size() - 1; i >= 0; --i) {
//            Bomb b = bombs.get(i);
//            if (b.isDeath()) {
//                playSound(clipBombExploydes);
//                bombs.remove(i);
//            }
//        }
//
//        for (int i = flames.size() - 1; i >= 0; --i) {
//            Flame f = flames.get(i);
//            setBackground(f);
//            f.update(l);
//            if (f.isDeath()) {
//                flames.remove(i);
//            }
//        }

        for (int i = bricks.size() - 1; i >=0 ; --i) {
            Brick e = bricks.get(i);
            setBackground(e);
            e.update(l);
            if (e.isDeath() && e.getCurState() == 3) {
                bricks.remove(i);
                if (e.getContainItem() != -1) {
//                    items.get(e.getContainItem()).setOpen(true);
                }
            }
        }

//        boolean calcDis = false;
//        for (int i = enemies.size() - 1; i >= 0; --i) {
//            Enemy e = enemies.get(i);
//            if (e instanceof Oneal && calcDis == false) {
//                calcDis = true;
//                ((Oneal) e).minDis();
//            }
//            setBackground(e);
//            e.update(l);
//            if (e.isDeath() && e.getCurState() == 4) {
//                enemies.remove(i);
//                playSound(clipEnemyDead);
//            }
//        }

        setBackground(player);
        player.update(l);
        if (player.isReborn()) {
            player.letsReborn();
            player.setReborn(false);
        }
    }

//    public void render() {
//        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
//        stillObjects.forEach(g -> g.render(gc));
//        entities.forEach(g -> g.render(gc));
//    }

    public void render() {
//        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
//        stillObjects.forEach(g -> g.render(gc));
//        entities.forEach(g -> g.render(gc));
//        for (Item i : items) {
//            if (!i.isOpen()) {
//                continue;
//            }
//            if (i.isDeath()) {
//                setBackground(i);
//            } else {
//                i.render(gc);
//            }
//        }
//
//        for (Bomb b : bombs) {
//            b.render(gc);
//        }
//
//        for (Flame f : flames) {
//            f.render(gc);
//        }

        for (Brick e : bricks) {
            e.render(gc);
        }
//        for (Enemy e : enemies) {
//            e.render(gc);
//        }
//
        player.render(gc);
    }
}