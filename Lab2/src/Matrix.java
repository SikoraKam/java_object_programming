import java.util.Random;

import static java.lang.Math.pow;

public class Matrix {
    double[] data;
    int rows;
    int cols;

    Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        data = new double[rows * cols];
    }

    public int GetRows() {
        return rows;
    }

    public int GetCols() {
        return cols;
    }

    //----------------- konstruktor z tablicy
    Matrix(double[][] d) {

        int longest = 0;
        for (int i = 0; i < d.length; i++) {
            if (d[i].length > longest)
                longest = d[i].length;
        }
        this.rows = d.length;
        this.cols = longest;
        data = new double[rows * cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (j > (d[i].length - 1))
                    data[(i * cols) + j] = 0;
                else
                    data[(i * cols) + j] = d[i][j];
            }
        }
    }

    //------------------------metoda zwracająca tablice dwuwymiarową
    public double[][] AsArray() {
        double[][] array = new double[rows][cols];
        for (int i = 0; i < cols * rows; i++) {
            int column = i % cols;
            int row = (i - column) / cols;
            array[row][column] = data[i];
        }
        return array;
    }

    //----------------------------------------- Getter i Setter
    public double Get(int r, int c) {
        return data[r * cols + c];
    }

    public void Set(int r, int c, double value) {
        data[r * cols + c] = value;
    }

    //------------------------------------ toString
    public String toString(){
        StringBuilder buf = new StringBuilder();
        buf.append("[ ");
        for(int i=0; i<rows; i++){
            buf.append("[");
            for(int j = 0; j<cols; j++){
                buf.append(data[i * cols + j]);
                if(j != cols-1)
                    buf.append(", ");
            }
            buf.append("] ");

        }
        buf.append("]");

        return buf.toString();
    }

    void Reshape(int newRows, int newCols) {
        if (rows * cols != newRows * newCols)
            throw new RuntimeException(String.format("%d x %d matrix can't be reshaped to %d x %d", rows, cols, newRows, newCols));
        else {
            this.rows = newRows;
            this.cols = newCols;
        }
    }

    int[] Shape() {
        int[] arrayShape = new int[2];
        arrayShape[0] = rows;
        arrayShape[1] = cols;
        return arrayShape;
    }

    Matrix add(Matrix m) {
        if (rows != m.GetRows() || cols != m.GetCols()) {
            throw new RuntimeException("zle wymiary");
        }
        Matrix result = new Matrix(rows, cols);

        for (int i = 0; i < cols * rows; i++) {
            result.data[i] = data[i] + m.data[i];
        }
        return result;
    }

    Matrix sub(Matrix m) {
        if (rows != m.GetRows() || cols != m.GetCols()) {
            throw new RuntimeException("zle wymiary");
        }
        Matrix result = new Matrix(rows, cols);

        for (int i = 0; i < cols * rows; i++) {
            result.data[i] = data[i] - m.data[i];
        }
        return result;
    }

    // mnozenie elementow, nie macierzy
    Matrix mul(Matrix m) {
        if (rows != m.GetRows() || cols != m.GetCols()) {
            throw new RuntimeException("zle wymiary");
        }
        Matrix result = new Matrix(rows, cols);

        for (int i = 0; i < cols * rows; i++) {
            result.data[i] = data[i] * m.data[i];
        }
        return result;
    }

    //dzielenie elementow, nie macierzy
    Matrix div(Matrix m) {
        if (rows != m.GetRows() || cols != m.GetCols()) {
            throw new RuntimeException("zle wymiary");
        }
        Matrix result = new Matrix(rows, cols);

        for (int i = 0; i < cols * rows; i++) {
            result.data[i] = data[i] / m.data[i];
        }
        return result;
    }

    Matrix add(double w) {
        Matrix matrix = new Matrix(rows,cols);
        for (int i = 0; i < rows * cols; i++)
            matrix.data[i] = this.data[i] + w;
        return matrix;
    }

    Matrix sub(double w) {
        Matrix matrix = new Matrix(rows,cols);
        for (int i = 0; i < rows * cols; i++)
            matrix.data[i] = this.data[i] - w;
        return matrix;
    }

    Matrix mul(double w) {
        Matrix matrix = new Matrix(rows,cols);
        for (int i = 0; i < rows * cols; i++)
            matrix.data[i] = this.data[i] * w;
        return matrix;
    }

    Matrix div(double w) {
        if(w == 0)
            throw new RuntimeException("nie wolno dzilic przez 0");
        Matrix matrix = new Matrix(rows,cols);
        for (int i = 0; i < rows * cols; i++)
            matrix.data[i] = this.data[i] / w;
        return matrix;
    }

    //---------------------------- mnozenie macierzy
    Matrix Dot(Matrix m) {
        if (this.cols != m.GetRows()) {
            throw new RuntimeException("zly wymiar macierzy");
        }
        Matrix result = new Matrix(rows, m.GetCols());
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < m.GetCols(); j++) {
                double sum = 0;
                for (int k = 0; k < cols; k++) {
                    sum += this.data[i * cols + k] * m.data[k * m.GetCols() + j];
                }
                result.data[i * m.GetCols() + j] = sum;
            }
        }
        return result;
    }

    //-------------------------------- Frobenius
    double Frobenius(){
        double sum = 0;
        for (int i=0; i<rows * cols; i++){
                sum += pow(this.data[i],2);
        }
        return sum;
    }
    //------------------------------------- wypełnienie macierzy randomem
    public static Matrix Random(int rows, int cols){
        Matrix m = new Matrix(rows, cols);
        Random r = new Random();
        for(int i=0;i<m.rows;i++){
            for(int j=0;j<m.cols;j++){
                m.Set(i,j,r.nextDouble() * 10);
            }
        }
        return m;
    }
    //------------------------------------- tworzenie macierzy jednostkowej
    public static Matrix Eye(int n){
        Matrix m = new Matrix(n,n);
        for (int i=0;i<m.rows;i++){
            for (int j=0;j<m.cols;j++){
                if(j==i)
                    m.Set(i,j,1);
                else
                    m.Set(i,j,0);
            }
        }
        return m;
    }

    Matrix getColumn(int i){
        if(i >= this.GetCols() || i<0){
            throw new IllegalArgumentException("Error");
        }

        Matrix result = new Matrix(this.GetRows(),1);
        for (int j=0; j<this.GetRows(); j++){
            result.Set(j,0, Get(j,i));
        }
        return result;
    }

    Matrix sumRows(){
        Matrix result = new Matrix(1,this.GetCols());
        for(int c=0; c<this.GetCols(); c++){
            double value = 0;
            for(int r=0; r<this.GetRows(); r++){
                value += this.Get(r,c);
            }
            result.Set(0,c,value);
        }
        return result;
    }

    //michał mówi że to brzydkie podejście z bezppośrednimi zmiennymi
    Matrix sumRows1(){
        Matrix result = new Matrix(1,this.GetCols());
        for(int i=0; i<this.GetRows(); i++){
            for(int j=0; j<this.GetCols(); j++){
                result.data[j]=result.data[j] + this.data[i*cols + j];
            }
        }
        return result;
    }
}