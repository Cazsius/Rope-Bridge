package com.mrtrollnugnug.ropebridge.item;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.mrtrollnugnug.ropebridge.RopeBridge;
import com.mrtrollnugnug.ropebridge.handler.BridgeBuildingHandler;
import com.mrtrollnugnug.ropebridge.handler.ConfigurationHandler;
import com.mrtrollnugnug.ropebridge.network.BridgeMessage;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBridgeBuilder extends Item {

    World world;

    EntityPlayer player;

    boolean viewSnap;

    float playerFov;

    boolean fovNormal;

    Timer smokeTimer;

    Timer buildTimer;

    Timer clickTimer;

    Style chatStyle = new Style().setColor(TextFormatting.DARK_AQUA);

    boolean posSet = false;

    BlockPos firstPos;

    private boolean warningSent = false;

    public ItemBridgeBuilder () {
        super();
        this.setCreativeTab(CreativeTabs.TOOLS);
        this.setMaxStackSize(1);
        this.setMaxDamage(64);
        this.smokeTimer = new Timer();
        this.buildTimer = new Timer();
        this.clickTimer = new Timer();
        this.viewSnap = false;
        this.fovNormal = true;
    }

    @Override
    public void onCreated (ItemStack stack, World worldIn, EntityPlayer playerIn) {

        playerIn.hasAchievement(RopeBridge.craftAchievement);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick (ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {

        if (worldIn.isRemote) {
            this.world = worldIn;
        }
        if (playerIn.world.isRemote) {
            this.player = playerIn;
            if (this.playerFov == 0) {
                this.playerFov = Minecraft.getMinecraft().gameSettings.fovSetting;
                // player.playSound(soundIn, volume, pitch);
            }
        }
        playerIn.setActiveHand(hand);

        if (worldIn.isRemote) {
            this.viewSnap = true;
            this.fovNormal = false;
            this.clickTimer = new Timer();
            this.clickTimer.schedule(new TimerTask() {

                @Override
                public void run () {

                    RopeBridge.snw.sendToServer(new BridgeMessage(0, 0, 0, 0, 2, 0));
                }
            }, 500);
        }
        return ActionResult.newResult(EnumActionResult.PASS, itemStackIn);
    }

    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getMaxItemUseDuration (ItemStack stack) {

        return 72000;
    }

    @Override
    public float getStrVsBlock (ItemStack stack, IBlockState state) {

        if (this.player == null)
            return 1.0F;
        else {
            if (this.player.isSneaking() && this.isBridgeBlock(state.getBlock())) {
                if (!this.warningSent) {
                    this.tellPlayer("WARNING! Breaking whole bridge!");
                    this.warningSent = true;
                }
                return 0.3F;
            }
            else
                return 1.0F;
        }
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     *
     * @param tooltip All lines to display in the Item's tooltip. This is a List of Strings.
     * @param advanced Whether the setting "Advanced tooltips" is enabled
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {

        tooltip.add("- Hold right-click to build");
        tooltip.add("- Sneak to break whole bridge");
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    @Override
    public EnumAction getItemUseAction (ItemStack stack) {

        return EnumAction.NONE;
    }

    @Override
    public void onPlayerStoppedUsing (ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {

        if (!worldIn.isRemote && entityLiving instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer) entityLiving;
            this.viewSnap = false;
            if (72000 - timeLeft > 10) {
                if (!player.onGround) {
                    this.tellPlayer("You must be standing on something to build a bridge!");
                }
                else {
                    final RayTraceResult hit = player.rayTrace(ConfigurationHandler.maxBridgeDistance, 1.0F);
                    // world.playSoundEffect(player.posX,player.posY,player.posZ,
                    // "random.bow", 1.0F, 1.0F);
                    // play sound at player random.bow
                    // Main.snw.sendToServer(new BridgeMessage(0, 0, 0, 0, 0,
                    // 0));
                    if (hit.typeOfHit == RayTraceResult.Type.BLOCK) {
                        final BlockPos floored = new BlockPos(Math.floor(player.posX), Math.floor(player.posY) - 1, Math.floor(player.posZ));
                        // Vector offsets
                        double xOffset = 0.0D;
                        double yOffset = 0.0D;
                        double zOffset = 0.0D;
                        if (hit.hitVec.xCoord % 1 == 0 && hit.hitVec.xCoord < floored.getX()) {
                            xOffset = -0.8D;
                        }
                        if (hit.hitVec.zCoord % 1 == 0 && hit.hitVec.zCoord < floored.getZ()) {
                            zOffset = -0.8D;
                        }
                        if (hit.hitVec.yCoord % 1 == 0) {
                            if (player.rotationPitch > 0) { // Looking from top
                                yOffset = -0.8D;
                            }
                        }

                        BridgeBuildingHandler.newBridge(player, this.playerFov, stack, -1, floored, new BlockPos(hit.hitVec.xCoord + xOffset, hit.hitVec.yCoord + yOffset, hit.hitVec.zCoord + zOffset));
                    }
                }
            }
            else {
                // Cancel click noise
                this.clickTimer.cancel();
            }
        }
    }

    /*
     * private void newBridge(ItemStack stack, int inputType, BlockPos pos1, BlockPos pos2) {
     * LinkedList<SlabPos> bridge = new LinkedList<SlabPos>(); boolean allClear = true; int
     * x1,y1,x2,y2,z,z2; int Xdiff = Math.abs(pos1.getX()-pos2.getX()); int Zdiff =
     * Math.abs(pos1.getZ()-pos2.getZ()); boolean rotate; if (Xdiff > Zdiff) { rotate = false;
     * x1 = pos1.getX(); y1 = pos1.getY(); x2 = pos2.getX(); y2 = pos2.getY(); z = pos1.getZ();
     * z2 = pos2.getZ(); } else { rotate = true; x1 = pos1.getZ(); y1 = pos1.getY(); x2 =
     * pos2.getZ(); y2 = pos2.getY(); z = pos1.getX(); z2 = pos2.getX(); } if
     * (Math.abs(z2-z)>3) {
     * tellPlayer("Sorry, bridge must be built in a cardinal dirrection. Please try again." );
     * return; } double m; double b; double distance; int distInt; m =
     * (double)(y2-y1)/(double)(x2-x1); if (!Main.ignoreSlopeWarnings && Math.abs(m)>0.2) {
     * tellPlayer("Sorry, your slope is too great. Please try again."); return; } b =
     * (double)y1-(m*(double)x1); distance = Math.abs(x2-x1); distInt = Math.abs(x2-x1); //
     * Check if bridge longer than 0 if (distInt < 2) { // bridge too short return; } // Check
     * for materials in inventory if (!hasMaterials(distInt-1) &&
     * !player.capabilities.isCreativeMode) { return; } for (int x = Math.min(x1, x2)+1; x<=
     * Math.max(x1, x2)-1; x++) { for (int y = Math.max(y1, y2); y>= Math.min(y1,
     * y2)-distInt/8-1; y--) { double funcVal =
     * m*(double)x+b-(distance/1000)*(Math.sin((x-Math.min(x1,
     * x2))*(Math.PI/distance)))*Main.bridgeDroopFactor + Main.bridgeYOffset; if
     * ((double)y+0.5>funcVal && (double)y-0.5<=funcVal) { int level; if (funcVal>=y) { if
     * (funcVal>=(double)y+0.25) { level = 4; } else { level = 3; } } else { if
     * (funcVal>=(double)y-0.25) { level = 2; } else { level = 1; } } allClear =
     * !addSlab(bridge,x,y+1,z,level,rotate) ? false : allClear; } } } if (allClear) { int type
     * = inputType==-1 ? getWoodType() : inputType; if (!player.capabilities.isCreativeMode) {
     * takeMaterials(distInt-1); if (stack.getItemDamage()==stack.getMaxDamage()) {
     * zoomTo(playerFov); } Main.snw.sendToServer(new bridgeMessage(3, 0, 0, 0, 0, 0)); //
     * damage item } Main.snw.sendToServer(new bridgeMessage(4, 0, 0, 0, 0, 0)); // trigger
     * building achievement tellPlayer("Building Bridge!"); buildBridge(bridge, type); } else {
     * tellPlayer("Oops! Looks like there's something in the way. Look for the Smoke to see where that is and try again."
     * ); } }
     */

    /*
     * private int getWoodType() { for (int i = 0; i < player.inventory.mainInventory.length;
     * i++) { ItemStack stack = player.inventory.mainInventory[i]; if (stack == null) {
     * continue; } String name = stack.getItem().getUnlocalizedName(); if
     * (name.equals("tile.woodSlab")) { return stack.getItemDamage(); } } return 0; }
     */

    private void tell (EntityPlayer playerIn, String message) {

        playerIn.sendMessage(new TextComponentString("[Rope Bridge]: " + message).setStyle(this.chatStyle));
    }

    private void tellPlayer (String message) {

        this.tell(this.player, message);
    }

    /*
     * private boolean addSlab(LinkedList<SlabPos> list, int x, int y, int z, int level,
     * boolean rotate) { boolean isClear; BlockPos pos; if (rotate) { pos = new BlockPos(z, y,
     * x); } else { pos = new BlockPos(x, y, z); } isClear = (Main.breakThroughBlocks ||
     * world.isAirBlock(pos) || world.getBlockState(pos).getBlock().isReplaceable(world, pos));
     * list.add(new SlabPos(pos, level, rotate)); if (!isClear) { spawnSmoke(pos, 15); } return
     * isClear; }
     */

    /*
     * private void spawnSmoke(BlockPos pos, int times) { if (times > 0) {
     * world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, pos.getX()+0.5, pos.getY()+0.5,
     * pos.getZ()+0.5, 0.0D, 0.0D, 0.0D, new int[0]); final BlockPos finPos = pos; final int
     * finTimes = times-1; smokeTimer.schedule(new TimerTask() { public void run() {
     * spawnSmoke(finPos, finTimes); } }, 1000); } } private void
     * buildBridge(LinkedList<SlabPos> bridge, int type) { SlabPos slab; if(!bridge.isEmpty())
     * { slab = bridge.pop(); // Server call build x y z Main.snw.sendToServer(new
     * bridgeMessage(1, slab.x, slab.y, slab.z, slab.level, (slab.rotate?1:0)+2*type));
     * spawnSmoke(new BlockPos(slab.x, slab.y, slab.z), 1); // play sound at x y z wood
     * Main.snw.sendToServer(new bridgeMessage(0, slab.x, slab.y, slab.z, 1, 0)); final
     * LinkedList<SlabPos> finBridge = bridge; final int finType = type;
     * buildTimer.schedule(new TimerTask() { public void run() { buildBridge(finBridge,
     * finType); } }, 100); } }
     */

    /**
     * rotates a player towards the closest cardinal direction when holding this item
     */
    @Override
    public void onUpdate (ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {

        if (worldIn.isRemote && entityIn instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer) entityIn;
            // if (player == null) {
            // player = (EntityPlayer) entityIn;
            // }

            // TODO May cause problem
            // if (((EntityPlayer) entityIn).getHeldItemMainhand() != null) {
            // if (((EntityPlayer)
            // entityIn).getHeldItemMainhand().getUnlocalizedName().equals(stack.getUnlocalizedName()))
            // {
            if (isSelected) {
                if (this.viewSnap) {
                    if (isSelected) {
                        this.rotatePlayerTowards(player, this.getNearestYaw(player));
                        this.zoomTowards(30);
                    }
                }
                else {
                    this.zoomTowards(this.playerFov);
                }
            }
            else {
                this.zoomTowards(this.playerFov);
                this.viewSnap = false;
                this.clickTimer.cancel();
            }
            // }
            // else {
            // zoomTowards(playerFov);
            // viewSnap = false;
            // clickTimer.cancel();
            // }
        }
    }

    /**
     * Called before a block is broken. Return true to prevent default block harvesting.
     *
     * Note: In SMP, this is called on both client and server sides!
     *
     * @param itemstack The current ItemStack
     * @param pos Block's position in world
     * @param player The Player that is wielding the item
     * @return True to prevent harvesting, false to continue as normal
     */
    @Override
    public boolean onBlockStartBreak (ItemStack itemstack, BlockPos pos, EntityPlayer playerIn) {

        if (playerIn.world.isRemote) {
            final Block blk = playerIn.world.getBlockState(pos).getBlock();
            if (playerIn.isSneaking() && this.isBridgeBlock(blk)) {
                this.breakBridge(playerIn.world, pos, blk.getMetaFromState(playerIn.world.getBlockState(pos)));
            }
        }
        return false;
    }

    private boolean isBridgeBlock (Block blockIn) {

        if (blockIn.getUnlocalizedName().contains("bridge_block"))
            return true;
        else
            return false;
    }

    /**
     * Breaks block at position posIn and recursively spreads to in-line neighbors
     *
     * @param posIn the position of the block to start breaking bridge from
     */
    private void breakBridge (World worldIn, BlockPos posIn, int meta) {

        // Break block and turn into air
        RopeBridge.snw.sendToServer(new BridgeMessage(1, posIn.getX(), posIn.getY(), posIn.getZ(), 0, 0));
        int xRange = 0;
        int zRange = 0;
        if (meta % 2 == 0) {
            xRange = 1;
        }
        else {
            zRange = 1;
        }
        for (int x = posIn.getX() - xRange; x <= posIn.getX() + xRange; x++) {
            for (int y = posIn.getY() - 1; y <= posIn.getY() + 1; y++) {
                for (int z = posIn.getZ() - zRange; z <= posIn.getZ() + zRange; z++) {
                    if (x - posIn.getX() == 0 && z - posIn.getZ() == 0) {
                        // No bridge
                    }
                    else {
                        final BlockPos currentPos = new BlockPos(x, y, z);
                        final IBlockState currentBlockState = worldIn.getBlockState(currentPos);
                        if (this.isBridgeBlock(currentBlockState.getBlock())) {
                            if (currentBlockState.getBlock().getMetaFromState(currentBlockState) == meta) {
                                final World worldInFinal = worldIn;
                                final BlockPos currentPosFinal = currentPos;
                                final int metaFinal = meta;
                                this.buildTimer.schedule(new TimerTask() {

                                    @Override
                                    public void run () {

                                        ItemBridgeBuilder.this.breakBridge(worldInFinal, currentPosFinal, metaFinal);
                                    }
                                }, 100);
                            }
                        }
                    }
                }
            }
        }
    }

    /*
     * private boolean hasMaterials(int dist) { if (player.capabilities.isCreativeMode) {
     * return true; } int slabsNeeded = dist; int stringNeeded = 1+Math.round(dist/2); int
     * slabsHad = 0; int stringHad = 0; for (int i = 0; i < 36; i++) { ItemStack stack =
     * player.inventory.mainInventory[i]; if (stack == null) { continue; } String name =
     * stack.getItem().getUnlocalizedName(); if (name.equals("item.string")) { stringHad +=
     * stack.stackSize; } if (name.equals("tile.woodSlab")) { slabsHad += stack.stackSize; } }
     * if (slabsHad>=slabsNeeded && stringHad>=stringNeeded) { return true; } else {
     * tellPlayer("You need at least "+slabsNeeded+" slabs and "
     * +stringNeeded+" strings to build this bridge."); return false; } }
     */

    /*
     * private void takeMaterials(int dist) { if (player.capabilities.isCreativeMode) { return;
     * } int slabsNeeded = dist; int stringNeeded = 1+Math.round(dist/2); for (int i = 0; i <
     * 36; i++) { ItemStack stack = player.inventory.mainInventory[i]; if (stack == null) {
     * continue; } String name = stack.getItem().getUnlocalizedName(); if
     * (name.equals("item.string")) { if (stack.stackSize > stringNeeded) { //stack.stackSize =
     * stack.stackSize - stringNeeded; // Update on server Main.snw.sendToServer(new
     * bridgeMessage(2, 0, 0, 0, i, stack.stackSize - stringNeeded)); stringNeeded = 0; } else
     * { stringNeeded -= stack.stackSize; //player.inventory.mainInventory[i] = null; // Update
     * on server Main.snw.sendToServer(new bridgeMessage(2, 0, 0, 0, i, 0)); continue; } } if
     * (name.equals("tile.woodSlab")) { if (stack.stackSize > slabsNeeded) { //stack.stackSize
     * = stack.stackSize - slabsNeeded; // Update on server Main.snw.sendToServer(new
     * bridgeMessage(2, 0, 0, 0, i, stack.stackSize - slabsNeeded)); slabsNeeded = 0; } else {
     * slabsNeeded -= stack.stackSize; //player.inventory.mainInventory[i] = null; // update on
     * server Main.snw.sendToServer(new bridgeMessage(2, 0, 0, 0, i, 0)); continue; } } } }
     */

    private float getNearestYaw (EntityPlayer player) {

        float yaw = player.rotationYaw % 360;
        if (yaw < 0) {
            yaw += 360;
        }
        if (yaw < 45)
            return 0F;
        if (yaw > 45 && yaw <= 135)
            return 90F;
        else if (yaw > 135 && yaw <= 225)
            return 180F;
        else if (yaw > 225 && yaw <= 315)
            return 270F;
        else
            return 360F;
    }

    private void rotatePlayerTowards (EntityPlayer player, float target) {

        float yaw = player.rotationYaw % 360;
        if (yaw < 0) {
            yaw += 360;
        }
        this.rotatePlayerTo(player, yaw + (target - yaw) / 4);
    }

    private void rotatePlayerTo (EntityPlayer player, float yaw) {

        final float original = player.rotationYaw;
        player.rotationYaw = yaw;
        player.prevRotationYaw += player.rotationYaw - original;
    }

    private void zoomTowards (float toFov) {

        if (ConfigurationHandler.zoomOnAim && toFov != 0 && !this.fovNormal) {
            final float currentFov = Minecraft.getMinecraft().gameSettings.fovSetting;
            if (Math.round(currentFov) != toFov) {
                this.zoomTo(currentFov + (toFov - currentFov) / 4);
            }
            else {
                if (Math.round(currentFov) == this.playerFov) {
                    this.fovNormal = true;
                }
            }
        }
    }

    private void zoomTo (float toFov) {

        if (ConfigurationHandler.zoomOnAim && toFov != 0) {
            Minecraft.getMinecraft().gameSettings.fovSetting = toFov;
        }
    }
}
