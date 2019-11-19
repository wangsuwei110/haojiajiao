package com.education.hjj.bz.util.weixinUtil;

import java.io.IOException;
import java.io.Writer;
import java.util.regex.Pattern;

import org.dom4j.io.XMLWriter;
import org.xml.sax.SAXException;

public class CDataContentHandler extends XMLWriter {

	public CDataContentHandler(Writer writer) throws IOException {
		super(writer);
	}

	// see http://www.w3.org/TR/xml/#syntax
	private static final Pattern XML_CHARS = Pattern.compile("[<>&]");

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
//		boolean useCData = XML_CHARS.matcher(new String(ch, start, length)).find();
		boolean useCData = true;
		
		if (useCData) {
			super.startCDATA();
		}
		
		super.characters(ch, start, length);
		
		if (useCData) {
			super.endCDATA();
		}
	}
}
