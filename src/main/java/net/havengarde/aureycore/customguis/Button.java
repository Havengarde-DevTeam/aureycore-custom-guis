package net.havengarde.aureycore.customguis;

import java.util.List;
import java.util.Map;

import net.havengarde.aureycore.common.stringformat.Color;
import net.havengarde.aureycore.common.interfaces.TriConsumer;
import net.havengarde.aureycore.common.utils.EnchantUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

public final class Button<G extends ACustomGUI> extends CustomGUIItem<G> {
	private TriConsumer<G, InventoryClickEvent, Button<G>> clickHandler;
	
	public Button(Color color, String name, TriConsumer<G, InventoryClickEvent, Button<G>> clickHandler) {
		this(color.toGlassPane(), color.toChatColor() + name, null, clickHandler);
	}
	
	public Button(Material mat, String name, TriConsumer<G, InventoryClickEvent, Button<G>> clickHandler) {
		this(mat, name, null, clickHandler);
	}
	
	public Button(Material mat, String name, List<String> lore, boolean enchanted, TriConsumer<G, InventoryClickEvent, Button<G>> clickHandler) {
		this(mat, 1, name, lore, enchanted ? EnchantUtils.getMapForEnchantment(Enchantment.LOYALTY) : null, clickHandler);
	}
	
	public Button(Material mat, String name, List<String> lore, TriConsumer<G, InventoryClickEvent, Button<G>> clickHandler) {
		this(mat, 1, name, lore, null, clickHandler);
	}
	
	public Button(Material mat, String name, List<String> lore, TriConsumer<G, InventoryClickEvent, Button<G>> clickHandler,
			ItemFlag... flags) {
		this(mat, 1, name, lore, null, clickHandler, flags);
	}
	
	public Button(Material mat, int amount, String name, List<String> lore, Map<Enchantment, Integer> enchantments, 
			TriConsumer<G, InventoryClickEvent, Button<G>> clickHandler) {
		super(mat, amount, name, lore, enchantments, ItemFlag.HIDE_ENCHANTS);
		
		this.clickHandler = clickHandler;
	}
	
	public Button(Material mat, int amount, String name, List<String> lore, Map<Enchantment, Integer> enchantments, 
			TriConsumer<G, InventoryClickEvent, Button<G>> clickHandler, ItemFlag... flags) {
		super(mat, amount, name, lore, enchantments, flags);
		
		this.clickHandler = clickHandler;
	}
	
	@Override
	public void onClick(G gui, InventoryClickEvent e) {
		clickHandler.accept(gui, e, this);
	}
	
	public void changeName(String newName) {
		ItemMeta meta = this.getItemMeta();
		meta.setDisplayName(newName);
		this.setItemMeta(meta);
	}
}
