package com.sean.ttj.handlebars.helpers;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.helper.EachHelper;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tengdj on 2016/6/23.
 */
public class HandlebarHelpers {
    public static Map<String, SimpleDateFormat> sdfMap = new HashMap<>();
    static{
        sdfMap.put("gmt", new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy"));
        sdfMap.put("day", new SimpleDateFormat("yyyy-MM-dd"));
        sdfMap.put("default", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    private static final Splitter splitter = Splitter.on(",");

    public CharSequence equals(String a,String b, Options options) throws IOException {
        if (StringUtils.equals(a, b)) {
            return options.fn();
        } else {
            return options.inverse();
        }
    }

    public CharSequence contains(String str, String searchStr, Options options) throws IOException{
        if (StringUtils.contains(str, searchStr)) {
            return options.fn();
        } else {
            return options.inverse();
        }
    }

    public CharSequence isnt(String a,String b, Options options) throws IOException {
        if (!StringUtils.equals(a, b)) {
            return options.fn();
        } else {
            return options.inverse();
        }
    }


    public CharSequence gt(Object source, Options options) throws IOException {
        long _source;
        if (source instanceof Long) {
            _source = (Long) source;
        } else if (source instanceof Integer) {
            _source = (Integer) source;
        } else {
            _source = Long.parseLong((String) source);
        }

        if (_source > (Integer) options.param(0)){
            return options.fn();
        }else{
            return options.inverse();
        }
    }

    public CharSequence lt(Object source, Options options) throws IOException {
        long _source;
        if (source instanceof Long) {
            _source = (Long) source;
        } else if (source instanceof Integer) {
            _source = (Integer) source;
        } else {
            _source = Long.parseLong((String) source);
        }

        if (_source < (Integer) options.param(0)){
            return options.fn();
        }else{
            return options.inverse();
        }
    }

    public CharSequence assign(String name, Options options) throws IOException {
        CharSequence finalValue = options.apply(options.fn);
        options.context.data(name, finalValue.toString().trim());
        return null;
    }


   /* public CharSequence json(Object context, Options options) throws IOException {
        return JsonMapper.nonEmptyMapper().toJson(context);
    }*/


    public CharSequence match(String regEx, Options options) throws IOException {
        Pattern pat = Pattern.compile(regEx);
        Matcher mat = pat.matcher((String) options.param(0));
        if (mat.find())
            return options.fn();
        else
            return options.inverse();
    }


    public CharSequence mod(Integer source, Options options) throws IOException {

        if ((source + 1) % (Integer) options.param(0) == 0)
            return options.fn();
        else
            return options.inverse();
    }


    public CharSequence size(Object context, Options options) throws IOException {
        if (context == null) return "0";
        if (context instanceof Collection) return String.valueOf(((Collection) context).size());
        if (context instanceof Map) return String.valueOf(((Map) context).size());
        return "0";
    }


    public CharSequence formatDate(Object dateObj, Options options) throws IOException {
        if (dateObj == null) {
            return "";
        }
        Date date = new DateTime(dateObj).toDate();
        String format = options.param(0, "day");
        if(StringUtils.isBlank(format)){
            format = "day";
        }
        if (format.equals("ut")) {
            return Long.toString(date.getTime());
        }
        if (!sdfMap.containsKey(format)) {
            sdfMap.put(format, new SimpleDateFormat(format));
        }
        return sdfMap.get(format).format(date);
    }

    public CharSequence formatPrice(Number price, Options options) throws IOException {
        return null;
    }

    /**
     * 如果Value为空,则使用默认值
     * @param value
     * @param defaultVal
     * @param options
     * @return
     * @throws IOException
     */
    public CharSequence nullDefault(String value,String defaultVal, Options options) throws IOException {
        return StringUtils.isBlank(value)?defaultVal:value;
    }
    /**
     * 只保留圆角的部分
     */
    public CharSequence innerStyle(Object context, Options options) throws IOException {

        if (context == null) {
            return "";
        }

        StringBuilder ret = new StringBuilder();

        String[] styles = ((String) context).split(";");
        for (String style : styles) { //border-radius,border-top-left-radius,...
            String key = style.split(":")[0];
            if (key.endsWith("radius")) {
                ret.append(style).append(";");
            }
        }
        return ret;
    }

    public CharSequence cIndex(Integer context, Options options) throws IOException {
        return "" + (char) (context + 'A');
    }


    public CharSequence formatRate(Double rate, Options options) throws IOException {
        final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.###");
        return rate == null ? "" : DECIMAL_FORMAT.format(rate / 1000.0);
    }

    public CharSequence formatIntegerRate(Integer rate, Options options) throws IOException {
        final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.###");
        return rate == null ? "" : DECIMAL_FORMAT.format(rate / 1000.0);
    }

    public CharSequence of(Object source, Options options) throws IOException {
        if (source == null) {
            return options.inverse();
        }

        String _source = source.toString();
        String param = options.param(0);
        if (Strings.isNullOrEmpty(param)) {
            return options.inverse();
        }

        final List<String> targets = splitter.splitToList(param);
        if (targets.contains(_source)) {
            return options.fn();
        }
        return options.inverse();
    }


    public CharSequence add(Object source, Options options) throws IOException {
        Object param = options.param(0);

        if (source == null && param == null) {
            return "";
        }

        if (source == null) {
            return param.toString();
        }

        if (param == null) {
            return source.toString();
        }

        if (source instanceof Double) {
            Double first = (Double) source;
            Double second = (Double) param;
            return String.valueOf(first + second);
        }

        if (source instanceof Integer) {
            Integer first = (Integer) source;
            Integer second = (Integer) param;
            return String.valueOf(first + second);
        }

        if (source instanceof Long) {
            Long first = (Long) source;
            Long second = (Long) param;
            return String.valueOf(first + second);
        }

        if (source instanceof String) {   // String 约定一定可以转换成Integer
            Integer first = Integer.parseInt(source.toString());
            Integer second = Integer.parseInt(param.toString());
            return String.valueOf(first + second);
        }

        throw new IllegalStateException("incorrect.type");
    }

    /**
     * 随机获取一个List或者一个Spring中用,隔开的值
     * @param context
     * @param options
     * @return
     * @throws IOException
     */
    public CharSequence rget(Object context, Options options) throws IOException {
        Random random = new Random(System.currentTimeMillis());
        Splitter splitter = Splitter.on(",");
        List list;
        if (context instanceof List) {
            list = (List) context;
        } else {
            list = splitter.splitToList(String.valueOf(context));
        }
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(random.nextInt(list.size())).toString();
    }

    public CharSequence notin(Object context, Options options) throws IOException {
        String param = options.param(0);
        if(param!=null){
            String[] arrStr = param.split(",");
            for(String str:arrStr){
                if(context.equals(str)){
                    return options.inverse(this);

                }
            }
        }
        return options.fn(this);
    }

    public CharSequence in(Object context, Options options) throws IOException {
        String param = options.param(0);
        if(param!=null){
            String[] arrStr = param.split(",");
            for(String str:arrStr){
                if(context.equals(str)){
                    return options.fn(this);

                }
            }
        }
        return options.inverse(this);
    }

    public CharSequence is(Object a,Object b, Options options) throws IOException {
        if (StringUtils.equals(String.valueOf(a), String.valueOf(b))) {
            return options.fn();
        } else {
            return options.inverse();
        }
    }

    public CharSequence eachListinmap(Object context, Options options) throws IOException {
        Helper<Object> helper = EachHelper.INSTANCE;
        if(context instanceof Map){
            Map map = (Map)context;
            String key = (String)options.param(0);
            Object value = map.get(key);
            if(value!=null){
                return helper.apply(value, options);
            };
        }
        return options.inverse(this);
    }

    public CharSequence has(String a, Options options) throws IOException {
        if(a.indexOf(String.valueOf(options.param(0)))>-1){
            return options.fn();
        }else{
            return options.inverse(this);
        }
    }

    public CharSequence subtractDate(Object context, Options options) throws IOException {
        if(options.param(0) instanceof Date && context instanceof Date){
            Date startDate = (Date)options.param(0);
            Date endDate = (Date)context;
            long start = startDate.getTime();
            long end = endDate.getTime();
            return String.valueOf((end-start)/(1000*3600*24));
        }
        return options.fn();
    }

    public String apartPhoneNum(Object context, Options options) throws IOException {
        String num = String.valueOf(context);
        if(num.length()==11){//手机号码
            num = num.replaceAll("(\\d{3})(\\d{4})", "$1 $2 ");
        }else if (num.length()==8){
            num = num.replaceAll("(\\d{4})", "$1 ");
        }
        return num;
    }

    public String subtract30Date(Object context, Options options) throws IOException {
        if(options.param(0) instanceof Date && context instanceof Date){
            Date startDate = (Date)options.param(0);
            Date endDate = (Date)context;
            long start = startDate.getTime();
            long end = endDate.getTime();
            long a = (end-start)/(1000*3600*24);
            if(a<31){
                return "剩余（"+String.valueOf(a)+"）天";
            }else{
                return "";
            }
        }
        return "";
    }

    public String splitLast(Object context, Options options) throws IOException {
        if(context==null){
            return "";
        }
        String a = String.valueOf(context);
        String[] arr = a.split(",");
        return arr[arr.length-1];
    }

    public CharSequence before(String a,Options options) throws IOException {
        int i=0;
        int j=0;
        String[] arr = options.param(1);
        String b = options.param(0);
        for(;i<arr.length;i++){
            if(String.valueOf(a).equals(arr[i])){
                break;
            }
        }
        for(;j<arr.length;j++){
            if(String.valueOf(b).equals(arr[j])){
                break;
            }
        }
        if(j>i)
            return options.inverse(this);
        else
            return options.fn(this);
    }


    public CharSequence _mod(Object a,Object b,Object c,Options options) throws IOException{
        return options.fn();
    }

//  "PHY-MAN-0022":"icon-mobile"
//  "PHY-MAN-0022":"icon-mobile2"
//  "INT-MAN-0010":"icon-broadband"
//  "OTH-MAN-0034":"icon-itv"


    public String getProdIcon(Object a,Options options){
        Map<String,String> map = new HashMap<String,String>();
        map.put("PHY-MAN-0022", "icon-mobile");
        map.put("INT-MAN-0010", "icon-broadband");
        map.put("OTH-MAN-0034", "icon-itv");
        String iconCls = map.get(String.valueOf(a));
        return Strings.isNullOrEmpty(iconCls)?"icon-mobile":iconCls;

    }
    public String subStringDateOther(Object a,Options options){
        if(a.toString().length()>10){
            return  a.toString().substring(0,10);
        }
        return a.toString();

    }
}
