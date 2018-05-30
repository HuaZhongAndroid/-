package com.bm.im.entity;

public class BaobeijiluEntity {
private String content;//沟通内容
private String followupMode;//沟通方式
private String followupDegree;//沟通程度
private String followupTime;//报备时间

public boolean isShowContent = false; //是否显示展开内容


public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public String getFollowupMode() {
	return followupMode;
}
public void setFollowupMode(String followupMode) {
	this.followupMode = followupMode;
}
public String getFollowupDegree() {
	return followupDegree;
}
public void setFollowupDegree(String followupDegree) {
	this.followupDegree = followupDegree;
}
public String getFollowupTime() {
	return followupTime;
}
public void setFollowupTime(String followupTime) {
	this.followupTime = followupTime;
}



}
