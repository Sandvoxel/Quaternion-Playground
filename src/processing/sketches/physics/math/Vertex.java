package processing.sketches.physics.math;

import processing.core.PVector;
import processing.sketches.MathUtil;

import java.awt.*;

public class Vertex {
    PVector bodyPos;
    float mass;
    private Color color = Color.blue;

    public Vertex(float x, float y, float z) {
        this(x,y,z,1f);
    }
    public Vertex(float x, float y, float z, float mass) {
        this.bodyPos = new PVector(x,y,z);
        this.mass = mass;
    }
    public Vertex(float x, float y, float z, Color color) {
        this.bodyPos = new PVector(x,y,z);
        this.color = color;
    }

    public Vertex(PVector vector) {
        this.bodyPos = vector;
        this.mass = 1f;
    }

    public void mult(float multi){
        bodyPos.mult(multi);
    }

    public PVector getBodyPos() {
        return bodyPos;
    }

    public PVector getTransformedBodyPos(float[][] transform) {
        return MathUtil.MultiMat(bodyPos, transform);
    }

    public Color getColor() {
        return color;
    }

    public float getMass() {
        return mass;
    }
}
