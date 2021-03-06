package com.hk.community.community.exception;

public enum CustomizeErrorCode implements ICustoizeErrorCode {


    QUESTON_NOT_FOUND("你找的问题不在了要不要换个试试", 2001),
    TARGET_PARAM_NOT_FOUND("未选中任何问题或评论进行回复", 2002),
    NOT_LOGIN("当前操作需要登录,请登录后重试", 2003),
    SYS_ERROR("服务冒烟了，要不你稍后再试试", 2004),
    TYPE_PARAM_WRONG("评论类型错误或不存在", 2005),
    COMMENT_NOT_FOUND("回复的评论不存在", 2006),
    CONTENT_IS_EMPTY("输入内容不能为空", 2007),
    READ_NOTIFICATION_FAIL("读取的不是当前用户信息", 2008),
    NOTIFICATION_NOT_FOUND("消息貌似不翼而飞了", 2009);
    private String message;
    private Integer code;


    CustomizeErrorCode(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
