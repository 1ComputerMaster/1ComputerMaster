package com.ssafy.vue.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.vue.model.WeatherDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.ognl.ObjectElementsAccessor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api")
@Api("날씨")
public class WeatherApiController {
    private static final Logger logger = LoggerFactory.getLogger(WeatherApiController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @ApiOperation(value = "날씨 검색", notes = "해당 날씨 검색.", response = List.class)
    @GetMapping("/weather")
    public ResponseEntity<HashMap<String, Object>> restApiGetWeather(@ApiParam(value = "param", required = true) WeatherDto weatherDto) throws Exception {
        /*요청정보입력 * 아래와 같은 정보들은 사용자 가이드를 확인하여 찾아주시면 됩니다. * 위도 경도는 엑셀파일 안에 있습니다. * */
//            자신이 조회를 원하는 지역의 경도와 위도를 입력해주세요
        
    	System.out.println(weatherDto.getNx() + " " + weatherDto.getNy());
        LocalDate now = LocalDate.now();
     // 연도, 월(문자열, 숫자), 일, 일(year 기준), 요일(문자열, 숫자)
     int year = now.getYear();
     int monthValue = now.getMonthValue();
     int dayOfMonth = now.getDayOfMonth();
     int dayOfYear = now.getDayOfYear();
     String dayOfWeek = now.getDayOfWeek().toString();
     int dayOfWeekValue = now.getDayOfWeek().getValue();
     String month;
     SimpleDateFormat format1 = new SimpleDateFormat("HH");
     SimpleDateFormat format2 = new SimpleDateFormat("mm");
     String h = format1.format(new Date()).toString();
     String m = format2.format(new Date()).toString();
     if(monthValue < 10) {
    	  month = 0+Integer.toString(monthValue);
     }else {
    	 month = Integer.toString(monthValue);
     }
     String day = Integer.toString(dayOfMonth);
     String year_str = Integer.toString(year);
        String nx = weatherDto.getNx();
//             경도
        String ny = weatherDto.getNy();
//             위도
        String baseDate = year_str+month+day;
//             자신이 조회하고싶은 날짜를 입력해주세요
        if(Integer.parseInt(h) > 2) {
        	int temp = Integer.parseInt(h)-1;
        	if(temp < 0) {
        		h = "0"+Integer.toString(temp);
        	}else {
        		h = Integer.toString(temp);
        	}
        }
        if(Integer.parseInt(m)<30) {
        	m = "00";
        }else {
        	m = "00";
        }
        String baseTime = h+m;
        
        
        String serviceKey = "cRv3SXAMhqr8WN3dHxlWGzd1Vac5Vc7uuEmPKZxOJeynn%2ByZqCoWq0woJvAGN0OGjTktx%2BPR8GWnhrAkoA%2BAOQ%3D%3D";
        String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst"
                + "?serviceKey=" + serviceKey
                + "&dataType=JSON"
                + "&numOfRows=10"
                + "&pageNo=1"
                + "&base_date=" + baseDate
                + "&base_time=0600"// + baseTime
                + "&nx=" + weatherDto.getNx() 
                + "&ny=" + weatherDto.getNy();

//             위 urlStr을 이용해서 URL 객체를 만들어줍니다.
        HashMap<String, Object> resultMap = getDataFromJson(url, "UTF-8", "get", "");
        System.out.println("# result" + resultMap);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", resultMap);

        return new ResponseEntity<HashMap<String, Object>>(resultMap, HttpStatus.OK);
    }

    public HashMap<String, Object> getDataFromJson(String url, String encoding, String type, String jsonStr) throws Exception {
        boolean isPost = false;

        if ("post".equals(type)) {
            isPost = true;
        }
        else {
            url = "".equals(jsonStr) ? url : url + "?request=" + jsonStr;
        }

        return getStringFromURL(url, encoding, isPost, jsonStr, "application/json");
    }

    public HashMap<String, Object> getStringFromURL(String url, String encoding, boolean isPost, String parameter, String contentType) throws Exception {
        URL apiUrl = new URL(url);
        HttpURLConnection conn = null;
        BufferedReader br = null;
        BufferedWriter bw = null;

        HashMap<String, Object> resultMap = new HashMap<>();

        try {
            conn = (HttpURLConnection) apiUrl.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setDoOutput(true);

            if (isPost) {
                bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
                bw.write(parameter);
                bw.flush();
                bw = null;
            }

            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));

            String line = null;
            StringBuffer result = new StringBuffer();

            while ((line = br.readLine()) != null) result.append(line);

            ObjectMapper mapper = new ObjectMapper();
            resultMap = mapper.readValue(result.toString(), HashMap.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(url + " interface fail " + e.toString());
        } finally {
            if (conn != null) conn.disconnect();
            if (bw != null) bw.close();
            if (br != null) br.close();
        }

        return resultMap;
    }
}
