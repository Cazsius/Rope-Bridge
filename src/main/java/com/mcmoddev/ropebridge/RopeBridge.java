package com.mcmoddev.ropebridge;

import com.mcmoddev.ropebridge.common.CommonProxy;
import com.mcmoddev.ropebridge.lib.Constants;
import com.mcmoddev.ropebridge.network.BridgeMessage;
import com.mcmoddev.ropebridge.network.LadderMessage;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Constants.MOD_ID, name = Constants.MOD_NAME, version = Constants.VERSION_NUMBER)
public class RopeBridge {
	private static SimpleNetworkWrapper snw;

	private int discriminator = 0;

	@Mod.Instance(Constants.MOD_ID)
	private static RopeBridge instance;

	@SidedProxy(clientSide = Constants.CLIENT_PROXY_CLASS, serverSide = Constants.SERVER_PROXY_CLASS)
	private static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		proxy.preInit(e);
		snw = NetworkRegistry.INSTANCE.newSimpleChannel(Constants.MOD_ID);
		snw.registerMessage(BridgeMessage.BridgeMessageHandler.class, BridgeMessage.class, discriminator++, Side.SERVER);
		snw.registerMessage(LadderMessage.LadderMessageHandler.class, LadderMessage.class, discriminator++, Side.SERVER);
	}

	public static SimpleNetworkWrapper getSnw() {
		return snw;
	}

	public static CommonProxy getProxy() {
		return proxy;
	}
}
