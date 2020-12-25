package com.moxi.mogublog.admin.dubbo.service;

import com.moxi.mogublog.commons.api.admin.IHelloService;
import org.apache.dubbo.config.annotation.Service;

/**
 * @Author tiansc
 * @Description TODO
 * @Classname IHelloServiceImpl
 * @Date 2020/12/25 14:42
 */
@Service(version = "1.0.0")
public class IHelloServiceImpl implements IHelloService {
    @Override
    public String hello(String name) {
        System.out.println("dubbo调用成功");
        return "dubbo调用成功";
    }
}
