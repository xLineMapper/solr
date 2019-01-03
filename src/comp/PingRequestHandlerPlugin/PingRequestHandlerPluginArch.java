package comp.PingRequestHandlerPlugin;


import edu.uci.isr.myx.fw.AbstractMyxSimpleBrick;
import edu.uci.isr.myx.fw.IMyxName;
import edu.uci.isr.myx.fw.MyxUtils;

import edu.umkc.solr.core.PluginInfo;

import edu.umkc.type.tmpl.IPingRequestHandlerPlugin;

import java.util.Map;

public class PingRequestHandlerPluginArch extends AbstractMyxSimpleBrick implements IPingRequestHandlerPlugin
{
    public static final IMyxName msg_IPingRequestHandlerPlugin = MyxUtils.createName("edu.umkc.type.tmpl.IPingRequestHandlerPlugin");


	private IPingRequestHandlerPluginImp _imp;

    public PingRequestHandlerPluginArch (){
		_imp = getImplementation();
		if (_imp != null){
			_imp.setArch(this);
		} else {
			System.exit(1);
		}
	}
    
    protected IPingRequestHandlerPluginImp getImplementation(){
        try{
			return new PingRequestHandlerPluginImp();    
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
		if (arg0.equals(msg_IPingRequestHandlerPlugin)){
			return this;
		}        
		return null;
	}
  
    //To be imported: Map,PluginInfo
    public boolean registerPingRequestHandlerPlugin (final PluginInfo info,final Map<String, PluginInfo> infoMap)   {
		return _imp.registerPingRequestHandlerPlugin(info,infoMap);
    }    
}