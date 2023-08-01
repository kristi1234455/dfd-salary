package com.dfd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.constant.GlobalConstant;
import com.dfd.entity.*;
import com.dfd.mapper.TotalSalaryItemMapper;
import com.dfd.mapper.TotalSalaryRoomMapper;
import com.dfd.service.*;
import com.dfd.vo.TotalSalaryItemInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TotalSalaryItemServiceImpl extends ServiceImpl<TotalSalaryItemMapper, TotalSalaryItem> implements TotalSalaryItemService {

    @Autowired
    private ItemMemberService itemMemberService;

    @Autowired
    private ItemSalaryService itemSalaryService;

    @Autowired
    private PerformanceSalaryService performanceSalaryService;

    @Autowired
    private DesignSalaryService designSalaryService;

    @Autowired
    private BidSalaryService bidSalaryService;


}
