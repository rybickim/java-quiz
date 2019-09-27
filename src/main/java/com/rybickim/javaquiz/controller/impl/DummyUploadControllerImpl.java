package com.rybickim.javaquiz.controller.impl;

import com.rybickim.javaquiz.controller.DummyUploadController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DummyUploadControllerImpl implements DummyUploadController {

    private static final Logger logger = LoggerFactory.getLogger(DummyUploadControllerImpl.class);

    @GetMapping("/dummyUpload")
    @Override
    public String dummyUploadPage(Model dataModel) {
        logger.debug("homePage()");

        return "dummyUpload";
    }
}
