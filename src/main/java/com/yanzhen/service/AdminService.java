package com.yanzhen.service;

import com.github.pagehelper.PageHelper;
import com.google.common.base.Splitter;
import com.yanzhen.entity.Admin;
import com.yanzhen.mapper.AdminMapper;
import com.yanzhen.utils.BeanMapUtils;
import com.yanzhen.utils.MapParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;

    //增
    public int create(Admin pi){
        return adminMapper.create(pi);
    }

    //删
    public int delete(Integer id){
        return adminMapper.delete(MapParameter.getInstance().addId(id).getMap());
    }

    public int deleteBatch(String ids){
        int flag = 0;
        List<String> list = Splitter.on(",").splitToList(ids);
        for (String s : list) {
            adminMapper.delete(MapParameter.getInstance().addId(Integer.parseInt(s)).getMap());
            flag++;
        }
        return flag;
    }

    //改
    public int update(Admin admin){
        Map<String, Object> map = MapParameter.getInstance().put(BeanMapUtils.beanToMapForUpdate(admin)).addId(admin.getId()).getMap();
        return adminMapper.update(map);
    }

    //查
    public List<Admin> query(Admin admin){
        PageHelper.startPage(admin.getPage(),admin.getLimit());
        Map<String, Object> map = MapParameter.getInstance().put(BeanMapUtils.beanToMap(admin)).getMap();
        return adminMapper.query(map);
    }

    //详细信息
    public Admin detail(Integer id){
        return adminMapper.detail(MapParameter.getInstance().addId(id).getMap());
    }

    //个数
    public int count(Admin admin){
        Map<String, Object> map = MapParameter.getInstance().put(BeanMapUtils.beanToMap(admin)).getMap();
        return adminMapper.count(map);
    }

    /**
     * 管理员登录：查询是否有这个账号、密码
     * @param account
     * @param password
     * @return
     */
    public Admin login(String account,String password){
        Map<String, Object> map = MapParameter.getInstance().add("account",account).add("password",password).getMap();
        return adminMapper.detail(map);
    }

}
