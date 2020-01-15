package com.shipment.dao;


import com.shipment.model.TradeOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TradeOrderMapper {

    public TradeOrder find(String id);

    public void insert(TradeOrder tradeOrder);

    public void update(TradeOrder tradeOrder);

    public void delete(TradeOrder tradeOrder);
}
