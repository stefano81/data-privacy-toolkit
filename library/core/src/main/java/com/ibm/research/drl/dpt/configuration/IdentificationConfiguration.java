/*******************************************************************
* IBM Confidential                                                *
*                                                                 *
* Copyright IBM Corp. 2015                                        *
*                                                                 *
* The source code for this program is not published or otherwise  *
* divested of its trade secrets, irrespective of what has         *
* been deposited with the U.S. Copyright Office.                  *
*******************************************************************/
package com.ibm.research.drl.prima.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.Map;

public class IdentificationConfiguration {
    /**
     * The constant DEFAULT.
     */
    public static final IdentificationConfiguration DEFAULT = new IdentificationConfiguration(
            1,
            .5,
            false,
            IdentificationStrategy.FREQUENCY_BASED,
            Collections.emptyMap(),
            Collections.emptyMap()
    );

    private final double defaultFrequencyThreshold;
    private final int defaultPriority;
    private final boolean considerEmptyForFrequency;
    
    private final IdentificationStrategy identificationStrategy;
    
    private final Map<String, Integer> priorities;
    private final Map<String, Double> frequencyThresholds;


    @JsonCreator
    public IdentificationConfiguration(
            @JsonProperty("defaultPriority") int defaultPriority,
            @JsonProperty("defaultFrequencyThreshold") double defaultFrequencyThreshold,
            @JsonProperty("considerEmptyForFrequency") boolean considerEmptyForFrequency,
            @JsonProperty("identificationStrategy") IdentificationStrategy identificationStrategy,
            @JsonProperty("priorities") Map<String, Integer> priorities,
            @JsonProperty("frequencyThresholds") Map<String, Double> frequencyThresholds

    ) {
        this.defaultPriority = defaultPriority;
        this.defaultFrequencyThreshold = defaultFrequencyThreshold;
        this.considerEmptyForFrequency = considerEmptyForFrequency;
        
        this.identificationStrategy = identificationStrategy;
        this.priorities = priorities;
        this.frequencyThresholds =  frequencyThresholds;
    }

    public IdentificationStrategy getIdentificationStrategy() {
        return identificationStrategy;
    }

    /**
     * Gets confidence threshold for type.
     *
     * @param typeName the type name
     * @return the confidence threshold for type
     */
    public synchronized double getFrequencyThresholdForType(String typeName) {
        return this.frequencyThresholds.getOrDefault(typeName, defaultFrequencyThreshold);
    }

    /**
     * Gets priority for type.
     *
     * @param typeName the type name
     * @return the priority for type
     */
    public synchronized int getPriorityForType(String typeName) {
        return this.priorities.getOrDefault(typeName, this.defaultPriority);
    }

    /**
     * Gets consider empty for confidence.
     *
     * @return the consider empty for confidence
     */
    public boolean getConsiderEmptyForFrequency() {
        return considerEmptyForFrequency;
    }
}
