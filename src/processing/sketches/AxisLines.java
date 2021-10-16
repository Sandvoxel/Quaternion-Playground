package processing.sketches;

import processing.core.PVector;
import processing.sketches.physics.RigidBody;

import java.awt.*;
import java.util.Arrays;

public class AxisLines extends RigidBody {

    public AxisLines() {
        PVector[] points = new PVector[3];

        points[0] = new PVector(4,0,0);
        points[1] = new PVector(0,4,0);
        points[2] = new PVector(0,0,4);

        Arrays.stream(points).forEach(point -> point.mult(60));

        this.vertices = points;
    }


    @Override
    public void draw() {

        float[][] mat = rotation.toMatrix();

        int i = 0;
        for (PVector point: vertices) {
            PVector p = MathUtil.MultiMat(point, mat);


            p.add(pos);

            sketch.strokeWeight(1);

            switch (i){
                case 0:
                    drawLine(p,Color.red);
                    break;
                case 1:
                    drawLine(p, Color.green);
                    break;
                case 2:
                    drawLine(p, Color.blue);
                    break;
            }
            i++;
        }
        sketch.stroke(Color.GRAY.getRGB());
        sketch.strokeWeight(5);
        sketch.point(pos.x,pos.y);
    }
    private void drawLine(PVector p, Color color){
        sketch.stroke(color.getRGB());
        sketch.line(pos.x, pos.y, p.x, p.y);
        sketch.strokeWeight(10);
        sketch.point(p.x, p.y);
    }
}
