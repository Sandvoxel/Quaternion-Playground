package processing.sketches;

import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;
import java.util.Arrays;

public class Cube {
    private final float mass = 50f;

    private final PVector pos;
    private final PVector momentum = new PVector();

    private PVector angularVelocity = new PVector(MathUtil.degToRad(0), MathUtil.degToRad(0), MathUtil.degToRad(0));
    private final AngularMomentum angularMomentum;

    private final PVector testForce = new PVector(2,0,0);

    PVector[] points = new PVector[9];

    PApplet applet = Main.sketch;
    private Quaternion rotation = new Quaternion().fromEuler(0,0,45);


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


        angularMomentum = new AngularMomentum(Arrays.copyOfRange(points,0,9), mass);
    }

    public void update() {

        angularVelocity = angularMomentum.getAngularVelocity(rotation);

        rotation = rotation.applyRotation(angularVelocity);


        if(applet.keyPressed){
            System.out.println(applyForce(points[8], testForce));
        }

        pos.add(momentum.div(mass));


    }

    public PVector applyForce(PVector point, PVector force){
        float mag = force.mag();
        force.normalize();

        return point.cross(force).mult(mag);
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
        PVector axis = angularVelocity.copy();
        axis.mult(10000);
        axis.add(pos);

        applet.stroke(Color.red.getRGB());
        applet.strokeWeight(1);
        applet.line(pos.x, pos.y, axis.x, axis.y);

        applet.strokeWeight(10);
        applet.point(axis.x, axis.y);


    }




}
