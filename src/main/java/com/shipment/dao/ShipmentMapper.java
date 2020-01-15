package com.shipment.dao;


import com.shipment.model.Shipment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShipmentMapper {

    public Shipment find(String id);

    public List<Shipment> findList(String tradeOrderId);

    public List<Shipment> findListAll(String tradeOrderId);

    public List<Shipment> findListByParentId(String parentShipmentId);

    public List<Shipment> findListByChildId(String childShipmentId);

    public void insert(Shipment shipment);

    public void update(Shipment shipment);

    public void updateByTradeId(Shipment shipment);

    public void delete(Shipment shipment);
}
