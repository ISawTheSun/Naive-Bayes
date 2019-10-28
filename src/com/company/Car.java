package com.company;

import java.util.List;

public class Car {
    List<String> atrr;
    String atrybutDecyzyjny;

    public Car(List<String> atrr, String atrybutDecyzyjny) {
        this.atrr = atrr;
        this.atrybutDecyzyjny = atrybutDecyzyjny;
    }

    @Override
    public String toString() {
        return "Car{" +
                "atrr=" +
                ", atrybutDecyzyjny='" + atrybutDecyzyjny + '\'' +
                '}';
    }
}
