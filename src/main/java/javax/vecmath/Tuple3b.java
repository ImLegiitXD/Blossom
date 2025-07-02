package javax.vecmath;

import java.io.Serializable;

public abstract class Tuple3b implements Serializable, Cloneable {
    static final long serialVersionUID = -483782685323607044L;

    public byte x;

    public byte y;

    public byte z;

    public Tuple3b(byte paramByte1, byte paramByte2, byte paramByte3) {
        this.x = paramByte1;

        this.y = paramByte2;

        this.z = paramByte3;

    }

    public Tuple3b(byte[] paramArrayOfbyte) {
        this.x = paramArrayOfbyte[0];

        this.y = paramArrayOfbyte[1];

        this.z = paramArrayOfbyte[2];

    }

    public Tuple3b(Tuple3b paramTuple3b) {
        this.x = paramTuple3b.x;

        this.y = paramTuple3b.y;

        this.z = paramTuple3b.z;

    }

    public Tuple3b() {
        this.x = 0;

        this.y = 0;

        this.z = 0;

    }

    public String toString() {
        return "(" + (this.x & 0xFF) + ", " + (this.y & 0xFF) + ", " + (this.z & 0xFF) + ")";

    }

    public final void get(byte[] paramArrayOfbyte) {
        paramArrayOfbyte[0] = this.x;

        paramArrayOfbyte[1] = this.y;

        paramArrayOfbyte[2] = this.z;

    }

    public final void get(Tuple3b paramTuple3b) {
        paramTuple3b.x = this.x;

        paramTuple3b.y = this.y;

        paramTuple3b.z = this.z;

    }

    public final void set(Tuple3b paramTuple3b) {
        this.x = paramTuple3b.x;

        this.y = paramTuple3b.y;

        this.z = paramTuple3b.z;

    }

    public final void set(byte[] paramArrayOfbyte) {
        this.x = paramArrayOfbyte[0];

        this.y = paramArrayOfbyte[1];

        this.z = paramArrayOfbyte[2];

    }

    public boolean equals(Tuple3b paramTuple3b) {
        try {
            return (this.x == paramTuple3b.x && this.y == paramTuple3b.y && this.z == paramTuple3b.z);

        } catch (NullPointerException nullPointerException) {
            return false;

        }

    }

    public boolean equals(Object paramObject) {
        try {
            Tuple3b tuple3b = (Tuple3b)paramObject;

            return (this.x == tuple3b.x && this.y == tuple3b.y && this.z == tuple3b.z);

        } catch (NullPointerException nullPointerException) {
            return false;

        } catch (ClassCastException classCastException) {
            return false;

        }

    }

    public int hashCode() {
        return (this.x & 0xFF) << 0 | (this.y & 0xFF) << 8 | (this.z & 0xFF) << 16;

    }

    public Object clone() {
        try {
            return super.clone();

        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();

        }

    }

}
