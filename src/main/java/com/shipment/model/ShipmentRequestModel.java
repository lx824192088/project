package com.shipment.model;


import java.math.BigDecimal;


public class ShipmentRequestModel {
    private String tradeOrderId;
    private BigDecimal quantity;
    private String parentShipmentId;
    private String childShipmentId;

    public String getTradeOrderId() {
        return tradeOrderId;
    }

    public void setTradeOrderId(String tradeOrderId) {
        this.tradeOrderId = tradeOrderId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getParentShipmentId() {
        return parentShipmentId;
    }

    public void setParentShipmentId(String parentShipmentId) {
        this.parentShipmentId = parentShipmentId;
    }

    public String getChildShipmentId() {
        return childShipmentId;
    }

    public void setChildShipmentId(String childShipmentId) {
        this.childShipmentId = childShipmentId;
    }
}
