package com.github.taskeren.ae2ssd.mixin;

import appeng.api.inventories.InternalInventory;
import appeng.blockentity.storage.ChestBlockEntity;
import appeng.util.inv.AppEngInternalInventory;
import com.github.taskeren.ae2ssd.SSDCells;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ChestBlockEntity.class, remap = false)
public abstract class ChestBlockEntityMixin {

	@Shadow @Final private AppEngInternalInventory cellInventory;

	@Shadow public abstract ItemStack getCell();

	@Shadow public abstract void onChangeInventory(InternalInventory inv, int slot);

	@Inject(method = "blinkCell", at = @At("RETURN"))
	private void ae2ssd$blinkCell(int slot, CallbackInfo ci) {
		// 和 DriveBlockEntityMixin 一样，但是每次 IO 都会触发 blinkCell，所以每次都判断一下就好了。
		var stack = getCell();
		if(SSDCells.isSupportedCell(stack) && !SSDCells.isValidCell(stack)) {
			onChangeInventory(cellInventory, 0);
		}
	}
}
