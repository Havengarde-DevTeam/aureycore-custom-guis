package net.havengarde.aureycore.customguis;

import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomGUIItem<G extends ACustomGUI> extends ItemStack {
	protected CustomGUIItem(Material mat, int amount, String name, List<String> lore,
			Map<Enchantment, Integer> enchantments, ItemFlag... flags) {
		this(mat, amount, mat.getMaxStackSize(), name, lore, enchantments, flags);
	}
	protected CustomGUIItem(Material mat, int amount, int maxStackSize, String name, List<String> lore,
			Map<Enchantment, Integer> enchantments, ItemFlag... flags) {
		super(mat, amount);
		
		ItemMeta meta = getItemMeta();
		if (name != null)
			meta.setDisplayName(name);
		if (lore != null)
			meta.setLore(lore);
		if (flags != null)
			for(ItemFlag f : flags)
				if (f != null)
					meta.addItemFlags(flags);
		setItemMeta(meta);
		
		if (enchantments != null)
			addUnsafeEnchantments(enchantments);
		
		CustomGUIManager.registerCustomGUIItem(this);
	}

	public void onClick(G gui, InventoryClickEvent e) {
		e.setCancelled(true);
	}
}
