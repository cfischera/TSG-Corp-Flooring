package com.tsg.fischer.flooring.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class Order {
    private int number;
    private String customerName, stateTerritory, productType;
    private BigDecimal taxRate, area, costPerSquareFoot, laborCostPerSquareFoot, materialCost,
                            laborCost, tax, total;

    public Order() {
        this.number = -1;
        this.customerName = null;
        this.stateTerritory = null;
        this.productType = null;
        this.taxRate = null;
        this.area = null;
        this.costPerSquareFoot = null;
        this.laborCostPerSquareFoot = null;
        this.materialCost = null;
        this.laborCost = null;
        this.tax = null;
        this.total = null;
    }

    public Order(int number, String customerName, String stateTerritory, BigDecimal taxRate, String productType,
                    BigDecimal area, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot,
                    BigDecimal materialCost, BigDecimal laborCost, BigDecimal tax, BigDecimal total) {
        this.number = number;
        this.customerName = customerName;
        this.stateTerritory = stateTerritory;
        this.taxRate = taxRate;
        this.productType = productType;
        this.area = area;
        this.costPerSquareFoot = costPerSquareFoot;
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
        this.materialCost = materialCost;
        this.laborCost = laborCost;
        this.tax = tax;
        this.total = total;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStateTerritory() {
        return stateTerritory;
    }

    public void setStateTerritory(String stateTerritory) {
        this.stateTerritory = stateTerritory;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
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

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return (this.number == order.number) &&
                this.customerName.equals(order.customerName) &&
                this.stateTerritory.equals(order.stateTerritory) &&
                this.taxRate.equals(order.taxRate) &&
                this.productType.equals(order.productType) &&
                this.area.equals(order.area) &&
                this.costPerSquareFoot.equals(order.costPerSquareFoot) &&
                this.laborCostPerSquareFoot.equals(order.laborCostPerSquareFoot) &&
                this.materialCost.equals(order.materialCost) &&
                this.laborCost.equals(order.laborCost) &&
                this.tax.equals(order.tax) &&
                this.total.equals(order.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, customerName, stateTerritory, taxRate,
                                productType, area, costPerSquareFoot, laborCostPerSquareFoot,
                                materialCost, laborCost, tax, total);
    }

    public String toString() {
        return "Order #"+this.getNumber()+" Customer: "+this.getCustomerName()+" State: "+this.getStateTerritory()+
                " TaxRate: "+this.getTaxRate()+" Product: "+this.getProductType()+" Area: "+this.getArea()+
                " Cost per sq. ft: "+this.getCostPerSquareFoot()+" Labor Cost per sq. ft: "+this.getLaborCostPerSquareFoot()+
                " Material Cost: "+this.getMaterialCost()+" Labor Cost: "+this.getLaborCost()+
                " Tax: "+this.getTax()+" Total: "+this.getTotal();
    }
}
