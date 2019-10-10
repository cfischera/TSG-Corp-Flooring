package com.tsg.fischer.flooring.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class StateTax {
    private String abbreviation, name;
    private BigDecimal taxRate;

    public StateTax() {
        this.abbreviation = null;
        this.name = null;
        this.taxRate = null;
    }

    public StateTax(String abbreviation, String name, BigDecimal taxRate) {
        this.abbreviation = abbreviation;
        this.name = name;
        this.taxRate = taxRate;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StateTax stateTax = (StateTax) o;
        return this.abbreviation.equals(stateTax.abbreviation) &&
                this.name.equals(stateTax.name) &&
                this.taxRate.equals(stateTax.taxRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(abbreviation, name, taxRate);
    }
}
