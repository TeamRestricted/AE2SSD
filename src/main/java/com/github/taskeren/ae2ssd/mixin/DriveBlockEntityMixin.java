package com.github.taskeren.ae2ssd.mixin;

import appeng.api.inventories.InternalInventory;
import appeng.blockentity.inventory.AppEngCellInventory;
import appeng.blockentity.storage.DriveBlockEntity;
import com.github.taskeren.ae2ssd.SSDCells;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = DriveBlockEntity.class, remap = false)
public abstract class DriveBlockEntityMixin {

	@Shadow public abstract void onChangeInventory(InternalInventory inv, int slot);

	@Shadow @Final private AppEngCellInventory inv;

	@Inject(method = "blinkCell", at = @At("RETURN"))
	private void ae2ssd$blinkCell(int slot, CallbackInfo ci) {
		// 硬盘IO时（DriveWatcher#insert / DriveWatcher#extract）会检测状态变化，
		// 然后执行 activityCallback，也就是 DriveBlockEntity#blinkCell。
		// 所以当这里被调用时，说明硬盘的状态改变了，用 onChangeInventory 让对应格子的硬盘进行状态更新。
		var stack = inv.getStackInSlot(slot);
		if(SSDCells.isSupportedCell(stack) && !SSDCells.isValidCell(stack)) {
			onChangeInventory(inv, slot);
		}
	}
}
