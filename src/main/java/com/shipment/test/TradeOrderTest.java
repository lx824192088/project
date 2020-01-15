package com.shipment.test;


import com.shipment.ShipmentApp;
import com.shipment.common.ShipmentException;
import com.shipment.model.*;
import com.shipment.service.TradeOrderService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={ShipmentApp.class})
public class TradeOrderTest {
    @Autowired
    private TradeOrderService tradeOrderService;
    @Test
    public void createTradeOrderTest1(){
        try {
            TradeOrderRequestModel requestModel = new TradeOrderRequestModel();
            tradeOrderService.createTradeOrder(requestModel);
        } catch (ShipmentException e) {
            String error = "quantity is null";
            Assert.assertEquals(error,e.getMessage());
        }

    }

    @Test
    public void createTradeOrderTest2(){
        try {
            TradeOrderRequestModel requestModel = new TradeOrderRequestModel();
            requestModel.setQuantity(BigDecimal.ZERO);
            tradeOrderService.createTradeOrder(requestModel);
        } catch (ShipmentException e) {
            String error = "quantity must be greater than zero";
            Assert.assertEquals(error,e.getMessage());
        }
    }
    @Test
    public void createTradeOrderTest3(){
        try {
            TradeOrderRequestModel requestModel = new TradeOrderRequestModel();
            requestModel.setQuantity(new BigDecimal("-1"));
            tradeOrderService.createTradeOrder(requestModel);
        } catch (ShipmentException e) {
            String error = "quantity must be greater than zero";
            Assert.assertEquals(error,e.getMessage());
        }
    }

    @Test
    public void createTradeOrderTest4(){
        //create trade
        TradeOrderRequestModel requestModel = new TradeOrderRequestModel();
        requestModel.setQuantity(new BigDecimal("100"));
        TradeOrder tradeOrder = tradeOrderService.createTradeOrder(requestModel);
        Assert.assertNotNull(tradeOrder);
    }

    @Test
    public void createTradeOrderTest5(){
        //create trade
        TradeOrderRequestModel requestModel = new TradeOrderRequestModel();
        requestModel.setQuantity(new BigDecimal("200"));
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

        //split shipment1
        SplitShipmentRequestModel requestModel3 = new SplitShipmentRequestModel();
        requestModel3.setParentShipmentId(shipment.getId());
        BigDecimal[] quantitys = new BigDecimal[2];
        quantitys[0] = new BigDecimal("40");
        quantitys[1] = new BigDecimal("60");
        requestModel3.setQuantitys(quantitys);
        tradeOrderService.splitShipment(requestModel3);

        //find split shipments
        List<Shipment> splitShipments = tradeOrderService.findShipmentByParentId(shipment.getId());

        //merge shipment
        MergeShipmentRequestModel requestModel4 = new MergeShipmentRequestModel();
        String[] parentId = {splitShipments.get(0).getId(),shipment1.getId()};
        requestModel4.setParentShipmentIds(parentId);
        requestModel4.setChildShipmentId("test003");
        Shipment mergeShipment = tradeOrderService.mergeShipment(requestModel4);

        //find merge shipments
        List<Shipment> mergeShipments = tradeOrderService.findShipmentByChildId(mergeShipment.getId());

        // check shipment
        Assert.assertNotNull(tradeOrder);
        Assert.assertNotNull(shipment);
        Assert.assertNotNull(splitShipments);
        Assert.assertTrue(splitShipments.size()==2);
        Assert.assertNotNull(mergeShipment);

        //change root shipment quantity
        TradeOrderRequestModel requestModel5 = new TradeOrderRequestModel();
        requestModel5.setQuantity(new BigDecimal("400"));
        requestModel5.setId(tradeOrder.getId());
        tradeOrderService.changeTradeQuantity(requestModel5);



        //check change quantity
        TradeOrder changeOrder = tradeOrderService.findTradeOrderById(tradeOrder.getId());
        List<Shipment> splitShipments1 = tradeOrderService.findShipmentByParentId(shipment.getId());
        Shipment changeShipment1 =splitShipments1.stream().filter(x->x.getQuantity().compareTo(new BigDecimal("120"))==0).collect(Collectors.toList()).get(0);
        Shipment changeShipment2 =splitShipments1.stream().filter(x->x.getQuantity().compareTo(new BigDecimal("80"))==0).collect(Collectors.toList()).get(0);

        Assert.assertTrue(changeOrder.getQuantity().compareTo(new BigDecimal("400"))==0);
        Assert.assertNotNull(changeShipment1);
        Assert.assertNotNull(changeShipment2);
    }
}
