package processing.sketches;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.Vector;

import static java.lang.Math.*;
import static processing.core.PApplet.cos;
import static processing.core.PApplet.sin;

public class Quaternion {
    private float x, y, z, w;

    public Quaternion() {
        x = 1;
    }

    public Quaternion(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;

        this.normalize();
    }
    public Quaternion(PVector vector) {
        this.x = 1;
        this.y = vector.x;
        this.z = vector.y;
        this.w = vector.z;
    }

    public PVector getAxisOfRotation(){
        return new PVector(y,z,w).normalize();
    }

    public Quaternion Scale(float scale){
        return new Quaternion(x,scale * y,scale * z,scale * w);
    }

    public Quaternion fromEuler(float yaw, float pitch, float roll){

        yaw = -MathUtil.degToRad(yaw);
        pitch = -MathUtil.degToRad(pitch);
        roll = -MathUtil.degToRad(roll);

        float cy = cos(roll * 0.5f);
        float sy = sin(roll * 0.5f);
        float cp = cos(pitch * 0.5f);
        float sp = sin(pitch * 0.5f);
        float cr = cos(yaw * 0.5f);
        float sr = sin(yaw * 0.5f);

        x = cr * cp * cy + sr * sp * sy;
        y = sr * cp * cy - cr * sp * sy;
        z = cr * sp * cy + sr * cp * sy;
        w = cr * cp * sy - sr * sp * cy;

        return this.normalize();
    }


    public float[][] toMatrix(){
        float[][] out = new float[3][3];

        float y2 = y * y;
        float z2 = z * z;
        float w2 = w * w;

        out[0][0] = 1 - 2 * (z2 + w2);
        out[0][1] = 2 * (y*z + x*w);
        out[0][2] = 2 * (y*w - x*z);

        out[1][0] = 2 * (y*z - x*w);
        out[1][1] = 1 - 2 * (y2 + w2);
        out[1][2] = 2 * (z*w + x*y);

        out[2][0] = 2 * (y*w + x*z);
        out[2][1] = 2 * (z*w - x*y);
        out[2][2] = 1 - 2 * (y2 + z2);

        return out;

    }

    public Quaternion multi(Quaternion q){
        Quaternion out = new Quaternion();

        out.x = x*q.x - y*q.y - z*q.z - w*q.w;
        out.y = x*q.y + y*q.x + z*q.w - w*q.z;
        out.z = x*q.z - y*q.w + z*q.x + w*q.y;
        out.w = x*q.w + y*q.z - z*q.y + w*q.x;

        out.normalize();
        return out;
    }

    public Quaternion applyRotation(PVector vector){
        Quaternion out = new Quaternion();
        if(vector.mag() == 0) return out;

        PVector vec = vector.copy();

        out.x = y*vec.x - z*vec.y - w*vec.z;
        out.y = x*vec.x + z*vec.x - w*vec.y;
        out.z = x*vec.y - y*vec.x + w*vec.x;
        out.w = x*vec.z + y*vec.y - z*vec.x;


/*        out.x = 0 - y*vec.x - z*vec.y - w*vec.z;
        out.y = x*vec.x + 0 + z*vec.x - w*vec.y;
        out.z = x*vec.y - y*vec.x + 0 + w*vec.x;
        out.w = x*vec.z + y*vec.y - z*vec.x + 0;*/


        return out;
    }

    public PVector getAxis(){
        return new PVector(y,z,w);
    }

    public Quaternion getInverce(){
        return new Quaternion(x, -y, -z, -w);
    }


    public float magnitude(){
        return Main.sqrt(x*x + y*y + z*z + w*w);
    }

    public Quaternion normalize(){
        float magnitude = magnitude();
        x /= magnitude;
        y /= magnitude;
        z /= magnitude;
        w /= magnitude;

        return this;
    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public float getW() {
        return w;
    }

    @Override
    public String toString() {
        return "Quaternion{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", w=" + w +
                '}';
    }
}
