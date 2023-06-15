package com.dfd.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.dfd.dto.UserLoginInDTO;
import com.dfd.dto.UserRegistDTO;
import com.dfd.dto.UserResetDTO;
import com.dfd.entity.User;
import com.dfd.mapper.UserMapper;
import com.dfd.org.n3r.idworker.Sid;
import com.dfd.service.UserService;
import com.dfd.utils.BusinessException;
import com.dfd.utils.CookieUtils;
import com.dfd.utils.JsonUtils;
import com.dfd.utils.MD5Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public UserMapper userMapper;

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public Boolean queryPhoneIsExist(String phone) {
        Example userExample = new Example(User.class);
        Example.Criteria userCriteria = userExample.createCriteria();
        userCriteria.andEqualTo("phone", phone);
//        User result = userMapper.selectOneByExample(userExample);
        User result = null;
        if (ObjectUtil.isNotEmpty(result)) {
            throw new BusinessException("用户名已经存在");
        }
        return result == null ? true : false;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Integer createUser(UserRegistDTO userRegistDTO, HttpServletRequest request, HttpServletResponse response) {
        boolean isExist = queryPhoneIsExist(userRegistDTO.getPhone());
        if (isExist) {
            throw new BusinessException("用户名已经存在");
        }
        if (!userRegistDTO.getPassword().equals(userRegistDTO.getConfirmPassword())) {
            throw new BusinessException("两次密码输入不一致");
        }
        User user = new User();
        BeanUtils.copyProperties(userRegistDTO,user);
        user.setUid(Sid.next());
        try {
            user.setPassword(MD5Utils.getMD5Str(userRegistDTO.getPassword()));
        } catch (Exception e) {
            throw new BusinessException(e.toString());
        }
        // 默认用户昵称同用户名
        user.setNickname(userRegistDTO.getUsername());
        // 默认头像
        user.setCreatedBy(userRegistDTO.getPhone());
        user.setUpdatedBy(userRegistDTO.getPhone());
        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());
//        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(user), true);
        // TODO 生成用户token，存入redis会话 封装到service中
        return userMapper.insert(user);
    }

    @Override
    public User loginInUser(UserLoginInDTO userLoginDTO, HttpServletRequest request, HttpServletResponse response) {
        User userResult = new User();
        try {
            userResult= queryUserForLogin(userLoginDTO.getPhone(), MD5Utils.getMD5Str(userLoginDTO.getPassword()));
        } catch (Exception e) {
            throw new BusinessException(e.toString());
        }
        if (ObjectUtil.isEmpty(userResult)) {
            throw new BusinessException("用户名或密码不正确");
        }
        // TODO 生成用户token，存入redis会话
//        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult), true);
        return userResult;
    }

    @Override
    public User logOutUser(String userId, HttpServletRequest request, HttpServletResponse response) {
        // 清除用户的相关信息的cookie
        CookieUtils.deleteCookie(request, response, "user");
        // TODO 用户退出登录，需要清空redis和cookie
        // TODO 分布式会话中需要清除用户数据
        return null;
    }

    @Override
    public Integer resetUser(UserResetDTO userResetDTO, HttpServletRequest request, HttpServletResponse response) {
        boolean isExist = queryPhoneIsExist(userResetDTO.getPhone());
        if (isExist) {
            throw new BusinessException("用户名不存在，请注册！");
        }
        User user = new User();
        BeanUtils.copyProperties(userResetDTO,user);
        try {
            user.setPassword(MD5Utils.getMD5Str(userResetDTO.getPassword()));
        } catch (Exception e) {
            throw new BusinessException(e.toString());
        }
        // 默认用户昵称同用户名
        // 默认头像
        user.setUpdatedBy(userResetDTO.getPhone());
        user.setUpdatedTime(new Date());
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(user), true);
        // TODO 生成用户token，存入redis会话 封装到service中
        return userMapper.insert(user);
    }

    /**
     * 检索用户名和密码是否匹配，用于登录
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public User queryUserForLogin(String phone, String password) {
        Example userExample = new Example(User.class);
        Example.Criteria userCriteria = userExample.createCriteria();
        userCriteria.andEqualTo("phone", phone);
        userCriteria.andEqualTo("password", password);
//        User result = userMapper.selectOneByExample(userExample);
        User result = null;
        return result;
    }
}
