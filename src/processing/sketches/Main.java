package processing.sketches;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.Arrays;

public class Main extends PApplet {
    public static PApplet sketch;
    Quaternion test = new Quaternion().fromEuler(90, 100, 90);

    Cube cube;


    PVector[] points = new PVector[4];

    public static void main(String... args) {
        PApplet.main("processing.sketches.Main");
    }

    public void settings() {
        sketch = this;
        size(1000, 1000);
    }

    public void setup() {
        background(0);
        cube = new Cube(new PVector(width / 2f, height / 2f));

/*        print(MathUtil.invert(test.toMatrix()));
        System.out.println();
        print(test.toMatrix());
        System.out.println();
        print(MathUtil.multiplyMatrices(test.toMatrix(), MathUtil.invert(test.toMatrix())));
*/

    }

    public void draw() {
        clear();


        cube.draw();
        cube.update();

    }
    public static void print(float[][] grid) {
        for (float[] floats : grid) {
            System.out.print("{ ");
            for (float aFloat : floats) {
                System.out.print(aFloat + ", ");
            }
            System.out.print("},");

            System.out.println();
        }
    }
}
