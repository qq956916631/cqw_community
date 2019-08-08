package com.cqw.community.dao;

import com.cqw.community.domain.database.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    @Insert("INSERT INTO user(account_id,name,token,gmt_create,gmt_modified) " +
            "values(#{account_id},#{name},#{token},#{gmt_create},#{gmt_modified})")
    void insert(User u);
}
