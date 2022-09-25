package com.github.taskeren.ae2ssd;

import appeng.api.storage.cells.ICellHandler;
import appeng.api.storage.cells.ISaveProvider;
import appeng.api.storage.cells.StorageCell;
import appeng.me.cells.BasicCellInventory;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

public class SSDCellHandler implements ICellHandler {

	private static final Logger LOG = LogManager.getLogger("SSD");

	public SSDCellHandler() {
		LOG.info("SSD Cell Handler initialized");
	}

	@Override
	public boolean isCell(ItemStack is) {
		return BasicCellInventory.isCell(is);
	}

	@Nullable
	@Override
	public StorageCell getCellInventory(ItemStack is, @Nullable ISaveProvider host) {
		return BasicCellInventory.createInventory(is, host);
	}

}
