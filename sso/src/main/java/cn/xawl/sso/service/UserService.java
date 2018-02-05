package cn.xawl.sso.service;

import cn.xawl.common.service.RedisService;
import cn.xawl.sso.dao.UserMapper;
import cn.xawl.sso.pojo.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    @Autowired
    private RedisService redisService;

    @Autowired
    private UserMapper userMapper;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public Boolean check(String param, Integer type) {
        if ( type < 1 || type > 3 ) {
            return null;
        }
        User record = new User();
        switch ( type ) {
            case 1:
                record.setUsername(param);
                break;
            case 2:
                record.setPhone(param);
                break;
            case 3:
                record.setEmail(param);
                break;
            default:
                break;
        }
        return userMapper.selectOne(record) == null;
    }

    public Boolean saveUser(User user) {
        user.setId(null);
        Date date = new Date();
        user.setUpdated(date);
        user.setCreated(date);
        user.setPassword(DigestUtils.md5Hex(user.getPassword().getBytes()));
        Integer saveInt = userMapper.insert(user);
        return saveInt == 1;
    }

    public String doLogin(String username, String password) throws JsonProcessingException {
        User recode = new User();
        recode.setUsername(username);
        User user = userMapper.selectOne(recode);
        if ( user == null ) {
            return null;
        } else if ( !user.getPassword().equals(DigestUtils.md5Hex(password)) ) {
            return null;
        }
        String token = DigestUtils.md5Hex(System.currentTimeMillis() + username);
        redisService.set("TOKEN_" + token, MAPPER.writeValueAsString(user), 60 * 30);
        return token;
    }

    public User queryUserBuToken(String token) {
        String key = "TOKEN_" + token;
        String jsonData = redisService.get(key);
        if ( StringUtils.isEmpty(jsonData) ) {
            return null;
        }
        try {
            //刷新用户生存时间
            redisService.expire(key, 60 * 30);
            return MAPPER.readValue(jsonData, User.class);
        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }
}
