package comp.ThreadDumpHandlerPlugin;


import comp.ThreadDumpHandlerPlugin.ThreadDumpHandlerPluginArch;

import edu.umkc.solr.core.PluginInfo;

import java.util.Map;

public interface IThreadDumpHandlerPluginImp 
{

	/*
	  Getter and Setter of architecture reference
	*/
    public void setArch (ThreadDumpHandlerPluginArch arch);
	public ThreadDumpHandlerPluginArch getArch();
	
	/*
  	  Myx Lifecycle Methods: these methods are called automatically by the framework
  	  as the bricks are created, attached, detached, and destroyed respectively.
	*/	
	public void init();	
	public void begin();
	public void end();
	public void destroy();

	/*
  	  Implementation primitives required by the architecture
	*/
  
    //To be imported: Map,PluginInfo
    public boolean registerThreadDumpHandlerPlugin (final PluginInfo info,final Map<String, PluginInfo> infoMap)  ;        
}