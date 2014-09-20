package me.loki2302;

import me.loki2302.web.annotations.Controller;
import me.loki2302.web.annotations.PathParam;
import me.loki2302.web.annotations.QueryParam;
import me.loki2302.web.annotations.RequestMapping;

@Controller
@RequestMapping("/api")
public class MyController {
    @RequestMapping("1")
    public String actionOne(
            @PathParam("x") int x,
            @QueryParam("y") String a) {

        return String.format("%d %s", x, a);
    }

    @RequestMapping("addNumbers")
    public int addNumbers(
            @PathParam("x") int x,
            @PathParam("y") int y) {

        return x + y;
    }
}
