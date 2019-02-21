package com.example.demo.controller.franchise;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.constants.StatusCode;
import com.example.demo.dao.franchise.FranchiseDAO;
import com.example.demo.model.franchise.FranchiseModel;
import com.example.demo.response.ErrorObject;
import com.example.demo.response.Response;
import com.example.demo.service.franchise.FranchiseService;
import com.example.demo.utils.CommonUtils;




@RestController
@RequestMapping("/v1")
@CrossOrigin(origins="http://localhost:4200",allowedHeaders="*")

public class FranchiseController {
	
	private static final Logger logger = LoggerFactory.getLogger(FranchiseController.class);

	@Autowired 
	FranchiseService franchiseService;
	
	@Autowired 
	FranchiseDAO franchiseDAO;
	
	
	/*--------------------------------------------Add franchise-----------------------------------------*/
	
	
	@RequestMapping(value = "/franchise", method = RequestMethod.POST, produces = "application/json")
	public Response addFranchise(@RequestBody FranchiseModel franchise, HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
		logger.info("addFranchise: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("addFranchise: Received request: "+ CommonUtils.getJson(franchise));
		
		return franchiseService.addFranchise(franchise);
		
		
	}
	
	
	/*--------------------------------------------get franchise by ID-----------------------------------------*/
	
	
	@RequestMapping(value = "/getfranchise/{franchiseId}", method = RequestMethod.GET, produces = "application/json")
public @ResponseBody String getFranchise(@PathVariable("franchiseId") String franchiseId, HttpServletRequest request,
		HttpServletResponse response) throws Exception 
{

	logger.info("getFranchise: Received request: " + request.getRequestURL().toString()
			+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
	
	FranchiseModel franchiseModel = franchiseService.getFranchise( franchiseId);
	
	Response res = CommonUtils.getResponseObject("Franchise Details");
	if (franchiseModel == null)
	{
		ErrorObject err = CommonUtils.getErrorResponse("Franchises Not Found", "Franchises Not Found");
		res.setErrors(err);
		res.setStatus(StatusCode.ERROR.name());
	} 
	else
	{
		res.setData(franchiseModel);
	}
	logger.info("getFranchise: Sent response");
	return CommonUtils.getJson(res);
}


/*--------------------------------------------update franchise -----------------------------------------*/

@RequestMapping(value = "/updatefranchise", method = RequestMethod.PUT, produces = "application/json")
public Response updateFranchise(@RequestBody FranchiseModel franchiseModel, HttpServletRequest request, HttpServletResponse response)
		throws Exception 
{
	logger.info("updateFranchise: Received request URL: " + request.getRequestURL().toString()
			+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
	logger.info("updateFranchise: Received request: " + CommonUtils.getJson(franchiseModel));
	
	return franchiseService.updateFranchise(franchiseModel);
	
}
/*--------------------------------------------get franchises -----------------------------------------*/
@RequestMapping(value = "/franchises", method = RequestMethod.GET, produces = "application/json")
public @ResponseBody String getFranchises(HttpServletRequest request, HttpServletResponse response) throws Exception 
{
	logger.info("getFranchises: Received request: " + request.getRequestURL().toString()
			+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
	List<FranchiseModel> model = franchiseService.getFranchises();
	
	
	Response res = CommonUtils.getResponseObject("List of Franchises");
	if (model == null) {
		ErrorObject err = CommonUtils.getErrorResponse("Franchises Not Found", "Franchises Not Found");
		res.setErrors(err);
		res.setStatus(StatusCode.ERROR.name());
	} else {
		res.setData(model);
	}
	logger.info("getFranchises: Sent response");
	return CommonUtils.getJson(res);
}

/*--------------------------------------------delete franchises -----------------------------------------*/
@RequestMapping(value="/deletefranchise/{franchiseId}",method = RequestMethod.DELETE, produces = "application/json")
public @ResponseBody Response deleteFranchise (@PathVariable("franchiseId") String franchiseId, HttpServletRequest request, HttpServletResponse response) throws Exception
{
	logger.info("getFranchise: Received request:" +request.getRequestURL().toString()
			+((request.getQueryString()==null)?  "" : "?" +request.getQueryString().toString()));
	
	return franchiseService.deleteFranchise(franchiseId);
}
/*--------------------------------------------Franchise login -----------------------------------------*/
@RequestMapping(value="/login",method = RequestMethod.POST, produces ="application/json")
public @ResponseBody String authenticate (@RequestBody FranchiseModel franchise, HttpServletRequest request, HttpServletResponse response) throws Exception

{
	logger.info("authenticate: Received request: " + request.getRequestURL().toString()
			+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
	logger.info("authenticate :Received request: " + CommonUtils.getJson(franchise));
	
	franchise =franchiseService.authenticate(franchise);
	
	Response res=CommonUtils.getResponseObject("authenticate franchise");
	if(franchise==null)
	{
		ErrorObject err=CommonUtils.getErrorResponse("Invalid Fullname or Password", "Invalid Fullname or Password");
		res.setErrors(err);
		res.setStatus(StatusCode.ERROR.name());
	}
	{
		res.setData(franchise);
		
	}
	logger.info("authenticate:sent response");
	return CommonUtils.getJson(res);
}
/*--------------------------------------------Forgot Password -----------------------------------------*/
@RequestMapping(value="/forgotPassword",method = RequestMethod.PUT, produces ="application/json")
public @ResponseBody String resetPassword(@RequestBody FranchiseModel franchiseModel, HttpServletRequest request,
		HttpServletResponse response) throws Exception
{
	logger.info("forgotPassword: Received request URL: " + request.getRequestURL().toString()
			+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
	logger.info("forgotPassword: Received request: "+CommonUtils.getJson(franchiseModel));
	
	String status = franchiseService.forgotPassword(franchiseModel);
	Response res = CommonUtils.getResponseObject("forgot password");
	if (status.equalsIgnoreCase(StatusCode.ERROR.name())) {
		ErrorObject err = CommonUtils.getErrorResponse("forgot password failed", "forgot password failed");
		res.setErrors(err);
		res.setStatus(StatusCode.ERROR.name());
}
	logger.info("forgotPassword: Sent response");
	return CommonUtils.getJson(res);
}
/*--------------------------------------------Franchise status -----------------------------------------*/

@RequestMapping(value = "/franchiseStatus", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
public Response updateFranchiseStatus(@RequestBody FranchiseModel franchiseModel, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
	logger.info("updateFranchiseStatus: Received request URL: " + request.getRequestURL().toString()
			+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
	logger.info("updateFranchiseStatus: Received request: " + CommonUtils.getJson(franchiseModel));
	return franchiseService.updateFranchiseStatus(franchiseModel);


}
/*--------------------------------------------isFullname Exist -----------------------------------------*/


@RequestMapping(value = "/franchiseExist/{fullname}", method = RequestMethod.GET, produces = "application/json")
public @ResponseBody String isFullnameExist(@PathVariable("fullname") String fullname, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
	logger.info("getFranchise: Received request: " + request.getRequestURL().toString()
			+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
	boolean isFullnameExist = franchiseService.isFullnameExist(fullname);
	Response res = CommonUtils.getResponseObject("Franchise Exist");
	Map<String, Boolean> obj = new HashMap<String, Boolean>();
	obj.put("isFullnameExist", isFullnameExist);
	res.setData(obj);
	if (!isFullnameExist) {
		res.setStatus(StatusCode.ERROR.name());
	}
	logger.info("getFranchise: Sent response");
	return CommonUtils.getJson(res);
}
}















