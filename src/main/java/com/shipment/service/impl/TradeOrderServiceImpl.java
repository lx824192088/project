package com.shipment.service.impl;


import com.shipment.common.ShipmentException;
import com.shipment.common.UidUtil;
import com.shipment.dao.ShipmentMapper;
import com.shipment.dao.TradeOrderMapper;
import com.shipment.model.*;
import com.shipment.service.TradeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TradeOrderServiceImpl implements TradeOrderService {
    @Autowired
    private TradeOrderMapper tradeOrderMapper;

    @Autowired
    private ShipmentMapper shipmentMapper;
    /**
     * create TradeOrder
     *
     * @param requestModel
     */
    @Override
    public TradeOrder createTradeOrder(TradeOrderRequestModel requestModel) {
        if(requestModel==null){
            throw new ShipmentException("requestModel is null");
        }
        if(requestModel.getQuantity()==null){
            throw new ShipmentException("quantity is null");
        }
        if(requestModel.getQuantity().compareTo(BigDecimal.ZERO)<1){
            throw new ShipmentException("quantity must be greater than zero");
        }
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setId(UidUtil.getUUID());
        tradeOrder.setTradeOrderNo("C"+UidUtil.getUUID());
        tradeOrder.setQuantity(requestModel.getQuantity());
        tradeOrder.setStatus("1");
        tradeOrderMapper.insert(tradeOrder);
        return tradeOrder;
    }

    /**
     * create shipment
     *
     * @param requestModel
     */
    @Override
    public Shipment createShipment(ShipmentRequestModel requestModel) {
        if(requestModel==null){
            throw new ShipmentException("requestModel is null");
        }
        if(requestModel.getQuantity()==null){
            throw new ShipmentException("quantity is null");
        }
        if(requestModel.getQuantity().compareTo(BigDecimal.ZERO)<1){
            throw new ShipmentException("quantity must be greater than zero");
        }
        TradeOrder tradeOrder = tradeOrderMapper.find(requestModel.getTradeOrderId());
        if(tradeOrder==null){
            throw new ShipmentException("not find tradeOrder");
        }
        if(!tradeOrder.getStatus().equals("1")){
            throw new ShipmentException("tradeOrder status is valid");
        }
        List<Shipment> list = shipmentMapper.findList(requestModel.getTradeOrderId());
        BigDecimal sum = BigDecimal.ZERO;
        if(list!=null && list.size()>0){
            for(Shipment item:list){
                sum = sum.add(item.getQuantity());
            }
        }
        sum = sum.add(requestModel.getQuantity());
        if(tradeOrder.getQuantity().compareTo(sum)<0){
            throw new ShipmentException("tradeOrder quantity less than or equal to shipment quantity");

        }
        Shipment shipment = new Shipment();
        shipment.setId(UidUtil.getUUID());
        shipment.setTradeOrderId(requestModel.getTradeOrderId());
        shipment.setQuantity(requestModel.getQuantity());
        shipment.setStatus("1");
        shipment.setParentShipmentId(requestModel.getParentShipmentId());
        shipment.setChildShipmentId(requestModel.getChildShipmentId());
        shipment.setCreateTime(new Date());
        shipmentMapper.insert(shipment);
        return shipment;
    }

    /**
     * split shipment
     *
     * @param requestModel
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void splitShipment(SplitShipmentRequestModel requestModel) {
        try{
            if(requestModel==null){
                throw new ShipmentException("requestModel is null");
            }
            Shipment splitShipment = shipmentMapper.find(requestModel.getParentShipmentId());
            if(splitShipment==null){
                throw new ShipmentException("splitShipment is empty");
            }
            if(!splitShipment.getStatus().equals("1")){
                throw new ShipmentException("this shipment status is invalid");
            }
            if(requestModel.getQuantitys()==null||requestModel.getQuantitys().length==0){
                throw new ShipmentException("quantity at least one");
            }
            BigDecimal sum =BigDecimal.ZERO;
            for(BigDecimal quantity:requestModel.getQuantitys()){
                if(quantity.compareTo(BigDecimal.ZERO)<=0){
                    throw new ShipmentException("quantity must be greater than 0");
                }
                sum = sum.add(quantity);
            }
            if(splitShipment.getQuantity().compareTo(sum)!=0){
                throw new ShipmentException("quantitys summary must equal to quantity");
            }

            splitShipment.setStatus("0");
            shipmentMapper.update(splitShipment);

            for(BigDecimal quantity:requestModel.getQuantitys()){
                ShipmentRequestModel request = new ShipmentRequestModel();
                request.setTradeOrderId(splitShipment.getTradeOrderId());
                request.setQuantity(quantity);
                request.setParentShipmentId(splitShipment.getId());
                this.createShipment(request);
            }

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }

    /**
     * merge shipment
     *
     * @param requestModel
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Shipment mergeShipment(MergeShipmentRequestModel requestModel) {
        try{
            if(requestModel==null){
                throw new ShipmentException("requestModel is null");
            }
            if(requestModel.getParentShipmentIds()==null){
                throw new ShipmentException("shipmentList is null");
            }
            if(requestModel.getParentShipmentIds().length<2){
                throw new ShipmentException("shipmentList must have at least two items");
            }
            List<Shipment> mergeList = new ArrayList();
            for(String id:requestModel.getParentShipmentIds()){
                mergeList.add(shipmentMapper.find(id));
            }

            for(Shipment shipment:mergeList){
                if(!shipment.getStatus().equals("1")){
                    throw new ShipmentException("this shipment is invalid");
                }
                if(!shipment.getTradeOrderId().equals(mergeList.get(0).getTradeOrderId())){
                    throw new ShipmentException("not the same trade");
                }
                shipment.setStatus("0");
                shipmentMapper.update(shipment);
            }
            BigDecimal sum = BigDecimal.ZERO;
            for(Shipment shipment:mergeList){
                sum = sum.add(shipment.getQuantity());
            }
            ShipmentRequestModel request = new ShipmentRequestModel();
            request.setTradeOrderId(mergeList.get(0).getTradeOrderId());
            request.setQuantity(sum);
            Shipment mergeShipment = this.createShipment(request);

            for(Shipment shipment:mergeList){
                shipment.setChildShipmentId(mergeShipment.getId());
                shipmentMapper.update(shipment);
            }
            return mergeShipment;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * change trade quantity
     *
     * @param requestModel
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeTradeQuantity(TradeOrderRequestModel requestModel) {
        try{
            if(requestModel==null){
                throw new ShipmentException("requestModel is null");
            }
            if(requestModel.getQuantity()==null){
                throw new ShipmentException("quantity is null");
            }
            if(requestModel.getQuantity().compareTo(BigDecimal.ZERO)<=0){
                throw new ShipmentException("quantity must be greater than zero");
            }
            TradeOrder tradeOrder = tradeOrderMapper.find(requestModel.getId());
            if(!tradeOrder.getStatus().equals("1")){
                throw new ShipmentException("trade status is valid");
            }
            BigDecimal rate = requestModel.getQuantity().divide(tradeOrder.getQuantity(),6,BigDecimal.ROUND_HALF_UP);
            tradeOrder.setQuantity(requestModel.getQuantity());
            tradeOrderMapper.update(tradeOrder);

            List<Shipment> list = shipmentMapper.findListAll(requestModel.getId());
            for(Shipment shipment:list){
                shipment.setQuantity(shipment.getQuantity().multiply(rate));
                shipmentMapper.update(shipment);
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }

    /**
     * find shipment by parentShipmentId
     * @param parentShipmentId
     * @return
     */
    public List<Shipment> findShipmentByParentId(String parentShipmentId){
        return shipmentMapper.findListByParentId(parentShipmentId);
    }

    /**
     * find shipment by childShipmentId
     * @param childShipmentId
     * @return
     */
    public List<Shipment> findShipmentByChildId(String childShipmentId){
        return shipmentMapper.findListByChildId(childShipmentId);
    }
    /**
     * find Trade Order By Id
     * @param tradeOrderId
     * @return
     */
    public TradeOrder findTradeOrderById(String tradeOrderId){
        return tradeOrderMapper.find(tradeOrderId);
    }
}
