package com.education.hjj.bz.enums;

/**
 * Copyright: Copyright (c) 2018
 *
 * @Description: token状态枚举类
 * @version: v1.0.0
 * @author: 汝毅
 * @date: 2018/11/7 13:40
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2018/11/7     汝毅           v1.0.0               修改原因
 */
public enum TokenState {
    /**
     * 过期
     */
    EXPIRED("EXPIRED"),
    /**
     * 无效(token不合法)
     */
    INVALID("INVALID"),
    /**
     * 有效的
     */
    VALID("VALID");

    private String state;

    TokenState(String state) {
        this.state = state;
    }


    /**
     * 根据状态字符串获取token状态枚举对象
     * @param tokenState
     * @return
     */
    public static TokenState getTokenState(String tokenState){
        TokenState[] states=TokenState.values();
        TokenState ts=null;
        for (TokenState state : states) {
            if(state.toString().equals(tokenState)){
                ts=state;
                break;
            }
        }

        return ts;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
