package com.lm.log.aop;

import com.lm.log.LmLog;
import com.lm.log.Priority;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.concurrent.TimeUnit;

/**
 * BroadcastReceiver切面
 */
@Aspect
public class BroadcastReceiverAspect {

    /**
     * onReceive方法切点
     */
    @Pointcut("execution(* android.content.BroadcastReceiver.onReceive(..))")
    void onReceive() {

    }


    @Around("onReceive()")
    public Object broadcastReceiverAround(ProceedingJoinPoint point) {
        Throwable error = null;

        Object result = null;

        long startNanos = System.nanoTime();
        try {
            result = point.proceed(point.getArgs());
        } catch (Throwable throwable) {
            error = throwable;
        }
        long stopNanos = System.nanoTime();
        long lengthMillis = TimeUnit.NANOSECONDS.toMillis(stopNanos - startNanos);

        autoLog(point, error, result, lengthMillis);

        return result;
    }

    private void autoLog(ProceedingJoinPoint point, Throwable error, Object result, long lengthMillis) {

        if (!LmLog.getConfig().isEnableActivityAspect()) {
            return;
        }

        String declaringTypeName = point.getSignature().getDeclaringTypeName();

        String[] broadcastReceiverFilters = LmLog.getConfig().getBroadcastReceiverFilters();
        if (broadcastReceiverFilters != null) {
            for (int i = 0; i < broadcastReceiverFilters.length; i++) {
                if (declaringTypeName.equals(broadcastReceiverFilters[i])) {
                    return;
                }
            }
        }

        StringBuilder logContent = new StringBuilder();

        logContent.append(point.getSourceLocation()).append(" [").append(lengthMillis).append("ms]").append('\n');

        CodeSignature codeSignature = (CodeSignature) point.getSignature();
        String methodName = codeSignature.getName();
        String[] argKeys = codeSignature.getParameterNames();
        Object[] argValues = point.getArgs();

        logContent.append(methodName).append('(');
        for (int i = 0; i < argKeys.length; i++) {
            if (i > 0) logContent.append(", ");
            logContent.append(argKeys[i]).append("=").append(argValues[i]);
        }
        logContent.append(")\n");

        boolean hasReturnType = point.getSignature() instanceof MethodSignature && ((MethodSignature) point.getSignature()).getReturnType() != void.class;
        if (hasReturnType) {
            logContent.append("return ").append(result);
        }

        Priority priority;
        if (error == null) {
            priority = Priority.VERBOSE;
        } else {
            priority = Priority.ERROR;
        }
        LmLog.log(priority, error, "BROADCAST_ASPECT", logContent.toString());
    }

}
