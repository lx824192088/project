package com.shipment.test;


import com.shipment.ShipmentApp;
import com.shipment.common.ShipmentException;
import com.shipment.model.Shipment;
import com.shipment.model.ShipmentRequestModel;
import com.shipment.model.TradeOrder;
import com.shipment.model.TradeOrderRequestModel;
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
public class ShipmentTest {
    @Autowired
    private TradeOrderService tradeOrderService;
    @Test
    public void createShipmentTest1(){
        try {
            ShipmentRequestModel requestModel = new ShipmentRequestModel();
            tradeOrderService.createShipment(requestModel);
        } catch (ShipmentException e) {
            String error = "quantity is null";
            Assert.assertEquals(error,e.getMessage());
        }
    }

    @Test
    public void createShipmentTest2(){
        try {
            ShipmentRequestModel requestModel = new ShipmentRequestModel();
            requestModel.setTradeOrderId("");
            requestModel.setQuantity(new BigDecimal("100"));
            tradeOrderService.createShipment(requestModel);
        } catch (ShipmentException e) {
            String error = "not find tradeOrder";
            Assert.assertEquals(error,e.getMessage());
        }

    }
    @Test
    public void createShipmentTest3(){
        try {
            ShipmentRequestModel requestModel = new ShipmentRequestModel();
            requestModel.setTradeOrderId("test001");
            requestModel.setQuantity(BigDecimal.ZERO);
            tradeOrderService.createShipment(requestModel);
        } catch (ShipmentException e) {
            String error = "quantity must be greater than zero";
            Assert.assertEquals(error,e.getMessage());
        }
    }
    @Test
    public void createShipmentTest4(){
        try {
            ShipmentRequestModel requestModel = new ShipmentRequestModel();
            requestModel.setTradeOrderId("test001");
            requestModel.setQuantity(new BigDecimal("-1"));
            tradeOrderService.createShipment(requestModel);
        } catch (ShipmentException e) {
            String error = "quantity must be greater than zero";
            Assert.assertEquals(error,e.getMessage());
        }
    }
    @Test
    public void createShipmentTest5(){
        try {
            //case tradeOrder quantity 100
            TradeOrderRequestModel requestModel = new TradeOrderRequestModel();
            requestModel.setQuantity(new BigDecimal("100"));
            TradeOrder tradeOrder = tradeOrderService.createTradeOrder(requestModel);

            ShipmentRequestModel requestModel1 = new ShipmentRequestModel();
            requestModel1.setTradeOrderId(tradeOrder.getId());
            requestModel1.setQuantity(new BigDecimal("200"));
            tradeOrderService.createShipment(requestModel1);
        } catch (ShipmentException e) {
            String error = "tradeOrder quantity less than or equal to shipment quantity";
            Assert.assertEquals(error,e.getMessage());
        }
    }

    @Test
    public void createShipmentTest6(){
        //case tradeOrder quantity 100
        TradeOrderRequestModel requestModel = new TradeOrderRequestModel();
        requestModel.setQuantity(new BigDecimal("100"));
        TradeOrder tradeOrder = tradeOrderService.createTradeOrder(requestModel);

        ShipmentRequestModel requestModel1 = new ShipmentRequestModel();
        requestModel1.setTradeOrderId(tradeOrder.getId());
        requestModel1.setQuantity(new BigDecimal("50"));
        Shipment shipment1 = tradeOrderService.createShipment(requestModel1);

        ShipmentRequestModel requestModel2 = new ShipmentRequestModel();
        requestModel2.setTradeOrderId(tradeOrder.getId());
        requestModel2.setQuantity(new BigDecimal("50"));
        Shipment shipment2 = tradeOrderService.createShipment(requestModel2);

        Assert.assertNotNull(shipment1);
        Assert.assertNotNull(shipment2);
    }
}
