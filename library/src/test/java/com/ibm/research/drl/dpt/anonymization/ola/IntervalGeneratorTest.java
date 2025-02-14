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
package com.ibm.research.drl.dpt.anonymization.ola;

import com.ibm.research.drl.dpt.anonymization.hierarchies.MaterializedHierarchy;
import com.ibm.research.drl.dpt.datasets.IPVDataset;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class IntervalGeneratorTest {

    @Test
    public void testGenerator() throws Exception {
        try (InputStream input = IntervalGeneratorTest.class.getResourceAsStream("/100.csv")) {
            IPVDataset original = IPVDataset.load(input, false, ',', '"', false);

            MaterializedHierarchy hierarchy = IntervalGenerator.generateHierarchy(original, 0);

            assertNotNull(hierarchy);
            assertThat(hierarchy.getHeight(), is(7));
            assertThat(hierarchy.getTopTerm(), is("1919-1998"));
        }
    }
}

