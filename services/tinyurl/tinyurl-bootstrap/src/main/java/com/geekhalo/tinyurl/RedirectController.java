package com.geekhalo.tinyurl;

import com.geekhalo.tinyurl.app.TinyUrlQueryApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


@Controller
public class RedirectController {
    @Autowired
    private TinyUrlQueryApplicationService queryApplicationService;

    @Value("${url404:}")
    private String urlFor404;

    @GetMapping("s/{code}")
    public ModelAndView redirect(@PathVariable String code){
        return this.queryApplicationService.accessByCode(code)
                .filter(tinyUrl -> tinyUrl.canAccess())
                .map(tinyUrl -> tinyUrl.getUrl())
                .map(url -> createRedirect(url))
                .orElse(createRedirect(urlFor404));
    }

    ModelAndView createRedirect(String url){
        RedirectView view = new RedirectView(url);
        return new ModelAndView(view);
    }
}
