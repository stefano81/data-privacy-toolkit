/*******************************************************************
 *                                                                 *
 * Copyright IBM Corp. 2015                                        *
 *                                                                 *
 *******************************************************************/
package com.ibm.research.drl.dpt.providers.masking;

import com.ibm.research.drl.dpt.configuration.DefaultMaskingConfiguration;
import com.ibm.research.drl.dpt.configuration.MaskingConfiguration;
import com.ibm.research.drl.dpt.managers.VINManager;
import com.ibm.research.drl.dpt.providers.identifiers.VINIdentifier;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.*;

public class VINMaskingProviderTest {

    private final VINIdentifier vinIdentifier = new VINIdentifier();

    @Test
    public void testDefaultMask() {
        MaskingProvider vinMaskingProvider = new VINMaskingProvider();

        String vin = "1B312345678901234";
        assertTrue(vinIdentifier.isOfThisType(vin));
        String maskedResult = vinMaskingProvider.mask(vin);
        //default is to preserve WMI and VDS
        assertTrue(vinIdentifier.isOfThisType(maskedResult));
        assertTrue(maskedResult.startsWith("1B3"));
        assertNotEquals(maskedResult, vin);
    }

    @Test
    public void testWMIFlagOff() {
        MaskingConfiguration configuration = new DefaultMaskingConfiguration();
        configuration.setValue("vin.wmi.preserve", false);
        configuration.setValue("vin.vds.preserve", true);
        MaskingProvider vinMaskingProvider = new VINMaskingProvider(new SecureRandom(), configuration);
        VINManager vinManager = new VINManager();

        String vin = "1B312345678901234";
        String maskedResult = vinMaskingProvider.mask(vin);

        assertTrue(vinIdentifier.isOfThisType(maskedResult));
        assertNotEquals(maskedResult, vin);

        String randomWMI = maskedResult.substring(0, 3);
        assertNotEquals("1B3", randomWMI);
        assertTrue(vinManager.isValidWMI(randomWMI));
    }

    @Test
    public void testVDSFlagOff() {
        MaskingConfiguration configuration = new DefaultMaskingConfiguration();
        configuration.setValue("vin.wmi.preserve", true);
        configuration.setValue("vin.vds.preserve", false);
        MaskingProvider vinMaskingProvider = new VINMaskingProvider(new SecureRandom(), configuration);
        VINManager vinManager = new VINManager();

        String vin = "1B312345678901234";
        String maskedResult = vinMaskingProvider.mask(vin);

        assertTrue(vinIdentifier.isOfThisType(maskedResult));
        assertNotEquals(maskedResult, vin);

        String randomVDS = maskedResult.substring(3, 9);
        String originalVDS = vin.substring(3, 9);
        assertNotEquals(originalVDS, randomVDS);
    }

    @Test
    @Disabled
    public void testPerformance() {
        int N = 1000000;
        DefaultMaskingConfiguration defaultConfiguration = new DefaultMaskingConfiguration("default");
        DefaultMaskingConfiguration nopreserveConfiguration = new DefaultMaskingConfiguration("no info preserve");
        nopreserveConfiguration.setValue("vin.wmi.preserve", false);
        nopreserveConfiguration.setValue("vin.vds.preserve", false);

        DefaultMaskingConfiguration[] configurations = new DefaultMaskingConfiguration[]{
                defaultConfiguration, nopreserveConfiguration
        };

        String[] originalValues = new String[]{
                "1B312345678901234"
        };

        for (DefaultMaskingConfiguration maskingConfiguration : configurations) {
            VINMaskingProvider maskingProvider = new VINMaskingProvider(maskingConfiguration);

            for (String originalValue : originalValues) {
                long startMillis = System.currentTimeMillis();

                for (int i = 0; i < N; i++) {
                    String maskedValue = maskingProvider.mask(originalValue);
                }

                long diff = System.currentTimeMillis() - startMillis;
                System.out.println(String.format("%s: %s: %d operations took %d milliseconds (%f per op)",
                        maskingConfiguration.getName(), originalValue, N, diff, (double) diff / N));
            }
        }
    }

    @Test
    public void testMaskInvalidValue() throws Exception {
        VINMaskingProvider vinMaskingProvider = new VINMaskingProvider();

        String invalidValue = "junk";
        String maskedResult = vinMaskingProvider.mask(invalidValue);

        assertNotEquals(maskedResult, invalidValue);
        assertTrue(vinIdentifier.isOfThisType(maskedResult));

    }
}

