package com.nasscom.einvoice.controller;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nasscom.einvoice.domain.Message;
import com.nasscom.einvoice.entity.Role;
import com.nasscom.einvoice.entity.User;
import com.nasscom.einvoice.filter.TokenService;
import com.nasscom.einvoice.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	UserService userService;
	@Autowired
	TokenService tokenService;
	@Autowired
	private JavaMailSender javaMailService;
	@Value("${spring.mail.from}")
	private String fromEmail;

	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public ResponseEntity<String> addUser(@RequestBody User user) {
		String message = "";
		try {
			user.setBranch(userService.getBranchByName(user.getBranch().getName()));
			Object[] roles =  user.getRoles().toArray();
			Role role = userService.getRoleByName(((Role)roles[0]).getName());
			Set<Role> roless = new HashSet<>();
			roless.add(role);
			user.setRoles(roless);
			userService.createUser(user);
			message = "Added";
			logger.debug("User Added");
		}catch(Exception e){
			message=e.getMessage();
		}
		return new ResponseEntity<>("{\"message\":\""+message+"\"}",HttpStatus.OK);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<String> updateUser(@RequestBody User user) {
		String message = "";
		try {
			user.setBranch(userService.getBranchByName(user.getBranch().getName()));
			Object[] roles =  user.getRoles().toArray();
			//Role role = userService.getRoleByName(((Role)roles[0]).getName());
			if(userService.getRoleByName(((Role)roles[0]).getName()) != null) {
				Role role = userService.getRoleByName(((Role)roles[0]).getName());
				Set<Role> roless = new HashSet<>();
				roless.add(role);
				user.setRoles(roless);
				userService.updateUser(user);
			} else {
				userService.updateUser(user);
			}
			message = "Updated";
			logger.debug("User Updated");
		}catch(Exception e){
			message=e.getMessage();
		}
		return new ResponseEntity<>("{\"message\":\""+message+"\"}",HttpStatus.OK);
	}
	
	@RequestMapping(value = "/get/all", method = RequestMethod.GET)
	public Object getAllUsers() {
		return userService.getAll();

	}
	
	@RequestMapping(value = "/get/{typeId}", method = RequestMethod.GET)
	public Object addUser(@PathVariable("typeId")String typeId,HttpServletRequest request, HttpServletResponse response) {
		logger.debug("Get user");
		if(!typeId.isEmpty()) {
			return "ALL".equalsIgnoreCase(typeId)?userService.getAll():userService.get(typeId);
		
		}
		return new Message("user Added");
	}

	@RequestMapping(value = { "/logout" }, method = RequestMethod.POST)
	public Message logoutDo(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		SecurityContextHolder.clearContext();
		session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		} 
		/*
		 * for(Cookie cookie : request.getCookies()) { cookie.setMaxAge(0); }
		 */
		return new Message("logout Successful");
	}

	@RequestMapping(value = "/submitforgotPassword", method = RequestMethod.POST,consumes="application/json", produces = "application/json")
	public Message submitforgotPassword(HttpServletRequest request, @RequestParam("username") String username) {
		String message = "";
		try {
			User user = userService.findUserByEmail(username);
			if (user != null) {
				String token = tokenService.createTokenForUser(user);
				SimpleMailMessage mailMessage = new SimpleMailMessage();
				mailMessage.setTo(username);
				mailMessage.setFrom(fromEmail);
				mailMessage.setSubject("Forgot Password");
				mailMessage.setText("Dear " + user.getName() + "," + "\n\n" + "Greetings from NASSCOM !!\n\n"
						+ "Please click on the following link, or paste into your browser to complete the reset password process :\n\n"
						+ request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
						+ "/#/submitforgotPassword?token=" + token);
				javaMailService.send(mailMessage);
				message = "Mail sent to registered Email Id.";
			} else {
				message = "Please enter registered Email ID.";
			}
		} catch (Exception e) {
			message = " Unable to send mail. Please try after some time.";
		}
		return new Message(message);
	}

	@RequestMapping(value = "/submitNewPassword", method = RequestMethod.POST, consumes="application/json", produces = "application/json")
	public Message submitNewPassword(HttpServletRequest request, @RequestParam("newpassword") String newpassword,
			@RequestParam("token") String token) {
		String message;
		try {
			User user = tokenService.parseUserFromToken(token);
			if (user != null) {
				byte[] encodedBytes = Base64.encode(newpassword.getBytes());
				String hashedPassword = new String(encodedBytes, Charset.forName("UTF-8"));
				/*
				 * BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); String
				 * hashedPassword = passwordEncoder.encode(newpassword);
				 */
				user.setPassword(hashedPassword);
				userService.save(user);
				message = "Password changed successfully.";
			} else {
				message = "Current Password does not match.";
			}
		} catch (Exception e) {
			message = "Password change failed.";
		}
		return new Message(message);
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST,consumes="application/json", produces = "application/json")
	public Message changePassword(HttpServletRequest request, @RequestParam("newpassword") String newpassword,
			@RequestParam("oldpassword") String oldpassword) {
		String message;
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username = ((org.springframework.security.core.userdetails.User) principal).getUsername();

			User user = userService.findUserByEmail(username);
			if (user != null) {
				/* BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); */
				byte[] decodedBytes = Base64.decode(user.getPassword().getBytes());
				String memberPassword = new String(decodedBytes, Charset.forName("UTF-8"));
				boolean result = oldpassword.equals(memberPassword);
				/*
				 * boolean result = passwordEncoder.matches(oldpassword , user.getPassword());
				 */

				if (result) {
					byte[] encodedBytes = Base64.encode(newpassword.getBytes());
					String hashedPassword = new String(encodedBytes, Charset.forName("UTF-8"));
					/*
					 * passwordEncoder = new BCryptPasswordEncoder();
					 * String hashedPassword=passwordEncoder.encode(newpassword);
					 */
					user.setPassword(hashedPassword);
					userService.save(user);
					message = "Password changed successfully.";
				} else {
					message = "Current Password does not match. Please enter correct password !!";
				}
			} else {
				message = "Member not found.";
			}
		} catch (Exception e) {
			message = "Password change failed.";
		}
		return new Message(message);
	}
}
