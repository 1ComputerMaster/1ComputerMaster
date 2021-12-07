package com.ssafy.vue.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.vue.model.HouseInfoDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.vue.model.LikeDealDto;
import com.ssafy.vue.model.mapper.LikeDealMapper;
@Service
public class LikeDealServiceImpl implements LikeDealService {
	
	@Autowired
	private SqlSession sqlSession;
	
	
	@Override
	public boolean insertMylike(LikeDealDto likeDealDto) throws SQLException {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(LikeDealMapper.class).insertMylike(likeDealDto);
	}
	@Override
	public List<LikeDealDto> HighId() throws SQLException  {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(LikeDealMapper.class).HighId();
	}
	@Override
	public List<HouseInfoDto> listMyLike(LikeDealDto likeDealDto) throws SQLException {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(LikeDealMapper.class).listMyLike(likeDealDto);
	}
	@Override
	public boolean deleteLike(LikeDealDto likeDealDto) {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(LikeDealMapper.class).deleteLike(likeDealDto);
	}

}
