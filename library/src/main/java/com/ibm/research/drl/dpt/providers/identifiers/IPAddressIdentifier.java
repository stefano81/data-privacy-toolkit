/*******************************************************************
 *                                                                 *
 * Copyright IBM Corp. 2021                                        *
 *                                                                 *
 *******************************************************************/
package com.ibm.research.drl.dpt.providers.identifiers;

import com.ibm.research.drl.dpt.providers.ProviderType;
import org.apache.commons.validator.routines.InetAddressValidator;

import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Pattern;

public class IPAddressIdentifier extends AbstractIdentifier {
    private static final String ipv4AddressPattern = "^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$";
    private static final Pattern ipv4matcher = Pattern.compile(ipv4AddressPattern);

    private static final String[] appropriateNames = {"IP Address", "IPAddress"};

    private static final InetAddressValidator INET_ADDRESS_VALIDATOR = InetAddressValidator.getInstance();

    @Override
    public ProviderType getType() {
        return ProviderType.IP_ADDRESS;
    }

    @Override
    public boolean isOfThisType(String data) {
        int matches = 0;

        for(int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            if (Character.isDigit(c) || c == '.' || c == ':') {
                matches++;
            }
        }

        if (matches == 0) {
            return false;
        }

        return isIPv4(data) || isIPv6(data);
    }

    @Override
    public String getDescription() {
        return "IP address identification. Supports both IPv4 and IPv6 addresses";
    }

    /**
     * Is i pv 4 boolean.
     *
     * @param data the data
     * @return the boolean
     */
    public boolean isIPv4(String data) {
        if (data.length() < 7 || data.length() > 15) {
            return false;
        }

        return ipv4matcher.matcher(data).matches();
    }

    /**
     * Is i pv 6 boolean.
     *
     * @param data the data
     * @return the boolean
     */
    public boolean isIPv6(String data) {
        if (data.length() > 45) {
            return false;
        }

        int idx = data.indexOf('%');

        if (idx > 0) {
            data = data.substring(0, idx);
        }

        return INET_ADDRESS_VALIDATOR.isValidInet6Address(data);
        //return ipv6matcher.matcher(data.toLowerCase()).matches();
    }

    @Override
    protected Collection<String> getAppropriateNames() {
        return Arrays.asList(appropriateNames);
    }

    @Override
    public int getMinimumCharacterRequirements() {
        return CharacterRequirements.DIGIT;
    }

    @Override
    public int getMinimumLength() {
        return 7;
    }

    @Override
    public int getMaximumLength() {
        return 39;
    }
}
