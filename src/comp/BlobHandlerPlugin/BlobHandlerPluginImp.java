package comp.BlobHandlerPlugin;


import edu.umkc.config.Bootstrapper;
import edu.umkc.solr.core.PluginInfo;

import java.util.Map;

import annotation.Feature;
import annotation.Optional;

@Optional(Feature.BLOB_HANDLER)
public class BlobHandlerPluginImp implements IBlobHandlerPluginImp
{
	private BlobHandlerPluginArch _arch;

  public BlobHandlerPluginImp (){
  }

	public void setArch(BlobHandlerPluginArch arch){
		_arch = arch;
	}

	public BlobHandlerPluginArch getArch(){
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
	
  private final String pluginName = "BlobHandler";
  
  //To be imported: Map,PluginInfo
  public boolean registerBlobHandlerPlugin (final PluginInfo info,final Map<String, PluginInfo> infoMap)   {
    if (info.className.contains(pluginName)) {
      infoMap.put(info.name, info);
      return true;
    }
    
    return false;
  }
}