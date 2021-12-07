package com.ssafy.vue.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.util.PageNavigation;
import com.ssafy.vue.model.BoardDto;
import com.ssafy.vue.model.NoticeDto;
import com.ssafy.vue.model.NoticeParameterDto;
import com.ssafy.vue.model.mapper.BoardMapper;
import com.ssafy.vue.model.mapper.NoticeMapper;

@Service
public class NoticeServiceImpl implements NoticeService {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public boolean writeArticle(NoticeDto noticeDto) throws Exception {
		if(noticeDto.getTitle() == null || noticeDto.getContent() == null) {
			throw new Exception();
		}
		return sqlSession.getMapper(NoticeMapper.class).writeArticle(noticeDto) == 1;
	}

	@Override
	public List<NoticeDto> listArticle(NoticeParameterDto noticeParameterDto) throws Exception {
		int start = noticeParameterDto.getPg() == 0 ? 0 : (noticeParameterDto.getPg() - 1) * noticeParameterDto.getSpp();
		noticeParameterDto.setStart(start);
		return sqlSession.getMapper(NoticeMapper.class).listArticle(noticeParameterDto);
	}

	@Override
	public PageNavigation makePageNavigation(NoticeParameterDto noticeParameterDto) throws Exception {
		int naviSize = 5;
		PageNavigation pageNavigation = new PageNavigation();
		pageNavigation.setCurrentPage(noticeParameterDto.getPg());
		pageNavigation.setNaviSize(naviSize);
		int totalCount = sqlSession.getMapper(NoticeMapper.class).getTotalCount(noticeParameterDto);//총글갯수  269
		pageNavigation.setTotalCount(totalCount);  
		int totalPageCount = (totalCount - 1) / noticeParameterDto.getSpp() + 1;//27
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = noticeParameterDto.getPg() <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < noticeParameterDto.getPg();
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();
		return pageNavigation;
	}

	@Override
	public NoticeDto getArticle(int noticeId) throws Exception {
		return sqlSession.getMapper(NoticeMapper.class).getArticle(noticeId);
	}
	
	/*
	 * @Override public void updateHit(int boardId) throws Exception {
	 * sqlSession.getMapper(BoardMapper.class).updateHit(boardId); }
	 */

	@Override
	@Transactional
	public boolean modifyArticle(NoticeDto noticeDto) throws Exception {
		return sqlSession.getMapper(NoticeMapper.class).modifyArticle(noticeDto) == 1;
	}

	@Override
	@Transactional
	public boolean deleteArticle(int noticeId) throws Exception {
//		sqlSession.getMapper(BoardMapper.class).deleteMemo(articleno);
		return sqlSession.getMapper(NoticeMapper.class).deleteArticle(noticeId) == 1;
	}
	@Override
	public List<NoticeDto> HighId() {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(NoticeMapper.class).HighId();
	}

	@Override
	public List<NoticeDto> listAllArticle(NoticeParameterDto noticeParameterDto) throws Exception {
		return sqlSession.getMapper(NoticeMapper.class).listAllArticle(noticeParameterDto);
	}

}