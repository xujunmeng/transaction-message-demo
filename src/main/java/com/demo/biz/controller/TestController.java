package com.demo.biz.controller;

import com.demo.biz.BusinessService;
import com.demo.biz.TransactionProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenyin
 * @since 2019-05-10
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private BusinessService businessService;

    @RequestMapping("/mqTest")
    public String callback(String data) {
        businessService.handleReduceMoney();
        return "Ok";
    }

}
