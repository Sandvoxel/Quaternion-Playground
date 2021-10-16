package processing.sketches.physics;

import processing.core.PApplet;
import processing.core.PVector;
import processing.sketches.Main;
import processing.sketches.MathUtil;

import java.awt.*;

public class RigidBody {
    protected PVector[] vertices;

    protected PVector pos;
    protected Quaternion rotation = new Quaternion();
    protected Quaternion rotationalInertia = new Quaternion();

    protected Color color = Color.blue;

    protected PApplet sketch;

    public void setup(){
        this.sketch = Main.sketch;
        this.pos = new PVector(sketch.width/2, sketch.height/2);
    }


    public void draw(){
        float[][] mat = rotation.toMatrix();

        for (PVector point : vertices){
            PVector p = MathUtil.MultiMat(point, mat);

            p.add(pos);

            sketch.stroke(color.getRGB());

            sketch.strokeWeight(10);
            sketch.point(p.x,p.y);


        }
    }

    public void rotate(Quaternion q){
        rotation = q.multi(rotation);
    }

}
