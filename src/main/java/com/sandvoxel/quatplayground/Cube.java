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

        points[0] = new PVector(120, 180, 30);
        points[1] = new PVector(-120, 180, 30);
        points[2] = new PVector(-120, -180, 30);
        points[3] = new PVector(120, -180, 30);

        points[4] = new PVector(120, 180, -30);
        points[5] = new PVector(-120, 180, -30);
        points[6] = new PVector(-120, -180, -30);
        points[7] = new PVector(120, -180, -30);

        points[8] = new PVector(0, -300, 0);


        angularMomentum = new AngularMomentum(Arrays.copyOfRange(points,0, 8), mass);
        angularVelocity = angularMomentum.getAngularVelocity(rotation);

//        Arrays.stream(points).forEach(centerOfMass::add);
//
//        centerOfMass.div(points.length);
    }

    float coolForce = 0;

    public void update() {

        angularVelocity = angularMomentum.getAngularVelocity(rotation);

        if(applet.keyPressed && applet.keyCode == 38){
            PVector point = new PVector(0, 180, 0);
            PVector force = new PVector((float) (Math.cos(coolForce))* 10,Math.abs((float) (Math.sin(coolForce))) * 10,0);
            coolForce += 0.05f;

            point = MathUtil.MultiMat(point, rotation.toMatrix());
            force = MathUtil.MultiMat(force, rotation.toMatrix());

            PVector temp = force.copy().mult(20).add(pos).add(point);
            PVector temp2 = point.copy().add(pos);
            applet.line(temp2.x,temp2.y,temp.x,temp.y);


            PVector vector = point.copy().cross(force);
            angularMomentum.applyForce(vector);

/*            float scale = -Math.abs();

            System.out.println(scale);*/

            momentum.add(force.mult(-1));
        }
        if(applet.keyPressed && applet.keyCode == 40){
            PVector vector = new PVector(120, 0, 0).copy().cross(new PVector(0,0,10));
            vector = MathUtil.MultiMat(vector,rotation.toMatrix());
            angularMomentum.applyForce(vector);
        }
        if(applet.keyPressed && applet.keyCode == 39){
            angularMomentum.zero();
            momentum.mult(0);
        }
        if(applet.keyPressed && applet.keyCode == 37){
            angularMomentum.zero();
            pos.set(new PVector( applet.width / 2f,  applet.height / 2f));
            momentum.mult(0);
        }


        rotation = rotation.applyRotation(angularVelocity);


        pos.add(momentum.copy().div(mass));


        if(pos.x > applet.width){
            pos.x = 0;
        }
    }

    public void draw() {

        float[][] mat = rotation.toMatrix();

        int i = 0;
        for (PVector point : points) {
            PVector p = MathUtil.MultiMat(point, mat);
            PVector com = MathUtil.MultiMat(centerOfMass,mat);
            p.sub(com);

            float color = p.z;
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
