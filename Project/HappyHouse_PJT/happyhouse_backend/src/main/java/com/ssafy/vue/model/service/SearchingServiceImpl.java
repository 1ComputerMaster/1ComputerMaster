package com.ssafy.vue.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.vue.model.HouseInfoDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.vue.model.DongCodeDto;
import com.ssafy.vue.model.mapper.SearchingServiceMapper;
@Service
public class SearchingServiceImpl implements SearchingService {
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<HouseInfoDto> listDong(DongCodeDto dongCodeDto) throws SQLException {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(SearchingServiceMapper.class).listDong(dongCodeDto);
	}

}
