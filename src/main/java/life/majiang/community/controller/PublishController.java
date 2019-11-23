package life.majiang.community.controller;

import life.majiang.community.mapper.QuesstionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    QuesstionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model) {

        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        if (title == null || title == "") {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description == null || description == "") {
            model.addAttribute("error", "内容补充不能为空");
            return "publish";
        }
        if (tag == null || tag == "") {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        User user = null;
        Cookie[] cookies = request.getCookies();//获取response中返回的cookie
        for (Cookie cookie : cookies) {//遍历cookies
            if (cookie.getName().equals("token")) {//键为token时
                String token = cookie.getValue();//取值
                user = userMapper.findByToken(token);//查询
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                }
                break;
            }
        }

        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        if (user != null) {
            Question question = new Question();
            question.setTitle(title);
            question.setDescription(description);
            question.setCreator(user.getId());
            question.setTag(tag);
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
            model.addAttribute("success", "发布成功");
            return "redirect:";
        }
        return "publish";
    }
}
