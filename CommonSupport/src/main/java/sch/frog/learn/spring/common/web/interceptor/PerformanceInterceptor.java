package sch.frog.learn.spring.common.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 性能监控拦截器
 */
@Slf4j
public class PerformanceInterceptor implements HandlerInterceptor {

    private ThreadLocal<StopWatch> stopWatchThreadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        StopWatch sw = new StopWatch();
        stopWatchThreadLocal.set(sw);
        sw.start();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        stopWatchThreadLocal.get().stop();
        stopWatchThreadLocal.get().start();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        StopWatch sw = stopWatchThreadLocal.get();
        sw.stop();

        String method = handler.getClass().getSimpleName();
        if(handler instanceof HandlerMethod){
            String beanType = ((HandlerMethod) handler).getBeanType().getName();
            String methodName = ((HandlerMethod) handler).getMethod().getName();
            method = beanType + "." + methodName;
        }

        log.info("{};{};{};{};{}ms;{}ms;{}ms",
                request.getRequestURI(),    // 请求路径
                method, // 请求方法
                response.getStatus(), ex == null ? "-" : ex.getClass().getSimpleName(), // 异常
                sw.getTotalTimeMillis(),    // 总耗时
                sw.getTotalTimeMillis() - sw.getLastTaskTimeMillis(),   // postHandle时间
                sw.getLastTaskTimeMillis());    // controller处理时间

        stopWatchThreadLocal.remove();
    }
}
