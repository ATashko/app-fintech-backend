package com.mstransaction.mstransaction.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CurrencyFormatter {

    private static final Map<String, Locale> currencyLocaleMap = new HashMap<>();

    static {
        // Initialize the map with currency codes and their corresponding locales
        currencyLocaleMap.put("AOA", new Locale("pt", "AO"));
        currencyLocaleMap.put("AUD", new Locale("en", "AU"));
        currencyLocaleMap.put("GBP", Locale.UK);
        currencyLocaleMap.put("EUR", Locale.FRANCE);
        currencyLocaleMap.put("KMF", new Locale("ar", "KM"));
        currencyLocaleMap.put("USD", Locale.US);
        currencyLocaleMap.put("KYD", new Locale("en", "KY"));
        currencyLocaleMap.put("ILS", new Locale("he", "IL"));
        currencyLocaleMap.put("XOF", new Locale("fr", "SN"));
        currencyLocaleMap.put("SSP", new Locale("en", "SS"));
        currencyLocaleMap.put("NZD", new Locale("en", "NZ"));
        currencyLocaleMap.put("EGP", new Locale("ar", "EG"));
        currencyLocaleMap.put("LRD", new Locale("en", "LR"));
        currencyLocaleMap.put("SBD", new Locale("en", "SB"));
        currencyLocaleMap.put("UZS", new Locale("uz", "UZ"));
        currencyLocaleMap.put("ZWL", new Locale("en", "ZW"));
        currencyLocaleMap.put("XAF", new Locale("fr", "CM"));
        currencyLocaleMap.put("AED", new Locale("ar", "AE"));
        currencyLocaleMap.put("ZMW", new Locale("en", "ZM"));
        currencyLocaleMap.put("KHR", new Locale("km", "KH"));
        currencyLocaleMap.put("VND", new Locale("vi", "VN"));
        currencyLocaleMap.put("XPF", new Locale("fr", "PF"));
        currencyLocaleMap.put("ZAR", new Locale("en", "ZA"));
        currencyLocaleMap.put("GYD", new Locale("en", "GY"));
        currencyLocaleMap.put("MZN", new Locale("pt", "MZ"));
        currencyLocaleMap.put("TMT", new Locale("tk", "TM"));
        currencyLocaleMap.put("LAK", new Locale("lo", "LA"));
        currencyLocaleMap.put("HUF", new Locale("hu", "HU"));
        currencyLocaleMap.put("ARS", new Locale("es", "AR"));
        currencyLocaleMap.put("TRY", new Locale("tr", "TR"));
        currencyLocaleMap.put("HTG", new Locale("fr", "HT"));
        currencyLocaleMap.put("SYP", new Locale("ar", "SY"));
        currencyLocaleMap.put("SEK", new Locale("sv", "SE"));
        currencyLocaleMap.put("BZD", new Locale("en", "BZ"));
        currencyLocaleMap.put("CLP", new Locale("es", "CL"));
        currencyLocaleMap.put("RSD", new Locale("sr", "RS"));
        currencyLocaleMap.put("DZD", new Locale("ar", "DZ"));
        currencyLocaleMap.put("BOB", new Locale("es", "BO"));
        currencyLocaleMap.put("CKD", new Locale("en", "CK"));
        currencyLocaleMap.put("VUV", new Locale("bi", "VU"));
        currencyLocaleMap.put("NAD", new Locale("en", "NA"));
        currencyLocaleMap.put("YER", new Locale("ar", "YE"));
        currencyLocaleMap.put("MOP", new Locale("zh", "MO"));
        currencyLocaleMap.put("MUR", new Locale("en", "MU"));
        currencyLocaleMap.put("AWG", new Locale("nl", "AW"));
        currencyLocaleMap.put("KGS", new Locale("ky", "KG"));
        currencyLocaleMap.put("GTQ", new Locale("es", "GT"));
        currencyLocaleMap.put("TOP", new Locale("to", "TO"));
        currencyLocaleMap.put("GNF", new Locale("fr", "GN"));
        currencyLocaleMap.put("TTD", new Locale("en", "TT"));
        currencyLocaleMap.put("JMD", new Locale("en", "JM"));
        currencyLocaleMap.put("BSD", new Locale("en", "BS"));
        currencyLocaleMap.put("RUB", new Locale("ru", "RU"));
        currencyLocaleMap.put("LSL", new Locale("en", "LS"));
        currencyLocaleMap.put("MMK", new Locale("my", "MM"));
        currencyLocaleMap.put("BTN", new Locale("dz", "BT"));
        currencyLocaleMap.put("MRU", new Locale("ar", "MR"));
        currencyLocaleMap.put("CNY", Locale.CHINA);
        currencyLocaleMap.put("PYG", new Locale("es", "PY"));
        currencyLocaleMap.put("DKK", new Locale("da", "DK"));
        currencyLocaleMap.put("NOK", new Locale("no", "NO"));
        currencyLocaleMap.put("GHS", new Locale("en", "GH"));
        currencyLocaleMap.put("MWK", new Locale("en", "MW"));
        currencyLocaleMap.put("PGK", new Locale("en", "PG"));
        currencyLocaleMap.put("SZL", new Locale("en", "SZ"));
        currencyLocaleMap.put("JOD", new Locale("ar", "JO"));
        currencyLocaleMap.put("CAD", Locale.CANADA);
        currencyLocaleMap.put("MAD", new Locale("ar", "MA"));
        currencyLocaleMap.put("MNT", new Locale("mn", "MN"));
        currencyLocaleMap.put("ISK", new Locale("is", "IS"));
        currencyLocaleMap.put("THB", new Locale("th", "TH"));
        currencyLocaleMap.put("BGN", new Locale("bg", "BG"));
        currencyLocaleMap.put("NIO", new Locale("es", "NI"));
        currencyLocaleMap.put("GIP", new Locale("en", "GI"));
        currencyLocaleMap.put("XCD", new Locale("en", "AG"));
        currencyLocaleMap.put("COP", new Locale("es", "CO"));
        currencyLocaleMap.put("BRL", new Locale("pt", "BR"));
        currencyLocaleMap.put("NPR", new Locale("ne", "NP"));
        currencyLocaleMap.put("KPW", new Locale("ko", "KP"));
        currencyLocaleMap.put("ANG", new Locale("nl", "AN"));
        currencyLocaleMap.put("SRD", new Locale("nl", "SR"));
        currencyLocaleMap.put("KWD", new Locale("ar", "KW"));
        currencyLocaleMap.put("SOS", new Locale("so", "SO"));
        currencyLocaleMap.put("CDF", new Locale("fr", "CD"));
        currencyLocaleMap.put("ALL", new Locale("sq", "AL"));
        currencyLocaleMap.put("HNL", new Locale("es", "HN"));
        currencyLocaleMap.put("SDG", new Locale("ar", "SD"));
        currencyLocaleMap.put("HKD", new Locale("zh", "HK"));
        currencyLocaleMap.put("LKR", new Locale("si", "LK"));
        currencyLocaleMap.put("BDT", new Locale("bn", "BD"));
        currencyLocaleMap.put("MGA", new Locale("mg", "MG"));
        currencyLocaleMap.put("BHD", new Locale("ar", "BH"));
        currencyLocaleMap.put("MKD", new Locale("mk", "MK"));
        currencyLocaleMap.put("UAH", new Locale("uk", "UA"));
        currencyLocaleMap.put("ERN", new Locale("ti", "ER"));
        currencyLocaleMap.put("AZN", new Locale("az", "AZ"));
        currencyLocaleMap.put("SLL", new Locale("en", "SL"));
        currencyLocaleMap.put("DJF", new Locale("fr", "DJ"));
        currencyLocaleMap.put("UYU", new Locale("es", "UY"));
        currencyLocaleMap.put("GMD", new Locale("en", "GM"));
        currencyLocaleMap.put("MDL", new Locale("ro", "MD"));
        currencyLocaleMap.put("AFN", new Locale("ps", "AF"));
        currencyLocaleMap.put("BND", new Locale("ms", "BN"));
        currencyLocaleMap.put("FJD", new Locale("en", "FJ"));
        currencyLocaleMap.put("MXN", new Locale("es", "MX"));
        currencyLocaleMap.put("CHF", new Locale("de", "CH"));
        currencyLocaleMap.put("RWF", new Locale("rw", "RW"));
        currencyLocaleMap.put("BMD", new Locale("en", "BM"));
        currencyLocaleMap.put("TWD", new Locale("zh", "TW"));
        currencyLocaleMap.put("JPY", Locale.JAPAN);
        currencyLocaleMap.put("QAR", new Locale("ar", "QA"));
        currencyLocaleMap.put("BWP", new Locale("en", "BW"));
        currencyLocaleMap.put("LBP", new Locale("ar", "LB"));
        currencyLocaleMap.put("LYD", new Locale("ar", "LY"));
        currencyLocaleMap.put("MYR", new Locale("ms", "MY"));
        currencyLocaleMap.put("CRC", new Locale("es", "CR"));
        currencyLocaleMap.put("BAM", new Locale("bs", "BA"));
        currencyLocaleMap.put("BIF", new Locale("fr", "BI"));
        currencyLocaleMap.put("CVE", new Locale("pt", "CV"));
        currencyLocaleMap.put("OMR", new Locale("ar", "OM"));
        currencyLocaleMap.put("BYN", new Locale("be", "BY"));
        currencyLocaleMap.put("BBD", new Locale("en", "BB"));
        currencyLocaleMap.put("TZS", new Locale("sw", "TZ"));
        currencyLocaleMap.put("WST", new Locale("sm", "WS"));
        currencyLocaleMap.put("ETB", new Locale("am", "ET"));
        currencyLocaleMap.put("INR", new Locale("hi", "IN"));
        currencyLocaleMap.put("MVR", new Locale("dv", "MV"));
        currencyLocaleMap.put("SCR", new Locale("fr", "SC"));
        currencyLocaleMap.put("UGX", new Locale("en", "UG"));
        currencyLocaleMap.put("FKP", new Locale("en", "FK"));
        currencyLocaleMap.put("STN", new Locale("pt", "ST"));
        currencyLocaleMap.put("IQD", new Locale("ar", "IQ"));
        currencyLocaleMap.put("PLN", new Locale("pl", "PL"));
        currencyLocaleMap.put("TJS", new Locale("tg", "TJ"));
        currencyLocaleMap.put("KES", new Locale("sw", "KE"));
        currencyLocaleMap.put("KZT", new Locale("kk", "KZ"));
        currencyLocaleMap.put("PAB", new Locale("es", "PA"));
        currencyLocaleMap.put("SGD", new Locale("en", "SG"));
        currencyLocaleMap.put("NGN", new Locale("en", "NG"));
        currencyLocaleMap.put("RON", new Locale("ro", "RO"));
        currencyLocaleMap.put("CZK", new Locale("cs", "CZ"));
        currencyLocaleMap.put("AMD", new Locale("hy", "AM"));
        currencyLocaleMap.put("VES", new Locale("es", "VE"));
        currencyLocaleMap.put("PHP", new Locale("fil", "PH"));
        currencyLocaleMap.put("PEN", new Locale("es", "PE"));
        currencyLocaleMap.put("KRW", new Locale("ko", "KR"));
        currencyLocaleMap.put("DOP", new Locale("es", "DO"));
        currencyLocaleMap.put("IRR", new Locale("fa", "IR"));
        currencyLocaleMap.put("CUC", new Locale("es", "CU"));
        currencyLocaleMap.put("SAR", new Locale("ar", "SA"));
        currencyLocaleMap.put("GEL", new Locale("ka", "GE"));
        currencyLocaleMap.put("PKR", new Locale("ur", "PK"));
        currencyLocaleMap.put("SHP", new Locale("en", "SH"));
        currencyLocaleMap.put("TND", new Locale("ar", "TN"));
        currencyLocaleMap.put("IDR", new Locale("id", "ID"));
    }

//    public static String formatCurrency(String currencyCode, double amount) {
//        Locale locale = currencyLocaleMap.get(currencyCode);
//        if (locale == null) {
//            throw new IllegalArgumentException("Unsupported currency code: " + currencyCode);
//        }
//
//        // Get the currency instance
//        Currency currency = Currency.getInstance(currencyCode);
//
//        // Configure the decimal format symbols
//        DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
//        symbols.setGroupingSeparator('.');
//        symbols.setDecimalSeparator(',');
//
//        String pattern = "¤ #,##0.00";
//
//        // Create and configure the decimal format
//        DecimalFormat currencyFormatter = new DecimalFormat(pattern, symbols);
//        currencyFormatter.setCurrency(currency);
//
//        return currencyFormatter.format(amount);
//    }

//    public static void main(String[] args) {
//
//        Locale locale = new Locale("en", "ES");
//        NumberFormat numberFormat = DecimalFormat.getInstance(locale);
//        DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
//        String pattern = "¤ #,##0.00";
//        decimalFormat.applyPattern(pattern);
//
//        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
//        currencyFormat.setCurrency(Currency.getInstance("EUR"));
//
//        // Ejemplos de formateo
//        double amount = 1234567.89;
//
//        String formattedCurrency = currencyFormat.format(amount);
//        System.out.println("Formatted Currency: " + formattedCurrency); // Imprimirá 1.234.567,89 €
//
//    }

    public static void formatNumber(BigDecimal number) {

        // Configurar el formato de número para separador de miles punto y separador decimal coma
        Locale spanishLocale = new Locale("es", "ES");
        NumberFormat numberFormat = DecimalFormat.getInstance(spanishLocale);
        DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
        decimalFormat.applyPattern("#,##0.00");

        // Formatear el BigDecimal con separador de miles punto y separador decimal coma
        String formattedNumber = decimalFormat.format(number);

        // Imprimir el resultado
        System.out.println("Formatted BigDecimal: " + formattedNumber); // Imprimirá 1.234.567,89
    }



}




