package com.dfd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.constant.GlobalConstant;
import com.dfd.constant.LoginConstant;
import com.dfd.dto.*;
import com.dfd.entity.BidSalary;
import com.dfd.entity.User;
import com.dfd.enums.DYDResultEnum;
import com.dfd.enums.RoleEnum;
import com.dfd.mapper.UserMapper;
import com.dfd.org.n3r.idworker.Sid;
import com.dfd.service.UserService;
import com.dfd.service.util.UserRequest;
import com.dfd.utils.*;
import com.dfd.vo.BidSalaryInfoVO;
import com.dfd.vo.UserRoleVO;
import com.dfd.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

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
        user.setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
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
        if(!RoleEnum.roleExist(user.getRole()) ){
            throw new BusinessException("该用户没有权限，请联系管理员设置权限！");
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
    public void resetUser(UserResetDTO userResetDTO, HttpServletRequest request, HttpServletResponse response) {
        boolean isExist = queryNumberIsExist(userResetDTO.getNumber());
        if (!isExist) {
            throw new BusinessException("用户名不存在，请注册！");
        }
        try {
            LambdaUpdateWrapper<User> queryWrapper = new LambdaUpdateWrapper();
            queryWrapper.eq(StringUtils.isNotBlank(userResetDTO.getNumber()), User:: getNumber, userResetDTO.getNumber())
                    .eq(User::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO)
                    .set(StringUtils.isNotBlank(userResetDTO.getPassword()), User:: getPassword, MD5Utils.getMD5Str(userResetDTO.getPassword()))
                    .set(User:: getUpdatedBy, userResetDTO.getNumber())
                    .set(User:: getUpdatedTime, new Date())
                    .set(User:: getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
            this.update(queryWrapper);
        } catch (Exception e) {
            throw new BusinessException(e.toString());
        }
    }

    @Override
    public User selectByNumber(String number) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(number), User:: getNumber, number)
                .eq(User::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public PageResult<UserRoleVO> infoRole(UserRoleInfoDTO userRoleInfoDTO) {
        User currentUser = UserRequest.getCurrentUser();
        if(!currentUser.getRole().equals(RoleEnum.ROLE_ADMIN.getCode())){
            throw new BusinessException(DYDResultEnum.ERROR_ROLE.getCode(),DYDResultEnum.ERROR_ROLE.getDesc());
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotBlank(userRoleInfoDTO.getNickname()), User:: getNickname, userRoleInfoDTO.getNickname())
                .like(StringUtils.isNotBlank(userRoleInfoDTO.getNumber()), User:: getNumber, userRoleInfoDTO.getNumber())
                .eq(User::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        queryWrapper.orderByDesc(User :: getCreatedTime);
        List<User> olist = baseMapper.selectList(queryWrapper);
        List<UserRoleVO> list = convertToInfoVO(olist);
        return PageResult.infoPage(olist.size(), userRoleInfoDTO.getCurrentPage(),userRoleInfoDTO.getPageSize(),list);
    }

    private List<UserRoleVO> convertToInfoVO(List<User> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        List<UserRoleVO> result = list.stream().map(user -> {
            if(!Optional.ofNullable(user).isPresent()){
                throw new BusinessException("用户数据为空");
            }
            UserRoleVO userRoleVO = new UserRoleVO();
            BeanUtil.copyProperties(user,userRoleVO);
            return userRoleVO;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public void updateRole(UserRoleUpdateDTO userRoleUpdateDTO) {
        User currentUser = UserRequest.getCurrentUser();
        if(!currentUser.getRole().equals(RoleEnum.ROLE_ADMIN.getCode())){
            throw new BusinessException(DYDResultEnum.ERROR_ROLE.getCode(),DYDResultEnum.ERROR_ROLE.getDesc());
        }
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<User>();
        updateWrapper.eq(StringUtils.isNotBlank(userRoleUpdateDTO.getUid()), User:: getUid, userRoleUpdateDTO.getUid())
                .eq(User::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO)
                .set(StringUtils.isNotBlank(userRoleUpdateDTO.getNumber()), User:: getNumber, userRoleUpdateDTO.getNumber())
                .set(StringUtils.isNotBlank(userRoleUpdateDTO.getNickname()), User:: getNickname, userRoleUpdateDTO.getNickname())
                .set(StringUtils.isNotBlank(userRoleUpdateDTO.getRole()), User:: getRole, userRoleUpdateDTO.getRole())
                .set(User:: getUpdatedBy, currentUser.getNumber())
                .set(User:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("用户权限更新失败!");
        }
    }

    @Override
    public void deleteRole(UserRoleDeleteDTO userRoleDeleteDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<User> updateWrapper = new UpdateWrapper<User>()
                .lambda()
                .in(!CollectionUtils.isEmpty(userRoleDeleteDTO.getUids()), User:: getUid, userRoleDeleteDTO.getUids())
                .set(User:: getIsDeleted, System.currentTimeMillis())
                .set(User:: getUpdatedBy, currentUser.getNumber())
                .set(User:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("权限用户删除失败!");
        }
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
