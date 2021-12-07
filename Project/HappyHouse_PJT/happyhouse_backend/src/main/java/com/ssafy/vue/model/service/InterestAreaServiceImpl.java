package com.ssafy.vue.model.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ssafy.vue.model.InterestedAreaDto;
import com.ssafy.vue.model.LikeDealDto;
import com.ssafy.vue.model.mapper.BoardMapper;
import com.ssafy.vue.model.mapper.InterestedAreaMapper;
import com.ssafy.vue.model.mapper.LikeDealMapper;
@Service
public class InterestAreaServiceImpl implements InterestAreaService {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public boolean InsertInterest(InterestedAreaDto interestAreaDto) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(InterestedAreaMapper.class).InsertInterest(interestAreaDto);
	}

	@Override
	public List<InterestedAreaDto> listInterest(InterestedAreaDto interestAreaDto) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(InterestedAreaMapper.class).listInterest(interestAreaDto);
	}

	@Override
	public boolean deleteInterest(InterestedAreaDto interestedAreaDto) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(InterestedAreaMapper.class).deleteInterest(interestedAreaDto);
	}

	@Override
	public String selectdongCode(InterestedAreaDto interestAreaDto) throws SQLException {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(InterestedAreaMapper.class).selectdongCode(interestAreaDto);
	}

}
