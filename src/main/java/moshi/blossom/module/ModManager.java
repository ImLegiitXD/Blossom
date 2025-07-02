package moshi.blossom.module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import moshi.blossom.Blossom;
import moshi.blossom.module.impl.combat.*;
import moshi.blossom.module.impl.exploit.*;
import moshi.blossom.module.impl.misc.DoggoBan;
import moshi.blossom.module.impl.movement.*;
import moshi.blossom.module.impl.player.*;
import moshi.blossom.module.impl.render.*;

public class ModManager {

    public List<Module> moduleList = new ArrayList<>();

    public void init() {
        this.moduleList.addAll(Arrays.<Module>asList(
        new AutoSword(),
        new Criticals(),
        new KillAura(),
        new AntiBot(),
        new AutoClick(),
        new InfiniteAura(),
        new Disabler(),
        new Debug(),
        new BedBreaker(),
        new AntiVoid(),
        new Flight(),
        new Speed(),
        new Sprint(),
        new NoSlow(),
        new Timer(),
        new LongJump(),
        new AntiLava(),
        new AutoArmor(),
        new AutoTool(),
        new FastMine(),
        new Gameplay(),
        new InvManager(),
        new NoFall(),
        new Scaffold(),
        new Scaffold2(),
        new Stealer(),
        new Velocity(),
        new FastUse(),
        new FastBow(),
        new Regen(),
        new Ambience(),
        new ESP(),
        new HUD(),
        new Outlines(),
        new XRay(),
        new Radar(),
        new Camera(),
        new Crosshair(),
        new DoggoBan()
        ));

    }

    public static List<Module> getMods(Module.Category category) {
        List<Module> mods = new ArrayList<>(Blossom.INSTANCE.getModManager().moduleList);

        mods.removeIf(module -> (module.getCategory() != category));

        return mods;

    }

    public static Module getMod(String name) {
        for (Module module : Blossom.INSTANCE.getModManager().moduleList) {
            if (module.getName().equalsIgnoreCase(name))
            return module;

        }

        throw new IllegalStateException("bad arg bro");

    }

}
