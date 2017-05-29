package Odczyt;

import Jama.Matrix;

import java.util.Arrays;

/**
 * Created by Linus on 22.03.2017.
 */
public class SprawdzSpojnosc {


    Matrix matrix;
    private final static double RI[] = {1,1, 1, 0.5247, 0.8816, 1.1086, 1.2479, 1.3417, 1.41, 1.45, 1.49};

    public SprawdzSpojnosc(double[][] macierz){
        matrix=new Matrix(macierz);
    }
    public SprawdzSpojnosc(Matrix matrix){
        this.matrix=matrix;
    }

    public double countErrorFactor(){
        double[] errorFactors = matrix.eig().getRealEigenvalues();
        double errorFactor = -10.0;
            System.out.println(">>"+Arrays.toString(errorFactors));
        for(double d : errorFactors){
            if(d>errorFactor){
                errorFactor = d;
            }
        }
      double blad= (errorFactor - (double) matrix.getRowDimension()) / (double) (matrix.getRowDimension() - 1);
        return blad/RI[matrix.getRowDimension()];
    }


}
