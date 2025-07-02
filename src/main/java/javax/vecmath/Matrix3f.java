package javax.vecmath;

import java.io.Serializable;

public class Matrix3f implements Serializable, Cloneable {

    static final long serialVersionUID = 329697160112089834L;

    public float m00;

    public float m01;

    public float m02;

    public float m10;

    public float m11;

    public float m12;

    public float m20;

    public float m21;

    public float m22;

    private static final double EPS = 1.0E-8D;

    public Matrix3f(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9) {
        this.m00 = paramFloat1;

        this.m01 = paramFloat2;

        this.m02 = paramFloat3;

        this.m10 = paramFloat4;

        this.m11 = paramFloat5;

        this.m12 = paramFloat6;

        this.m20 = paramFloat7;

        this.m21 = paramFloat8;

        this.m22 = paramFloat9;

    }

    public Matrix3f(float[] paramArrayOffloat) {
        this.m00 = paramArrayOffloat[0];

        this.m01 = paramArrayOffloat[1];

        this.m02 = paramArrayOffloat[2];

        this.m10 = paramArrayOffloat[3];

        this.m11 = paramArrayOffloat[4];

        this.m12 = paramArrayOffloat[5];

        this.m20 = paramArrayOffloat[6];

        this.m21 = paramArrayOffloat[7];

        this.m22 = paramArrayOffloat[8];

    }

    public Matrix3f(Matrix3d paramMatrix3d) {
        this.m00 = (float)paramMatrix3d.m00;

        this.m01 = (float)paramMatrix3d.m01;

        this.m02 = (float)paramMatrix3d.m02;

        this.m10 = (float)paramMatrix3d.m10;

        this.m11 = (float)paramMatrix3d.m11;

        this.m12 = (float)paramMatrix3d.m12;

        this.m20 = (float)paramMatrix3d.m20;

        this.m21 = (float)paramMatrix3d.m21;

        this.m22 = (float)paramMatrix3d.m22;

    }

    public Matrix3f(Matrix3f paramMatrix3f) {
        this.m00 = paramMatrix3f.m00;

        this.m01 = paramMatrix3f.m01;

        this.m02 = paramMatrix3f.m02;

        this.m10 = paramMatrix3f.m10;

        this.m11 = paramMatrix3f.m11;

        this.m12 = paramMatrix3f.m12;

        this.m20 = paramMatrix3f.m20;

        this.m21 = paramMatrix3f.m21;

        this.m22 = paramMatrix3f.m22;

    }

    public Matrix3f() {
        this.m00 = 0.0F;

        this.m01 = 0.0F;

        this.m02 = 0.0F;

        this.m10 = 0.0F;

        this.m11 = 0.0F;

        this.m12 = 0.0F;

        this.m20 = 0.0F;

        this.m21 = 0.0F;

        this.m22 = 0.0F;

    }

    public String toString() {
        return this.m00 + ", " + this.m01 + ", " + this.m02 + "\n" + this.m10 + ", " + this.m11 + ", " + this.m12 + "\n" + this.m20 + ", " + this.m21 + ", " + this.m22 + "\n";

    }

    public final void setIdentity() {
        this.m00 = 1.0F;

        this.m01 = 0.0F;

        this.m02 = 0.0F;

        this.m10 = 0.0F;

        this.m11 = 1.0F;

        this.m12 = 0.0F;

        this.m20 = 0.0F;

        this.m21 = 0.0F;

        this.m22 = 1.0F;

    }

    public final void setScale(float paramFloat) {
        double[] arrayOfDouble1 = new double[9];

        double[] arrayOfDouble2 = new double[3];

        getScaleRotate(arrayOfDouble2, arrayOfDouble1);

        this.m00 = (float)(arrayOfDouble1[0] * paramFloat);

        this.m01 = (float)(arrayOfDouble1[1] * paramFloat);

        this.m02 = (float)(arrayOfDouble1[2] * paramFloat);

        this.m10 = (float)(arrayOfDouble1[3] * paramFloat);

        this.m11 = (float)(arrayOfDouble1[4] * paramFloat);

        this.m12 = (float)(arrayOfDouble1[5] * paramFloat);

        this.m20 = (float)(arrayOfDouble1[6] * paramFloat);

        this.m21 = (float)(arrayOfDouble1[7] * paramFloat);

        this.m22 = (float)(arrayOfDouble1[8] * paramFloat);

    }

    public final void setElement(int paramInt1, int paramInt2, float paramFloat) {
        switch (paramInt1) {
            case 0:
            switch (paramInt2) {
                case 0:
                this.m00 = paramFloat;

                return;

                case 1:
                this.m01 = paramFloat;

                return;

                case 2:
                this.m02 = paramFloat;

                return;

            }

            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f0"));

            case 1:
            switch (paramInt2) {
                case 0:
                this.m10 = paramFloat;

                return;

                case 1:
                this.m11 = paramFloat;

                return;

                case 2:
                this.m12 = paramFloat;

                return;

            }

            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f0"));

            case 2:
            switch (paramInt2) {
                case 0:
                this.m20 = paramFloat;

                return;

                case 1:
                this.m21 = paramFloat;

                return;

                case 2:
                this.m22 = paramFloat;

                return;

            }

            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f0"));

        }

        throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f0"));

    }

    public final void getRow(int paramInt, Vector3f paramVector3f) {
        if (paramInt == 0) {
            paramVector3f.x = this.m00;

            paramVector3f.y = this.m01;

            paramVector3f.z = this.m02;

        } else if (paramInt == 1) {
            paramVector3f.x = this.m10;

            paramVector3f.y = this.m11;

            paramVector3f.z = this.m12;

        } else if (paramInt == 2) {
            paramVector3f.x = this.m20;

            paramVector3f.y = this.m21;

            paramVector3f.z = this.m22;

        } else {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f1"));

        }

    }

    public final void getRow(int paramInt, float[] paramArrayOffloat) {
        if (paramInt == 0) {
            paramArrayOffloat[0] = this.m00;

            paramArrayOffloat[1] = this.m01;

            paramArrayOffloat[2] = this.m02;

        } else if (paramInt == 1) {
            paramArrayOffloat[0] = this.m10;

            paramArrayOffloat[1] = this.m11;

            paramArrayOffloat[2] = this.m12;

        } else if (paramInt == 2) {
            paramArrayOffloat[0] = this.m20;

            paramArrayOffloat[1] = this.m21;

            paramArrayOffloat[2] = this.m22;

        } else {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f1"));

        }

    }

    public final void getColumn(int paramInt, Vector3f paramVector3f) {
        if (paramInt == 0) {
            paramVector3f.x = this.m00;

            paramVector3f.y = this.m10;

            paramVector3f.z = this.m20;

        } else if (paramInt == 1) {
            paramVector3f.x = this.m01;

            paramVector3f.y = this.m11;

            paramVector3f.z = this.m21;

        } else if (paramInt == 2) {
            paramVector3f.x = this.m02;

            paramVector3f.y = this.m12;

            paramVector3f.z = this.m22;

        } else {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f3"));

        }

    }

    public final void getColumn(int paramInt, float[] paramArrayOffloat) {
        if (paramInt == 0) {
            paramArrayOffloat[0] = this.m00;

            paramArrayOffloat[1] = this.m10;

            paramArrayOffloat[2] = this.m20;

        } else if (paramInt == 1) {
            paramArrayOffloat[0] = this.m01;

            paramArrayOffloat[1] = this.m11;

            paramArrayOffloat[2] = this.m21;

        } else if (paramInt == 2) {
            paramArrayOffloat[0] = this.m02;

            paramArrayOffloat[1] = this.m12;

            paramArrayOffloat[2] = this.m22;

        } else {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f3"));

        }

    }

    public final float getElement(int paramInt1, int paramInt2) {
        switch (paramInt1) {
            case 0:
            switch (paramInt2) {
                case 0:
                return this.m00;

                case 1:
                return this.m01;

                case 2:
                return this.m02;

            }

            break;

            case 1:
            switch (paramInt2) {
                case 0:
                return this.m10;

                case 1:
                return this.m11;

                case 2:
                return this.m12;

            }

            break;

            case 2:
            switch (paramInt2) {
                case 0:
                return this.m20;

                case 1:
                return this.m21;

                case 2:
                return this.m22;

            }

            break;

        }

        throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f5"));

    }

    public final void setRow(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3) {
        switch (paramInt) {
            case 0:
            this.m00 = paramFloat1;

            this.m01 = paramFloat2;

            this.m02 = paramFloat3;

            return;

            case 1:
            this.m10 = paramFloat1;

            this.m11 = paramFloat2;

            this.m12 = paramFloat3;

            return;

            case 2:
            this.m20 = paramFloat1;

            this.m21 = paramFloat2;

            this.m22 = paramFloat3;

            return;

        }

        throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f6"));

    }

    public final void setRow(int paramInt, Vector3f paramVector3f) {
        switch (paramInt) {
            case 0:
            this.m00 = paramVector3f.x;

            this.m01 = paramVector3f.y;

            this.m02 = paramVector3f.z;

            return;

            case 1:
            this.m10 = paramVector3f.x;

            this.m11 = paramVector3f.y;

            this.m12 = paramVector3f.z;

            return;

            case 2:
            this.m20 = paramVector3f.x;

            this.m21 = paramVector3f.y;

            this.m22 = paramVector3f.z;

            return;

        }

        throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f6"));

    }

    public final void setRow(int paramInt, float[] paramArrayOffloat) {
        switch (paramInt) {
            case 0:
            this.m00 = paramArrayOffloat[0];

            this.m01 = paramArrayOffloat[1];

            this.m02 = paramArrayOffloat[2];

            return;

            case 1:
            this.m10 = paramArrayOffloat[0];

            this.m11 = paramArrayOffloat[1];

            this.m12 = paramArrayOffloat[2];

            return;

            case 2:
            this.m20 = paramArrayOffloat[0];

            this.m21 = paramArrayOffloat[1];

            this.m22 = paramArrayOffloat[2];

            return;

        }

        throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f6"));

    }

    public final void setColumn(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3) {
        switch (paramInt) {
            case 0:
            this.m00 = paramFloat1;

            this.m10 = paramFloat2;

            this.m20 = paramFloat3;

            return;

            case 1:
            this.m01 = paramFloat1;

            this.m11 = paramFloat2;

            this.m21 = paramFloat3;

            return;

            case 2:
            this.m02 = paramFloat1;

            this.m12 = paramFloat2;

            this.m22 = paramFloat3;

            return;

        }

        throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f9"));

    }

    public final void setColumn(int paramInt, Vector3f paramVector3f) {
        switch (paramInt) {
            case 0:
            this.m00 = paramVector3f.x;

            this.m10 = paramVector3f.y;

            this.m20 = paramVector3f.z;

            return;

            case 1:
            this.m01 = paramVector3f.x;

            this.m11 = paramVector3f.y;

            this.m21 = paramVector3f.z;

            return;

            case 2:
            this.m02 = paramVector3f.x;

            this.m12 = paramVector3f.y;

            this.m22 = paramVector3f.z;

            return;

        }

        throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f9"));

    }

    public final void setColumn(int paramInt, float[] paramArrayOffloat) {
        switch (paramInt) {
            case 0:
            this.m00 = paramArrayOffloat[0];

            this.m10 = paramArrayOffloat[1];

            this.m20 = paramArrayOffloat[2];

            return;

            case 1:
            this.m01 = paramArrayOffloat[0];

            this.m11 = paramArrayOffloat[1];

            this.m21 = paramArrayOffloat[2];

            return;

            case 2:
            this.m02 = paramArrayOffloat[0];

            this.m12 = paramArrayOffloat[1];

            this.m22 = paramArrayOffloat[2];

            return;

        }

        throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f9"));

    }

    public final float getScale() {
        double[] arrayOfDouble1 = new double[9];

        double[] arrayOfDouble2 = new double[3];

        getScaleRotate(arrayOfDouble2, arrayOfDouble1);

        return (float)Matrix3d.max3(arrayOfDouble2);

    }

    public final void add(float paramFloat) {
        this.m00 += paramFloat;

        this.m01 += paramFloat;

        this.m02 += paramFloat;

        this.m10 += paramFloat;

        this.m11 += paramFloat;

        this.m12 += paramFloat;

        this.m20 += paramFloat;

        this.m21 += paramFloat;

        this.m22 += paramFloat;

    }

    public final void add(float paramFloat, Matrix3f paramMatrix3f) {
        paramMatrix3f.m00 += paramFloat;

        paramMatrix3f.m01 += paramFloat;

        paramMatrix3f.m02 += paramFloat;

        paramMatrix3f.m10 += paramFloat;

        paramMatrix3f.m11 += paramFloat;

        paramMatrix3f.m12 += paramFloat;

        paramMatrix3f.m20 += paramFloat;

        paramMatrix3f.m21 += paramFloat;

        paramMatrix3f.m22 += paramFloat;

    }

    public final void add(Matrix3f paramMatrix3f1, Matrix3f paramMatrix3f2) {
        paramMatrix3f1.m00 += paramMatrix3f2.m00;

        paramMatrix3f1.m01 += paramMatrix3f2.m01;

        paramMatrix3f1.m02 += paramMatrix3f2.m02;

        paramMatrix3f1.m10 += paramMatrix3f2.m10;

        paramMatrix3f1.m11 += paramMatrix3f2.m11;

        paramMatrix3f1.m12 += paramMatrix3f2.m12;

        paramMatrix3f1.m20 += paramMatrix3f2.m20;

        paramMatrix3f1.m21 += paramMatrix3f2.m21;

        paramMatrix3f1.m22 += paramMatrix3f2.m22;

    }

    public final void add(Matrix3f paramMatrix3f) {
        this.m00 += paramMatrix3f.m00;

        this.m01 += paramMatrix3f.m01;

        this.m02 += paramMatrix3f.m02;

        this.m10 += paramMatrix3f.m10;

        this.m11 += paramMatrix3f.m11;

        this.m12 += paramMatrix3f.m12;

        this.m20 += paramMatrix3f.m20;

        this.m21 += paramMatrix3f.m21;

        this.m22 += paramMatrix3f.m22;

    }

    public final void sub(Matrix3f paramMatrix3f1, Matrix3f paramMatrix3f2) {
        paramMatrix3f1.m00 -= paramMatrix3f2.m00;

        paramMatrix3f1.m01 -= paramMatrix3f2.m01;

        paramMatrix3f1.m02 -= paramMatrix3f2.m02;

        paramMatrix3f1.m10 -= paramMatrix3f2.m10;

        paramMatrix3f1.m11 -= paramMatrix3f2.m11;

        paramMatrix3f1.m12 -= paramMatrix3f2.m12;

        paramMatrix3f1.m20 -= paramMatrix3f2.m20;

        paramMatrix3f1.m21 -= paramMatrix3f2.m21;

        paramMatrix3f1.m22 -= paramMatrix3f2.m22;

    }

    public final void sub(Matrix3f paramMatrix3f) {
        this.m00 -= paramMatrix3f.m00;

        this.m01 -= paramMatrix3f.m01;

        this.m02 -= paramMatrix3f.m02;

        this.m10 -= paramMatrix3f.m10;

        this.m11 -= paramMatrix3f.m11;

        this.m12 -= paramMatrix3f.m12;

        this.m20 -= paramMatrix3f.m20;

        this.m21 -= paramMatrix3f.m21;

        this.m22 -= paramMatrix3f.m22;

    }

    public final void transpose() {
        float f = this.m10;

        this.m10 = this.m01;

        this.m01 = f;

        f = this.m20;

        this.m20 = this.m02;

        this.m02 = f;

        f = this.m21;

        this.m21 = this.m12;

        this.m12 = f;

    }

    public final void transpose(Matrix3f paramMatrix3f) {
        if (this != paramMatrix3f) {
            this.m00 = paramMatrix3f.m00;

            this.m01 = paramMatrix3f.m10;

            this.m02 = paramMatrix3f.m20;

            this.m10 = paramMatrix3f.m01;

            this.m11 = paramMatrix3f.m11;

            this.m12 = paramMatrix3f.m21;

            this.m20 = paramMatrix3f.m02;

            this.m21 = paramMatrix3f.m12;

            this.m22 = paramMatrix3f.m22;

        } else {
            transpose();

        }

    }

    public final void set(Quat4f paramQuat4f) {
        this.m00 = 1.0F - 2.0F * paramQuat4f.y * paramQuat4f.y - 2.0F * paramQuat4f.z * paramQuat4f.z;

        this.m10 = 2.0F * (paramQuat4f.x * paramQuat4f.y + paramQuat4f.w * paramQuat4f.z);

        this.m20 = 2.0F * (paramQuat4f.x * paramQuat4f.z - paramQuat4f.w * paramQuat4f.y);

        this.m01 = 2.0F * (paramQuat4f.x * paramQuat4f.y - paramQuat4f.w * paramQuat4f.z);

        this.m11 = 1.0F - 2.0F * paramQuat4f.x * paramQuat4f.x - 2.0F * paramQuat4f.z * paramQuat4f.z;

        this.m21 = 2.0F * (paramQuat4f.y * paramQuat4f.z + paramQuat4f.w * paramQuat4f.x);

        this.m02 = 2.0F * (paramQuat4f.x * paramQuat4f.z + paramQuat4f.w * paramQuat4f.y);

        this.m12 = 2.0F * (paramQuat4f.y * paramQuat4f.z - paramQuat4f.w * paramQuat4f.x);

        this.m22 = 1.0F - 2.0F * paramQuat4f.x * paramQuat4f.x - 2.0F * paramQuat4f.y * paramQuat4f.y;

    }

    public final void set(AxisAngle4f paramAxisAngle4f) {
        float f = (float)Math.sqrt((paramAxisAngle4f.x * paramAxisAngle4f.x + paramAxisAngle4f.y * paramAxisAngle4f.y + paramAxisAngle4f.z * paramAxisAngle4f.z));

        if (f < 1.0E-8D) {
            this.m00 = 1.0F;

            this.m01 = 0.0F;

            this.m02 = 0.0F;

            this.m10 = 0.0F;

            this.m11 = 1.0F;

            this.m12 = 0.0F;

            this.m20 = 0.0F;

            this.m21 = 0.0F;

            this.m22 = 1.0F;

        } else {
            f = 1.0F / f;

            float f1 = paramAxisAngle4f.x * f;

            float f2 = paramAxisAngle4f.y * f;

            float f3 = paramAxisAngle4f.z * f;

            float f4 = (float)Math.sin(paramAxisAngle4f.angle);

            float f5 = (float)Math.cos(paramAxisAngle4f.angle);

            float f6 = 1.0F - f5;

            float f7 = f1 * f3;

            float f8 = f1 * f2;

            float f9 = f2 * f3;

            this.m00 = f6 * f1 * f1 + f5;

            this.m01 = f6 * f8 - f4 * f3;

            this.m02 = f6 * f7 + f4 * f2;

            this.m10 = f6 * f8 + f4 * f3;

            this.m11 = f6 * f2 * f2 + f5;

            this.m12 = f6 * f9 - f4 * f1;

            this.m20 = f6 * f7 - f4 * f2;

            this.m21 = f6 * f9 + f4 * f1;

            this.m22 = f6 * f3 * f3 + f5;

        }

    }

    public final void set(AxisAngle4d paramAxisAngle4d) {
        double d = Math.sqrt(paramAxisAngle4d.x * paramAxisAngle4d.x + paramAxisAngle4d.y * paramAxisAngle4d.y + paramAxisAngle4d.z * paramAxisAngle4d.z);

        if (d < 1.0E-8D) {
            this.m00 = 1.0F;

            this.m01 = 0.0F;

            this.m02 = 0.0F;

            this.m10 = 0.0F;

            this.m11 = 1.0F;

            this.m12 = 0.0F;

            this.m20 = 0.0F;

            this.m21 = 0.0F;

            this.m22 = 1.0F;

        } else {
            d = 1.0D / d;

            double d1 = paramAxisAngle4d.x * d;

            double d2 = paramAxisAngle4d.y * d;

            double d3 = paramAxisAngle4d.z * d;

            double d4 = Math.sin(paramAxisAngle4d.angle);

            double d5 = Math.cos(paramAxisAngle4d.angle);

            double d6 = 1.0D - d5;

            double d7 = d1 * d3;

            double d8 = d1 * d2;

            double d9 = d2 * d3;

            this.m00 = (float)(d6 * d1 * d1 + d5);

            this.m01 = (float)(d6 * d8 - d4 * d3);

            this.m02 = (float)(d6 * d7 + d4 * d2);

            this.m10 = (float)(d6 * d8 + d4 * d3);

            this.m11 = (float)(d6 * d2 * d2 + d5);

            this.m12 = (float)(d6 * d9 - d4 * d1);

            this.m20 = (float)(d6 * d7 - d4 * d2);

            this.m21 = (float)(d6 * d9 + d4 * d1);

            this.m22 = (float)(d6 * d3 * d3 + d5);

        }

    }

    public final void set(Quat4d paramQuat4d) {
        this.m00 = (float)(1.0D - 2.0D * paramQuat4d.y * paramQuat4d.y - 2.0D * paramQuat4d.z * paramQuat4d.z);

        this.m10 = (float)(2.0D * (paramQuat4d.x * paramQuat4d.y + paramQuat4d.w * paramQuat4d.z));

        this.m20 = (float)(2.0D * (paramQuat4d.x * paramQuat4d.z - paramQuat4d.w * paramQuat4d.y));

        this.m01 = (float)(2.0D * (paramQuat4d.x * paramQuat4d.y - paramQuat4d.w * paramQuat4d.z));

        this.m11 = (float)(1.0D - 2.0D * paramQuat4d.x * paramQuat4d.x - 2.0D * paramQuat4d.z * paramQuat4d.z);

        this.m21 = (float)(2.0D * (paramQuat4d.y * paramQuat4d.z + paramQuat4d.w * paramQuat4d.x));

        this.m02 = (float)(2.0D * (paramQuat4d.x * paramQuat4d.z + paramQuat4d.w * paramQuat4d.y));

        this.m12 = (float)(2.0D * (paramQuat4d.y * paramQuat4d.z - paramQuat4d.w * paramQuat4d.x));

        this.m22 = (float)(1.0D - 2.0D * paramQuat4d.x * paramQuat4d.x - 2.0D * paramQuat4d.y * paramQuat4d.y);

    }

    public final void set(float[] paramArrayOffloat) {
        this.m00 = paramArrayOffloat[0];

        this.m01 = paramArrayOffloat[1];

        this.m02 = paramArrayOffloat[2];

        this.m10 = paramArrayOffloat[3];

        this.m11 = paramArrayOffloat[4];

        this.m12 = paramArrayOffloat[5];

        this.m20 = paramArrayOffloat[6];

        this.m21 = paramArrayOffloat[7];

        this.m22 = paramArrayOffloat[8];

    }

    public final void set(Matrix3f paramMatrix3f) {
        this.m00 = paramMatrix3f.m00;

        this.m01 = paramMatrix3f.m01;

        this.m02 = paramMatrix3f.m02;

        this.m10 = paramMatrix3f.m10;

        this.m11 = paramMatrix3f.m11;

        this.m12 = paramMatrix3f.m12;

        this.m20 = paramMatrix3f.m20;

        this.m21 = paramMatrix3f.m21;

        this.m22 = paramMatrix3f.m22;

    }

    public final void set(Matrix3d paramMatrix3d) {
        this.m00 = (float)paramMatrix3d.m00;

        this.m01 = (float)paramMatrix3d.m01;

        this.m02 = (float)paramMatrix3d.m02;

        this.m10 = (float)paramMatrix3d.m10;

        this.m11 = (float)paramMatrix3d.m11;

        this.m12 = (float)paramMatrix3d.m12;

        this.m20 = (float)paramMatrix3d.m20;

        this.m21 = (float)paramMatrix3d.m21;

        this.m22 = (float)paramMatrix3d.m22;

    }

    public final void invert(Matrix3f paramMatrix3f) {
        invertGeneral(paramMatrix3f);

    }

    public final void invert() {
        invertGeneral(this);

    }

    private final void invertGeneral(Matrix3f paramMatrix3f) {
        double[] arrayOfDouble1 = new double[9];

        double[] arrayOfDouble2 = new double[9];

        int[] arrayOfInt = new int[3];

        arrayOfDouble1[0] = paramMatrix3f.m00;

        arrayOfDouble1[1] = paramMatrix3f.m01;

        arrayOfDouble1[2] = paramMatrix3f.m02;

        arrayOfDouble1[3] = paramMatrix3f.m10;

        arrayOfDouble1[4] = paramMatrix3f.m11;

        arrayOfDouble1[5] = paramMatrix3f.m12;

        arrayOfDouble1[6] = paramMatrix3f.m20;

        arrayOfDouble1[7] = paramMatrix3f.m21;

        arrayOfDouble1[8] = paramMatrix3f.m22;

        if (!luDecomposition(arrayOfDouble1, arrayOfInt))
        throw new SingularMatrixException(VecMathI18N.getString("Matrix3f12"));

        for (byte b = 0; b < 9; b++)
        arrayOfDouble2[b] = 0.0D;

        arrayOfDouble2[0] = 1.0D;

        arrayOfDouble2[4] = 1.0D;

        arrayOfDouble2[8] = 1.0D;

        luBacksubstitution(arrayOfDouble1, arrayOfInt, arrayOfDouble2);

        this.m00 = (float)arrayOfDouble2[0];

        this.m01 = (float)arrayOfDouble2[1];

        this.m02 = (float)arrayOfDouble2[2];

        this.m10 = (float)arrayOfDouble2[3];

        this.m11 = (float)arrayOfDouble2[4];

        this.m12 = (float)arrayOfDouble2[5];

        this.m20 = (float)arrayOfDouble2[6];

        this.m21 = (float)arrayOfDouble2[7];

        this.m22 = (float)arrayOfDouble2[8];

    }

    static boolean luDecomposition(double[] paramArrayOfdouble, int[] paramArrayOfint) {
        double[] arrayOfDouble = new double[3];

        int j = 0;

        int k = 0;

        int i = 3;

        while (i-- != 0) {
            double d = 0.0D;

            int m = 3;

            while (m-- != 0) {
                double d1 = paramArrayOfdouble[j++];

                d1 = Math.abs(d1);

                if (d1 > d)
                d = d1;

            }

            if (d == 0.0D)
            return false;

            arrayOfDouble[k++] = 1.0D / d;

        }

        byte b = 0;

        for (i = 0; i < 3; i++) {
            for (j = 0; j < i; j++) {
                int n = b + 3 * j + i;

                double d1 = paramArrayOfdouble[n];

                int m = j;

                int i1 = b + 3 * j;

                for (int i2 = b + i; m-- != 0; i2 += 3) {
                    d1 -= paramArrayOfdouble[i1] * paramArrayOfdouble[i2];

                    i1++;

                }

                paramArrayOfdouble[n] = d1;

            }

            double d = 0.0D;

            k = -1;

            for (j = i; j < 3; j++) {
                int n = b + 3 * j + i;

                double d1 = paramArrayOfdouble[n];

                int m = i;

                int i1 = b + 3 * j;

                for (int i2 = b + i; m-- != 0; i2 += 3) {
                    d1 -= paramArrayOfdouble[i1] * paramArrayOfdouble[i2];

                    i1++;

                }

                paramArrayOfdouble[n] = d1;

                double d2;

                if ((d2 = arrayOfDouble[j] * Math.abs(d1)) >= d) {
                    d = d2;

                    k = j;

                }

            }

            if (k < 0)
            throw new RuntimeException(VecMathI18N.getString("Matrix3f13"));

            if (i != k) {
                int m = 3;

                int n = b + 3 * k;

                int i1 = b + 3 * i;

                while (m-- != 0) {
                    double d1 = paramArrayOfdouble[n];

                    paramArrayOfdouble[n++] = paramArrayOfdouble[i1];

                    paramArrayOfdouble[i1++] = d1;

                }

                arrayOfDouble[k] = arrayOfDouble[i];

            }

            paramArrayOfint[i] = k;

            if (paramArrayOfdouble[b + 3 * i + i] == 0.0D)
            return false;

            if (i != 2) {
                double d1 = 1.0D / paramArrayOfdouble[b + 3 * i + i];

                int m = b + 3 * (i + 1) + i;

                j = 2 - i;

                while (j-- != 0) {
                    paramArrayOfdouble[m] = paramArrayOfdouble[m] * d1;

                    m += 3;

                }

            }

        }

        return true;

    }

    static void luBacksubstitution(double[] paramArrayOfdouble1, int[] paramArrayOfint, double[] paramArrayOfdouble2) {
        byte b2 = 0;

        for (byte b1 = 0; b1 < 3; b1++) {
            byte b4 = b1;

            byte b = -1;

            for (byte b3 = 0; b3 < 3; b3++) {
                int i = paramArrayOfint[b2 + b3];

                double d = paramArrayOfdouble2[b4 + 3 * i];

                paramArrayOfdouble2[b4 + 3 * i] = paramArrayOfdouble2[b4 + 3 * b3];

                if (b >= 0) {
                    int j = b3 * 3;

                    for (byte b6 = b; b6 <= b3 - 1; b6++)
                    d -= paramArrayOfdouble1[j + b6] * paramArrayOfdouble2[b4 + 3 * b6];

                } else if (d != 0.0D) {
                    b = b3;

                }

                paramArrayOfdouble2[b4 + 3 * b3] = d;

            }

            byte b5 = 6;

            paramArrayOfdouble2[b4 + 6] = paramArrayOfdouble2[b4 + 6] / paramArrayOfdouble1[b5 + 2];

            b5 -= 3;

            paramArrayOfdouble2[b4 + 3] = (paramArrayOfdouble2[b4 + 3] - paramArrayOfdouble1[b5 + 2] * paramArrayOfdouble2[b4 + 6]) / paramArrayOfdouble1[b5 + 1];

            b5 -= 3;

            paramArrayOfdouble2[b4 + 0] = (paramArrayOfdouble2[b4 + 0] - paramArrayOfdouble1[b5 + 1] * paramArrayOfdouble2[b4 + 3] - paramArrayOfdouble1[b5 + 2] * paramArrayOfdouble2[b4 + 6]) / paramArrayOfdouble1[b5 + 0];

        }

    }

    public final float determinant() {
        return this.m00 * (this.m11 * this.m22 - this.m12 * this.m21) + this.m01 * (this.m12 * this.m20 - this.m10 * this.m22) + this.m02 * (this.m10 * this.m21 - this.m11 * this.m20);

    }

    public final void set(float paramFloat) {
        this.m00 = paramFloat;

        this.m01 = 0.0F;

        this.m02 = 0.0F;

        this.m10 = 0.0F;

        this.m11 = paramFloat;

        this.m12 = 0.0F;

        this.m20 = 0.0F;

        this.m21 = 0.0F;

        this.m22 = paramFloat;

    }

    public final void rotX(float paramFloat) {
        float f1 = (float)Math.sin(paramFloat);

        float f2 = (float)Math.cos(paramFloat);

        this.m00 = 1.0F;

        this.m01 = 0.0F;

        this.m02 = 0.0F;

        this.m10 = 0.0F;

        this.m11 = f2;

        this.m12 = -f1;

        this.m20 = 0.0F;

        this.m21 = f1;

        this.m22 = f2;

    }

    public final void rotY(float paramFloat) {
        float f1 = (float)Math.sin(paramFloat);

        float f2 = (float)Math.cos(paramFloat);

        this.m00 = f2;

        this.m01 = 0.0F;

        this.m02 = f1;

        this.m10 = 0.0F;

        this.m11 = 1.0F;

        this.m12 = 0.0F;

        this.m20 = -f1;

        this.m21 = 0.0F;

        this.m22 = f2;

    }

    public final void rotZ(float paramFloat) {
        float f1 = (float)Math.sin(paramFloat);

        float f2 = (float)Math.cos(paramFloat);

        this.m00 = f2;

        this.m01 = -f1;

        this.m02 = 0.0F;

        this.m10 = f1;

        this.m11 = f2;

        this.m12 = 0.0F;

        this.m20 = 0.0F;

        this.m21 = 0.0F;

        this.m22 = 1.0F;

    }

    public final void mul(float paramFloat) {
        this.m00 *= paramFloat;

        this.m01 *= paramFloat;

        this.m02 *= paramFloat;

        this.m10 *= paramFloat;

        this.m11 *= paramFloat;

        this.m12 *= paramFloat;

        this.m20 *= paramFloat;

        this.m21 *= paramFloat;

        this.m22 *= paramFloat;

    }

    public final void mul(float paramFloat, Matrix3f paramMatrix3f) {
        this.m00 = paramFloat * paramMatrix3f.m00;

        this.m01 = paramFloat * paramMatrix3f.m01;

        this.m02 = paramFloat * paramMatrix3f.m02;

        this.m10 = paramFloat * paramMatrix3f.m10;

        this.m11 = paramFloat * paramMatrix3f.m11;

        this.m12 = paramFloat * paramMatrix3f.m12;

        this.m20 = paramFloat * paramMatrix3f.m20;

        this.m21 = paramFloat * paramMatrix3f.m21;

        this.m22 = paramFloat * paramMatrix3f.m22;

    }

    public final void mul(Matrix3f paramMatrix3f) {
        float f1 = this.m00 * paramMatrix3f.m00 + this.m01 * paramMatrix3f.m10 + this.m02 * paramMatrix3f.m20;

        float f2 = this.m00 * paramMatrix3f.m01 + this.m01 * paramMatrix3f.m11 + this.m02 * paramMatrix3f.m21;

        float f3 = this.m00 * paramMatrix3f.m02 + this.m01 * paramMatrix3f.m12 + this.m02 * paramMatrix3f.m22;

        float f4 = this.m10 * paramMatrix3f.m00 + this.m11 * paramMatrix3f.m10 + this.m12 * paramMatrix3f.m20;

        float f5 = this.m10 * paramMatrix3f.m01 + this.m11 * paramMatrix3f.m11 + this.m12 * paramMatrix3f.m21;

        float f6 = this.m10 * paramMatrix3f.m02 + this.m11 * paramMatrix3f.m12 + this.m12 * paramMatrix3f.m22;

        float f7 = this.m20 * paramMatrix3f.m00 + this.m21 * paramMatrix3f.m10 + this.m22 * paramMatrix3f.m20;

        float f8 = this.m20 * paramMatrix3f.m01 + this.m21 * paramMatrix3f.m11 + this.m22 * paramMatrix3f.m21;

        float f9 = this.m20 * paramMatrix3f.m02 + this.m21 * paramMatrix3f.m12 + this.m22 * paramMatrix3f.m22;

        this.m00 = f1;

        this.m01 = f2;

        this.m02 = f3;

        this.m10 = f4;

        this.m11 = f5;

        this.m12 = f6;

        this.m20 = f7;

        this.m21 = f8;

        this.m22 = f9;

    }

    public final void mul(Matrix3f paramMatrix3f1, Matrix3f paramMatrix3f2) {
        if (this != paramMatrix3f1 && this != paramMatrix3f2) {
            this.m00 = paramMatrix3f1.m00 * paramMatrix3f2.m00 + paramMatrix3f1.m01 * paramMatrix3f2.m10 + paramMatrix3f1.m02 * paramMatrix3f2.m20;

            this.m01 = paramMatrix3f1.m00 * paramMatrix3f2.m01 + paramMatrix3f1.m01 * paramMatrix3f2.m11 + paramMatrix3f1.m02 * paramMatrix3f2.m21;

            this.m02 = paramMatrix3f1.m00 * paramMatrix3f2.m02 + paramMatrix3f1.m01 * paramMatrix3f2.m12 + paramMatrix3f1.m02 * paramMatrix3f2.m22;

            this.m10 = paramMatrix3f1.m10 * paramMatrix3f2.m00 + paramMatrix3f1.m11 * paramMatrix3f2.m10 + paramMatrix3f1.m12 * paramMatrix3f2.m20;

            this.m11 = paramMatrix3f1.m10 * paramMatrix3f2.m01 + paramMatrix3f1.m11 * paramMatrix3f2.m11 + paramMatrix3f1.m12 * paramMatrix3f2.m21;

            this.m12 = paramMatrix3f1.m10 * paramMatrix3f2.m02 + paramMatrix3f1.m11 * paramMatrix3f2.m12 + paramMatrix3f1.m12 * paramMatrix3f2.m22;

            this.m20 = paramMatrix3f1.m20 * paramMatrix3f2.m00 + paramMatrix3f1.m21 * paramMatrix3f2.m10 + paramMatrix3f1.m22 * paramMatrix3f2.m20;

            this.m21 = paramMatrix3f1.m20 * paramMatrix3f2.m01 + paramMatrix3f1.m21 * paramMatrix3f2.m11 + paramMatrix3f1.m22 * paramMatrix3f2.m21;

            this.m22 = paramMatrix3f1.m20 * paramMatrix3f2.m02 + paramMatrix3f1.m21 * paramMatrix3f2.m12 + paramMatrix3f1.m22 * paramMatrix3f2.m22;

        } else {
            float f1 = paramMatrix3f1.m00 * paramMatrix3f2.m00 + paramMatrix3f1.m01 * paramMatrix3f2.m10 + paramMatrix3f1.m02 * paramMatrix3f2.m20;

            float f2 = paramMatrix3f1.m00 * paramMatrix3f2.m01 + paramMatrix3f1.m01 * paramMatrix3f2.m11 + paramMatrix3f1.m02 * paramMatrix3f2.m21;

            float f3 = paramMatrix3f1.m00 * paramMatrix3f2.m02 + paramMatrix3f1.m01 * paramMatrix3f2.m12 + paramMatrix3f1.m02 * paramMatrix3f2.m22;

            float f4 = paramMatrix3f1.m10 * paramMatrix3f2.m00 + paramMatrix3f1.m11 * paramMatrix3f2.m10 + paramMatrix3f1.m12 * paramMatrix3f2.m20;

            float f5 = paramMatrix3f1.m10 * paramMatrix3f2.m01 + paramMatrix3f1.m11 * paramMatrix3f2.m11 + paramMatrix3f1.m12 * paramMatrix3f2.m21;

            float f6 = paramMatrix3f1.m10 * paramMatrix3f2.m02 + paramMatrix3f1.m11 * paramMatrix3f2.m12 + paramMatrix3f1.m12 * paramMatrix3f2.m22;

            float f7 = paramMatrix3f1.m20 * paramMatrix3f2.m00 + paramMatrix3f1.m21 * paramMatrix3f2.m10 + paramMatrix3f1.m22 * paramMatrix3f2.m20;

            float f8 = paramMatrix3f1.m20 * paramMatrix3f2.m01 + paramMatrix3f1.m21 * paramMatrix3f2.m11 + paramMatrix3f1.m22 * paramMatrix3f2.m21;

            float f9 = paramMatrix3f1.m20 * paramMatrix3f2.m02 + paramMatrix3f1.m21 * paramMatrix3f2.m12 + paramMatrix3f1.m22 * paramMatrix3f2.m22;

            this.m00 = f1;

            this.m01 = f2;

            this.m02 = f3;

            this.m10 = f4;

            this.m11 = f5;

            this.m12 = f6;

            this.m20 = f7;

            this.m21 = f8;

            this.m22 = f9;

        }

    }

    public final void mulNormalize(Matrix3f paramMatrix3f) {
        double[] arrayOfDouble1 = new double[9];

        double[] arrayOfDouble2 = new double[9];

        double[] arrayOfDouble3 = new double[3];

        arrayOfDouble1[0] = (this.m00 * paramMatrix3f.m00 + this.m01 * paramMatrix3f.m10 + this.m02 * paramMatrix3f.m20);

        arrayOfDouble1[1] = (this.m00 * paramMatrix3f.m01 + this.m01 * paramMatrix3f.m11 + this.m02 * paramMatrix3f.m21);

        arrayOfDouble1[2] = (this.m00 * paramMatrix3f.m02 + this.m01 * paramMatrix3f.m12 + this.m02 * paramMatrix3f.m22);

        arrayOfDouble1[3] = (this.m10 * paramMatrix3f.m00 + this.m11 * paramMatrix3f.m10 + this.m12 * paramMatrix3f.m20);

        arrayOfDouble1[4] = (this.m10 * paramMatrix3f.m01 + this.m11 * paramMatrix3f.m11 + this.m12 * paramMatrix3f.m21);

        arrayOfDouble1[5] = (this.m10 * paramMatrix3f.m02 + this.m11 * paramMatrix3f.m12 + this.m12 * paramMatrix3f.m22);

        arrayOfDouble1[6] = (this.m20 * paramMatrix3f.m00 + this.m21 * paramMatrix3f.m10 + this.m22 * paramMatrix3f.m20);

        arrayOfDouble1[7] = (this.m20 * paramMatrix3f.m01 + this.m21 * paramMatrix3f.m11 + this.m22 * paramMatrix3f.m21);

        arrayOfDouble1[8] = (this.m20 * paramMatrix3f.m02 + this.m21 * paramMatrix3f.m12 + this.m22 * paramMatrix3f.m22);

        Matrix3d.compute_svd(arrayOfDouble1, arrayOfDouble3, arrayOfDouble2);

        this.m00 = (float)arrayOfDouble2[0];

        this.m01 = (float)arrayOfDouble2[1];

        this.m02 = (float)arrayOfDouble2[2];

        this.m10 = (float)arrayOfDouble2[3];

        this.m11 = (float)arrayOfDouble2[4];

        this.m12 = (float)arrayOfDouble2[5];

        this.m20 = (float)arrayOfDouble2[6];

        this.m21 = (float)arrayOfDouble2[7];

        this.m22 = (float)arrayOfDouble2[8];

    }

    public final void mulNormalize(Matrix3f paramMatrix3f1, Matrix3f paramMatrix3f2) {
        double[] arrayOfDouble1 = new double[9];

        double[] arrayOfDouble2 = new double[9];

        double[] arrayOfDouble3 = new double[3];

        arrayOfDouble1[0] = (paramMatrix3f1.m00 * paramMatrix3f2.m00 + paramMatrix3f1.m01 * paramMatrix3f2.m10 + paramMatrix3f1.m02 * paramMatrix3f2.m20);

        arrayOfDouble1[1] = (paramMatrix3f1.m00 * paramMatrix3f2.m01 + paramMatrix3f1.m01 * paramMatrix3f2.m11 + paramMatrix3f1.m02 * paramMatrix3f2.m21);

        arrayOfDouble1[2] = (paramMatrix3f1.m00 * paramMatrix3f2.m02 + paramMatrix3f1.m01 * paramMatrix3f2.m12 + paramMatrix3f1.m02 * paramMatrix3f2.m22);

        arrayOfDouble1[3] = (paramMatrix3f1.m10 * paramMatrix3f2.m00 + paramMatrix3f1.m11 * paramMatrix3f2.m10 + paramMatrix3f1.m12 * paramMatrix3f2.m20);

        arrayOfDouble1[4] = (paramMatrix3f1.m10 * paramMatrix3f2.m01 + paramMatrix3f1.m11 * paramMatrix3f2.m11 + paramMatrix3f1.m12 * paramMatrix3f2.m21);

        arrayOfDouble1[5] = (paramMatrix3f1.m10 * paramMatrix3f2.m02 + paramMatrix3f1.m11 * paramMatrix3f2.m12 + paramMatrix3f1.m12 * paramMatrix3f2.m22);

        arrayOfDouble1[6] = (paramMatrix3f1.m20 * paramMatrix3f2.m00 + paramMatrix3f1.m21 * paramMatrix3f2.m10 + paramMatrix3f1.m22 * paramMatrix3f2.m20);

        arrayOfDouble1[7] = (paramMatrix3f1.m20 * paramMatrix3f2.m01 + paramMatrix3f1.m21 * paramMatrix3f2.m11 + paramMatrix3f1.m22 * paramMatrix3f2.m21);

        arrayOfDouble1[8] = (paramMatrix3f1.m20 * paramMatrix3f2.m02 + paramMatrix3f1.m21 * paramMatrix3f2.m12 + paramMatrix3f1.m22 * paramMatrix3f2.m22);

        Matrix3d.compute_svd(arrayOfDouble1, arrayOfDouble3, arrayOfDouble2);

        this.m00 = (float)arrayOfDouble2[0];

        this.m01 = (float)arrayOfDouble2[1];

        this.m02 = (float)arrayOfDouble2[2];

        this.m10 = (float)arrayOfDouble2[3];

        this.m11 = (float)arrayOfDouble2[4];

        this.m12 = (float)arrayOfDouble2[5];

        this.m20 = (float)arrayOfDouble2[6];

        this.m21 = (float)arrayOfDouble2[7];

        this.m22 = (float)arrayOfDouble2[8];

    }

    public final void mulTransposeBoth(Matrix3f paramMatrix3f1, Matrix3f paramMatrix3f2) {
        if (this != paramMatrix3f1 && this != paramMatrix3f2) {
            this.m00 = paramMatrix3f1.m00 * paramMatrix3f2.m00 + paramMatrix3f1.m10 * paramMatrix3f2.m01 + paramMatrix3f1.m20 * paramMatrix3f2.m02;

            this.m01 = paramMatrix3f1.m00 * paramMatrix3f2.m10 + paramMatrix3f1.m10 * paramMatrix3f2.m11 + paramMatrix3f1.m20 * paramMatrix3f2.m12;

            this.m02 = paramMatrix3f1.m00 * paramMatrix3f2.m20 + paramMatrix3f1.m10 * paramMatrix3f2.m21 + paramMatrix3f1.m20 * paramMatrix3f2.m22;

            this.m10 = paramMatrix3f1.m01 * paramMatrix3f2.m00 + paramMatrix3f1.m11 * paramMatrix3f2.m01 + paramMatrix3f1.m21 * paramMatrix3f2.m02;

            this.m11 = paramMatrix3f1.m01 * paramMatrix3f2.m10 + paramMatrix3f1.m11 * paramMatrix3f2.m11 + paramMatrix3f1.m21 * paramMatrix3f2.m12;

            this.m12 = paramMatrix3f1.m01 * paramMatrix3f2.m20 + paramMatrix3f1.m11 * paramMatrix3f2.m21 + paramMatrix3f1.m21 * paramMatrix3f2.m22;

            this.m20 = paramMatrix3f1.m02 * paramMatrix3f2.m00 + paramMatrix3f1.m12 * paramMatrix3f2.m01 + paramMatrix3f1.m22 * paramMatrix3f2.m02;

            this.m21 = paramMatrix3f1.m02 * paramMatrix3f2.m10 + paramMatrix3f1.m12 * paramMatrix3f2.m11 + paramMatrix3f1.m22 * paramMatrix3f2.m12;

            this.m22 = paramMatrix3f1.m02 * paramMatrix3f2.m20 + paramMatrix3f1.m12 * paramMatrix3f2.m21 + paramMatrix3f1.m22 * paramMatrix3f2.m22;

        } else {
            float f1 = paramMatrix3f1.m00 * paramMatrix3f2.m00 + paramMatrix3f1.m10 * paramMatrix3f2.m01 + paramMatrix3f1.m20 * paramMatrix3f2.m02;

            float f2 = paramMatrix3f1.m00 * paramMatrix3f2.m10 + paramMatrix3f1.m10 * paramMatrix3f2.m11 + paramMatrix3f1.m20 * paramMatrix3f2.m12;

            float f3 = paramMatrix3f1.m00 * paramMatrix3f2.m20 + paramMatrix3f1.m10 * paramMatrix3f2.m21 + paramMatrix3f1.m20 * paramMatrix3f2.m22;

            float f4 = paramMatrix3f1.m01 * paramMatrix3f2.m00 + paramMatrix3f1.m11 * paramMatrix3f2.m01 + paramMatrix3f1.m21 * paramMatrix3f2.m02;

            float f5 = paramMatrix3f1.m01 * paramMatrix3f2.m10 + paramMatrix3f1.m11 * paramMatrix3f2.m11 + paramMatrix3f1.m21 * paramMatrix3f2.m12;

            float f6 = paramMatrix3f1.m01 * paramMatrix3f2.m20 + paramMatrix3f1.m11 * paramMatrix3f2.m21 + paramMatrix3f1.m21 * paramMatrix3f2.m22;

            float f7 = paramMatrix3f1.m02 * paramMatrix3f2.m00 + paramMatrix3f1.m12 * paramMatrix3f2.m01 + paramMatrix3f1.m22 * paramMatrix3f2.m02;

            float f8 = paramMatrix3f1.m02 * paramMatrix3f2.m10 + paramMatrix3f1.m12 * paramMatrix3f2.m11 + paramMatrix3f1.m22 * paramMatrix3f2.m12;

            float f9 = paramMatrix3f1.m02 * paramMatrix3f2.m20 + paramMatrix3f1.m12 * paramMatrix3f2.m21 + paramMatrix3f1.m22 * paramMatrix3f2.m22;

            this.m00 = f1;

            this.m01 = f2;

            this.m02 = f3;

            this.m10 = f4;

            this.m11 = f5;

            this.m12 = f6;

            this.m20 = f7;

            this.m21 = f8;

            this.m22 = f9;

        }

    }

    public final void mulTransposeRight(Matrix3f paramMatrix3f1, Matrix3f paramMatrix3f2) {
        if (this != paramMatrix3f1 && this != paramMatrix3f2) {
            this.m00 = paramMatrix3f1.m00 * paramMatrix3f2.m00 + paramMatrix3f1.m01 * paramMatrix3f2.m01 + paramMatrix3f1.m02 * paramMatrix3f2.m02;

            this.m01 = paramMatrix3f1.m00 * paramMatrix3f2.m10 + paramMatrix3f1.m01 * paramMatrix3f2.m11 + paramMatrix3f1.m02 * paramMatrix3f2.m12;

            this.m02 = paramMatrix3f1.m00 * paramMatrix3f2.m20 + paramMatrix3f1.m01 * paramMatrix3f2.m21 + paramMatrix3f1.m02 * paramMatrix3f2.m22;

            this.m10 = paramMatrix3f1.m10 * paramMatrix3f2.m00 + paramMatrix3f1.m11 * paramMatrix3f2.m01 + paramMatrix3f1.m12 * paramMatrix3f2.m02;

            this.m11 = paramMatrix3f1.m10 * paramMatrix3f2.m10 + paramMatrix3f1.m11 * paramMatrix3f2.m11 + paramMatrix3f1.m12 * paramMatrix3f2.m12;

            this.m12 = paramMatrix3f1.m10 * paramMatrix3f2.m20 + paramMatrix3f1.m11 * paramMatrix3f2.m21 + paramMatrix3f1.m12 * paramMatrix3f2.m22;

            this.m20 = paramMatrix3f1.m20 * paramMatrix3f2.m00 + paramMatrix3f1.m21 * paramMatrix3f2.m01 + paramMatrix3f1.m22 * paramMatrix3f2.m02;

            this.m21 = paramMatrix3f1.m20 * paramMatrix3f2.m10 + paramMatrix3f1.m21 * paramMatrix3f2.m11 + paramMatrix3f1.m22 * paramMatrix3f2.m12;

            this.m22 = paramMatrix3f1.m20 * paramMatrix3f2.m20 + paramMatrix3f1.m21 * paramMatrix3f2.m21 + paramMatrix3f1.m22 * paramMatrix3f2.m22;

        } else {
            float f1 = paramMatrix3f1.m00 * paramMatrix3f2.m00 + paramMatrix3f1.m01 * paramMatrix3f2.m01 + paramMatrix3f1.m02 * paramMatrix3f2.m02;

            float f2 = paramMatrix3f1.m00 * paramMatrix3f2.m10 + paramMatrix3f1.m01 * paramMatrix3f2.m11 + paramMatrix3f1.m02 * paramMatrix3f2.m12;

            float f3 = paramMatrix3f1.m00 * paramMatrix3f2.m20 + paramMatrix3f1.m01 * paramMatrix3f2.m21 + paramMatrix3f1.m02 * paramMatrix3f2.m22;

            float f4 = paramMatrix3f1.m10 * paramMatrix3f2.m00 + paramMatrix3f1.m11 * paramMatrix3f2.m01 + paramMatrix3f1.m12 * paramMatrix3f2.m02;

            float f5 = paramMatrix3f1.m10 * paramMatrix3f2.m10 + paramMatrix3f1.m11 * paramMatrix3f2.m11 + paramMatrix3f1.m12 * paramMatrix3f2.m12;

            float f6 = paramMatrix3f1.m10 * paramMatrix3f2.m20 + paramMatrix3f1.m11 * paramMatrix3f2.m21 + paramMatrix3f1.m12 * paramMatrix3f2.m22;

            float f7 = paramMatrix3f1.m20 * paramMatrix3f2.m00 + paramMatrix3f1.m21 * paramMatrix3f2.m01 + paramMatrix3f1.m22 * paramMatrix3f2.m02;

            float f8 = paramMatrix3f1.m20 * paramMatrix3f2.m10 + paramMatrix3f1.m21 * paramMatrix3f2.m11 + paramMatrix3f1.m22 * paramMatrix3f2.m12;

            float f9 = paramMatrix3f1.m20 * paramMatrix3f2.m20 + paramMatrix3f1.m21 * paramMatrix3f2.m21 + paramMatrix3f1.m22 * paramMatrix3f2.m22;

            this.m00 = f1;

            this.m01 = f2;

            this.m02 = f3;

            this.m10 = f4;

            this.m11 = f5;

            this.m12 = f6;

            this.m20 = f7;

            this.m21 = f8;

            this.m22 = f9;

        }

    }

    public final void mulTransposeLeft(Matrix3f paramMatrix3f1, Matrix3f paramMatrix3f2) {
        if (this != paramMatrix3f1 && this != paramMatrix3f2) {
            this.m00 = paramMatrix3f1.m00 * paramMatrix3f2.m00 + paramMatrix3f1.m10 * paramMatrix3f2.m10 + paramMatrix3f1.m20 * paramMatrix3f2.m20;

            this.m01 = paramMatrix3f1.m00 * paramMatrix3f2.m01 + paramMatrix3f1.m10 * paramMatrix3f2.m11 + paramMatrix3f1.m20 * paramMatrix3f2.m21;

            this.m02 = paramMatrix3f1.m00 * paramMatrix3f2.m02 + paramMatrix3f1.m10 * paramMatrix3f2.m12 + paramMatrix3f1.m20 * paramMatrix3f2.m22;

            this.m10 = paramMatrix3f1.m01 * paramMatrix3f2.m00 + paramMatrix3f1.m11 * paramMatrix3f2.m10 + paramMatrix3f1.m21 * paramMatrix3f2.m20;

            this.m11 = paramMatrix3f1.m01 * paramMatrix3f2.m01 + paramMatrix3f1.m11 * paramMatrix3f2.m11 + paramMatrix3f1.m21 * paramMatrix3f2.m21;

            this.m12 = paramMatrix3f1.m01 * paramMatrix3f2.m02 + paramMatrix3f1.m11 * paramMatrix3f2.m12 + paramMatrix3f1.m21 * paramMatrix3f2.m22;

            this.m20 = paramMatrix3f1.m02 * paramMatrix3f2.m00 + paramMatrix3f1.m12 * paramMatrix3f2.m10 + paramMatrix3f1.m22 * paramMatrix3f2.m20;

            this.m21 = paramMatrix3f1.m02 * paramMatrix3f2.m01 + paramMatrix3f1.m12 * paramMatrix3f2.m11 + paramMatrix3f1.m22 * paramMatrix3f2.m21;

            this.m22 = paramMatrix3f1.m02 * paramMatrix3f2.m02 + paramMatrix3f1.m12 * paramMatrix3f2.m12 + paramMatrix3f1.m22 * paramMatrix3f2.m22;

        } else {
            float f1 = paramMatrix3f1.m00 * paramMatrix3f2.m00 + paramMatrix3f1.m10 * paramMatrix3f2.m10 + paramMatrix3f1.m20 * paramMatrix3f2.m20;

            float f2 = paramMatrix3f1.m00 * paramMatrix3f2.m01 + paramMatrix3f1.m10 * paramMatrix3f2.m11 + paramMatrix3f1.m20 * paramMatrix3f2.m21;

            float f3 = paramMatrix3f1.m00 * paramMatrix3f2.m02 + paramMatrix3f1.m10 * paramMatrix3f2.m12 + paramMatrix3f1.m20 * paramMatrix3f2.m22;

            float f4 = paramMatrix3f1.m01 * paramMatrix3f2.m00 + paramMatrix3f1.m11 * paramMatrix3f2.m10 + paramMatrix3f1.m21 * paramMatrix3f2.m20;

            float f5 = paramMatrix3f1.m01 * paramMatrix3f2.m01 + paramMatrix3f1.m11 * paramMatrix3f2.m11 + paramMatrix3f1.m21 * paramMatrix3f2.m21;

            float f6 = paramMatrix3f1.m01 * paramMatrix3f2.m02 + paramMatrix3f1.m11 * paramMatrix3f2.m12 + paramMatrix3f1.m21 * paramMatrix3f2.m22;

            float f7 = paramMatrix3f1.m02 * paramMatrix3f2.m00 + paramMatrix3f1.m12 * paramMatrix3f2.m10 + paramMatrix3f1.m22 * paramMatrix3f2.m20;

            float f8 = paramMatrix3f1.m02 * paramMatrix3f2.m01 + paramMatrix3f1.m12 * paramMatrix3f2.m11 + paramMatrix3f1.m22 * paramMatrix3f2.m21;

            float f9 = paramMatrix3f1.m02 * paramMatrix3f2.m02 + paramMatrix3f1.m12 * paramMatrix3f2.m12 + paramMatrix3f1.m22 * paramMatrix3f2.m22;

            this.m00 = f1;

            this.m01 = f2;

            this.m02 = f3;

            this.m10 = f4;

            this.m11 = f5;

            this.m12 = f6;

            this.m20 = f7;

            this.m21 = f8;

            this.m22 = f9;

        }

    }

    public final void normalize() {
        double[] arrayOfDouble1 = new double[9];

        double[] arrayOfDouble2 = new double[3];

        getScaleRotate(arrayOfDouble2, arrayOfDouble1);

        this.m00 = (float)arrayOfDouble1[0];

        this.m01 = (float)arrayOfDouble1[1];

        this.m02 = (float)arrayOfDouble1[2];

        this.m10 = (float)arrayOfDouble1[3];

        this.m11 = (float)arrayOfDouble1[4];

        this.m12 = (float)arrayOfDouble1[5];

        this.m20 = (float)arrayOfDouble1[6];

        this.m21 = (float)arrayOfDouble1[7];

        this.m22 = (float)arrayOfDouble1[8];

    }

    public final void normalize(Matrix3f paramMatrix3f) {
        double[] arrayOfDouble1 = new double[9];

        double[] arrayOfDouble2 = new double[9];

        double[] arrayOfDouble3 = new double[3];

        arrayOfDouble1[0] = paramMatrix3f.m00;

        arrayOfDouble1[1] = paramMatrix3f.m01;

        arrayOfDouble1[2] = paramMatrix3f.m02;

        arrayOfDouble1[3] = paramMatrix3f.m10;

        arrayOfDouble1[4] = paramMatrix3f.m11;

        arrayOfDouble1[5] = paramMatrix3f.m12;

        arrayOfDouble1[6] = paramMatrix3f.m20;

        arrayOfDouble1[7] = paramMatrix3f.m21;

        arrayOfDouble1[8] = paramMatrix3f.m22;

        Matrix3d.compute_svd(arrayOfDouble1, arrayOfDouble3, arrayOfDouble2);

        this.m00 = (float)arrayOfDouble2[0];

        this.m01 = (float)arrayOfDouble2[1];

        this.m02 = (float)arrayOfDouble2[2];

        this.m10 = (float)arrayOfDouble2[3];

        this.m11 = (float)arrayOfDouble2[4];

        this.m12 = (float)arrayOfDouble2[5];

        this.m20 = (float)arrayOfDouble2[6];

        this.m21 = (float)arrayOfDouble2[7];

        this.m22 = (float)arrayOfDouble2[8];

    }

    public final void normalizeCP() {
        float f = 1.0F / (float)Math.sqrt((this.m00 * this.m00 + this.m10 * this.m10 + this.m20 * this.m20));

        this.m00 *= f;

        this.m10 *= f;

        this.m20 *= f;

        f = 1.0F / (float)Math.sqrt((this.m01 * this.m01 + this.m11 * this.m11 + this.m21 * this.m21));

        this.m01 *= f;

        this.m11 *= f;

        this.m21 *= f;

        this.m02 = this.m10 * this.m21 - this.m11 * this.m20;

        this.m12 = this.m01 * this.m20 - this.m00 * this.m21;

        this.m22 = this.m00 * this.m11 - this.m01 * this.m10;

    }

    public final void normalizeCP(Matrix3f paramMatrix3f) {
        float f = 1.0F / (float)Math.sqrt((paramMatrix3f.m00 * paramMatrix3f.m00 + paramMatrix3f.m10 * paramMatrix3f.m10 + paramMatrix3f.m20 * paramMatrix3f.m20));

        paramMatrix3f.m00 *= f;

        paramMatrix3f.m10 *= f;

        paramMatrix3f.m20 *= f;

        f = 1.0F / (float)Math.sqrt((paramMatrix3f.m01 * paramMatrix3f.m01 + paramMatrix3f.m11 * paramMatrix3f.m11 + paramMatrix3f.m21 * paramMatrix3f.m21));

        paramMatrix3f.m01 *= f;

        paramMatrix3f.m11 *= f;

        paramMatrix3f.m21 *= f;

        this.m02 = this.m10 * this.m21 - this.m11 * this.m20;

        this.m12 = this.m01 * this.m20 - this.m00 * this.m21;

        this.m22 = this.m00 * this.m11 - this.m01 * this.m10;

    }

    public boolean equals(Matrix3f paramMatrix3f) {
        try {
            return (this.m00 == paramMatrix3f.m00 && this.m01 == paramMatrix3f.m01 && this.m02 == paramMatrix3f.m02 && this.m10 == paramMatrix3f.m10 && this.m11 == paramMatrix3f.m11 && this.m12 == paramMatrix3f.m12 && this.m20 == paramMatrix3f.m20 && this.m21 == paramMatrix3f.m21 && this.m22 == paramMatrix3f.m22);

        } catch (NullPointerException nullPointerException) {
            return false;

        }

    }

    public boolean equals(Object paramObject) {
        try {
            Matrix3f matrix3f = (Matrix3f)paramObject;

            return (this.m00 == matrix3f.m00 && this.m01 == matrix3f.m01 && this.m02 == matrix3f.m02 && this.m10 == matrix3f.m10 && this.m11 == matrix3f.m11 && this.m12 == matrix3f.m12 && this.m20 == matrix3f.m20 && this.m21 == matrix3f.m21 && this.m22 == matrix3f.m22);

        } catch (ClassCastException classCastException) {
            return false;

        } catch (NullPointerException nullPointerException) {
            return false;

        }

    }

    public boolean epsilonEquals(Matrix3f paramMatrix3f, float paramFloat) {
        boolean bool = true;

        if (Math.abs(this.m00 - paramMatrix3f.m00) > paramFloat)
        bool = false;

        if (Math.abs(this.m01 - paramMatrix3f.m01) > paramFloat)
        bool = false;

        if (Math.abs(this.m02 - paramMatrix3f.m02) > paramFloat)
        bool = false;

        if (Math.abs(this.m10 - paramMatrix3f.m10) > paramFloat)
        bool = false;

        if (Math.abs(this.m11 - paramMatrix3f.m11) > paramFloat)
        bool = false;

        if (Math.abs(this.m12 - paramMatrix3f.m12) > paramFloat)
        bool = false;

        if (Math.abs(this.m20 - paramMatrix3f.m20) > paramFloat)
        bool = false;

        if (Math.abs(this.m21 - paramMatrix3f.m21) > paramFloat)
        bool = false;

        if (Math.abs(this.m22 - paramMatrix3f.m22) > paramFloat)
        bool = false;

        return bool;

    }

    public int hashCode() {
        long l = 1L;

        l = 31L * l + VecMathUtil.floatToIntBits(this.m00);

        l = 31L * l + VecMathUtil.floatToIntBits(this.m01);

        l = 31L * l + VecMathUtil.floatToIntBits(this.m02);

        l = 31L * l + VecMathUtil.floatToIntBits(this.m10);

        l = 31L * l + VecMathUtil.floatToIntBits(this.m11);

        l = 31L * l + VecMathUtil.floatToIntBits(this.m12);

        l = 31L * l + VecMathUtil.floatToIntBits(this.m20);

        l = 31L * l + VecMathUtil.floatToIntBits(this.m21);

        l = 31L * l + VecMathUtil.floatToIntBits(this.m22);

        return (int)(l ^ l >> 32L);

    }

    public final void setZero() {
        this.m00 = 0.0F;

        this.m01 = 0.0F;

        this.m02 = 0.0F;

        this.m10 = 0.0F;

        this.m11 = 0.0F;

        this.m12 = 0.0F;

        this.m20 = 0.0F;

        this.m21 = 0.0F;

        this.m22 = 0.0F;

    }

    public final void negate() {
        this.m00 = -this.m00;

        this.m01 = -this.m01;

        this.m02 = -this.m02;

        this.m10 = -this.m10;

        this.m11 = -this.m11;

        this.m12 = -this.m12;

        this.m20 = -this.m20;

        this.m21 = -this.m21;

        this.m22 = -this.m22;

    }

    public final void negate(Matrix3f paramMatrix3f) {
        this.m00 = -paramMatrix3f.m00;

        this.m01 = -paramMatrix3f.m01;

        this.m02 = -paramMatrix3f.m02;

        this.m10 = -paramMatrix3f.m10;

        this.m11 = -paramMatrix3f.m11;

        this.m12 = -paramMatrix3f.m12;

        this.m20 = -paramMatrix3f.m20;

        this.m21 = -paramMatrix3f.m21;

        this.m22 = -paramMatrix3f.m22;

    }

    public final void transform(Tuple3f paramTuple3f) {
        float f1 = this.m00 * paramTuple3f.x + this.m01 * paramTuple3f.y + this.m02 * paramTuple3f.z;

        float f2 = this.m10 * paramTuple3f.x + this.m11 * paramTuple3f.y + this.m12 * paramTuple3f.z;

        float f3 = this.m20 * paramTuple3f.x + this.m21 * paramTuple3f.y + this.m22 * paramTuple3f.z;

        paramTuple3f.set(f1, f2, f3);

    }

    public final void transform(Tuple3f paramTuple3f1, Tuple3f paramTuple3f2) {
        float f1 = this.m00 * paramTuple3f1.x + this.m01 * paramTuple3f1.y + this.m02 * paramTuple3f1.z;

        float f2 = this.m10 * paramTuple3f1.x + this.m11 * paramTuple3f1.y + this.m12 * paramTuple3f1.z;

        paramTuple3f2.z = this.m20 * paramTuple3f1.x + this.m21 * paramTuple3f1.y + this.m22 * paramTuple3f1.z;

        paramTuple3f2.x = f1;

        paramTuple3f2.y = f2;

    }

    void getScaleRotate(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2) {
        double[] arrayOfDouble = new double[9];

        arrayOfDouble[0] = this.m00;

        arrayOfDouble[1] = this.m01;

        arrayOfDouble[2] = this.m02;

        arrayOfDouble[3] = this.m10;

        arrayOfDouble[4] = this.m11;

        arrayOfDouble[5] = this.m12;

        arrayOfDouble[6] = this.m20;

        arrayOfDouble[7] = this.m21;

        arrayOfDouble[8] = this.m22;

        Matrix3d.compute_svd(arrayOfDouble, paramArrayOfdouble1, paramArrayOfdouble2);

    }

    public Object clone() {
        Matrix3f matrix3f = null;

        try {
            matrix3f = (Matrix3f)super.clone();

        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();

        }

        return matrix3f;

    }

}
