package com.materials.gpt.enums;

public enum HttpCodeEnum {

    success("M0200"),
    MO201("M0201"),
    M0202( "M0202"),
    operate("M300"),
    M0301( "M0301"),
    failed("M0500"),
    TOKEN_FAILED("M0401"),
    NOT_AUTHORIZED("M0403"),
    ACCESS_DENIED("M0600");

    private String value;

    private HttpCodeEnum(String value) { this.value = value; }
    public String getValue() { return this.value; }
    public void setValue(String value) { this.value = value; }


}
