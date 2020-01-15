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
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={ShipmentApp.class})
public class SplitShipmentTest {
    @Autowired
    private TradeOrderService tradeOrderService;

    @Test
    public void splitShipmentTest1(){
        try {
            SplitShipmentRequestModel requestModel = new SplitShipmentRequestModel();
            tradeOrderService.splitShipment(requestModel);
        } catch (Exception e) {
            String error = "splitShipment is empty";
            Assert.assertEquals(error,e.getMessage());
        }
    }

    @Test
    public void splitShipmentTest2(){
        try {
            SplitShipmentRequestModel requestModel = new SplitShipmentRequestModel();
            requestModel.setParentShipmentId("");
            tradeOrderService.splitShipment(requestModel);
        } catch (Exception e) {
            String error = "splitShipment is empty";
            Assert.assertEquals(error,e.getMessage());
        }
    }
    @Test
    public void splitShipmentTest3(){
        try {
            //create trade
            TradeOrderRequestModel requestModel = new TradeOrderRequestModel();
            requestModel.setQuantity(new BigDecimal("100"));
            TradeOrder tradeOrder = tradeOrderService.createTradeOrder(requestModel);

            //create shipment1
            ShipmentRequestModel requestModel1 = new ShipmentRequestModel();
            requestModel1.setTradeOrderId(tradeOrder.getId());
            requestModel1.setQuantity(new BigDecimal("100"));
            Shipment shipment = tradeOrderService.createShipment(requestModel1);

            SplitShipmentRequestModel requestModel3 = new SplitShipmentRequestModel();
            requestModel3.setParentShipmentId(shipment.getId());
            BigDecimal[] quantitys = new BigDecimal[2];
            quantitys[0] = new BigDecimal("20");
            quantitys[1] = new BigDecimal("60");
            requestModel3.setQuantitys(quantitys);
            tradeOrderService.splitShipment(requestModel3);
        } catch (Exception e) {
            String error = "quantitys summary must equal to quantity";
            Assert.assertEquals(error,e.getMessage());
        }
    }
    @Test
    public void splitShipmentTest4(){
        //create trade
        TradeOrderRequestModel requestModel = new TradeOrderRequestModel();
        requestModel.setQuantity(new BigDecimal("100"));
        TradeOrder tradeOrder = tradeOrderService.createTradeOrder(requestModel);

        //create shipment1
        ShipmentRequestModel requestModel1 = new ShipmentRequestModel();
        requestModel1.setTradeOrderId(tradeOrder.getId());
        requestModel1.setQuantity(new BigDecimal("100"));
        Shipment shipment = tradeOrderService.createShipment(requestModel1);

        SplitShipmentRequestModel requestModel3 = new SplitShipmentRequestModel();
        requestModel3.setParentShipmentId(shipment.getId());
        BigDecimal[] quantitys = new BigDecimal[2];
        quantitys[0] = new BigDecimal("40");
        quantitys[1] = new BigDecimal("60");
        requestModel3.setQuantitys(quantitys);
        tradeOrderService.splitShipment(requestModel3);
        //find split shipments
        List<Shipment> splitShipments = tradeOrderService.findShipmentByParentId(shipment.getId());

        //check
        Assert.assertNotNull(splitShipments);
        Assert.assertTrue(splitShipments.size()==2);
    }

}
