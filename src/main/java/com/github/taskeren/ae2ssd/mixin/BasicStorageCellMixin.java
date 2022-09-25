package com.github.taskeren.ae2ssd.mixin;

import appeng.items.storage.BasicStorageCell;
import com.github.taskeren.ae2ssd.SSDCells;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BasicStorageCell.class, remap = false)
public abstract class BasicStorageCellMixin {

	@Inject(method = "getColor", at = @At("RETURN"), cancellable = true)
	private static void ae2ssd$getColor(ItemStack stack, int tintIndex, CallbackInfoReturnable<Integer> cir) {
		if(!SSDCells.isValid(stack)) {
			cir.setReturnValue(0x66CCFF);
		}
	}
}
