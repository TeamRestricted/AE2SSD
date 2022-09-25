package com.github.taskeren.ae2ssd.mixin;

import appeng.api.storage.cells.CellState;
import com.github.taskeren.ae2ssd.SSDMod;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = CellState.class, remap = false)
public class CellStateMixin {

	@Final
	@Mutable
	@Shadow
	private static CellState[] $VALUES;

	@Inject(method = "<clinit>()V", at = @At("RETURN"))
	private static void ae2ssd$clInit(CallbackInfo ci) {
		$VALUES = ArrayUtils.add($VALUES, SSDMod.SSD_INVALID = (CellState) (Object) new CellStateMixin("BROKEN", $VALUES.length, 0xAAFF00));
	}

	private CellStateMixin(String name, int index, int stateColor) {
	}
}
