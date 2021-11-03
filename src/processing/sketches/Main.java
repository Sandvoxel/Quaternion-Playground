package processing.sketches;

import processing.core.PApplet;
import processing.core.PVector;

public class Main extends PApplet {
    public static PApplet sketch;
    Quaternion rot = new Quaternion().fromEuler(1, 1, 1);

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
    }

    public void draw() {
        clear();


        cube.draw();
        cube.update();

    }
}
