package com.bm.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.bm.api.BaseApi;
import com.bm.entity.Dictionary;
import com.lib.http.AsyncHttpHelp;
import com.lib.http.ServiceCallback;
import com.lib.http.result.CommonListResult;

public class BaseDataUtil {

	
	public static List<Dictionary> yinhangList;
	
	
	public static void LoadBaseData(Context context)
	{
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("code", "kaiHuHang");
		AsyncHttpHelp.getInstance().httpGet(context,BaseApi.API_dictionary_list, map, new ServiceCallback<CommonListResult<Dictionary>>(){
			@Override
			public void done(int what, CommonListResult<Dictionary> obj) {
				if(obj.data != null)
				{
					yinhangList = obj.data;
				}
			}
			@Override
			public void error(String msg) {
			}});
		
	}
	
	
	
}
