//package me.solby.xboot.config.exception;
//
//import me.solby.itool.response.Result;
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * me.solby.xboot.config.exception
// *
// * @author majhdk
// * @date 2019-08-04
// */
//@RestController
//public class GlobalErrorHandler implements ErrorController {
//
//    @RequestMapping("/error")
//    public Result<String> handle404Error() {
//        return new Result<String>("404", "404 NOT FOUND");
//    }
//
//    @Override
//    public String getErrorPath() {
//        return null;
//    }
//}
