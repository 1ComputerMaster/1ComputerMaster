package com.ssafy.vue.model.service;

import com.ssafy.vue.model.mapper.BoardMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.vue.model.MemberDto;
import com.ssafy.vue.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public MemberDto login(MemberDto memberDto) throws Exception {
		if(memberDto.getMemberId() == null || memberDto.getMemberPw() == null)
			return null;
		return sqlSession.getMapper(MemberMapper.class).login(memberDto);
	}

	@Override
	public MemberDto memberInfo(String memberId) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).memberInfo(memberId);
	}

	@Override
	public boolean registerMember(MemberDto memberDto) throws Exception {
		if(memberDto.getMemberId() == null || memberDto.getMemberPw() == null) {
			throw new Exception();
		}
		return sqlSession.getMapper(MemberMapper.class).registerMember(memberDto) == 1;
	}

	@Override
	public boolean modifyMember(MemberDto memberDto) throws Exception {
		if(memberDto.getMemberId() == null || memberDto.getMemberPw() == null) {
			throw new Exception();
		}
		return sqlSession.getMapper(MemberMapper.class).modifyMember(memberDto) == 1;
	}

}
