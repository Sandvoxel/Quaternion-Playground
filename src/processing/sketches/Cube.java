package processing.sketches;

import processing.core.PApplet;
import processing.core.PVector;
import processing.sketches.physics.RigidBody;

import java.util.Arrays;

public class Cube extends RigidBody {

    private float size;


    public Cube(float size) {
        PVector[] points = new PVector[8];

        points[0] = new PVector(1,1,1);
        points[1] = new PVector(-1,1,1);
        points[2] = new PVector(-1,-1,1);
        points[3] = new PVector(1,-1,1);

        points[4] = new PVector(1,1,-1);
        points[5] = new PVector(-1,1,-1);
        points[6] = new PVector(-1,-1,-1);
        points[7] = new PVector(1,-1,-1);

        Arrays.stream(points).forEach(point -> point.mult(size));

        this.size = size;
        super.vertices = points;
    }

}
