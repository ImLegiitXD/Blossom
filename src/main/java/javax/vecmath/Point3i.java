package javax.vecmath;

import java.io.Serializable;

public class Point3i extends Tuple3i implements Serializable {

    static final long serialVersionUID = 6149289077348153921L;

    public Point3i(int paramInt1, int paramInt2, int paramInt3) {
        super(paramInt1, paramInt2, paramInt3);

    }

    public Point3i(int[] paramArrayOfint) {
        super(paramArrayOfint);

    }

    public Point3i(Tuple3i paramTuple3i) {
        super(paramTuple3i);

    }

public Point3i() {}

}
