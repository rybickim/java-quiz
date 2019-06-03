package com.rybickim.javaquiz.controller;

import org.springframework.ui.Model;

public interface HomeController {

    String postJson(Model dataModel, String data);
    String homePage(Model dataModel);

}
