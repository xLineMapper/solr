package comp.CoreContainer;


import static com.google.common.base.Preconditions.checkNotNull;
import edu.umkc.config.Bootstrapper;
import edu.umkc.solr.cloud.ZkController;
import edu.umkc.solr.core.ConfigSet;
import edu.umkc.solr.core.ConfigSetService;
import edu.umkc.solr.core.CoreDescriptor;
import edu.umkc.solr.core.CorePropertiesLocator;
import edu.umkc.solr.core.CoresLocator;
import edu.umkc.solr.core.JarRepository;
import edu.umkc.solr.core.NodeConfig;
import edu.umkc.solr.core.PluginBag;
import edu.umkc.solr.core.ZkContainer;
import edu.umkc.solr.handler.RequestHandlerBase;
import edu.umkc.solr.handler.admin.CollectionsHandler;
import edu.umkc.solr.handler.admin.CoreAdminHandler;
import edu.umkc.solr.handler.admin.InfoHandler;
import edu.umkc.solr.handler.component.HttpShardHandlerFactory;
import edu.umkc.solr.handler.component.ShardHandlerFactory;
import edu.umkc.solr.logging.LogWatcher;
import edu.umkc.solr.request.SolrRequestHandler;
import edu.umkc.solr.security.AuthenticationPlugin;
import edu.umkc.solr.security.AuthorizationPlugin;
import edu.umkc.solr.update.UpdateShardHandler;
import edu.umkc.solr.util.DefaultSolrThreadFactory;
import edu.umkc.solr.util.FileUtils;
import edu.umkc.type.ICoreContainer;
import edu.umkc.type.ISolrCore;
import edu.umkc.type.ISolrCores;
import edu.umkc.type.ISolrResourceLoader;
import edu.umkc.type.tmpl.CoreLoadFailure;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

import org.apache.solr.client.solrj.impl.HttpClientConfigurer;
import org.apache.solr.client.solrj.impl.HttpClientUtil;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.SolrException.ErrorCode;
import org.apache.solr.common.util.ExecutorUtil;
import org.apache.solr.common.util.IOUtils;
import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import comp.SolrCoreFactory.SolrCoreFactoryImp.SolrCore;
import comp.SolrResourceLoader.SolrResourceLoaderImp;

public class CoreContainerImp implements ICoreContainerImp, ICoreContainer
{
  private CoreContainerArch _arch;

  public CoreContainerImp (){
  }
  
  public CoreContainerImp (final NodeConfig config) {
    initCoreContainer(config);
  }
  
  public CoreContainerImp (NodeConfig config, Properties properties) {
    this(config, properties, new CorePropertiesLocator(config.getCoreRootDirectory()));
  }
  
  public CoreContainerImp (NodeConfig config, Properties properties, CoresLocator locator) {
    this.solrHome = config.getSolrResourceLoader().getInstanceDir();
    this.cfg = checkNotNull(config);
    this.coresLocator = locator;
    this.containerProperties = new Properties(properties);
  }
  
  public void setArch(CoreContainerArch arch){
    _arch = arch;
  }
  
  public CoreContainerArch getArch(){
    return _arch;
  }

  /*
      Myx Lifecycle Methods: these methods are called automatically by the framework
      as the bricks are created, attached, detached, and destroyed respectively.
  */  
  public void init() {
    Bootstrapper.incrInitCount();
  }
  
  public void begin() {
    solrCores = _arch.OUT_ISolrCoresBuilder.createSolrCores(this);
    
    Bootstrapper.incrBeginCount();
  }

  public void end() {
    //TODO Auto-generated method stub
  }
  
  public void destroy() {
    //TODO Auto-generated method stub
  }

  /*
      Implementation primitives required by the architecture
  */

  public ICoreContainer initCoreContainer (final ISolrResourceLoader loader) {
    return initCoreContainer(_arch.OUT_ISolrXmlConfig.fromSolrHome(loader, loader.getInstanceDir()));
  }
  
  public ICoreContainer initCoreContainer (final NodeConfig config) {
    return initCoreContainer(config, new Properties());
  }
  
  public ICoreContainer initCoreContainer (final NodeConfig config, final Properties properties) {
    return initCoreContainer(config, properties, new CorePropertiesLocator(config.getCoreRootDirectory()));
  }
  
  public ICoreContainer initCoreContainer (final NodeConfig config, final Properties properties, final CoresLocator locator) {
    solrHome = config.getSolrResourceLoader().getInstanceDir();
    cfg = checkNotNull(config);
    coresLocator = locator;
    containerProperties = new Properties(properties);
    
    return this;
  }
  
  public ICoreContainer initCoreContainer (final String solrHome) {
    return initCoreContainer(new SolrResourceLoaderImp(solrHome));
  }

  public static final Logger log = LoggerFactory.getLogger(CoreContainerImp.class);

  public ISolrCores solrCores;

  protected AuthorizationPlugin authorizationPlugin;

  protected AuthenticationPlugin authenticationPlugin;

  protected final Map<String, CoreLoadFailure> coreInitFailures = new ConcurrentHashMap<>();

  protected CoreAdminHandler coreAdminHandler = null;
  protected CollectionsHandler collectionsHandler = null;
  private InfoHandler infoHandler;
  
  protected Properties containerProperties;

  private ConfigSetService coreConfigService;
  
  protected ZkContainer zkSys = new ZkContainer();
  protected ShardHandlerFactory shardHandlerFactory;
  
  private UpdateShardHandler updateShardHandler;

  protected LogWatcher logging = null;

  private CloserThread backgroundCloser = null;
  protected NodeConfig cfg;
//  protected ISolrResourceLoader loader;

  protected String solrHome;

  protected CoresLocator coresLocator;
  
  private String hostName;

  private final JarRepository jarRepository = new JarRepository(this);

  final public static String CORES_HANDLER_PATH       = "/admin/cores";
  final public static String COLLECTIONS_HANDLER_PATH = "/admin/collections";
  final public static String INFO_HANDLER_PATH        = "/admin/info";

  final public static String AUTHENTICATION_PLUGIN_PROP = "authenticationPlugin";

  private PluginBag<SolrRequestHandler> containerHandlers = new PluginBag<>(SolrRequestHandler.class, null);

  public ExecutorService getCoreZkRegisterExecutorService() {
    return zkSys.getCoreZkRegisterExecutorService();
  }

  public SolrRequestHandler getRequestHandler(String path) {
    return RequestHandlerBase.getRequestHandler(path, containerHandlers);
  }

  public PluginBag<SolrRequestHandler> getRequestHandlers() {
    return this.containerHandlers;
  }

  {
    log.info("New CoreContainer " + System.identityHashCode(this));
  }

  private void intializeAuthorizationPlugin() {
    //Initialize the Authorization module
    Map securityProps = getZkController().getZkStateReader().getSecurityProps();
    if(securityProps != null) {
      Map authorizationConf = (Map) securityProps.get("authorization");
      if(authorizationConf == null) return;
      String klas = (String) authorizationConf.get("class");
      if(klas == null){
        throw new SolrException(ErrorCode.SERVER_ERROR, "class is required for authorization plugin");
      }
      log.info("Initializing authorization plugin: " + klas);
      authorizationPlugin = getResourceLoader().newInstance((String) klas,
          AuthorizationPlugin.class);

      // Read and pass the authorization context to the plugin
      authorizationPlugin.init(authorizationConf);
    } else {
      log.info("Security conf doesn't exist. Skipping setup for authorization module.");
    }
  }

  private void initializeAuthenticationPlugin() {
    String pluginClassName = null;
    Map<String, Object> authenticationConfig = null;

    if (isZooKeeperAware()) {
      Map securityProps = getZkController().getZkStateReader().getSecurityProps();
      if (securityProps != null) {
        authenticationConfig = (Map<String, Object>) securityProps.get("authentication");
        if (authenticationConfig!=null) {
          if (authenticationConfig.containsKey("class")) {
            pluginClassName = String.valueOf(authenticationConfig.get("class"));
          } else {
            throw new SolrException(ErrorCode.SERVER_ERROR, "No 'class' specified for authentication in ZK.");
          }
        }
      }
    }

    if (pluginClassName != null) {
      log.info("Authentication plugin class obtained from ZK: "+pluginClassName);
    } else if (System.getProperty(AUTHENTICATION_PLUGIN_PROP) != null) {
      pluginClassName = System.getProperty(AUTHENTICATION_PLUGIN_PROP);
      log.info("Authentication plugin class obtained from system property '" +
          AUTHENTICATION_PLUGIN_PROP + "': " + pluginClassName);
    } else {
      log.info("No authentication plugin used.");
    }

    // Initialize the filter
    if (pluginClassName != null) {
      try {
        Class cl = Class.forName(pluginClassName);
        authenticationPlugin = (AuthenticationPlugin) cl.newInstance();
      } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
        throw new SolrException(ErrorCode.SERVER_ERROR, e);
      }
    }
    if (authenticationPlugin != null) {
      authenticationPlugin.init(authenticationConfig);

      // Setup HttpClient to use the plugin's configurer for internode communication
      HttpClientConfigurer configurer = authenticationPlugin.getDefaultConfigurer();
      HttpClientUtil.setConfigurer(configurer);

      // The default http client of the core container's shardHandlerFactory has already been created and
      // configured using the default httpclient configurer. We need to reconfigure it using the plugin's
      // http client configurer to set it up for internode communication.
      log.info("Reconfiguring the shard handler factory and update shard handler.");
      if (getShardHandlerFactory() instanceof HttpShardHandlerFactory) {
        ((HttpShardHandlerFactory)getShardHandlerFactory()).reconfigureHttpClient(configurer);
      }
      getUpdateShardHandler().reconfigureHttpClient(configurer);
    }
  }
  
  public Properties getContainerProperties() {
    return containerProperties;
  }

  //-------------------------------------------------------------------
  // Initialization / Cleanup
  //-------------------------------------------------------------------

  /**
   * Load the cores defined for this CoreContainer
   */
  public void load()  {

    log.info("Loading cores into CoreContainer [instanceDir={}]", _arch.OUT_ISolrResourceLoader.getInstanceDir());

    // add the sharedLib to the shared resource loader before initializing cfg based plugins
    String libDir = cfg.getSharedLibDirectory();
    if (libDir != null) {
      File f = FileUtils.resolvePath(new File(solrHome), libDir);
      log.info("loading shared library: " + f.getAbsolutePath());
      _arch.OUT_ISolrResourceLoader.addToClassLoader(libDir, null, false);
      _arch.OUT_ISolrResourceLoader.reloadLuceneSPI();
    }


    shardHandlerFactory = ShardHandlerFactory.newInstance(cfg.getShardHandlerFactoryPluginInfo(), _arch.OUT_ISolrResourceLoader);

    updateShardHandler = new UpdateShardHandler(cfg.getUpdateShardHandlerConfig());

    solrCores.allocateLazyCores(cfg.getTransientCacheSize(), _arch.OUT_ISolrResourceLoader);

    logging = LogWatcher.newRegisteredLogWatcher(cfg.getLogWatcherConfig(), _arch.OUT_ISolrResourceLoader);

    hostName = cfg.getNodeName();
    log.info("Node Name: " + hostName);

    zkSys.initZooKeeper(this, solrHome, cfg.getCloudConfig());

    initializeAuthenticationPlugin();

    if (isZooKeeperAware()) {
      intializeAuthorizationPlugin();
    }

    collectionsHandler = createHandler(cfg.getCollectionsHandlerClass(), CollectionsHandler.class);
    containerHandlers.put(COLLECTIONS_HANDLER_PATH, collectionsHandler);
    infoHandler        = createHandler(cfg.getInfoHandlerClass(), InfoHandler.class);
    containerHandlers.put(INFO_HANDLER_PATH, infoHandler);
    coreAdminHandler   = createHandler(cfg.getCoreAdminHandlerClass(), CoreAdminHandler.class);
    containerHandlers.put(CORES_HANDLER_PATH, coreAdminHandler);

    coreConfigService = ConfigSetService.createConfigSetService(cfg, _arch.OUT_ISolrResourceLoader, zkSys.zkController);

    containerProperties.putAll(cfg.getSolrProperties());

    // setup executor to load cores in parallel
    // do not limit the size of the executor in zk mode since cores may try and wait for each other.
    ExecutorService coreLoadExecutor = ExecutorUtil.newMDCAwareFixedThreadPool(
        ( zkSys.getZkController() == null ? cfg.getCoreLoadThreadCount() : Integer.MAX_VALUE ),
        new DefaultSolrThreadFactory("coreLoadExecutor") );

    try {

      List<CoreDescriptor> cds = coresLocator.discover(this);
      checkForDuplicateCoreNames(cds);

      List<Callable<ISolrCore>> creators = new ArrayList<>();
      for (final CoreDescriptor cd : cds) {
        if (cd.isTransient() || !cd.isLoadOnStartup()) {
          solrCores.putDynamicDescriptor(cd.getName(), cd);
        }
        if (cd.isLoadOnStartup()) {
          creators.add(new Callable<ISolrCore>() {
            @Override
            public ISolrCore call() throws Exception {
              if (zkSys.getZkController() != null) {
                zkSys.getZkController().throwErrorIfReplicaReplaced(cd);
              }
              return create(cd, false);   
            }
          });
        }
      }

      try {
        coreLoadExecutor.invokeAll(creators);
      }
      catch (InterruptedException e) {
        throw new SolrException(SolrException.ErrorCode.SERVICE_UNAVAILABLE, "Interrupted while loading cores");
      }

      // Start the background thread
      backgroundCloser = new CloserThread(this, solrCores, cfg);
      backgroundCloser.start();

    } finally {
      ExecutorUtil.shutdownNowAndAwaitTermination(coreLoadExecutor);
    }
    
    if (isZooKeeperAware()) {
      // register in zk in background threads
      Collection<ISolrCore> cores = getCores();
      if (cores != null) {
        for (ISolrCore core : cores) {
          try {
            zkSys.registerInZk(core, true);
          } catch (Throwable t) {
            SolrException.log(log, "Error registering SolrCore", t);
          }
        }
      }
      zkSys.getZkController().checkOverseerDesignate();
    }
  }

  private static void checkForDuplicateCoreNames(List<CoreDescriptor> cds) {
    Map<String, String> addedCores = Maps.newHashMap();
    for (CoreDescriptor cd : cds) {
      final String name = cd.getName();
      if (addedCores.containsKey(name))
        throw new SolrException(ErrorCode.SERVER_ERROR,
            String.format(Locale.ROOT, "Found multiple cores with the name [%s], with instancedirs [%s] and [%s]",
                name, addedCores.get(name), cd.getInstanceDir()));
      addedCores.put(name, cd.getInstanceDir());
    }
  }

  private volatile boolean isShutDown = false;
  
  public boolean isShutDown() {
    return isShutDown;
  }

  /**
   * Stops all cores.
   */
  public void shutdown() {
    log.info("Shutting down CoreContainer instance="
        + System.identityHashCode(this));
    
    isShutDown = true;
    
    if (isZooKeeperAware()) {
      cancelCoreRecoveries();
      zkSys.publishCoresAsDown(solrCores.getCores());
    }

    try {
      if (coreAdminHandler != null) coreAdminHandler.shutdown();
    } catch (Exception e) {
      log.warn("Error shutting down CoreAdminHandler. Continuing to close CoreContainer.", e);
    }

    try {
      // First wake up the closer thread, it'll terminate almost immediately since it checks isShutDown.
      synchronized (solrCores.getModifyLock()) {
        solrCores.getModifyLock().notifyAll(); // wake up anyone waiting
      }
      if (backgroundCloser != null) { // Doesn't seem right, but tests get in here without initializing the core.
        try {
          backgroundCloser.join();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          if (log.isDebugEnabled()) {
            log.debug("backgroundCloser thread was interrupted before finishing");
          }
        }
      }
      // Now clear all the cores that are being operated upon.
      solrCores.close();

      // It's still possible that one of the pending dynamic load operation is waiting, so wake it up if so.
      // Since all the pending operations queues have been drained, there should be nothing to do.
      synchronized (solrCores.getModifyLock()) {
        solrCores.getModifyLock().notifyAll(); // wake up the thread
      }

    } finally {
      try {
        if (shardHandlerFactory != null) {
          shardHandlerFactory.close();
        }
      } finally {
        try {
          if (updateShardHandler != null) {
            updateShardHandler.close();
          }
        } finally {
          // we want to close zk stuff last
          zkSys.close();
        }
      }
    }
    
    // It should be safe to close the authorization plugin at this point.
    try {
      if(authorizationPlugin != null) {
        authorizationPlugin.close();
      }
    } catch (IOException e) {
      log.warn("Exception while closing authorization plugin.", e);
    }
    
    // It should be safe to close the authentication plugin at this point.
    try {
      if(authenticationPlugin != null) {
        authenticationPlugin.close();
        authenticationPlugin = null;
      }
    } catch (Exception e) {
      log.warn("Exception while closing authentication plugin.", e);
    }

    org.apache.lucene.util.IOUtils.closeWhileHandlingException(_arch.OUT_ISolrResourceLoader); // best effort
  }

  public void cancelCoreRecoveries() {

    List<ISolrCore> cores = solrCores.getCores();

    // we must cancel without holding the cores sync
    // make sure we wait for any recoveries to stop
    for (ISolrCore core : cores) {
      try {
        core.getSolrCoreState().cancelRecovery();
      } catch (Exception e) {
        SolrException.log(log, "Error canceling recovery for core", e);
      }
    }
  }
  
  @Override
  protected void finalize() throws Throwable {
    try {
      if(!isShutDown){
        log.error("CoreContainer was not close prior to finalize(), indicates a bug -- POSSIBLE RESOURCE LEAK!!!  instance=" + System.identityHashCode(this));
      }
    } finally {
      super.finalize();
    }
  }

  public CoresLocator getCoresLocator() {
    return coresLocator;
  }
  
  protected ISolrCore registerCore(String name, ISolrCore core, boolean registerInZk) {
    if( core == null ) {
      throw new RuntimeException( "Can not register a null core." );
    }
    if( name == null ||
        name.indexOf( '/'  ) >= 0 ||
        name.indexOf( '\\' ) >= 0 ){
      throw new RuntimeException( "Invalid core name: "+name );
    }
    // We can register a core when creating them via the admin UI, so we need to insure that the dynamic descriptors
    // are up to date
    CoreDescriptor cd = core.getCoreDescriptor();
    if ((cd.isTransient() || ! cd.isLoadOnStartup())
        && solrCores.getDynamicDescriptor(name) == null) {
      // Store it away for later use. includes non-transient but not
      // loaded at startup cores.
      solrCores.putDynamicDescriptor(name, cd);
    }

    ISolrCore old;

    if (isShutDown) {
      core.close();
      throw new IllegalStateException("This CoreContainer has been close");
    }
    if (cd.isTransient()) {
      old = solrCores.putTransientCore(cfg, name, core, _arch.OUT_ISolrResourceLoader);
    } else {
      old = solrCores.putCore(name, core);
    }
      /*
      * set both the name of the descriptor and the name of the
      * core, since the descriptors name is used for persisting.
      */

    core.setName(name);

    coreInitFailures.remove(name);

    if( old == null || old == core) {
      log.info( "registering core: "+name );
      if (registerInZk) {
        zkSys.registerInZk(core, false);
      }
      return null;
    }
    else {
      log.info( "replacing core: "+name );
      old.close();
      if (registerInZk) {
        zkSys.registerInZk(core, false);
      }
      return old;
    }
  }

  /**
   * Creates a new core based on a CoreDescriptor, publishing the core state to the cluster
   * @param cd the CoreDescriptor
   * @return the newly created core
   */
  public ISolrCore create(CoreDescriptor cd) {
    return create(cd, true);
  }

  /**
   * Creates a new core based on a CoreDescriptor.
   *
   * @param dcore        a core descriptor
   * @param publishState publish core state to the cluster if true
   *
   * @return the newly created core
   */
  public ISolrCore create(CoreDescriptor dcore, boolean publishState) {

    if (isShutDown) {
      throw new SolrException(ErrorCode.SERVICE_UNAVAILABLE, "Solr has close.");
    }

    ISolrCore core = null;
    try {

      if (zkSys.getZkController() != null) {
        zkSys.getZkController().preRegister(dcore);
      }

      ConfigSet coreConfig = coreConfigService.getConfig(dcore);
      log.info("Creating SolrCore '{}' using configuration from {}", dcore.getName(), coreConfig.getName());
      // TODO: there should be a SolrCore manager
      core = _arch.OUT_ISolrCoreFactory.createSolrCore(dcore, coreConfig);;
      solrCores.addCreated(core);

      // always kick off recovery if we are in non-Cloud mode
      if (!isZooKeeperAware() && core.getUpdateHandler().getUpdateLog() != null) {
        core.getUpdateHandler().getUpdateLog().recoverFromLog();
      }

      registerCore(dcore.getName(), core, publishState);

      return core;
    } catch (Exception e) {
      coreInitFailures.put(dcore.getName(), new CoreLoadFailure(dcore, e));
      log.error("Error creating core [{}]: {}", dcore.getName(), e.getMessage(), e);
      final SolrException solrException = new SolrException(ErrorCode.SERVER_ERROR, "Unable to create core [" + dcore.getName() + "]", e);
      if(core != null && !core.isClosed())
        IOUtils.closeQuietly(core);
      throw solrException;
    } catch (Throwable t) {
      SolrException e = new SolrException(ErrorCode.SERVER_ERROR, "JVM Error creating core [" + dcore.getName() + "]: " + t.getMessage(), t);
      log.error("Error creating core [{}]: {}", dcore.getName(), t.getMessage(), t);
      coreInitFailures.put(dcore.getName(), new CoreLoadFailure(dcore, e));
      if(core != null && !core.isClosed())
        IOUtils.closeQuietly(core);
      throw t;
    }

  }

  /**
   * @return a Collection of registered SolrCores
   */
  public Collection<ISolrCore> getCores() {
    return solrCores.getCores();
  }

  /**
   * @return a Collection of the names that cores are mapped to
   */
  public Collection<String> getCoreNames() {
    return solrCores.getCoreNames();
  }

  /** This method is currently experimental.
   * @return a Collection of the names that a specific core is mapped to.
   */
  public Collection<String> getCoreNames(ISolrCore core) {
    return solrCores.getCoreNames(core);
  }

  /**
   * get a list of all the cores that are currently loaded
   * @return a list of al lthe available core names in either permanent or transient core lists.
   */
  public Collection<String> getAllCoreNames() {
    return solrCores.getAllCoreNames();

  }

  /**
   * Returns an immutable Map of Exceptions that occured when initializing 
   * SolrCores (either at startup, or do to runtime requests to create cores) 
   * keyed off of the name (String) of the SolrCore that had the Exception 
   * during initialization.
   * <p>
   * While the Map returned by this method is immutable and will not change 
   * once returned to the client, the source data used to generate this Map 
   * can be changed as various SolrCore operations are performed:
   * </p>
   * <ul>
   *  <li>Failed attempts to create new SolrCores will add new Exceptions.</li>
   *  <li>Failed attempts to re-create a SolrCore using a name already contained in this Map will replace the Exception.</li>
   *  <li>Failed attempts to reload a SolrCore will cause an Exception to be added to this list -- even though the existing SolrCore with that name will continue to be available.</li>
   *  <li>Successful attempts to re-created a SolrCore using a name already contained in this Map will remove the Exception.</li>
   *  <li>Registering an existing SolrCore with a name already contained in this Map (ie: ALIAS or SWAP) will remove the Exception.</li>
   * </ul>
   */
  public Map<String, CoreLoadFailure> getCoreInitFailures() {
    return ImmutableMap.copyOf(coreInitFailures);
  }


  // ---------------- Core name related methods --------------- 
  /**
   * Recreates a SolrCore.
   * While the new core is loading, requests will continue to be dispatched to
   * and processed by the old core
   * 
   * @param name the name of the SolrCore to reload
   */
  public void reload(String name) {

    ISolrCore core = solrCores.getCoreFromAnyList(name, false);
    if (core == null)
      throw new SolrException( SolrException.ErrorCode.BAD_REQUEST, "No such core: " + name );

    CoreDescriptor cd = core.getCoreDescriptor();
    try {
      solrCores.waitAddPendingCoreOps(name);
      ConfigSet coreConfig = coreConfigService.getConfig(cd);
      log.info("Reloading SolrCore '{}' using configuration from {}", cd.getName(), coreConfig.getName());
      ISolrCore newCore = core.reload(coreConfig);
      registerCore(name, newCore, false);
    }
    catch (Exception e) {
      coreInitFailures.put(cd.getName(), new CoreLoadFailure(cd, e));
      throw new SolrException(ErrorCode.SERVER_ERROR, "Unable to reload core [" + cd.getName() + "]", e);
    }
    finally {
      solrCores.removeFromPendingOps(name);
    }

  }

  /**
   * Swaps two SolrCore descriptors.
   */
  public void swap(String n0, String n1) {
    if( n0 == null || n1 == null ) {
      throw new SolrException( SolrException.ErrorCode.BAD_REQUEST, "Can not swap unnamed cores." );
    }
    solrCores.swap(n0, n1);

    coresLocator.swap(this, solrCores.getCoreDescriptor(n0), solrCores.getCoreDescriptor(n1));

    log.info("swapped: " + n0 + " with " + n1);
  }

  /**
   * Unload a core from this container, leaving all files on disk
   * @param name the name of the core to unload
   */
  public void unload(String name) {
    unload(name, false, false, false);
  }

  /**
   * Unload a core from this container, optionally removing the core's data and configuration
   *
   * @param name the name of the core to unload
   * @param deleteIndexDir if true, delete the core's index on close
   * @param deleteDataDir if true, delete the core's data directory on close
   * @param deleteInstanceDir if true, delete the core's instance directory on close
   */
  public void unload(String name, boolean deleteIndexDir, boolean deleteDataDir, boolean deleteInstanceDir) {

    // check for core-init errors first
    CoreLoadFailure loadFailure = coreInitFailures.remove(name);
    if (loadFailure != null) {
      // getting the index directory requires opening a DirectoryFactory with a SolrConfig, etc,
      // which we may not be able to do because of the init error.  So we just go with what we
      // can glean from the CoreDescriptor - datadir and instancedir
      SolrCore.deleteUnloadedCore(loadFailure.cd, deleteDataDir, deleteInstanceDir);
      return;
    }

    CoreDescriptor cd = solrCores.getCoreDescriptor(name);
    if (cd == null)
      throw new SolrException(ErrorCode.BAD_REQUEST, "Cannot unload non-existent core [" + name + "]");

    boolean close = solrCores.isLoadedNotPendingClose(name);
    ISolrCore core = solrCores.remove(name);
    coresLocator.delete(this, cd);

    if (core == null) {
      // transient core
      SolrCore.deleteUnloadedCore(cd, deleteDataDir, deleteInstanceDir);
      return;
    }

    if (zkSys.getZkController() != null) {
      // cancel recovery in cloud mode
      core.getSolrCoreState().cancelRecovery();
    }
    
    core.unloadOnClose(deleteIndexDir, deleteDataDir, deleteInstanceDir);
    if (close)
      core.close();

    if (zkSys.getZkController() != null) {
      try {
        zkSys.getZkController().unregister(name, cd);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new SolrException(ErrorCode.SERVER_ERROR, "Interrupted while unregistering core [" + name + "] from cloud state");
      } catch (KeeperException e) {
        throw new SolrException(ErrorCode.SERVER_ERROR, "Error unregistering core [" + name + "] from cloud state", e);
      }
    }
  }

  public void rename(String name, String toName) {
    try (ISolrCore core = getCore(name)) {
      if (core != null) {
        registerCore(toName, core, true);
        ISolrCore old = solrCores.remove(name);
        coresLocator.rename(this, old.getCoreDescriptor(), core.getCoreDescriptor());
      }
    }
  }

  /**
   * Get the CoreDescriptors for all cores managed by this container
   * @return a List of CoreDescriptors
   */
  public List<CoreDescriptor> getCoreDescriptors() {
    return solrCores.getCoreDescriptors();
  }

  public CoreDescriptor getCoreDescriptor(String coreName) {
    // TODO make this less hideous!
    for (CoreDescriptor cd : getCoreDescriptors()) {
      if (cd.getName().equals(coreName))
        return cd;
    }
    return null;
  }

  public String getCoreRootDirectory() {
    return cfg.getCoreRootDirectory();
  }

  public ISolrCore getCore(String name) {

    // Do this in two phases since we don't want to lock access to the cores over a load.
    ISolrCore core = solrCores.getCoreFromAnyList(name, true);

    if (core != null) {
      return core;
    }

    // OK, it's not presently in any list, is it in the list of dynamic cores but not loaded yet? If so, load it.
    CoreDescriptor desc = solrCores.getDynamicDescriptor(name);
    if (desc == null) { //Nope, no transient core with this name

      // if there was an error initalizing this core, throw a 500
      // error with the details for clients attempting to access it.
      CoreLoadFailure loadFailure = getCoreInitFailures().get(name);
      if (null != loadFailure) {
        throw new SolrException(ErrorCode.SERVER_ERROR, "SolrCore '" + name +
                                "' is not available due to init failure: " +
                                loadFailure.exception.getMessage(), loadFailure.exception);
      }
      // otherwise the user is simply asking for something that doesn't exist.
      return null;
    }

    // This will put an entry in pending core ops if the core isn't loaded
    core = solrCores.waitAddPendingCoreOps(name);

    if (isShutDown) return null; // We're quitting, so stop. This needs to be after the wait above since we may come off
                                 // the wait as a consequence of shutting down.
    try {
      if (core == null) {
        if (zkSys.getZkController() != null) {
          zkSys.getZkController().throwErrorIfReplicaReplaced(desc);
        }
        core = create(desc); // This should throw an error if it fails.
      }
      core.open();
    }
    finally {
      solrCores.removeFromPendingOps(name);
    }

    return core;
  }

  public JarRepository getJarRepository(){
    return jarRepository;
  }

  // ---------------- CoreContainer request handlers --------------

  protected <T> T createHandler(String handlerClass, Class<T> clazz) {
    return _arch.OUT_ISolrResourceLoader.newInstance(handlerClass, clazz, null, new Class[] { ICoreContainer.class }, new Object[] { this });
  }

  public String getHostName() {
    return this.hostName;
  }

  /**
   * Gets the alternate path for multicore handling:
   * This is used in case there is a registered unnamed core (aka name is "") to
   * declare an alternate way of accessing named cores.
   * This can also be used in a pseudo single-core environment so admins can prepare
   * a new version before swapping.
   */
  public String getManagementPath() {
    return cfg.getManagementPath();
  }

  public LogWatcher getLogging() {
    return logging;
  }

  /**
   * Determines whether the core is already loaded or not but does NOT load the core
   *
   */
  public boolean isLoaded(String name) {
    return solrCores.isLoaded(name);
  }

  public boolean isLoadedNotPendingClose(String name) {
    return solrCores.isLoadedNotPendingClose(name);
  }

  /**
   * Gets a solr core descriptor for a core that is not loaded. Note that if the caller calls this on a
   * loaded core, the unloaded descriptor will be returned.
   *
   * @param cname - name of the unloaded core descriptor to load. NOTE:
   * @return a coreDescriptor. May return null
   */
  public CoreDescriptor getUnloadedCoreDescriptor(String cname) {
    return solrCores.getUnloadedCoreDescriptor(cname);
  }

  public String getSolrHome() {
    return solrHome;
  }

  public boolean isZooKeeperAware() {
    return zkSys.getZkController() != null;
  }
  
  public ZkController getZkController() {
    return zkSys.getZkController();
  }
  
  public NodeConfig getConfig() {
    return cfg;
  }

  /** The default ShardHandlerFactory used to communicate with other solr instances */
  public ShardHandlerFactory getShardHandlerFactory() {
    return shardHandlerFactory;
  }
  
  public UpdateShardHandler getUpdateShardHandler() {
    return updateShardHandler;
  }

  public ISolrResourceLoader getResourceLoader() {
    return _arch.OUT_ISolrResourceLoader;
  }

  public AuthorizationPlugin getAuthorizationPlugin() {
    return authorizationPlugin;
  }

  public AuthenticationPlugin getAuthenticationPlugin() {
    return authenticationPlugin;
  }

}