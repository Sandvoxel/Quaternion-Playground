package processing.sketches;

import processing.core.PVector;
import processing.sketches.physics.math.Mesh;
import processing.sketches.physics.math.Vertex;
import processing.sketches.physics.rigidbodys.RigidBody;

import java.awt.*;
import java.util.Arrays;

public class Cube extends RigidBody {

    private float size;


    public Cube(float size) {
        this.size = size;
        Vertex[] points = new Vertex[8];

        points[0] = new Vertex(1,1,1, Color.cyan);
        points[1] = new Vertex(-1,1,1, 60);
        points[2] = new Vertex(-1,-1,1);
        points[3] = new Vertex(1,-1,1);

        points[4] = new Vertex(1,1,-1);
        points[5] = new Vertex(-1,1,-1);
        points[6] = new Vertex(-1,-1,-1, Color.MAGENTA);
        points[7] = new Vertex(1,-1,-1);


        this.mesh = new Mesh(points);
    }

    @Override
    public void draw() {
        super.draw(size);
    }
}
