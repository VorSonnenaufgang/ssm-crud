package ink.vor.crud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author muquanrui
 * @date 31/03/2022 21:25
 */
@Controller
public class IndexController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/list")
    public String list() {
        return "list";
    }
}
