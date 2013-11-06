package org.rapidpm.demo.jaxenter.blog0006.tableviewdemo;

import java.util.Objects;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
* Created by Sven Ruppert on 01.11.13.
*/
public class CurrencyValue implements Comparable<CurrencyValue> {



    private final DoubleProperty umrechnungsfaktor = new SimpleDoubleProperty();

    private final DoubleProperty euro = new SimpleDoubleProperty();
    private final DoubleProperty dollar = new SimpleDoubleProperty();
    private final DoubleProperty percentage = new SimpleDoubleProperty();

    @Override
    public int hashCode() {
        return Objects.hash(euro, dollar, percentage);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final CurrencyValue other = (CurrencyValue) obj;
        return Objects.equals(this.euro, other.euro) && Objects.equals(this.dollar, other.dollar) && Objects.equals(this.percentage, other.percentage);
    }

    public CurrencyValue() {


    }


    public CurrencyValue changeUmrechnungsFaktor(Double urf){ setUmrechnungsfaktor(urf); return this; }
    public CurrencyValue addEUR(Double eur){ setEuro(eur); return this; }
    public CurrencyValue addUSD(Double usd){ setDollar(usd); return this; }
    public CurrencyValue addPerc(Double perc){ setPercentage(perc); return this; }


    public double getUmrechnungsfaktor() {
        return umrechnungsfaktor.get();
    }

    public DoubleProperty umrechnungsfaktorProperty() {
        return umrechnungsfaktor;
    }

    public void setUmrechnungsfaktor(double umrechnungsfaktor) {
        this.umrechnungsfaktor.set(umrechnungsfaktor);
    }

    public double getEuro() {
        return euro.get();
    }

    public DoubleProperty euroProperty() {
        return euro;
    }

    public void setEuro(double euro) {
        this.euro.set(euro);
    }

    public double getDollar() {
        return dollar.get();
    }

    public DoubleProperty dollarProperty() {
        return dollar;
    }

    public void setDollar(double dollar) {
        this.dollar.set(dollar);
    }

    public double getPercentage() {
        return percentage.get();
    }

    public DoubleProperty percentageProperty() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage.set(percentage);
    }

    @Override
    public int compareTo(CurrencyValue o) {
        return euro.getValue().compareTo(o.euroProperty().getValue());
    }
}
