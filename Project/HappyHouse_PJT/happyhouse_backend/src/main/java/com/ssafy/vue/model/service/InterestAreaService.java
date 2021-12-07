package com.ssafy.vue.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.vue.model.InterestedAreaDto;

public interface InterestAreaService {

	public boolean InsertInterest(InterestedAreaDto interestAreaDto) throws Exception;

	public List<InterestedAreaDto> listInterest(InterestedAreaDto interestAreaDto) throws Exception;
	public boolean deleteInterest(InterestedAreaDto interestedAreaDto) throws Exception;

	public String selectdongCode(InterestedAreaDto interestAreaDto) throws SQLException;
}
