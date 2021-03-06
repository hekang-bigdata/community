package com.hk.community.community.controller;

import com.hk.community.community.cache.HotTagCache;
import com.hk.community.community.dto.PaginationDTO;
import com.hk.community.community.dto.QuestionDTO;
import com.hk.community.community.mapper.UserMapper;
import com.hk.community.community.model.User;
import com.hk.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 作者: 何康先生
 * 时间: 2020-03-14 10:44
 * 描述:
 **/
@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @Autowired
    private HotTagCache  hotTagCache;

    @GetMapping("/")
    public String hello(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size,
                        @RequestParam(name = "search", required = false) String search,
                        @RequestParam(name="tag",required = false) String tag) {
        PaginationDTO pagination = questionService.list(search,tag, page, size);
        List<String> tags = hotTagCache.getHots();
        model.addAttribute("pagination", pagination);
        model.addAttribute("search", search);
        model.addAttribute("tags", tags);
        model.addAttribute("tag",tag);
        return "index";
    }
}
