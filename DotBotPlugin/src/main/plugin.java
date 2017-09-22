package main;

import Bot.CommandPriority;
import PluginLoader.DotBotPlugin;

/**
 * Removing of this disclaimer is forbidden.
 *
 * @author BubbleEgg
 * @verions: 1.0.0
 **/

public class plugin implements DotBotPlugin{
	
	@Override
	public void onEnable() {
		System.out.println("bla started");
	}
	
	@Override
	public void onDisable() {
		System.out.println("bla stopped");
	}
	
	@Override
	public void onCommand(String command, String[] args) {
	
	}
	
	@Override
	public CommandPriority getPriorty() {
		return CommandPriority.PRIORITY_NORMAL;
	}
}
