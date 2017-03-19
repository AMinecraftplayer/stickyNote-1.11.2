package net.oskyedz.stickyNote.handlers;

import net.minecraft.util.IStringSerializable;
import net.oskyedz.stickyNote.item.ItemStickyNote;

/**
 * Handles all of our enums which me use for meta data blocks and items
 * 
 * @author CJMinecraft
 *
 */
public class EnumHandler {

	/**
	 * This is used by the {@link BlockBreaker} and the {@link ItemStickyNote}
	 * 
	 * @author CJMinecraft
	 *
	 */
	public static enum StickyNoteTypes implements IStringSerializable {
		BASIC("basic", 0), ADVANCED("advanced", 1);

		private int ID;
		private String name;

		private StickyNoteTypes(String name, int ID) {
			this.ID = ID;
			this.name = name;
		}

		@Override
		public String getName() {
			return this.name;
		}

		public int getID() {
			return ID;
		}

		@Override
		public String toString() {
			return getName();
		}

	}

	/**
	 * This is used by {@link BlockTinOre}
	 * 
	 * @author CJMinecraft
	 *
	 */
	public static enum OreType implements IStringSerializable {
		OVERWORLD("overworld", 0), NETHER("nether", 1), END("end", 2);

		private int ID;
		private String name;

		private OreType(String name, int ID) {
			this.ID = ID;
			this.name = name;
		}

		@Override
		public String getName() {
			return this.name;
		}

		public int getID() {
			return ID;
		}

		@Override
		public String toString() {
			return getName();
		}
	}

}