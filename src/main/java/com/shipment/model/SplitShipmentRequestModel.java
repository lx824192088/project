package com.shipment.model;


import java.math.BigDecimal;

public class SplitShipmentRequestModel {
    private String parentShipmentId;

    private BigDecimal[] quantitys;

    public String getParentShipmentId() {
        return parentShipmentId;
    }

    public void setParentShipmentId(String parentShipmentId) {
        this.parentShipmentId = parentShipmentId;
    }

    public BigDecimal[] getQuantitys() {
        return quantitys;
    }

    public void setQuantitys(BigDecimal[] quantitys) {
        this.quantitys = quantitys;
    }
}
