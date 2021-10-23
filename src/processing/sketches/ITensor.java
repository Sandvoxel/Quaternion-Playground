package processing.sketches;

import processing.core.PVector;

public class ITensor {
    private float[][] tensor = new float[3][3];
    private float mass;
    private float pointMass;

    public ITensor(PVector[] points, float mass) {
        this.mass = mass;


    }
}
