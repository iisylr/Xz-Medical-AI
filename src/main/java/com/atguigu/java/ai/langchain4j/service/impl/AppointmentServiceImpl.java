package com.atguigu.java.ai.langchain4j.service.impl;

import com.atguigu.java.ai.langchain4j.entity.Appointment;
import com.atguigu.java.ai.langchain4j.mapper.AppointmentMapper;
import com.atguigu.java.ai.langchain4j.service.AppointmentService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements AppointmentService
{

    /**
     * 检查预约是否存在
     *
     * @param appointment 预约信息
     * @return 预约信息或null
     */
    public Appointment getOne(Appointment appointment) {
        LambdaQueryWrapper<Appointment> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Appointment::getUsername, appointment.getUsername())
                .eq(Appointment::getIdCard, appointment.getIdCard())
                .eq(Appointment::getDepartment, appointment.getDepartment())
                .eq(Appointment::getDate, appointment.getDate())
                .eq(Appointment::getTime, appointment.getTime());

        return baseMapper.selectOne(queryWrapper);
    }
}
