package com.mrtrollnugnug.ropebridge.network;

import com.mrtrollnugnug.ropebridge.handler.LadderBuildingHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class LadderMessage implements IMessage {
    private BlockPos from;
    private EnumFacing side;

    public LadderMessage() {}
    
    public LadderMessage(BlockPos from, EnumFacing side) {
        this.from = from;
        this.side = side;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.from = BlockPos.fromLong(buf.readLong());
        this.side = EnumFacing.byIndex(buf.readByte());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(this.from.toLong());
        buf.writeByte(side.getIndex());
    }

    public static class LadderMessageHandler implements IMessageHandler<LadderMessage, IMessage> {
        @Override
        public IMessage onMessage(LadderMessage message, MessageContext ctx) {
            if (ctx.side == Side.SERVER) {
                final EntityPlayer player = ctx.getServerHandler().player;
                FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> LadderBuildingHandler.newLadder(message.from, player, player.getEntityWorld(), message.side, player.getHeldItemMainhand()));
            }
            return null;
        }
    }
}
