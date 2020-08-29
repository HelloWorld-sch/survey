package com.yanzhen.service;

import com.github.pagehelper.PageHelper;
import com.google.common.base.Splitter;
import com.yanzhen.entity.AnswerOpt;
import com.yanzhen.entity.AnswerTxt;
import com.yanzhen.entity.Survey;
import com.yanzhen.mapper.AnswerOptMapper;
import com.yanzhen.mapper.AnswerTxtMapper;
import com.yanzhen.mapper.SurveyMapper;
import com.yanzhen.utils.BeanMapUtils;
import com.yanzhen.utils.MapParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SurveyService {

    @Autowired
    private SurveyMapper surveyMapper;

    @Autowired
    private AnswerTxtMapper answerTxtMapper;

    @Autowired
    private AnswerOptMapper answerOptMapper;

    //增
    public int create(Survey pi){
        return surveyMapper.create(pi);
    }

    //删
    public int deleteBatch(String ids){
        int flag = 0;
        List<String> list = Splitter.on(",").splitToList(ids);
        for (String s : list) {
            surveyMapper.delete(MapParameter.getInstance().addId(Integer.parseInt(s)).getMap());
            flag++;
        }
        return flag;
    }


    public int delete(Integer id){
        return surveyMapper.delete(MapParameter.getInstance().addId(id).getMap());
    }

    //改
    public int update(Survey survey){
        Map<String, Object> map = MapParameter.getInstance().put(BeanMapUtils.beanToMapForUpdate(survey)).addId(survey.getId()).getMap();
        return surveyMapper.update(map);
    }

    //查
    public List<Survey> query(Survey survey){
        PageHelper.startPage(survey.getPage(),survey.getLimit());
        Map<String, Object> map = MapParameter.getInstance().put(BeanMapUtils.beanToMap(survey)).getMap();
        return surveyMapper.query(map);
    }

    //查询所有
    public List<Survey> queryAll(Survey survey){
        Map<String, Object> map = MapParameter.getInstance().put(BeanMapUtils.beanToMap(survey)).getMap();
        return surveyMapper.query(map);
    }

    //查询详细
    public Survey detail(Integer id){
        return surveyMapper.detail(MapParameter.getInstance().addId(id).getMap());
    }

    //查询个数
    public int count(Survey survey){
        Map<String, Object> map = MapParameter.getInstance().put(BeanMapUtils.beanToMap(survey)).getMap();
        return surveyMapper.count(map);
    }


    //查询选择题答案
    public List<AnswerOpt> queryAnswerOpt(AnswerOpt answerOpt){
        Map<String, Object> map = MapParameter.getInstance().put(BeanMapUtils.beanToMap(answerOpt)).getMap();
        return answerOptMapper.query(map);
    }

    //更新问卷的状态
    public void updateState(){
        List<Integer> list = surveyMapper.queryByState(Survey.state_create);
        for (Integer id : list) {
            surveyMapper.update(MapParameter.getInstance().add("updateState",Survey.state_exec).add("id",id).getMap());
        }
        List<Integer> list2 = surveyMapper.queryByStateForExec(Survey.state_exec);
        for (Integer id : list2) {
            surveyMapper.update(MapParameter.getInstance().add("updateState",Survey.state_over).add("id",id).getMap());
        }
    }

    //提交答案
    public Integer submit(List<AnswerOpt> opts,List<AnswerTxt> txts){
        int flag = 0;
        for (AnswerOpt opt : opts) {
            flag = answerOptMapper.create(opt);
        }
        for (AnswerTxt txt : txts) {
            flag = answerTxtMapper.create(txt);
        }
        return flag;
    }

}
