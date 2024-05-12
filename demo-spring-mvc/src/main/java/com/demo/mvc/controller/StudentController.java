package com.demo.mvc.controller;

import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class StudentController {

  private List<Country> countries =
      List.of(
          new Country("USA", "US"),
          new Country("Brazil", "BR"),
          new Country("POLAND", "PL"),
          new Country("Germany", "DE"));

  @GetMapping("/show-student-form")
  public String showStudentForm(Model theModel) {
    theModel.addAttribute("student", new Student());
    theModel.addAttribute("countries", countries);

    return "student-form";
  }

  @PostMapping("/process-student")
  public String processStudentForm(
      @Valid @ModelAttribute("student") Student student,
      BindingResult bindingResult // result of validation
      ) {

    if (bindingResult.hasErrors()) {
      return "student-form";
    }
    return "student-confirmation";
  }

  @GetMapping("/leaders")
  public String leaders() {
    return "leaders";
  }

  @GetMapping("/system")
  public String system() {
    return "system";
  }

  @GetMapping("/denied-page")
  public String deniedPage() {
    return "denied-page";
  }


  // for trimming empty spaces
  @InitBinder
  public void fieldTrimBinder(WebDataBinder webDataBinder) {
    StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
    webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
  }
}
