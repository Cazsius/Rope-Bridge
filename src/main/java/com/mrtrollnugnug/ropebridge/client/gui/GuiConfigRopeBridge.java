package com.mrtrollnugnug.ropebridge.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.mrtrollnugnug.ropebridge.handler.ConfigurationHandler;
import com.mrtrollnugnug.ropebridge.lib.Constants;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiConfigRopeBridge extends GuiConfig {
	static final Configuration cfg = ConfigurationHandler.getConfig();

	public GuiConfigRopeBridge(GuiScreen parent) {
		super(parent, generateConfigList(), Constants.MOD_ID, false, false,
				GuiConfig.getAbridgedConfigPath(cfg.toString()));
	}

	/**
	 * Generates a list of configuration options to be displayed in forge's
	 * configuration GUI.
	 *
	 * @return List<IConfigElement>: A list of IConfigElement which are used to
	 *         populate forge's configuration GUI.
	 */
	public static List<IConfigElement> generateConfigList() {
		final ArrayList<IConfigElement> elements = new ArrayList<>();

		for (final String name : cfg.getCategoryNames()) {
			elements.add(new ConfigElement(cfg.getCategory(name)));
		}
		return elements;
	}
}