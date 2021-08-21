package net.havengarde.aureycore.customguis;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class ACustomChestGUI extends ACustomGUI {
	protected final ItemStack SPACER = new CustomGUIItem<ACustomGUI>(Material.BLACK_STAINED_GLASS_PANE, 1, " ", null, null);
	protected final int pages;
	protected int currentPage;
	final int size;
	
	public ACustomChestGUI(int size, int pages, int currentPage) {
		this.size = size;
		this.pages = pages;
		this.currentPage = currentPage;
	}
	
	public ACustomChestGUI(int size, int pages) {
		this(size, pages, 1);
	}
	
	public ACustomChestGUI(int size) {
		this(size, 1, 1);
	}
	
	@Override
	protected void update() {
		inventory.setContents(getContents(this.currentPage));
	}
	
	protected final <G extends ACustomChestGUI> ItemStack getPageButton(Material material, String name, List<String> lore, int page) {
		return new Button<G>(material, name, lore, (gui, e, b) -> {
			gui.navigateToPage(page);
		});
	}
	protected final void navigateToPage(int page) {
		if (page >= 1 && page <= pages) {
			this.currentPage = page;
			this.update();
		}
	}
	
	@Override
	protected final String getName() {
		return getName(currentPage);
	}
	protected abstract String getName(int page);
	@Override
	protected final ItemStack[] getContents() {
		return getContents(currentPage);
	}
	protected abstract ItemStack[] getContents(int page);
	
	protected final ItemStack[] getSpacerContent() { 
		ItemStack[] content = new ItemStack[size];
		Arrays.fill(content, SPACER);
		return content;
	}
	
	Inventory inventory;
	
	@Override
	public Inventory getInventory() {
		if (inventory == null) {
			inventory = Bukkit.createInventory(this, size, this.getName(this.currentPage));
			inventory.setContents(getContents(this.currentPage));
		}
		
		return inventory;
	}
}
