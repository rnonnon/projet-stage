package fr.stage.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import fr.stage.domain.Company;
import fr.stage.domain.Computer;
import fr.stage.dto.CompanyDTO;
import fr.stage.dto.ComputerDTO;
import fr.stage.mapper.CompanyMapper;
import fr.stage.mapper.ComputerMapper;
import fr.stage.service.CompanyService;
import fr.stage.service.ComputerService;
import fr.stage.validator.ComputerCreationValidator;

/**
 * Servlet implementation class EditComputer
 */
@Controller
@RequestMapping("/editComputer")
public class EditComputer {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CompanyService companyService;

    @Autowired
    ComputerService computerService;

    @Autowired
    ComputerCreationValidator computerValidator;

    @Autowired
    ComputerMapper computerMapper;

    @Autowired
    CompanyMapper companyMapper;

    // Validator
    @Autowired
    ComputerCreationValidator computerCreationValidator;

    // To access Messages_locale.properties
    @Autowired
    ResourceBundleMessageSource messageSource;

    // To manipulate the cookie to get the locale
    @Autowired
    CookieLocaleResolver cookieLocaleResolver;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
	binder.setValidator(computerCreationValidator);
    }

    private List<CompanyDTO> getCompanies() {
	List<Company> companies = companyService.findAll();
	List<CompanyDTO> companiesList = new ArrayList<CompanyDTO>();
	for (Company comp : companies) {
	    companiesList.add(companyMapper.toCompanyDTO(comp));
	}
	return companiesList;
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String doGet(@RequestParam(value = "id", required = true) long id, ModelMap model) {
	Computer computer = computerService.find(id);
	// Create DTO from computer found / or from null
	ComputerDTO computerDTO = computerMapper.toComputerDTO(computer);
	// FATAL! User shouldn't access this page without having something to
	// modify (id error)
	if (computerDTO == null) {
	    throw new RuntimeException();
	}
	model.addAttribute("computer", computerDTO);
	model.addAttribute("companiesList", getCompanies());
	return "pages/editComputer";
    }

    @RequestMapping(method = RequestMethod.POST)
    protected ModelAndView doPost(@ModelAttribute("computer") @Valid ComputerDTO computerDTO,  
	    BindingResult result,
	    @CookieValue(value="goTo", required = false) Integer cookGoTo,
	    @CookieValue(value="search", required = false) String cookSearch,
	    @CookieValue(value="orderBy", required = false) Byte cookOrderBy) {

	if (result.hasGlobalErrors()) {
	    throw new RuntimeException();
	}
	// If no error
	else if (!result.hasErrors()) {
	    // Update in DB
	    Computer computer = computerMapper.toComputer(computerDTO);
	    computerService.update(computer);
	    // Redirect to Dashboard
	    ModelAndView mod = new ModelAndView("redirect:dashboard");
	    // Get locale to obtain the message in desired language
	    Locale loc = LocaleContextHolder.getLocale();

	    mod.addObject("successMessage",
		    messageSource.getMessage("successMessage.edited", null, loc));
	    mod.addObject("goTo",cookGoTo);
	    mod.addObject("search",cookSearch);
	    mod.addObject("orderBy",cookOrderBy);

	    return mod;
	}
	// Error but not fatal
	else {
	    ModelAndView mod = new ModelAndView("pages/editComputer");
	    mod.addObject("computer", computerDTO);
	    mod.addObject("companiesList", getCompanies());
	    return mod;
	}
    }
    @RequestMapping(method = RequestMethod.POST,value="/cancel")
    protected ModelAndView cancelEdit(@CookieValue(value="goTo", required = false) Integer cookGoTo,
	    @CookieValue(value="search", required = false) String cookSearch,
	    @CookieValue(value="orderBy", required = false) Byte cookOrderBy){

	ModelAndView mod = new ModelAndView("redirect:dashboard");
	mod.addObject("goTo",cookGoTo);
	mod.addObject("search",cookSearch);
	mod.addObject("orderBy",cookOrderBy);
	return mod;
    }
}
