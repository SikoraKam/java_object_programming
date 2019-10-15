public class Main {

    public static void PrintMatrix(double[][] m){
        for (int i=0;i<m.length; i++){
            for (int j=0; j<m[i].length; j++){
                System.out.printf("%.1f ", m[i][j]);
            }
            System.out.printf("\n");
        }
    }



    public static void main(String[] args) {
        Matrix matrix1 = new Matrix(2,2); // zwykly konstruktor
        Matrix matrix2 = new Matrix(new double[][]{{1,2,3,4},{5,6},{7,8},{9}}); // konstruktor z tablicy

        /*matrix1.Set(0,0, 1);
        matrix1.Set(0,1,2);
        matrix1.Set(1,0, 3);
        matrix1.Set(1,1, 4);

        double[][] returnedMatrix1 = matrix1.AsArray();
        PrintMatrix(returnedMatrix1);


        double getNumber1 = matrix2.Get(2,2);
        double getNumber2 = matrix2.Get(0,1);
        System.out.println(getNumber1 + " and " + getNumber2);

        matrix1.Reshape(1,4);
        returnedMatrix1 = matrix1.AsArray();
        PrintMatrix(returnedMatrix1);


        double[][] returnedMatrix2 = matrix2.add(2).AsArray();
        PrintMatrix(returnedMatrix2);


        Matrix matrix3 = new Matrix(new double[][]{{1,2,3,4},{5,6},{7,8},{9}});
        PrintMatrix(matrix3.AsArray());
        Matrix multipledMatrix = matrix3.Dot(matrix2);
        PrintMatrix(multipledMatrix.AsArray());

        double frobenius = matrix1.Frobenius();
        System.out.println(frobenius);
        System.out.printf("\n");

        Matrix newMatrix = matrix3.add(10);
        PrintMatrix(matrix3.AsArray());*/
        System.out.println(matrix2.toString());

    }
}
