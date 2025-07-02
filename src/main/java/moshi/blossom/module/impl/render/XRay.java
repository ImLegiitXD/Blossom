package moshi.blossom.module.impl.render;

import com.google.common.collect.Lists;
import moshi.blossom.module.Module;
import moshi.blossom.option.Option;
import moshi.blossom.option.impl.NumberOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class XRay extends Module {

    public final NumberOption opacityOption;

    public final List<Integer> KEY_IDS;

    public final Set<Integer> blockIDs;

    public XRay() {
        super("XRay", "X Ray", Module.Category.RENDER);

        this.opacityOption = new NumberOption("OPACITY", "Opacity", 110.0, 0.0, 255.0, 1.0);

        this.KEY_IDS = Lists.newArrayList(
        10, 11, 8, 9, 14, 15, 16, 21, 41,
        42, 46, 48, 52, 56, 57, 61, 62, 73, 74, 84,
        89, 103, 116, 117, 118, 120, 129, 133, 137,
        145, 152, 153, 154
        );

        this.blockIDs = new HashSet<>();

        setupOptions(opacityOption);

    }

    @Override
    public void onEnable() {
        super.onEnable();

        blockIDs.clear();

        try {
            blockIDs.addAll(KEY_IDS);

        } catch (Exception e) {
            e.printStackTrace();

        }

        mc.renderGlobal.loadRenderers();

    }

    @Override
    public void onDisable() {
        super.onDisable();

        mc.renderGlobal.loadRenderers();

    }

}
