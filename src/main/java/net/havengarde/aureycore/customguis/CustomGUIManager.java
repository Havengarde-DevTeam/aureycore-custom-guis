package net.havengarde.aureycore.customguis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("rawtypes")
public final class CustomGUIManager implements Listener {
	private static final ArrayList<CustomGUIItem> customGUIItems = new ArrayList<>();
	private static final HashMap<UUID, ACustomGUI> customGUIs = new HashMap<>();
	
	public static void openGUI(ACustomGUI gui, Player p) {
		p.closeInventory();
		customGUIs.put(p.getUniqueId(), gui);
		gui.assignPlayer(p);
		p.openInventory(gui.getInventory());
		gui.onOpen(p);
	}
	
	static void registerCustomGUIItem(CustomGUIItem item) {
		customGUIItems.add(item);
	}
    
    @SuppressWarnings("unchecked")
	@EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
		Inventory inv = e.getClickedInventory();
		if (inv == null) return;
		
		InventoryHolder topHolder = e.getView().getTopInventory().getHolder();
		
		// click happened inside GUI
		if (customGUIs.containsValue(topHolder) && topHolder.getInventory().equals(inv)) {
    		ACustomGUI gui = (ACustomGUI) topHolder;
			ItemStack item = e.getCurrentItem();
			
			// check if button is a custom GUI item
			for(CustomGUIItem customGUIItem : CustomGUIManager.customGUIItems)
				if (customGUIItem.isSimilar(item)) {
					e.setCancelled(true);
					
					switch (e.getClick()) {
					case LEFT: case RIGHT: case SHIFT_LEFT: case SHIFT_RIGHT: 
						customGUIItem.onClick(gui, e);
						break;
					default: break;
					}
					return;
				}
			
			// check if GUI storage is not locked
			if (!(gui instanceof ACustomStoringGUI)) return;
			ACustomStoringGUI storingGUI = (ACustomStoringGUI) gui;
			if (storingGUI.isStorageLocked()) 
				e.setCancelled(true);
			else {
				storingGUI.updateStorageInventory();
				
//				ItemStack cursorItem = e.getCursor();
//				int slot = e.getSlot();
//				
//				// if cursor has item
//				if (cursorItem != null && cursorItem.getType() != Material.AIR) {
//					// if slot has item
//					if (item != null) {
//						// remove it from storage
//						storingGUI.removeItemFromStorage(item);
//						// if shift click
//						if (e.isShiftClick()) {
//							// add removed item to player inventory
//							e.getWhoClicked().getInventory().addItem(item);
//						} 
//						// if left click
//						else if (e.isLeftClick()) {
//							// if items are the same
//							if (item.isSimilar(cursorItem)) {
//								// add them together
//								int combinedAmount = item.getAmount() + cursorItem.getAmount(),
//									newSlotAmount = Math.min(cursorItem.getMaxStackSize(), combinedAmount),
//									excessAmount = combinedAmount - newSlotAmount;
//								ItemStack combinedItems = cursorItem.clone(), excessItems = cursorItem.clone();
//								combinedItems.setAmount(newSlotAmount);
//								excessItems.setAmount(excessAmount);
//								storingGUI.setItemInStorage(slot, combinedItems);
//								e.getWhoClicked().setItemOnCursor(excessItems);
//							}
//							// if items are different
//							else {
//								// set cursor to removed item and set slot to cursor item
//								storingGUI.setItemInStorage(slot, cursorItem);
//								e.getWhoClicked().setItemOnCursor(item);
//							}
//						}
//						// if right click
//						else if (e.isRightClick()) {
//							// if items are the same
//							if (item.isSimilar(cursorItem)) {
//								// if slot can fit more
//								if (item.getAmount() < item.getMaxStackSize()) {
//									// add one from cursor
//									int newSlotAmount = item.getAmount() + 1,
//										excessAmount = cursorItem.getAmount() - 1;
//									ItemStack combinedItems = cursorItem.clone(), excessItems = cursorItem.clone();
//									combinedItems.setAmount(newSlotAmount);
//									excessItems.setAmount(excessAmount);
//									storingGUI.setItemInStorage(slot, combinedItems);
//									e.getWhoClicked().setItemOnCursor(excessItems);
//								}
//								// else ignore it
//							}
//							// if items are different
//							else {
//								// set cursor to removed item and set slot to cursor item
//								storingGUI.setItemInStorage(slot, cursorItem);
//								e.getWhoClicked().setItemOnCursor(item);
//							}
//						}
//					}
//					
//					// if slot is empty
//					else {
//						// if left click
//						if (e.isLeftClick()) {
//							// transfer whole stack from cursor to slot
//							storingGUI.setItemInStorage(slot, cursorItem);
//							e.getWhoClicked().setItemOnCursor(null);
//						}
//						// if right click
//						else if (e.isRightClick()) {
//							// add one to storage and remove one from cursor
//							ItemStack oneOfCursorItem = cursorItem.clone();
//							oneOfCursorItem.setAmount(1);
//							storingGUI.setItemInStorage(slot, oneOfCursorItem);
//							cursorItem.setAmount(cursorItem.getAmount() - 1);
//						}
//					}
//				}
//				// if cursor is empty but slot is not empty
//				else if (item != null) {
//					// if right click
//					if (e.isRightClick()) {
//						// transfer half of stack in slot to cursor
//						ItemStack newCursorItem = item.clone();
//						item.setAmount(item.getAmount() / 2); // divide slot items in two
//						newCursorItem.setAmount(newCursorItem.getAmount() - item.getAmount()); // remove slot items from new cursor item
//						storingGUI.setItemInStorage(slot, item); // remove new cursor items from inventory
//						e.getWhoClicked().setItemOnCursor(newCursorItem);
//					} else {
//						storingGUI.removeItemFromStorage(item);
//						// if shift click
//						if (e.isShiftClick()) {
//							// transfer whole stack from slot to player inventory
//							e.getWhoClicked().getInventory().addItem(item);
//						}
//						// if left click
//						else if (e.isLeftClick()) {
//							// transfer whole stack from slot to cursor
//							e.getWhoClicked().setItemOnCursor(item);
//						}
//					}
//				}
			}
		} 
		
		// check for shift clicks from player inventory
		else if (customGUIs.containsValue(topHolder) && e.isShiftClick() && topHolder instanceof ACustomStoringGUI) {
			ACustomStoringGUI storingGUI = (ACustomStoringGUI) topHolder;
			
			if (storingGUI.isStorageLocked() || storingGUI.getLockedPlayerInventorySlot() == e.getSlot()) 
				e.setCancelled(true);
			else 
				storingGUI.updateStorageInventory();
		}
	}
    
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
    	Inventory inv = e.getInventory();
    	InventoryHolder holder = inv.getHolder();
    	
    	if (customGUIs.remove(e.getPlayer().getUniqueId()) != null) {
    		ACustomGUI gui = (ACustomGUI) holder;
    		gui.onClose((Player) e.getPlayer());
    	}
    }
}
