package com.education.hjj.bz.util.weixinUtil;
import org.springframework.util.ObjectUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
public class ObjectToMapUntils {
    public static Map<String, String> objectToMap(Object obj) throws Exception {
        if (obj == null)
            return null;

        Map<String, String> map = new HashMap<String, String>();

        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object value = getter != null ? getter.invoke(obj) : null;
            //此处需去掉空字段
            if (!ObjectUtils.isEmpty(value)) {
                map.put(key, String.valueOf(value));
            }
        }

        return map;
    }
}
