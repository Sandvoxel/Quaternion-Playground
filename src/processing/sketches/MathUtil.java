package processing.sketches;

import processing.core.PVector;

public class MathUtil {

    public static PVector MultiMat(PVector vec, float[][] mat){
        PVector out = new PVector();

        out.x = mat[0][0] * vec.x + mat[0][1] * vec.y + mat[0][2] * vec.z;
        out.y = mat[1][0] * vec.x + mat[1][1] * vec.y + mat[1][2] * vec.z;
        out.z = mat[2][0] * vec.x + mat[2][1] * vec.y + mat[2][2] * vec.z;

        return out;
    }

    public static float degToRad(float deg){
        return deg * (Main.PI / 180);
    }
}
