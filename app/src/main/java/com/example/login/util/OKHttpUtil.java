package com.example.login.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.example.login.entity.Member;


import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OKHttpUtil {

	public Response getHttpWithParam(String url,Map<String,String> params) throws IOException {
		OkHttpClient client = new OkHttpClient();
		//Request request = new Request.Builder().build();
		 Builder builder = new Request.Builder();
		 
		 HttpUrl.Builder urlBuilder =HttpUrl.parse(url)
                 .newBuilder();
		for(Map.Entry<String, String> param: params.entrySet()) {
		   urlBuilder.addQueryParameter(param.getKey(), param.getValue());
		}
		Request request =builder.url(urlBuilder.build()).build();
		Response response =client.newCall(request).execute();
		
		return response;
	}
	
	public Response postHttpWithBody(String url,Object object,Map<String,String> params) throws IOException {
		OkHttpClient client = new OkHttpClient();
		String content=JSONObject.toJSONString(object);
		MediaType JSON=MediaType.parse("application/json;charset=utf-8");
		@SuppressWarnings("deprecation")
		RequestBody requestBody = RequestBody.create(JSON, content);
		
		HttpUrl.Builder urlBuilder =HttpUrl.parse(url)
                .newBuilder();
		for(Map.Entry<String, String> param: params.entrySet()) {
		   urlBuilder.addQueryParameter(param.getKey(), param.getValue());
		}
			
		Request request = new Request.Builder()
	                .url(urlBuilder.build())
	                .post(requestBody)
	                .build();
		
		Response response =client.newCall(request).execute();
		
		return response;
	}
	public static void main(String[] args) throws IOException {
		OKHttpUtil s=new OKHttpUtil();
		Map<String,String> map=new HashMap<>();
		Member m=new Member();
		m.setEmail("asd");
		m.setName("asdasd");
		
		map.put("name", "ccj");
		
		//s.postHttpWithBody(m,map);
	}
}
