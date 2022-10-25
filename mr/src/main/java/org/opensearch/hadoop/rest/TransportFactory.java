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
package org.opensearch.hadoop.rest;

import org.opensearch.hadoop.cfg.Settings;
import org.opensearch.hadoop.security.SecureSettings;

/**
 * Creates {@link Transport} Objects
 */
public interface TransportFactory {
    /**
     * Creates a {@link Transport} object
     * @param settings Specifies the Transport's properties
     * @param secureSettings Any secure settings that should be provided
     * @param hostInfo Host to connect to
     */
    Transport create(Settings settings, SecureSettings secureSettings, String hostInfo);
}