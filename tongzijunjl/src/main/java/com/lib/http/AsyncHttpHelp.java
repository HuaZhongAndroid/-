package com.lib.http;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.content.Context;
import android.util.Log;

import com.bm.api.BaseApi;
import com.bm.app.App;
import com.richer.tzjjl.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lib.http.result.BaseResult;
import com.lib.log.Lg;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class AsyncHttpHelp {
	
	
	 private AsyncHttpHelp() {}  
	    private static AsyncHttpHelp single=null;  
	    //静态工厂方法   
	    public static AsyncHttpHelp getInstance() {  
	         if (single == null) {    
	             single = new AsyncHttpHelp();  
	         }    
	        return single;  
	    }  

	// private static String
	// url="https://103.21.116.141:8443/huafa/app/userUnRead/getAllUnreadCount.do?type=1&cityId=310100&userId=1e55ff20a7264a0a9a3ebe02b066a5f8&";

	// private static String url =
	// "http://112.64.173.178/huafa/app/userUnRead/getAllUnreadCount.do?type=1&cityId=310100&userId=1e55ff20a7264a0a9a3ebe02b066a5f8&";

	
	    String urlStr;
	public  void httpGet(Context context, String url,HashMap<String, String> params,final ServiceCallback callback) {
		url = BaseApi.API_URL_PRE + url;
		String logStr = new String(url);
	     urlStr = new String(url);
		urlStr+="?";
		logStr+="?";
		try {
			if(params != null){
				Iterator<Entry<String, String>> it = params.entrySet().iterator();
				while(it.hasNext()){
					Entry<String,String> entry = it.next();
					if(entry.getValue()!=null){
					urlStr+=entry.getKey()+"="+URLEncoder.encode(entry.getValue(), "utf-8")+"&";
					logStr+=entry.getKey()+"="+entry.getValue()+"&";
					}
				}
				params = null;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Lg.i("url:", logStr);
	    final	AsyncHttpClient client = new AsyncHttpClient();
		client.setTimeout(30*1000);
		client.setSSLSocketFactory(getSSL());
		callback.url = logStr;
		if(context  ==  null){
			client.get( urlStr, getReponHandler(callback));
		}else{
			client.get(context, urlStr, null, getReponHandler(callback));
		}
			
	}

	public  void httpPost(Context context,String url, Map<String, String> map, String fileName, List<File> files,ServiceCallback callback) {
		url = BaseApi.API_URL_PRE + url;
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		String logStr = url+"?";
		try {
			if (map != null) {
				Set<String> keys = map.keySet();
				for (Iterator<String> i = keys.iterator(); i.hasNext();) {
					String key = i.next();
					 logStr+=key+"="+map.get(key)+"&";
					 params.put(key,map.get(key));
//					params.put(key, URLEncoder.encode(map.get(key), "utf-8"));
				}
			}

			File[] filess=new File[files.size()];
			if (files != null) {
				for (int i = 0; i < files.size(); i++) {
					filess[i]=files.get(i);
				}
				params.put(fileName, filess);
			} 
			Lg.e("url:", logStr);
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		callback.url = logStr;
		client.post(context, url, params, getReponHandler(callback));

	}

	
	
	private  ResponseHandlerInterface getReponHandler(final ServiceCallback callback) {
		final BaseResult r = null;
		return new TextHttpResponseHandler() {
			
			
			public boolean getUseSynchronousMode() {
				return false;
			};
			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Log.e("error", "网络请求失败 url = "+callback.url+"  error"+arg2);
				arg3.printStackTrace();
				App.toast("网络请求失败");
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				System.out.println("返回结果:" + arg2);
				BaseResult r = null;
				try {
					Log.e("onSuccess", " url = "+callback.url+"  \n"+arg2);
					r = getGson().fromJson(arg2, callback.type);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (r == null|| (r.status != BaseResult.STATUS_SUCCESS && r.msg == null)) {
					callback.error(App.getInstance().getString(R.string.connect_server_failed));
				} else if (r.status != BaseResult.STATUS_SUCCESS) {
					callback.error(r.msg);
				} else {
					callback.done(0, r);
				}
			}
		};
	}

	
	/**
	 * 提供默认日期解析格式的Gson对象
	 * 
	 * @return
	 */
	public  Gson getGson() {
		return new GsonBuilder().setDateFormat(DefaultDateFormat).create();
	}
	public static final String DefaultDateFormat = "yyyy/MM/dd HH:mm:ss";
	
	

	
	/**
	 * 
	 * https  支持
	 * @return
	 */
	private  MySSLSocketFactory getSSL(){
		/// We initialize a default Keystore
				KeyStore trustStore;
				MySSLSocketFactory socketFactory = null;
				try {
					trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
					// 如需要加入证书在这里设置
					trustStore.load(null, null);
					socketFactory = new MySSLSocketFactory(trustStore);
					socketFactory.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				} catch (KeyStoreException e) {
					e.printStackTrace();
				} catch (KeyManagementException e) {
					e.printStackTrace();
				} catch (UnrecoverableKeyException e) {
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (CertificateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				return socketFactory;
	}
	
	
	
}
