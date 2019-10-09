package cheng.community.service;


import cheng.community.dto.PaginationDTO;
import cheng.community.dto.QuestionDTO;
import cheng.community.mapper.QuestionMapper;
import cheng.community.mapper.UserMapper;
import cheng.community.model.Question;
import cheng.community.model.User;
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
        Integer totalCount = questionMapper.count();
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
        List<Question> questions=questionMapper.list(offset,size);
        List<QuestionDTO>questionDTOList=new ArrayList<>();

        for (Question question :questions ) {
             User user= userMapper.findById(question.getCreator());
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

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO=new PaginationDTO();
        Integer totalCount = questionMapper.countById(userId);
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
        List<Question> questions=questionMapper.listByUserId(userId,offset,size);
        List<QuestionDTO>questionDTOList=new ArrayList<>();
        for (Question question :questions ) {
            User user= userMapper.findById(question.getCreator());
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

    public QuestionDTO getById(Integer id) {
        Question question  = questionMapper.getById(id);
        QuestionDTO questionDTO=new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        return questionDTO;
    }
}
