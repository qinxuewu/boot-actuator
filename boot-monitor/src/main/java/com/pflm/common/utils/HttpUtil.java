package com.pflm.common.utils;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class HttpUtil {

    // 日志
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    /**
     * 定义编码格式 UTF-8
     */
    public static final String URL_PARAM_DECODECHARSET_UTF8 = "UTF-8";

    /**
     * 定义编码格式 GBK
     */
    public static final String URL_PARAM_DECODECHARSET_GBK = "GBK";

    private static final String URL_PARAM_CONNECT_FLAG = "&";

    private static final String EMPTY = "";

    private static MultiThreadedHttpConnectionManager connectionManager = null;

    private static int connectionTimeOut = 25000;

    private static int socketTimeOut = 25000;

    private static int maxConnectionPerHost = 20;

    private static int maxTotalConnections = 20;

    private static HttpClient client;

    static {
        connectionManager = new MultiThreadedHttpConnectionManager();
        connectionManager.getParams().setConnectionTimeout(connectionTimeOut);
        connectionManager.getParams().setSoTimeout(socketTimeOut);
        connectionManager.getParams().setDefaultMaxConnectionsPerHost(maxConnectionPerHost);
        connectionManager.getParams().setMaxTotalConnections(maxTotalConnections);
        client = new HttpClient(connectionManager);
    }

    /**
     * POST方式提交数据
     *
     * @param url    待请求的URL
     * @param params 要提交的数据
     * @param enc    编码
     * @return 响应结果
     * @throws IOException IO异常
     */
    public static String URLPost(String url, Map<String, Object> params, String enc) {

        String response = EMPTY;
        PostMethod postMethod = null;
        try {
            postMethod = new PostMethod(url);
            postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);
            //将表单的值放入postMethod中
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                Object value = params.get(key);
                postMethod.addParameter(key, String.valueOf(value));
            }
            //执行postMethod
            int statusCode = client.executeMethod(postMethod);
            if (statusCode == HttpStatus.SC_OK) {
                response = postMethod.getResponseBodyAsString();
            } else {
                logger.error("响应状态码 = " + postMethod.getStatusCode());
            }
        } catch (HttpException e) {
            logger.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("发生网络异常", e);
            e.printStackTrace();
        } finally {
            if (postMethod != null) {
                postMethod.releaseConnection();
                postMethod = null;
            }
        }

        return response;
    }

    /**
     * GET方式提交数据
     *
     * @param url    待请求的URL
     * @param params 要提交的数据
     * @param enc    编码
     * @return 响应结果
     * @throws IOException IO异常
     */
    public static String URLGet(String url, Map<String, String> params, String enc) {

        String response = EMPTY;
        GetMethod getMethod = null;
        StringBuffer strtTotalURL = new StringBuffer(EMPTY);

        if (strtTotalURL.indexOf("?") == -1) {
            strtTotalURL.append(url).append("?").append(getUrl(params, enc));
        } else {
            strtTotalURL.append(url).append("&").append(getUrl(params, enc));
        }
//        logger.debug("GET请求URL = \n" + strtTotalURL.toString());

        try {
            getMethod = new GetMethod(strtTotalURL.toString());
            getMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);
            //执行getMethod
            int statusCode = client.executeMethod(getMethod);
            if (statusCode == HttpStatus.SC_OK) {
            	InputStream inputStream = getMethod.getResponseBodyAsStream();
            	BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            	StringBuffer result = new StringBuffer();  
            	String str= "";  
            	while((str = br.readLine()) != null){  
            		result .append(str );  
            	}  
                response = result.toString();
                inputStream.close();
                br.close();
            } else {
                logger.debug("响应状态码 = " + getMethod.getStatusCode());
            }
        } catch (HttpException e) {
            logger.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("发生网络异常", e);
            e.printStackTrace();
        } finally {
            if (getMethod != null) {
                getMethod.releaseConnection();
                getMethod = null;
            }
        }

        return response;
    }


    /**
     * GET方式提交数据
     *
     * @param url    待请求的URL
     * @return 响应结果
     * @throws IOException IO异常
     */
    public static String URLGet(String url) {
        String urlStr=url.replaceAll(" ","%20");
        String response = EMPTY;
        GetMethod getMethod = null;
//        logger.debug("GET请求URL = \n" + urlStr);
        try {
            getMethod = new GetMethod(urlStr);
            getMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            //执行getMethod
            int statusCode = client.executeMethod(getMethod);
            if (statusCode == HttpStatus.SC_OK) {
            	InputStream inputStream = getMethod.getResponseBodyAsStream();
            	BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
            	StringBuffer result = new StringBuffer();  
            	String str= "";  
            	while((str = br.readLine()) != null){  
            		result .append(str );  
            	}  
                response = result.toString();
                inputStream.close();
                br.close();
            } else {
//                logger.debug("响应状态码 = " + getMethod.getStatusCode());
            }
        } catch (HttpException e) {
            logger.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("发生网络异常", e);
            e.printStackTrace();
        } finally {
            if (getMethod != null) {
                getMethod.releaseConnection();
                getMethod = null;
            }
        }

        return response;
    }
    /**
     * 据Map生成URL字符串
     *
     * @param map      Map
     * @param valueEnc URL编码
     * @return URL
     */
    private static String getUrl(Map<String, String> map, String valueEnc) {

        if (null == map || map.keySet().size() == 0) {
            return (EMPTY);
        }
        StringBuffer url = new StringBuffer();
        Set<String> keys = map.keySet();
        for (Iterator<String> it = keys.iterator(); it.hasNext(); ) {
            String key = it.next();
            if (map.containsKey(key)) {
                String val = map.get(key);
                String str = val != null ? val : EMPTY;
                try {
                    str = URLEncoder.encode(str, valueEnc);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                url.append(key).append("=").append(str).append(URL_PARAM_CONNECT_FLAG);
            }
        }
        String strURL = EMPTY;
        strURL = url.toString();
        if (URL_PARAM_CONNECT_FLAG.equals(EMPTY + strURL.charAt(strURL.length() - 1))) {
            strURL = strURL.substring(0, strURL.length() - 1);
        }

        return (strURL);
    }




    /**
     * 解析URL的端口和ip
     *
     * @param URL
     * @return
     */
    public static Object[] getIpPortFormURL(String URL) {
        Object[] ip_port = new Object[2];
        try {
            java.net.URL url = new java.net.URL(URL);
            ip_port[0] = url.getHost();
            ip_port[1] = url.getPort() != -1 ? url.getPort() : 80;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ip_port;
    }

    /**
     * 微信多媒体文件上传
     * @param url  访问url
     * @param access_token
     * @param type 文件类型(临时素材需要制定媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）)
     * @param file 文件对象
     * @return
     */
    public static String uploadImage(String url, String access_token, String type, File file){
        HttpClient client = new HttpClient();
        String uploadurl =url.replaceAll("ACCESS_TOKEN",access_token).replaceAll("TYPE",type);
        PostMethod post = new PostMethod(uploadurl);
        post.setRequestHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:30.0) Gecko/20100101 Firefox/30.0");
        post.setRequestHeader("Host", "file.api.weixin.qq.com");
        post.setRequestHeader("Connection", "Keep-Alive");
        post.setRequestHeader("Cache-Control", "no-cache");
        String responseContent="";
        try {
            if (file != null && file.exists()) {
                FilePart filepart = new FilePart("media", file, "image/jpeg", "UTF-8");
                Part[] parts = new Part[] { filepart };
                MultipartRequestEntity entity = new MultipartRequestEntity(parts, post.getParams());
                post.setRequestEntity(entity);
                int status = client.executeMethod(post);
                if (status == org.apache.http.HttpStatus.SC_OK) {
                     responseContent = post.getResponseBodyAsString();
                }else {
                    logger.error("响应状态码 = " + post.getStatusCode());
                }
            }
        }catch (HttpException e) {
            logger.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("发生网络异常", e);
            e.printStackTrace();
        }finally {
            if (post != null) {
                post.releaseConnection();
                post = null;
            }
        }
        return  responseContent;
    }



}
