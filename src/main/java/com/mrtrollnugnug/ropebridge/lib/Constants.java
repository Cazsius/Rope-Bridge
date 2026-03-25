package com.mrtrollnugnug.ropebridge.lib;

import net.minecraft.resources.Identifier;

public final class Constants {
	public static final String MOD_ID = "ropebridge";

	public static final Identifier BUILD_BRIDGE_ADVANCEMENT = modLoc("main/build_bridge");
	public static final Identifier BUILD_LADDER_ADVANCEMENT = modLoc("main/build_ladder");

	public static final class Messages {
		public static final String WARNING_BREAKING = "chat.ropebridge.warning.breaking";
		public static final String NOT_ON_GROUND = "chat.ropebridge.info.notonground";
		public static final String NOT_CARDINAL = "chat.ropebridge.info.notcardinal";
		public static final String SLOPE_GREAT = "chat.ropebridge.info.greatslope";
		public static final String OBSTRUCTED = "chat.ropebridge.info.obstruction";
		public static final String UNDERFUNDED_BRIDGE = "chat.ropebridge.info.underfunded_bridge";
		public static final String UNDERFUNDED_LADDER = "chat.ropebridge.info.underfunded_ladder";
		public static final String BAD_SIDE = "chat.ropebridge.info.bad_side";
		public static final String TOP = "chat.ropebridge.params.top";
		public static final String BOTTOM = "chat.ropebridge.params.bottom";
		public static final String NOT_SOLID = "chat.ropebridge.info.not_solid";
	}

	public static Identifier modLoc(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
