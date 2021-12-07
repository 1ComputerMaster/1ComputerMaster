package com.ssafy.vue.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.model.InterestedAreaDto;
@Mapper
public interface InterestedAreaMapper {
	public boolean InsertInterest(InterestedAreaDto interestAreaDto) throws SQLException;
	public List<InterestedAreaDto> listInterest(InterestedAreaDto interestAreaDto) throws SQLException;
	public boolean deleteInterest(InterestedAreaDto interestedAreaDto) throws SQLException;
	public String selectdongCode(InterestedAreaDto interestAreaDto) throws SQLException;

}
