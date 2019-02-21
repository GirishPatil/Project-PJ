package com.example.demo.service.franchise;



import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.constants.StatusCode;
import com.example.demo.dao.franchise.FranchiseDAO;
import com.example.demo.domain.franchise.Franchise;
import com.example.demo.mapper.franchise.FranchiseMapper;
import com.example.demo.model.franchise.FranchiseModel;
import com.example.demo.response.Response;
import com.example.demo.utils.CommonUtils;
import com.example.demo.utils.SmtpMailSender;


@Service
public class FranchiseServiceImpl implements FranchiseService {
	
	@Autowired
	FranchiseDAO franchiseDAO;
	
	
	@Autowired
	FranchiseMapper franchiseMapper;
	
	@Autowired
	SmtpMailSender smtpMailSender;
	
	private static final Logger logger = LoggerFactory.getLogger(FranchiseServiceImpl.class);

	@Override
	public Response addFranchise(FranchiseModel franchiseModel) throws Exception 
	{
		try
		{
			Franchise franchise= new Franchise();
			franchise.setfranchiseId(CommonUtils.generateRandomId());
			franchise.setfullname(franchiseModel.getfullname());
			franchise.setphoneno(franchiseModel.getphoneno());
			franchise.setemail(franchiseModel.getemail());
		    franchise.setpassword(CommonUtils.encriptString(franchiseModel.getpassword()));
		    franchise.setActive(true);
			
		    
		    
		    Response response = franchiseDAO.addFranchise(franchise);
			String email=franchise.getemail();
			String name=franchise.getfullname();
			smtpMailSender.send(email,"Hello Learn how to select a Franchisor","" + 
					" Dear sir/Mam," + 
				 
					"Greetings from Gurukulam team @ !Gurukulam pre school Hope you find the above piece from our Franchising Series valuable. " + 
					"In case you have any query please speak with undersigned." + 
					 
					"Warm Regards,"
					+ "Gurulukulam Team"
					+ " ="+ name);

			return response;
			
			
		}
		catch(Exception e)
		{
			logger.info("Exception Service:" + e.getMessage());
			Response response=new Response();
			response.setStatus(StatusCode.ERROR.name());
			response.setMessage(e.getMessage());
			return response;
		}
	}

	@Override
	public Response updateFranchise(FranchiseModel franchiseModel) throws Exception {
		
		{
			Franchise franchise=new Franchise();
			BeanUtils.copyProperties(franchiseModel, franchise);
			Response res=franchiseDAO.updateFranchise(franchise);
			return res;
		}
	}
	/*--------------------------------------------get franchise by ID-----------------------------------------*/
	
	
		@Override
		public  FranchiseModel getFranchise(String franchiseId) throws Exception {
			try 
			{
				FranchiseModel franchiseModel = new FranchiseModel();
				Franchise franchise = franchiseDAO.getFranchise(franchiseId);
				BeanUtils.copyProperties(franchise, franchiseModel);
				return franchiseModel;
			} 
			catch(Exception e) 
			{
				logger.info("Exception getFranchise:", e);
				return null;
			}
		}

	

	

	@Override
	public Response deleteFranchise(String franchiseId) throws Exception {
		try
		{
			return franchiseDAO.deleteFranchise(franchiseId);
		} 
		catch (Exception e) 
		{
			logger.info("Exception deleteFranchise:", e);
			return null;
	}
	}
	@Override
	public List<FranchiseModel> getFranchises() throws Exception {
		try
		{
			List<Franchise> franchises = franchiseDAO.getFranchises();
			return franchiseMapper.entityList(franchises);
		} 
		catch (Exception ex)
		{
			logger.info("Exception getFranchises:", ex);
		}
		return null;
	}

	@Override
	public FranchiseModel authenticate(FranchiseModel franchiseModel) throws Exception {
		franchiseModel.setpassword(CommonUtils.encriptString(franchiseModel.getpassword()));
		Franchise franchise = new Franchise();
		BeanUtils.copyProperties(franchiseModel, franchise);
		System.out.println(franchiseModel.getemail());
		System.out.println(franchiseModel.getphoneno());

		franchise = franchiseDAO.authenticate(franchise);
		if (franchise == null)
			return null;
		BeanUtils.copyProperties(franchise, franchiseModel);
		return franchiseModel;
	}
	/*--------------------------------------------forgot password-----------------------------------------*/

@SuppressWarnings("unused")
	@Override
	public String forgotPassword(FranchiseModel franchiseModel) throws Exception {
		
		try {
			Franchise franchise = new Franchise();
			BeanUtils.copyProperties(franchiseModel, franchise);
			franchise = franchiseDAO.isFranchiseExist(franchise);
			System.out.println(franchise.getfullname());
			if (franchise != null) {
				String password = CommonUtils.generateRandomId();
				
				String status = franchiseDAO.forgotPassword(franchise.getfranchiseId(), CommonUtils.encriptString(password));
				if (status.equals(StatusCode.SUCCESS.name())) {
					String email=franchise.getemail();
					String pass=password;
					smtpMailSender.send(email,"Snipe It tech pvt ltd reset password","forgot password ="+pass);
				}
				return status;
			} 
			else
				return StatusCode.ERROR.name(); 
			
			
			
		} catch (Exception e) {
			logger.error("Exception in forgotPassword:", e);
			return StatusCode.ERROR.name();
		}
	
}

@Override
public boolean isFullnameExist(String fullname) throws Exception {
 
	try {
		return franchiseDAO.isFullnameExist(fullname);
	} catch (Exception e) {
		logger.info("Exception isFullnameExist:", e);
	return false;
}
}
@Override
public Response updateFranchiseStatus(FranchiseModel franchiseModel) throws Exception {
	try {
		Franchise franchise = new Franchise();
		BeanUtils.copyProperties(franchiseModel, franchise);
		Response response = franchiseDAO.updateFranchiseStatus(franchise);
		return response;
	} catch (Exception ex) {
		logger.info("Exception in updateFranchiseStatus:" + ex.getMessage());
	}
	
	return null;
}

}




