package comp.PythonResponseWriter;


import edu.uci.isr.myx.fw.AbstractMyxSimpleBrick;
import edu.uci.isr.myx.fw.IMyxName;
import edu.uci.isr.myx.fw.MyxUtils;

import edu.umkc.solr.request.SolrQueryRequest;

import edu.umkc.solr.response.QueryResponseWriter;
import edu.umkc.solr.response.SolrQueryResponse;

import edu.umkc.type.tmpl.IPythonResponseWriter;

import java.io.IOException;
import java.io.Writer;

import org.apache.solr.common.util.NamedList;

public class PythonResponseWriterArch extends AbstractMyxSimpleBrick implements IPythonResponseWriter
{
    public static final IMyxName msg_IPythonResponseWriter = MyxUtils.createName("edu.umkc.type.tmpl.IPythonResponseWriter");


	private IPythonResponseWriterImp _imp;

    public PythonResponseWriterArch (){
		_imp = getImplementation();
		if (_imp != null){
			_imp.setArch(this);
		} else {
			System.exit(1);
		}
	}
    
    protected IPythonResponseWriterImp getImplementation(){
        try{
			return new PythonResponseWriterImp();    
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
		if (arg0.equals(msg_IPythonResponseWriter)){
			return this;
		}        
		return null;
	}
  
    //To be imported: IOException,Writer,NamedList,SolrQueryRequest,QueryResponseWriter,SolrQueryResponse
    public void write (Writer writer,SolrQueryRequest request,SolrQueryResponse response) throws IOException {
		_imp.write(writer,request,response);
    }    
    public String getContentType (SolrQueryRequest request,SolrQueryResponse response)   {
		return _imp.getContentType(request,response);
    }    
    public void init (NamedList args)   {
		_imp.init(args);
    }    
}