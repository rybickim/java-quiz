package com.rybickim.javaquiz.controller;

import org.springframework.ui.Model;

public interface HomeController {

    String removeElement();
    String homePage(Model dataModel);

}
