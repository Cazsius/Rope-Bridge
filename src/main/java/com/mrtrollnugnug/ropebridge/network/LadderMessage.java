package com.mrtrollnugnug.ropebridge.network;

import com.mrtrollnugnug.ropebridge.handler.LadderBuildingHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class LadderMessage {
  private BlockPos from;
  private Direction side;

  public LadderMessage() {
  }

  public LadderMessage(BlockPos from, Direction side) {
    this.from = from;
    this.side = side;
  }

  public LadderMessage(PacketBuffer buf) {
    this.from = BlockPos.fromLong(buf.readLong());
    this.side = Direction.byIndex(buf.readByte());
  }

  public void encode(ByteBuf buf) {
    buf.writeLong(this.from.toLong());
    buf.writeByte(side.getIndex());
  }

  public void handle(Supplier<NetworkEvent.Context> ctx) {
    ctx.get().enqueueWork(() -> {
              final PlayerEntity player = ctx.get().getSender();
              LadderBuildingHandler.newLadder(from, player, player.getEntityWorld(), side, player.getHeldItemMainhand());
            }
    );
    ctx.get().setPacketHandled(true);
  }
}
