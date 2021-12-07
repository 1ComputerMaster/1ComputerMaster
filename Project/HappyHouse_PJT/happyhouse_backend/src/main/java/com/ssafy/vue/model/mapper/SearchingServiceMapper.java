package com.ssafy.vue.model.mapper;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.vue.model.HouseInfoDto;
import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.model.DongCodeDto;
@Mapper
public interface SearchingServiceMapper {
	public List<HouseInfoDto> listDong(DongCodeDto dongCodeDto) throws SQLException;

}
