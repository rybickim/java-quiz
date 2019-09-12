package com.rybickim.javaquiz.controller;

import com.rybickim.javaquiz.domain.Questions;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface HomeRestController {

    byte[] getDiagram(@PathVariable long diagramId);

}
