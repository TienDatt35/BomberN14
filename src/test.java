import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import uet.oop.bomberman.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import uet.oop.bomberman.Map.Map;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;
import javafx.scene.input.KeyEvent;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class test extends Application {

    public static void main(String[] args) {
            launch(args);
    }

//    @Override
//    public void start(Stage primaryStage) {
//        primaryStage.setTitle("Title");
//
//        final Circle circ = new Circle(100, 40, 30);
//        final Group root = new Group(circ);
//
//        final Scene scene = new Scene(root, 800, 400, Color.BEIGE);
//
//        final Text text1 = new Text(25, 25, "java2s.com");
//        text1.setFill(Color.CHOCOLATE);
//        text1.setFont(Font.font(java.awt.Font.SERIF, 25));
//        root.getChildren().add(text1);
//
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }

    @Override
    public void start(Stage stage) {
        Canvas canvas;
        GraphicsContext gc;
        Canvas canvas1;
        GraphicsContext gc1;
        canvas = new Canvas(Sprite.SCALED_SIZE * 31, Sprite.SCALED_SIZE * 13);
        gc = canvas.getGraphicsContext2D();
        canvas1 = new Canvas(Sprite.SCALED_SIZE * 31, Sprite.SCALED_SIZE * 13);
        gc1 = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);


        Group root1 = new Group();
        root1.getChildren().add(canvas1);
        Scene scene1 = new Scene(root1);
        // Tao scene
        String path = System.getProperty("user.dir") + "/res/";

        stage.setTitle("Bomberman");
        Image image = new Image(String.valueOf(new File("C:\\Users\\Huyen\\Desktop\\Hai\\UET\\hoc\\OOP\\BomberN14\\res\\image\\startGame.png")));
            gc.drawImage(image, 0, 0, Sprite.SCALED_SIZE * 31, Sprite.SCALED_SIZE * 13);


        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case SHIFT: {
                    System.out.println(1);
                }
                case BACK_SPACE: {
//                    stage.setScene(scene1);
//                    Map.makeMap("Level1.txt");

                }
            }
        });

        stage.setScene(scene);
        stage.show();
//        Map.makeMap(BombermanGame.map[0]);
    }
}
