package processing.sketches;

import org.checkerframework.checker.units.qual.C;
import processing.core.PApplet;
import processing.core.PVector;
import processing.sketches.physics.Quaternion;

public class Main extends PApplet {
    public static PApplet sketch;

    AxisLines axisLines = new AxisLines();
    Cube cube = new Cube(60);

    PVector force = new PVector();

    PVector[] points = new PVector[4];

    public void settings() {
        sketch = this;
        size(600, 600);
    }

    public void setup() {
        background(0);

        axisLines.setup();
        cube.setup();
    }

    PVector lastMousePos = new PVector();

    public void draw() {
        clear();

        axisLines.draw();
        cube.draw();

        if(mousePressed) {
            Quaternion mouseRot = new Quaternion().fromEuler(lastMousePos.x - mouseX, mouseY - lastMousePos.y, 0);
            //axisLines.rotate(mouseRot);
            cube.rotate(mouseRot);
        }


        lastMousePos.x = mouseX;
        lastMousePos.y = mouseY;

    }

    public static void main(String... args) {
        PApplet.main("processing.sketches.Main");
    }
}
