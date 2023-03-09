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
package com.ibm.research.drl.dpt.providers.identifiers;

import com.ibm.research.drl.dpt.managers.IMEIManager;
import com.ibm.research.drl.dpt.providers.ProviderType;

import java.util.Arrays;
import java.util.Collection;

public class IMEIIdentifier extends AbstractIdentifier implements LuhnBasedIdentifier {
    private final static String[] appropriateNames = new String[]{"IMEI"};
    private final static IMEIManager imeiManager = IMEIManager.getInstance();

    @Override
    protected Collection<String> getAppropriateNames() {
        return Arrays.asList(appropriateNames);
    }

    @Override
    public ProviderType getType() {
        return ProviderType.IMEI;
    }

    @Override
    public boolean isOfThisType(String data) {
        int dataLength = data.length();

        if (dataLength < 15 || dataLength > 16) {
            return false;
        }

        for (int i = 0; i < dataLength; i++) {
            if (!Character.isDigit(data.charAt(i))) {
                return false;
            }
        }

        String tac = data.substring(0, 8);
        if (!imeiManager.isValidKey(tac)) {
            return false;
        }

        return checkLastDigit(data);
    }

    @Override
    public String getDescription() {
        return "IMEI identification";
    }

    @Override
    public int getMinimumCharacterRequirements() {
        return CharacterRequirements.DIGIT;
    }

    @Override
    public int getMinimumLength() {
        return imeiManager.getMinimumLength();
    }

    @Override
    public int getMaximumLength() {
        return imeiManager.getMaximumLength();
    }
}
