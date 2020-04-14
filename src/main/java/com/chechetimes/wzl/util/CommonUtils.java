package com.chechetimes.wzl.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.regex.Pattern;

public class CommonUtils {
    private static Pattern NUMBER_PATTERN = Pattern.compile("^[-\\+]?[\\d]*$");
    private static Pattern CAR_NUMBER_PATTERN = Pattern.compile("([翼京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{1}(([A-Z0-9]{4,7})|([DF]{1}[A-HJ-NP-Z0-9]{1}[\\d]{4})|([\\d]{5}[DF]{1})|([A-HJ-NP-Z0-9]{4,6}[警挂学港澳]{1})|([\\d]{4}领)))|([特]?WJ[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]?[\\d]{4}[TDSHBXJ]?)|([\\d]{6}使)|([VKHBSLJNGCE]{1}[A-HJ-Z]{1}[\\d]{5})");
    private static final Pattern IDENTITY_PATTERN = Pattern.compile("^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$");
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static ConcurrentHashMap.KeySetView<Integer, Boolean> cacheKey = ConcurrentHashMap.newKeySet();
    private final static Pattern MAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");

    public static boolean isEmpty(Object o) {
        return isEmpty(o, false);
    }

    public static boolean isEmpty(Object o, boolean depth) {
        if (o == null) {
            return true;
        }

        if (o instanceof String) {
            return "".equals(o);
        }

        if (o.getClass().isArray()) {
            if (((Object[]) o).length == 0) {
                return true;
            } else if (depth) {
                boolean flag = true;
                for (Object it : (Object[]) o) {
                    flag = isEmpty(it);
                    if (!flag) {
                        break;
                    }
                }
                return flag;
            }
        }

        if (o instanceof Collection) {
            if (((Collection) o).isEmpty()) {
                return true;
            } else if (depth) {
                boolean flag = true;
                for (Object it : (Collection) o) {
                    flag = isEmpty(it);
                    if (!flag) {
                        break;
                    }
                }
                return flag;
            }
        }

        if (o instanceof Map) {
            if (((Map) o).isEmpty()) {
                return true;
            } else if (depth) {
                boolean flag = true;
                for (Object it : ((Map) o).values()) {
                    flag = isEmpty(it);
                    if (!flag) {
                        break;
                    }
                }
                return flag;
            }
        }

        return false;
    }

    public static boolean isInteger(String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        } else {
            return NUMBER_PATTERN.matcher(str).matches();
        }
    }

    public static boolean isCarNumberNo(String carNumber) {
        if (StringUtils.isEmpty(carNumber)) {
            return false;
        } else {
            return CAR_NUMBER_PATTERN.matcher(carNumber).matches();
        }
    }

    public static String getBatchNo() {
        return LocalDateTime.now().format(formatter) + String.format("%04d", RandomUtils.nextInt(0, 10000));
    }

    public static boolean throttleById(Object key, Function func) {
        Assert.notNull(key, "key不能为空");
        int keyCode = key.hashCode();
        if (!cacheKey.contains(keyCode)) {
            synchronized (cacheKey) {
                if (cacheKey.contains(keyCode)) {
                    return false;
                }
                cacheKey.add(keyCode);
            }

            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            func.apply(null);
            cacheKey.remove(keyCode);
            return true;
        }
        return false;
    }

    public static String getReplace(String str, int num) {
        if (StringUtils.isBlank(str) || "null".equals(str)) {
            return "";
        }
        return String.format("%-" + str.length() + "s", str.replaceAll("(.{" + num + "}).+", "$1")).replace(" ", "*");
    }

    /**
     * 将bean转换成map
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String, Object> transBean2Map(Object obj) throws Exception {
        if (obj == null) {
            return new HashMap<>(0);
        }
        Map<String, Object> map = new HashMap<>();
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            Object value = property.getReadMethod().invoke(obj);
            map.put(key, value);
        }
        return map;
    }

    /**
     * map转换成bean
     *
     * @param map
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T transMap2Bean(Map<String, ?> map, Class<T> clazz) throws Exception {
        T t = clazz.newInstance();
        BeanUtils.populate(t, map);
        return t;
    }

    /**
     * 生成UUID
     *
     * @return
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
    }

    /**
     * 将list分成每count个一组
     *
     * @param list
     * @param count
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> getSubListByCount(final List<T> list, int count) {
        int listSize = list.size();
        int resultListSize = listSize % count == 0 ? listSize / count : listSize / count + 1;
        List<List<T>> resultList = new ArrayList<>(resultListSize);
        for (int i = 0; i < listSize; i += count) {
            if (i + count > listSize) {
                count = listSize - i;
            }
            resultList.add(list.subList(i, i + count));
        }
        return resultList;
    }

    /**
     * 将Date转换为LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * 将Date转换为LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate toLocalDate(Date date) {
        return toLocalDateTime(date).toLocalDate();
    }

    /**
     * 将Date转换为LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate toLocalDate(String date) {
        return LocalDate.parse(date);
    }

    public static boolean isIdentity(String str) {
        return str != null && IDENTITY_PATTERN.matcher(str).matches();
    }

    /**
     * 判断Map里面是否包含某个key
     * @param map
     * @param key
     * @return
     */
    public static boolean isExistFromMap(Map<String, Object> map, String key) {
        assert map != null && map.size() > 0;
        boolean exist = false;
        if (map.get(key) != null && StringUtils.isNotEmpty(map.get(key).toString())) {
            exist = true;
        }
        return exist;
    }

    /**
     * 是否为邮件类型
     * @param mail
     * @return
     */
    public static boolean isMail(String mail) {
        return mail != null && MAIL_PATTERN.matcher(mail).matches();
    }

}
