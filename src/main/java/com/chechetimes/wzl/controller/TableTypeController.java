package com.chechetimes.wzl.controller;


import com.alibaba.fastjson.JSONObject;
import com.chechetimes.wzl.entity.TableType;
import com.chechetimes.wzl.service.TableTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mybatisplus
 * @since 2020-04-14
 */
@RestController
@RequestMapping("tableType")
public class TableTypeController {

    @Autowired
    private TableTypeService tableTypeService;

    @RequestMapping(value = "all")
    public String allTableType() {
        return JSONObject.toJSONString(tableTypeService.list());
    }

}

