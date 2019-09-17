package com.rybickim.javaquiz.controller.impl;

import com.rybickim.javaquiz.controller.HomeRestController;
import com.rybickim.javaquiz.domain.Questions;
import com.rybickim.javaquiz.service.CategoryService;
import com.rybickim.javaquiz.service.QuestionService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class HomeRestControllerImpl implements HomeRestController {

    private static final Logger logger = LoggerFactory.getLogger(HomeRestControllerImpl.class);

    private ServletContext servletContext;

    @Autowired
    public HomeRestControllerImpl(ServletContext servletContext) {
        logger.debug("HomeRestControllerImpl(): " + servletContext);
        this.servletContext = servletContext;
    }

    @GetMapping(value = "/getDiagram")
    @Override
    public void getDiagram(HttpServletResponse response) throws IOException {
        logger.debug("getDiagram() from HomeRestControllerImpl, servletContext.getContextPath().isEmpty(): " + servletContext.getContextPath().isEmpty());

        InputStream in = servletContext.getResourceAsStream("/src/main/resources/static/img/concurrenthashmap.png");
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        IOUtils.copy(in, response.getOutputStream());

//        byte[] data = new byte[0];
//        URL url = getClass().getResource("static/img/concurrenthashmap.png");
//
//        try {
//            data = IOUtils.toByteArray(url.openStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


//        String realPath = request.getSession().getServletContext().getRealPath("/");
//        realPath = realPath + "/" + diagramId;
//        Path path = Paths.get(realPath);
//        byte[] data = new byte[0];
//        try {
//            data = Files.readAllBytes(path);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        return data;
    }
}
