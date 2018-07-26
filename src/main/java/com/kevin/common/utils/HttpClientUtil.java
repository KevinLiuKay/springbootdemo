package com.kevin.common.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtil {
	
	private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	
    /**
     * 发送HttpGet请求
     * @param url
     * @return
     */
    public static String sendGet(String url, String param) {
        //1.获得一个httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //2.生成一个get请求
        HttpGet httpget = new HttpGet(url + "?" + param);
        CloseableHttpResponse response = null;
        try {
            //3.执行get请求并返回结果
            response = httpclient.execute(httpget);
        } catch (IOException e1) {
            logger.debug("发送GET请求出现异常！" + e1.getMessage());
            //e1.printStackTrace();
        }
        String result = null;
        try {
            //4.处理结果，这里将结果返回为字符串
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    
    /**
     * 发送不带参数的HttpPost请求
     * @param url
     * @return
     */
    public static String sendPost(String url) {
        //1.获得一个httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //2.生成一个post请求
        HttpPost httppost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            //3.执行get请求并返回结果
            response = httpclient.execute(httppost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //4.处理结果，这里将结果返回为字符串
        HttpEntity entity = response.getEntity();
        String result = null;
        try {
            result = EntityUtils.toString(entity);
        } catch (Exception e) {
            logger.debug("发送post请求出现异常！" + e.getMessage());
            //e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 发送HttpPost请求，参数为map
     * @param url
     * @param map
     * @return
     */
    public static String sendPost(String url, Map<String, String> map) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            //给参数赋值
            formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(entity);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httppost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity entity1 = response.getEntity();
        String result = null;
        try {
            result = EntityUtils.toString(entity1);
        } catch (Exception e) {
            logger.debug("发送post请求出现异常！" + e.getMessage());
            //e.printStackTrace();
        }
        return result;
    }
	
	public static String getCsbHttp(String urlstr,String jsonstr) throws Exception {	
		String uigHttp = urlstr;
		//String str = "{\"method\":\"get_integral_next\",\"body\":{\"integralType\":\"999999\",\"integral\":\"600\"}}";
		//String str = jsonstr;
		PostMethod post = new PostMethod(uigHttp);
		//RequestEntity entity = new StringRequestEntity(str, "application/x-www-form-urlencoded", "utf-8");
		//post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		post.addParameter("param", jsonstr);
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		String result = null;
		try {
			int statusCode = httpClient.executeMethod(post);
			if (statusCode == HttpStatus.SC_OK)
				result = post.getResponseBodyAsString();
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("发生IO异常");
			
		} finally {
			post.releaseConnection();
		}
		return result;
	}
	
	public String getMarketingPost(String urlstr,String jsonstr) throws Exception {	
		PostMethod post = new PostMethod(urlstr);

		RequestEntity entity = new StringRequestEntity(jsonstr, "application/json", "utf-8");
		post.setRequestEntity(entity);
		HttpClient httpClient = new HttpClient();
		
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);
		//httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		//httpClient.getParams().setParameter("Cookie", "JSESSIONID=687e2c228c1f982f06a076119b2d5307&r=1318128534304;Path=;");
		httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT,"	Mozilla/5.0 (Windows NT 5.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1");
		String result = null;
		InputStream inputStream = null;
		InputStreamReader reader = null;
		BufferedReader br = null;
		try {
			int statusCode = httpClient.executeMethod(post);
			logger.debug("-----> statusCode:"+statusCode);
			if (statusCode == HttpStatus.SC_OK){
				inputStream = post.getResponseBodyAsStream();  
				reader = new InputStreamReader(inputStream,"UTF-8");
				br = new BufferedReader(reader);  
				StringBuffer stringBuffer = new StringBuffer();  
				String str= "";  
				while((str = br.readLine()) != null){  
					stringBuffer.append(str);  
				}
				result = stringBuffer.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("发生IO异常");
		} finally {
			if(br!=null){
				br.close();
			}
			if(reader!=null){
				reader.close();
			}
			if(inputStream!=null){
				inputStream.close();
			}
			post.releaseConnection();
		}
		return result;
	}
	
	
	public static String inputStream2String(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read()) != -1) {
			baos.write(i);
		}
		return baos.toString();
	}
}


