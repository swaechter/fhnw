import java.util.Arrays;

public class Matrix {

    static {
        // Workaround to not rely on the environment variable LD_LIBRARY_PATH
        System.load(System.getProperty("user.dir") + "/libmatrix.so");
    }

    private final int height;

    private final int width;

    private double data[];

    public Matrix(int height, int width, double[] data) {
        this.height = height;
        this.width = width;
        this.data = (data != null) ? data : new double[height * width];
    }

    public Matrix(int height, int width) {
        this(height, width, null);
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = Math.random();
        }
    }

    public Matrix(int height, int width, double value) {
        this(height, width, null);
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = value;
        }
    }

    public boolean equals(Matrix matrix) {
        return height == matrix.height && width == matrix.width && Arrays.equals(data, matrix.data);
    }

    public Matrix multiplyWithTime(Matrix matrix) {
        long start1 = System.currentTimeMillis();
        Matrix newmatrix = multiply(matrix);
        System.out.println("Java Multiply: " + (System.currentTimeMillis() - start1));
        return newmatrix;
    }

    public Matrix multiply(Matrix matrix) {
        if (width != matrix.height) {
            throw new IllegalArgumentException("Invalid sizes");
        }

        int index1 = 0, index3 = 0;
        Matrix newmatrix = new Matrix(height, matrix.width, null);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < matrix.width; j++) {
                double counter = 0.0;
                int index2 = 0;
                for (int k = 0; k < width; k++) {
                    counter += data[index1 + k] * matrix.data[index2 + j];
                    index2 += matrix.width;
                }
                newmatrix.data[index3 + j] = counter;
            }
            index1 += width;
            index3 += matrix.width;
        }
        return newmatrix;
    }

    public Matrix multiplyNativeWithTime(Matrix matrix) {
        long start1 = System.currentTimeMillis();
        Matrix newmatrix = multiplyNative(matrix);
        System.out.println("C Multiply: " + (System.currentTimeMillis() - start1));
        return newmatrix;
    }

    public Matrix multiplyNative(Matrix matrix) {
        if (width != matrix.height) {
            throw new IllegalArgumentException("Invalid sizes");
        }

        Matrix newmatrix = new Matrix(height, matrix.width, null);
        multiplyC(data, matrix.data, newmatrix.data, height, width, matrix.width);
        return newmatrix;
    }

    native void multiplyC(double[] a, double[] b, double[] r, int i, int j, int k);

    public Matrix powerWithTime(int k) {
        long start1 = System.currentTimeMillis();
        Matrix newmatrix = power(k);
        System.out.println("Java Power: " + (System.currentTimeMillis() - start1));
        return newmatrix;
    }

    public Matrix power(int k) {
        if (height != width) {
            throw new IllegalArgumentException("Matrix is not a square");
        }

        if (k < 1) {
            throw new IllegalArgumentException("Invalid power");
        }

        if (k == 1) {
            return this;
        }

        Matrix matrix = this;
        for (int i = 1; i < k; i++) {
            matrix = multiply(matrix);
        }
        return matrix;
    }

    public Matrix powerNativeWithTime(int k) {
        long start1 = System.currentTimeMillis();
        Matrix newmatrix = powerNative(k);
        System.out.println("C Power: " + (System.currentTimeMillis() - start1));
        return newmatrix;
    }

    public Matrix powerNative(int k) {
        if (height != width) {
            throw new IllegalArgumentException("Matrix is not a square");
        }

        if (k < 1) {
            throw new IllegalArgumentException("Invalid power");
        }

        if (k == 1) {
            return this;
        }

        Matrix newmatrix = new Matrix(height, width, null);
        powerC(data, newmatrix.data, height, width, k);
        return newmatrix;
    }

    native void powerC(double[] a, double[] r, int i, int j, int k);
}
