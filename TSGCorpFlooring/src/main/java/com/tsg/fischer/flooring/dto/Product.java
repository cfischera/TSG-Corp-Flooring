package com.tsg.fischer.flooring.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private String type;
    private BigDecimal costPerSquareFoot, laborCostPerSquareFoot;

    public Product() {
        this.type = null;
        this.costPerSquareFoot = null;
        this.laborCostPerSquareFoot = null;
    }

    public Product(String type, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot) {
        this.type = type;
        this.costPerSquareFoot = costPerSquareFoot;
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return this.type.equals(product.type) &&
                this.costPerSquareFoot.equals(product.costPerSquareFoot) &&
                this.laborCostPerSquareFoot.equals(product.laborCostPerSquareFoot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, costPerSquareFoot, laborCostPerSquareFoot);
    }

    public String toString() {
        return ""+this.getType()+": costs "+getCostPerSquareFoot()+" per sq. ft."+
                " and labor costs "+getLaborCostPerSquareFoot()+" per sq. ft.";
    }
}
