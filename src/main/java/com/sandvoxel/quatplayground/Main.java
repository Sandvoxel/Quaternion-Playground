package com.sandvoxel.quatplayground;

import processing.core.PApplet;
import processing.core.PVector;
import processing.event.KeyEvent;

public class Main extends PApplet {
    public static PApplet sketch;
    public static boolean keyz[] = new boolean [5];
    Quaternion test = new Quaternion().fromEuler(90, 100, 90);

    Cube cube;


    PVector[] points = new PVector[4];

    public static void main(String... args) {
        PApplet.main("com.sandvoxel.quatplayground.Main");
    }

    public void settings() {
        sketch = this;
        size(1920, 1080);
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

    @Override
    public void keyPressed(KeyEvent event) {
        if (key == 'w')  keyz[0] = true;
        if (key == 's')  keyz[1] = true;
        if (key == 'a')  keyz[2] = true;
        if (key == 'd')  keyz[3] = true;
        if (key == ' ')  keyz[4] = true;

    }

    @Override
    public void keyReleased(KeyEvent event) {
        if (key == 'w')  keyz[0] = false;
        if (key == 's')  keyz[1] = false;
        if (key == 'a')  keyz[2] = false;
        if (key == 'd')  keyz[3] = false;
        if (key == ' ')  keyz[4] = false;

    }


}
