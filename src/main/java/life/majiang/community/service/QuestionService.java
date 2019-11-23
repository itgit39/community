package life.majiang.community.service;

import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.mapper.QuesstionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuesstionMapper quesstionMapper;

    public PaginationDTO list(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        List<QuestionDTO> questionDTOS = new ArrayList<>();

        if (page < 1) {
            page = 1;
        }

        Integer offset = size * (page - 1);

        List<Question> questions = quesstionMapper.list(offset, size);


        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);//将question的所有属性拷贝到questionDto
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOS);
        //拿到总数
        Integer totalCount = quesstionMapper.count();
        paginationDTO.setPagination(totalCount, page, size);
        return paginationDTO;
    }
}
