package com.meuprojeto.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateHelper {
    private static final DateTimeFormatter HTML5_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static String obterDataFutura(int diasAFrente) {
        LocalDate dataFutura = LocalDate.now().plusDays(diasAFrente);
        return dataFutura.format(HTML5_FORMAT);
    }

    public static String obterDataPassada(int diasAtras) {
        LocalDate dataPassada = LocalDate.now().minusDays(diasAtras);
        return dataPassada.format(HTML5_FORMAT);
    }
}