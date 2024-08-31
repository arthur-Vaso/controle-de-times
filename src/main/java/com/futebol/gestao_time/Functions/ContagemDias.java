package com.futebol.gestao_time.Functions;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;

public class ContagemDias {
    public static int contarSábadosNoAno(int ano) {
        Year anoRef = Year.of(ano);
        LocalDate primeiroDiaDoAno = anoRef.atDay(1);
        LocalDate ultimoDiaDoAno = anoRef.atDay(anoRef.length());

        int contagem = 0;
        LocalDate dataAtual = primeiroDiaDoAno;

        while (!dataAtual.isAfter(ultimoDiaDoAno)) {
            if (dataAtual.getDayOfWeek() == DayOfWeek.SATURDAY) {
                contagem++;
            }
            dataAtual = dataAtual.plusDays(1);
        }

        return contagem;
    }

    public static int contarSabadosNoMes(int ano, int mes) {
        YearMonth anoMes = YearMonth.of(ano, mes);
        LocalDate primeiroDiaDoMes = anoMes.atDay(1);
        LocalDate ultimoDiaDoMes = anoMes.atEndOfMonth();

        int contagem = 0;
        LocalDate dataAtual = primeiroDiaDoMes;

        while (!dataAtual.isAfter(ultimoDiaDoMes)) {
            if (dataAtual.getDayOfWeek() == DayOfWeek.SATURDAY) {
                contagem++;
            }
            dataAtual = dataAtual.plusDays(1);
        }

        return contagem;
    }
}
