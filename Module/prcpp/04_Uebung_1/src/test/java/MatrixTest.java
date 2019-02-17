import org.junit.Assert;
import org.junit.Test;

public class MatrixTest {

    @Test
    public void testEquals() {
        Matrix matrix1 = new Matrix(2, 2, new double[]{1, 2, 3, 4});
        Matrix matrix2 = new Matrix(2, 2, new double[]{1, 2, 3, 4});
        Matrix matrix3 = new Matrix(2, 3, new double[]{1, 2, 3, 4, 5, 6});
        Matrix matrix4 = new Matrix(0, 0, new double[]{});

        Assert.assertTrue(matrix1.equals(matrix1));
        Assert.assertTrue(matrix1.equals(matrix2));
        Assert.assertFalse(matrix1.equals(matrix3));
        Assert.assertFalse(matrix1.equals(matrix4));
    }

    @Test
    public void testMultiply() {
        Matrix matrix1 = new Matrix(2, 3, new double[]{1, 2, 3, 4, 5, 6});
        Matrix matrix2 = new Matrix(3, 2, new double[]{3, 6, 2, 5, 1, 4});
        Matrix matrix3 = new Matrix(2, 2, new double[]{10, 28, 28, 73});

        Matrix matrix4 = matrix1.multiply(matrix2);
        Assert.assertTrue(matrix3.equals(matrix4));

        Matrix matrix5 = matrix1.multiplyNative(matrix2);
        Assert.assertTrue(matrix3.equals(matrix5));
    }

    @Test
    public void testPower() {
        Matrix matrix1 = new Matrix(2, 2, new double[]{1, 2, 3, 4});
        Matrix matrix2 = matrix1.powerNative(1);
        Assert.assertTrue(matrix1.equals(matrix2));

        Matrix matrix3 = new Matrix(2, 2, new double[]{7, 10, 15, 22});
        Matrix matrix4 = matrix1.power(2);
        Assert.assertTrue(matrix3.equals(matrix4));

        Matrix matrix5 = new Matrix(2, 2, new double[]{37, 54, 81, 118});
        Matrix matrix6 = matrix1.power(3);
        Assert.assertTrue(matrix5.equals(matrix6));

        Matrix matrix7 = matrix1.powerNative(1);
        Assert.assertTrue(matrix1.equals(matrix7));

        Matrix matrix8 = new Matrix(2, 2, new double[]{7, 10, 15, 22});
        Matrix matrix9 = matrix1.powerNative(2);
        Assert.assertTrue(matrix8.equals(matrix9));

        Matrix matrix10 = new Matrix(2, 2, new double[]{37, 54, 81, 118});
        Matrix matrix11 = matrix1.powerNative(3);
        Assert.assertTrue(matrix10.equals(matrix11));
    }

    @Test
    public void testSpeed() {
        new Application();
    }
}
