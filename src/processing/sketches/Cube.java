package processing.sketches;

import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

public class Cube {
    private final float mass = 1f;

    private final PVector pos;
    private final PVector momentum = new PVector();
    private final PVector angularMomentum = new PVector(MathUtil.degToRad(1), MathUtil.degToRad(1), MathUtil.degToRad(0));

    private final PVector testForce = new PVector(1,0,0);

    PVector[] points = new PVector[9];

    PApplet applet = Main.sketch;
    private Quaternion rotation = new Quaternion(1f, 0, 0, 0f);


    public Cube(PVector pos) {
        this.pos = pos;

        points[0] = new PVector(1, 1, 1);
        points[1] = new PVector(-1, 1, 1);
        points[2] = new PVector(-1, -1, 1);
        points[3] = new PVector(1, -1, 1);

        points[4] = new PVector(1, 1, -1);
        points[5] = new PVector(-1, 1, -1);
        points[6] = new PVector(-1, -1, -1);
        points[7] = new PVector(1, -1, -1);

        points[8] = new PVector(0, 4, 0);
    }

    public void update() {

        rotation = rotation.applyRotation(angularMomentum);

        //System.out.println(applyForce(points[0], testForce));

        pos.add(momentum.div(mass));

    }

    public void draw() {

        float[][] mat = rotation.toMatrix();

        int i = 0;
        for (PVector point : points) {
            PVector p = MathUtil.MultiMat(point, mat);

            float color = p.z;
            p.mult(60);
            p.add(pos);

            applet.stroke((color + 1) * 255 / 2, (color + 1) * 255 / 2, 255);
            applet.strokeWeight(1);

            if (i == 8) {

                applet.line(pos.x, pos.y, p.x, p.y);
            }

            applet.strokeWeight(10);
            applet.point(p.x, p.y);

            i++;
        }
        PVector axis = angularMomentum.copy();
        axis.mult(10000);
        axis.add(pos);

        applet.stroke(Color.red.getRGB());
        applet.strokeWeight(1);
        applet.line(pos.x, pos.y, axis.x, axis.y);

        applet.strokeWeight(10);
        applet.point(axis.x, axis.y);


    }

    public PVector applyForce(PVector point, PVector force){
        return point.cross(force);
    }


}
