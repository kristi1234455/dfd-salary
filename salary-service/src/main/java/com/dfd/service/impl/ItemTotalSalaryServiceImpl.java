package com.dfd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.constant.GlobalConstant;
import com.dfd.dto.*;
import com.dfd.entity.ItemTotalSalary;
import com.dfd.entity.TotalSalary;
import com.dfd.entity.User;
import com.dfd.mapper.ItemTotalSalaryMapper;
import com.dfd.mapper.TotalSalaryMapper;
import com.dfd.service.ItemService;
import com.dfd.service.ItemTotalSalaryService;
import com.dfd.service.MemberService;
import com.dfd.service.TotalSalaryService;
import com.dfd.service.util.UserRequest;
import com.dfd.utils.BusinessException;
import com.dfd.utils.PageResult;
import com.dfd.utils.UUIDUtil;
import com.dfd.vo.SpecialInfoVO;
import com.dfd.vo.TotalSalaryInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yy
 * @date 2023/6/9 17:30
 */
@Service
public class ItemTotalSalaryServiceImpl extends ServiceImpl<ItemTotalSalaryMapper, ItemTotalSalary> implements ItemTotalSalaryService {


}












