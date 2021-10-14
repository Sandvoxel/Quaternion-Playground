package processing.sketches;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.Arrays;
import java.util.Comparator;

public class Main extends PApplet {
    public static PApplet sketch;

    Quaternion quaternion = new Quaternion().fromEuler(45,0,0);

    Quaternion rot = new Quaternion().fromEuler(1,1,0);



    PVector[] points = new PVector[12];

    public void settings() {
        sketch = this;
        size(600, 600);
    }

    public void setup() {
        background(0);

        quaternion.normalize();

        points[0] = new PVector(1,1,1);
        points[1] = new PVector(-1,1,1);
        points[2] = new PVector(-1,-1,1);
        points[3] = new PVector(1,-1,1);

        points[4] = new PVector(1,1,-1);
        points[5] = new PVector(-1,1,-1);
        points[6] = new PVector(-1,-1,-1);
        points[7] = new PVector(1,-1,-1);

        points[8] = new PVector();
        points[9] = new PVector(4,0,0);
        points[10] = new PVector(0,4,0);
        points[11] = new PVector(0,0,4);
    }

    PVector center;
    PVector lastMousePos = new PVector();

    public void draw() {
        clear();
        PVector pos = new PVector(width/2f, height/2f/* + sin(MathUtil.degToRad(frameCount % 360))*100*/);
        float[][] mat = quaternion.toMatrix();

        int i = 0;
        for (PVector point: points) {
            PVector p = MathUtil.MultiMat(point, mat);

            float color = p.z;
            p.mult(60);
            p.add(pos);

            stroke((color + 1) * 255/2, (color + 1) * 255/2,255);
            strokeWeight(1);

            if(i == 8)
                center = p;
            if(i == 9) {
                stroke(255, 0, 0);
                line(center.x, center.y, p.x, p.y);
            }
            if(i == 10) {
                stroke(0, 255, 0);
                line(center.x, center.y, p.x, p.y);
            }
            if(i == 11) {
                stroke(0, 0, 255);
                line(center.x, center.y, p.x, p.y);
            }

            strokeWeight(10);


            point(p.x, p.y);

            i++;
        }

        if(mousePressed){
            Quaternion mouseRot = new Quaternion().fromEuler(lastMousePos.x - mouseX, mouseY - lastMousePos.y, 0);
            quaternion = mouseRot.multi(quaternion);

        }else {
            quaternion  = rot.multi(quaternion);
        }
        lastMousePos.x = mouseX;
        lastMousePos.y = mouseY;

        //quaternion  = rot2.multi(quaternion);



    }

    public static void main(String... args) {
        PApplet.main("processing.sketches.Main");
    }
}
