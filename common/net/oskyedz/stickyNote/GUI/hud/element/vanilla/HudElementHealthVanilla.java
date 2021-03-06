package net.oskyedz.stickyNote.gui.hud.element.vanilla;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.oskyedz.stickyNote.gui.hud.element.HudElement;
import net.oskyedz.stickyNote.gui.hud.element.HudElementType;
import net.oskyedz.lib.GameData;
import net.oskyedz.lib.gui.override.GuiIngameStickyNote;

public class HudElementHealthVanilla extends HudElement {

	protected int playerHealth;
	protected long lastSystemTime;
	protected long healthUpdateCounter;
	protected int lastPlayerHealth;
	protected Random rand = new Random();

	public HudElementHealthVanilla() {
		super(HudElementType.HEALTH, 0, 0, 0, 0, true);
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		ScaledResolution res = new ScaledResolution(this.mc);
		int width = res.getScaledWidth();
		int height = res.getScaledHeight();
		GuiIngameStickyNote theGui = ((GuiIngameStickyNote) gui);
		int updateCounter = theGui.getUpdateCounter();
		EntityPlayer player = (EntityPlayer) this.mc.getRenderViewEntity();
		int health = GameData.ceil(player.getHealth());
		boolean highlight = this.healthUpdateCounter > updateCounter && (this.healthUpdateCounter - updateCounter) / 3L % 2L == 1L;

		if (health < this.playerHealth && player.hurtResistantTime > 0) {
			this.lastSystemTime = Minecraft.getSystemTime();
			this.healthUpdateCounter = updateCounter + 20;
		} else if (health > this.playerHealth && player.hurtResistantTime > 0) {
			this.lastSystemTime = Minecraft.getSystemTime();
			this.healthUpdateCounter = updateCounter + 10;
		}

		if (Minecraft.getSystemTime() - this.lastSystemTime > 1000L) {
			this.playerHealth = health;
			this.lastPlayerHealth = health;
			this.lastSystemTime = Minecraft.getSystemTime();
		}

		this.playerHealth = health;
		int healthLast = this.lastPlayerHealth;

		float healthMax = GameData.getPlayerMaxHealth();
		float absorb = GameData.ceil(player.getAbsorptionAmount());

		int healthRows = GameData.ceil((healthMax + absorb) / 2.0F / 10.0F);
		int rowHeight = Math.max(10 - (healthRows - 2), 3);

		this.rand.setSeed(updateCounter * 312871);

		int left = width / 2 - 91;
		int top = height - GuiIngameStickyNote.left_height;
		GuiIngameStickyNote.left_height += (healthRows * rowHeight);
		if (rowHeight != 10)
			GuiIngameStickyNote.left_height += 10 - rowHeight;

		int regen = -1;
		if (GameData.isPlayerRegenerating()) {
			regen = updateCounter % 25;
		}

		final int TOP = 9 * (GameData.getWorld().getWorldInfo().isHardcoreModeEnabled() ? 5 : 0);
		final int BACKGROUND = (highlight ? 25 : 16);
		int MARGIN = 16;
		if (GameData.isPlayerPoisoned())
			MARGIN += 36;
		else if (GameData.isPlayerWithering())
			MARGIN += 72;
		float absorbRemaining = absorb;

		for (int i = GameData.ceil((healthMax + absorb) / 2.0F) - 1; i >= 0; --i) {
			// int b0 = (highlight ? 1 : 0);
			int row = GameData.ceil((i + 1) / 10.0F) - 1;
			int x = left + i % 10 * 8;
			int y = top - row * rowHeight;

			if (health <= 4)
				y += this.rand.nextInt(2);
			if (i == regen)
				y -= 2;

			gui.drawTexturedModalRect(x, y, BACKGROUND, TOP, 9, 9);

			if (highlight) {
				if (i * 2 + 1 < healthLast)
					gui.drawTexturedModalRect(x, y, MARGIN + 54, TOP, 9, 9); // 6
				else if (i * 2 + 1 == healthLast)
					gui.drawTexturedModalRect(x, y, MARGIN + 63, TOP, 9, 9); // 7
			}

			if (absorbRemaining > 0.0F) {
				if (absorbRemaining == absorb && absorb % 2.0F == 1.0F) {
					gui.drawTexturedModalRect(x, y, MARGIN + 153, TOP, 9, 9); // 17
					absorbRemaining -= 1.0F;
				} else {
					gui.drawTexturedModalRect(x, y, MARGIN + 144, TOP, 9, 9); // 16
					absorbRemaining -= 2.0F;
				}
			} else {
				if (i * 2 + 1 < health)
					gui.drawTexturedModalRect(x, y, MARGIN + 36, TOP, 9, 9); // 4
				else if (i * 2 + 1 == health)
					gui.drawTexturedModalRect(x, y, MARGIN + 45, TOP, 9, 9); // 5
			}
		}
	}

}
