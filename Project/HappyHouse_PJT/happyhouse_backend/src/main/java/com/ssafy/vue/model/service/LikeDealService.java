package com.ssafy.vue.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.vue.model.HouseInfoDto;
import com.ssafy.vue.model.LikeDealDto;

public interface LikeDealService {
	public boolean insertMylike(LikeDealDto likeDealDto) throws SQLException;
	public List<LikeDealDto> HighId() throws SQLException;
	public List<HouseInfoDto> listMyLike(LikeDealDto likeDealDto) throws SQLException;
	public boolean deleteLike(LikeDealDto likeDealDto);
}
