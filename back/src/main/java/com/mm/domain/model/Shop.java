package com.mm.domain.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author tanwenhai@gusoftware.com
 */
@Setter
@Getter
public class Shop extends BaseModel {
    private Integer id;
    private String name;
    private String imgUrl;
    private String content;
}
