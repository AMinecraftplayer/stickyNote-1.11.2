package net.oskyedz.lib;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTippedArrow;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.oskyedz.lib.gui.override.GuiIngameStickyNote;

public class GameData {

	protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");
	
	
	//General Minecraft Data
	private static Minecraft mc;
	public static Minecraft getMinecraft(){
		if(mc == null) mc = Minecraft.getMinecraft();
		return mc;
	}
	
	public static boolean shouldDrawHUD(){
		return getMinecraft().playerController.shouldDrawHUD();
	}
	
	public static EntityPlayerSP getPlayer(){
		return getMinecraft().player;
	}
	
	
	//Player Data
	public static int getPlayerHealth(){
		return ceil(getPlayer().getHealth());
	}
	
	public static int getPlayerMaxHealth(){
		return ceil(getPlayer().getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue());
	}
	
	public static int getPlayerAbsorption(){
		return floor(getPlayer().getAbsorptionAmount());
	}
	
	public static boolean isPlayerPoisoned(){
		return getPlayer().isPotionActive(MobEffects.POISON);
	}
	
	public static boolean isPlayerRegenerating(){
		return getPlayer().isPotionActive(MobEffects.REGENERATION);
	}
	
	public static boolean isPlayerWithering(){
		return getPlayer().isPotionActive(MobEffects.WITHER);
	}
	
	public static int getPlayerAir(){
		return getPlayer().getAir();
	}
	
	public static boolean isPlayerUnderwater(){
		return getPlayer().isInsideOfMaterial(Material.WATER);
	}
	
	public static int getPlayerArmor(){
		return ForgeHooks.getTotalArmorValue(getPlayer());
	}
	
	public static int getPlayerXPCap(){
		return getPlayer().xpBarCap();
	}
	
	public static float getPlayerXPRaw(){
		return getPlayer().experience;
	}
	
	public static int getPlayerXP(){
		return ceil(getPlayerXPCap() * getPlayer().experience);
	}
	
	public static FoodStats getPlayerFoodStats(){
		return getPlayer().getFoodStats();
	}
	
	public static int getPlayerFood(){
		return getPlayerFoodStats().getFoodLevel();
	}
	
	public static int getPlayerMaxFood(){
		return 20;
	}
	
	public static boolean doesPlayerNeedFood(){
		return getPlayerFoodStats().needFood();
	}
	
	public static boolean isPlayerHungered(){
		return getPlayer().isPotionActive(MobEffects.HUNGER);
	}
	public static ItemStack getMainhand(){
		return getPlayer().getHeldItemMainhand();
	}
	
	public static ItemStack getOffhand(){
		return getPlayer().getHeldItemOffhand();
	}
	
	public static Entity getMount(){
		return getPlayer().getRidingEntity();
	}

	public static boolean isRidingLivingMount(){
		return getMount() instanceof EntityLivingBase;
	}	
	
	public static ItemStack getMainInventoryItemOfSlot(int slot){
		return getPlayer().inventory.mainInventory.get(slot);
	}
	
	public static float getCooledAttackStrength(){
		return 1F;
	}
	
	public static float getItemAnimationsToGo(ItemStack item){
		return item.getAnimationsToGo();
	}
	
	public static float getHorseJumpPower(){
		return getPlayer().getHorseJumpPower();
	}
	
	public static int getPlayerXPLevel(){
		return getPlayer().experienceLevel;
	}
	
	public static boolean hasPlayerClock(){
		return getPlayer().inventory.hasItemStack(new ItemStack(Items.CLOCK));
	}
	
	public static boolean hasPlayerCompass(){
		return getPlayer().inventory.hasItemStack(new ItemStack(Items.COMPASS));
	}
	
	public static int getPlayerArmorInventoryLength(){
		return getPlayer().inventory.armorInventory.size();
	}
	
	public static ItemStack getArmorInSlot(int slot){
		return getPlayer().inventory.armorItemInSlot(slot);
	}
	
	public static ItemStack getItemInHand(int hand){
		if(hand == 0) return getMainhand();
		else if(hand == 1) return getOffhand();
		else return nullStack();
	}
	
	public static int getOffhandSide(){
		if(getPlayer().getPrimaryHand() == EnumHandSide.RIGHT) return 0;
		else return 1;
	}
	public static int getInventorySize(){
		return getPlayer().inventory.getSizeInventory();
	}
	
	public static ItemStack getItemInSlot(int slot){
		return getPlayer().inventory.getStackInSlot(slot);
	}
	
	public static int getItemStackSize(ItemStack item){
		return item.getCount();
	}
	
	public static ItemStack setItemStackSize(ItemStack item, int count){
		item.setCount(count);
		return item;
	}
	
	public static float getRotationYaw(){
		return getPlayer().rotationYaw;
	}
	
	public static long getWorldTime(){
		return getPlayer().getEntityWorld().getWorldTime();
	}
	
	public static int[] getPlayerPos(){
		int[] pos = new int[3];
		pos[0] = getPlayer().getPosition().getX();
		pos[1] = getPlayer().getPosition().getY();
		pos[2] = getPlayer().getPosition().getZ();
		return pos;
	}
	public static ItemStack nullStack(){
		return ItemStack.EMPTY;
	}
	
	public static World getWorld(){
		return getMinecraft().world;
	}
	
	public static float overlayMessageTime(GuiIngameStickyNote gui){
		//return gui.getRecordPlayingUpFor();
		return gui.getOverlayMessageTime();
	}
	
	public static int overlayColor(GuiIngameStickyNote gui, float hue){
		//return 16777215;
		return (gui.getAnimateOverlayMessageColor() ? Color.HSBtoRGB(hue / 50.0F, 0.7F, 0.6F) & 0xFFFFFF : 0xFFFFFF);
	}
	
	public static boolean isRecordPlaying(GuiIngameStickyNote gui){
		//return gui.getRecordIsPlaying();
		return false;
	}
	
	public static String getOverlayText(GuiIngameStickyNote gui){
		//return gui.getRecordPlaying();
		return gui.getOverlayMessage();
	}
	
	public static int getAttackIndicatorSetting(){
		//return -1;
		return getMinecraft().gameSettings.attackIndicator;
	}
	
	public static int addArrowStackIfCorrect(ItemStack item, ItemStack arrow){
		PotionType type1 = null;
		if (item.getItem() instanceof ItemTippedArrow)
			type1 = PotionUtils.getPotionTypeFromNBT(item.getTagCompound());
		if (item.getItem() instanceof ItemTippedArrow) {
			PotionType type2 = PotionUtils.getPotionTypeFromNBT(arrow.getTagCompound());
			if (type1.getEffects() == type2.getEffects()) {
				return GameData.getItemStackSize(arrow);
			}
		} else {
			return GameData.getItemStackSize(arrow);
		}
		
		return GameData.getItemStackSize(arrow);
	}
	
	public static ItemStack arrowStack(){
		return new ItemStack(Items.ARROW);
	}
	public static void bindIcons(){
		getMinecraft().getTextureManager().bindTexture(Gui.ICONS);
	}
	
	public static void bindButtonTextures(){
		getMinecraft().getTextureManager().bindTexture(buttonTextures);
	}
	
	public static void renderItemIntoGUI(EntityPlayer player, ItemStack item, int xPos, int yPos){
		getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(item, xPos, yPos);
	}
	
	public static boolean spectatorStuff(){
		if (getMinecraft().playerController.isSpectator() && getMinecraft().pointedEntity == null) {
			RayTraceResult raytraceresult = getMinecraft().objectMouseOver;

			if (raytraceresult == null || raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK) {
				return true;
			}

			BlockPos blockpos = raytraceresult.getBlockPos();

			net.minecraft.block.state.IBlockState state = GameData.getWorld().getBlockState(blockpos);
			if (!state.getBlock().hasTileEntity(state) || !(GameData.getWorld().getTileEntity(blockpos) instanceof IInventory)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isArrow(ItemStack item){
		if(item != GameData.nullStack()) {
			return ItemStack.areItemsEqual(item, arrowStack());
			//return item.getItem() instanceof ItemArrow;
		}
		
		return false;
	}
	
	public static void doRenderDirections(){
		//OpenGlHelper.renderDirections(10);
	}
	//OpenGL stuff
	public static int getSrcAlpha(){
		return GL11.GL_SRC_ALPHA;
		//return GlStateManager.SourceFactor.SRC_ALPHA;
	}
	
	public static int getOneMinusSrcAlpha(){
		return GL11.GL_ONE_MINUS_SRC_ALPHA;
		//return GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA;
	}
	
	public static int getGlOne(){
		return GL11.GL_ONE;
		//return GlStateManager.SourceFactor.ONE;
	}
	
	public static int getGlZero(){
		return GL11.GL_ZERO;
		//return GlStateManager.SourceFactor.ZERO;
	}
	
	public static void tryBlendFuncSeparate(){
		GlStateManager.tryBlendFuncSeparate(getSrcAlpha(), getOneMinusSrcAlpha(), getGlOne(), getGlZero());
	}
	
	public static void blendFunc(){
		GlStateManager.blendFunc(getSrcAlpha(), getOneMinusSrcAlpha());
	}
	
	public static EntityPlayer playerOfEvent(EntityItemPickupEvent event){
		return event.getEntityPlayer();
	}
	
	public static EntityItem itemOfEvent(EntityItemPickupEvent event){
		return event.getItem();
	}
	
	public static void beginVertex(int i, VertexFormat format){
		Tessellator tessellator = Tessellator.getInstance();
		//WorldRenderer vertexbuffer = tessellator.getWorldRenderer();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(i, format);
	}
	
	public static void addVertexPos(double x, double y, double z){
		Tessellator tessellator = Tessellator.getInstance();
		//WorldRenderer vertexbuffer = tessellator.getWorldRenderer();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		vertexbuffer.pos(x, y, z).endVertex();
	}
	
	public static void drawVertex(){
		Tessellator tessellator = Tessellator.getInstance();
		tessellator.draw();
	}
	
	//Math functions
	public static int ceil(float value){
		return MathHelper.ceil(value);
	}
	
	public static int floor(float value){
		return MathHelper.floor(value);
	}
	
	public static int ceil(double value){
		return MathHelper.ceil(value);
	}
	
	public static double clamp(double d1, double d2, double d3){
		return MathHelper.clamp(d1, d2, d3);
	}
	
	public static float clamp(float f1, float f2, float f3){
		return MathHelper.clamp(f1, f2, f3);
	}
	
	public static int hsvToRGB(float f1, float f2, float f3){
		//MathHelper.hsvToRGB(f1, f2, f3);
		return 0;
	}
}
