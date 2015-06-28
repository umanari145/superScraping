/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.util;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.interceptor.AroundConstruct;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 * 記録を残すInjectするオブジェクト
 *
 * @author matsumoto
 */
public class Tracer implements Serializable {

    /**
     * ロガー
     */
    @Inject
    transient Logger logger;

    /**
     * ログ情報を集めるユーティリティ
     */
    @Inject
    LoggingUtil u;

    /**
     * コンストラクタ用のメソッド
     *
     * @param ic
     * @throws Exception
     */
    @AroundConstruct
    public void ConstructorLog(InvocationContext ic) throws Exception {
        logger.fine("◆ENTER◆" + u.ConstructorName(ic) + "【PARAM】" + u.paramList(ic));
        try {
            ic.proceed();
        } finally {
            logger.fine("◆EXIT◆" + u.ConstructorName(ic));
        }
    }

    /**
     * 通常のメソッド用のメソッド
     *
     * @param ic
     * @return
     * @throws Exception
     */
    @AroundInvoke
    public Object MethodLog(InvocationContext ic) throws Exception {
        logger.fine("■ENTER■" + u.className(ic) + "#" + u.methodName(ic) + "【PARAM】" + u.paramList(ic));
        Object result = null;
        try {
            result = ic.proceed();
            return result;
        } finally {
            logger.fine("■EXIT■" + u.className(ic) + "#" + u.methodName(ic) + "【RESULT】" + result);
        }
    }

    /**
     * メソッド開始時のログ情報の作成
     *
     * @param ic InvocationContext
     * @return ログ文字列
     * @throws Exception
     */
    public String makeStartLog(InvocationContext ic) throws Exception {
        StringBuilder startLog = new StringBuilder("");
        startLog.append("ENTER:");
        startLog.append(u.ConstructorName(ic));
        startLog.append("[PARAM]");
        startLog.append(u.paramList(ic));
        return startLog.toString();
    }

    /**
     * メソッド終了時のログ情報の作成
     *
     * @param ic InvocationContext
     * @return ログ文字列
     * @throws Exception
     */
    public String makeEndLog(InvocationContext ic) throws Exception {
        StringBuilder endLog = new StringBuilder("");
        endLog.append("EXIT:");
        endLog.append(u.ConstructorName(ic));
        return endLog.toString();
    }
}
