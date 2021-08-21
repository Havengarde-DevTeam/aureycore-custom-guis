package net.havengarde.aureycore.customguis;

import net.havengarde.aureycore.common.utils.TaskUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public abstract class ACustomGUI implements InventoryHolder {
	protected Player player;
	
	final void assignPlayer(Player p) {
		this.player = p;
		this.onAssign();
	}
	protected abstract void onAssign();
	protected void onOpen(Player p) { return ;}
	protected void onClose(Player p) { return; }
	
	protected abstract void update();
	
	protected abstract String getName();
	protected abstract ItemStack[] getContents();

	protected void updateAsync(Plugin plugin) {
		TaskUtils.runTask(plugin, this::update);
	}
}
