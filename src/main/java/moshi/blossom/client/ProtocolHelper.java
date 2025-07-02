package moshi.blossom.client;

import de.florianmichael.viamcp.ViaMCP;

public class ProtocolHelper

{
    public void initViaMCP() {
        try {
            ViaMCP.create();
            ViaMCP.INSTANCE.initAsyncSlider();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
