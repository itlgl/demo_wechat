package com.itlgl.demo.wechat.service.areacode.bean;

import android.support.annotation.NonNull;

public class AreaCode implements Comparable<AreaCode> {
    /**
     * ex:86
     */
    public int areaCode;
    /**
     * ex:中国
     */
    public String area;
    /**
     * ex:ZHONGGUO
     */
    public String areaPinyin;

    public AreaCode() {}

    public AreaCode(int areaCode, String area, String areaPinyin) {
        this.areaCode = areaCode;
        this.area = area;
        this.areaPinyin = areaPinyin;
    }

    @Override
    public int compareTo(@NonNull AreaCode areaCode) {
        if(areaPinyin == null && areaCode.areaPinyin != null) {
            return -1;
        } else if(areaPinyin != null && areaCode.areaPinyin == null) {
            return 1;
        } else if(areaPinyin == null && areaCode.areaPinyin == null) {
            return 0;
        } else {
            return areaPinyin.compareTo(areaCode.areaPinyin);
        }
    }
}
