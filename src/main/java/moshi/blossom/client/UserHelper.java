package moshi.blossom.client;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.EntityLivingBase;

public class UserHelper

{
    public final List<String> friendList = new ArrayList<>(), targetList = new ArrayList<>();

    public boolean isFriend(EntityLivingBase entity) {
        return (this.friendList.contains(entity.getName()) || this.friendList.contains(entity.getDisplayName().getUnformattedText()));
    }

    public boolean isTarget(EntityLivingBase entity) {
        return (this.targetList.contains(entity.getName()) || this.targetList.contains(entity.getDisplayName().getUnformattedText()));
    }
}
