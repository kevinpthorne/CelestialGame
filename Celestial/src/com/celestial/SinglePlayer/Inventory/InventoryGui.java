package com.celestial.SinglePlayer.Inventory;

import java.util.List;

import com.celestial.Celestial;
import com.jme3.scene.Node;
import com.jme3.ui.Picture;

/**
 * Inventory GUI stuffs
 *
 * @author kevint.
 *         Created Jun 6, 2013.
 */
public class InventoryGui {

	Celestial parent;
	
	private Picture hotbarsel;
	
	Node hotbaricons = new Node();
	
	public InventoryGui(Celestial parent) {
		this.parent = parent;
		
		initHotBar();
		
	}
	
	public void initHotBar() {
		Picture pic = new Picture("HotBar");
		pic.setImage(this.parent.getAssetManager(), "/assets/textures/inventory/hotbar.png", true);
		pic.setWidth(342);
		pic.setHeight(38);
		pic.setPosition(0, this.parent.getSettings().getHeight() - 63);
		this.parent.getguiNode().attachChild(pic);
		
		this.hotbarsel = new Picture("HotBarSelection");
		this.hotbarsel.setImage(this.parent.getAssetManager(), "/assets/textures/inventory/selected.png", true);
		this.hotbarsel.setWidth(38);
		this.hotbarsel.setHeight(38);
		this.hotbarsel.setPosition(0, this.parent.getSettings().getHeight() - 63);
		this.parent.getguiNode().attachChild(this.hotbarsel);
		
		this.parent.getguiNode().attachChild(this.hotbaricons);
	}
	
	public void updateHotBar() {
		this.parent.getguiNode().detachChild(this.hotbaricons);
		this.hotbaricons.detachAllChildren();
		List<InventorySlot> slots = this.parent.getInventoryManager().getAllHotSlots();
		for(InventorySlot slot : slots) {
			if(slot.getItem() != null) {
				Picture pic = new Picture("HotBarIcon");
				pic.setImage(this.parent.getAssetManager(), slot.getItem().getIcon(), true);
				pic.setWidth(38);
				pic.setHeight(38);
				if(slots.indexOf(slot) == 1)
					pic.setPosition(0, this.parent.getSettings().getHeight() - 63);
				else if(slots.indexOf(slot) == 2)
					pic.setPosition(38, this.parent.getSettings().getHeight() - 63);
				else if(slots.indexOf(slot) == 3)
					pic.setPosition(76, this.parent.getSettings().getHeight() - 63);
				else if(slots.indexOf(slot) == 4)
					pic.setPosition(114, this.parent.getSettings().getHeight() - 63);
				else if(slots.indexOf(slot) == 5)
					pic.setPosition(152, this.parent.getSettings().getHeight() - 63);
				else if(slots.indexOf(slot) == 6)
					pic.setPosition(190, this.parent.getSettings().getHeight() - 63);
				else if(slots.indexOf(slot) == 7)
					pic.setPosition(228, this.parent.getSettings().getHeight() - 63);
				else if(slots.indexOf(slot) == 8)
					pic.setPosition(266, this.parent.getSettings().getHeight() - 63);
				else if(slots.indexOf(slot) == 9)
					pic.setPosition(304, this.parent.getSettings().getHeight() - 63);
				this.hotbaricons.attachChild(pic);
			}
		}
		this.parent.getguiNode().attachChild(this.hotbaricons);
	}
	
	public void setHotBarSelection(int slot) {
		if(slot == 1) {
			this.hotbarsel.setPosition(0, this.parent.getSettings().getHeight() - 63);
		} else if(slot == 2) {
			this.hotbarsel.setPosition(39, this.parent.getSettings().getHeight() - 63);
		} else if(slot == 3) {
			this.hotbarsel.setPosition(77, this.parent.getSettings().getHeight() - 63);
		} else if(slot == 4) {
			this.hotbarsel.setPosition(115, this.parent.getSettings().getHeight() - 63);
		} else if(slot == 5) {
			this.hotbarsel.setPosition(153, this.parent.getSettings().getHeight() - 63);
		} else if(slot == 6) {
			this.hotbarsel.setPosition(191, this.parent.getSettings().getHeight() - 63);
		} else if(slot == 7) {
			this.hotbarsel.setPosition(229, this.parent.getSettings().getHeight() - 63);
		} else if(slot == 8) {
			this.hotbarsel.setPosition(267, this.parent.getSettings().getHeight() - 63);
		} else if(slot == 9) {
			this.hotbarsel.setPosition(305, this.parent.getSettings().getHeight() - 63);
		} else {
			this.hotbarsel.setPosition(0, this.parent.getSettings().getHeight() - 63);
		}
	}
} 