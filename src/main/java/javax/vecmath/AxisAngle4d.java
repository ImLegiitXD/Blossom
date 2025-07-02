package javax.vecmath;

import java.io.Serializable;

public class AxisAngle4d implements Serializable, Cloneable {

    static final long serialVersionUID = 3644296204459140589L;

    public double x;

    public double y;

    public double z;

    public double angle;

    static final double EPS = 1.0E-6D;

    public AxisAngle4d(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
        this.x = paramDouble1;

        this.y = paramDouble2;

        this.z = paramDouble3;

        this.angle = paramDouble4;

    }

    public AxisAngle4d(double[] paramArrayOfdouble) {
        this.x = paramArrayOfdouble[0];

        this.y = paramArrayOfdouble[1];

        this.z = paramArrayOfdouble[2];

        this.angle = paramArrayOfdouble[3];

    }

    public AxisAngle4d(AxisAngle4d paramAxisAngle4d) {
        this.x = paramAxisAngle4d.x;

        this.y = paramAxisAngle4d.y;

        this.z = paramAxisAngle4d.z;

        this.angle = paramAxisAngle4d.angle;

    }

    public AxisAngle4d(AxisAngle4f paramAxisAngle4f) {
        this.x = paramAxisAngle4f.x;

        this.y = paramAxisAngle4f.y;

        this.z = paramAxisAngle4f.z;

        this.angle = paramAxisAngle4f.angle;

    }

    public AxisAngle4d(Vector3d paramVector3d, double paramDouble) {
        this.x = paramVector3d.x;

        this.y = paramVector3d.y;

        this.z = paramVector3d.z;

        this.angle = paramDouble;

    }

    public AxisAngle4d() {
        this.x = 0.0D;

        this.y = 0.0D;

        this.z = 1.0D;

        this.angle = 0.0D;

    }

    public final void set(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
        this.x = paramDouble1;

        this.y = paramDouble2;

        this.z = paramDouble3;

        this.angle = paramDouble4;

    }

    public final void set(double[] paramArrayOfdouble) {
        this.x = paramArrayOfdouble[0];

        this.y = paramArrayOfdouble[1];

        this.z = paramArrayOfdouble[2];

        this.angle = paramArrayOfdouble[3];

    }

    public final void set(AxisAngle4d paramAxisAngle4d) {
        this.x = paramAxisAngle4d.x;

        this.y = paramAxisAngle4d.y;

        this.z = paramAxisAngle4d.z;

        this.angle = paramAxisAngle4d.angle;

    }

    public final void set(AxisAngle4f paramAxisAngle4f) {
        this.x = paramAxisAngle4f.x;

        this.y = paramAxisAngle4f.y;

        this.z = paramAxisAngle4f.z;

        this.angle = paramAxisAngle4f.angle;

    }

    public final void set(Vector3d paramVector3d, double paramDouble) {
        this.x = paramVector3d.x;

        this.y = paramVector3d.y;

        this.z = paramVector3d.z;

        this.angle = paramDouble;

    }

    public final void get(double[] paramArrayOfdouble) {
        paramArrayOfdouble[0] = this.x;

        paramArrayOfdouble[1] = this.y;

        paramArrayOfdouble[2] = this.z;

        paramArrayOfdouble[3] = this.angle;

    }

    public final void set(Matrix4f paramMatrix4f) {
        Matrix3d matrix3d = new Matrix3d();

        paramMatrix4f.get(matrix3d);

        this.x = (float)(matrix3d.m21 - matrix3d.m12);

        this.y = (float)(matrix3d.m02 - matrix3d.m20);

        this.z = (float)(matrix3d.m10 - matrix3d.m01);

        double d = this.x * this.x + this.y * this.y + this.z * this.z;

        if (d > 1.0E-6D) {
            d = Math.sqrt(d);

            double d1 = 0.5D * d;

            double d2 = 0.5D * (matrix3d.m00 + matrix3d.m11 + matrix3d.m22 - 1.0D);

            this.angle = (float)Math.atan2(d1, d2);

            double d3 = 1.0D / d;

            this.x *= d3;

            this.y *= d3;

            this.z *= d3;

        } else {
            this.x = 0.0D;

            this.y = 1.0D;

            this.z = 0.0D;

            this.angle = 0.0D;

        }

    }

    public final void set(Matrix4d paramMatrix4d) {
        Matrix3d matrix3d = new Matrix3d();

        paramMatrix4d.get(matrix3d);

        this.x = (float)(matrix3d.m21 - matrix3d.m12);

        this.y = (float)(matrix3d.m02 - matrix3d.m20);

        this.z = (float)(matrix3d.m10 - matrix3d.m01);

        double d = this.x * this.x + this.y * this.y + this.z * this.z;

        if (d > 1.0E-6D) {
            d = Math.sqrt(d);

            double d1 = 0.5D * d;

            double d2 = 0.5D * (matrix3d.m00 + matrix3d.m11 + matrix3d.m22 - 1.0D);

            this.angle = (float)Math.atan2(d1, d2);

            double d3 = 1.0D / d;

            this.x *= d3;

            this.y *= d3;

            this.z *= d3;

        } else {
            this.x = 0.0D;

            this.y = 1.0D;

            this.z = 0.0D;

            this.angle = 0.0D;

        }

    }

    public final void set(Matrix3f paramMatrix3f) {
        this.x = (paramMatrix3f.m21 - paramMatrix3f.m12);

        this.y = (paramMatrix3f.m02 - paramMatrix3f.m20);

        this.z = (paramMatrix3f.m10 - paramMatrix3f.m01);

        double d = this.x * this.x + this.y * this.y + this.z * this.z;

        if (d > 1.0E-6D) {
            d = Math.sqrt(d);

            double d1 = 0.5D * d;

            double d2 = 0.5D * ((paramMatrix3f.m00 + paramMatrix3f.m11 + paramMatrix3f.m22) - 1.0D);

            this.angle = (float)Math.atan2(d1, d2);

            double d3 = 1.0D / d;

            this.x *= d3;

            this.y *= d3;

            this.z *= d3;

        } else {
            this.x = 0.0D;

            this.y = 1.0D;

            this.z = 0.0D;

            this.angle = 0.0D;

        }

    }

    public final void set(Matrix3d paramMatrix3d) {
        this.x = (float)(paramMatrix3d.m21 - paramMatrix3d.m12);

        this.y = (float)(paramMatrix3d.m02 - paramMatrix3d.m20);

        this.z = (float)(paramMatrix3d.m10 - paramMatrix3d.m01);

        double d = this.x * this.x + this.y * this.y + this.z * this.z;

        if (d > 1.0E-6D) {
            d = Math.sqrt(d);

            double d1 = 0.5D * d;

            double d2 = 0.5D * (paramMatrix3d.m00 + paramMatrix3d.m11 + paramMatrix3d.m22 - 1.0D);

            this.angle = (float)Math.atan2(d1, d2);

            double d3 = 1.0D / d;

            this.x *= d3;

            this.y *= d3;

            this.z *= d3;

        } else {
            this.x = 0.0D;

            this.y = 1.0D;

            this.z = 0.0D;

            this.angle = 0.0D;

        }

    }

    public final void set(Quat4f paramQuat4f) {
        double d = (paramQuat4f.x * paramQuat4f.x + paramQuat4f.y * paramQuat4f.y + paramQuat4f.z * paramQuat4f.z);

        if (d > 1.0E-6D) {
            d = Math.sqrt(d);

            double d1 = 1.0D / d;

            this.x = paramQuat4f.x * d1;

            this.y = paramQuat4f.y * d1;

            this.z = paramQuat4f.z * d1;

            this.angle = 2.0D * Math.atan2(d, paramQuat4f.w);

        } else {
            this.x = 0.0D;

            this.y = 1.0D;

            this.z = 0.0D;

            this.angle = 0.0D;

        }

    }

    public final void set(Quat4d paramQuat4d) {
        double d = paramQuat4d.x * paramQuat4d.x + paramQuat4d.y * paramQuat4d.y + paramQuat4d.z * paramQuat4d.z;

        if (d > 1.0E-6D) {
            d = Math.sqrt(d);

            double d1 = 1.0D / d;

            this.x = paramQuat4d.x * d1;

            this.y = paramQuat4d.y * d1;

            this.z = paramQuat4d.z * d1;

            this.angle = 2.0D * Math.atan2(d, paramQuat4d.w);

        } else {
            this.x = 0.0D;

            this.y = 1.0D;

            this.z = 0.0D;

            this.angle = 0.0D;

        }

    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.angle + ")";

    }

    public boolean equals(AxisAngle4d paramAxisAngle4d) {
        try {
            return (this.x == paramAxisAngle4d.x && this.y == paramAxisAngle4d.y && this.z == paramAxisAngle4d.z && this.angle == paramAxisAngle4d.angle);

        } catch (NullPointerException nullPointerException) {
            return false;

        }

    }

    public boolean equals(Object paramObject) {
        try {
            AxisAngle4d axisAngle4d = (AxisAngle4d)paramObject;

            return (this.x == axisAngle4d.x && this.y == axisAngle4d.y && this.z == axisAngle4d.z && this.angle == axisAngle4d.angle);

        } catch (NullPointerException nullPointerException) {
            return false;

        } catch (ClassCastException classCastException) {
            return false;

        }

    }

    public boolean epsilonEquals(AxisAngle4d paramAxisAngle4d, double paramDouble) {
        double d = this.x - paramAxisAngle4d.x;

        if (((d < 0.0D) ? -d : d) > paramDouble)
        return false;

        d = this.y - paramAxisAngle4d.y;

        if (((d < 0.0D) ? -d : d) > paramDouble)
        return false;

        d = this.z - paramAxisAngle4d.z;

        if (((d < 0.0D) ? -d : d) > paramDouble)
        return false;

        d = this.angle - paramAxisAngle4d.angle;

        return !(((d < 0.0D) ? -d : d) > paramDouble);

    }

    public int hashCode() {
        long l = 1L;

        l = 31L * l + VecMathUtil.doubleToLongBits(this.x);

        l = 31L * l + VecMathUtil.doubleToLongBits(this.y);

        l = 31L * l + VecMathUtil.doubleToLongBits(this.z);

        l = 31L * l + VecMathUtil.doubleToLongBits(this.angle);

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
