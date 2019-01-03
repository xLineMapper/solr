package comp.StandardRequestHandlerPlugin;


import edu.umkc.config.Bootstrapper;
import edu.umkc.solr.core.PluginInfo;

import java.util.Map;

import annotation.Feature;
import annotation.Optional;

@Optional(Feature.STANDARD_REQUEST_HANDLER)
public class StandardRequestHandlerPluginImp implements IStandardRequestHandlerPluginImp
{
	private StandardRequestHandlerPluginArch _arch;

    public StandardRequestHandlerPluginImp (){
    }

	public void setArch(StandardRequestHandlerPluginArch arch){
		_arch = arch;
	}
	public StandardRequestHandlerPluginArch getArch(){
		return _arch;
	}

	/*
  	  Myx Lifecycle Methods: these methods are called automatically by the framework
  	  as the bricks are created, attached, detached, and destroyed respectively.
	*/	
  public void init(){
    Bootstrapper.incrInitCount();
  }
  
  public void begin(){
    Bootstrapper.incrBeginCount();
  }
	public void end(){
		//TODO Auto-generated method stub
	}
	public void destroy(){
		//TODO Auto-generated method stub
	}

	/*
  	  Implementation primitives required by the architecture
	*/
	
	private final String pluginName = "StandardRequestHandler";
  
    //To be imported: Map,PluginInfo
    public boolean registerStandardRequestHandlerPlugin (final PluginInfo info,final Map<String, PluginInfo> infoMap)   {
      if (info.className.contains(pluginName)) {
        infoMap.put(info.name, info);
        return true;
      }
      
      return false;
    }
}