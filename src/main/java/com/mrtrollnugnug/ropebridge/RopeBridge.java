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

    public static SimpleNetworkWrapper snw;

    public static Achievement craftAchievement, buildAchievement;

    @Mod.Instance(Constants.MOD_ID)
    public static RopeBridge instance;

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

        if (ConfigurationHandler.customAchievements) {
            craftAchievement = new Achievement("achievement.grapplingGun", "grapplingGun", 8, 2, ContentHandler.itemBridgeBuilder, AchievementList.BUILD_BETTER_PICKAXE);
            craftAchievement.registerStat();
            buildAchievement = new Achievement("achievement.buildBridge", "buildBridge", 10, 2, ContentHandler.blockBridgeSlab2, craftAchievement);
            buildAchievement.registerStat();
        }

        snw = NetworkRegistry.INSTANCE.newSimpleChannel(Constants.MOD_ID);
        snw.registerMessage(BuildMessage.BuildMessageHandler.class, BuildMessage.class, this.discriminator++, Side.SERVER);
    }

    private int discriminator = 0;
}