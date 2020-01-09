package com.mrtrollnugnug.ropebridge.network;

import com.mrtrollnugnug.ropebridge.handler.BridgeBuildingHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class BridgeMessage {
  private BlockPos from;
  private BlockPos to;

  public BridgeMessage() {
  }

  // We need this because when the message is received on the target, it
  // tries to use the default constructor (this one) to initialize a new
  // IMessage instance, have that message read out data with fromBytes and
  // finally pass that message to the IMessageHandler
  public BridgeMessage(PacketBuffer buffer) {
    from = BlockPos.fromLong(buffer.readLong());
    to = BlockPos.fromLong(buffer.readLong());
  }

  public BridgeMessage(BlockPos from, BlockPos to) {
    this.from = from;
    this.to = to;
  }

  public void encode(PacketBuffer buffer) {
    buffer.writeLong(from.toLong());
    buffer.writeLong(to.toLong());
  }

  public void handle(Supplier<NetworkEvent.Context> ctx) {
    ctx.get().enqueueWork(() -> {
      final PlayerEntity player = ctx.get().getSender();
      BridgeBuildingHandler.newBridge(player, player.getHeldItemMainhand(), from, to);
    });
    ctx.get().setPacketHandled(true);
  }
}
