import org.junit.Test;

import static org.junit.Assert.*;

public class MatrixTest {

    @Test
    public void MatrixTest(){
        int rows = (int)(Math.random() * 10);
        int cols = (int)(Math.random() * 10);
        Matrix matrix = new Matrix(rows, cols);
        //sprawdzenie rozmiarow
        assertEquals(rows,matrix.GetRows());
        assertEquals(cols,matrix.GetCols());
    }
    @Test
    public void MatrixArrayTest(){
        double[][] d = {{1,2,3,4},{5,6,9,9},{7,9,9}};
        Matrix matrix = new Matrix(d);
        double[][] returned = matrix.AsArray();
        //sprawdzenie rozmiarow
        assertEquals(d.length,returned.length);
        assertEquals(d[0].length,returned[0].length);
        //sprawdzenie elementów automatycznie zerowych
        assertEquals(0,matrix.Get(2,3),0);

    }

    @Test
    public void getRows() {

    }

    @Test
    public void getCols() {
    }

    @Test
    public void asArray() {
        double[][] d = {{1,2,3,4},{5,6,9,9},{7,9,9,9}};
        Matrix matrix = new Matrix(d);
        double[][] returned = matrix.AsArray();
        assertArrayEquals(d[0],returned[0],0);
        assertArrayEquals(d[1],returned[1],0);
        assertArrayEquals(d[2],returned[2],0);
    }

    @Test
    public void get() {
        double[][] d = {{1,2,3,4},{5,6,9,9},{7,9,9,9}};
        Matrix matrix = new Matrix(d);
        double number = matrix.Get(0,3);
        assertEquals(4, number, 0);
    }

    @Test
    public void set() {
        double[][] d = {{1,2,3,4},{5,6,9,9},{7,9,9,9}};
        Matrix matrix = new Matrix(d);
        matrix.Set(0,0,5);
        assertEquals(5,matrix.Get(0,0),0);
    }

    @Test
    public void testToString() {
        String s= "[[1.0,2.3,4.56], [12.3,  45, 21.8]]";
        s= s.replaceAll("(\\[|\\]|\\s)+","");
        String[] t = s.split("(,)+");
        for(String x:t){
            System.out.println(String.format("\'%s\'",x ));
        }

        double[]d=new double[t.length];
        for(int i=0;i<t.length;i++) {
            d[i] = Double.parseDouble(t[i]);
        }

        double arr[][]=new double[1][];
        arr[0]=d;

        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[i].length;j++){
                System.out.println(arr[i][j]);
            }
        }
    }

    @Test
    public void reshape() {
        double[][] d = {{1,2,3,4},{5,6,9,9},{7,9,9,9}};
        Matrix matrix = new Matrix(d);
        try{
            matrix.Reshape(2,3);
        }catch (RuntimeException exception){
            return;
        }
        fail("nieprzechwycono");
    }

    @Test
    public void shape() {
        double[][] d = {{1,2,3,4},{5,6,9,9},{7,9,9,9}};
        Matrix matrix = new Matrix(d);
        int[] returnedArray = matrix.Shape();
        assertEquals(d.length,returnedArray[0]);
        assertEquals(d[0].length, returnedArray[1]);
    }

    @Test
    public void add() {
        double[][] d = {{1,2,3,4},{5,6,9,9},{7,9,9,9}};
        Matrix matrix = new Matrix(d);
        Matrix matrix2 = matrix.mul(-1);

        Matrix result = matrix.add(matrix2);
        assertEquals(0,result.Frobenius(),0);
    }

    @Test
    public void sub() {
        double[][] d = {{1,2,3,4},{5,6,9,9},{7,9,9,9}};
        Matrix matrix1 = new Matrix(d);
        Matrix matrix2 = new Matrix(d);
        Matrix result = matrix1.sub(matrix2);
        assertEquals(0,result.Frobenius(),0);
    }

    @Test
    public void mul() {
        double[][] d = {{1,2,3,4},{5,6,9,9},{7,9,9,9}};
        double[][] d1 = {{1,2,3},{5,6,9},{7,9,9}};
        Matrix matrix = new Matrix(d);
        Matrix matrix1 = new Matrix(d1);
        try{
            matrix.mul(matrix1);
        }catch(RuntimeException exception) {
            return;
        }
        fail("nie przechwycono");

         //test pomnozenia kazdego elemento przez 0 w formie macierzowej
        double [][] d2 ={{0,0,0},{0,0,0},{0,0,0}};
        matrix = new Matrix(d2);
        Matrix result = matrix1.mul(matrix);
        assertEquals(0,result.Frobenius(),0);

    }

    @Test
    public void div() {
        double[][] d = {{1,2,3,4},{5,6,9,9},{7,9,9,9}};
        Matrix matrix = new Matrix(d);
        Matrix result = matrix.div(matrix);
        assertEquals(matrix.GetCols() * matrix.GetRows(), result.Frobenius(),0);
    }

    @Test
    public void add1() {
        double[][] d = {{1,2,3,4},{5,6,9,9},{7,9,9,9}};
        Matrix matrix = new Matrix(d);
        Matrix result = matrix.add(2);
        assertEquals(3,result.Get(0,0),0);
    }

    @Test
    public void sub1() {
        double[][] d = {{1,2,3,4},{5,6,9,9},{7,9,9,9}};
        Matrix matrix = new Matrix(d);
        Matrix result = matrix.sub(2);
        assertEquals(-1,result.Get(0,0),0);
    }

    @Test
    public void mul1() {
        double[][] d = {{1,2,3,4},{5,6,9,9},{7,9,9,9}};
        Matrix matrix = new Matrix(d);
        Matrix matrix2 = matrix.mul(-1);

        Matrix result = matrix.add(matrix2);
        assertEquals(0,result.Frobenius(),0);
    }

    @Test
    public void div1() {
        double[][] d = {{1,2,3,4},{5,6,9,9},{7,9,9,9}};
        Matrix matrix = new Matrix(d);
        try{
            matrix.div(0);
        }catch (RuntimeException excpetion){
            return;
        }
        fail("nieprzechwycono");

        Matrix result = matrix.div(2);
        assertEquals(2,result.Get(0,3),0);
    }

    @Test
    public void dot() {
        double [][] tab1 = {{1,2,3},{5,6},{7,8}};
        double [][] tab2 = {{0},{4,1,2},{2,1}};
        Matrix matrix1 = new Matrix(tab1);
        Matrix matrix2 = new Matrix(tab2);

        Matrix result = matrix1.Dot(matrix2);
        double [][] d = result.AsArray();

        double[][] resultExpected = {{14,5,4},{24,6,12},{32,8,16}};

        assertArrayEquals(resultExpected[0],d[0],0);
        assertArrayEquals(resultExpected[1],d[1],0);
        assertArrayEquals(resultExpected[2],d[2],0);

    }

    @Test
    public void frobenius() {
    }

    @Test
    public void random() {
    }

    @Test
    public void eye() {
        int n = 3;
        Matrix matrix = Matrix.Eye(n);
        assertEquals(n,matrix.Frobenius(),0);
    }
}