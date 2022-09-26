package com.github.taskeren.ae2ssd.mixin;

import appeng.me.cells.BasicCellHandler;
import com.github.taskeren.ae2ssd.SSDCells;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = BasicCellHandler.class, remap = false)
public abstract class BasicCellHandlerMixin {

	@Inject(method = "isCell", at = @At("RETURN"), cancellable = true)
	private void ae2ssd$isCell(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(cir.getReturnValueZ() && SSDCells.isValidCell(stack));
	}

	@Inject(method = "addCellInformationToTooltip", at = @At("HEAD"))
	private void ae2ssd$addCellInformationToTooltip(ItemStack is, List<Component> lines, CallbackInfo ci) {
		lines.add(SSDCells.tooltipsIOCount(is));
	}

}
