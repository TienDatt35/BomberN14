package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    
    public static final int WIDTH = 20;
    public static final int HEIGHT = 15;
    
    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    static Entity bomberman;
    char[][] mapMatrix;
    int numberLevel = 0;
    int numberRow = 0;
    int numberColumn = 0;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
//        bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        createMap();
        // Tao Canvas
//        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        canvas = new Canvas(Sprite.SCALED_SIZE * numberColumn, Sprite.SCALED_SIZE * numberRow);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case RIGHT: {
                    bomberman.moveRight();
                    break;
                }
                case LEFT: {
                    bomberman.moveLeft();
                    break;
                }
                case UP: {
                    bomberman.moveUp();
                    break;
                }
                case DOWN: {
                    bomberman.moveDown();
                    break;
                }
                case SPACE: {
                    break;
                }
            }
        });

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();


//        Entity bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);
    }

    public void createMap() {
        createMapFromFile();
        for (int i = 0; i < numberRow; i++) {
            for (int j = 0; j < numberColumn; j++) {
                Entity object1 = new Grass(j, i, Sprite.grass.getFxImage());
                stillObjects.add(object1);
                switch (mapMatrix[i][j]) {
                    case '#': {
                        Entity object = new Wall(j, i, Sprite.wall.getFxImage());
                        stillObjects.add(object);
                        break;
                    }
                    case 'p': {
//                        Entity object = new Bomber(j, i, Sprite.player_right.getFxImage());
                        bomberman = new Bomber(j, i, Sprite.player_right.getFxImage());
                        stillObjects.add(bomberman);
                        break;
                    }
                    case '*': {
                        Entity object = new Bomber(j, i, Sprite.brick.getFxImage());
                        stillObjects.add(object);
                        break;
                    }
                    case '1': {
                        Entity object = new Bomber(j, i, Sprite.balloom_left1.getFxImage());
                        stillObjects.add(object);
                        break;
                    }
                    case '2': {
                        Entity object = new Bomber(j, i, Sprite.oneal_left1.getFxImage());
                        stillObjects.add(object);
                        break;
                    }
                    case 'x': {
                        Entity object = new Bomber(j, i, Sprite.portal.getFxImage());
                        stillObjects.add(object);
                        break;
                    }
                    case 'f': {
                        Entity object = new Bomber(j, i, Sprite.powerup_flames.getFxImage());
                        stillObjects.add(object);
                        break;
                    }
                }
            }
        }

//        for (int i = 0; i < WIDTH; i++) {
//            for (int j = 0; j < HEIGHT; j++) {
//                Entity object;
//                if (j == 0 || j == HEIGHT - 1 || i == 0 || i == WIDTH - 1) {
//                    object = new Wall(i, j, Sprite.wall.getFxImage());
//                }
//                else {
//                    object = new Grass(i, j, Sprite.grass.getFxImage());
//                }
//                stillObjects.add(object);
//            }
//        }
    }

    private void createMapFromFile() {
//        String filePath = "C:\\Users\\Huyen\\Desktop\\Hai\\UET\\hoc\\OOP\\bomberman-starter-starter-2\\res\\levels\\level1.txt";
        String filePath = "res/levels/Level1.txt";
        try {
            File file = new File(filePath);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
//            StringBuffer sb = new StringBuffer();
            String line;

            if ((line = br.readLine()) != null) {
                String[] tokens = line.split("\\s");
                numberLevel = Integer.parseInt(tokens[0]);
                numberRow = Integer.parseInt(tokens[1]);
                numberColumn = Integer.parseInt(tokens[2]);
            }
            if (numberLevel < 1 || (numberRow < 1 && numberColumn < 1)) {
                return;
            }

            mapMatrix = new char[numberRow][numberColumn];
            int countRow = -1;
            while ((line = br.readLine()) != null && line.startsWith("#")) {
                countRow = countRow + 1;
                for (int i = 0; i < line.length(); i++) {
                    mapMatrix[countRow][i] = line.charAt(i);
                }
            }
            for (int i = 0; i < numberRow; i++) {
                for (int j = 0; j < numberColumn; j++) {
                    System.out.print(mapMatrix[i][j]);
                }
                System.out.print("\n");
            }

            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void update() {
        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}
