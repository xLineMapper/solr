package comp.CoreAdminHandlerPlugin;


import edu.uci.isr.myx.fw.AbstractMyxSimpleBrick;
import edu.uci.isr.myx.fw.IMyxName;
import edu.uci.isr.myx.fw.MyxUtils;

import edu.umkc.solr.core.PluginInfo;

import edu.umkc.type.tmpl.ICoreAdminHandlerPlugin;

import java.util.Map;

public class CoreAdminHandlerPluginArch extends AbstractMyxSimpleBrick implements ICoreAdminHandlerPlugin
{
    public static final IMyxName msg_ICoreAdminHandlerPlugin = MyxUtils.createName("edu.umkc.type.tmpl.ICoreAdminHandlerPlugin");


	private ICoreAdminHandlerPluginImp _imp;

    public CoreAdminHandlerPluginArch (){
		_imp = getImplementation();
		if (_imp != null){
			_imp.setArch(this);
		} else {
			System.exit(1);
		}
	}
    
    protected ICoreAdminHandlerPluginImp getImplementation(){
        try{
			return new CoreAdminHandlerPluginImp();    
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
		if (arg0.equals(msg_ICoreAdminHandlerPlugin)){
			return this;
		}        
		return null;
	}
  
    //To be imported: Map,PluginInfo
    public boolean registerCoreAdminHandlerPlugin (final PluginInfo info,final Map<String, PluginInfo> infoMap)   {
		return _imp.registerCoreAdminHandlerPlugin(info,infoMap);
    }    
}