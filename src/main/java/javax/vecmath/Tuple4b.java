package javax.vecmath;

import java.io.Serializable;

public abstract class Tuple4b implements Serializable, Cloneable {
    static final long serialVersionUID = -8226727741811898211L;

    public byte x;

    public byte y;

    public byte z;

    public byte w;

    public Tuple4b(byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4) {
        this.x = paramByte1;

        this.y = paramByte2;

        this.z = paramByte3;

        this.w = paramByte4;

    }

    public Tuple4b(byte[] paramArrayOfbyte) {
        this.x = paramArrayOfbyte[0];

        this.y = paramArrayOfbyte[1];

        this.z = paramArrayOfbyte[2];

        this.w = paramArrayOfbyte[3];

    }

    public Tuple4b(Tuple4b paramTuple4b) {
        this.x = paramTuple4b.x;

        this.y = paramTuple4b.y;

        this.z = paramTuple4b.z;

        this.w = paramTuple4b.w;

    }

    public Tuple4b() {
        this.x = 0;

        this.y = 0;

        this.z = 0;

        this.w = 0;

    }

    public String toString() {
        return "(" + (this.x & 0xFF) + ", " + (this.y & 0xFF) + ", " + (this.z & 0xFF) + ", " + (this.w & 0xFF) + ")";

    }

    public final void get(byte[] paramArrayOfbyte) {
        paramArrayOfbyte[0] = this.x;

        paramArrayOfbyte[1] = this.y;

        paramArrayOfbyte[2] = this.z;

        paramArrayOfbyte[3] = this.w;

    }

    public final void get(Tuple4b paramTuple4b) {
        paramTuple4b.x = this.x;

        paramTuple4b.y = this.y;

        paramTuple4b.z = this.z;

        paramTuple4b.w = this.w;

    }

    public final void set(Tuple4b paramTuple4b) {
        this.x = paramTuple4b.x;

        this.y = paramTuple4b.y;

        this.z = paramTuple4b.z;

        this.w = paramTuple4b.w;

    }

    public final void set(byte[] paramArrayOfbyte) {
        this.x = paramArrayOfbyte[0];

        this.y = paramArrayOfbyte[1];

        this.z = paramArrayOfbyte[2];

        this.w = paramArrayOfbyte[3];

    }

    public boolean equals(Tuple4b paramTuple4b) {
        try {
            return (this.x == paramTuple4b.x && this.y == paramTuple4b.y && this.z == paramTuple4b.z && this.w == paramTuple4b.w);

        } catch (NullPointerException nullPointerException) {
            return false;

        }

    }

    public boolean equals(Object paramObject) {
        try {
            Tuple4b tuple4b = (Tuple4b)paramObject;

            return (this.x == tuple4b.x && this.y == tuple4b.y && this.z == tuple4b.z && this.w == tuple4b.w);

        } catch (NullPointerException nullPointerException) {
            return false;

        } catch (ClassCastException classCastException) {
            return false;

        }

    }

    public int hashCode() {
        return (this.x & 0xFF) << 0 | (this.y & 0xFF) << 8 | (this.z & 0xFF) << 16 | (this.w & 0xFF) << 24;

    }

    public Object clone() {
        try {
            return super.clone();

        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();

        }

    }

}
