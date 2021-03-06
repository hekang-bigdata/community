package com.hk.community.community.controller;

import com.hk.community.community.cache.TagCache;
import com.hk.community.community.dto.QuestionDTO;
import com.hk.community.community.mapper.QuestionMapper;
import com.hk.community.community.mapper.UserMapper;
import com.hk.community.community.model.Question;
import com.hk.community.community.model.User;
import com.hk.community.community.service.QuestionService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jws.WebParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 作者: 何康先生
 * 时间: 2020-03-19 22:18
 * 描述:
 **/
@Log4j2
@Controller
public class PublishController {


    @Autowired
    private QuestionService questionService;
    @GetMapping("/publish/{id}")
    public  String edit(@PathVariable(name="id") Long id,
                        Model  model){
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());

        model.addAttribute("tags",TagCache.get());
        return "publish";
    }
    @GetMapping("/publish")
    public String publish(Model  model) {
        model.addAttribute("tags",TagCache.get());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "tag",required = false) String tag,
            @RequestParam(value = "id",required = false) Long  id,
            HttpServletRequest request,
            Model  model
    ) {
        model.addAttribute("tags",TagCache.get());
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if(title==null||title==""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(description==null||description==""){
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }

        if(tag==null||tag==""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }

        log.info("title:"+title);
        log.info("description:"+description);
        log.info("tag:"+tag);
        String invalid = TagCache.filterInvalid(tag);

        if(StringUtils.isNotBlank(invalid)){
            model.addAttribute("error","输入非法标签"+invalid);
            return "publish";
        }

        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
