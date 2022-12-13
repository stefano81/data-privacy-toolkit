/*******************************************************************
 *                                                                 *
 * Copyright IBM Corp. 2021                                        *
 *                                                                 *
 *******************************************************************/
package com.ibm.research.drl.dpt.providers.identifiers;

import com.ibm.research.drl.dpt.providers.ProviderType;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocaleDateIdentifier extends AbstractIdentifier {
    private static final Logger logger = LogManager.getLogger(LocaleDateIdentifier.class);

    private final MonthIdentifier monthIdentifier = new MonthIdentifier();

    private final Pattern dmy = Pattern.compile("(\\d{1,2})\\s+(\\p{Alpha}{5,}|\\d{1,2}) (\\d{2,4})");
    private final Pattern dm = Pattern.compile("(\\d{1,2})\\s+(\\p{Alpha}{5,}|\\d{1,2})");

    @Override
    public ProviderType getType() {
        return ProviderType.DATETIME;
    }

    @Override
    public boolean isOfThisType(String data) {
        {
            Matcher dmyMatcher = dmy.matcher(data);

            if (dmyMatcher.matches()) {
                int day = Integer.parseInt(dmyMatcher.group(1));
                String month = dmyMatcher.group(2);
                String year = dmyMatcher.group(3);

                int numericMonth;
                if (monthIdentifier.isOfThisType(month)) {
                    return true;
                }
                try {
                    numericMonth = Integer.parseInt(month);
                } catch (NumberFormatException e) {
                    return false;
                }
                switch (numericMonth) {
                    case 2:
                        return 1 <= day && day <= 29;

                    case 11:
                    case 4:
                    case 6:
                    case 9:
                        return 1 <= day && day <= 30;

                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        return 1 <= day && day <= 31;
                }

                return false;
            }
        }

        {
            Matcher dmMatcher = dm.matcher(data);
            if (dmMatcher.matches()) {
                int day = Integer.parseInt(dmMatcher.group(1));
                String month = dmMatcher.group(2);

                return monthIdentifier.isOfThisType(month) &&
                        (1 <= day && day <= 31);
            }
        }

        return false;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public int getMinimumCharacterRequirements() {
        return 0;
    }

    @Override
    public int getMinimumLength() {
        return 0;
    }

    @Override
    public int getMaximumLength() {
        return 0;
    }
}
