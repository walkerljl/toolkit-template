package org.walkerljl.toolkit.template.handle;

import org.walkerljl.toolkit.logging.Logger;
import org.walkerljl.toolkit.standard.exception.AppException;
import org.walkerljl.toolkit.standard.exception.code.ErrorCode;
import org.walkerljl.toolkit.template.log.InvocationInfo;
import org.walkerljl.toolkit.template.log.LoggerDetailUtil;
import org.walkerljl.toolkit.template.log.LoggerDigestUtil;
import org.walkerljl.toolkit.template.log.LoggerUtil;

/**
 * AbstractHandleTemplate
 *
 * @author xingxun
 * @Date 2017/11/19
 */
public abstract class AbstractHandleTemplate {

    /**
     * 记录日志
     *
     * @param invocationInfo 调用信息
     * @param <RESULT>
     */
    protected <PARAM, RESULT> void doLog(InvocationInfo<PARAM, RESULT> invocationInfo) {
        if (invocationInfo == null) {
            return;
        }
        //记录摘要日志
        LoggerDigestUtil.logDigest(invocationInfo, getDigestLogger());
        //记录详细日志
        LoggerDetailUtil.logDetail(invocationInfo, getDetailLogger());

        //记录异常日志
        Logger errorLogger = getErrorLogger();
        if (errorLogger == null) {
            return;
        }
        Throwable e = invocationInfo.getThrowable();
        boolean isRecordErrorLog = (e != null && (!(e instanceof AppException) || !(((AppException) e).getCode() instanceof ErrorCode)));
        if (isRecordErrorLog) {
            LoggerUtil.error(errorLogger, e);
        }
    }

    /**
     * 跟踪异常时，是否重新抛出异常
     *
     * @return
     */
    protected boolean canRethrowException() {
        return true;
    }

    /**
     * 获取记录摘要的日志对象
     *
     * @return
     */
    protected abstract Logger getDigestLogger();

    /**
     * 获取记录详情的日志对象
     *
     * @return
     */
    protected abstract Logger getDetailLogger();

    /**
     * 获取记录错误的日志对象
     *
     * @return
     */
    protected abstract Logger getErrorLogger();
}
