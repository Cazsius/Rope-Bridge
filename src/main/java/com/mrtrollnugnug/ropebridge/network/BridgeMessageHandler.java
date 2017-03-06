package com.mrtrollnugnug.ropebridge.network;

import com.mrtrollnugnug.ropebridge.RopeBridge;
import com.mrtrollnugnug.ropebridge.handler.ContentHandler;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

@Deprecated
public class BridgeMessageHandler implements IMessageHandler<BridgeMessage, IMessage>
{

    @Override
    public IMessage onMessage(BridgeMessage bridgeMessage, MessageContext context)
    {

        final BridgeMessage message = bridgeMessage; // Used for Sounds
        final MessageContext ctx = context;
        final IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.world;
        // or Minecraft.getMinecraft() on the client
        mainThread.addScheduledTask(() -> {

            final EntityPlayerMP player = ctx.getServerHandler().playerEntity;
            final WorldServer world = (WorldServer) player.world;
            switch (message.command) {
            case 0: { // Sound
                switch (message.invIndex) {
                case 0: {
                    break;
                }
                case 1: {
                    break;
                }
                case 2: {
                    break;
                }
                }
                /*
                 * if (message.posX==0) { // Sound at player
                 * world.playSoundAtEntity(player, name, 1.0F,
                 * world.rand.nextFloat() * 0.1F + 0.9F); } else { // Sound at
                 * coordinates world.playSoundEffect(message.posX, message.posY,
                 * message.posZ, name, 1.0F, world.rand.nextFloat() * 0.1F +
                 * 0.9F); } break;
                 */
            }
            case 1: { // set a block
                final BlockPos blockPos = new BlockPos(message.posX, message.posY, message.posZ);
                world.destroyBlock(blockPos, true);
                Block blk;
                switch (message.invIndex) {
                case 0: {
                    blk = Blocks.AIR;
                    break;
                }
                case 1: {
                    blk = ContentHandler.getBlockBridgeSlab1();
                    break;
                }
                case 2: {
                    blk = ContentHandler.getBlockBridgeSlab2();
                    break;
                }
                case 3: {
                    blk = ContentHandler.getBlockBridgeSlab3();
                    break;
                }
                case 4: {
                    blk = ContentHandler.getBlockBridgeSlab4();
                    break;
                }
                default: {
                    blk = Blocks.AIR;
                    break;
                }
                }
                world.setBlockState(blockPos, blk.getStateFromMeta(message.stackSize));
                break;
            }
            case 2: { // set inventory
                if (message.stackSize == 0) {
                    player.inventory.mainInventory[message.invIndex] = null;
                }
                else {
                    player.inventory.mainInventory[message.invIndex].stackSize = message.stackSize;
                }
                break;
            }
            case 3: { // damage item
                if (player.getHeldItemMainhand().getItemDamage() == player.getHeldItemMainhand().getMaxDamage()) {
                    player.getHeldItemMainhand().damageItem(player.getHeldItemMainhand().getMaxDamage(), player);
                    ;
                }
                else {
                    player.getHeldItemMainhand().damageItem(1, player);
                }
                break;
            }
            case 4: { // trigger the achievement for building a bridge
                player.hasAchievement(RopeBridge.getBuildAchievement());
                break;
            }
            }
        });
        return null; // no response in this case
    }

}
