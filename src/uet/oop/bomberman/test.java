package uet.oop.bomberman;

import java.util.Arrays;

import static java.lang.Math.random;

public class test {
    public static void main(String[] args) {
        int h = 13;
        int w = 31;
        char map[][] = new char[h][w];
        for (int i = 0;  i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (i == 0 || j == 0 || i == h-1 || j == w-1 || i % 2 == 0 && j % 2 == 0) {
                    map[i][j] = '#';
                }
                else {
                    map[i][j] = ' ';
                }
            }
        }
        map[1][1] = 'p';

        int items = 4;
        int bricks = 30;
        int enemy = 6;
        int[] etity = new int[items+bricks+enemy];
        for(int i = 0; i < etity.length; i++)
        {
            while (true){
                int tmp = (int) (Math.random() * (h*w));
                int tmpi = tmp / w;
                int tmpj = tmp / h;
                if(map[tmpi][tmpj] == ' ') {
                    etity[i] = tmp;
                    break;
                }
            }
        }


        int tmp = 0;
        while (enemy > 0) {
            int x = (int) (Math.random() * 4) + 1;
            int tmpi = etity[tmp] / w;
            int tmpj = etity[tmp] / h;
            if (x == 1) {
                map[tmpi][tmpj] = '1';
            }
            else if(x == 2) {
                map[tmpi][tmpj] = '2';
            } else if(x == 3) {
                map[tmpi][tmpj] = '3';
            } else {
                map[tmpi][tmpj] = '4';
            }
            tmp++;
            enemy--;
        }

        while (items > 0) {
            int x = (int) (Math.random() * 3) + 1;
            int tmpi = etity[tmp] / w;
            int tmpj = etity[tmp] / h;
            if (x == 1) {
                map[tmpi][tmpj] = 's';
            }
            else if(x == 2) {
                map[tmpi][tmpj] = 'x';
            } else {
                map[tmpi][tmpj] = 'f';
            }
            tmp++;
            items--;
        }
        while (bricks > 0) {
            int tmpi = etity[tmp] / w;
            int tmpj = etity[tmp] / h;

            map[tmpi][tmpj] = '*';

            tmp++;
            bricks--;
        }




//        int brick = 0;
//        for (int i = 0;  i < h; i++) {
//            for (int j = 0; j < w; j++) {
//                if (map[i][j] == ' ') {
//                    while (brick < 30) {
//                        int x = (int) (Math.random() * 2);
//                        if(x == 1) {
//                            map[i][j] = '*';
//                            brick++;
//                        }
//                        break;
//                    }
//                }
//            }
//        }
//        int tmp = 0;
//        int enemy = 0;
//        int[] enemies = new int[1000];
//        for(int i = 0; i < 1000; i++)
//        {
//            enemies[i] = (int) (Math.random() * (h*w));
//        }
//        Arrays.stream(enemies).sorted();
//        for (int i = 0;  i < h; i++) {
//            for (int j = 0; j < w; j++) {
//                if (map[i][j] == ' ' && ((i+1)*(j+1) == enemies[++tmp]) && enemy < 6) {
//                    int x = (enemies[tmp] % 4) + 1;
//                    if(x == 1) {
//                        map[i][j] =  '1';
//                    }else if(x == 2) {
//                        map[i][j] =  '2';
//                    }else if(x == 3) {
//                        map[i][j] =  '3';
//                    }else {
//                        map[i][j] =  '4';
//                    }
//                    enemy++;
//                }
//            }
//        }


        for (int i = 0;  i < h; i++) {
            for (int j = 0; j < w; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }
}
