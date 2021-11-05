package processing.sketches;

import processing.core.PVector;

import java.util.Arrays;
import java.util.stream.IntStream;

public class MathUtil {

    /**
     * Multiply Vector3 by a 3x3 rotation matrix
     * @param vec Vector3 point to rotate.
     * @param mat Rotation matrix.
     * @return Transformed points.
     */
    public static PVector MultiMat(PVector vec, float[][] mat) {
        PVector out = new PVector();

        out.x = mat[0][0] * vec.x + mat[0][1] * vec.y + mat[0][2] * vec.z;
        out.y = mat[1][0] * vec.x + mat[1][1] * vec.y + mat[1][2] * vec.z;
        out.z = mat[2][0] * vec.x + mat[2][1] * vec.y + mat[2][2] * vec.z;

        return out;
    }

    /**
     * Multiply 2 matrices
     * @param firstMatrix first matrix
     * @param secondMatrix second matrix
     * @return Multiplied matrix
     */
    public static float[][] multiplyMatrices(float[][] firstMatrix, float[][] secondMatrix) {
        float[][] result = new float[3][3];

        for (int i = 0; i < firstMatrix.length; i++) {
            for (int j = 0; j < secondMatrix[0].length; j++) {
                for (int k = 0; k < firstMatrix[0].length; k++) {
                    result[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
                }
            }
        }
        return result;
    }

    public static float degToRad(float deg) {
        return deg * (Main.PI / 180);
    }

    /**
     * inverts matrix
     * @param a matrix to invert
     * @return inverts matrix
     */
    public static float[][] invert(float a[][])
    {
        int n = a.length;
        float[][] x = new float[n][n];
        float[][] b = new float[n][n];
        int[] index = new int[n];
        for (int i=0; i<n; ++i)
            b[i][i] = 1;

        // Transform the matrix into an upper triangle
        gaussian(a, index);

        // Update the matrix b[i][j] with the ratios stored
        for (int i=0; i<n-1; ++i)
            for (int j=i+1; j<n; ++j)
                for (int k=0; k<n; ++k)
                    b[index[j]][k]
                            -= a[index[j]][i]*b[index[i]][k];

        // Perform backward substitutions
        for (int i=0; i<n; ++i)
        {
            x[n-1][i] = b[index[n-1]][i]/a[index[n-1]][n-1];
            for (int j=n-2; j>=0; --j)
            {
                x[j][i] = b[index[j]][i];
                for (int k=j+1; k<n; ++k)
                {
                    x[j][i] -= a[index[j]][k]*x[k][i];
                }
                x[j][i] /= a[index[j]][j];
            }
        }
        return x;
    }

    public static void gaussian(float[][] a, int[] index)
    {
        int n = index.length;
        float[] c = new float[n];

        // Initialize the index
        for (int i=0; i<n; ++i)
            index[i] = i;

        // Find the rescaling factors, one from each row
        for (int i=0; i<n; ++i)
        {
            float c1 = 0;
            for (int j=0; j<n; ++j)
            {
                float c0 = Math.abs(a[i][j]);
                if (c0 > c1) c1 = c0;
            }
            c[i] = c1;
        }

        // Search the pivoting element from each column
        int k = 0;
        for (int j=0; j<n-1; ++j)
        {
            float pi1 = 0;
            for (int i=j; i<n; ++i)
            {
                float pi0 = Math.abs(a[index[i]][j]);
                pi0 /= c[index[i]];
                if (pi0 > pi1)
                {
                    pi1 = pi0;
                    k = i;
                }
            }

            // Interchange rows according to the pivoting order
            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i=j+1; i<n; ++i)
            {
                float pj = a[index[i]][j]/a[index[j]][j];

                // Record pivoting ratios below the diagonal
                a[index[i]][j] = pj;

                // Modify other elements accordingly
                for (int l=j+1; l<n; ++l)
                    a[index[i]][l] -= pj*a[index[j]][l];
            }
        }
    }


}
