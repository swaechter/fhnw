public class Application {

    public static void main(String[] args) {
        new Application();
    }

    public Application() {
        Matrix matrix1 = new Matrix(500, 6000);
        Matrix matrix2 = new Matrix(6000, 400);
        Matrix matrix3 = new Matrix(250, 250);

        matrix1.multiplyWithTime(matrix2);
        matrix1.multiplyNativeWithTime(matrix2);

        matrix3.powerWithTime(91);
        matrix3.powerNativeWithTime(91);
    }
}
