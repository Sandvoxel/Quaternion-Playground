package processing.sketches;

import processing.core.PVector;

public class AngularMomentum {
    private float[][] tensor = new float[3][3];
    private PVector angularMomentum = new PVector();
    private float mass;
    private float pointMass;

    public AngularMomentum(PVector[] points, float mass) {
        this.mass = mass;


    }
}
