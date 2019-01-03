package comp.PropertiesRequestHandlerPlugin;


import edu.uci.isr.myx.fw.AbstractMyxSimpleBrick;
import edu.uci.isr.myx.fw.IMyxName;
import edu.uci.isr.myx.fw.MyxUtils;

import edu.umkc.solr.core.PluginInfo;

import edu.umkc.type.tmpl.IPropertiesRequestHandlerPlugin;

import java.util.Map;

public class PropertiesRequestHandlerPluginArch extends AbstractMyxSimpleBrick implements IPropertiesRequestHandlerPlugin
{
    public static final IMyxName msg_IPropertiesRequestHandlerPlugin = MyxUtils.createName("edu.umkc.type.tmpl.IPropertiesRequestHandlerPlugin");


	private IPropertiesRequestHandlerPluginImp _imp;

    public PropertiesRequestHandlerPluginArch (){
		_imp = getImplementation();
		if (_imp != null){
			_imp.setArch(this);
		} else {
			System.exit(1);
		}
	}
    
    protected IPropertiesRequestHandlerPluginImp getImplementation(){
        try{
			return new PropertiesRequestHandlerPluginImp();    
        } catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    public void init(){
        _imp.init();
    }
    
    public void begin(){
        _imp.begin();
    }
    
    public void end(){
        _imp.end();
    }
    
    public void destroy(){
        _imp.destroy();
    }
    
	public Object getServiceObject(IMyxName arg0) {
		if (arg0.equals(msg_IPropertiesRequestHandlerPlugin)){
			return this;
		}        
		return null;
	}
  
    //To be imported: Map,PluginInfo
    public boolean registerPropertiesRequestHandlerPlugin (final PluginInfo info,final Map<String, PluginInfo> infoMap)   {
		return _imp.registerPropertiesRequestHandlerPlugin(info,infoMap);
    }    
}