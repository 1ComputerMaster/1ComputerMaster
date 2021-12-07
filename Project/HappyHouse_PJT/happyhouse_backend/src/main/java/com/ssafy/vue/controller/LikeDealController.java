package com.ssafy.vue.controller;

import java.util.List;

import com.ssafy.vue.model.HouseInfoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.vue.model.LikeDealDto;
import com.ssafy.vue.model.service.LikeDealService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
@Api("게시판 컨트롤러  API V1")
@RestController
@RequestMapping("/likedeal")
public class LikeDealController {
	private static final Logger logger = LoggerFactory.getLogger(LikeDealController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private LikeDealService likeDealService;
	
	@ApiOperation(value = "가장 큰 likeID 뽑음", notes = "가장 큰 likeID 뽑음.", response = List.class)
	@GetMapping("/high")
	public ResponseEntity<List<LikeDealDto>> HighId() throws Exception {
		logger.info("HighId - 호출");
		return new ResponseEntity<List<LikeDealDto>>(likeDealService.HighId(), HttpStatus.OK);
	}

	@ApiOperation(value = "새로 등록하는 것", notes = "새로운 게시글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> insertMylike(@RequestBody @ApiParam(value = "게시글 정보.", required = true) LikeDealDto likeDealDto) throws Exception {
		logger.info("insertMylike - 호출");
		List<HouseInfoDto> list = likeDealService.listMyLike(likeDealDto);
		for(HouseInfoDto ldt : list)
		{
			if(ldt.getAptCode() == likeDealDto.getAptCode()) {
				return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
			}
		}
		if (likeDealService.insertMylike(likeDealDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "내 관심 거래 가져오기", notes = "내 관심 거래 정보를 출력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@GetMapping
	public ResponseEntity<List<HouseInfoDto>> listMyLike(@ApiParam(value = "관심 거래", required = true) LikeDealDto likeDealDto) throws Exception {
		logger.info("listMyLike - 호출");
		return new ResponseEntity<List<HouseInfoDto>>(likeDealService.listMyLike(likeDealDto), HttpStatus.OK);
	}

	@ApiOperation(value = "Like 삭제", notes = "내 관심 거래 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping
	public ResponseEntity<String> deleteLike(@ApiParam(value = "삭제할 글의 글번호.", required = true) LikeDealDto likeDealDto) throws Exception {
		logger.info("deleteLike - 호출");
		if (likeDealService.deleteLike(likeDealDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}
