package org.c4sg.util;

import java.util.HashMap;
import java.util.Locale;

/***
 *  Italy:          ITA -> IT,
 *  Netherlands:    NLD -> NL
 *  etc.
 */

public class CountryCodeConverterUtil {

    public static String convertToIso2(String iso3code) {

        String[] countries = Locale.getISOCountries();

        HashMap<String, Locale> localeMap = new HashMap<>(countries.length);
        for (String country : countries) {
            Locale locale = new Locale("", country);
            localeMap.put(locale.getISO3Country().toUpperCase(), locale);
        }

        return localeMap.get(iso3code).getCountry();
    }
}
