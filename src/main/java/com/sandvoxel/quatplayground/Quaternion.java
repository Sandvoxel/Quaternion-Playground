package com.sandvoxel.quatplayground;

import processing.core.PVector;

import static processing.core.PApplet.cos;
import static processing.core.PApplet.sin;
import static processing.core.PConstants.TAU;

public class Quaternion {
    private float w, x, y, z;

    public Quaternion() {
        w = 1;
    }

    public Quaternion(float x, float y, float z, float w) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;

        this.normalize();
    }

    /**
     * Converts Eular angles to a Quaternion for testing
     * @param yaw X
     * @param pitch Y
     * @param roll Z
     * @return Quaternion of given angles
     */
    public Quaternion fromEuler(float yaw, float pitch, float roll) {

        yaw = -MathUtil.degToRad(yaw);
        pitch = -MathUtil.degToRad(pitch);
        roll = -MathUtil.degToRad(roll);

        float cy = cos(roll * 0.5f);
        float sy = sin(roll * 0.5f);
        float cp = cos(pitch * 0.5f);
        float sp = sin(pitch * 0.5f);
        float cr = cos(yaw * 0.5f);
        float sr = sin(yaw * 0.5f);

        w = cr * cp * cy + sr * sp * sy;
        x = sr * cp * cy - cr * sp * sy;
        y = cr * sp * cy + sr * cp * sy;
        z = cr * cp * sy - sr * sp * cy;

        return this.normalize();
    }

    /**
     * converts Quaternion to rotation matrix
     * @return 3x3 rotation matrix
     */
    public float[][] toMatrix() {
        float[][] out = new float[3][3];

        float y2 = x * x;
        float z2 = y * y;
        float w2 = z * z;

        out[0][0] = 1 - 2 * (z2 + w2);
        out[0][1] = 2 * (x * y + w * z);
        out[0][2] = 2 * (x * z - w * y);

        out[1][0] = 2 * (x * y - w * z);
        out[1][1] = 1 - 2 * (y2 + w2);
        out[1][2] = 2 * (y * z + w * x);

        out[2][0] = 2 * (x * z + w * y);
        out[2][1] = 2 * (y * z - w * x);
        out[2][2] = 1 - 2 * (y2 + z2);

        return out;

    }

    /**
     * multiples Quaternion
     * @param q Quaternion to multiply against.
     * @return Quaternion after multiply.
     */
    public Quaternion multi(Quaternion q) {
        Quaternion out = new Quaternion();

        out.w = w * q.w - x * q.x - y * q.y - z * q.z;
        out.x = w * q.x + x * q.w + y * q.z - z * q.y;
        out.y = w * q.y - x * q.z + y * q.w + z * q.x;
        out.z = w * q.z + x * q.y - y * q.x + z * q.w;

        out.normalize();
        return out;
    }

    /**
     * Gets the change in q from angular velocity
     * @param vector angular velocity
     * @return a quat of the change of the rotation.
     */
    public Quaternion applyRotation(PVector vector) {
        Quaternion out = new Quaternion();
        float rotation = (vector.mag() % (TAU)) / 2;
        PVector vec = vector.copy().normalize();

        vec.mult(sin(rotation));

        out.w = cos(rotation);
        out.x = vec.x;
        out.y = vec.y;
        out.z = vec.z;

        return multi(out.normalize());
    }

    /**
     * gets the magnitude of the Quaternion
     * @return float of the length of the Quaternion.
     */
    public float magnitude() {
        return Main.sqrt(w * w + x * x + y * y + z * z);
    }

    /**
     * normalizes Quaternion and returns the normalized Quaternion
     * @return normalized Quaternion
     */
    public Quaternion normalize() {
        float magnitude = magnitude();
        w /= magnitude;
        x /= magnitude;
        y /= magnitude;
        z /= magnitude;

        return this;
    }

    public Quaternion getInverse(){
        return new Quaternion(-x,-y,-z,w);
    }

    @Override
    public String toString() {
        return "Quaternion{" +
                "x=" + w +
                ", y=" + x +
                ", z=" + y +
                ", w=" + z +
                '}';
    }

    public Quaternion copy() {
        return new Quaternion(x,y,z,w);
    }
}
