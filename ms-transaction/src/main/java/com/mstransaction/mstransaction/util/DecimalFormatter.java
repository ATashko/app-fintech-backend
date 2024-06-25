package com.mstransaction.mstransaction.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class DecimalFormatter {


    public static String formatDecimal(double number, char decimalSeparator, char groupingSeparator) {
        // Configurar los símbolos de formato

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(decimalSeparator);
        symbols.setGroupingSeparator(groupingSeparator);

        // Crear el patrón de formato
        String pattern = "#,##0.00"; // Puedes ajustar el patrón según sea necesario

        // Crear el formateador con el patrón y los símbolos
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);

        // Formatear el número
        return decimalFormat.format(number);
    }

    public static void main(String[] args) {
        // Ejemplos de uso
        double number = 1234567.89;

        // Formatear con diferentes separadores
        String formatted1 = formatDecimal(number, '.', '.'); // Separador decimal: punto, Separador de miles: coma


        // Mostrar los resultados
        System.out.println("Formatted (..): " + formatted1);

    }


};

