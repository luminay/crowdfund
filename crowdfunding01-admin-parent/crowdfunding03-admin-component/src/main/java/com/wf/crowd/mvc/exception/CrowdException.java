package com.wf.crowd.mvc.exception;

import com.google.gson.Gson;
import com.wf.crowd.exception.AccessForbiddenException;
import com.wf.crowd.exception.LoginAcctAlreadyInUseException;
import com.wf.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import com.wf.crowd.exception.LoginFailedException;
import com.wf.crowd.util.CrowdUtil;
import com.wf.crowd.util.ResultEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ClassName: CroedException
 * Package: com.wf.crowd.mvc.exception
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/7/9 17:53
 * @Version 1.0
 */
@ControllerAdvice
public class CrowdException {

    @ExceptionHandler(LoginAcctAlreadyInUseException.class)
    public ModelAndView resolveLoginAcctAlreadyInUseException(Exception exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName="admin-user-add";
        return commonResolve(exception,viewName,request,response);
    }

    @ExceptionHandler(value = {AccessForbiddenException.class,LoginFailedException.class})
    public ModelAndView resolveLoginFailedAndAccessForbiddenException(Exception exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName="admin-login";
        return commonResolve(exception,viewName,request,response);
    }

    @ExceptionHandler(value = {RuntimeException.class, LoginAcctAlreadyInUseForUpdateException.class})
    public ModelAndView resolveRuntimeException(Exception exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName="system-error";
        return commonResolve(exception,viewName,request,response);
    }

    private  ModelAndView commonResolve(Exception exception, String viewName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean requestType = CrowdUtil.judgeRequestType(request);
        if(requestType){
            ResultEntity<Object> failed = ResultEntity.failed(exception.getMessage());
            Gson gson=new Gson();
            String json = gson.toJson(failed);
            response.getWriter().write(json);
            return null;
        }
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("exception",exception.getMessage());
        modelAndView.setViewName(viewName);
        return modelAndView;
    }
}
