package com.shipment.model;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Shipment implements Serializable{
    private static final long serialVersionUID = 2L;

    private String id;

    private String tradeOrderId;

    private String parentShipmentId;

    private String childShipmentId;

    private BigDecimal quantity;

    private String status;

    private String createUser;

    private Date createTime;

    private String modifyUser;

    private Date modifyTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTradeOrderId() {
        return tradeOrderId;
    }

    public void setTradeOrderId(String tradeOrderId) {
        this.tradeOrderId = tradeOrderId;
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

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
