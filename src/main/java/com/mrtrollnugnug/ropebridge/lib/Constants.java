package com.mrtrollnugnug.ropebridge.lib;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Constants {

    public static final String MOD_ID = "ropebridge";

    public static final String MOD_NAME = "Ropbe Bridge Mod";

    public static final String VERSION_NUMBER = "1.3.1.102";

    public static final String CLIENT_PROXY_CLASS = "com.mrtrollnugnug.ropebridge.client.ClientProxy";

    public static final String SERVER_PROXY_CLASS = "com.mrtrollnugnug.ropebridge.common.CommonProxy";

    public static final String GUI_FACTORY = "com.mrtrollnugnug.ropebridge.client.gui.GuiFactoryRopeBridge";

    public static final Random RANDOM = new Random();

    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);
}
