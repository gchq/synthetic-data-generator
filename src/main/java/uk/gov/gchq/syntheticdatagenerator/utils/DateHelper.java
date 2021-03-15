/*
 * Copyright 2018-2021 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.gchq.syntheticdatagenerator.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

public final class DateHelper {
    private static final ThreadLocal<GregorianCalendar> GREGORIAN_CALENDAR = ThreadLocal.withInitial(GregorianCalendar::new);
    private static final int MIN_BIRTH_YEAR = 1900;
    private static final int BIRTH_YEAR_RANGE = 100;
    private static final int MIN_HIRE_AGE = 20;
    private static final int HIRE_YEAR_RANGE = 40;

    private DateHelper() {
    }

    public static String generateDateOfBirth(final Random random) {
        GregorianCalendar localCalendar = GREGORIAN_CALENDAR.get();
        int year = MIN_BIRTH_YEAR + random.nextInt(BIRTH_YEAR_RANGE);
        localCalendar.set(Calendar.YEAR, year);
        int dayOfYear = random.nextInt(localCalendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        localCalendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
        GREGORIAN_CALENDAR.remove();
        return localCalendar.get(Calendar.DAY_OF_MONTH) + "/" + (localCalendar.get(Calendar.MONTH) + 1) + "/" + year;
    }

    public static String generateHireDate(final String dateOfBirthStr, final Random random) {
        String birthYearStr = dateOfBirthStr.split("\\/")[2];
        String hireDateStr = dateOfBirthStr.substring(0, dateOfBirthStr.length() - 4);

        int birthYear = Integer.parseInt(birthYearStr);
        int hireYear = birthYear + MIN_HIRE_AGE + random.nextInt(HIRE_YEAR_RANGE);

        hireDateStr = hireDateStr + hireYear;

        return hireDateStr;

    }
}
