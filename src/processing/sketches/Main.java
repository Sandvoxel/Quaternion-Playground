package processing.sketches;

import org.checkerframework.checker.units.qual.C;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.Arrays;
import java.util.Comparator;

public class Main extends PApplet {
    public static PApplet sketch;
    Quaternion rot = new Quaternion().fromEuler(1,1,1);

    Cube cube;



    PVector[] points = new PVector[4];

    public void settings() {
        sketch = this;
        size(600, 600);
    }

    public void setup() {
        background(0);
        cube = new Cube( new PVector(width/2f, height/2f));
    }



    public void draw() {
        clear();


        cube.draw();
        cube.update();

    }


    public static void main(String... args) {
        PApplet.main("processing.sketches.Main");
    }
}
