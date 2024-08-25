package top.zhx47.baiduwp.common.exception;

import com.github.lianjiatech.retrofit.spring.boot.exception.RetrofitException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.zhx47.baiduwp.api.baidu.exception.BaiduPanApiExpireException;
import top.zhx47.baiduwp.api.baidu.exception.BaiduPanApiOtherException;
import top.zhx47.baiduwp.common.AuthenticationUtils;
import top.zhx47.baiduwp.common.ResponseDTO;
import top.zhx47.baiduwp.common.exception.error.ErrorCode;
import top.zhx47.baiduwp.common.service.BaiduOpenApiCustomerService;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionInterceptor {

    private final BaiduOpenApiCustomerService baiduOpenApiCustomerService;


    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseDTO<?> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        log.error("请求地址'{}',不支持'{}'请求", request.getRequestURI(), e.getMethod());
        return ResponseDTO.fail(new ApiException(ErrorCode.FAILED, e.getMethod()));
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(ApiException.class)
    public ResponseDTO<?> handleServiceException(ApiException e) {
        log.error(e.getMessage(), e);
        return ResponseDTO.fail(e, e.getPayload());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseDTO<?> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String errorMsg = String.format("请求地址'%s',发生未知异常.", request.getRequestURI());
        log.error(errorMsg, e);
        return ResponseDTO.fail(new ApiException(ErrorCode.FAILED, e.getMessage()));
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseDTO<?> handleException(Exception e, HttpServletRequest request) {
        String errorMsg = String.format("请求地址'%s',发生未知异常.", request.getRequestURI());
        log.error(errorMsg, e);
        return ResponseDTO.fail(new ApiException(ErrorCode.FAILED, e.getMessage()));
    }

    /**
     * 请求API异常
     */
    @ExceptionHandler(RetrofitException.class)
    public ResponseDTO<?> handleRetrofitException(RetrofitException e, HttpServletRequest request) {
        String errorMsg = String.format("请求地址'%s',发生未知异常.", request.getRequestURI());
        log.error(errorMsg, e);

        Throwable rootCause = e.getCause();

        // 检查原始异常是否存在
        if (rootCause != null) {
            if (rootCause instanceof BaiduPanApiExpireException) {
                baiduOpenApiCustomerService.refreshAccessToken(AuthenticationUtils.getRefreshToken());
                return ResponseDTO.fail(new ApiException(ErrorCode.Business.BAIDUPAN_AUTO_REFRESH, e.getMessage()));
            } else if (rootCause instanceof BaiduPanApiOtherException) {
                return ResponseDTO.fail(new ApiException(ErrorCode.FAILED, e.getMessage()));
            }
        }

        return ResponseDTO.fail(new ApiException(ErrorCode.FAILED, e.getMessage()));
    }

}
