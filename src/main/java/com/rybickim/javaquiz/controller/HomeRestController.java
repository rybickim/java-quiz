package com.rybickim.javaquiz.controller;

import com.rybickim.javaquiz.domain.Questions;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface HomeRestController {

    ResponseEntity<Resource> getDiagram();

}
