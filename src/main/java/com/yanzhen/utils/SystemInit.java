package com.yanzhen.utils;

import com.yanzhen.entity.Admin;
import com.yanzhen.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//全局初始化adminMap
public class SystemInit {

    @Autowired
    private AdminMapper adminMapper;

    public static Map<Integer,Admin> adminMap = new HashMap<>();

    public void init(){
        List<Admin> list = adminMapper.query(null);
        for (Admin admin : list) {
            adminMap.put(admin.getId(),admin);
        }
        System.out.println("初始化加载数据..."+adminMap);
    }

}
