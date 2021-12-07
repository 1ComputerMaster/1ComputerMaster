package com.ssafy.vue.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "InterestedAreaDto : 관심 지역", description = "관심 지역의 상세 정보를 나타낸다.")
public class InterestedAreaDto {
	
	@ApiModelProperty(value = "관심 지역 Id")
	private int interId;
	@ApiModelProperty(value = "Dong Code")
	private String dongCode;
	@ApiModelProperty(value = "member Id")
	private String memberId;
	@ApiModelProperty(value = "등록 날짜")
	private String interDate;
	@ApiModelProperty(value = "동 이름")
	private String dongName;
	@ApiModelProperty(value = "시/도 이름")
	private String sidoName;
	@ApiModelProperty(value = "구/군 이름")
	private String gugunName;
	private String lat; 
	private String lng;
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dongName == null) ? 0 : dongName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InterestedAreaDto other = (InterestedAreaDto) obj;
		if (dongName == null) {
			if (other.dongName != null)
				return false;
		} else if (!dongName.equals(other.dongName))
			return false;
		return true;
	}
	
	public int getInterId() {
		return interId;
	}
	public void setInterId(int interId) {
		this.interId = interId;
	}
	public String getDongCode() {
		return dongCode;
	}
	public void setDongCode(String dongCode) {
		this.dongCode = dongCode;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getInterDate() {
		return interDate;
	}
	public void setInterDate(String interDate) {
		this.interDate = interDate;
	}
	public String getDongName() {
		return dongName;
	}
	public void setDongName(String dongName) {
		this.dongName = dongName;
	}
	public String getSidoName() {
		return sidoName;
	}
	public void setSidoName(String sidoName) {
		this.sidoName = sidoName;
	}
	public String getGugunName() {
		return gugunName;
	}
	public void setGugunName(String gugunName) {
		this.gugunName = gugunName;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	} 
	
	
}
