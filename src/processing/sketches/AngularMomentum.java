package processing.sketches;

import processing.core.PVector;

import java.util.Arrays;

public class AngularMomentum {
    private float[][] tensor = new float[3][3];
    private PVector angularMomentum = new PVector();
    private float mass;
    private float pointMass;

    public AngularMomentum(PVector[] points, float mass) {
        this.mass = mass;
        this.pointMass = mass / points.length;

        Arrays.stream(points).forEach(point -> {

            float xy = -pointMass*(point.x*point.y);
            float xz = -pointMass*(point.x*point.z);
            float yz = -pointMass*(point.y*point.z);

            tensor[0][0] += pointMass*(point.y * point.y + point.z * point.z);
            tensor[0][1] += xy;
            tensor[0][2] += xz;

            tensor[1][0] += xy;
            tensor[1][1] += pointMass*(point.x * point.x + point.z * point.z);
            tensor[1][2] += yz;

            tensor[2][0] += xz;
            tensor[2][1] += yz;
            tensor[2][2] += pointMass*(point.x * point.x + point.y * point.y);

        });
    }

    public float[][] calculateTensor(Quaternion rotation){
        float[][] newTensor = Arrays.stream(tensor).map(float[]::clone).toArray(float[][]::new);

        newTensor = MathUtil.multiplyMatrices(rotation.toMatrix(), newTensor);
        newTensor = MathUtil.multiplyMatrices(newTensor, MathUtil.invert(rotation.toMatrix()));
        System.out.println(Arrays.deepToString(newTensor));

        return newTensor;
    }



    public void applyForce(PVector force){
        // TODO: implement method
    }
}
