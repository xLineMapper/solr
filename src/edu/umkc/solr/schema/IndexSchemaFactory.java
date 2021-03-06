package edu.umkc.solr.schema;
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.solr.common.SolrException;
import org.apache.solr.common.SolrException.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import edu.umkc.solr.core.PluginInfo;
import edu.umkc.solr.core.SolrConfig;
import edu.umkc.solr.util.SystemIdResolver;
import edu.umkc.solr.util.plugin.NamedListInitializedPlugin;
import edu.umkc.type.ISolrResourceLoader;

import java.io.File;
import java.io.InputStream;

/** Base class for factories for IndexSchema implementations */
public abstract class IndexSchemaFactory implements NamedListInitializedPlugin {
  private static final Logger log = LoggerFactory.getLogger(IndexSchemaFactory.class);
  
  /** Returns an index schema created from a local resource */
  public IndexSchema create(String resourceName, SolrConfig config) {
    ISolrResourceLoader loader = config.getResourceLoader();
    InputStream schemaInputStream = null;

    if (null == resourceName) {
      resourceName = IndexSchema.DEFAULT_SCHEMA_FILE;
    }

    try {
      schemaInputStream = loader.openSchema(resourceName);
    } catch (Exception e) {
      final String msg = "Error loading schema resource " + resourceName;
      log.error(msg, e);
      throw new SolrException(ErrorCode.SERVER_ERROR, msg, e);
    }
    InputSource inputSource = new InputSource(schemaInputStream);
    inputSource.setSystemId(SystemIdResolver.createSystemIdFromResourceName(resourceName));
    IndexSchema schema = new IndexSchema(config, resourceName, inputSource);
    return schema;
  }

  /** Instantiates the configured schema factory, then calls create on it. */
  public static IndexSchema buildIndexSchema(String resourceName, SolrConfig config) {
    PluginInfo info = config.getPluginInfo(IndexSchemaFactory.class.getName());
    IndexSchemaFactory factory;
    if (null != info) {
      factory = config.getResourceLoader().newInstance(info.className, IndexSchemaFactory.class);
      factory.init(info.initArgs);
    } else {
      factory = new ClassicIndexSchemaFactory();
    }
    IndexSchema schema = factory.create(resourceName, config);
    return schema;
  }

  /** 
   * Returns the resource name that will be used: if the schema is managed, the resource
   * name will be drawn from the schema factory configuration in the given SolrConfig.
   * Otherwise, the given resourceName will be returned.
   * 
   * @param resourceName The name to use if the schema is not managed
   * @param config The SolrConfig from which to get the schema factory config
   * @return If the schema is managed, the resource name from the given SolrConfig,
   *         otherwise the given resourceName. 
   */
  public static String getResourceNameToBeUsed(String resourceName, SolrConfig config) {
    PluginInfo info = config.getPluginInfo(IndexSchemaFactory.class.getName());
    final String nonManagedResourceName = null == resourceName ? IndexSchema.DEFAULT_SCHEMA_FILE : resourceName;
    if (null == info) {
      return nonManagedResourceName;
    }
    String managedSchemaResourceName
        = (String)info.initArgs.get(ManagedIndexSchemaFactory.MANAGED_SCHEMA_RESOURCE_NAME);
    if (null == managedSchemaResourceName) {
      managedSchemaResourceName = ManagedIndexSchemaFactory.DEFAULT_MANAGED_SCHEMA_RESOURCE_NAME;
    }
    if ((new File(config.getResourceLoader().getConfigDir(), managedSchemaResourceName)).exists()) {
      return managedSchemaResourceName;
    }
    return nonManagedResourceName;
  }
}
