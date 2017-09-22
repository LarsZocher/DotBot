package Main;

import Bot.DotBot;
import PluginLoader.PluginLoader;
import Utils.Debugger;

/**
 * Removing of this disclaimer is forbidden.
 *
 * @author BubbleEgg
 * @verions: 1.0.0
 **/

public class main {
	
	public static Debugger debug = new Debugger(true);
	
	public static void main(String[] args){
		debug.print("starting...");
		DotBot db = new DotBot();
		db.start();
		debug.print("program successfully started!");
		
	}
	
}
