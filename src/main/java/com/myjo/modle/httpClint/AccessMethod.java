package com.myjo.modle.httpClint;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baidu.aip.ocr.AipOcr;
import com.myjo.modle.API.BaiDuAPI;


/**
 * 天马发送请求并获得响应的方法
 * 
 * @author 麦子
 *
 */
@Service
public class AccessMethod {

	@Value("${tianma.nickName}")
	private String nickName;

	@Value("${tianma.pwd}")
	private String pwd;

	@Value("${tianma.remember}")
	private String remember;

	private String[] finalCookie;
	 private static final Logger LOGGER = LoggerFactory.getLogger(AccessMethod.class);
	/**
	 * 得到 (cookie+验证码图片)
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private Map<String, String> sendRequestAndGetResponse(){
		OutputStream os = null;
		CloseableHttpResponse response=null;
		CloseableHttpClient httpclient=null;
		try {
			// 生成HttpClient对象
			httpclient = HttpClients.createDefault();
			// 创建一个GET对象
			String codeUrl = "http://www.tianmasport.com/ms/ImageServlet?time=new%20Date().getTime()";
			HttpGet get = new HttpGet(codeUrl);
			// 执行请求
			response = httpclient.execute(get);
			// 取得响应结果
			Header[] headers = response.getAllHeaders();
			Map<String, String> map = new HashMap<String, String>();
			for (Header h : headers) {
				map.put(h.getName(), h.getValue());
			}
			String cookie = map.get("Set-Cookie");
			if(cookie!=null) {
				LOGGER.debug("得到验证码信息");
			}
			// 生成图片
			int width = 65;
			int height = 30;
			String path = "src/main/resources/valcodeImg/valcode.jpg";
			System.out.println("图片路径:" + path);
			File file = new File(path);
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			ImageIO.write(image, "jpg", file);
			LOGGER.debug("生成验证码图片成功!");
			// 写入图片
			os = new FileOutputStream(file);
			response.getEntity().writeTo(os);
			ImageIO.write(image, "jpg", os);
			LOGGER.debug("写入验证码图片成功!");
			String vcode = getVcode();
			Map<String, String> cookieAndVcode = new HashMap<String, String>();
			cookieAndVcode.put("cookie", cookie);
			cookieAndVcode.put("vcode", vcode);
			return cookieAndVcode;
		}catch(Exception e) {
			LOGGER.error("获得Cookie || 验证码失败");
		}finally {
			try {
				os.close(); 
				response.close();
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 解析验证码图片
	 * @return
	 */
	public String getVcode() {
		String vcode=null;
		try {
			AipOcr client = new AipOcr(BaiDuAPI.APP_ID, BaiDuAPI.API_KEY, BaiDuAPI.SECRET_KEY);
			String path = "src/main/resources/valcodeImg/valcode.jpg";
			System.out.println(path);
			String image = path;
			JSONObject res = client.basicAccurateGeneral(image, null);
			JSONArray json = res.getJSONArray("words_result");
			vcode = (String) json.getJSONObject(0).get("words");
			System.out.println("v:" + vcode);
		}catch(Exception e) {
			throw new RuntimeException("解析验证码图片失败！");
		}
		return vcode;
	}
	/**
	 * 登录
	 */
	public void sendRequestAndGetResponses() {
		CloseableHttpClient httpclient = null;
		FileWriter fw = null;
		try {
			Map<String, String> cookieAndVcode = sendRequestAndGetResponse();
			String cookie = cookieAndVcode.get("cookie");
			String vcode = cookieAndVcode.get("vcode").trim();

			String[] str = cookie.split(";");
			finalCookie = str[0].split("=");
			CookieStore cookieStore = new BasicCookieStore();
			BasicClientCookie cookies = new BasicClientCookie(finalCookie[0], finalCookie[1]);
			cookies.setVersion(0);
			cookies.setDomain("www.tianmasport.com");
			cookies.setPath("/ms");
			cookieStore.addCookie(cookies);
			httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

			List<NameValuePair> form = new ArrayList<NameValuePair>();
			form.add(new BasicNameValuePair("nickName", nickName));
			form.add(new BasicNameValuePair("pwd", pwd));
			form.add(new BasicNameValuePair("verifyCode", vcode));
			form.add(new BasicNameValuePair("remember", remember));

			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(form, "UTF-8");
			HttpPost login = new HttpPost("http://www.tianmasport.com/ms/beLogin.do");
			login.setEntity(entity);
			login.setHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36");
			login.setHeader("Referer", "http://www.tianmasport.com/ms/login.shtml");
			CloseableHttpResponse loginResponse = httpclient.execute(login);
			String loginInfo = EntityUtils.toString(loginResponse.getEntity());
			// System.out.println(loginInfo);
			// 进入主页，不然无权限
			HttpGet main = new HttpGet("http://www.tianmasport.com/ms/main.shtml");
			CloseableHttpResponse mainResponse = httpclient.execute(main);

			if (loginInfo.contains("false")) {
				//睡眠2---3秒
				int random = (int)Math.random()*1000+2000;
				Thread.sleep(random);
				LOGGER.debug("Session过期,用户重新登录");
				sendRequestAndGetResponses();
			} else {
				LOGGER.info("登录成功");
				// 保存cookie到文件中
				String path = "src/main/resources/cookie.txt";
				fw = new FileWriter(path);
				fw.write(finalCookie[0] + "=" + finalCookie[1]);
				fw.flush();
			}
		} catch (Exception e) {
			LOGGER.error("Register:"+e.getMessage());
		} finally {
			try {
				if (httpclient != null) {
					httpclient.close();
				}
				if (fw != null) {
					fw.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
