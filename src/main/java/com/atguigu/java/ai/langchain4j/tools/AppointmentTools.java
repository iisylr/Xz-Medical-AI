package com.atguigu.java.ai.langchain4j.tools;

import com.atguigu.java.ai.langchain4j.entity.Appointment;
import com.atguigu.java.ai.langchain4j.service.AppointmentService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppointmentTools {

    @Autowired
    private AppointmentService appointmentService;

    @Tool(
            name = "预约挂号",
            value = "根据参数，先执行工具方法 queryDepartment 查询是否可预约，并直接给用户回答是否可预约。" +
                    "若可以预约，则让用户确认所有预约信息，用户确认后再进行预约。" +
                    "如果用户没有提供具体的医生姓名，请从向量存储中找到以为医生。")
    public String bookAppointment(Appointment appointment) {
        Appointment existent = appointmentService.getOne(appointment);

        if (existent == null) {
            // 防止大模型幻觉设置预约id
            appointment.setId(null);

            if (appointmentService.save(appointment)) {
                return "预约成功";
            } else {
                return "预约失败";
            }
        }

        return "您在相同的科室和时间已有预约。";
    }

    @Tool(name = "取消预约", value = "根据参数，首先查询预约是否存在，若存在则取消预约，若不存在则提示用户没有预约记录")
    public String cancelAppointment(Appointment appointment) {
        Appointment existent = appointmentService.getOne(appointment);

        if (existent == null) {
            return "您没有预约记录，请核对预约科室和时间。";
        } else {
            if (appointmentService.removeById(existent.getId())) {
                return "取消预约成功";
            } else {
                return "取消预约失败";
            }
        }
    }

    @Tool(name = "查询是否有号源", value = "根据科室、日期、时间、医师姓名查询是否有号源，并返回给用户")
    public boolean queryDepartment(
            @P(value = "科室") String department,
            @P(value = "日期") String date,
            @P(value = "时间（上午/下午）") String time,
            @P(value = "医师姓名", required = false) String doctorName)
    {
        // 维护医生的排班信息：
        // 如果没有指定医生名字，则根据其他条件查询是否有可以预约的医生（有返回true，否则返回false）；
        // 如果指定了医生名字，则判断医生是否有排班（没有排版返回false）
        // 如果有排班，则判断医生排班时间段是否已约满（约满返回false，有空闲时间返回true）

        return true;
    }
}
