package com.yanzhen.utils;

import com.yanzhen.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

//定时任务——更新问卷状态
@EnableScheduling
public class ScheduleTask {

    @Autowired
    private SurveyService surveyService;
    /**
     * 调查问卷状态的任务
     */
    @Scheduled(fixedRate=1000 * 60 * 60)
//    @Scheduled(cron = "* * */1 * * ?")
    public void state(){
        System.out.println("执行任务....");
        surveyService.updateState();
    }

}
