package com.dfd.controller;

import com.dfd.dto.ItemInfoQueryDTO;
import com.dfd.mapper.UserMapper;
import com.dfd.service.AttendanceService;
import com.dfd.service.ItemService;
import com.dfd.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * @author yy
 * @date 2023/6/8 10:22
 */
@SpringBootTest
@RunWith(SpringRunner.class)
//@AutoConfigureMockMvc
public class ItemControllerTest {

    @Resource
    private ItemService itemService;

//    @Autowired
//    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Test
    public void testInfo() throws Exception{
//        String json="{\"currentPage\":1,\n" +
//                "\"pageSize\":10}";
//        //执行一个RequestBuilder请求，会自动执行SpringMVC的流程并映射到相应的控制器执行处理；
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/item/info")
//                        .content(json.getBytes()) //传json参数
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
////                        .header("Authorization","Bearer ")
//                )
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(print());
//        ItemInfoQueryDTO param = ItemInfoQueryDTO.builder().build();
//        userService.selectByNumber("111");
//        itemService.queryItemList(param);
    }
}
