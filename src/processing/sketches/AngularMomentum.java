package processing.sketches;

import processing.core.PVector;

import java.util.Arrays;

public class AngularMomentum {
    private final float[][] bodyTensor = new float[3][3];
    private final PVector angularMomentum = new PVector(0,0,0);
    private final float pointMass;

    public AngularMomentum(PVector[] points, float mass) {
        this.pointMass = mass / points.length;

        Arrays.stream(points).forEach(point -> {

            float xy = -pointMass*(point.x*point.y);
            float xz = -pointMass*(point.x*point.z);
            float yz = -pointMass*(point.y*point.z);

            bodyTensor[0][0] += pointMass*(point.y * point.y + point.z * point.z);
            bodyTensor[0][1] += xy;
            bodyTensor[0][2] += xz;

            bodyTensor[1][0] += xy;
            bodyTensor[1][1] += pointMass*(point.x * point.x + point.z * point.z);
            bodyTensor[1][2] += yz;

            bodyTensor[2][0] += xz;
            bodyTensor[2][1] += yz;
            bodyTensor[2][2] += pointMass*(point.x * point.x + point.y * point.y);

        });
    }

    public float[][] calculateTensor(Quaternion rotation){
        float[][] newTensor = Arrays.stream(bodyTensor).map(float[]::clone).toArray(float[][]::new);

        newTensor = MathUtil.multiplyMatrices(rotation.toMatrix(), newTensor);
        newTensor = MathUtil.multiplyMatrices(newTensor, MathUtil.invert(rotation.toMatrix()));

        return newTensor;
    }


    public PVector getAngularVelocity(Quaternion rotation){
        return MathUtil.MultiMat(angularMomentum, MathUtil.invert(calculateTensor(rotation)));
    }

    public void applyForce(PVector force){
        angularMomentum.add(force);
    }
}
