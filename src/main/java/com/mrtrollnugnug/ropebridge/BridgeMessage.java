package com.mrtrollnugnug.ropebridge;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class BridgeMessage implements IMessage {

    int command; // 0 = smoke, 1 = setState, 2 = inventory change

    int posX, posY, posZ; // Where smoke or setState

    int invIndex; // inventory index to change/upper or lower

    int stackSize; // value of stack/rotate or not

    public BridgeMessage () {
        this.command = -1;
    }

    public BridgeMessage (int command, int posX, int posY, int posZ, int invIndex, int stackSize) {
        this.command = command;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.invIndex = invIndex;
        this.stackSize = stackSize;
    }

    @Override
    public void fromBytes (ByteBuf buf) {

        this.command = ByteBufUtils.readVarInt(buf, 5);
        this.posX = ByteBufUtils.readVarInt(buf, 5);
        this.posY = ByteBufUtils.readVarInt(buf, 5);
        this.posZ = ByteBufUtils.readVarInt(buf, 5);
        this.invIndex = ByteBufUtils.readVarInt(buf, 5);
        this.stackSize = ByteBufUtils.readVarInt(buf, 5);
    }

    @Override
    public void toBytes (ByteBuf buf) {

        ByteBufUtils.writeVarInt(buf, this.command, 5);
        ByteBufUtils.writeVarInt(buf, this.posX, 5);
        ByteBufUtils.writeVarInt(buf, this.posY, 5);
        ByteBufUtils.writeVarInt(buf, this.posZ, 5);
        ByteBufUtils.writeVarInt(buf, this.invIndex, 5);
        ByteBufUtils.writeVarInt(buf, this.stackSize, 5);
    }
}
