/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.opensearch.hadoop.serialization.handler.write.impl;

import org.opensearch.hadoop.OpenSearchHadoopIllegalArgumentException;
import org.opensearch.hadoop.handler.ErrorCollector;
import org.opensearch.hadoop.handler.ErrorHandler;
import org.opensearch.hadoop.handler.impl.AbortOnFailure;
import org.opensearch.hadoop.handler.impl.AbstractHandlerLoader;
import org.opensearch.hadoop.handler.impl.DropAndLog;
import org.opensearch.hadoop.handler.impl.elasticsearch.ElasticsearchHandler;
import org.opensearch.hadoop.serialization.handler.write.ISerializationErrorHandler;
import org.opensearch.hadoop.serialization.handler.write.SerializationFailure;

public class SerializationHandlerLoader extends AbstractHandlerLoader<ISerializationErrorHandler> {

    public static final String ES_WRITE_DATA_ERROR_HANDLERS = "es.write.data.error.handlers";
    public static final String ES_WRITE_DATA_ERROR_HANDLER = "es.write.data.error.handler";

    public SerializationHandlerLoader() {
        super(ISerializationErrorHandler.class);
    }

    @Override
    protected String getHandlersPropertyName() {
        return ES_WRITE_DATA_ERROR_HANDLERS;
    }

    @Override
    protected String getHandlerPropertyName() {
        return ES_WRITE_DATA_ERROR_HANDLER;
    }

    @Override
    protected ISerializationErrorHandler loadBuiltInHandler(NamedHandlers handlerName) {
        ErrorHandler<SerializationFailure, Object, ErrorCollector<Object>> genericHandler;
        switch (handlerName) {
            case FAIL:
                genericHandler = AbortOnFailure.create();
                break;
            case LOG:
                genericHandler = DropAndLog.create(new SerializationLogRenderer());
                break;
            case ES:
                genericHandler = ElasticsearchHandler.create(getSettings(), new SerializationEventConverter());
                break;
            default:
                throw new OpenSearchHadoopIllegalArgumentException(
                        "Could not find default implementation for built in handler type [" + handlerName + "]"
                );
        }
        return new DelegatingErrorHandler(genericHandler);
    }
}