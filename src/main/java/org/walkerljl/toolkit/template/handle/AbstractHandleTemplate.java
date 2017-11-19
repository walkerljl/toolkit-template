package org.walkerljl.toolkit.template.handle;

import org.walkerljl.toolkit.logging.Logger;
import org.walkerljl.toolkit.standard.exception.AppException;
import org.walkerljl.toolkit.standard.exception.AppRpcException;
import org.walkerljl.toolkit.standard.exception.ErrorCode;
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

    protected  <RESULT> void assertInvocationInfo(InvocationInfo<RESULT> invocationInfo) {
        if (invocationInfo == null) {
            rethrowException(AppErrorCode.UNKOWN, "invocation info is null.");
        }
        if (!invocationInfo.isSuccess()) {
            rethrowException(AppErrorCode.UNKOWN, invocationInfo.getTraceInfo());
        }
    }

    protected <RESULT> void doLog(InvocationInfo<RESULT> invocationInfo) {
        LoggerDigestUtil.logDigest(invocationInfo, getDigestLogger());
        LoggerDetailUtil.logDetail(invocationInfo, getDetailLogger());
        Logger errorLogger = getErrorLogger();
        if (errorLogger == null) {
           return;
        }
        Throwable e = invocationInfo.getThrowable();
        if (!(e instanceof AppException) || !(((AppException)e).getCode() instanceof ErrorCode)) {
            LoggerUtil.error(errorLogger, e);
        }
    }

    protected void tryRethrowException(Throwable e) {
        if (canRethrowException()) {
            //rethrow the exception
            if (e instanceof RuntimeException) {
                rethrowException((RuntimeException) e);
            } else {
                throw new Error(e.getMessage(), e);
            }
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

    protected void rethrowException(ErrorCode errorCode, String errorMsg) {
        throw new AppRpcException(errorCode, errorMsg);
    }

    protected void rethrowException(RuntimeException runtimeException) {
        throw new AppRpcException(runtimeException);
    }

    protected abstract Logger getDigestLogger();

    protected abstract Logger getDetailLogger();

    protected abstract Logger getErrorLogger();
}
