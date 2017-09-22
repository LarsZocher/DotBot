package Utils;

import java.util.Date;

/**
 * Removing of this disclaimer is forbidden.
 *
 * @author BubbleEgg
 * @verions: 1.0.0
 **/

public class Debugger {
	
	private boolean debug;
	
	public Debugger(boolean debug){
		this.debug = debug;
	}
	
	public void print(String message){
		Date date = new Date(System.currentTimeMillis());
		System.out.println("[DEBUGGER/"+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds()+"] "+message);
	}
	
	public boolean isDebug(){
		return this.debug;
	}
}
