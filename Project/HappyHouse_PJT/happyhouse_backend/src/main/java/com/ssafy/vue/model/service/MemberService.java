package com.ssafy.vue.model.service;

import com.ssafy.vue.model.MemberDto;

public interface MemberService {

	public MemberDto login(MemberDto memberDto) throws Exception;
	public MemberDto memberInfo(String memberId) throws Exception;

    boolean registerMember(MemberDto memberDto) throws Exception;

	boolean modifyMember(MemberDto memberDto) throws Exception;
}
