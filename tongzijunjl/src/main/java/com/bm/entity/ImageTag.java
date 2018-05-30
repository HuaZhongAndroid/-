package com.bm.entity;

import java.io.Serializable;

public class ImageTag implements Serializable{

	
	/**
	 * 图片tag
	 * 
	 */
	private boolean imgTag;
	
	/**
	 * 图片地址
	 * 
	 */
	private String imageStr;
	public boolean isImgTag() {
		return imgTag;
	}
	public void setImgTag(boolean imgTag) {
		this.imgTag = imgTag;
	}
	public String getImageStr() {
		return imageStr;
	}
	public void setImageStr(String imageStr) {
		this.imageStr = imageStr;
	}
	
	
}
