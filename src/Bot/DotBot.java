package Bot;

import Main.main;
import PluginLoader.PluginLoader;
import com.darkprograms.speech.microphone.Microphone;
import com.darkprograms.speech.recognizer.GSpeechDuplex;
import com.darkprograms.speech.recognizer.GSpeechResponseListener;
import com.darkprograms.speech.recognizer.GoogleResponse;
import net.sourceforge.javaflacencoder.FLACFileWriter;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Removing of this disclaimer is forbidden.
 *
 * @author BubbleEgg
 * @verions: 1.0.0
 **/

public class DotBot {
	
	public PluginLoader pluginLoader = new PluginLoader();
	private Thread listening;
	private Timer restartTimer;
	private boolean isListening = false;
	private final Microphone mic = new Microphone(FLACFileWriter.FLAC);
	private final int restartTimerTime = 1000*20;
	private final String GOOGLE_API_KEY = "AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw";
	private GSpeechDuplex duplex = new GSpeechDuplex(GOOGLE_API_KEY);
	private GSpeechResponseListener gsrl;
	
	public DotBot(){
		initialize();
	}
	
	public void initialize(){
		main.debug.print("[DotBot] initializing DotBot...");
		restartTimer = new Timer();
		listening = new Thread();
		main.debug.print("[DotBot] initialized DotBot!");
	}
	
	public void startRestartTimer(){
		stopRestartTimer();
		restartTimer = new Timer();
		restartTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				if(!isListening)
					newListingThread();
			}
		}, restartTimerTime, restartTimerTime);
		main.debug.print("[DotBot] started restart timer!");
	}
	
	public void stopRestartTimer(){
		restartTimer.cancel();
		main.debug.print("[DotBot] stopped restart timer!");
	}
	
	public void newListingThread(){
		main.debug.print("[DotBot] starting new ListeningThread...");
		if(this.listening.isAlive()){
			this.listening.stop();
			main.debug.print("[DotBot] stopped old Theard!");
		}
		this.listening = new Thread(() -> {
			GSpeechDuplex duplex = new GSpeechDuplex(GOOGLE_API_KEY);
			duplex.setLanguage("de");
			mic.open();
			gsrl = new GSpeechResponseListener() {
				
				public void onResponse(GoogleResponse googleResponse) {
					String output = "";
					
					//Get the response from Google Cloud
					output = googleResponse.getResponse();
					isListening = output!=null;
					if (output != null) {
						output = output.toLowerCase();
						System.out.println(output);
						onCommand(output);
					} else
						System.out.println("Output was null");
				}
			};
			
			duplex.addResponseListener(gsrl);
			try {
				duplex.recognize(mic.getTargetDataLine(), mic.getAudioFormat());
			} catch (IOException | LineUnavailableException e) {
				e.printStackTrace();
			}
		});
		listening.start();
		main.debug.print("[DotBot] new ListeningThread started!");
	}
	
	public void startListening(){
		startRestartTimer();
		newListingThread();
		main.debug.print("[DotBot] started listening!");
	}
	
	public void stopListening(){
		stopRestartTimer();
		if(listening.isAlive()) {
			duplex.removeResponseListener(gsrl);
			mic.close();
			listening.stop();
		}
		main.debug.print("[DotBot] stopped listening!");
	}
	
	public void start(){
		pluginLoader.start();
		startListening();
		main.debug.print("[DotBot] DotBot started!");
	}
	
	public void stop(){
		pluginLoader.stop();
		stopListening();
		main.debug.print("[DotBot] DotBot stopped!");
	}
	
	public void onCommand(String command){
		main.debug.print("[DotBot] command: " + command);
	}
}
