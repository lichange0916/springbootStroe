package com.cy.store.service.impl;

import com.cy.store.entity.Address;
import com.cy.store.mapper.AddressMapper;
import com.cy.store.service.IAddressService;
import com.cy.store.service.IDistrictService;
import com.cy.store.service.ex.*;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 新增收获地址的实现类
 */
@Service
public class AddressServiceImpl implements IAddressService{
    @Autowired
    private AddressMapper addressMapper;

    //在添加用户收获地址的业务层,依赖于IDistrictService的业务层接口
    @Autowired
    private IDistrictService districtService;

    @Value("${user.address.max-count}")
    private Integer maxCount;

    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        //调用收获地址统计的方法
        Integer count = addressMapper.countByUid(uid);
        //判断count是否超过20条数据
        if (count>maxCount){
            throw new AddressCountLimitException("用户收获地址超出上限");
        }
        //对address对象中的数据进行补全：省市区
        String provinceName = districtService.getNameByCode(address.getProvinceCode()); //省
        String cityName = districtService.getNameByCode(address.getCityCode()); //市
        String areaName = districtService.getNameByCode(address.getAreaCode()); //区
        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);

        address.setUid(uid);
        //查询是否似乎第一条地址，如果是的则为默认地址
        Integer isDefault = count == 0?1:0; //1表示默认，0表示不是默认
        address.setIsDefault(isDefault);
        //补全四项日志
        address.setCreatedUser(username);
        address.setModifiedUser(username);
        address.setModifiedTime(new Date());
        address.setCreatedTime(new Date());
        //插入收获地址的方法
        Integer rows = addressMapper.insertAddress(address);
        if (rows != 1){
            throw new InsertException("插入用户的收货地址产生未知异常!");
        }
    }

    @Override
    public List<Address> getByUid(Integer uid) {
        List<Address> list = addressMapper.findByUid(uid);
        for (Address address:list){
            address.setProvinceName(null);
            address.setProvinceCode(null);
            address.setCityCode(null);
            address.setCityName(null);
            address.setAreaCode(null);
            address.setAreaName(null);
        }
        return list;
    }

    @Override
    public void setDefault(Integer aid, Integer uid, String username) {
        //检测收货地址是否存在
        Address result = addressMapper.findByAid(aid);
        if (result == null){
            throw new AddressNotFoundException("收货地址不存在!");
        }

        //检测当前获取到的收货地址数据归属
        if (!result.getUid().equals(uid)){
            throw new AccessDeniedException("非法数据访问!");
        }

        //先将所有的收货地址设置为非默认
        Integer rows = addressMapper.updateNonDefault(uid);
        if (rows < 1){
            throw new UpdateException("更新数据产生未知异常!");
        }

        //将用户选中的某条地址设置为默认收货地址
        rows = addressMapper.updateDefaultByAid(aid, username, new Date());
        if (rows != 1){
            throw new UpdateException("更新数据时产生未知异常!");
        }


    }

    @Override
    public void delete(Integer aid, Integer uid, String username) {
        //检测收货地址是否存在
        Address result = addressMapper.findByAid(aid);
        if (result == null){
            throw new AddressNotFoundException("收货地址不存在!");
        }

        //检测当前获取到的收货地址数据归属
        if (!result.getUid().equals(uid)){
            throw new AccessDeniedException("非法数据访问!");
        }

        //地址删除操作
        Integer rows = addressMapper.deleteByAid(aid);
        if (rows != 1){
            throw new DeleteException("删除数据产生未知异常!");
        }

        Integer count = addressMapper.countByUid(uid);

        //如果没有地址数据，直接返回
        if (count ==0){
            return;
        }

        //如果删除的值为非默认的地址
        if (result.getIsDefault() ==0){
            //直接终止程序
            return;
        }

        //判断删除的是否是默认数据，默认值删除后，设置后续符合条件的地址为默认值
        if (result.getIsDefault() == 1){
            //将这条数据中的is_default字符设置为 1
            Address address = addressMapper.findLastModified(uid);
            rows = addressMapper.updateDefaultByAid(address.getAid(), username, new Date());
        }

        if (rows !=1){
            throw new UpdateException("更新数据时产生未知的异常!");
        }

    }
}
