package com.yanzhen.service;

import com.google.common.base.Splitter;
import com.yanzhen.entity.Question;
import com.yanzhen.entity.QuestionOpt;
import com.yanzhen.mapper.QuestionMapper;
import com.yanzhen.mapper.QuestionOptMapper;
import com.yanzhen.utils.BeanMapUtils;
import com.yanzhen.utils.MapParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    
    @Autowired
    private QuestionOptMapper questionOptMapper;

    //新增问题：分更新插入和首次插入
    public int create(Question pi) {
        int flag = 0;
        //ID就是表中的id值，判断问题是否是第一次插入
        if (pi.getId() != null) {
            flag = this.update(pi);
            //删除问题的对应选项
            questionOptMapper.delete(MapParameter.getInstance().add("questionId", pi.getId()).getMap());
        } else {
            flag = questionMapper.create(pi);
        }
        if (flag > 0) {
            //获取问题的选项
            List<QuestionOpt> options = pi.getOptions();
            int i = 0;
            for (QuestionOpt option : options) {
                option.setSurveyId(pi.getSurveyId());
                option.setQuestionId(pi.getId());
                option.setOrderby(++i);
                questionOptMapper.create(option);
            }
        }
        return pi.getId();
    }

    /**
     * 批量删除
     * @param ids
     * @return  删除的问题个数
     */
    public int deleteBatch(String ids) {
        int flag = 0;
        List<String> list = Splitter.on(",").splitToList(ids);
        for (String s : list) {
            questionMapper.delete(MapParameter.getInstance().addId(Integer.parseInt(s)).getMap());
            questionOptMapper.delete(MapParameter.getInstance().add("questionId", Integer.parseInt(s)).getMap());
            flag++;
        }
        return flag;
    }

    public int delete(Integer id) {
        return questionMapper.delete(MapParameter.getInstance().addId(id).getMap());
    }

    //改
    public int update(Question question) {
        Map<String, Object> map = MapParameter.getInstance().put(BeanMapUtils.beanToMapForUpdate(question)).addId(question.getId()).getMap();
        return questionMapper.update(map);
    }

    /**
     * 查询对应的问题以及对应的选项
     * @param question 问题
     * @return 问题
     */
    public List<Question> query(Question question) {
        Map<String, Object> map = MapParameter.getInstance().put(BeanMapUtils.beanToMap(question)).getMap();
        //查询出当前问题
        List<Question> questionList = questionMapper.query(map);
        //查出当前问卷的所有选项：存疑，为什么不直接按照问题id查询对应的选项？
        List<QuestionOpt> optList = questionOptMapper.query(MapParameter.getInstance().add("surveyId", question.getSurveyId()).getMap());
        for (Question question1 : questionList) {
            //保存当前问题的选项
            List<QuestionOpt> options = new ArrayList<>();
            //遍历问卷的所有选项
            for (QuestionOpt questionOpt : optList) {
                if (question1.getId() == questionOpt.getQuestionId()) {
                    options.add(questionOpt);
                }
            }
            question1.setOptions(options);
        }
        return questionList;
    }

    //信息
    public Question detail(Integer id) {
        return questionMapper.detail(MapParameter.getInstance().addId(id).getMap());
    }

    //个数
    public int count(Question question) {
        Map<String, Object> map = MapParameter.getInstance().put(BeanMapUtils.beanToMap(question)).getMap();
        return questionMapper.count(map);
    }

}
