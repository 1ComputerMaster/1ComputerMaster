package com.ssafy.vue.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.vue.model.DongCodeDto;
import com.ssafy.vue.model.HouseInfoDto;

public interface SearchingService {
	List<HouseInfoDto> listDong(DongCodeDto dongCodeDto) throws SQLException;
}
