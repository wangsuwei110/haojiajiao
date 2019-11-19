package com.education.hjj.bz.util.weixinUtil;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XmlParseUtil {

	/**
	 * xmlToBean
	 */
	public static Object xmlToBean(String xmlstr, Class<?> load) throws JAXBException, IOException {
		JAXBContext context = JAXBContext.newInstance(load);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		Object object = unmarshaller.unmarshal(new StringReader(xmlstr));
		return object;
	}

	/**
	 * beanToXml
	 */
	public static String beanToXml(Object obj, Class<?> load) throws JAXBException, IOException {
		JAXBContext context = JAXBContext.newInstance(load);
		Marshaller marshaller = context.createMarshaller();
		// 去报文头
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		StringWriter writer = new StringWriter();
		CDataContentHandler cc = new CDataContentHandler(writer);
		marshaller.marshal(obj, cc);
		return writer.toString();
	}
}
