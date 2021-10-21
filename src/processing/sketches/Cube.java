package processing.sketches;

import processing.core.PApplet;
import processing.core.PVector;

public class Cube {
    private Quaternion rotation = new Quaternion();

    private float mass = 1f;

    private PVector pos;
    private PVector momentum = new PVector();
    private PVector thrust = new PVector(0,0.01f,0);

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

    PVector lastMousePos = new PVector();


    public void update(){
        float[][] mat = rotation.toMatrix();

        momentum.add(MathUtil.MultiMat(thrust,mat));

        pos.add(momentum.div(mass));
        if(applet.mousePressed){
            Quaternion mouseRot = new Quaternion().fromEuler(lastMousePos.x - applet.mouseX, applet.mouseY - lastMousePos.y, 0);
            rotation = mouseRot.multi(rotation);
        }
        lastMousePos.x = applet.mouseX;
        lastMousePos.y = applet.mouseY;
    }


    public void draw(){

        float[][] mat = rotation.toMatrix();

        int i = 0;
        for (PVector point: points) {
            PVector p = MathUtil.MultiMat(point, mat);

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


    }



}
