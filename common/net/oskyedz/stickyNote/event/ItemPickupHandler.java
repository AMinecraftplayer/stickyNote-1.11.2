package net.oskyedz.stickyNote.event;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import net.oskyedz.lib.GameData;
import net.oskyedz.stickyNote.main.ModStickyNote;
import net.oskyedz.stickyNote.pickup.ItemPickup;

public class ItemPickupHandler {

	private List<ItemPickup> pickups = new ArrayList<ItemPickup>();
	
	private static ItemStack storedItem;
	
	@SubscribeEvent
	public void onPickupItem(ItemPickupEvent event) {
		if(!event.isCanceled() && event.player.equals(GameData.getPlayer()) && storedItem != null && ItemStack.areItemsEqual(event.pickedUp.getEntityItem(), storedItem)){
			addPickup(storedItem.copy());
			storedItem = null;
			ModStickyNote.renderDetailsAgain[0] = true;
			ModStickyNote.renderDetailsAgain[1] = true;
			ModStickyNote.renderDetailsAgain[2] = true;
		}
	}
	
	@SubscribeEvent
	public void onEntityPickupItem(EntityItemPickupEvent event){
		if(GameData.playerOfEvent(event).equals(GameData.getPlayer())){
			storedItem = GameData.itemOfEvent(event).getEntityItem().copy();
		}
	}
	
	public void addPickup(ItemStack item){
		boolean added = false;
		for(int i = 0; i < this.pickups.size(); i++){
			if(ItemStack.areItemsEqual(item, this.pickups.get(i).getItem())){
				this.pickups.get(i).addItems(GameData.getItemStackSize(item));
				added = true;
			}
		}
		if(!added){
			this.pickups.add(new ItemPickup(item.copy()));
		}
	}
	
	public void onUpdate(){
		for(int i = 0; i < this.pickups.size(); i++){
			if(this.pickups.get(i).onUpdate()) this.pickups.remove(i);
		}
	}
	
	public List<ItemPickup> getPickups(){
		return this.pickups;
	}
}
