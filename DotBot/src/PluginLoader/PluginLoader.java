package PluginLoader;

import Main.main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * Removing of this disclaimer is forbidden.
 *
 * @author BubbleEgg
 * @verions: 1.0.0
 **/

public class PluginLoader {
	
	private ArrayList<DotBotPlugin> loadedPlugins = new ArrayList<DotBotPlugin>();
	
	public void start() {
		File pluginsFolder = new File("plugins");
		if(!pluginsFolder.exists())
			pluginsFolder.mkdir();
		for(File plugin : pluginsFolder.listFiles())
			loadPlugin(plugin);
		for(DotBotPlugin plugin : loadedPlugins)
			plugin.onEnable();
		main.debug.print("[PluginLoader] PluginLoader started!");
	}
	
	public void stop() {
		for(DotBotPlugin plugin : loadedPlugins)
			plugin.onDisable();
		main.debug.print("[PluginLoader] PluginLoader stopped!");
	}
	
	public void loadPlugin(File pluginFile) {
		try {
			main.debug.print("[PluginLoader] loading "+pluginFile.getName()+"...");
			JarFile file = new JarFile(pluginFile);
			Manifest manifest = file.getManifest();
			Attributes attrib = manifest.getMainAttributes();
			String main = attrib.getValue(Attributes.Name.MAIN_CLASS);
			if(main.split("\\.")[0].equals("main")&&main.split("\\.")[1].equals("main"))
				main = main.split("\\.")[1];
			Main.main.debug.print("[PluginLoader] main class: "+main);
			Class cl = new URLClassLoader(new URL[]{pluginFile.toURL()}).loadClass(main);
			Class[] interfaces = cl.getInterfaces();
			boolean isPlugin = false;
			for(int y = 0; y < interfaces.length && !isPlugin; y++)
				if(interfaces[y].getName().equals("PluginLoader.DotBotPlugin"))
					isPlugin = true;
			if(isPlugin){
				DotBotPlugin plugin = (DotBotPlugin) cl.newInstance();
				loadedPlugins.add(plugin);
				Main.main.debug.print("[PluginLoader] successfully loaded "+pluginFile.getName()+"!");
				return;
			}
		} catch(IOException e) {
			e.printStackTrace();
		} catch(IllegalAccessException e) {
			e.printStackTrace();
		} catch(InstantiationException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		Main.main.debug.print("[PluginLoader] failed to load "+pluginFile.getName()+"!");
	}
}
