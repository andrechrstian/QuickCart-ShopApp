package org.example.tokonyadia.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Hello {

    @RequestMapping(value = "mapp", method = RequestMethod.GET)
    public String hello() {
        return "Hello req mapping";
    }

    //Querry Param
    @GetMapping("hello")
    public static String hello(@RequestParam(value = "name",defaultValue = "Andre") String name) {
        return "Hello Get " + name;
    }

    //Request Body
    @PostMapping("hello")
    public String helloPost(@RequestBody String name) {
        return "Hello Post " + name;
    }

    //Path Param
    @GetMapping("hello/{name}")
    public String helloPath(@PathVariable String name) {
        return "Hello Path Parameter " + name;
    }

}
