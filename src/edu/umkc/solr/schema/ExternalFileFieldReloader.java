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

package edu.umkc.solr.schema;

import org.apache.lucene.index.IndexReader;
import org.apache.solr.common.util.NamedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umkc.solr.core.AbstractSolrEventListener;
import comp.SolrIndexSearcherFactory.SolrIndexSearcherFactoryImp.SolrIndexSearcher;
import edu.umkc.solr.search.function.FileFloatSource;
import edu.umkc.type.ISolrCore;

import java.util.ArrayList;
import java.util.List;

/**
 * An event listener to reload ExternalFileFields for new searchers.
 *
 * Opening a new IndexSearcher will invalidate the internal caches used by
 * {@link ExternalFileField}.  By default, these caches are reloaded lazily
 * by the first search that uses them.  For large external files, this can
 * slow down searches unacceptably.
 *
 * To reload the caches when the searcher is first opened, set up event
 * listeners in your solrconfig.xml:
 *
 * <pre>
 *   &lt;listener event="newSearcher" class="edu.umkc.solr.schema.ExternalFileFieldReloader"/&gt;
 *   &lt;listener event="firstSearcher" class="edu.umkc.solr.schema.ExternalFileFieldReloader"/&gt;
 * </pre>
 *
 * The caches will be reloaded for all ExternalFileFields in your schema after
 * each commit.
 */
public class ExternalFileFieldReloader extends AbstractSolrEventListener {

  private String datadir;
  private List<FileFloatSource> fieldSources = new ArrayList<>();

  private static final Logger log = LoggerFactory.getLogger(ExternalFileFieldReloader.class);

  public ExternalFileFieldReloader(ISolrCore core) {
    super(core);
    datadir = core.getDataDir();
  }

  @Override
  public void init(NamedList args) {
    cacheFieldSources(getCore().getLatestSchema());
  }

  @Override
  public void newSearcher(SolrIndexSearcher newSearcher, SolrIndexSearcher currentSearcher) {
    // We need to reload the caches for the new searcher
    if (null == currentSearcher || newSearcher.getSchema() != currentSearcher.getSchema()) {
      cacheFieldSources(newSearcher.getSchema());
    }
    IndexReader reader = newSearcher.getIndexReader();
    for (FileFloatSource fieldSource : fieldSources) {
      fieldSource.refreshCache(reader);
    }
  }

  /** Caches FileFloatSource's from all ExternalFileField instances in the schema */
  public void cacheFieldSources(IndexSchema schema) {
    fieldSources.clear();
    for (SchemaField field : schema.getFields().values()) {
      FieldType type = field.getType();
      if (type instanceof ExternalFileField) {
        ExternalFileField eff = (ExternalFileField)type;
        fieldSources.add(eff.getFileFloatSource(field, datadir));
        log.info("Adding ExternalFileFieldReloader listener for field {}", field.getName());
      }
    }
  }
}