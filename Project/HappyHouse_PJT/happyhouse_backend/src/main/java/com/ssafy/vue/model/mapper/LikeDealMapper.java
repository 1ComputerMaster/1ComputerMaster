package com.ssafy.vue.model.mapper;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.vue.model.HouseInfoDto;
import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.model.DongCodeDto;
import com.ssafy.vue.model.LikeDealDto;
@Mapper
public interface LikeDealMapper {
	public boolean insertMylike(LikeDealDto likeDealDto) throws SQLException;
	public List<LikeDealDto> HighId() throws SQLException;
	public List<HouseInfoDto> listMyLike(LikeDealDto likeDealDto) throws SQLException;
	public boolean deleteLike(LikeDealDto likeDealDto);
}
