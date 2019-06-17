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
 * Fragment切面
 */
@Aspect
public class FragmentAspect {

    /**
     * Fragment生命周期切点
     */
    @Pointcut("execution(* android.app.Fragment.on*(..))")
    public void onLifecycle() {

    }

    /**
     * v4的Fragment生命周期切点
     */
    @Pointcut("execution(* android.support.v4.app.Fragment.on*(..))")
    public void onLifecycleV4() {

    }

    /**
     * Fragment切面打印日志
     */
    @Around("onLifecycle() || onLifecycleV4()")
    public Object fragmentAround(ProceedingJoinPoint point) {
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

        if ("android.support.v4.app.Fragment".equals(declaringTypeName)
                || "android.arch.lifecycle.ReportFragment".equals(declaringTypeName)) {
            return;
        }

        String[] fragmentFilters = LmLog.getConfig().getFragmentFilters();
        if (fragmentFilters != null) {
            for (int i = 0; i < fragmentFilters.length; i++) {
                if (declaringTypeName.equals(fragmentFilters[i])) {
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
        LmLog.log(priority, error, "FRAGMENT_ASPECT", logContent.toString());
    }

}
