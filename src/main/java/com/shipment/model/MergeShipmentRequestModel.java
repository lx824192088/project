package com.shipment.model;



public class MergeShipmentRequestModel {

    private String[] parentShipmentIds;

    private String childShipmentId;

    public String[] getParentShipmentIds() {
        return parentShipmentIds;
    }

    public void setParentShipmentIds(String[] parentShipmentIds) {
        this.parentShipmentIds = parentShipmentIds;
    }

    public String getChildShipmentId() {
        return childShipmentId;
    }

    public void setChildShipmentId(String childShipmentId) {
        this.childShipmentId = childShipmentId;
    }
}
