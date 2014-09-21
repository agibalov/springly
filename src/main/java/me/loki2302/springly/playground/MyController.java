package me.loki2302.springly.playground;

import me.loki2302.springly.playground.web.annotations.Controller;
import me.loki2302.springly.playground.web.annotations.PathParam;
import me.loki2302.springly.playground.web.annotations.QueryParam;
import me.loki2302.springly.playground.web.annotations.RequestMapping;

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
