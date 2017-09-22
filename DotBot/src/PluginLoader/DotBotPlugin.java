package PluginLoader;

import Bot.CommandPriority;

/**
 * Removing of this disclaimer is forbidden.
 *
 * @author BubbleEgg
 * @verions: 1.0.0
 **/

public interface DotBotPlugin {
	public void onEnable();
	public void onDisable();
	public void onCommand(String command, String[] args);
	public CommandPriority getPriorty();
}
