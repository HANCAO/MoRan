package moran.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.dom4j.Element;

import com.thoughtworks.xstream.XStream;

import moran.po.TextMessage;

public class MessageUtil {
	
	public static final String MESSAGE_TEXT="text";
	public static final String MESSAGE_IMAGE="image";
	public static final String MESSAGE_VIDEO="video";
	public static final String MESSAGE_VOICE="voice";
	public static final String MESSAGE_LOC="loction";
	public static final String MESSAGE_LINK="link";
	public static final String MESSAGE_EVENT="event";
	public static final String MESSAGE_SUBSCRIBE="subscribe";
	public static final String MESSAGE_UNSUBSCRIBE="unsubscribe";
	public static final String MESSAGE_CLICK="click";
	public static final String MESSAGE_VIEW="view";
	
	/*xml转换为map类型*/
	public static Map<String,String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException{
		Map<String,String> map=new HashMap<String,String>();
		SAXReader reader=new SAXReader();
		
		InputStream ins=request.getInputStream();
		Document doc=reader.read(ins);
		
		Element root=doc.getRootElement();
		
		List<Element> list=root.elements();
		
		for(Element e:list) {
			map.put(e.getName(), e.getText());
		}
		ins.close();
		return map;
	}
	
	/*文本消息对象转换为xml类型*/
	public static String textMessageToxml(TextMessage textMessage) {
		XStream xstream=new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
	
	public static String initText(String toUserName,String fromUserName,String content) {
		 TextMessage text=new TextMessage();
		 text.setFromUserName(toUserName);
		 text.setToUserName(fromUserName);
		 text.setMsgType(MESSAGE_TEXT);
		 text.setCreateTime(String.valueOf(new Date().getTime()));
		 text.setContent(content);
		 return textMessageToxml(text);
	}
	
	/*主菜单*/
	public static String menuText() {
		StringBuffer sb=new StringBuffer();
		sb.append("欢迎关注\n\n");
		sb.append("1.公众号介绍\n");
		sb.append("2.技术介绍\n\n");
		sb.append("回复？调出此菜单。");
		return sb.toString();
	}
	
	public static String fristMenu() {
		StringBuffer sb=new StringBuffer();
		sb.append("微信公众号开发");
		return sb.toString();
	}
	
	public static String secondMenu() {
		StringBuffer sb=new StringBuffer();
		sb.append("Java Web + Tomcat + Servlet + natapp");
		return sb.toString();
	}
}
