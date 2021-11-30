package uet.oop.bomberman.Map;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static uet.oop.bomberman.BombermanGame.gc;

public class Map {
    public static void makeMap(String fileName) {
        try {
            createMap(fileName);
            if (fileName.compareTo("Level1.txt") == 0) {
                Bomber.resetBombLimit();
                Bomber.resetSpeed();
                Bomb.resetLen();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createMap(String fileName) throws IOException {
        BombermanGame.clearAll();
        Scanner objReader = new Scanner(new File(BombermanGame.path + "levels/" + fileName));
        int stage = objReader.nextInt();
        int m = objReader.nextInt();
        int n = objReader.nextInt();
        objReader.nextLine();
        for (int i = 0; i < m; i++) {
            String S = objReader.nextLine();
            for (int j = 0; j < n; j++) {
                char c = S.charAt(j);
                if (c == '#') {
                    BombermanGame.stillObjects.add(new Wall(j, i, Sprite.wall.getFxImage()));
                } else {
                    Grass newGrass = new Grass(j, i, Sprite.grass.getFxImage());
                    newGrass.render(gc);

                    switch (c) {
                        case ('p'):
                            BombermanGame.player = new Bomber(j, i, Sprite.player_right.getFxImage());
                            break;
                        case ('1'):
                            BombermanGame.enemies.add(new Balloon(j, i, Sprite.balloom_left1.getFxImage()));
                            break;
                        case ('2'):
                            BombermanGame.enemies.add(new Oneal(j, i, Sprite.oneal_left1.getFxImage()));
                            break;
                        case ('3'):
                            BombermanGame.enemies.add(new Minvo(j, i, Sprite.minvo_left1.getFxImage()));
                            break;
                        case ('4'):
                            BombermanGame.enemies.add(new Kondoria(j, i, Sprite.kondoria_left1.getFxImage()));
                            break;
                        case ('*'):
                            BombermanGame.bricks.add(new Brick(j, i, Sprite.brick.getFxImage()));
                            break;
                        case ('x'):
                            BombermanGame.items.add(new Item(j, i, Sprite.portal.getFxImage(), 4));
                            Brick X = new Brick(j, i, Sprite.brick.getFxImage());
                            X.setContainItem(BombermanGame.items.size() - 1);
                            BombermanGame.bricks.add(X);
                            break;
                        case ('s'):
                            BombermanGame.items.add(new Item(j, i, Sprite.powerup_speed.getFxImage(), 3));
                            Brick Y = new Brick(j, i, Sprite.brick.getFxImage());
                            Y.setContainItem(BombermanGame.items.size() - 1);
                            BombermanGame.bricks.add(Y);
                            break;
                        case ('f'):
                            BombermanGame.items.add(new Item(j, i, Sprite.powerup_flames.getFxImage(), 2));
                            Brick Z = new Brick(j, i, Sprite.brick.getFxImage());
                            Z.setContainItem(BombermanGame.items.size() - 1);
                            BombermanGame.bricks.add(Z);
                            break;
                        case ('b'):
                            BombermanGame.items.add(new Item(j, i, Sprite.powerup_bombs.getFxImage(), 1));
                            Brick T = new Brick(j, i, Sprite.brick.getFxImage());
                            T.setContainItem(BombermanGame.items.size() - 1);
                            BombermanGame.bricks.add(T);
                            break;
                    }
                }
            }
        }
        BombermanGame.stillObjects.forEach(g -> g.render(gc));
    }
}
