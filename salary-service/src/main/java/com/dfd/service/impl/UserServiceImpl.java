package com.dfd.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.constant.GlobalConstant;
import com.dfd.constant.LoginConstant;
import com.dfd.dto.UserLoginInDTO;
import com.dfd.dto.UserRegistDTO;
import com.dfd.dto.UserResetDTO;
import com.dfd.entity.Attendance;
import com.dfd.entity.Item;
import com.dfd.entity.User;
import com.dfd.mapper.UserMapper;
import com.dfd.org.n3r.idworker.Sid;
import com.dfd.service.UserService;
import com.dfd.utils.*;
import com.dfd.vo.ItemInfoVO;
import com.dfd.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static com.dfd.utils.TokenUtil.token;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    public UserMapper userMapper;

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public Boolean queryNumberIsExist(String number) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<User>();
        userLambdaQueryWrapper.eq(StringUtils.isNotBlank(number),User::getNumber, number);
        User result = userMapper.selectOne(userLambdaQueryWrapper);
        if (ObjectUtil.isNotEmpty(result)) {
            throw new BusinessException("该用户已经存在");
        }
        return result != null ? true : false;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Integer createUser(UserRegistDTO userRegistDTO, HttpServletRequest request, HttpServletResponse response) {
        boolean isExist = queryNumberIsExist(userRegistDTO.getNumber());
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
        user.setCreatedBy(userRegistDTO.getNumber());
        user.setUpdatedBy(userRegistDTO.getNumber());
        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());
        CookieUtils.setCookie(request, response, LoginConstant.CURRENT_USER, JSON.toJSONString(user), true);
        return userMapper.insert(user);
    }

    @Override
    public UserVO loginInUser(UserLoginInDTO userLoginDTO, HttpServletRequest request, HttpServletResponse response) {
        User user = new User();
        try {
            user= queryUserForLogin(userLoginDTO.getNumber(), MD5Utils.getMD5Str(userLoginDTO.getPassword()));
        } catch (Exception e) {
            throw new BusinessException(e.toString());
        }
        if (ObjectUtil.isEmpty(user)) {
            throw new BusinessException("用户名或密码不正确");
        }
        String token = TokenUtil.token(user.getNumber(),user.getPassword());
        UserVO userVO = UserVO.builder()
                        .user(user)
                        .token(token).build();
        CookieUtils.setCookie(request, response, user.getId().toString(), token, true);
        request.setAttribute(LoginConstant.CURRENT_USER, JSON.toJSONString(user));
        return userVO;
    }

    @Override
    public User logOutUser(String userId, HttpServletRequest request, HttpServletResponse response) {
        User user = baseMapper.selectById(userId);
        if(ObjectUtil.isEmpty(user)){
            throw new BusinessException("用户id不存在，登录用户错误！");
        }
        // 清除用户的相关信息的cookie
        CookieUtils.deleteCookie(request, response, userId);
        request.setAttribute(LoginConstant.CURRENT_USER, null);
        return null;
    }

    @Override
    public Integer resetUser(UserResetDTO userResetDTO, HttpServletRequest request, HttpServletResponse response) {
        boolean isExist = queryNumberIsExist(userResetDTO.getNumber());
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
        user.setUpdatedBy(userResetDTO.getNumber());
        user.setUpdatedTime(new Date());
        return userMapper.insert(user);
    }

    @Override
    public User selectByNumber(String number) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(number), User:: getPhone, number)
                .eq(User::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        return baseMapper.selectOne(queryWrapper);
    }

    /**
     * 检索用户名和密码是否匹配，用于登录
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public User queryUserForLogin(String number, String password) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(number), User:: getNumber, number)
                .eq(StringUtils.isNotEmpty(password), User:: getPassword, password)
                .eq(User::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        User result = baseMapper.selectOne(queryWrapper);
        return result;
    }
}
