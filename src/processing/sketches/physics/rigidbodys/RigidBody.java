package processing.sketches.physics.rigidbodys;

import processing.core.PApplet;
import processing.core.PVector;
import processing.sketches.Main;
import processing.sketches.MathUtil;
import processing.sketches.physics.math.Mesh;
import processing.sketches.physics.math.Quaternion;
import processing.sketches.physics.math.Vertex;

import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;

public class RigidBody {
    protected Mesh mesh = new Mesh();

    protected PVector pos;
    protected PVector velocity = new PVector();
    protected Quaternion rotation = new Quaternion();
    protected Quaternion rotationalInertia = new Quaternion().fromEuler(0,0,0);

    private PVector centerOfMass = new PVector();

    protected PApplet sketch;

    public void setup(){
        this.sketch = Main.sketch;
        this.pos = new PVector(sketch.width/2f, sketch.height/2f);

        AtomicReference<Float> mass = new AtomicReference<>((float) 0);
        mesh.getVertexStream().forEach(vertex -> {
            mass.updateAndGet(v -> (v + vertex.getMass()));
            PVector vector = vertex.getBodyPos().copy().mult(vertex.getMass());
            centerOfMass.add(vector);
        });
        centerOfMass.div(mass.get());

    }

    public void draw(){
        draw(1);
    }


    public void draw(float scale){
        float[][] mat = rotation.toMatrix();

        for (Vertex point : mesh.getVertices()){

            PVector p = MathUtil.MultiMat(point.getBodyPos().copy().add(centerOfMass), mat);

            p.mult(scale);

            p.add(pos);

            sketch.stroke(point.getColor().getRGB());

            sketch.strokeWeight(10);
            sketch.point(p.x,p.y);


        }
    }

    public void applyForce(PVector bodySpacePos, PVector force){

    }

    public void update(){
        float[][] mat = rotation.toMatrix();

        PVector lowestPoint = new PVector();

        for (Vertex point : mesh.getVertices()){
            PVector p = MathUtil.MultiMat(point.getBodyPos().copy().add(centerOfMass), mat);
            p.mult(60);
            p.add(pos);

            lowestPoint = p.y >= lowestPoint.y ? p : lowestPoint;

        }
        if(lowestPoint.y >= sketch.height - 6){
            pos.y = sketch.height - lowestPoint.sub(pos).y - 6f;
            velocity.mult(0);
        }else {
            velocity.y += 0.5f;
        }

        pos.add(velocity);
        rotation = rotation.multi(rotationalInertia);
    }

    public void rotate(Quaternion q){
        rotation = q.multi(rotation);
    }

}
