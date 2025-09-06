package moshi.blossom.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import moshi.blossom.command.impl.*;

public class ComManager {

    public String prefix = ".";

    public List<Command> commandList = new ArrayList<>();

    public void init() {
    this.commandList.addAll(Arrays.asList(new ModCom(), new ToggleCom(), new BindCom(), new TargetCom(), new FriendCom(), new ConfigCom(), new JoinCom()));

    }

}
