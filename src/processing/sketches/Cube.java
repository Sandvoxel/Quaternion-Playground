package processing.sketches;

import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Cube {
    private Quaternion rotation = new Quaternion(1f,0,0,0f);
    //private Quaternion angularMomentum = new Quaternion(0.99f,0f,1f,0f);

    private final float mass = 1f;

    private final PVector pos;
    private final PVector momentum = new PVector();

    PVector[] points = new PVector[9];

    PApplet applet = Main.sketch;


    public Cube(PVector pos) {
        this.pos = pos;

        points[0] = new PVector(1,1,1);
        points[1] = new PVector(-1,1,1);
        points[2] = new PVector(-1,-1,1);
        points[3] = new PVector(1,-1,1);

        points[4] = new PVector(1,1,-1);
        points[5] = new PVector(-1,1,-1);
        points[6] = new PVector(-1,-1,-1);
        points[7] = new PVector(1,-1,-1);

        points[8] = new PVector(0,4,0);
    }


    private final PVector angularMomentum = new PVector(1,  0, 0);

    public void update(){
        float[][] mat = rotation.toMatrix();


        System.out.println(rotation.applyRotation(angularMomentum));

        rotation = rotation.multi(rotation.applyRotation(angularMomentum));


        pos.add(momentum.div(mass));

/*        momentum.add(MathUtil.MultiMat(thrust,mat));

        if(applet.mousePressed){
            Quaternion mouseRot = new Quaternion().fromEuler(lastMousePos.x - applet.mouseX, applet.mouseY - lastMousePos.y, 0);
            rotation = mouseRot.multi(rotation);
        }
        lastMousePos.x = applet.mouseX;
        lastMousePos.y = applet.mouseY;*/
    }


    public void draw(){

        float[][] mat = rotation.toMatrix();

        int i = 0;
        for (PVector point: points) {
            PVector p = MathUtil.MultiMat(point, mat);

            p.y *= -1;

            float color = p.z;
            p.mult(60);
            p.add(pos);

            applet.stroke((color + 1) * 255/2, (color + 1) * 255/2,255);
            applet.strokeWeight(1);

            if(i == 8){

                applet.line(pos.x, pos.y, p.x,p.y);
            }

            applet.strokeWeight(10);
            applet.point(p.x, p.y);

            i++;
        }
/*        PVector axis = angularMomentum.getAxisOfRotation();
        System.out.println(axis);
        axis.mult(60);
        axis.add(pos);

        applet.stroke(Color.red.getRGB());
        applet.strokeWeight(1);
        applet.line(pos.x, pos.y, axis.x,axis.y);

        applet.strokeWeight(10);
        applet.point(axis.x, axis.y);*/


    }



}
