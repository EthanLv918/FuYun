package com.fuYunSoft.mapper;

import com.fuYunSoft.pojo.AllToken;
import org.apache.ibatis.annotations.Param;

public interface AllTokenMapper {
    AllToken selectByAppAndType(@Param("appName") String appName,
                                @Param("tokenType") String tokenType);
    int insert(AllToken record);
    int updateByPrimaryKey(AllToken record);
}