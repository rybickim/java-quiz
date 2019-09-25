package com.rybickim.javaquiz.controller.impl;

import com.rybickim.javaquiz.controller.HomeRestController;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.ServletContextResource;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class HomeRestControllerImpl implements HomeRestController {

    private static final Logger logger = LoggerFactory.getLogger(HomeRestControllerImpl.class);

    private ServletContext servletContext;

    @Autowired
    public HomeRestControllerImpl(ServletContext servletContext) {
        logger.debug("HomeRestControllerImpl(): " + servletContext);
        this.servletContext = servletContext;
    }

    @GetMapping("/getText")
    public String getText(){
        return "Hello world";
    }

    @GetMapping(value = "/getImage", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage() throws IOException {
        InputStream in = getClass().getResourceAsStream("/static/img/concurrenthashmap.png");
        return IOUtils.toByteArray(in);
    }

    @GetMapping(value = "/getDiagram")
    @Override
    public ResponseEntity<Resource> getDiagram(){
        logger.debug("getDiagram() from HomeRestControllerImpl, servletContext: " + servletContext.toString());

        final HttpHeaders headers = new HttpHeaders();
        String path = "/static/img/concurrenthashmap.png";
        Resource resource = new ServletContextResource(servletContext, path);

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);

    }
}
