/*
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
*/
package com.ibm.research.drl.dpt.managers;

import com.ibm.research.drl.dpt.util.Tuple;
import com.ibm.research.drl.dpt.util.localization.LocalizationManager;
import com.ibm.research.drl.dpt.util.localization.Resource;
import com.ibm.research.drl.dpt.util.localization.ResourceEntry;
import org.apache.commons.csv.CSVRecord;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class StreetNameManager extends ResourceBasedManager<String> {
    private static final Collection<ResourceEntry> resourceStreetNameList =
            LocalizationManager.getInstance().getResources(Resource.STREET_NAMES);

    private final static StreetNameManager STREET_NAME_MANAGER = new StreetNameManager();

    public static StreetNameManager getInstance() {
        return STREET_NAME_MANAGER;
    }

    private StreetNameManager() {
        super();
    }


    @Override
    protected Collection<ResourceEntry> getResources() {
        return resourceStreetNameList;
    }

    @Override
    protected List<Tuple<String, String>> parseResourceRecord(CSVRecord line, String countryCode) {
        String sname = line.get(0).trim();
        String key = sname.toUpperCase();

        return List.of(new Tuple<>(key, sname));
    }

    @Override
    public Collection<String> getItemList() {
        return getValues();
    }
}
