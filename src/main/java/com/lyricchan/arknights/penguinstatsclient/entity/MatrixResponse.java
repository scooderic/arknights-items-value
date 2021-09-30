package com.lyricchan.arknights.penguinstatsclient.entity;

import java.util.List;

public class MatrixResponse implements java.io.Serializable {

    private List<Matrix> matrix;

    public List<Matrix> getMatrix() {
        return matrix;
    }

    public void setMatrix(List<Matrix> matrix) {
        this.matrix = matrix;
    }
}
