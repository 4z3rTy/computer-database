package com.excilys.controllers;

import com.excilys.model.PdfUserDetails;
import com.excilys.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;

@SessionAttributes({ "currentUser" })
@Controller
public class Login {

	private static final Logger logger = LoggerFactory.getLogger(Login.class);

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {

		return "login";

	}

	@RequestMapping(value = "/loginFailed", method = RequestMethod.GET)
	public String loginError(Model model) {

		logger.info("Login attempt failed");

		model.addAttribute("error", "true");

		return "login";

	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(SessionStatus session) {

		SecurityContextHolder.getContext().setAuthentication(null);

		session.setComplete();

		return "redirect:/welcome";

	}

	@RequestMapping(value = "/postLogin", method = RequestMethod.POST)
	public String postLogin(Model model, HttpSession session) {

		logger.info("postLogin()");

		// read principal out of security context and set it to session

		UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder
				.getContext().getAuthentication();

		validatePrinciple(authentication.getPrincipal());

		User loggedInUser = ((PdfUserDetails) authentication.getPrincipal()).getUserDetails();

		model.addAttribute("currentUser", loggedInUser.getUsername());

		session.setAttribute("userId", loggedInUser.getId());

		return "redirect:/wallPage";

	}

	private void validatePrinciple(Object principal) {

		if (!(principal instanceof PdfUserDetails)) {

			throw new IllegalArgumentException("Principal can not be null!");

		}

	}

}