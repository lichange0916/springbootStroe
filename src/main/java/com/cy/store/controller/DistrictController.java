package com.cy.store.controller;

import com.cy.store.entity.District;
import com.cy.store.service.IDistrictService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.cy.store.controller.BaseController.OK;

@RequestMapping("districts")
@RestController
public class DistrictController{
    @Autowired
    private IDistrictService districtService;

    //district开头的都被拦截到getByParent方法中
    @RequestMapping({"/",""})
    public JsonResult<List<District>> getByParent(String parent){
        List<District> data = districtService.getParent(parent);
        return new JsonResult<>(OK,data);
    }
}
