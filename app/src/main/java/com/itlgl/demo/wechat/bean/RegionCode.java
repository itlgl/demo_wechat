package com.itlgl.demo.wechat.bean;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

public class RegionCode implements Serializable, Comparable<RegionCode> {
    String name;
    String sortName;
    int code;

    public RegionCode() {}

    public RegionCode(String name, String sortName, int code) {
        this.name = name;
        this.sortName = sortName;
        this.code = code;
    }

    public RegionCode setName(String name) {
        this.name = name;
        return this;
    }

    public RegionCode setSortName(String sortName) {
        this.sortName = sortName;
        return this;
    }

    public RegionCode setCode(int code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getSortName() {
        return sortName;
    }

    public int getCode() {
        return code;
    }

    @Override
    public int compareTo(@NonNull RegionCode o) {
        if(this.sortName != null && o.sortName == null) {
            return 1;
        } else if(this.sortName == null && o.sortName != null) {
            return -1;
        } else if(this.sortName == null && o.sortName == null) {
            return 0;
        } else {
            return this.sortName.compareTo(o.sortName);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegionCode that = (RegionCode) o;

        if (code != that.code) return false;
        if (!name.equals(that.name)) return false;
        return sortName.equals(that.sortName);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + sortName.hashCode();
        result = 31 * result + code;
        return result;
    }

    @Override
    public String toString() {
        return "RegionCode{" +
                "name='" + name + '\'' +
                ", sortName='" + sortName + '\'' +
                ", code=" + code +
                '}';
    }
}
