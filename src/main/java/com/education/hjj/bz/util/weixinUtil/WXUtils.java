package com.education.hjj.bz.util.weixinUtil;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import com.education.hjj.bz.controller.PayController;
import com.education.hjj.bz.util.weixinUtil.config.Constant;
import com.education.hjj.bz.util.weixinUtil.vo.CheckRedPackRequestPo;
import com.education.hjj.bz.util.weixinUtil.vo.PayCashPo;
import com.education.hjj.bz.util.weixinUtil.vo.RedpackRequestPo;
import com.education.hjj.bz.util.weixinUtil.vo.RedpackResponsePo;
public class WXUtils {
	
	private static Logger logger = LoggerFactory.getLogger(WXUtils.class);
   
    /**
     * 方法用途: 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序），并且生成url参数串<br>
     * 实现步骤: <br>
     *
     * @param paraMap    要排序的Map对象
     * @param urlEncode  是否需要URLENCODE
     * @param keyToLower 是否需要将Key转换为全小写 true:key 转化成小写，false:不转化
     * @return
     */
    public static String formatUrlMap(Map<String, String> paraMap, boolean urlEncode, boolean keyToLower) {
        String buff = "";
        try {
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(paraMap.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
                @Override
                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });
            // 构造URL 键值对的格式
            StringBuilder buf = new StringBuilder();
            for (Map.Entry<String, String> item : infoIds) {
                if (StringUtils.isNotBlank(item.getKey())) {
                    String key = item.getKey();
                    String val = item.getValue();
                    if (urlEncode) {
                        val = URLEncoder.encode(val, "utf-8");
                    }
                    if (keyToLower) {
                        buf.append(key.toLowerCase() + "=" + val);
                    } else {
                        buf.append(key + "=" + val);
                    }
                    buf.append("&");
                }

            }
            buff = buf.toString();
            if (buff.isEmpty() == false) {
                buff = buff.substring(0, buff.length() - 1);
            }
        } catch (Exception e) {
            return null;
        }
        return buff;
    }
    
    /**  
     * 签名字符串  
     * @param text 需要签名的字符串
     * @param key 密钥  
     * @param input_charset 编码格式
     * @return 签名结果  
     */   
    public static String sign(String text, String key, String input_charset) {   
        text = text + "&key=" + key;   
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));   
    }   
    
    /**  
     * @param content  
     * @param charset  
     * @return  
     * @throws SignatureException  
     * @throws UnsupportedEncodingException  
     */   
    public static byte[] getContentBytes(String content, String charset) {   
        if (charset == null || "".equals(charset)) {   
            return content.getBytes();   
        }   
        try {   
            return content.getBytes(charset);   
        } catch (UnsupportedEncodingException e) {   
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);   
        }   
    }   
    
    /**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Map doXMLParse(String strxml) throws Exception {
		if(null == strxml || "".equals(strxml)) {
			return null;
		}
		
		Map m = new HashMap();
		InputStream in = String2Inputstream(strxml);
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		Iterator it = list.iterator();
		while(it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List children = e.getChildren();
			if(children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = getChildrenText(children);
			}
			
			m.put(k, v);
		}
		
		//关闭流
		in.close();
		
		return m;
	}
	
	/**
	 * 获取子结点的xml
	 * @param children
	 * @return String
	 */
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if(!children.isEmpty()) {
			Iterator it = children.iterator();
			while(it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if(!list.isEmpty()) {
					sb.append(getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}
		
		return sb.toString();
	}
	
	public static InputStream String2Inputstream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}
	
	/**  
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串  
     * @param params 需要排序并参与字符拼接的参数组  
     * @return 拼接后字符串  
     */   
    public static String createLinkString(Map<String, String> params) {   
        List<String> keys = new ArrayList<String>(params.keySet());   
        Collections.sort(keys);   
        String prestr = "";   
        for (int i = 0; i < keys.size(); i++) {   
            String key = keys.get(i);   
            String value = params.get(key);   
            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符   
                prestr = prestr + key + "=" + value;   
            } else {   
                prestr = prestr + key + "=" + value + "&";   
            }   
        }   
        return prestr;   
    }   

    /**  
     * 签名字符串  
     *  @param text 需要签名的字符串
     * @param sign 签名结果  
     * @param key 密钥
     * @param input_charset 编码格式  
     * @return 签名结果  
     */   
    public static boolean verify(String text, String sign, String key, String input_charset) {   
        text = text + key;   
        String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));
        logger.info("caohuan**********mysign:" + mysign);
        logger.info("caohuan**********sign:" + sign);
        if (mysign.equals(sign)) {
        	logger.info("caohuan****************验证成功");
            return true;
		} else {
			logger.info("caohuan****************验证失败");
			return false;
        }   
    } 
    
    /**
	 * 微信支付，现金红包接口，将参数以xml格式转为流数据传递给微信的支付接口 将微信返回数据转成javabean
	 */
	public static String httpsXMLPostPay(String url, Object param, String certPath, String mchId) throws Exception {
		
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		// 读取证书
		FileInputStream instream = new FileInputStream(new File(certPath));
		try {
			
			logger.info("instream： " + instream);
			// password是商户号
			keyStore.load(instream, mchId.toCharArray());
		} catch (Exception e) {
			logger.debug(e.getMessage(), e);
			System.err.println(e);
		} finally {
			instream.close();
		}
		SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build();
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
 
		logger.info("httpclient： " + httpclient);
		
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader("Connection", "keep-alive");
			httpPost.addHeader("Accept", "*/*");
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			httpPost.addHeader("Host", "api.mch.weixin.qq.com");
			httpPost.addHeader("X-Requested-With", "XMLHttpRequest");
			httpPost.addHeader("Cache-Control", "max-age=0");
			httpPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
			// 设置超时时间
	        httpPost.setConfig(getRequestConfig());
			
			logger.info("executing request： " + httpPost.getRequestLine());
			
			String xml = "";
			
			if(Constant.CHECK_RED_PACK.equalsIgnoreCase(url)) {
				xml = XmlParseUtil.beanToXml(param, CheckRedPackRequestPo.class);
			}
			
			if(Constant.SEND_RED_PACK.equalsIgnoreCase(url)) {
				logger.info("小程序红包提现....");
				xml = XmlParseUtil.beanToXml(param, RedpackRequestPo.class);
			}
			
			if(Constant.PAY_CASH.equalsIgnoreCase(url)) {
				logger.info("提现到零钱....");
				xml = XmlParseUtil.beanToXml(param, PayCashPo.class);
			}
 
			
			logger.info("调试模式_统一下单接口请求XML数据: {}", xml);
 
			httpPost.setEntity(new ByteArrayEntity(xml.getBytes("UTF-8")));
 
			CloseableHttpResponse response = httpclient.execute(httpPost);
 
			try {
				HttpEntity entity = response.getEntity();
				System.err.println(response.getStatusLine());
				if (entity != null) {
					logger.info("Response content length: " + entity.getContentLength());
					
					String result = EntityUtils.toString(entity);
					
					logger.info(result);
					
					return result;
					
				}
				EntityUtils.consume(entity);
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
		return "";
	}

	/**
	 * 请求超时时间(毫秒) 5秒
     * 响应超时时间(毫秒) 15秒
	 * @return
	 */
	public static RequestConfig getRequestConfig() {
		
		return RequestConfig.custom().setConnectTimeout(5 * 1000).setConnectionRequestTimeout(15 * 1000).build();

	}
}
