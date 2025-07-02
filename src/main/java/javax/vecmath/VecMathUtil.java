package javax.vecmath;

class VecMathUtil {
    static int floatToIntBits(float paramFloat) {
        return (paramFloat == 0.0F) ? 0 : Float.floatToIntBits(paramFloat);

    }

    static long doubleToLongBits(double paramDouble) {
        return (paramDouble == 0.0D) ? 0L : Double.doubleToLongBits(paramDouble);

    }

    static final long hashLongBits(long hash, long l) {
        hash *= 31L;

        return hash + l;

    }

    static final long hashFloatBits(long hash, float f) {
        hash *= 31L;

        // Treat 0.0d and -0.0d the same (all zero bits)
        if (f == 0.0f)
        return hash;

        return hash + Float.floatToIntBits(f);

    }

    static final long hashDoubleBits(long hash, double d) {
        hash *= 31L;

        // Treat 0.0d and -0.0d the same (all zero bits)
        if (d == 0.0d)
        return hash;

        return hash + Double.doubleToLongBits(d);

    }

    static final int hashFinish(long hash) {
        return (int)(hash ^ (hash >> 32));

    }

}
