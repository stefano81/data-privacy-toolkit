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

import com.ibm.research.drl.dpt.managers.Manager;

public abstract class AbstractManagerBasedIdentifier extends AbstractIdentifier {

    /**
     * Gets manager.
     *
     * @return the manager
     */
    protected abstract Manager getManager();

    @Override
    public boolean isOfThisType(String identifier) {
        if (this.getMinimumLength() > 0 && identifier.length() < this.getMinimumLength()) {
            return false;
        }

        if (this.getMaximumLength() > 0 && identifier.length() > this.getMaximumLength()) {
            return false;
        }

        return getManager().isValidKey(identifier);
    }

    @Override
    public int getMaximumLength() {
        return getManager().getMaximumLength();
    }

    @Override
    public int getMinimumLength() {
        return getManager().getMinimumLength();
    }
}
