package com.mm.service;

import com.github.pagehelper.PageHelper;
import com.mm.annotation.TransactionalRollbackForBusinessException;
import com.mm.common.exception.BusinessException;
import com.mm.domain.model.City;
import com.mm.domain.model.Shop;
import com.mm.domain.pojo.CityAndShopPO;
import com.mm.domain.pojo.CityDTO;
import com.mm.domain.pojo.ShopDTO;
import com.mm.mapper.ShopMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {

    @Autowired
    ShopMapper shopMapper;

    public List<Shop> find(String name, Integer pageNo, Integer pageSize) {
        return PageHelper.startPage(pageNo, pageSize).doSelectPage(() -> shopMapper.find(name));
    }

    public boolean add(ShopDTO shopDTO) {
        Shop shop = new Shop();
        BeanUtils.copyProperties(shopDTO, shop);
        return shopMapper.insert(shop) > 0;
    }

    public boolean update(ShopDTO shopDTO) {
        Shop shop = detail(shopDTO.getId());
        BeanUtils.copyProperties(shopDTO, shop);
        return shopMapper.update(shop) > 0;
    }

    @TransactionalRollbackForBusinessException
    public boolean del(ShopDTO shopDTO) {
        Integer id = shopDTO.getId();
        return shopMapper.deleteById(id) > 0;
    }

    public Shop detail(Integer id) {
        return shopMapper.findOneById(id);
    }
}
