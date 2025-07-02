package javax.vecmath;

import java.io.Serializable;

public class GMatrix implements Serializable, Cloneable {

    static final long serialVersionUID = 2777097312029690941L;

    private static final boolean debug = false;

    int nRow;

    int nCol;

    double[][] values;

    private static final double EPS = 1.0E-10D;

    public GMatrix(int paramInt1, int paramInt2) {
        int i;

        this.values = new double[paramInt1][paramInt2];

        this.nRow = paramInt1;

        this.nCol = paramInt2;

        byte b;

        for (b = 0; b < paramInt1; b++) {
            for (byte b1 = 0; b1 < paramInt2; b1++)
            this.values[b][b1] = 0.0D;

        }

        if (paramInt1 < paramInt2) {
            i = paramInt1;

        } else {
            i = paramInt2;

        }

        for (b = 0; b < i; b++)
        this.values[b][b] = 1.0D;

    }

    public GMatrix(int paramInt1, int paramInt2, double[] paramArrayOfdouble) {
        this.values = new double[paramInt1][paramInt2];

        this.nRow = paramInt1;

        this.nCol = paramInt2;

        for (byte b = 0; b < paramInt1; b++) {
            for (byte b1 = 0; b1 < paramInt2; b1++)
            this.values[b][b1] = paramArrayOfdouble[b * paramInt2 + b1];

        }

    }

    public GMatrix(GMatrix paramGMatrix) {
        this.nRow = paramGMatrix.nRow;

        this.nCol = paramGMatrix.nCol;

        this.values = new double[this.nRow][this.nCol];

        for (byte b = 0; b < this.nRow; b++) {
            for (byte b1 = 0; b1 < this.nCol; b1++)
            this.values[b][b1] = paramGMatrix.values[b][b1];

        }

    }

    public final void mul(GMatrix paramGMatrix) {
        if (this.nCol != paramGMatrix.nRow || this.nCol != paramGMatrix.nCol)
        throw new MismatchedSizeException(VecMathI18N.getString("GMatrix0"));

        double[][] arrayOfDouble = new double[this.nRow][this.nCol];

        for (byte b = 0; b < this.nRow; b++) {
            for (byte b1 = 0; b1 < this.nCol; b1++) {
                arrayOfDouble[b][b1] = 0.0D;

                for (byte b2 = 0; b2 < this.nCol; b2++)
                arrayOfDouble[b][b1] = arrayOfDouble[b][b1] + this.values[b][b2] * paramGMatrix.values[b2][b1];

            }

        }

        this.values = arrayOfDouble;

    }

    public final void mul(GMatrix paramGMatrix1, GMatrix paramGMatrix2) {
        if (paramGMatrix1.nCol != paramGMatrix2.nRow || this.nRow != paramGMatrix1.nRow || this.nCol != paramGMatrix2.nCol)
        throw new MismatchedSizeException(VecMathI18N.getString("GMatrix1"));

        double[][] arrayOfDouble = new double[this.nRow][this.nCol];

        for (byte b = 0; b < paramGMatrix1.nRow; b++) {
            for (byte b1 = 0; b1 < paramGMatrix2.nCol; b1++) {
                arrayOfDouble[b][b1] = 0.0D;

                for (byte b2 = 0; b2 < paramGMatrix1.nCol; b2++)
                arrayOfDouble[b][b1] = arrayOfDouble[b][b1] + paramGMatrix1.values[b][b2] * paramGMatrix2.values[b2][b1];

            }

        }

        this.values = arrayOfDouble;

    }

    public final void mul(GVector paramGVector1, GVector paramGVector2) {
        if (this.nRow < paramGVector1.getSize())
        throw new MismatchedSizeException(VecMathI18N.getString("GMatrix2"));

        if (this.nCol < paramGVector2.getSize())
        throw new MismatchedSizeException(VecMathI18N.getString("GMatrix3"));

        for (byte b = 0; b < paramGVector1.getSize(); b++) {
            for (byte b1 = 0; b1 < paramGVector2.getSize(); b1++)
            this.values[b][b1] = paramGVector1.values[b] * paramGVector2.values[b1];

        }

    }

    public final void add(GMatrix paramGMatrix) {
        if (this.nRow != paramGMatrix.nRow)
        throw new MismatchedSizeException(VecMathI18N.getString("GMatrix4"));

        if (this.nCol != paramGMatrix.nCol)
        throw new MismatchedSizeException(VecMathI18N.getString("GMatrix5"));

        for (byte b = 0; b < this.nRow; b++) {
            for (byte b1 = 0; b1 < this.nCol; b1++)
            this.values[b][b1] = this.values[b][b1] + paramGMatrix.values[b][b1];

        }

    }

    public final void add(GMatrix paramGMatrix1, GMatrix paramGMatrix2) {
        if (paramGMatrix2.nRow != paramGMatrix1.nRow)
        throw new MismatchedSizeException(VecMathI18N.getString("GMatrix6"));

        if (paramGMatrix2.nCol != paramGMatrix1.nCol)
        throw new MismatchedSizeException(VecMathI18N.getString("GMatrix7"));

        if (this.nCol != paramGMatrix1.nCol || this.nRow != paramGMatrix1.nRow)
        throw new MismatchedSizeException(VecMathI18N.getString("GMatrix8"));

        for (byte b = 0; b < this.nRow; b++) {
            for (byte b1 = 0; b1 < this.nCol; b1++)
            this.values[b][b1] = paramGMatrix1.values[b][b1] + paramGMatrix2.values[b][b1];

        }

    }

    public final void sub(GMatrix paramGMatrix) {
        if (this.nRow != paramGMatrix.nRow)
        throw new MismatchedSizeException(VecMathI18N.getString("GMatrix9"));

        if (this.nCol != paramGMatrix.nCol)
        throw new MismatchedSizeException(VecMathI18N.getString("GMatrix28"));

        for (byte b = 0; b < this.nRow; b++) {
            for (byte b1 = 0; b1 < this.nCol; b1++)
            this.values[b][b1] = this.values[b][b1] - paramGMatrix.values[b][b1];

        }

    }

    public final void sub(GMatrix paramGMatrix1, GMatrix paramGMatrix2) {
        if (paramGMatrix2.nRow != paramGMatrix1.nRow)
        throw new MismatchedSizeException(VecMathI18N.getString("GMatrix10"));

        if (paramGMatrix2.nCol != paramGMatrix1.nCol)
        throw new MismatchedSizeException(VecMathI18N.getString("GMatrix11"));

        if (this.nRow != paramGMatrix1.nRow || this.nCol != paramGMatrix1.nCol)
        throw new MismatchedSizeException(VecMathI18N.getString("GMatrix12"));

        for (byte b = 0; b < this.nRow; b++) {
            for (byte b1 = 0; b1 < this.nCol; b1++)
            this.values[b][b1] = paramGMatrix1.values[b][b1] - paramGMatrix2.values[b][b1];

        }

    }

    public final void negate() {
        for (byte b = 0; b < this.nRow; b++) {
            for (byte b1 = 0; b1 < this.nCol; b1++)
            this.values[b][b1] = -this.values[b][b1];

        }

    }

    public final void negate(GMatrix paramGMatrix) {
        if (this.nRow != paramGMatrix.nRow || this.nCol != paramGMatrix.nCol)
        throw new MismatchedSizeException(VecMathI18N.getString("GMatrix13"));

        for (byte b = 0; b < this.nRow; b++) {
            for (byte b1 = 0; b1 < this.nCol; b1++)
            this.values[b][b1] = -paramGMatrix.values[b][b1];

        }

    }

    public final void setIdentity() {
        int i;

        byte b;

        for (b = 0; b < this.nRow; b++) {
            for (byte b1 = 0; b1 < this.nCol; b1++)
            this.values[b][b1] = 0.0D;

        }

        if (this.nRow < this.nCol) {
            i = this.nRow;

        } else {
            i = this.nCol;

        }

        for (b = 0; b < i; b++)
        this.values[b][b] = 1.0D;

    }

    public final void setZero() {
        for (byte b = 0; b < this.nRow; b++) {
            for (byte b1 = 0; b1 < this.nCol; b1++)
            this.values[b][b1] = 0.0D;

        }

    }

    public final void identityMinus() {
        int i;

        byte b;

        for (b = 0; b < this.nRow; b++) {
            for (byte b1 = 0; b1 < this.nCol; b1++)
            this.values[b][b1] = -this.values[b][b1];

        }

        if (this.nRow < this.nCol) {
            i = this.nRow;

        } else {
            i = this.nCol;

        }

        for (b = 0; b < i; b++)
        this.values[b][b] = this.values[b][b] + 1.0D;

    }

    public final void invert() {
        invertGeneral(this);

    }

    public final void invert(GMatrix paramGMatrix) {
        invertGeneral(paramGMatrix);

    }

    public final void copySubMatrix(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, GMatrix paramGMatrix) {
        if (this != paramGMatrix) {
            for (byte b = 0; b < paramInt3; b++) {
                for (byte b1 = 0; b1 < paramInt4; b1++)
                paramGMatrix.values[paramInt5 + b][paramInt6 + b1] = this.values[paramInt1 + b][paramInt2 + b1];

            }

        } else {
            double[][] arrayOfDouble = new double[paramInt3][paramInt4];

            byte b;

            for (b = 0; b < paramInt3; b++) {
                for (byte b1 = 0; b1 < paramInt4; b1++)
                arrayOfDouble[b][b1] = this.values[paramInt1 + b][paramInt2 + b1];

            }

            for (b = 0; b < paramInt3; b++) {
                for (byte b1 = 0; b1 < paramInt4; b1++)
                paramGMatrix.values[paramInt5 + b][paramInt6 + b1] = arrayOfDouble[b][b1];

            }

        }

    }

    public final void setSize(int paramInt1, int paramInt2) {
        int i;

        int j;

        double[][] arrayOfDouble = new double[paramInt1][paramInt2];

        if (this.nRow < paramInt1) {
            i = this.nRow;

        } else {
            i = paramInt1;

        }

        if (this.nCol < paramInt2) {
            j = this.nCol;

        } else {
            j = paramInt2;

        }

        for (byte b = 0; b < i; b++) {
            for (byte b1 = 0; b1 < j; b1++)
            arrayOfDouble[b][b1] = this.values[b][b1];

        }

        this.nRow = paramInt1;

        this.nCol = paramInt2;

        this.values = arrayOfDouble;

    }

    public final void set(double[] paramArrayOfdouble) {
        for (byte b = 0; b < this.nRow; b++) {
            for (byte b1 = 0; b1 < this.nCol; b1++)
            this.values[b][b1] = paramArrayOfdouble[this.nCol * b + b1];

        }

    }

    public final void set(Matrix3f paramMatrix3f) {
        if (this.nCol < 3 || this.nRow < 3) {
            this.nCol = 3;

            this.nRow = 3;

            this.values = new double[this.nRow][this.nCol];

        }

        this.values[0][0] = paramMatrix3f.m00;

        this.values[0][1] = paramMatrix3f.m01;

        this.values[0][2] = paramMatrix3f.m02;

        this.values[1][0] = paramMatrix3f.m10;

        this.values[1][1] = paramMatrix3f.m11;

        this.values[1][2] = paramMatrix3f.m12;

        this.values[2][0] = paramMatrix3f.m20;

        this.values[2][1] = paramMatrix3f.m21;

        this.values[2][2] = paramMatrix3f.m22;

        for (byte b = 3; b < this.nRow; b++) {
            for (byte b1 = 3; b1 < this.nCol; b1++)
            this.values[b][b1] = 0.0D;

        }

    }

    public final void set(Matrix3d paramMatrix3d) {
        if (this.nRow < 3 || this.nCol < 3) {
            this.values = new double[3][3];

            this.nRow = 3;

            this.nCol = 3;

        }

        this.values[0][0] = paramMatrix3d.m00;

        this.values[0][1] = paramMatrix3d.m01;

        this.values[0][2] = paramMatrix3d.m02;

        this.values[1][0] = paramMatrix3d.m10;

        this.values[1][1] = paramMatrix3d.m11;

        this.values[1][2] = paramMatrix3d.m12;

        this.values[2][0] = paramMatrix3d.m20;

        this.values[2][1] = paramMatrix3d.m21;

        this.values[2][2] = paramMatrix3d.m22;

        for (byte b = 3; b < this.nRow; b++) {
            for (byte b1 = 3; b1 < this.nCol; b1++)
            this.values[b][b1] = 0.0D;

        }

    }

    public final void set(Matrix4f paramMatrix4f) {
        if (this.nRow < 4 || this.nCol < 4) {
            this.values = new double[4][4];

            this.nRow = 4;

            this.nCol = 4;

        }

        this.values[0][0] = paramMatrix4f.m00;

        this.values[0][1] = paramMatrix4f.m01;

        this.values[0][2] = paramMatrix4f.m02;

        this.values[0][3] = paramMatrix4f.m03;

        this.values[1][0] = paramMatrix4f.m10;

        this.values[1][1] = paramMatrix4f.m11;

        this.values[1][2] = paramMatrix4f.m12;

        this.values[1][3] = paramMatrix4f.m13;

        this.values[2][0] = paramMatrix4f.m20;

        this.values[2][1] = paramMatrix4f.m21;

        this.values[2][2] = paramMatrix4f.m22;

        this.values[2][3] = paramMatrix4f.m23;

        this.values[3][0] = paramMatrix4f.m30;

        this.values[3][1] = paramMatrix4f.m31;

        this.values[3][2] = paramMatrix4f.m32;

        this.values[3][3] = paramMatrix4f.m33;

        for (byte b = 4; b < this.nRow; b++) {
            for (byte b1 = 4; b1 < this.nCol; b1++)
            this.values[b][b1] = 0.0D;

        }

    }

    public final void set(Matrix4d paramMatrix4d) {
        if (this.nRow < 4 || this.nCol < 4) {
            this.values = new double[4][4];

            this.nRow = 4;

            this.nCol = 4;

        }

        this.values[0][0] = paramMatrix4d.m00;

        this.values[0][1] = paramMatrix4d.m01;

        this.values[0][2] = paramMatrix4d.m02;

        this.values[0][3] = paramMatrix4d.m03;

        this.values[1][0] = paramMatrix4d.m10;

        this.values[1][1] = paramMatrix4d.m11;

        this.values[1][2] = paramMatrix4d.m12;

        this.values[1][3] = paramMatrix4d.m13;

        this.values[2][0] = paramMatrix4d.m20;

        this.values[2][1] = paramMatrix4d.m21;

        this.values[2][2] = paramMatrix4d.m22;

        this.values[2][3] = paramMatrix4d.m23;

        this.values[3][0] = paramMatrix4d.m30;

        this.values[3][1] = paramMatrix4d.m31;

        this.values[3][2] = paramMatrix4d.m32;

        this.values[3][3] = paramMatrix4d.m33;

        for (byte b = 4; b < this.nRow; b++) {
            for (byte b1 = 4; b1 < this.nCol; b1++)
            this.values[b][b1] = 0.0D;

        }

    }

    public final void set(GMatrix paramGMatrix) {
        if (this.nRow < paramGMatrix.nRow || this.nCol < paramGMatrix.nCol) {
            this.nRow = paramGMatrix.nRow;

            this.nCol = paramGMatrix.nCol;

            this.values = new double[this.nRow][this.nCol];

        }

        int i;

        for (i = 0; i < Math.min(this.nRow, paramGMatrix.nRow); i++) {
            for (byte b = 0; b < Math.min(this.nCol, paramGMatrix.nCol); b++)
            this.values[i][b] = paramGMatrix.values[i][b];

        }

        for (i = paramGMatrix.nRow; i < this.nRow; i++) {
            for (int j = paramGMatrix.nCol; j < this.nCol; j++)
            this.values[i][j] = 0.0D;

        }

    }

    public final int getNumRow() {
        return this.nRow;

    }

    public final int getNumCol() {
        return this.nCol;

    }

    public final double getElement(int paramInt1, int paramInt2) {
        return this.values[paramInt1][paramInt2];

    }

    public final void setElement(int paramInt1, int paramInt2, double paramDouble) {
        this.values[paramInt1][paramInt2] = paramDouble;

    }

    public final void getRow(int paramInt, double[] paramArrayOfdouble) {
        for (byte b = 0; b < this.nCol; b++)
        paramArrayOfdouble[b] = this.values[paramInt][b];

    }

    public final void getRow(int paramInt, GVector paramGVector) {
        if (paramGVector.getSize() < this.nCol)
        paramGVector.setSize(this.nCol);

        for (byte b = 0; b < this.nCol; b++)
        paramGVector.values[b] = this.values[paramInt][b];

    }

    public final void getColumn(int paramInt, double[] paramArrayOfdouble) {
        for (byte b = 0; b < this.nRow; b++)
        paramArrayOfdouble[b] = this.values[b][paramInt];

    }

    public final void getColumn(int paramInt, GVector paramGVector) {
        if (paramGVector.getSize() < this.nRow)
        paramGVector.setSize(this.nRow);

        for (byte b = 0; b < this.nRow; b++)
        paramGVector.values[b] = this.values[b][paramInt];

    }

    public final void get(Matrix3d paramMatrix3d) {
        if (this.nRow < 3 || this.nCol < 3) {
            paramMatrix3d.setZero();

            if (this.nCol > 0) {
                if (this.nRow > 0) {
                    paramMatrix3d.m00 = this.values[0][0];

                    if (this.nRow > 1) {
                        paramMatrix3d.m10 = this.values[1][0];

                        if (this.nRow > 2)
                        paramMatrix3d.m20 = this.values[2][0];

                    }

                }

                if (this.nCol > 1) {
                    if (this.nRow > 0) {
                        paramMatrix3d.m01 = this.values[0][1];

                        if (this.nRow > 1) {
                            paramMatrix3d.m11 = this.values[1][1];

                            if (this.nRow > 2)
                            paramMatrix3d.m21 = this.values[2][1];

                        }

                    }

                    if (this.nCol > 2 && this.nRow > 0) {
                        paramMatrix3d.m02 = this.values[0][2];

                        if (this.nRow > 1) {
                            paramMatrix3d.m12 = this.values[1][2];

                            if (this.nRow > 2)
                            paramMatrix3d.m22 = this.values[2][2];

                        }

                    }

                }

            }

        } else {
            paramMatrix3d.m00 = this.values[0][0];

            paramMatrix3d.m01 = this.values[0][1];

            paramMatrix3d.m02 = this.values[0][2];

            paramMatrix3d.m10 = this.values[1][0];

            paramMatrix3d.m11 = this.values[1][1];

            paramMatrix3d.m12 = this.values[1][2];

            paramMatrix3d.m20 = this.values[2][0];

            paramMatrix3d.m21 = this.values[2][1];

            paramMatrix3d.m22 = this.values[2][2];

        }

    }

    public final void get(Matrix3f paramMatrix3f) {
        if (this.nRow < 3 || this.nCol < 3) {
            paramMatrix3f.setZero();

            if (this.nCol > 0) {
                if (this.nRow > 0) {
                    paramMatrix3f.m00 = (float)this.values[0][0];

                    if (this.nRow > 1) {
                        paramMatrix3f.m10 = (float)this.values[1][0];

                        if (this.nRow > 2)
                        paramMatrix3f.m20 = (float)this.values[2][0];

                    }

                }

                if (this.nCol > 1) {
                    if (this.nRow > 0) {
                        paramMatrix3f.m01 = (float)this.values[0][1];

                        if (this.nRow > 1) {
                            paramMatrix3f.m11 = (float)this.values[1][1];

                            if (this.nRow > 2)
                            paramMatrix3f.m21 = (float)this.values[2][1];

                        }

                    }

                    if (this.nCol > 2 && this.nRow > 0) {
                        paramMatrix3f.m02 = (float)this.values[0][2];

                        if (this.nRow > 1) {
                            paramMatrix3f.m12 = (float)this.values[1][2];

                            if (this.nRow > 2)
                            paramMatrix3f.m22 = (float)this.values[2][2];

                        }

                    }

                }

            }

        } else {
            paramMatrix3f.m00 = (float)this.values[0][0];

            paramMatrix3f.m01 = (float)this.values[0][1];

            paramMatrix3f.m02 = (float)this.values[0][2];

            paramMatrix3f.m10 = (float)this.values[1][0];

            paramMatrix3f.m11 = (float)this.values[1][1];

            paramMatrix3f.m12 = (float)this.values[1][2];

            paramMatrix3f.m20 = (float)this.values[2][0];

            paramMatrix3f.m21 = (float)this.values[2][1];

            paramMatrix3f.m22 = (float)this.values[2][2];

        }

    }

    public final void get(Matrix4d paramMatrix4d) {
        if (this.nRow < 4 || this.nCol < 4) {
            paramMatrix4d.setZero();

            if (this.nCol > 0) {
                if (this.nRow > 0) {
                    paramMatrix4d.m00 = this.values[0][0];

                    if (this.nRow > 1) {
                        paramMatrix4d.m10 = this.values[1][0];

                        if (this.nRow > 2) {
                            paramMatrix4d.m20 = this.values[2][0];

                            if (this.nRow > 3)
                            paramMatrix4d.m30 = this.values[3][0];

                        }

                    }

                }

                if (this.nCol > 1) {
                    if (this.nRow > 0) {
                        paramMatrix4d.m01 = this.values[0][1];

                        if (this.nRow > 1) {
                            paramMatrix4d.m11 = this.values[1][1];

                            if (this.nRow > 2) {
                                paramMatrix4d.m21 = this.values[2][1];

                                if (this.nRow > 3)
                                paramMatrix4d.m31 = this.values[3][1];

                            }

                        }

                    }

                    if (this.nCol > 2) {
                        if (this.nRow > 0) {
                            paramMatrix4d.m02 = this.values[0][2];

                            if (this.nRow > 1) {
                                paramMatrix4d.m12 = this.values[1][2];

                                if (this.nRow > 2) {
                                    paramMatrix4d.m22 = this.values[2][2];

                                    if (this.nRow > 3)
                                    paramMatrix4d.m32 = this.values[3][2];

                                }

                            }

                        }

                        if (this.nCol > 3 && this.nRow > 0) {
                            paramMatrix4d.m03 = this.values[0][3];

                            if (this.nRow > 1) {
                                paramMatrix4d.m13 = this.values[1][3];

                                if (this.nRow > 2) {
                                    paramMatrix4d.m23 = this.values[2][3];

                                    if (this.nRow > 3)
                                    paramMatrix4d.m33 = this.values[3][3];

                                }

                            }

                        }

                    }

                }

            }

        } else {
            paramMatrix4d.m00 = this.values[0][0];

            paramMatrix4d.m01 = this.values[0][1];

            paramMatrix4d.m02 = this.values[0][2];

            paramMatrix4d.m03 = this.values[0][3];

            paramMatrix4d.m10 = this.values[1][0];

            paramMatrix4d.m11 = this.values[1][1];

            paramMatrix4d.m12 = this.values[1][2];

            paramMatrix4d.m13 = this.values[1][3];

            paramMatrix4d.m20 = this.values[2][0];

            paramMatrix4d.m21 = this.values[2][1];

            paramMatrix4d.m22 = this.values[2][2];

            paramMatrix4d.m23 = this.values[2][3];

            paramMatrix4d.m30 = this.values[3][0];

            paramMatrix4d.m31 = this.values[3][1];

            paramMatrix4d.m32 = this.values[3][2];

            paramMatrix4d.m33 = this.values[3][3];

        }

    }

    public final void get(Matrix4f paramMatrix4f) {
        if (this.nRow < 4 || this.nCol < 4) {
            paramMatrix4f.setZero();

            if (this.nCol > 0) {
                if (this.nRow > 0) {
                    paramMatrix4f.m00 = (float)this.values[0][0];

                    if (this.nRow > 1) {
                        paramMatrix4f.m10 = (float)this.values[1][0];

                        if (this.nRow > 2) {
                            paramMatrix4f.m20 = (float)this.values[2][0];

                            if (this.nRow > 3)
                            paramMatrix4f.m30 = (float)this.values[3][0];

                        }

                    }

                }

                if (this.nCol > 1) {
                    if (this.nRow > 0) {
                        paramMatrix4f.m01 = (float)this.values[0][1];

                        if (this.nRow > 1) {
                            paramMatrix4f.m11 = (float)this.values[1][1];

                            if (this.nRow > 2) {
                                paramMatrix4f.m21 = (float)this.values[2][1];

                                if (this.nRow > 3)
                                paramMatrix4f.m31 = (float)this.values[3][1];

                            }

                        }

                    }

                    if (this.nCol > 2) {
                        if (this.nRow > 0) {
                            paramMatrix4f.m02 = (float)this.values[0][2];

                            if (this.nRow > 1) {
                                paramMatrix4f.m12 = (float)this.values[1][2];

                                if (this.nRow > 2) {
                                    paramMatrix4f.m22 = (float)this.values[2][2];

                                    if (this.nRow > 3)
                                    paramMatrix4f.m32 = (float)this.values[3][2];

                                }

                            }

                        }

                        if (this.nCol > 3 && this.nRow > 0) {
                            paramMatrix4f.m03 = (float)this.values[0][3];

                            if (this.nRow > 1) {
                                paramMatrix4f.m13 = (float)this.values[1][3];

                                if (this.nRow > 2) {
                                    paramMatrix4f.m23 = (float)this.values[2][3];

                                    if (this.nRow > 3)
                                    paramMatrix4f.m33 = (float)this.values[3][3];

                                }

                            }

                        }

                    }

                }

            }

        } else {
            paramMatrix4f.m00 = (float)this.values[0][0];

            paramMatrix4f.m01 = (float)this.values[0][1];

            paramMatrix4f.m02 = (float)this.values[0][2];

            paramMatrix4f.m03 = (float)this.values[0][3];

            paramMatrix4f.m10 = (float)this.values[1][0];

            paramMatrix4f.m11 = (float)this.values[1][1];

            paramMatrix4f.m12 = (float)this.values[1][2];

            paramMatrix4f.m13 = (float)this.values[1][3];

            paramMatrix4f.m20 = (float)this.values[2][0];

            paramMatrix4f.m21 = (float)this.values[2][1];

            paramMatrix4f.m22 = (float)this.values[2][2];

            paramMatrix4f.m23 = (float)this.values[2][3];

            paramMatrix4f.m30 = (float)this.values[3][0];

            paramMatrix4f.m31 = (float)this.values[3][1];

            paramMatrix4f.m32 = (float)this.values[3][2];

            paramMatrix4f.m33 = (float)this.values[3][3];

        }

    }

    public final void get(GMatrix paramGMatrix) {
        int k;

        int m;

        if (this.nCol < paramGMatrix.nCol) {
            k = this.nCol;

        } else {
            k = paramGMatrix.nCol;

        }

        if (this.nRow < paramGMatrix.nRow) {
            m = this.nRow;

        } else {
            m = paramGMatrix.nRow;

        }

        int i;

        for (i = 0; i < m; i++) {
            for (byte b = 0; b < k; b++)
            paramGMatrix.values[i][b] = this.values[i][b];

        }

        for (i = m; i < paramGMatrix.nRow; i++) {
            for (byte b = 0; b < paramGMatrix.nCol; b++)
            paramGMatrix.values[i][b] = 0.0D;

        }

        for (int j = k; j < paramGMatrix.nCol; j++) {
            for (i = 0; i < m; i++)
            paramGMatrix.values[i][j] = 0.0D;

        }

    }

    public final void setRow(int paramInt, double[] paramArrayOfdouble) {
        for (byte b = 0; b < this.nCol; b++)
        this.values[paramInt][b] = paramArrayOfdouble[b];

    }

    public final void setRow(int paramInt, GVector paramGVector) {
        for (byte b = 0; b < this.nCol; b++)
        this.values[paramInt][b] = paramGVector.values[b];

    }

    public final void setColumn(int paramInt, double[] paramArrayOfdouble) {
        for (byte b = 0; b < this.nRow; b++)
        this.values[b][paramInt] = paramArrayOfdouble[b];

    }

    public final void setColumn(int paramInt, GVector paramGVector) {
        for (byte b = 0; b < this.nRow; b++)
        this.values[b][paramInt] = paramGVector.values[b];

    }

    public final void mulTransposeBoth(GMatrix paramGMatrix1, GMatrix paramGMatrix2) {
        if (paramGMatrix1.nRow != paramGMatrix2.nCol || this.nRow != paramGMatrix1.nCol || this.nCol != paramGMatrix2.nRow)
        throw new MismatchedSizeException(VecMathI18N.getString("GMatrix14"));

        if (paramGMatrix1 == this || paramGMatrix2 == this) {
            double[][] arrayOfDouble = new double[this.nRow][this.nCol];

            for (byte b = 0; b < this.nRow; b++) {
                for (byte b1 = 0; b1 < this.nCol; b1++) {
                    arrayOfDouble[b][b1] = 0.0D;

                    for (byte b2 = 0; b2 < paramGMatrix1.nRow; b2++)
                    arrayOfDouble[b][b1] = arrayOfDouble[b][b1] + paramGMatrix1.values[b2][b] * paramGMatrix2.values[b1][b2];

                }

            }

            this.values = arrayOfDouble;

        } else {
            for (byte b = 0; b < this.nRow; b++) {
                for (byte b1 = 0; b1 < this.nCol; b1++) {
                    this.values[b][b1] = 0.0D;

                    for (byte b2 = 0; b2 < paramGMatrix1.nRow; b2++)
                    this.values[b][b1] = this.values[b][b1] + paramGMatrix1.values[b2][b] * paramGMatrix2.values[b1][b2];

                }

            }

        }

    }

    public final void mulTransposeRight(GMatrix paramGMatrix1, GMatrix paramGMatrix2) {
        if (paramGMatrix1.nCol != paramGMatrix2.nCol || this.nCol != paramGMatrix2.nRow || this.nRow != paramGMatrix1.nRow)
        throw new MismatchedSizeException(VecMathI18N.getString("GMatrix15"));

        if (paramGMatrix1 == this || paramGMatrix2 == this) {
            double[][] arrayOfDouble = new double[this.nRow][this.nCol];

            for (byte b = 0; b < this.nRow; b++) {
                for (byte b1 = 0; b1 < this.nCol; b1++) {
                    arrayOfDouble[b][b1] = 0.0D;

                    for (byte b2 = 0; b2 < paramGMatrix1.nCol; b2++)
                    arrayOfDouble[b][b1] = arrayOfDouble[b][b1] + paramGMatrix1.values[b][b2] * paramGMatrix2.values[b1][b2];

                }

            }

            this.values = arrayOfDouble;

        } else {
            for (byte b = 0; b < this.nRow; b++) {
                for (byte b1 = 0; b1 < this.nCol; b1++) {
                    this.values[b][b1] = 0.0D;

                    for (byte b2 = 0; b2 < paramGMatrix1.nCol; b2++)
                    this.values[b][b1] = this.values[b][b1] + paramGMatrix1.values[b][b2] * paramGMatrix2.values[b1][b2];

                }

            }

        }

    }

    public final void mulTransposeLeft(GMatrix paramGMatrix1, GMatrix paramGMatrix2) {
        if (paramGMatrix1.nRow != paramGMatrix2.nRow || this.nCol != paramGMatrix2.nCol || this.nRow != paramGMatrix1.nCol)
        throw new MismatchedSizeException(VecMathI18N.getString("GMatrix16"));

        if (paramGMatrix1 == this || paramGMatrix2 == this) {
            double[][] arrayOfDouble = new double[this.nRow][this.nCol];

            for (byte b = 0; b < this.nRow; b++) {
                for (byte b1 = 0; b1 < this.nCol; b1++) {
                    arrayOfDouble[b][b1] = 0.0D;

                    for (byte b2 = 0; b2 < paramGMatrix1.nRow; b2++)
                    arrayOfDouble[b][b1] = arrayOfDouble[b][b1] + paramGMatrix1.values[b2][b] * paramGMatrix2.values[b2][b1];

                }

            }

            this.values = arrayOfDouble;

        } else {
            for (byte b = 0; b < this.nRow; b++) {
                for (byte b1 = 0; b1 < this.nCol; b1++) {
                    this.values[b][b1] = 0.0D;

                    for (byte b2 = 0; b2 < paramGMatrix1.nRow; b2++)
                    this.values[b][b1] = this.values[b][b1] + paramGMatrix1.values[b2][b] * paramGMatrix2.values[b2][b1];

                }

            }

        }

    }

    public final void transpose() {
        if (this.nRow != this.nCol) {
            int i = this.nRow;

            this.nRow = this.nCol;

            this.nCol = i;

            double[][] arrayOfDouble = new double[this.nRow][this.nCol];

            for (i = 0; i < this.nRow; i++) {
                for (byte b = 0; b < this.nCol; b++)
                arrayOfDouble[i][b] = this.values[b][i];

            }

            this.values = arrayOfDouble;

        } else {
            for (byte b = 0; b < this.nRow; b++) {
                for (byte b1 = 0; b1 < b; b1++) {
                    double d = this.values[b][b1];

                    this.values[b][b1] = this.values[b1][b];

                    this.values[b1][b] = d;

                }

            }

        }

    }

    public final void transpose(GMatrix paramGMatrix) {
        if (this.nRow != paramGMatrix.nCol || this.nCol != paramGMatrix.nRow)
        throw new MismatchedSizeException(VecMathI18N.getString("GMatrix17"));

        if (paramGMatrix != this) {
            for (byte b = 0; b < this.nRow; b++) {
                for (byte b1 = 0; b1 < this.nCol; b1++)
                this.values[b][b1] = paramGMatrix.values[b1][b];

            }

        } else {
            transpose();

        }

    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(this.nRow * this.nCol * 8);

        for (byte b = 0; b < this.nRow; b++) {
            for (byte b1 = 0; b1 < this.nCol; b1++)
            stringBuffer.append(this.values[b][b1]).append(" ");

            stringBuffer.append("\n");

        }

        return stringBuffer.toString();

    }

    private static void checkMatrix(GMatrix paramGMatrix) {
        for (byte b = 0; b < paramGMatrix.nRow; b++) {
            for (byte b1 = 0; b1 < paramGMatrix.nCol; b1++) {
                if (Math.abs(paramGMatrix.values[b][b1]) < 1.0E-10D) {
                    System.out.print(" 0.0     ");

                } else {
                    System.out.print(" " + paramGMatrix.values[b][b1]);

                }

            }

            System.out.print("\n");

        }

    }

    public int hashCode() {
        long l = 1L;

        l = 31L * l + this.nRow;

        l = 31L * l + this.nCol;

        for (byte b = 0; b < this.nRow; b++) {
            for (byte b1 = 0; b1 < this.nCol; b1++)
            l = 31L * l + VecMathUtil.doubleToLongBits(this.values[b][b1]);

        }

        return (int)(l ^ l >> 32L);

    }

    public boolean equals(GMatrix paramGMatrix) {
        try {
            if (this.nRow != paramGMatrix.nRow || this.nCol != paramGMatrix.nCol)
            return false;

            for (byte b = 0; b < this.nRow; b++) {
                for (byte b1 = 0; b1 < this.nCol; b1++) {
                    if (this.values[b][b1] != paramGMatrix.values[b][b1])
                    return false;

                }

            }

            return true;

        } catch (NullPointerException nullPointerException) {
            return false;

        }

    }

    public boolean equals(Object paramObject) {
        try {
            GMatrix gMatrix = (GMatrix)paramObject;

            if (this.nRow != gMatrix.nRow || this.nCol != gMatrix.nCol)
            return false;

            for (byte b = 0; b < this.nRow; b++) {
                for (byte b1 = 0; b1 < this.nCol; b1++) {
                    if (this.values[b][b1] != gMatrix.values[b][b1])
                    return false;

                }

            }

            return true;

        } catch (ClassCastException classCastException) {
            return false;

        } catch (NullPointerException nullPointerException) {
            return false;

        }

    }

    public boolean epsilonEquals(GMatrix paramGMatrix, float paramFloat) {
        return epsilonEquals(paramGMatrix, paramFloat);

    }

    public boolean epsilonEquals(GMatrix paramGMatrix, double paramDouble) {
        if (this.nRow != paramGMatrix.nRow || this.nCol != paramGMatrix.nCol)
        return false;

        for (byte b = 0; b < this.nRow; b++) {
            for (byte b1 = 0; b1 < this.nCol; b1++) {
                double d = this.values[b][b1] - paramGMatrix.values[b][b1];

                if (((d < 0.0D) ? -d : d) > paramDouble)
                return false;

            }

        }

        return true;

    }

    public final double trace() {
        int i;

        if (this.nRow < this.nCol) {
            i = this.nRow;

        } else {
            i = this.nCol;

        }

        double d = 0.0D;

        for (byte b = 0; b < i; b++)
        d += this.values[b][b];

        return d;

    }

    public final int SVD(GMatrix paramGMatrix1, GMatrix paramGMatrix2, GMatrix paramGMatrix3) {
        if (this.nCol != paramGMatrix3.nCol || this.nCol != paramGMatrix3.nRow)
        throw new MismatchedSizeException(VecMathI18N.getString("GMatrix18"));

        if (this.nRow != paramGMatrix1.nRow || this.nRow != paramGMatrix1.nCol)
        throw new MismatchedSizeException(VecMathI18N.getString("GMatrix25"));

        if (this.nRow != paramGMatrix2.nRow || this.nCol != paramGMatrix2.nCol)
        throw new MismatchedSizeException(VecMathI18N.getString("GMatrix26"));

        if (this.nRow == 2 && this.nCol == 2 && this.values[1][0] == 0.0D) {
            paramGMatrix1.setIdentity();

            paramGMatrix3.setIdentity();

            if (this.values[0][1] == 0.0D)
            return 2;

            double[] arrayOfDouble1 = new double[1];

            double[] arrayOfDouble2 = new double[1];

            double[] arrayOfDouble3 = new double[1];

            double[] arrayOfDouble4 = new double[1];

            double[] arrayOfDouble5 = new double[2];

            arrayOfDouble5[0] = this.values[0][0];

            arrayOfDouble5[1] = this.values[1][1];

            compute_2X2(this.values[0][0], this.values[0][1], this.values[1][1], arrayOfDouble5, arrayOfDouble1, arrayOfDouble3, arrayOfDouble2, arrayOfDouble4, 0);

            update_u(0, paramGMatrix1, arrayOfDouble3, arrayOfDouble1);

            update_v(0, paramGMatrix3, arrayOfDouble4, arrayOfDouble2);

            return 2;

        }

        return computeSVD(this, paramGMatrix1, paramGMatrix2, paramGMatrix3);

    }

    public final int LUD(GMatrix paramGMatrix, GVector paramGVector) {
        int i = paramGMatrix.nRow * paramGMatrix.nCol;

        double[] arrayOfDouble = new double[i];

        int[] arrayOfInt1 = new int[1];

        int[] arrayOfInt2 = new int[paramGMatrix.nRow];

        if (this.nRow != this.nCol)
        throw new MismatchedSizeException(VecMathI18N.getString("GMatrix19"));

        if (this.nRow != paramGMatrix.nRow)
        throw new MismatchedSizeException(VecMathI18N.getString("GMatrix27"));

        if (this.nCol != paramGMatrix.nCol)
        throw new MismatchedSizeException(VecMathI18N.getString("GMatrix27"));

        if (paramGMatrix.nRow != paramGVector.getSize())
        throw new MismatchedSizeException(VecMathI18N.getString("GMatrix20"));

        byte b;

        for (b = 0; b < this.nRow; b++) {
            for (byte b1 = 0; b1 < this.nCol; b1++)
            arrayOfDouble[b * this.nCol + b1] = this.values[b][b1];

        }

        if (!luDecomposition(paramGMatrix.nRow, arrayOfDouble, arrayOfInt2, arrayOfInt1))
        throw new SingularMatrixException(VecMathI18N.getString("GMatrix21"));

        for (b = 0; b < this.nRow; b++) {
            for (byte b1 = 0; b1 < this.nCol; b1++)
            paramGMatrix.values[b][b1] = arrayOfDouble[b * this.nCol + b1];

        }

        for (b = 0; b < paramGMatrix.nRow; b++)
        paramGVector.values[b] = arrayOfInt2[b];

        return arrayOfInt1[0];

    }

    public final void setScale(double paramDouble) {
        int i;

        if (this.nRow < this.nCol) {
            i = this.nRow;

        } else {
            i = this.nCol;

        }

        byte b;

        for (b = 0; b < this.nRow; b++) {
            for (byte b1 = 0; b1 < this.nCol; b1++)
            this.values[b][b1] = 0.0D;

        }

        for (b = 0; b < i; b++)
        this.values[b][b] = paramDouble;

    }

    final void invertGeneral(GMatrix paramGMatrix) {
        int i = paramGMatrix.nRow * paramGMatrix.nCol;

        double[] arrayOfDouble1 = new double[i];

        double[] arrayOfDouble2 = new double[i];

        int[] arrayOfInt1 = new int[paramGMatrix.nRow];

        int[] arrayOfInt2 = new int[1];

        if (paramGMatrix.nRow != paramGMatrix.nCol)
        throw new MismatchedSizeException(VecMathI18N.getString("GMatrix22"));

        byte b;

        for (b = 0; b < this.nRow; b++) {
            for (byte b1 = 0; b1 < this.nCol; b1++)
            arrayOfDouble1[b * this.nCol + b1] = paramGMatrix.values[b][b1];

        }

        if (!luDecomposition(paramGMatrix.nRow, arrayOfDouble1, arrayOfInt1, arrayOfInt2))
        throw new SingularMatrixException(VecMathI18N.getString("GMatrix21"));

        for (b = 0; b < i; b++)
        arrayOfDouble2[b] = 0.0D;

        for (b = 0; b < this.nCol; b++)
        arrayOfDouble2[b + b * this.nCol] = 1.0D;

        luBacksubstitution(paramGMatrix.nRow, arrayOfDouble1, arrayOfInt1, arrayOfDouble2);

        for (b = 0; b < this.nRow; b++) {
            for (byte b1 = 0; b1 < this.nCol; b1++)
            this.values[b][b1] = arrayOfDouble2[b * this.nCol + b1];

        }

    }

    static boolean luDecomposition(int paramInt, double[] paramArrayOfdouble, int[] paramArrayOfint1, int[] paramArrayOfint2) {
        double[] arrayOfDouble = new double[paramInt];

        byte b2 = 0;

        byte b3 = 0;

        paramArrayOfint2[0] = 1;

        int i = paramInt;

        while (i-- != 0) {
            double d = 0.0D;

            int j = paramInt;

            while (j-- != 0) {
                double d1 = paramArrayOfdouble[b2++];

                d1 = Math.abs(d1);

                if (d1 > d)
                d = d1;

            }

            if (d == 0.0D)
            return false;

            arrayOfDouble[b3++] = 1.0D / d;

        }

        byte b4 = 0;

        for (byte b1 = 0; b1 < paramInt; b1++) {
            for (i = 0; i < b1; i++) {
                int m = b4 + paramInt * i + b1;

                double d1 = paramArrayOfdouble[m];

                int k = i;

                int n = b4 + paramInt * i;

                int i1;

                for (i1 = b4 + b1; k-- != 0; i1 += paramInt) {
                    d1 -= paramArrayOfdouble[n] * paramArrayOfdouble[i1];

                    n++;

                }

                paramArrayOfdouble[m] = d1;

            }

            double d = 0.0D;

            int j = -1;

            for (i = b1; i < paramInt; i++) {
                int m = b4 + paramInt * i + b1;

                double d2 = paramArrayOfdouble[m];

                int k = b1;

                int n = b4 + paramInt * i;

                int i1;

                for (i1 = b4 + b1; k-- != 0; i1 += paramInt) {
                    d2 -= paramArrayOfdouble[n] * paramArrayOfdouble[i1];

                    n++;

                }

                paramArrayOfdouble[m] = d2;

                double d1;

                if ((d1 = arrayOfDouble[i] * Math.abs(d2)) >= d) {
                    d = d1;

                    j = i;

                }

            }

            if (j < 0)
            throw new RuntimeException(VecMathI18N.getString("GMatrix24"));

            if (b1 != j) {
                int k = paramInt;

                int m = b4 + paramInt * j;

                int n = b4 + paramInt * b1;

                while (k-- != 0) {
                    double d1 = paramArrayOfdouble[m];

                    paramArrayOfdouble[m++] = paramArrayOfdouble[n];

                    paramArrayOfdouble[n++] = d1;

                }

                arrayOfDouble[j] = arrayOfDouble[b1];

                paramArrayOfint2[0] = -paramArrayOfint2[0];

            }

            paramArrayOfint1[b1] = j;

            if (paramArrayOfdouble[b4 + paramInt * b1 + b1] == 0.0D)
            return false;

            if (b1 != paramInt - 1) {
                double d1 = 1.0D / paramArrayOfdouble[b4 + paramInt * b1 + b1];

                int k = b4 + paramInt * (b1 + 1) + b1;

                i = paramInt - 1 - b1;

                while (i-- != 0) {
                    paramArrayOfdouble[k] = paramArrayOfdouble[k] * d1;

                    k += paramInt;

                }

            }

        }

        return true;

    }

    static void luBacksubstitution(int paramInt, double[] paramArrayOfdouble1, int[] paramArrayOfint, double[] paramArrayOfdouble2) {
        byte b2 = 0;

        for (byte b1 = 0; b1 < paramInt; b1++) {
            byte b4 = b1;

            byte b = -1;

            byte b3;

            for (b3 = 0; b3 < paramInt; b3++) {
                int i = paramArrayOfint[b2 + b3];

                double d = paramArrayOfdouble2[b4 + paramInt * i];

                paramArrayOfdouble2[b4 + paramInt * i] = paramArrayOfdouble2[b4 + paramInt * b3];

                if (b >= 0) {
                    int j = b3 * paramInt;

                    for (byte b5 = b; b5 <= b3 - 1; b5++)
                    d -= paramArrayOfdouble1[j + b5] * paramArrayOfdouble2[b4 + paramInt * b5];

                } else if (d != 0.0D) {
                    b = b3;

                }

                paramArrayOfdouble2[b4 + paramInt * b3] = d;

            }

            for (b3 = 0; b3 < paramInt; b3++) {
                int j = paramInt - 1 - b3;

                int i = paramInt * j;

                double d = 0.0D;

                for (byte b5 = 1; b5 <= b3; b5++)
                d += paramArrayOfdouble1[i + paramInt - b5] * paramArrayOfdouble2[b4 + paramInt * (paramInt - b5)];

                paramArrayOfdouble2[b4 + paramInt * j] = (paramArrayOfdouble2[b4 + paramInt * j] - d) / paramArrayOfdouble1[i + j];

            }

        }

    }

    static int computeSVD(GMatrix paramGMatrix1, GMatrix paramGMatrix2, GMatrix paramGMatrix3, GMatrix paramGMatrix4) {
        int k;

        int m;

        int n;

        GMatrix gMatrix1 = new GMatrix(paramGMatrix1.nRow, paramGMatrix1.nCol);

        GMatrix gMatrix2 = new GMatrix(paramGMatrix1.nRow, paramGMatrix1.nCol);

        GMatrix gMatrix3 = new GMatrix(paramGMatrix1.nRow, paramGMatrix1.nCol);

        GMatrix gMatrix4 = new GMatrix(paramGMatrix1);

        if (gMatrix4.nRow >= gMatrix4.nCol) {
            m = gMatrix4.nCol;

            k = gMatrix4.nCol - 1;

        } else {
            m = gMatrix4.nRow;

            k = gMatrix4.nRow;

        }

        if (gMatrix4.nRow > gMatrix4.nCol) {
            n = gMatrix4.nRow;

        } else {
            n = gMatrix4.nCol;

        }

        double[] arrayOfDouble1 = new double[n];

        double[] arrayOfDouble2 = new double[m];

        double[] arrayOfDouble3 = new double[k];

        paramGMatrix2.setIdentity();

        paramGMatrix4.setIdentity();

        int i = gMatrix4.nRow;

        int j = gMatrix4.nCol;

        for (byte b2 = 0; b2 < m; b2++) {
            if (i > 1) {
                double d1 = 0.0D;

                byte b3;

                for (b3 = 0; b3 < i; b3++)
                d1 += gMatrix4.values[b3 + b2][b2] * gMatrix4.values[b3 + b2][b2];

                d1 = Math.sqrt(d1);

                if (gMatrix4.values[b2][b2] == 0.0D) {
                    arrayOfDouble1[0] = d1;

                } else {
                    arrayOfDouble1[0] = gMatrix4.values[b2][b2] + d_sign(d1, gMatrix4.values[b2][b2]);

                }

                for (b3 = 1; b3 < i; b3++)
                arrayOfDouble1[b3] = gMatrix4.values[b2 + b3][b2];

                double d2 = 0.0D;

                for (b3 = 0; b3 < i; b3++)
                d2 += arrayOfDouble1[b3] * arrayOfDouble1[b3];

                d2 = 2.0D / d2;

                byte b4;

                for (b4 = b2; b4 < gMatrix4.nRow; b4++) {
                    for (byte b = b2; b < gMatrix4.nRow; b++)
                    gMatrix2.values[b4][b] = -d2 * arrayOfDouble1[b4 - b2] * arrayOfDouble1[b - b2];

                }

                for (b3 = b2; b3 < gMatrix4.nRow; b3++)
                gMatrix2.values[b3][b3] = gMatrix2.values[b3][b3] + 1.0D;

                double d3 = 0.0D;

                for (b3 = b2; b3 < gMatrix4.nRow; b3++)
                d3 += gMatrix2.values[b2][b3] * gMatrix4.values[b3][b2];

                gMatrix4.values[b2][b2] = d3;

                for (b4 = b2; b4 < gMatrix4.nRow; b4++) {
                    for (int i1 = b2 + 1; i1 < gMatrix4.nCol; i1++) {
                        gMatrix1.values[b4][i1] = 0.0D;

                        for (b3 = b2; b3 < gMatrix4.nCol; b3++)
                        gMatrix1.values[b4][i1] = gMatrix1.values[b4][i1] + gMatrix2.values[b4][b3] * gMatrix4.values[b3][i1];

                    }

                }

                for (b4 = b2; b4 < gMatrix4.nRow; b4++) {
                    for (int i1 = b2 + 1; i1 < gMatrix4.nCol; i1++)
                    gMatrix4.values[b4][i1] = gMatrix1.values[b4][i1];

                }

                for (b4 = b2; b4 < gMatrix4.nRow; b4++) {
                    for (byte b = 0; b < gMatrix4.nCol; b++) {
                        gMatrix1.values[b4][b] = 0.0D;

                        for (b3 = b2; b3 < gMatrix4.nCol; b3++)
                        gMatrix1.values[b4][b] = gMatrix1.values[b4][b] + gMatrix2.values[b4][b3] * paramGMatrix2.values[b3][b];

                    }

                }

                for (b4 = b2; b4 < gMatrix4.nRow; b4++) {
                    for (byte b = 0; b < gMatrix4.nCol; b++)
                    paramGMatrix2.values[b4][b] = gMatrix1.values[b4][b];

                }

                i--;

            }

            if (j > 2) {
                double d1 = 0.0D;

                int i1;

                for (i1 = 1; i1 < j; i1++)
                d1 += gMatrix4.values[b2][b2 + i1] * gMatrix4.values[b2][b2 + i1];

                d1 = Math.sqrt(d1);

                if (gMatrix4.values[b2][b2 + 1] == 0.0D) {
                    arrayOfDouble1[0] = d1;

                } else {
                    arrayOfDouble1[0] = gMatrix4.values[b2][b2 + 1] + d_sign(d1, gMatrix4.values[b2][b2 + 1]);

                }

                for (i1 = 1; i1 < j - 1; i1++)
                arrayOfDouble1[i1] = gMatrix4.values[b2][b2 + i1 + 1];

                double d2 = 0.0D;

                for (i1 = 0; i1 < j - 1; i1++)
                d2 += arrayOfDouble1[i1] * arrayOfDouble1[i1];

                d2 = 2.0D / d2;

                int i2;

                for (i2 = b2 + 1; i2 < j; i2++) {
                    for (int i3 = b2 + 1; i3 < gMatrix4.nCol; i3++)
                    gMatrix3.values[i2][i3] = -d2 * arrayOfDouble1[i2 - b2 - 1] * arrayOfDouble1[i3 - b2 - 1];

                }

                for (i1 = b2 + 1; i1 < gMatrix4.nCol; i1++)
                gMatrix3.values[i1][i1] = gMatrix3.values[i1][i1] + 1.0D;

                double d3 = 0.0D;

                for (i1 = b2; i1 < gMatrix4.nCol; i1++)
                d3 += gMatrix3.values[i1][b2 + 1] * gMatrix4.values[b2][i1];

                gMatrix4.values[b2][b2 + 1] = d3;

                for (i2 = b2 + 1; i2 < gMatrix4.nRow; i2++) {
                    for (int i3 = b2 + 1; i3 < gMatrix4.nCol; i3++) {
                        gMatrix1.values[i2][i3] = 0.0D;

                        for (i1 = b2 + 1; i1 < gMatrix4.nCol; i1++)
                        gMatrix1.values[i2][i3] = gMatrix1.values[i2][i3] + gMatrix3.values[i1][i3] * gMatrix4.values[i2][i1];

                    }

                }

                for (i2 = b2 + 1; i2 < gMatrix4.nRow; i2++) {
                    for (int i3 = b2 + 1; i3 < gMatrix4.nCol; i3++)
                    gMatrix4.values[i2][i3] = gMatrix1.values[i2][i3];

                }

                for (i2 = 0; i2 < gMatrix4.nRow; i2++) {
                    for (int i3 = b2 + 1; i3 < gMatrix4.nCol; i3++) {
                        gMatrix1.values[i2][i3] = 0.0D;

                        for (i1 = b2 + 1; i1 < gMatrix4.nCol; i1++)
                        gMatrix1.values[i2][i3] = gMatrix1.values[i2][i3] + gMatrix3.values[i1][i3] * paramGMatrix4.values[i2][i1];

                    }

                }

                for (i2 = 0; i2 < gMatrix4.nRow; i2++) {
                    for (int i3 = b2 + 1; i3 < gMatrix4.nCol; i3++)
                    paramGMatrix4.values[i2][i3] = gMatrix1.values[i2][i3];

                }

                j--;

            }

        }

        byte b1;

        for (b1 = 0; b1 < m; b1++)
        arrayOfDouble2[b1] = gMatrix4.values[b1][b1];

        for (b1 = 0; b1 < k; b1++)
        arrayOfDouble3[b1] = gMatrix4.values[b1][b1 + 1];

        if (gMatrix4.nRow == 2 && gMatrix4.nCol == 2) {
            double[] arrayOfDouble4 = new double[1];

            double[] arrayOfDouble5 = new double[1];

            double[] arrayOfDouble6 = new double[1];

            double[] arrayOfDouble7 = new double[1];

            compute_2X2(arrayOfDouble2[0], arrayOfDouble3[0], arrayOfDouble2[1], arrayOfDouble2, arrayOfDouble6, arrayOfDouble4, arrayOfDouble7, arrayOfDouble5, 0);

            update_u(0, paramGMatrix2, arrayOfDouble4, arrayOfDouble6);

            update_v(0, paramGMatrix4, arrayOfDouble5, arrayOfDouble7);

            return 2;

        }

        compute_qr(0, arrayOfDouble3.length - 1, arrayOfDouble2, arrayOfDouble3, paramGMatrix2, paramGMatrix4);

        return arrayOfDouble2.length;

    }

    static void compute_qr(int paramInt1, int paramInt2, double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, GMatrix paramGMatrix1, GMatrix paramGMatrix2) {
        double[] arrayOfDouble1 = new double[1];

        double[] arrayOfDouble2 = new double[1];

        double[] arrayOfDouble3 = new double[1];

        double[] arrayOfDouble4 = new double[1];

        GMatrix gMatrix = new GMatrix(paramGMatrix1.nCol, paramGMatrix2.nRow);

        double d3 = 1.0D;

        double d4 = -1.0D;

        boolean bool = false;

        double d1 = 0.0D;

        double d2 = 0.0D;

        for (byte b = 0; b < 2 && !bool; b++) {
            int j;

            for (j = paramInt1; j <= paramInt2; j++) {
                if (j == paramInt1) {
                    int m;

                    if (paramArrayOfdouble2.length == paramArrayOfdouble1.length) {
                        m = paramInt2;

                    } else {
                        m = paramInt2 + 1;

                    }

                    double d5 = compute_shift(paramArrayOfdouble1[m - 1], paramArrayOfdouble2[paramInt2], paramArrayOfdouble1[m]);

                    d1 = (Math.abs(paramArrayOfdouble1[j]) - d5) * (d_sign(d3, paramArrayOfdouble1[j]) + d5 / paramArrayOfdouble1[j]);

                    d2 = paramArrayOfdouble2[j];

                }

                double d = compute_rot(d1, d2, arrayOfDouble4, arrayOfDouble2);

                if (j != paramInt1)
                paramArrayOfdouble2[j - 1] = d;

                d1 = arrayOfDouble2[0] * paramArrayOfdouble1[j] + arrayOfDouble4[0] * paramArrayOfdouble2[j];

                paramArrayOfdouble2[j] = arrayOfDouble2[0] * paramArrayOfdouble2[j] - arrayOfDouble4[0] * paramArrayOfdouble1[j];

                d2 = arrayOfDouble4[0] * paramArrayOfdouble1[j + 1];

                paramArrayOfdouble1[j + 1] = arrayOfDouble2[0] * paramArrayOfdouble1[j + 1];

                update_v(j, paramGMatrix2, arrayOfDouble2, arrayOfDouble4);

                d = compute_rot(d1, d2, arrayOfDouble3, arrayOfDouble1);

                paramArrayOfdouble1[j] = d;

                d1 = arrayOfDouble1[0] * paramArrayOfdouble2[j] + arrayOfDouble3[0] * paramArrayOfdouble1[j + 1];

                paramArrayOfdouble1[j + 1] = arrayOfDouble1[0] * paramArrayOfdouble1[j + 1] - arrayOfDouble3[0] * paramArrayOfdouble2[j];

                if (j < paramInt2) {
                    d2 = arrayOfDouble3[0] * paramArrayOfdouble2[j + 1];

                    paramArrayOfdouble2[j + 1] = arrayOfDouble1[0] * paramArrayOfdouble2[j + 1];

                }

                update_u(j, paramGMatrix1, arrayOfDouble1, arrayOfDouble3);

            }

            if (paramArrayOfdouble1.length == paramArrayOfdouble2.length) {
                double d = compute_rot(d1, d2, arrayOfDouble4, arrayOfDouble2);

                d1 = arrayOfDouble2[0] * paramArrayOfdouble1[j] + arrayOfDouble4[0] * paramArrayOfdouble2[j];

                paramArrayOfdouble2[j] = arrayOfDouble2[0] * paramArrayOfdouble2[j] - arrayOfDouble4[0] * paramArrayOfdouble1[j];

                paramArrayOfdouble1[j + 1] = arrayOfDouble2[0] * paramArrayOfdouble1[j + 1];

                update_v(j, paramGMatrix2, arrayOfDouble2, arrayOfDouble4);

            }

            while (paramInt2 - paramInt1 > 1 && Math.abs(paramArrayOfdouble2[paramInt2]) < 4.89E-15D)
            paramInt2--;

            for (int k = paramInt2 - 2; k > paramInt1; k--) {
                if (Math.abs(paramArrayOfdouble2[k]) < 4.89E-15D) {
                    compute_qr(k + 1, paramInt2, paramArrayOfdouble1, paramArrayOfdouble2, paramGMatrix1, paramGMatrix2);

                    for (paramInt2 = k - 1; paramInt2 - paramInt1 > 1 && Math.abs(paramArrayOfdouble2[paramInt2]) < 4.89E-15D; paramInt2--);

                }

            }

            if (paramInt2 - paramInt1 <= 1 && Math.abs(paramArrayOfdouble2[paramInt1 + 1]) < 4.89E-15D)
            bool = true;

        }

        if (Math.abs(paramArrayOfdouble2[1]) < 4.89E-15D) {
            compute_2X2(paramArrayOfdouble1[paramInt1], paramArrayOfdouble2[paramInt1], paramArrayOfdouble1[paramInt1 + 1], paramArrayOfdouble1, arrayOfDouble3, arrayOfDouble1, arrayOfDouble4, arrayOfDouble2, 0);

            paramArrayOfdouble2[paramInt1] = 0.0D;

            paramArrayOfdouble2[paramInt1 + 1] = 0.0D;

        }

        int i = paramInt1;

        update_u(i, paramGMatrix1, arrayOfDouble1, arrayOfDouble3);

        update_v(i, paramGMatrix2, arrayOfDouble2, arrayOfDouble4);

    }

    private static void print_se(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2) {
        System.out.println("\ns =" + paramArrayOfdouble1[0] + " " + paramArrayOfdouble1[1] + " " + paramArrayOfdouble1[2]);

        System.out.println("e =" + paramArrayOfdouble2[0] + " " + paramArrayOfdouble2[1]);

    }

    private static void update_v(int paramInt, GMatrix paramGMatrix, double[] paramArrayOfdouble1, double[] paramArrayOfdouble2) {
        for (byte b = 0; b < paramGMatrix.nRow; b++) {
            double d = paramGMatrix.values[b][paramInt];

            paramGMatrix.values[b][paramInt] = paramArrayOfdouble1[0] * d + paramArrayOfdouble2[0] * paramGMatrix.values[b][paramInt + 1];

            paramGMatrix.values[b][paramInt + 1] = -paramArrayOfdouble2[0] * d + paramArrayOfdouble1[0] * paramGMatrix.values[b][paramInt + 1];

        }

    }

    private static void chase_up(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, int paramInt, GMatrix paramGMatrix) {
        double[] arrayOfDouble1 = new double[1];

        double[] arrayOfDouble2 = new double[1];

        GMatrix gMatrix1 = new GMatrix(paramGMatrix.nRow, paramGMatrix.nCol);

        GMatrix gMatrix2 = new GMatrix(paramGMatrix.nRow, paramGMatrix.nCol);

        double d1 = paramArrayOfdouble2[paramInt];

        double d2 = paramArrayOfdouble1[paramInt];

        int i;

        for (i = paramInt; i > 0; i--) {
            double d = compute_rot(d1, d2, arrayOfDouble2, arrayOfDouble1);

            d1 = -paramArrayOfdouble2[i - 1] * arrayOfDouble2[0];

            d2 = paramArrayOfdouble1[i - 1];

            paramArrayOfdouble1[i] = d;

            paramArrayOfdouble2[i - 1] = paramArrayOfdouble2[i - 1] * arrayOfDouble1[0];

            update_v_split(i, paramInt + 1, paramGMatrix, arrayOfDouble1, arrayOfDouble2, gMatrix1, gMatrix2);

        }

        paramArrayOfdouble1[i + 1] = compute_rot(d1, d2, arrayOfDouble2, arrayOfDouble1);

        update_v_split(i, paramInt + 1, paramGMatrix, arrayOfDouble1, arrayOfDouble2, gMatrix1, gMatrix2);

    }

    private static void chase_across(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, int paramInt, GMatrix paramGMatrix) {
        double[] arrayOfDouble1 = new double[1];

        double[] arrayOfDouble2 = new double[1];

        GMatrix gMatrix1 = new GMatrix(paramGMatrix.nRow, paramGMatrix.nCol);

        GMatrix gMatrix2 = new GMatrix(paramGMatrix.nRow, paramGMatrix.nCol);

        double d2 = paramArrayOfdouble2[paramInt];

        double d1 = paramArrayOfdouble1[paramInt + 1];

        int i;

        for (i = paramInt; i < paramGMatrix.nCol - 2; i++) {
            double d = compute_rot(d1, d2, arrayOfDouble2, arrayOfDouble1);

            d2 = -paramArrayOfdouble2[i + 1] * arrayOfDouble2[0];

            d1 = paramArrayOfdouble1[i + 2];

            paramArrayOfdouble1[i + 1] = d;

            paramArrayOfdouble2[i + 1] = paramArrayOfdouble2[i + 1] * arrayOfDouble1[0];

            update_u_split(paramInt, i + 1, paramGMatrix, arrayOfDouble1, arrayOfDouble2, gMatrix1, gMatrix2);

        }

        paramArrayOfdouble1[i + 1] = compute_rot(d1, d2, arrayOfDouble2, arrayOfDouble1);

        update_u_split(paramInt, i + 1, paramGMatrix, arrayOfDouble1, arrayOfDouble2, gMatrix1, gMatrix2);

    }

    private static void update_v_split(int paramInt1, int paramInt2, GMatrix paramGMatrix1, double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, GMatrix paramGMatrix2, GMatrix paramGMatrix3) {
        for (byte b = 0; b < paramGMatrix1.nRow; b++) {
            double d = paramGMatrix1.values[b][paramInt1];

            paramGMatrix1.values[b][paramInt1] = paramArrayOfdouble1[0] * d - paramArrayOfdouble2[0] * paramGMatrix1.values[b][paramInt2];

            paramGMatrix1.values[b][paramInt2] = paramArrayOfdouble2[0] * d + paramArrayOfdouble1[0] * paramGMatrix1.values[b][paramInt2];

        }

        System.out.println("topr    =" + paramInt1);

        System.out.println("bottomr =" + paramInt2);

        System.out.println("cosr =" + paramArrayOfdouble1[0]);

        System.out.println("sinr =" + paramArrayOfdouble2[0]);

        System.out.println("\nm =");

        checkMatrix(paramGMatrix3);

        System.out.println("\nv =");

        checkMatrix(paramGMatrix2);

        paramGMatrix3.mul(paramGMatrix3, paramGMatrix2);

        System.out.println("\nt*m =");

        checkMatrix(paramGMatrix3);

    }

    private static void update_u_split(int paramInt1, int paramInt2, GMatrix paramGMatrix1, double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, GMatrix paramGMatrix2, GMatrix paramGMatrix3) {
        for (byte b = 0; b < paramGMatrix1.nCol; b++) {
            double d = paramGMatrix1.values[paramInt1][b];

            paramGMatrix1.values[paramInt1][b] = paramArrayOfdouble1[0] * d - paramArrayOfdouble2[0] * paramGMatrix1.values[paramInt2][b];

            paramGMatrix1.values[paramInt2][b] = paramArrayOfdouble2[0] * d + paramArrayOfdouble1[0] * paramGMatrix1.values[paramInt2][b];

        }

        System.out.println("\nm=");

        checkMatrix(paramGMatrix3);

        System.out.println("\nu=");

        checkMatrix(paramGMatrix2);

        paramGMatrix3.mul(paramGMatrix2, paramGMatrix3);

        System.out.println("\nt*m=");

        checkMatrix(paramGMatrix3);

    }

    private static void update_u(int paramInt, GMatrix paramGMatrix, double[] paramArrayOfdouble1, double[] paramArrayOfdouble2) {
        for (byte b = 0; b < paramGMatrix.nCol; b++) {
            double d = paramGMatrix.values[paramInt][b];

            paramGMatrix.values[paramInt][b] = paramArrayOfdouble1[0] * d + paramArrayOfdouble2[0] * paramGMatrix.values[paramInt + 1][b];

            paramGMatrix.values[paramInt + 1][b] = -paramArrayOfdouble2[0] * d + paramArrayOfdouble1[0] * paramGMatrix.values[paramInt + 1][b];

        }

    }

    private static void print_m(GMatrix paramGMatrix1, GMatrix paramGMatrix2, GMatrix paramGMatrix3) {
        GMatrix gMatrix = new GMatrix(paramGMatrix1.nCol, paramGMatrix1.nRow);

        gMatrix.mul(paramGMatrix2, gMatrix);

        gMatrix.mul(gMatrix, paramGMatrix3);

        System.out.println("\n m = \n" + toString(gMatrix));

    }

    private static String toString(GMatrix paramGMatrix) {
        StringBuffer stringBuffer = new StringBuffer(paramGMatrix.nRow * paramGMatrix.nCol * 8);

        for (byte b = 0; b < paramGMatrix.nRow; b++) {
            for (byte b1 = 0; b1 < paramGMatrix.nCol; b1++) {
                if (Math.abs(paramGMatrix.values[b][b1]) < 1.0E-9D) {
                    stringBuffer.append("0.0000 ");

                } else {
                    stringBuffer.append(paramGMatrix.values[b][b1]).append(" ");

                }

            }

            stringBuffer.append("\n");

        }

        return stringBuffer.toString();

    }

    private static void print_svd(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, GMatrix paramGMatrix1, GMatrix paramGMatrix2) {
        GMatrix gMatrix = new GMatrix(paramGMatrix1.nCol, paramGMatrix2.nRow);

        System.out.println(" \ns = ");

        byte b;

        for (b = 0; b < paramArrayOfdouble1.length; b++)
        System.out.println(" " + paramArrayOfdouble1[b]);

        System.out.println(" \ne = ");

        for (b = 0; b < paramArrayOfdouble2.length; b++)
        System.out.println(" " + paramArrayOfdouble2[b]);

        System.out.println(" \nu  = \n" + paramGMatrix1.toString());

        System.out.println(" \nv  = \n" + paramGMatrix2.toString());

        gMatrix.setIdentity();

        for (b = 0; b < paramArrayOfdouble1.length; b++)
        gMatrix.values[b][b] = paramArrayOfdouble1[b];

        for (b = 0; b < paramArrayOfdouble2.length; b++)
        gMatrix.values[b][b + 1] = paramArrayOfdouble2[b];

        System.out.println(" \nm  = \n" + gMatrix.toString());

        gMatrix.mulTransposeLeft(paramGMatrix1, gMatrix);

        gMatrix.mulTransposeRight(gMatrix, paramGMatrix2);

        System.out.println(" \n u.transpose*m*v.transpose  = \n" + gMatrix.toString());

    }

    static double max(double paramDouble1, double paramDouble2) {
        return (paramDouble1 > paramDouble2) ? paramDouble1 : paramDouble2;

    }

    static double min(double paramDouble1, double paramDouble2) {
        return (paramDouble1 < paramDouble2) ? paramDouble1 : paramDouble2;

    }

    static double compute_shift(double paramDouble1, double paramDouble2, double paramDouble3) {
        double d6;

        double d3 = Math.abs(paramDouble1);

        double d4 = Math.abs(paramDouble2);

        double d5 = Math.abs(paramDouble3);

        double d1 = min(d3, d5);

        double d2 = max(d3, d5);

        if (d1 == 0.0D) {
            d6 = 0.0D;

            if (d2 != 0.0D) {
                double d = Math.min(d2, d4) / Math.max(d2, d4);

            }

        }

        else if (d4 < d2) {
            double d9 = d1 / d2 + 1.0D;

            double d10 = (d2 - d1) / d2;

            double d7 = d4 / d2;

            double d11 = d7 * d7;

            double d8 = 2.0D / (Math.sqrt(d9 * d9 + d11) + Math.sqrt(d10 * d10 + d11));

            d6 = d1 * d8;

        } else {
            double d = d2 / d4;

            if (d == 0.0D) {
                d6 = d1 * d2 / d4;

            } else {
                double d10 = d1 / d2 + 1.0D;

                double d11 = (d2 - d1) / d2;

                double d7 = d10 * d;

                double d8 = d11 * d;

                double d9 = 1.0D / (Math.sqrt(d7 * d7 + 1.0D) + Math.sqrt(d8 * d8 + 1.0D));

                d6 = d1 * d9 * d;

                d6 += d6;

            }

        }

        return d6;

    }

    static int compute_2X2(double paramDouble1, double paramDouble2, double paramDouble3, double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, double[] paramArrayOfdouble3, double[] paramArrayOfdouble4, double[] paramArrayOfdouble5, int paramInt) {
        boolean bool;

        double d1 = 2.0D;

        double d2 = 1.0D;

        double d15 = paramArrayOfdouble1[0];

        double d14 = paramArrayOfdouble1[1];

        double d10 = 0.0D;

        double d11 = 0.0D;

        double d12 = 0.0D;

        double d13 = 0.0D;

        double d3 = 0.0D;

        double d7 = paramDouble1;

        double d4 = Math.abs(d7);

        double d9 = paramDouble3;

        double d6 = Math.abs(paramDouble3);

        byte b = 1;

        if (d6 > d4) {
            bool = true;

        } else {
            bool = false;

        }

        if (bool) {
            b = 3;

            double d = d7;

            d7 = d9;

            d9 = d;

            d = d4;

            d4 = d6;

            d6 = d;

        }

        double d8 = paramDouble2;

        double d5 = Math.abs(d8);

        if (d5 == 0.0D) {
            paramArrayOfdouble1[1] = d6;

            paramArrayOfdouble1[0] = d4;

            d10 = 1.0D;

            d11 = 1.0D;

            d12 = 0.0D;

            d13 = 0.0D;

        } else {
            boolean bool1 = true;

            if (d5 > d4) {
                b = 2;

                if (d4 / d5 < 1.0E-10D) {
                    bool1 = false;

                    d15 = d5;

                    if (d6 > 1.0D) {
                        d14 = d4 / d5 / d6;

                    } else {
                        d14 = d4 / d5 * d6;

                    }

                    d10 = 1.0D;

                    d12 = d9 / d8;

                    d13 = 1.0D;

                    d11 = d7 / d8;

                }

            }

            if (bool1) {
                double d18;

                double d20;

                double d17 = d4 - d6;

                if (d17 == d4) {
                    d18 = 1.0D;

                } else {
                    d18 = d17 / d4;

                }

                double d19 = d8 / d7;

                double d22 = 2.0D - d18;

                double d23 = d19 * d19;

                double d24 = d22 * d22;

                double d21 = Math.sqrt(d24 + d23);

                if (d18 == 0.0D) {
                    d20 = Math.abs(d19);

                } else {
                    d20 = Math.sqrt(d18 * d18 + d23);

                }

                double d16 = (d21 + d20) * 0.5D;

                if (d5 > d4) {
                    b = 2;

                    if (d4 / d5 < 1.0E-10D) {
                        bool1 = false;

                        d15 = d5;

                        if (d6 > 1.0D) {
                            d14 = d4 / d5 / d6;

                        } else {
                            d14 = d4 / d5 * d6;

                        }

                        d10 = 1.0D;

                        d12 = d9 / d8;

                        d13 = 1.0D;

                        d11 = d7 / d8;

                    }

                }

                if (bool1) {
                    d17 = d4 - d6;

                    if (d17 == d4) {
                        d18 = 1.0D;

                    } else {
                        d18 = d17 / d4;

                    }

                    d19 = d8 / d7;

                    d22 = 2.0D - d18;

                    d23 = d19 * d19;

                    d24 = d22 * d22;

                    d21 = Math.sqrt(d24 + d23);

                    if (d18 == 0.0D) {
                        d20 = Math.abs(d19);

                    } else {
                        d20 = Math.sqrt(d18 * d18 + d23);

                    }

                    d16 = (d21 + d20) * 0.5D;

                    d14 = d6 / d16;

                    d15 = d4 * d16;

                    if (d23 == 0.0D) {
                        if (d18 == 0.0D) {
                            d22 = d_sign(d1, d7) * d_sign(d2, d8);

                        } else {
                            d22 = d8 / d_sign(d17, d7) + d19 / d22;

                        }

                    } else {
                        d22 = (d19 / (d21 + d22) + d19 / (d20 + d18)) * (d16 + 1.0D);

                    }

                    d18 = Math.sqrt(d22 * d22 + 4.0D);

                    d11 = 2.0D / d18;

                    d13 = d22 / d18;

                    d10 = (d11 + d13 * d19) / d16;

                    d12 = d9 / d7 * d13 / d16;

                }

            }

            if (bool) {
                paramArrayOfdouble3[0] = d13;

                paramArrayOfdouble2[0] = d11;

                paramArrayOfdouble5[0] = d12;

                paramArrayOfdouble4[0] = d10;

            } else {
                paramArrayOfdouble3[0] = d10;

                paramArrayOfdouble2[0] = d12;

                paramArrayOfdouble5[0] = d11;

                paramArrayOfdouble4[0] = d13;

            }

            if (b == 1)
            d3 = d_sign(d2, paramArrayOfdouble5[0]) * d_sign(d2, paramArrayOfdouble3[0]) * d_sign(d2, paramDouble1);

            if (b == 2)
            d3 = d_sign(d2, paramArrayOfdouble4[0]) * d_sign(d2, paramArrayOfdouble3[0]) * d_sign(d2, paramDouble2);

            if (b == 3)
            d3 = d_sign(d2, paramArrayOfdouble4[0]) * d_sign(d2, paramArrayOfdouble2[0]) * d_sign(d2, paramDouble3);

            paramArrayOfdouble1[paramInt] = d_sign(d15, d3);

            double d = d3 * d_sign(d2, paramDouble1) * d_sign(d2, paramDouble3);

            paramArrayOfdouble1[paramInt + 1] = d_sign(d14, d);

        }

        return 0;

    }

    static double compute_rot(double paramDouble1, double paramDouble2, double[] paramArrayOfdouble1, double[] paramArrayOfdouble2) {
        double d1;

        double d2;

        double d3;

        if (paramDouble2 == 0.0D) {
            d1 = 1.0D;

            d2 = 0.0D;

            d3 = paramDouble1;

        } else if (paramDouble1 == 0.0D) {
            d1 = 0.0D;

            d2 = 1.0D;

            d3 = paramDouble2;

        } else {
            double d5 = paramDouble1;

            double d6 = paramDouble2;

            double d4 = max(Math.abs(d5), Math.abs(d6));

            if (d4 >= 4.994797680505588E145D) {
                byte b3 = 0;

                while (d4 >= 4.994797680505588E145D) {
                    b3++;

                    d5 *= 2.002083095183101E-146D;

                    d6 *= 2.002083095183101E-146D;

                    d4 = max(Math.abs(d5), Math.abs(d6));

                }

                d3 = Math.sqrt(d5 * d5 + d6 * d6);

                d1 = d5 / d3;

                d2 = d6 / d3;

                byte b1 = b3;

                for (byte b2 = 1; b2 <= b3; b2++)
                d3 *= 4.994797680505588E145D;

            } else if (d4 <= 2.002083095183101E-146D) {
                byte b3 = 0;

                while (d4 <= 2.002083095183101E-146D) {
                    b3++;

                    d5 *= 4.994797680505588E145D;

                    d6 *= 4.994797680505588E145D;

                    d4 = max(Math.abs(d5), Math.abs(d6));

                }

                d3 = Math.sqrt(d5 * d5 + d6 * d6);

                d1 = d5 / d3;

                d2 = d6 / d3;

                byte b1 = b3;

                for (byte b2 = 1; b2 <= b3; b2++)
                d3 *= 2.002083095183101E-146D;

            } else {
                d3 = Math.sqrt(d5 * d5 + d6 * d6);

                d1 = d5 / d3;

                d2 = d6 / d3;

            }

            if (Math.abs(paramDouble1) > Math.abs(paramDouble2) && d1 < 0.0D) {
                d1 = -d1;

                d2 = -d2;

                d3 = -d3;

            }

        }

        paramArrayOfdouble1[0] = d2;

        paramArrayOfdouble2[0] = d1;

        return d3;

    }

    static double d_sign(double paramDouble1, double paramDouble2) {
        double d = (paramDouble1 >= 0.0D) ? paramDouble1 : -paramDouble1;

        return (paramDouble2 >= 0.0D) ? d : -d;

    }

    public Object clone() {
        GMatrix gMatrix = null;

        try {
            gMatrix = (GMatrix)super.clone();

        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();

        }

        gMatrix.values = new double[this.nRow][this.nCol];

        for (byte b = 0; b < this.nRow; b++) {
            for (byte b1 = 0; b1 < this.nCol; b1++)
            gMatrix.values[b][b1] = this.values[b][b1];

        }

        return gMatrix;

    }

}
