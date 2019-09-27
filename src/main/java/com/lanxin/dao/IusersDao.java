package com.lanxin.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2019/9/26 0026.
 */
@Mapper
public interface IusersDao {

    public String selectPassByUsername(String username);

    public List<String> selectPermsByUsername(String username);

    public List<String> selectRolesByUsername(String username);

}
