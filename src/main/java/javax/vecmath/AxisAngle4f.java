package javax.vecmath;

import java.io.Serializable;

public class AxisAngle4f implements Serializable, Cloneable {

    static final long serialVersionUID = -163246355858070601L;

    public float x;

    public float y;

    public float z;

    public float angle;

    static final double EPS = 1.0E-6D;

    public AxisAngle4f(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
        this.x = paramFloat1;

        this.y = paramFloat2;

        this.z = paramFloat3;

        this.angle = paramFloat4;

    }

    public AxisAngle4f(float[] paramArrayOffloat) {
        this.x = paramArrayOffloat[0];

        this.y = paramArrayOffloat[1];

        this.z = paramArrayOffloat[2];

        this.angle = paramArrayOffloat[3];

    }

    public AxisAngle4f(AxisAngle4f paramAxisAngle4f) {
        this.x = paramAxisAngle4f.x;

        this.y = paramAxisAngle4f.y;

        this.z = paramAxisAngle4f.z;

        this.angle = paramAxisAngle4f.angle;

    }

    public AxisAngle4f(AxisAngle4d paramAxisAngle4d) {
        this.x = (float)paramAxisAngle4d.x;

        this.y = (float)paramAxisAngle4d.y;

        this.z = (float)paramAxisAngle4d.z;

        this.angle = (float)paramAxisAngle4d.angle;

    }

    public AxisAngle4f(Vector3f paramVector3f, float paramFloat) {
        this.x = paramVector3f.x;

        this.y = paramVector3f.y;

        this.z = paramVector3f.z;

        this.angle = paramFloat;

    }

    public AxisAngle4f() {
        this.x = 0.0F;

        this.y = 0.0F;

        this.z = 1.0F;

        this.angle = 0.0F;

    }

    public final void set(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
        this.x = paramFloat1;

        this.y = paramFloat2;

        this.z = paramFloat3;

        this.angle = paramFloat4;

    }

    public final void set(float[] paramArrayOffloat) {
        this.x = paramArrayOffloat[0];

        this.y = paramArrayOffloat[1];

        this.z = paramArrayOffloat[2];

        this.angle = paramArrayOffloat[3];

    }

    public final void set(AxisAngle4f paramAxisAngle4f) {
        this.x = paramAxisAngle4f.x;

        this.y = paramAxisAngle4f.y;

        this.z = paramAxisAngle4f.z;

        this.angle = paramAxisAngle4f.angle;

    }

    public final void set(AxisAngle4d paramAxisAngle4d) {
        this.x = (float)paramAxisAngle4d.x;

        this.y = (float)paramAxisAngle4d.y;

        this.z = (float)paramAxisAngle4d.z;

        this.angle = (float)paramAxisAngle4d.angle;

    }

    public final void set(Vector3f paramVector3f, float paramFloat) {
        this.x = paramVector3f.x;

        this.y = paramVector3f.y;

        this.z = paramVector3f.z;

        this.angle = paramFloat;

    }

    public final void get(float[] paramArrayOffloat) {
        paramArrayOffloat[0] = this.x;

        paramArrayOffloat[1] = this.y;

        paramArrayOffloat[2] = this.z;

        paramArrayOffloat[3] = this.angle;

    }

    public final void set(Quat4f paramQuat4f) {
        double d = (paramQuat4f.x * paramQuat4f.x + paramQuat4f.y * paramQuat4f.y + paramQuat4f.z * paramQuat4f.z);

        if (d > 1.0E-6D) {
            d = Math.sqrt(d);

            double d1 = 1.0D / d;

            this.x = (float)(paramQuat4f.x * d1);

            this.y = (float)(paramQuat4f.y * d1);

            this.z = (float)(paramQuat4f.z * d1);

            this.angle = (float)(2.0D * Math.atan2(d, paramQuat4f.w));

        } else {
            this.x = 0.0F;

            this.y = 1.0F;

            this.z = 0.0F;

            this.angle = 0.0F;

        }

    }

    public final void set(Quat4d paramQuat4d) {
        double d = paramQuat4d.x * paramQuat4d.x + paramQuat4d.y * paramQuat4d.y + paramQuat4d.z * paramQuat4d.z;

        if (d > 1.0E-6D) {
            d = Math.sqrt(d);

            double d1 = 1.0D / d;

            this.x = (float)(paramQuat4d.x * d1);

            this.y = (float)(paramQuat4d.y * d1);

            this.z = (float)(paramQuat4d.z * d1);

            this.angle = (float)(2.0D * Math.atan2(d, paramQuat4d.w));

        } else {
            this.x = 0.0F;

            this.y = 1.0F;

            this.z = 0.0F;

            this.angle = 0.0F;

        }

    }

    public final void set(Matrix4f paramMatrix4f) {
        Matrix3f matrix3f = new Matrix3f();

        paramMatrix4f.get(matrix3f);

        this.x = matrix3f.m21 - matrix3f.m12;

        this.y = matrix3f.m02 - matrix3f.m20;

        this.z = matrix3f.m10 - matrix3f.m01;

        double d = (this.x * this.x + this.y * this.y + this.z * this.z);

        if (d > 1.0E-6D) {
            d = Math.sqrt(d);

            double d1 = 0.5D * d;

            double d2 = 0.5D * ((matrix3f.m00 + matrix3f.m11 + matrix3f.m22) - 1.0D);

            this.angle = (float)Math.atan2(d1, d2);

            double d3 = 1.0D / d;

            this.x = (float)(this.x * d3);

            this.y = (float)(this.y * d3);

            this.z = (float)(this.z * d3);

        } else {
            this.x = 0.0F;

            this.y = 1.0F;

            this.z = 0.0F;

            this.angle = 0.0F;

        }

    }

    public final void set(Matrix4d paramMatrix4d) {
        Matrix3d matrix3d = new Matrix3d();

        paramMatrix4d.get(matrix3d);

        this.x = (float)(matrix3d.m21 - matrix3d.m12);

        this.y = (float)(matrix3d.m02 - matrix3d.m20);

        this.z = (float)(matrix3d.m10 - matrix3d.m01);

        double d = (this.x * this.x + this.y * this.y + this.z * this.z);

        if (d > 1.0E-6D) {
            d = Math.sqrt(d);

            double d1 = 0.5D * d;

            double d2 = 0.5D * (matrix3d.m00 + matrix3d.m11 + matrix3d.m22 - 1.0D);

            this.angle = (float)Math.atan2(d1, d2);

            double d3 = 1.0D / d;

            this.x = (float)(this.x * d3);

            this.y = (float)(this.y * d3);

            this.z = (float)(this.z * d3);

        } else {
            this.x = 0.0F;

            this.y = 1.0F;

            this.z = 0.0F;

            this.angle = 0.0F;

        }

    }

    public final void set(Matrix3f paramMatrix3f) {
        this.x = paramMatrix3f.m21 - paramMatrix3f.m12;

        this.y = paramMatrix3f.m02 - paramMatrix3f.m20;

        this.z = paramMatrix3f.m10 - paramMatrix3f.m01;

        double d = (this.x * this.x + this.y * this.y + this.z * this.z);

        if (d > 1.0E-6D) {
            d = Math.sqrt(d);

            double d1 = 0.5D * d;

            double d2 = 0.5D * ((paramMatrix3f.m00 + paramMatrix3f.m11 + paramMatrix3f.m22) - 1.0D);

            this.angle = (float)Math.atan2(d1, d2);

            double d3 = 1.0D / d;

            this.x = (float)(this.x * d3);

            this.y = (float)(this.y * d3);

            this.z = (float)(this.z * d3);

        } else {
            this.x = 0.0F;

            this.y = 1.0F;

            this.z = 0.0F;

            this.angle = 0.0F;

        }

    }

    public final void set(Matrix3d paramMatrix3d) {
        this.x = (float)(paramMatrix3d.m21 - paramMatrix3d.m12);

        this.y = (float)(paramMatrix3d.m02 - paramMatrix3d.m20);

        this.z = (float)(paramMatrix3d.m10 - paramMatrix3d.m01);

        double d = (this.x * this.x + this.y * this.y + this.z * this.z);

        if (d > 1.0E-6D) {
            d = Math.sqrt(d);

            double d1 = 0.5D * d;

            double d2 = 0.5D * (paramMatrix3d.m00 + paramMatrix3d.m11 + paramMatrix3d.m22 - 1.0D);

            this.angle = (float)Math.atan2(d1, d2);

            double d3 = 1.0D / d;

            this.x = (float)(this.x * d3);

            this.y = (float)(this.y * d3);

            this.z = (float)(this.z * d3);

        } else {
            this.x = 0.0F;

            this.y = 1.0F;

            this.z = 0.0F;

            this.angle = 0.0F;

        }

    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.angle + ")";

    }

    public boolean equals(AxisAngle4f paramAxisAngle4f) {
        try {
            return (this.x == paramAxisAngle4f.x && this.y == paramAxisAngle4f.y && this.z == paramAxisAngle4f.z && this.angle == paramAxisAngle4f.angle);

        } catch (NullPointerException nullPointerException) {
            return false;

        }

    }

    public boolean equals(Object paramObject) {
        try {
            AxisAngle4f axisAngle4f = (AxisAngle4f)paramObject;

            return (this.x == axisAngle4f.x && this.y == axisAngle4f.y && this.z == axisAngle4f.z && this.angle == axisAngle4f.angle);

        } catch (NullPointerException nullPointerException) {
            return false;

        } catch (ClassCastException classCastException) {
            return false;

        }

    }

    public boolean epsilonEquals(AxisAngle4f paramAxisAngle4f, float paramFloat) {
        float f = this.x - paramAxisAngle4f.x;

        if (((f < 0.0F) ? -f : f) > paramFloat)
        return false;

        f = this.y - paramAxisAngle4f.y;

        if (((f < 0.0F) ? -f : f) > paramFloat)
        return false;

        f = this.z - paramAxisAngle4f.z;

        if (((f < 0.0F) ? -f : f) > paramFloat)
        return false;

        f = this.angle - paramAxisAngle4f.angle;

        return !(((f < 0.0F) ? -f : f) > paramFloat);

    }

    public int hashCode() {
        long l = 1L;

        l = 31L * l + VecMathUtil.floatToIntBits(this.x);

        l = 31L * l + VecMathUtil.floatToIntBits(this.y);

        l = 31L * l + VecMathUtil.floatToIntBits(this.z);

        l = 31L * l + VecMathUtil.floatToIntBits(this.angle);

        return (int)(l ^ l >> 32L);

    }

    public Object clone() {
        try {
            return super.clone();

        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();

        }

    }

}
