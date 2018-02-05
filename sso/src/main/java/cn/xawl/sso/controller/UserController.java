package cn.xawl.sso.controller;

import cn.xawl.common.CookieUtils;
import cn.xawl.sso.pojo.User;
import cn.xawl.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping( "/user" )
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping( "/login" )
    private String toLogin() {
        return "login";
    }

    @GetMapping( "/register" )
    private String toRegister() {
        return "register";
    }

    private static final String LOGIN_COOKIE = "LOGIN_COOKIE";

    /**
     * 检查数据可用
     *
     * @param param
     * @param type
     * @return
     */
    @GetMapping( "/check/{param}/{type}" )
    public ResponseEntity<Boolean> check(@PathVariable( "param" ) String param, @PathVariable( "type" ) Integer type) {
        try {
            System.out.println(param + ":" + type);
            Boolean b = userService.check(param, type);
            if ( null == b ) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            return ResponseEntity.ok(!b);
        } catch ( Exception e ) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 注册
     *
     * @param user
     * @param bindingResult
     * @return
     */
    @PostMapping( value = "doRegister" )
    @ResponseBody
    public Map<String, Object> doRegister(@Valid User user, BindingResult bindingResult) {
        try {
            Map<String, Object> result = new HashMap<String, Object>();
            if ( bindingResult.hasErrors() ) {
                List<String> msgs = new ArrayList<String>();
                List<ObjectError> objectErrors = bindingResult.getAllErrors();
                objectErrors.stream().forEach((objectError) -> {
                    String msg = objectError.getDefaultMessage();
                    msgs.add(msg);
                });
                result.put("status", 400);
                result.put("data", StringUtils.join(msgs, "|"));
            }
            Boolean b = userService.saveUser(user);
            if ( b ) {
                result.put("status", 200);
            } else {
                result.put("status", 200);
                result.put("data", "注册失败请重新填写数据");
            }
            return result;
        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping( "/doLogin" )
    @ResponseBody
    public Map<String, Object> doLogin(@RequestParam( "username" ) String username, @RequestParam( "password" ) String password
            , HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String token = userService.doLogin(username, password);
            if ( token == null ) {
                result.put("status", 400);
            } else {
                result.put("status", 200);
                CookieUtils.setCookie(request, response, LOGIN_COOKIE, token);
            }
            return result;
        } catch ( Exception e ) {
            e.printStackTrace();
            result.put("data", "error");
        }
        return result;
    }

    @GetMapping( "/{token}" )
    public ResponseEntity<User> queryUserBuToken(@PathVariable( "token" ) String token) {
        try {
            User user = userService.queryUserBuToken(token);
            if ( user == null ) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(user);
        }catch ( Exception e ){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
