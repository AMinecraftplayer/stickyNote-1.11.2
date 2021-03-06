package net.oskyedz.stickyNote.gui.hud.element.modern;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.oskyedz.stickyNote.gui.hud.HudModern;
import net.oskyedz.stickyNote.gui.hud.element.HudElementBarred;
import net.oskyedz.stickyNote.gui.hud.element.HudElementType;
import net.oskyedz.lib.GameData;

public class HudElementHealthMountModern extends HudElementBarred{

	public HudElementHealthMountModern() {
		super(HudElementType.HEALTH_MOUNT, 0, 0, 0, 0, false);
	}

	@Override
	public boolean checkConditions() {
		return GameData.isRidingLivingMount() && GameData.shouldDrawHUD();
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		EntityLivingBase mount = (EntityLivingBase) GameData.getMount();
		int health = (int) Math.ceil(mount.getHealth());
		int healthMax = (int) mount.getMaxHealth();
		int xOffset = ((HudModern) this.rpgHud.huds.get("modern")).getPosX();
		String stringHealth = health + "/" + healthMax;
		int posX = (this.settings.render_player_face ? 26 : 4) + (this.settings.show_numbers_health ? xOffset - 1: -2);
		
		if (this.settings.show_numbers_health) {
			int width2 = this.mc.fontRendererObj.getStringWidth(stringHealth) / 2;
			drawRect(posX, 24, width2 + 4, 5, 0xA0000000);
			GL11.glScaled(0.5D, 0.5D, 0.5D);
			gui.drawString(this.mc.fontRendererObj, stringHealth, posX * 2 + 4, 48, -1);
			GL11.glScaled(2.0D, 2.0D, 2.0D);
		}
		
		drawTetragon(posX, posX, 21, 21, 58, 54, 3, 3, 0xA0000000);
		drawTetragon(posX + 2, posX + 2, 21, 21, (int)(((double)health / (double)healthMax) * 53), (int)(((double)health / (double)healthMax) * 53 - 2), 1, 1, this.settings.color_health);
		
	}

}
