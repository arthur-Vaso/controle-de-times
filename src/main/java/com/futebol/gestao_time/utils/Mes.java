package com.futebol.gestao_time.utils;

public enum Mes {
    JANEIRO(1), FEVEREIRO(2), MARCO(3), ABRIL(4), MAIO(5), JUNHO(6),
    JULHO(7), AGOSTO(8), SETEMBRO(9), OUTUBRO(10), NOVEMBRO(11), DEZEMBRO(12);

    private final int numero;

    Mes(int numero) {
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

    public static Mes fromNumero(int numero) {
        for (Mes mes : Mes.values()) {
            if (mes.getNumero() == numero) {
                return mes;
            }
        }
        throw new IllegalArgumentException("Número inválido: " + numero);
    }
}
