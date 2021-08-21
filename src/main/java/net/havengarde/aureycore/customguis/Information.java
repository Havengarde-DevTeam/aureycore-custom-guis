package net.havengarde.aureycore.customguis;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.havengarde.aureycore.common.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public final class Information extends CustomGUIItem<ACustomGUI> {
	public Information(ItemStack item, String... lore) {
		this(item.getType(), item.hasItemMeta() && item.getItemMeta().getEnchants().size() > 0, ItemUtils.getName(item),
				item.hasItemMeta() && item.getItemMeta().hasLore()
					? Stream.concat(item.getItemMeta().getLore().stream(), Arrays.stream(lore)).collect(Collectors.toList()) 
					: Arrays.asList(lore));
	}
	
	public Information(Material mat, boolean enchanted, String title, String... lore) {
		this(mat, enchanted, title, Arrays.asList(lore));
	}

	@SuppressWarnings("serial")
	public Information(Material mat, boolean enchanted, String title, List<String> lore) {
		this(mat, title, lore, enchanted ? new HashMap<Enchantment, Integer>() {
			{
				put(Enchantment.LOYALTY, 1);
			}
		} : null, enchanted ? ItemFlag.HIDE_ENCHANTS : null);
	}

	public Information(Material mat, String title, List<String> lore, HashMap<Enchantment, Integer> enchantments, ItemFlag... flags) {
		super(mat, 1, title, lore, enchantments, flags);
	}
}
