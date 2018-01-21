package com.mm.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author tanwenhai@gusoftware.com
 */
@Getter
@Setter
public class BaseModel {
    protected Date gmtCreate;
    protected Date gmtModify;
}
