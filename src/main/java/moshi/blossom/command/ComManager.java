package moshi.blossom.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import moshi.blossom.command.impl.*;

public class ComManager {

    public String prefix = ".";

    public List<Command> commandList = new ArrayList<>();

    public void init() {
    this.commandList.addAll(Arrays.asList(new Command[] { (Command)new ModCom(), (Command)new ToggleCom(), (Command)new BindCom(), (Command)new TargetCom(), (Command)new FriendCom(), (Command)new ConfigCom(), (Command)new JoinCom() }));

    }

}
