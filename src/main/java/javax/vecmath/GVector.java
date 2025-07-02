package javax.vecmath;

import java.io.Serializable;

public class GVector implements Serializable, Cloneable {

    private int length;

    double[] values;

    static final long serialVersionUID = 1398850036893875112L;

    public GVector(int paramInt) {
        this.length = paramInt;

        this.values = new double[paramInt];

        for (byte b = 0; b < paramInt; b++)
        this.values[b] = 0.0D;

    }

    public GVector(double[] paramArrayOfdouble) {
        this.length = paramArrayOfdouble.length;

        this.values = new double[paramArrayOfdouble.length];

        for (byte b = 0; b < this.length; b++)
        this.values[b] = paramArrayOfdouble[b];

    }

    public GVector(GVector paramGVector) {
        this.values = new double[paramGVector.length];

        this.length = paramGVector.length;

        for (byte b = 0; b < this.length; b++)
        this.values[b] = paramGVector.values[b];

    }

    public GVector(Tuple2f paramTuple2f) {
        this.values = new double[2];

        this.values[0] = paramTuple2f.x;

        this.values[1] = paramTuple2f.y;

        this.length = 2;

    }

    public GVector(Tuple3f paramTuple3f) {
        this.values = new double[3];

        this.values[0] = paramTuple3f.x;

        this.values[1] = paramTuple3f.y;

        this.values[2] = paramTuple3f.z;

        this.length = 3;

    }

    public GVector(Tuple3d paramTuple3d) {
        this.values = new double[3];

        this.values[0] = paramTuple3d.x;

        this.values[1] = paramTuple3d.y;

        this.values[2] = paramTuple3d.z;

        this.length = 3;

    }

    public GVector(Tuple4f paramTuple4f) {
        this.values = new double[4];

        this.values[0] = paramTuple4f.x;

        this.values[1] = paramTuple4f.y;

        this.values[2] = paramTuple4f.z;

        this.values[3] = paramTuple4f.w;

        this.length = 4;

    }

    public GVector(Tuple4d paramTuple4d) {
        this.values = new double[4];

        this.values[0] = paramTuple4d.x;

        this.values[1] = paramTuple4d.y;

        this.values[2] = paramTuple4d.z;

        this.values[3] = paramTuple4d.w;

        this.length = 4;

    }

    public GVector(double[] paramArrayOfdouble, int paramInt) {
        this.length = paramInt;

        this.values = new double[paramInt];

        for (byte b = 0; b < paramInt; b++)
        this.values[b] = paramArrayOfdouble[b];

    }

    public final double norm() {
        double d = 0.0D;

        for (byte b = 0; b < this.length; b++)
        d += this.values[b] * this.values[b];

        return Math.sqrt(d);

    }

    public final double normSquared() {
        double d = 0.0D;

        for (byte b = 0; b < this.length; b++)
        d += this.values[b] * this.values[b];

        return d;

    }

    public final void normalize(GVector paramGVector) {
        double d1 = 0.0D;

        if (this.length != paramGVector.length)
        throw new MismatchedSizeException(VecMathI18N.getString("GVector0"));

        byte b;

        for (b = 0; b < this.length; b++)
        d1 += paramGVector.values[b] * paramGVector.values[b];

        double d2 = 1.0D / Math.sqrt(d1);

        for (b = 0; b < this.length; b++)
        this.values[b] = paramGVector.values[b] * d2;

    }

    public final void normalize() {
        double d1 = 0.0D;

        byte b;

        for (b = 0; b < this.length; b++)
        d1 += this.values[b] * this.values[b];

        double d2 = 1.0D / Math.sqrt(d1);

        for (b = 0; b < this.length; b++)
        this.values[b] = this.values[b] * d2;

    }

    public final void scale(double paramDouble, GVector paramGVector) {
        if (this.length != paramGVector.length)
        throw new MismatchedSizeException(VecMathI18N.getString("GVector1"));

        for (byte b = 0; b < this.length; b++)
        this.values[b] = paramGVector.values[b] * paramDouble;

    }

    public final void scale(double paramDouble) {
        for (byte b = 0; b < this.length; b++)
        this.values[b] = this.values[b] * paramDouble;

    }

    public final void scaleAdd(double paramDouble, GVector paramGVector1, GVector paramGVector2) {
        if (paramGVector2.length != paramGVector1.length)
        throw new MismatchedSizeException(VecMathI18N.getString("GVector2"));

        if (this.length != paramGVector1.length)
        throw new MismatchedSizeException(VecMathI18N.getString("GVector3"));

        for (byte b = 0; b < this.length; b++)
        this.values[b] = paramGVector1.values[b] * paramDouble + paramGVector2.values[b];

    }

    public final void add(GVector paramGVector) {
        if (this.length != paramGVector.length)
        throw new MismatchedSizeException(VecMathI18N.getString("GVector4"));

        for (byte b = 0; b < this.length; b++)
        this.values[b] = this.values[b] + paramGVector.values[b];

    }

    public final void add(GVector paramGVector1, GVector paramGVector2) {
        if (paramGVector1.length != paramGVector2.length)
        throw new MismatchedSizeException(VecMathI18N.getString("GVector5"));

        if (this.length != paramGVector1.length)
        throw new MismatchedSizeException(VecMathI18N.getString("GVector6"));

        for (byte b = 0; b < this.length; b++)
        this.values[b] = paramGVector1.values[b] + paramGVector2.values[b];

    }

    public final void sub(GVector paramGVector) {
        if (this.length != paramGVector.length)
        throw new MismatchedSizeException(VecMathI18N.getString("GVector7"));

        for (byte b = 0; b < this.length; b++)
        this.values[b] = this.values[b] - paramGVector.values[b];

    }

    public final void sub(GVector paramGVector1, GVector paramGVector2) {
        if (paramGVector1.length != paramGVector2.length)
        throw new MismatchedSizeException(VecMathI18N.getString("GVector8"));

        if (this.length != paramGVector1.length)
        throw new MismatchedSizeException(VecMathI18N.getString("GVector9"));

        for (byte b = 0; b < this.length; b++)
        this.values[b] = paramGVector1.values[b] - paramGVector2.values[b];

    }

    public final void mul(GMatrix paramGMatrix, GVector paramGVector) {
        double[] arrayOfDouble;

        if (paramGMatrix.getNumCol() != paramGVector.length)
        throw new MismatchedSizeException(VecMathI18N.getString("GVector10"));

        if (this.length != paramGMatrix.getNumRow())
        throw new MismatchedSizeException(VecMathI18N.getString("GVector11"));

        if (paramGVector != this) {
            arrayOfDouble = paramGVector.values;

        } else {
            arrayOfDouble = (double[])this.values.clone();

        }

        for (int i = this.length - 1; i >= 0; i--) {
            this.values[i] = 0.0D;

            for (int j = paramGVector.length - 1; j >= 0; j--)
            this.values[i] = this.values[i] + paramGMatrix.values[i][j] * arrayOfDouble[j];

        }

    }

    public final void mul(GVector paramGVector, GMatrix paramGMatrix) {
        double[] arrayOfDouble;

        if (paramGMatrix.getNumRow() != paramGVector.length)
        throw new MismatchedSizeException(VecMathI18N.getString("GVector12"));

        if (this.length != paramGMatrix.getNumCol())
        throw new MismatchedSizeException(VecMathI18N.getString("GVector13"));

        if (paramGVector != this) {
            arrayOfDouble = paramGVector.values;

        } else {
            arrayOfDouble = (double[])this.values.clone();

        }

        for (int i = this.length - 1; i >= 0; i--) {
            this.values[i] = 0.0D;

            for (int j = paramGVector.length - 1; j >= 0; j--)
            this.values[i] = this.values[i] + paramGMatrix.values[j][i] * arrayOfDouble[j];

        }

    }

    public final void negate() {
        for (int i = this.length - 1; i >= 0; i--)
        this.values[i] = this.values[i] * -1.0D;

    }

    public final void zero() {
        for (byte b = 0; b < this.length; b++)
        this.values[b] = 0.0D;

    }

    public final void setSize(int paramInt) {
        int i;

        double[] arrayOfDouble = new double[paramInt];

        if (this.length < paramInt) {
            i = this.length;

        } else {
            i = paramInt;

        }

        for (byte b = 0; b < i; b++)
        arrayOfDouble[b] = this.values[b];

        this.length = paramInt;

        this.values = arrayOfDouble;

    }

    public final void set(double[] paramArrayOfdouble) {
        for (int i = this.length - 1; i >= 0; i--)
        this.values[i] = paramArrayOfdouble[i];

    }

    public final void set(GVector paramGVector) {
        if (this.length < paramGVector.length) {
            this.length = paramGVector.length;

            this.values = new double[this.length];

            for (byte b = 0; b < this.length; b++)
            this.values[b] = paramGVector.values[b];

        } else {
            int i;

            for (i = 0; i < paramGVector.length; i++)
            this.values[i] = paramGVector.values[i];

            for (i = paramGVector.length; i < this.length; i++)
            this.values[i] = 0.0D;

        }

    }

    public final void set(Tuple2f paramTuple2f) {
        if (this.length < 2) {
            this.length = 2;

            this.values = new double[2];

        }

        this.values[0] = paramTuple2f.x;

        this.values[1] = paramTuple2f.y;

        for (byte b = 2; b < this.length; b++)
        this.values[b] = 0.0D;

    }

    public final void set(Tuple3f paramTuple3f) {
        if (this.length < 3) {
            this.length = 3;

            this.values = new double[3];

        }

        this.values[0] = paramTuple3f.x;

        this.values[1] = paramTuple3f.y;

        this.values[2] = paramTuple3f.z;

        for (byte b = 3; b < this.length; b++)
        this.values[b] = 0.0D;

    }

    public final void set(Tuple3d paramTuple3d) {
        if (this.length < 3) {
            this.length = 3;

            this.values = new double[3];

        }

        this.values[0] = paramTuple3d.x;

        this.values[1] = paramTuple3d.y;

        this.values[2] = paramTuple3d.z;

        for (byte b = 3; b < this.length; b++)
        this.values[b] = 0.0D;

    }

    public final void set(Tuple4f paramTuple4f) {
        if (this.length < 4) {
            this.length = 4;

            this.values = new double[4];

        }

        this.values[0] = paramTuple4f.x;

        this.values[1] = paramTuple4f.y;

        this.values[2] = paramTuple4f.z;

        this.values[3] = paramTuple4f.w;

        for (byte b = 4; b < this.length; b++)
        this.values[b] = 0.0D;

    }

    public final void set(Tuple4d paramTuple4d) {
        if (this.length < 4) {
            this.length = 4;

            this.values = new double[4];

        }

        this.values[0] = paramTuple4d.x;

        this.values[1] = paramTuple4d.y;

        this.values[2] = paramTuple4d.z;

        this.values[3] = paramTuple4d.w;

        for (byte b = 4; b < this.length; b++)
        this.values[b] = 0.0D;

    }

    public final int getSize() {
        return this.values.length;

    }

    public final double getElement(int paramInt) {
        return this.values[paramInt];

    }

    public final void setElement(int paramInt, double paramDouble) {
        this.values[paramInt] = paramDouble;

    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(this.length * 8);

        for (byte b = 0; b < this.length; b++)
        stringBuffer.append(this.values[b]).append(" ");

        return stringBuffer.toString();

    }

    public int hashCode() {
        long l = 1L;

        for (byte b = 0; b < this.length; b++)
        l = 31L * l + VecMathUtil.doubleToLongBits(this.values[b]);

        return (int)(l ^ l >> 32L);

    }

    public boolean equals(GVector paramGVector) {
        try {
            if (this.length != paramGVector.length)
            return false;

            for (byte b = 0; b < this.length; b++) {
                if (this.values[b] != paramGVector.values[b])
                return false;

            }

            return true;

        } catch (NullPointerException nullPointerException) {
            return false;

        }

    }

    public boolean equals(Object paramObject) {
        try {
            GVector gVector = (GVector)paramObject;

            if (this.length != gVector.length)
            return false;

            for (byte b = 0; b < this.length; b++) {
                if (this.values[b] != gVector.values[b])
                return false;

            }

            return true;

        } catch (ClassCastException classCastException) {
            return false;

        } catch (NullPointerException nullPointerException) {
            return false;

        }

    }

    public boolean epsilonEquals(GVector paramGVector, double paramDouble) {
        if (this.length != paramGVector.length)
        return false;

        for (byte b = 0; b < this.length; b++) {
            double d = this.values[b] - paramGVector.values[b];

            if (((d < 0.0D) ? -d : d) > paramDouble)
            return false;

        }

        return true;

    }

    public final double dot(GVector paramGVector) {
        if (this.length != paramGVector.length)
        throw new MismatchedSizeException(VecMathI18N.getString("GVector14"));

        double d = 0.0D;

        for (byte b = 0; b < this.length; b++)
        d += this.values[b] * paramGVector.values[b];

        return d;

    }

    public final void SVDBackSolve(GMatrix paramGMatrix1, GMatrix paramGMatrix2, GMatrix paramGMatrix3, GVector paramGVector) {
        if (paramGMatrix1.nRow != paramGVector.getSize() || paramGMatrix1.nRow != paramGMatrix1.nCol || paramGMatrix1.nRow != paramGMatrix2.nRow)
        throw new MismatchedSizeException(VecMathI18N.getString("GVector15"));

        if (paramGMatrix2.nCol != this.values.length || paramGMatrix2.nCol != paramGMatrix3.nCol || paramGMatrix2.nCol != paramGMatrix3.nRow)
        throw new MismatchedSizeException(VecMathI18N.getString("GVector23"));

        GMatrix gMatrix = new GMatrix(paramGMatrix1.nRow, paramGMatrix2.nCol);

        gMatrix.mul(paramGMatrix1, paramGMatrix3);

        gMatrix.mulTransposeRight(paramGMatrix1, paramGMatrix2);

        gMatrix.invert();

        mul(gMatrix, paramGVector);

    }

    public final void LUDBackSolve(GMatrix paramGMatrix, GVector paramGVector1, GVector paramGVector2) {
        int i = paramGMatrix.nRow * paramGMatrix.nCol;

        double[] arrayOfDouble1 = new double[i];

        double[] arrayOfDouble2 = new double[i];

        int[] arrayOfInt = new int[paramGVector1.getSize()];

        if (paramGMatrix.nRow != paramGVector1.getSize())
        throw new MismatchedSizeException(VecMathI18N.getString("GVector16"));

        if (paramGMatrix.nRow != paramGVector2.getSize())
        throw new MismatchedSizeException(VecMathI18N.getString("GVector24"));

        if (paramGMatrix.nRow != paramGMatrix.nCol)
        throw new MismatchedSizeException(VecMathI18N.getString("GVector25"));

        byte b;

        for (b = 0; b < paramGMatrix.nRow; b++) {
            for (byte b1 = 0; b1 < paramGMatrix.nCol; b1++)
            arrayOfDouble1[b * paramGMatrix.nCol + b1] = paramGMatrix.values[b][b1];

        }

        for (b = 0; b < i; b++)
        arrayOfDouble2[b] = 0.0D;

        for (b = 0; b < paramGMatrix.nRow; b++)
        arrayOfDouble2[b * paramGMatrix.nCol] = paramGVector1.values[b];

        for (b = 0; b < paramGMatrix.nCol; b++)
        arrayOfInt[b] = (int)paramGVector2.values[b];

        GMatrix.luBacksubstitution(paramGMatrix.nRow, arrayOfDouble1, arrayOfInt, arrayOfDouble2);

        for (b = 0; b < paramGMatrix.nRow; b++)
        this.values[b] = arrayOfDouble2[b * paramGMatrix.nCol];

    }

    public final double angle(GVector paramGVector) {
        return Math.acos(dot(paramGVector) / norm() * paramGVector.norm());

    }

    public final void interpolate(GVector paramGVector1, GVector paramGVector2, float paramFloat) {
        interpolate(paramGVector1, paramGVector2, paramFloat);

    }

    public final void interpolate(GVector paramGVector, float paramFloat) {
        interpolate(paramGVector, paramFloat);

    }

    public final void interpolate(GVector paramGVector1, GVector paramGVector2, double paramDouble) {
        if (paramGVector2.length != paramGVector1.length)
        throw new MismatchedSizeException(VecMathI18N.getString("GVector20"));

        if (this.length != paramGVector1.length)
        throw new MismatchedSizeException(VecMathI18N.getString("GVector21"));

        for (byte b = 0; b < this.length; b++)
        this.values[b] = (1.0D - paramDouble) * paramGVector1.values[b] + paramDouble * paramGVector2.values[b];

    }

    public final void interpolate(GVector paramGVector, double paramDouble) {
        if (paramGVector.length != this.length)
        throw new MismatchedSizeException(VecMathI18N.getString("GVector22"));

        for (byte b = 0; b < this.length; b++)
        this.values[b] = (1.0D - paramDouble) * this.values[b] + paramDouble * paramGVector.values[b];

    }

    public Object clone() {
        GVector gVector = null;

        try {
            gVector = (GVector)super.clone();

        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();

        }

        gVector.values = new double[this.length];

        for (byte b = 0; b < this.length; b++)
        gVector.values[b] = this.values[b];

        return gVector;

    }

}
