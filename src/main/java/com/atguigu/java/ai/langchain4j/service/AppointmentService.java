package com.atguigu.java.ai.langchain4j.service;

import com.atguigu.java.ai.langchain4j.entity.Appointment;
import com.baomidou.mybatisplus.extension.service.IService;

public interface AppointmentService extends IService<Appointment> {

    /**
     * 检查预约是否存在
     * @param appointment 预约信息
     * @return 预约信息或null
     */
    Appointment getOne(Appointment appointment);
}
