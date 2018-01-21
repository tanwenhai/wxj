package com.mm.domain.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author tanwenhai@gusoftware.com
 */
@Setter
@Getter
public class Category extends BaseModel {
    private Integer id;
    private String name;
    private Integer sort;
}
