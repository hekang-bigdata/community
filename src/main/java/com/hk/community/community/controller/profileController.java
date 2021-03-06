package com.hk.community.community.controller;

import com.hk.community.community.dto.PaginationDTO;
import com.hk.community.community.mapper.UserMapper;
import com.hk.community.community.model.Notification;
import com.hk.community.community.model.User;
import com.hk.community.community.service.NotificationService;
import com.hk.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 作者: 何康先生
 * 时间: 2020-03-23 21:44
 * 描述:
 **/
@Controller
public class profileController {


    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size) {
        User user = (User)request.getSession().getAttribute("user");

        if (user == null) {
            return "redirect:/";
        }
        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
            PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);
            model.addAttribute("pagination", paginationDTO);
        } else if ("replies".equals(action)) {
            PaginationDTO paginationDTO = notificationService.list(user.getId(),page,size);
            model.addAttribute("section", "replies");
            model.addAttribute("pagination",paginationDTO);
            model.addAttribute("sectionName", "最新回复");
        }


        return "profile";
    }
}
