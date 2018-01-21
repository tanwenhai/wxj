package com.mm.service;

import com.github.pagehelper.PageHelper;
import com.mm.annotation.TransactionalRollbackForBusinessException;
import com.mm.common.exception.BusinessException;
import com.mm.domain.model.Category;
import com.mm.domain.model.City;
import com.mm.domain.model.CityShop;
import com.mm.domain.pojo.CategoryAndCityPO;
import com.mm.domain.pojo.CategoryDTO;
import com.mm.domain.pojo.CityAndShopPO;
import com.mm.domain.pojo.CityDTO;
import com.mm.mapper.CityMapper;
import com.mm.mapper.CityShopMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.JDBCType;
import java.util.List;

/**
 * @author tanwenhai@gusoftware.com
 */
@Service
public class CityService {
    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private CityShopMapper cityShopMapper;

    public List<City> find(String name, Integer pageNo, Integer pageSize) {
        return PageHelper.startPage(pageNo, pageSize).doSelectPage(() -> cityMapper.find(name));
    }

    @TransactionalRollbackForBusinessException
    public boolean add(CityDTO cityDTO) {
        City city = new City();
        BeanUtils.copyProperties(cityDTO, city);
        try {
            if (cityMapper.insert(city) > 0 && cityDTO.getShopList().size() > 0) {
                cityDTO.setId(city.getId());
                cityShopMapper.batchInsert(cityDTO.toCityShopList());
                return true;
            }
        } catch (Exception e) {
            throw new BusinessException(e);
        }

        return false;
    }

    @TransactionalRollbackForBusinessException
    public boolean update(CityDTO cityDTO) {
        City city = new City();
        BeanUtils.copyProperties(cityDTO, city);
        try {
            if (cityMapper.update(city) > 0 && cityDTO.getShopList().size() > 0) {
                cityShopMapper.deleteByCategoryId(city.getId());
                cityShopMapper.batchInsert(cityDTO.toCityShopList());
                return true;
            }

        } catch (Exception e) {
            throw new BusinessException(e);
        }

        return false;
    }

    @TransactionalRollbackForBusinessException
    public boolean del(CityDTO cityDTO) {
        Integer id = cityDTO.getId();
        try {
            if (cityMapper.deleteById(id) > 0) {
                cityShopMapper.deleteByCategoryId(id);
                return true;
            }
        } catch (Exception e) {
            throw new BusinessException(e);
        }

        return false;
    }

    public CityAndShopPO detail(Integer id) {
        return cityMapper.findCityAndShopById(id);
    }
}
