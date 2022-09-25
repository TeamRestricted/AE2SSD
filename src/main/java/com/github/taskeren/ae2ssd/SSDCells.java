package com.github.taskeren.ae2ssd;

import appeng.core.localization.GuiText;
import appeng.core.localization.Tooltips;
import appeng.util.Platform;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class SSDCells {

	public static final String NBT_CATEGORY = "ssd";

	public static final String NBT_IO_COUNT = "io_count";

	private SSDCells() {}

	@NotNull
	public static Optional<CompoundTag> getTag(ItemStack stack) {
		return Optional.ofNullable(getTagInternal(stack, false));
	}

	@Contract("_, true -> !null; _, false -> _")
	public static CompoundTag getTagInternal(ItemStack stack, boolean create) {
		CompoundTag ssdTag;
		if(create) {
			ssdTag = stack.getOrCreateTagElement(NBT_CATEGORY);
		} else {
			ssdTag = stack.getTagElement(NBT_CATEGORY);
		}
		return ssdTag;
	}

	public static int getIOCount(ItemStack stack) {
		var tag = getTag(stack);
		return tag.map(t -> t.getInt(NBT_IO_COUNT)).orElse(0);
	}

	public static int getMaxIOCount(ItemStack stack) {
		return 9999;
	}

	public static void addIOCount(ItemStack stack, int count) {
		if(!Platform.isServer()) return;

		var tag = getTagInternal(stack, true);
		tag.putInt(NBT_IO_COUNT, tag.getInt(NBT_IO_COUNT) + count);
	}

	public static boolean isValid(ItemStack stack) {
		var count = getIOCount(stack);
		var maxCount = getMaxIOCount(stack);
		return count < maxCount;
	}

	// TODO: change the text
	public static Component tooltipsIOCount(ItemStack stack) {
		var count = getIOCount(stack);
		var maxCount = getMaxIOCount(stack);
		return Tooltips.of(
				GuiText.BytesUsed,
				Tooltips.of(
						Tooltips.ofUnformattedNumberWithRatioColor(count, 1, false),
						Tooltips.of(" "),
						Tooltips.of(GuiText.Of),
						Tooltips.of(" "),
						Tooltips.ofUnformattedNumber(maxCount)
				)
		);
	}
}
