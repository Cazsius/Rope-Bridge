package com.mrtrollnugnug.ropebridge;

import com.mrtrollnugnug.ropebridge.common.CommonProxy;
import com.mrtrollnugnug.ropebridge.handler.ConfigurationHandler;
import com.mrtrollnugnug.ropebridge.handler.ContentHandler;
import com.mrtrollnugnug.ropebridge.lib.Constants;
import com.mrtrollnugnug.ropebridge.network.BuildMessage;

import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Constants.MOD_ID, name = Constants.MOD_NAME, version = Constants.VERSION_NUMBER, guiFactory = Constants.GUI_FACTORY)
public class RopeBridge
{

    private static SimpleNetworkWrapper snw;

    private static Achievement craftAchievement;
    private static  Achievement buildAchievement;
    
    private int discriminator = 0;

    @Mod.Instance(Constants.MOD_ID)
    private static RopeBridge instance;

    @SidedProxy(clientSide = Constants.CLIENT_PROXY_CLASS, serverSide = Constants.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent e)
    {

        ConfigurationHandler.initConfig(e.getSuggestedConfigurationFile());
        ContentHandler.initBlocks();
        ContentHandler.initItems();
        ContentHandler.initRecipes();
        proxy.preInit(e);

        if  (ConfigurationHandler.isCustomAchievements()) {
            setCraftAchievement(new Achievement("achievement.grapplingGun", "grapplingGun", 8, 2, ContentHandler.getItemBridgeBuilder(), AchievementList.BUILD_BETTER_PICKAXE));
            getCraftAchievement().registerStat();
            setBuildAchievement(new Achievement("achievement.buildBridge", "buildBridge", 10, 2, ContentHandler.getBlockBridgeSlab2(), getCraftAchievement()));
            getBuildAchievement().registerStat();
        }

        setSnw(NetworkRegistry.INSTANCE.newSimpleChannel(Constants.MOD_ID));
        getSnw().registerMessage(BuildMessage.BuildMessageHandler.class, BuildMessage.class, this.discriminator++, Side.SERVER);
    }
    
	public static SimpleNetworkWrapper getSnw() {
		return snw;
	}
	public static void setSnw(SimpleNetworkWrapper snw) {
		RopeBridge.snw = snw;
	}

	public static Achievement getCraftAchievement() {
		return craftAchievement;
	}

	public static void setCraftAchievement(Achievement craftAchievement) {
		RopeBridge.craftAchievement = craftAchievement;
	}

	public static Achievement getBuildAchievement() {
		return buildAchievement;
	}

	public static void setBuildAchievement(Achievement buildAchievement) {
		RopeBridge.buildAchievement = buildAchievement;
	}
}