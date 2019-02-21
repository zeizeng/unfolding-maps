package module2;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.Date;

/**
 * Description
 *
 * @author weizengflyinsky@gmail.com
 * @version 1.0
 * @date 2019/2/2116:41
 */
public class MyPApplet extends PApplet {

    private String URL = "E:\\ideaproject\\unfolding-maps\\resoures\\2013062320262198.jpg";
    private PImage backgroundImg;

    public void setup() {
        size(200, 200);
        backgroundImg = loadImage(URL);
        backgroundImg.resize(width, height);
        image(backgroundImg, 0, 0);
    }

    public void draw() {
        ellipse(width / 4, height / 5, width / 5, height / 5);
        int[] color = sunColorSec(second());
        fill(color[0], color[1], color[2]);

    }

    public int[] sunColorSec(float seconds) {
        int[] rgs = new int[3];
        float diffFrom30 = Math.abs(30 - seconds);

        float ratio = diffFrom30 / 30;

        rgs[0] = (int) (255 * ratio);
        rgs[1] = (int) (255 * ratio);
        rgs[2] = 0;
        return rgs;
    }

//    public static void main(String[] args) {
//        MyPApplet myPApplet = new MyPApplet();
//        int i = 0;
//        while (i < 10000) {
//            myPApplet.draw();
//            i++;
//        }
//    }
}
