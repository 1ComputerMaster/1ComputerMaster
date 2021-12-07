package com.ssafy.vue.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.vue.model.BoardDto;
import com.ssafy.vue.model.BoardParameterDto;
import com.ssafy.vue.model.InterestedAreaDto;
import com.ssafy.vue.model.LikeDealDto;
import com.ssafy.vue.model.service.InterestAreaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/interest")
@Api("게시판 컨트롤러  API V1")
public class InterestedAreaController {
	private static final Logger logger = LoggerFactory.getLogger(InterestedAreaController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private InterestAreaService interestAreaService;

	@ApiOperation(value = "관심 지역", notes = "내 관심지역 즉, 해당 동에 대해서 저장한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> InsertInterest(@RequestBody @ApiParam(value = "게시글 정보.", required = true) InterestedAreaDto interestAreaDto) throws Exception {
		logger.info("InsertInterest - 호출");
		interestAreaDto.setDongCode(interestAreaService.selectdongCode(interestAreaDto));
		if (interestAreaService.InsertInterest(interestAreaDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "관심 지역 목록", notes = "모든 관심 지역의 정보를 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<List<InterestedAreaDto>> listInterest(@ApiParam(value = "게시글을 얻기위한 부가정보.", required = true) InterestedAreaDto interestAreaDto) throws Exception {
		logger.info("listInterest - 호출");
		List<InterestedAreaDto> list = interestAreaService.listInterest(interestAreaDto);
		List<InterestedAreaDto> newList = new ArrayList<InterestedAreaDto>(new HashSet<InterestedAreaDto>(list));
		return new ResponseEntity<List<InterestedAreaDto>>(newList, HttpStatus.OK);
	}
	@ApiOperation(value = "관심지역 삭제", notes = "내 관심지역 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping
	public ResponseEntity<String> deleteInterest(@ApiParam(value = "삭제할 글의 글번호.", required = true) InterestedAreaDto interestedAreaDto) throws Exception {
		logger.info("deleteInterest - 호출");
		if (interestAreaService.deleteInterest(interestedAreaDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

}
