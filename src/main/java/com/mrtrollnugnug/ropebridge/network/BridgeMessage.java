package com.mrtrollnugnug.ropebridge.network;

import com.mrtrollnugnug.ropebridge.handler.BridgeBuildingHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class BridgeMessage implements IMessage {
    private BlockPos from;
    private BlockPos to;

    public BridgeMessage() {}
        // We need this because when the message is received on the target, it
        // tries to use the default constructor (this one) to initialize a new
        // IMessage instance, have that message read out data with fromBytes and
        // finally pass that message to the IMessageHandler

    public BridgeMessage(BlockPos from, BlockPos to) {
        super();
        this.from = from;
        this.to = to;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.from = BlockPos.fromLong(buf.readLong());
        this.to = BlockPos.fromLong(buf.readLong());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(this.from.toLong());
        buf.writeLong(this.to.toLong());
    }

    public static class BridgeMessageHandler implements IMessageHandler<BridgeMessage, IMessage> {
        @Override
        public IMessage onMessage(BridgeMessage message, MessageContext ctx) {
            if (ctx.side == Side.SERVER) {
                final EntityPlayer player = ctx.getServerHandler().player;
                FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> BridgeBuildingHandler.newBridge(player, player.getHeldItemMainhand(), -1, message.from, message.to));
            }
            return null;
        }
    }
}
