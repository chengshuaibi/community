package cheng.community.service;


import cheng.community.dto.PaginationDTO;
import cheng.community.dto.QuestionDTO;
import cheng.community.mapper.QuestionMapper;
import cheng.community.mapper.UserMapper;
import cheng.community.model.Question;
import cheng.community.model.QuestionExample;
import cheng.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cheng
 * @date 2019/10/7 0007 上午 11:56
 */
@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    private Integer offset;

    public PaginationDTO list(Integer page, Integer size){
        PaginationDTO paginationDTO=new PaginationDTO();
        Integer totalCount =(int)questionMapper.countByExample(new QuestionExample());
        Integer totalPage= totalCount%size == 0 ? (totalCount/size ) : (totalCount/size +1);
        if (page<1){
            page=1;
        }

        if (page>totalPage){
            page=totalPage;
        }
        paginationDTO.setPagination(totalPage,page);
        //分页功能
        Integer offset=size*(page-1);
        //分页查询
        List<Question> questions=questionMapper.selectByExampleWithBLOBsWithRowbounds(new QuestionExample(),new RowBounds(offset,size));
        List<QuestionDTO>questionDTOList=new ArrayList<>();

        for (Question question :questions ) {
             User user= userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        /*
        *  paginationDTO写入questions注入到index中
        * */
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }

    /*分页操作*/

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO=new PaginationDTO();
        QuestionExample questionExample=new QuestionExample();
        questionExample.createCriteria().
               andCreatorEqualTo(userId);
        Integer totalCount =(int)questionMapper.countByExample(questionExample);
        Integer totalPage= totalCount%size == 0 ? (totalCount/size ) : (totalCount/size +1);
        if (page<1){
            page=1;
        }
        if (page>totalPage){
            page=totalPage;
        }
        paginationDTO.setPagination(totalPage,page);
        //分页功能
        if (page<=0){
            offset=0;}else {
            offset = size * (page - 1);
        }
        QuestionExample example=new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        List<Question> questions=questionMapper.selectByExampleWithBLOBsWithRowbounds(example,new RowBounds(offset,size));
        List<QuestionDTO>questionDTOList=new ArrayList<>();
        for (Question question :questions ) {
            User user= userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        /*
         *  paginationDTO写入questions注入到index中
         * */
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }

    //按照id查询问题
    public QuestionDTO getById(Integer id) {
        Question question  = questionMapper.selectByPrimaryKey(id);
        QuestionDTO questionDTO=new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user= userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    //创建或者更新问题
    public void createOrUpdate(Question question) {
        if(question.getId()==null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insertSelective(question);
        }else{
            //更新
            Question updateQuestion=new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example=new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());
            questionMapper.updateByExampleSelective(updateQuestion,example);
        }
    }
}
