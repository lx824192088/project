package com.shipment.service;


import com.shipment.model.*;

import java.util.List;

public interface TradeOrderService {

    /**
     * create TradeOrder
     * @param requestModel
     */
    public TradeOrder createTradeOrder(TradeOrderRequestModel requestModel);

    /**
     * create shipment
     * @param requestModel
     */
    public Shipment createShipment(ShipmentRequestModel requestModel);

    /**
     * split shipment
     * @param requestModel
     */
    public void splitShipment(SplitShipmentRequestModel requestModel);

    /**
     * merge shipment
     * @param requestModel
     */
    public Shipment mergeShipment(MergeShipmentRequestModel requestModel);

    /**
     * change trade quantity
     * @param requestModel
     */
    public void changeTradeQuantity(TradeOrderRequestModel requestModel);

    /**
     * find shipment by parentShipmentId
     * @param parentShipmentId
     * @return
     */
    public List<Shipment> findShipmentByParentId(String parentShipmentId);

    /**
     * find shipment by childShipmentId
     * @param childShipmentId
     * @return
     */
    public List<Shipment> findShipmentByChildId(String childShipmentId);

    /**
     * find Trade Order By Id
     * @param tradeOrderId
     * @return
     */
    public TradeOrder findTradeOrderById(String tradeOrderId);
}
