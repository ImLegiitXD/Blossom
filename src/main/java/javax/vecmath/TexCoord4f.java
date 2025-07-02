package javax.vecmath;

import java.io.Serializable;

public class TexCoord4f extends Tuple4f implements Serializable {

    static final long serialVersionUID = -3517736544731446513L;

    public TexCoord4f(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
        super(paramFloat1, paramFloat2, paramFloat3, paramFloat4);

    }

    public TexCoord4f(float[] paramArrayOffloat) {
        super(paramArrayOffloat);

    }

    public TexCoord4f(TexCoord4f paramTexCoord4f) {
        super(paramTexCoord4f);

    }

    public TexCoord4f(Tuple4f paramTuple4f) {
        super(paramTuple4f);

    }

    public TexCoord4f(Tuple4d paramTuple4d) {
        super(paramTuple4d);

    }

public TexCoord4f() {}

}
