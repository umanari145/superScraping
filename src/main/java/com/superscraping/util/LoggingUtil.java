/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.util;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import javax.enterprise.context.Dependent;
import javax.interceptor.InvocationContext;


/**
 * ログ情報を集めるためのクラス
 *
 * @author matsumoto
 */
@Dependent
public class LoggingUtil implements Serializable {

    /**
     * クラス名を取得
     *
     * @param ic　InvokeContext
     * @return クラス名
     */
    public String className(InvocationContext ic) {
        java.lang.reflect.Method method = ic.getMethod();
        Class decClass = method.getDeclaringClass();
        return decClass.getName();
    }

    /**
     * メソッド名を取得
     *
     * @param ic　InvokeContext
     * @return メソッド名
     */
    public String methodName(InvocationContext ic) {
        java.lang.reflect.Method method = ic.getMethod();
        return method.getName();
    }

    /**
     * パラメーターリストを取得
     *
     * @param ic InvokeContext
     * @return パラメーターを文字列化
     */
    public String paramList(InvocationContext ic) {
        Object[] params = ic.getParameters();
        return Arrays.toString(params);
    }

    /**
     * コンストラクタ名を取得
     *
     * @param ic InvokeContext
     * @return コンストラクタ
     */
    public String ConstructorName(InvocationContext ic) {
        Constructor con = ic.getConstructor();
        return con.getName();
    }
}