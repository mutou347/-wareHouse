package com.myjo.modle.API;

import java.io.UnsupportedEncodingException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.aip.ocr.AipOcr;

public class BaiDuAPI {
		public static final String APP_ID="10940680";
		public static final String API_KEY="kczT5UXLcRsG8BUe03pFuTek";
		public static final String SECRET_KEY="wdl08w51UTyV8kLElGypHBulBAZ4Dhnz";
		/**
		 * 解析验证码图片的接口
		 * @return
		 * @throws UnsupportedEncodingException
		 * @throws JSONException
		 */
		public String getVcode(){
			try {
				AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
				String path = "src/main/resources/valcodeImg/valcode.jpg";
				System.out.println(path);
				String image = path;
				JSONObject res = client.basicAccurateGeneral(image, null);
				JSONArray json = res.getJSONArray("words_result");
				String vcode = (String) json.getJSONObject(0).get("words");
				return vcode;
			}catch(Exception e) {
				e.printStackTrace();
			}
			return null;
		}
}
