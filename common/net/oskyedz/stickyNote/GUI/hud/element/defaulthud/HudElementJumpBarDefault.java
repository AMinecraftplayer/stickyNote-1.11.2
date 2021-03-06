package net.oskyedz.stickyNote.gui.hud.element.defaulthud;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.oskyedz.stickyNote.gui.hud.element.HudElementBarred;
import net.oskyedz.stickyNote.gui.hud.element.HudElementType;
import net.oskyedz.lib.GameData;

public class HudElementJumpBarDefault extends HudElementBarred {

	public HudElementJumpBarDefault() {
		super(HudElementType.JUMP_BAR, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return GameData.isRidingLivingMount() && (this.settings.limit_jumpbar ? GameData.getHorseJumpPower() > 0F: true);
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		ScaledResolution res = new ScaledResolution(this.mc);
		int height = res.getScaledHeight();
		int center = res.getScaledWidth() / 2;
		float jumpPower = GameData.getHorseJumpPower();
		int value = (int) (jumpPower * 100.0F);
		drawCustomBar(center - 70, height - 80, 141, 10, value / 100.0D * 100.0D, this.settings.color_jumpbar, offsetColorPercent(this.settings.color_jumpbar, OFFSET_PERCENT));
	}

}
