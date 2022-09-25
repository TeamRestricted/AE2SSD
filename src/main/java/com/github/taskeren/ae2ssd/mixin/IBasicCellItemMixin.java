package com.github.taskeren.ae2ssd.mixin;

import appeng.api.storage.cells.IBasicCellItem;
import appeng.items.storage.BasicStorageCell;
import com.github.taskeren.ae2ssd.SSDCells;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = BasicStorageCell.class, remap = false)
public abstract class IBasicCellItemMixin implements IBasicCellItem {

	@Override
	public boolean isStorageCell(ItemStack stack) {
		return SSDCells.isValid(stack);
	}
}
