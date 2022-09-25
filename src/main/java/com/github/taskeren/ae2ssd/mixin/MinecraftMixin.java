package com.github.taskeren.ae2ssd.mixin;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {

	@Shadow @Final private Window window;

	@Shadow protected abstract String createTitle();

	@Inject(method = "updateTitle", at = @At("RETURN"))
	private void onUpdateTitle(CallbackInfo ci) {
		window.setTitle(createTitle() + " [SSD]");
	}

}
