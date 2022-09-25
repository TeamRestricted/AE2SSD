package com.github.taskeren.ae2ssd.mixin;

import appeng.api.config.Actionable;
import appeng.api.networking.security.IActionSource;
import appeng.api.stacks.AEKey;
import appeng.api.storage.cells.CellState;
import appeng.me.cells.BasicCellInventory;
import com.github.taskeren.ae2ssd.SSDCells;
import com.github.taskeren.ae2ssd.SSDMod;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BasicCellInventory.class, remap = false)
public abstract class BasicCellInventoryMixin {

	@Shadow @Final private ItemStack i;

	@Shadow protected abstract void saveChanges();

	@Shadow public abstract CellState getStatus();

	@Inject(method = "getStatus", at = @At("RETURN"), cancellable = true)
	private void ae2ssd$getStatus(CallbackInfoReturnable<CellState> cir) {
		if(!SSDCells.isValid(i)) {
			cir.setReturnValue(SSDMod.SSD_INVALID);
		}
	}

	@Inject(method = "innerInsert", at = @At("RETURN"))
	private void ae2ssd$innerInsert(AEKey what, long amount, Actionable mode, IActionSource source, CallbackInfoReturnable<Long> cir) {
		if(mode == Actionable.SIMULATE) return;
		SSDMod.LOGGER.info("Increased by insertion");
		var insertAmount = cir.getReturnValueJ();
		if(insertAmount != 0) {
			SSDCells.addIOCount(i, 2);
			saveChanges();
		}
	}

	@Inject(method = "extract", at = @At("RETURN"))
	private void ae2ssd$extract(AEKey what, long amount, Actionable mode, IActionSource source, CallbackInfoReturnable<Long> cir) {
		if(mode == Actionable.SIMULATE) return;
		SSDMod.LOGGER.info("Increased by extraction "+getStatus());
		var extractAmount = cir.getReturnValueJ();
		if(extractAmount != 0) {
			SSDCells.addIOCount(i, 1);
			saveChanges();
		}
	}

	@Inject(method = "isCell", at = @At("RETURN"), cancellable = true)
	private static void ae2ssd$isCell(ItemStack input, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(cir.getReturnValueZ() && SSDCells.isValid(input));
	}

}
