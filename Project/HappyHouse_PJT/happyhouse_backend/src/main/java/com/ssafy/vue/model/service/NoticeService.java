package com.ssafy.vue.model.service;

import java.util.List;

import com.ssafy.util.PageNavigation;
import com.ssafy.vue.model.NoticeDto;
import com.ssafy.vue.model.NoticeParameterDto;

public interface NoticeService {
	public boolean writeArticle(NoticeDto NoticeDto) throws Exception;
	public List<NoticeDto> listArticle(NoticeParameterDto NoticeParameterDto) throws Exception;
	public PageNavigation makePageNavigation(NoticeParameterDto NoticeParameterDto) throws Exception;
	
	public NoticeDto getArticle(int noticeId) throws Exception;
//	public void updateHit(int articleno) throws Exception;
	public boolean modifyArticle(NoticeDto NoticeDto) throws Exception;
	public boolean deleteArticle(int noticeId) throws Exception;
	public List<NoticeDto> HighId();

    List<NoticeDto> listAllArticle(NoticeParameterDto noticeParameterDto) throws Exception;
}
