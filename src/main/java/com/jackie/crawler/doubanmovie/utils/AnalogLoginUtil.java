package com.jackie.crawler.doubanmovie.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.*;

/**
 * 自动登录
 *
 * @author Administrator
 */
public class AnalogLoginUtil {

    private static HttpClient httpClient = new DefaultHttpClient();

    /**
     * 登录豆瓣
     */
    public static void loginDouban() {
        String redir = "https://www.douban.com/people/161719114/";
        String login_src = "https://accounts.douban.com/login";
        String form_email = "tqvivo@gmail.com";
        String form_password = "tang37";
        String captcha_id = getImgID();
        String login = "登录";
        String captcha_solution = "";
        System.out.println("请输入验证码：");

        BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
        try {
            captcha_solution = buff.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //构建参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("redir", redir));
        list.add(new BasicNameValuePair("form_email", form_email));
        list.add(new BasicNameValuePair("form_password", form_password));
        list.add(new BasicNameValuePair("captcha-solution", captcha_solution));
        list.add(new BasicNameValuePair("captcha-id", captcha_id));
        list.add(new BasicNameValuePair("login", login));
        HttpPost httpPost = new HttpPost(login_src);
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "utf-8");
            System.out.println(result);

            HttpGet httpGet = new HttpGet(redir);
            HttpResponse response1 = httpClient.execute(httpGet);
            HttpEntity entity1 = response1.getEntity();
            String result1 = EntityUtils.toString(entity1, "utf-8");
            System.out.println(result1);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取验证码图片“token”值
     *
     * @return token
     */
    private static String getImgID() {
        String src = "https://www.douban.com/j/misc/captcha";
        HttpGet httpGet = new HttpGet(src);
        String token = "";
        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity, "utf-8");
            Map<String, String> mapList = getResultList(content);
            token = mapList.get("token");
            String url = "https:" + mapList.get("url");
            downImg(url);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }

    /**
     * 用JSON 把数据格式化，并生成迭代器，放入Map中返回
     *
     * @param content 请求验证码时服务器返回的数据
     * @return Map集合
     */
    public static Map<String, String> getResultList(String content) {
        Map<String, String> map = new HashMap<>();
        try {
            JSONObject jo = new JSONObject(content);
            Iterator it = jo.keys();
            String key = "";
            String value = "";
            while (it.hasNext()) {
                key = (String)it.next();
                if ("r".equals(key)) {
                    value = jo.getBoolean(key) + "";
                } else {
                    value = jo.getString(key);
                }
                map.put(key, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 此方法是下载验证码图片到本地
     *
     * @param src 给个验证图片完整的地址
     */
    private static void downImg(String src) {
        File fileDir = new File("E:\\document\\crawler\\douban");
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        File file = new File("E:\\document\\crawler\\douban\\jackie.png");
        if (file.exists()) {
            file.delete();
        }
        InputStream input = null;
        FileOutputStream out = null;
        HttpGet httpGet = new HttpGet(src);
        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            input = entity.getContent();
            int i = -1;
            byte[] byt = new byte[1024];
            out = new FileOutputStream(file);
            while (( i = input.read(byt) ) != -1) {
                out.write(byt);
            }
            System.out.println("图片下载成功！");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AnalogLoginUtil.loginDouban();
    }
}
