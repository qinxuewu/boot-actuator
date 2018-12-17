package com.pflm.common.utils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 把字节数B转化为KB、MB、GB的方法
 * @author qinxuewu
 * @version 1.00
 * @time 22/11/2018下午 2:54
 */
public class ByteConvKbUtils {

    public static String getPrintSize(long size) {
        //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (size < 1024) {
            return String.valueOf(size) + "B";
        } else {
            size = size / 1024;
        }
        //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        //因为还没有到达要使用另一个单位的时候
        //接下去以此类推
        if (size < 1024) {
            return String.valueOf(size) + "KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            //因为如果以MB为单位的话，要保留最后1位小数，
            //因此，把此数乘以100之后再取余
            size = size * 100;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "MB";
        } else {
            //否则如果要以GB为单位的，先除于1024再作同样的处理
            size = size * 100 / 1024;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "GB";
        }
    }

    public static String getMB(String k){
    	double m = Double.valueOf(k) /1024.0;
    	return String.valueOf(Math.round(m)); 
    }
    private static BigDecimal kb=new BigDecimal("1000");
    private static BigDecimal kib=new BigDecimal("1024");
    private static BigDecimal base=new BigDecimal("1");
    private static List<BigDecimal> kbValue=new ArrayList<BigDecimal>();
    private static List<BigDecimal> kibValue=new ArrayList<BigDecimal>();
    private static String DEFAULT_FORMAT="%.5f";

    public static enum Units{
        B(0,false),
        KB(1,false),
        KiB(1,true),
        MB(2,false),
        MiB(2,true),
        GB(3,false),
        GiB(3,true),
        TB(4,false),
        TiB(4,true),
        PB(5,false),
        PiB(5,true),
        EB(6,false),
        EiB(6,true),
        ZB(7,false),
        ZiB(7,true),
        YB(8,false),
        YiB(8,true),
        BB(9,false),
        BiB(9,true),
        NB(10,false),
        NiB(10,true),
        DB(11,false),
        DiB(11,true);
        private int index;
        private boolean isKib;
        private Units(int index, boolean isKib){
            this.index=index;
            this.isKib=isKib;
        }
        public int value(){
            return this.index;
        }

        public boolean isKib(){
            return this.isKib;
        }
        public boolean equals(Units other){
            return this.value()==other.value();
        }
    }

    private ByteConvKbUtils(){}
    static{
        init();
    };

    private static void init(){
        for(Units units:Units.values()){
            if(units.equals(Units.B)){
                kbValue.add(units.value(), base);
                kibValue.add(units.value(),base);
            }else if(units.isKib()){
                kibValue.add(units.value(),kibValue.get(units.value()-1).multiply(kib));
            }else{
                kbValue.add(units.value(), kbValue.get(units.value()-1).multiply(kb));
            }
        }
    }

    public static String convert(int value,Units unit){
        return convert(value,unit,DEFAULT_FORMAT);
    }
    public static String convert(int value,Units unit,String format){
        return convert(new BigDecimal(String.valueOf(value)),unit,format);
    }

    public static String convert(long value,Units unit){
        return convert(value,unit,DEFAULT_FORMAT);
    }
    public static String convert(long value,Units unit,String format){
        return convert(new BigDecimal(String.valueOf(value)),unit,format);
    }

    public static String convert(BigDecimal value,Units unit){
        return convert(value,unit,DEFAULT_FORMAT);
    }
    public static String convert(BigDecimal value,Units unit,String format){
        return doConvert(value,unit,format);
    }

    private static String doConvert(BigDecimal value,Units unit,String format){
        if(unit.equals(Units.B)){
            return value.toString().split("\\.",2)[0]+unit;
        }else{
            BigDecimal divisor= unit.isKib()?kibValue.get(unit.value()):kbValue.get(unit.value());
            return String.format(format,value.divide(divisor).doubleValue())+unit;
        }
    }

    public static double convertDouble(BigDecimal value,Units unit){
        return doConvert(value,unit);
    }
    public static long convertLong(String value,Units unit){
    	BigDecimal b=new BigDecimal(value);
        return doConvert(b,unit);
    }
    private static long  doConvert(BigDecimal value,Units unit){
        if(unit.equals(Units.B)){
            return value.longValue();
        }else{
            BigDecimal divisor= unit.isKib()?kibValue.get(unit.value()):kbValue.get(unit.value());
            return value.divide(divisor).longValue();
        }
    }
    public static void main(String[] args) {


     System.err.println(getMB("8704.0"));



    }
}
