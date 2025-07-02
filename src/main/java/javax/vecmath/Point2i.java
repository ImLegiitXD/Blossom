package javax.vecmath;

import java.io.Serializable;

public class Point2i extends Tuple2i implements Serializable {

    static final long serialVersionUID = 9208072376494084954L;

    public Point2i(int paramInt1, int paramInt2) {
        super(paramInt1, paramInt2);

    }

    public Point2i(int[] paramArrayOfint) {
        super(paramArrayOfint);

    }

    public Point2i(Tuple2i paramTuple2i) {
        super(paramTuple2i);

    }

public Point2i() {}

}
