package com.ssafy.vue.controller;

import java.util.List;

import com.ssafy.vue.model.HouseInfoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.vue.model.DongCodeDto;
import com.ssafy.vue.model.NoticeDto;
import com.ssafy.vue.model.NoticeParameterDto;
import com.ssafy.vue.model.service.SearchingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/dong")
@Api("검색 서비스 컨트롤러  API V1")
public class SearchingServiceController {
	private static final Logger logger = LoggerFactory.getLogger(SearchingServiceController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private SearchingService searchingService;
	
	@ApiOperation(value = "동 이름으로 검색", notes = "해당 동에 대한 정보를 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<List<HouseInfoDto>> listDong(@ApiParam(value = "해당 동의 정보를 얻기 위함", required = true) DongCodeDto dongCodeDto) throws Exception {
		logger.info("listDong - 호출");
		return new ResponseEntity<List<HouseInfoDto>>(searchingService.listDong(dongCodeDto), HttpStatus.OK);
	}
	
}
