/*******************************************************************
 *                                                                 *
 * Copyright IBM Corp. 2018                                        *
 *                                                                 *
 *******************************************************************/
package com.ibm.research.drl.dpt.spark.risk;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibm.research.drl.dpt.configuration.DataTypeFormat;
import com.ibm.research.drl.dpt.datasets.CSVDatasetOptions;
import com.ibm.research.drl.dpt.datasets.DatasetOptions;
import com.ibm.research.drl.dpt.exceptions.MisconfigurationException;

import java.util.List;

public class DatasetLinkingOptions {
    
    final String target;
    final DataTypeFormat targetFormat;
    final DatasetOptions targetDatasetOptions;
    final boolean targetAggregated;
    final String targetCountColumn;
    
    final DataTypeFormat inputFormat;
    final DatasetOptions inputDatasetOptions;
    final List<BasicColumnInformation> columnInformation;
    
    public String getTarget() {
        return target;
    }
    
    public DataTypeFormat getTargetFormat() {
        return targetFormat;
    }

    public DatasetOptions getTargetDatasetOptions() {
        return targetDatasetOptions;
    }

    public DataTypeFormat getInputFormat() {
        return inputFormat;
    }

    public DatasetOptions getInputDatasetOptions() {
        return inputDatasetOptions;
    }

    public List<BasicColumnInformation> getColumnInformation() {
        return columnInformation;
    }

    public boolean isTargetAggregated() {
        return targetAggregated;
    }

    public String getTargetCountColumn() {
        return targetCountColumn;
    }

    @JsonCreator
    public DatasetLinkingOptions(
            @JsonProperty(value = "target", required = true) String target,
            @JsonProperty(value = "targetFormat", required = true) String targetFormat,
            @JsonProperty(value = "targetAggregated", required = true) boolean targetIsAggregated,
            @JsonProperty(value = "targetCountColumn", defaultValue = "") String targetCountColumn,
            @JsonProperty(value = "inputFormat", required = true) String inputFormat,
            @JsonProperty(value = "delimiter", defaultValue = ",") char delimiter,
            @JsonProperty(value = "quoteChar", defaultValue = "\"") char quoteChar,
            @JsonProperty(value = "hasHeader", defaultValue = "false") boolean hasHeader,
            @JsonProperty(value = "trimFields") boolean trimFields,
            @JsonProperty(value = "columnInformation") List<BasicColumnInformation> columnInformation
            ) {
        this.target = target;
        this.targetFormat = DataTypeFormat.valueOf(targetFormat);
        this.targetAggregated = targetIsAggregated;
        
        if (!this.targetAggregated) {
            this.targetCountColumn = null;
        }
        else {
            this.targetCountColumn = targetCountColumn;
            
            if (this.targetCountColumn == null || this.targetCountColumn.isEmpty()) {
                throw new MisconfigurationException("target is aggregated but no count column is specified");
            }
        }
        
        if (this.targetFormat == DataTypeFormat.CSV) {
            this.targetDatasetOptions = new CSVDatasetOptions(hasHeader, delimiter, quoteChar, trimFields);
        }
        else {
            this.targetDatasetOptions = null;
        }
        
        this.inputFormat = DataTypeFormat.valueOf(inputFormat);

        if (this.inputFormat == DataTypeFormat.CSV) {
            this.inputDatasetOptions = new CSVDatasetOptions(hasHeader, delimiter, quoteChar, trimFields);
        } else {
            this.inputDatasetOptions = null;
        }
        
        this.columnInformation = columnInformation;
    }
}
