package com.shipment.test;


import com.shipment.ShipmentApp;
import com.shipment.model.*;
import com.shipment.service.TradeOrderService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={ShipmentApp.class})
public class MergeShipmentTest {
    @Autowired
    private TradeOrderService tradeOrderService;

    @Test
    public void mergeShipmentTest1(){
        try {
            MergeShipmentRequestModel requestModel = new MergeShipmentRequestModel();
            tradeOrderService.mergeShipment(requestModel);
        } catch (Exception e) {
            String error = "shipmentList is null";
            Assert.assertEquals(error,e.getMessage());
        }
    }

    @Test
    public void mergeShipmentTest3(){
        try {
            //create trade
            TradeOrderRequestModel requestModel = new TradeOrderRequestModel();
            requestModel.setQuantity(new BigDecimal("180"));
            TradeOrder tradeOrder = tradeOrderService.createTradeOrder(requestModel);

            //create shipment1
            ShipmentRequestModel requestModel1 = new ShipmentRequestModel();
            requestModel1.setTradeOrderId(tradeOrder.getId());
            requestModel1.setQuantity(new BigDecimal("100"));
            Shipment shipment = tradeOrderService.createShipment(requestModel1);

            MergeShipmentRequestModel requestModel3 = new MergeShipmentRequestModel();
            String[] parentId = {shipment.getId()};
            requestModel3.setParentShipmentIds(parentId);
            tradeOrderService.mergeShipment(requestModel3);
        } catch (Exception e) {
            String error = "shipmentList must have at least two items";
            Assert.assertEquals(error,e.getMessage());
        }
    }
    @Test
    public void mergeShipmentTest4(){
        try {
            //create trade
            TradeOrderRequestModel requestModel = new TradeOrderRequestModel();
            requestModel.setQuantity(new BigDecimal("180"));
            TradeOrder tradeOrder = tradeOrderService.createTradeOrder(requestModel);

            //create trade1
            TradeOrderRequestModel trade1 = new TradeOrderRequestModel();
            trade1.setQuantity(new BigDecimal("180"));
            TradeOrder tradeOrder1 = tradeOrderService.createTradeOrder(trade1);

            //create shipment1
            ShipmentRequestModel requestModel1 = new ShipmentRequestModel();
            requestModel1.setTradeOrderId(tradeOrder.getId());
            requestModel1.setQuantity(new BigDecimal("100"));
            Shipment shipment = tradeOrderService.createShipment(requestModel1);

            //create shipment2
            ShipmentRequestModel requestModel2 = new ShipmentRequestModel();
            requestModel2.setTradeOrderId(tradeOrder1.getId());
            requestModel2.setQuantity(new BigDecimal("80"));
            Shipment shipment1 = tradeOrderService.createShipment(requestModel2);

            MergeShipmentRequestModel requestModel3 = new MergeShipmentRequestModel();
            String[] parentId = {shipment.getId(),shipment1.getId()};
            requestModel3.setParentShipmentIds(parentId);
            tradeOrderService.mergeShipment(requestModel3);
        } catch (Exception e) {
            String error = "not the same trade";
            Assert.assertEquals(error,e.getMessage());
        }
    }

    @Test
    public void mergeShipmentTest5(){
        //create trade
        TradeOrderRequestModel requestModel = new TradeOrderRequestModel();
        requestModel.setQuantity(new BigDecimal("180"));
        TradeOrder tradeOrder = tradeOrderService.createTradeOrder(requestModel);

        //create shipment1
        ShipmentRequestModel requestModel1 = new ShipmentRequestModel();
        requestModel1.setTradeOrderId(tradeOrder.getId());
        requestModel1.setQuantity(new BigDecimal("100"));
        Shipment shipment = tradeOrderService.createShipment(requestModel1);

        //create shipment2
        ShipmentRequestModel requestModel2 = new ShipmentRequestModel();
        requestModel2.setTradeOrderId(tradeOrder.getId());
        requestModel2.setQuantity(new BigDecimal("80"));
        Shipment shipment1 = tradeOrderService.createShipment(requestModel2);

        MergeShipmentRequestModel requestModel3 = new MergeShipmentRequestModel();
        String[] parentId = {shipment.getId(),shipment1.getId()};
        requestModel3.setParentShipmentIds(parentId);
        Shipment shipment2 = tradeOrderService.mergeShipment(requestModel3);

        //check
        Assert.assertNotNull(shipment2);
        Assert.assertTrue(shipment2.getQuantity().compareTo(new BigDecimal("180"))==0);
    }
}
