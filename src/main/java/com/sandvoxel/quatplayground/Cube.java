package com.sandvoxel.quatplayground;

import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Cube {
    private final float mass = 50f;

    private final PVector pos;
    private final PVector centerOfMass = new PVector();
    private final PVector momentum = new PVector();

    private Quaternion rotation = new Quaternion();
    private PVector angularVelocity;
    private final AngularMomentum angularMomentum;

    PApplet applet = Main.sketch;

    List<PVector> points = new ArrayList<>();
    PVector topLine;


    public Cube(PVector pos) {
        this.pos = pos;
        int numberOfRings = 5;
        int numberOfPoints = 20;
        float radius = 100;
        float height = 360;

        float heightIncrement = height / numberOfRings;
        heightIncrement += heightIncrement / (numberOfRings - 1);

        for (int i = 0; i < numberOfRings; i++) {
            for (int j = 0; j < numberOfPoints; j++) {
                float increment = (float) ((Math.PI * 2) / numberOfPoints);
                points.add(new PVector((float)Math.cos(increment * j) * radius, (i * heightIncrement) - (height / 2), (float)Math.sin(increment * j) * radius));
            }
        }

       /* points.add(new PVector(120, 180, 30));
        points.add(new PVector(-120, 180, 30));
        points.add(new PVector(-120, -180, 30));
        points.add(new PVector(120, -180, 30));

        points.add(new PVector(120, 180, -30));
        points.add(new PVector(-120, 180, -30));
        points.add(new PVector(-120, -180, -30));
        points.add(new PVector(120, -180, -30));*/

        topLine =  new PVector(0, -300, 0);


        angularMomentum = new AngularMomentum(points.toArray(new PVector[0]), mass);
        angularVelocity = angularMomentum.getAngularVelocity(rotation);

        points.forEach(centerOfMass::add);

        centerOfMass.div(points.size());
    }

    float coolAngle = (float) (Math.PI / 2);

    public void update() {

        angularVelocity = angularMomentum.getAngularVelocity(rotation);

        PVector point = new PVector(0, 180, 0);
        PVector force = new PVector((float) (Math.cos(coolAngle))* 5,Math.abs((float) (Math.sin(coolAngle))) * 5,0);

        point = MathUtil.MultiMat(point, rotation.toMatrix());
        force = MathUtil.MultiMat(force, rotation.toMatrix());

        PVector temp = force.copy().mult(20).add(pos).add(point);
        PVector temp2 = point.copy().add(pos);
        applet.line(temp2.x,temp2.y,temp.x,temp.y);



        if(Main.keyz[0]){
            PVector vector = point.copy().cross(force);
            angularMomentum.applyForce(vector);

            momentum.add(force.mult(-1));
        }
        if(Main.keyz[1]){
            angularMomentum.zero();
            pos.set(new PVector( applet.width / 2f,  applet.height / 2f));
            momentum.mult(0);
        }
        if(Main.keyz[2]){
            coolAngle += 0.05f;
        }
        if(Main.keyz[3]){
            coolAngle -= 0.05f;
        }
        if(Main.keyz[4]){
            PVector spin = new PVector(180, 0, 0).cross(new PVector(0,0,5));
            angularMomentum.applyForce(MathUtil.MultiMat(spin, rotation.toMatrix()));

        }


        rotation = rotation.applyRotation(angularVelocity);

        /*momentum.add(new PVector(0,1));*/

        pos.add(momentum.copy().div(mass));


        if(pos.x > applet.width){
            pos.x = 0;
        }
    }

    public void draw() {

        float[][] mat = rotation.toMatrix();
        PVector com = MathUtil.MultiMat(centerOfMass, mat);

        List<PVector> transformedPoints = points.stream()
                .map(PVector::copy)
                .map(x -> MathUtil.MultiMat(x, mat))
                .sorted(Comparator.comparingDouble(x -> x.z))
                .collect(Collectors.toList());

        for (PVector p : transformedPoints) {

            p.sub(com);

            applet.stroke( p.z * 255 / 2,  p.z * 255 / 2, 255);

            p.add(pos);

            applet.strokeWeight(10);

            applet.point(p.x, p.y);
        }

        PVector l = MathUtil.MultiMat(topLine.copy().sub(centerOfMass), mat);
        applet.stroke(l.z * 255 / 2, l.z * 255 / 2, 255);
        l.add(pos);
        applet.point(l.x,l.y);
        applet.strokeWeight(1);
        applet.line(pos.x - com.x, pos.y - com.y, l.x, l.y);

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
