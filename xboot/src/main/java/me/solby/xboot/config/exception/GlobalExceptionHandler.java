package me.solby.xboot.config.exception;

import me.solby.itool.response.Result;
import me.solby.itool.verify.ObjectUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

/**
 * me.solby.xboot.config.exception
 *
 * @author majhdk
 * @date 2019-07-16
 */
@RestControllerAdvice
public class GlobalExceptionHandler /*extends ResponseEntityExceptionHandler */{

    /**
     * 自定义异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<Result> BusinessExceptionHandler(BusinessException e) {
        return ResponseEntity.status(HttpStatus.OK).body(
                Result.failure(ObjectUtil.isEmpty(e.getCode()) ? "-1" : e.getCode(), e.getMessage())
        );
    }

//    @Override
//    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex,
//                                                               HttpHeaders headers, HttpStatus status,
//                                                               WebRequest request) {
//        return super.handleMissingPathVariable(ex, headers, status, request);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
//                                                                   HttpHeaders headers, HttpStatus status,
//                                                                   WebRequest request) {
//        return super.handleNoHandlerFoundException(ex, headers, status, request);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//                                                                  HttpHeaders headers, HttpStatus status,
//                                                                  WebRequest request) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Result.failure("400",
//                ex.getBindingResult().getFieldErrors().stream()
//                        .map(it -> it.getField() + ": " + it.getDefaultMessage())
//                        .collect(Collectors.joining()))
//        );
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
//                                                                  HttpHeaders headers, HttpStatus status,
//                                                                  WebRequest request) {
//        return super.handleHttpMessageNotReadable(ex, headers, status, request);
//    }
}
