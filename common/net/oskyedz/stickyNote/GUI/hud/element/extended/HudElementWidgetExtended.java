package net.oskyedz.stickyNote.gui.hud.element.extended;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.Gui;
import net.oskyedz.stickyNote.gui.hud.element.HudElementTexture;
import net.oskyedz.stickyNote.gui.hud.element.HudElementType;
import net.oskyedz.lib.GameData;
import net.oskyedz.stickyNote.main.ModStickyNote;

public class HudElementWidgetExtended extends HudElementTexture {

	public HudElementWidgetExtended() {
		super(HudElementType.WIDGET, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.playerController.shouldDrawHUD();
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		bind(INTERFACE);
		gui.drawTexturedModalRect(this.settings.render_player_face ? 50 : 26, this.settings.render_player_face ? 4 : 0, 0, 35, 114, 44);
		if (GameData.isRidingLivingMount()) {
			gui.drawTexturedModalRect(this.settings.render_player_face ? 51 : 23, this.settings.render_player_face ? 44 : 39, 164, 0, 92, 20);
		}

		if(ModStickyNote.instance.settings.render_player_face) {
			gui.drawTexturedModalRect(0, 0, 114, 0, 50, 50);
			bind(getPlayerSkin(GameData.getPlayer()));
			GL11.glScaled(0.5D, 0.5D, 0.5D);
			gui.drawTexturedModalRect(34, 34, 32, 32, 32, 32);
			gui.drawTexturedModalRect(34, 34, 160, 32, 32, 32);
			GL11.glScaled(2.0D, 2.0D, 2.0D);
			GameData.bindIcons();
		} else {
			gui.drawTexturedModalRect(0, 3, 214, 20, 26, 38);
		}
	}

}
