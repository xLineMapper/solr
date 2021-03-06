package comp.QueryComponentRegister;


import edu.umkc.config.Bootstrapper;
import edu.umkc.solr.handler.component.QueryComponent;
import edu.umkc.solr.handler.component.SearchComponent;

import java.util.Map;

public class QueryComponentRegisterImp implements IQueryComponentRegisterImp
{
	private QueryComponentRegisterArch _arch;

  public QueryComponentRegisterImp (){
  }

	public void setArch(QueryComponentRegisterArch arch){
		_arch = arch;
	}

	public QueryComponentRegisterArch getArch(){
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
  
  //To be imported: Map,SearchComponent
  public void register (Map<String, Class<? extends SearchComponent>> componentBag)   {
		componentBag.put(QueryComponent.COMPONENT_NAME, QueryComponent.class);
  }
}