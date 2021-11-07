package com.sandvoxel.quatplayground;

import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;
import java.util.Arrays;

public class Cube {
    private final float mass = 50f;

    private final PVector pos;
    private final PVector centerOfMass = new PVector();
    private final PVector momentum = new PVector();

    private Quaternion rotation = new Quaternion();
    private PVector angularVelocity;
    private final AngularMomentum angularMomentum;

    PVector[] points = new PVector[9];

    PApplet applet = Main.sketch;


    public Cube(PVector pos) {
        this.pos = pos;

        points[0] = new PVector(2, 3, 0.5f);
        points[1] = new PVector(-2, 3, 0.5f);
        points[2] = new PVector(-2, -3, 0.5f);
        points[3] = new PVector(2, -3, 0.5f);

        points[4] = new PVector(2, 3, -0.5f);
        points[5] = new PVector(-2, 3, -0.5f);
        points[6] = new PVector(-2, -3, -0.5f);
        points[7] = new PVector(2, -3, -0.5f);

        points[8] = new PVector(0, -5, 0);


        angularMomentum = new AngularMomentum(Arrays.copyOfRange(points,0, 8), mass);
        angularVelocity = angularMomentum.getAngularVelocity(rotation);

//        Arrays.stream(points).forEach(centerOfMass::add);
//
//        centerOfMass.div(points.length);
    }

    public void update() {

        angularVelocity = angularMomentum.getAngularVelocity(rotation);

        if(applet.keyPressed && applet.keyCode == 38){
            PVector vector = new PVector(2, 3, 0).copy().cross(new PVector(-1,0,0));

            vector = MathUtil.MultiMat(vector,rotation.toMatrix());
            angularMomentum.applyForce(vector);

        }
        if(applet.keyPressed && applet.keyCode == 40){
            PVector vector = points[0].copy().cross(new PVector(-1,0,0));
            vector = MathUtil.MultiMat(vector,rotation.toMatrix());
            angularMomentum.applyForce(vector);
        }
        if(applet.keyPressed && applet.keyCode == 39){
            angularMomentum.zero();
        }


        rotation = rotation.applyRotation(angularVelocity);


        pos.add(momentum.div(mass));

    }

    public void draw() {

        float[][] mat = rotation.toMatrix();

        int i = 0;
        for (PVector point : points) {
            PVector p = MathUtil.MultiMat(point, mat);
            PVector com = MathUtil.MultiMat(centerOfMass,mat);
            p.sub(com);

            float color = p.z;
            p.mult(60);
            com.mult(60);
            p.add(pos);

            applet.stroke((color + 1) * 255 / 2, (color + 1) * 255 / 2, 255);
            applet.strokeWeight(1);

            if (i == 8) {
                applet.line(pos.x - com.x, pos.y - com.y, p.x, p.y);
            }

            applet.strokeWeight(10);
            if(i == 0)
                applet.stroke(Color.MAGENTA.getRGB());
            applet.point(p.x, p.y);

            i++;
        }
        PVector axis = angularVelocity.copy();
        axis.mult(200);
        axis.add(pos);

        applet.stroke(Color.red.getRGB());
        applet.strokeWeight(1);
        applet.line(pos.x, pos.y, axis.x, axis.y);

        applet.strokeWeight(10);
        applet.point(axis.x, axis.y);


    }




}
