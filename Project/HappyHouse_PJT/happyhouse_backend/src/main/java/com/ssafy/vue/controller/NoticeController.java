package com.ssafy.vue.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.vue.model.BoardDto;
import com.ssafy.vue.model.NoticeDto;
import com.ssafy.vue.model.NoticeParameterDto;
import com.ssafy.vue.model.service.NoticeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

//http://localhost:9999/vue/swagger-ui.html
//@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.POST} , maxAge = 6000)
@RestController
@RequestMapping("/notice")
@Api("공지사항 컨트롤러  API V1")
public class NoticeController {

	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private NoticeService noticeService;
	

	@ApiOperation(value = "공지사항 글작성", notes = "새로운 공지글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> writeArticle(@RequestBody @ApiParam(value = "공지글 정보.", required = true) NoticeDto NoticeDto) throws Exception {
		logger.info("writeArticle - 호출");
		if (noticeService.writeArticle(NoticeDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "공지사항 글목록", notes = "모든 공지글의 정보를 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<List<NoticeDto>> listArticle(@ApiParam(value = "공지글을 얻기위한 부가정보.", required = true) NoticeParameterDto NoticeParameterDto) throws Exception {
		logger.info("listArticle - 호출");
		return new ResponseEntity<List<NoticeDto>>(noticeService.listArticle(NoticeParameterDto), HttpStatus.OK);
	}

	@ApiOperation(value = "공지사항 전체 글목록", notes = "모든 공지글의 정보를 반환한다.", response = List.class)
	@GetMapping("/all")
	public ResponseEntity<List<NoticeDto>> listAllArticle(@ApiParam(value = "공지글을 얻기위한 부가정보.", required = true) NoticeParameterDto NoticeParameterDto) throws Exception {
		logger.info("listAllArticle - 호출");
		return new ResponseEntity<List<NoticeDto>>(noticeService.listAllArticle(NoticeParameterDto), HttpStatus.OK);
	}

	@ApiOperation(value = "게시판 글 번호", notes = "모든 게시글의 번호를 반환한다.", response = List.class)
	@GetMapping("/high")
	public ResponseEntity<List<NoticeDto>> HighId() throws Exception {
		logger.info("HighId - 호출");
		return new ResponseEntity<List<NoticeDto>>(noticeService.HighId(), HttpStatus.OK);
	}

	
	@ApiOperation(value = "공지사항 글보기", notes = "글번호에 해당하는 공지글의 정보를 반환한다.", response = NoticeDto.class)
	@GetMapping("/{noticeId}")
	public ResponseEntity<NoticeDto> getArticle(@PathVariable("noticeId") @ApiParam(value = "얻어올 글의 글번호.", required = true) int noticeId) throws Exception {
		logger.info("getArticle - 호출 : " + noticeId);
		//NoticeService.updateHit(NoticeId);
		return new ResponseEntity<NoticeDto>(noticeService.getArticle(noticeId), HttpStatus.OK);
	}
	
	@ApiOperation(value = "공지사항 글수정", notes = "새로운 공지글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping
	public ResponseEntity<String> modifyArticle(@RequestBody @ApiParam(value = "수정할 글정보.", required = true) NoticeDto noticeDto) throws Exception {
		logger.info("modifyArticle - 호출");
		
		if (noticeService.modifyArticle(noticeDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
	
	@ApiOperation(value = "공지사항 글삭제", notes = "글번호에 해당하는 공지글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/{noticeId}")
	public ResponseEntity<String> deleteArticle(@PathVariable("noticeId") @ApiParam(value = "살제할 글의 글번호.", required = true) int noticeId) throws Exception {
		logger.info("deleteArticle - 호출");
		if (noticeService.deleteArticle(noticeId)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}