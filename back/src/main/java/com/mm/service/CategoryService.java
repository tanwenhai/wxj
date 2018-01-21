package com.mm.service;

import com.github.pagehelper.PageHelper;
import com.mm.annotation.TransactionalRollbackForBusinessException;
import com.mm.common.exception.BusinessException;
import com.mm.domain.model.Category;
import com.mm.domain.model.CategoryCity;
import com.mm.domain.pojo.CategoryDTO;
import com.mm.domain.pojo.CategoryAndCityPO;
import com.mm.mapper.CategoryCityMapper;
import com.mm.mapper.CategoryMapper;
import com.mm.mapper.CityMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author tanwenhai@gusoftware.com
 */
@Service
public class CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    CityMapper cityMapper;

    @Autowired
    CategoryCityMapper categoryCityMapper;

    @TransactionalRollbackForBusinessException
    public boolean add(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        try {
            if (categoryMapper.insert(category) > 0 && categoryDTO.getCityList().size() > 0) {
                categoryDTO.setId(category.getId());
                categoryCityMapper.batchInsert(categoryDTO.toCategoryCityList());
                return true;
            }
        } catch (Exception e) {
            throw new BusinessException(e);
        }

        return false;
    }

    @TransactionalRollbackForBusinessException
    public boolean update(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        try {
            if (categoryMapper.update(category) > 0 && categoryDTO.getCityList().size() > 0) {
                categoryCityMapper.deleteByCategoryId(category.getId());
                categoryCityMapper.batchInsert(categoryDTO.toCategoryCityList());
                return true;
            }

        } catch (Exception e) {
            throw new BusinessException(e);
        }

        return false;
    }

    @TransactionalRollbackForBusinessException
    public boolean del(CategoryDTO categoryDTO) {
        Integer id = categoryDTO.getId();
        try {
            if (categoryMapper.deleteById(id) > 0) {
                categoryCityMapper.deleteByCategoryId(id);
                return true;
            }

        } catch (Exception e) {
            throw new BusinessException(e);
        }

        return false;
    }

    public List<CategoryAndCityPO> search(String name, Integer pageNo, Integer pageSize) {
        return PageHelper.startPage(pageNo, pageSize).doSelectPage(() -> categoryMapper.findCategoryWithCity(name));
    }

    public CategoryAndCityPO detail(Integer id) {
        return categoryMapper.findCategoryWithCityById(id);
    }

}
