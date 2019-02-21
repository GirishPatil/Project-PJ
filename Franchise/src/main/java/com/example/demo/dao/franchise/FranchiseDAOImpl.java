package com.example.demo.dao.franchise;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.example.demo.constants.StatusCode;
import com.example.demo.domain.franchise.Franchise;
import com.example.demo.response.Response;
import com.example.demo.utils.CommonUtils;




@Repository
@Transactional
public  class FranchiseDAOImpl implements FranchiseDAO {
	
	
	private static final Logger logger = LoggerFactory.getLogger(FranchiseDAOImpl.class);


	@PersistenceContext
	EntityManager entityManager;

	@Override
	public Response addFranchise(Franchise franchise) throws Exception {
		Response response = CommonUtils.getResponseObject("Add Franchise data");
		try 
		{
			entityManager.persist(franchise);
			response.setStatus(StatusCode.SUCCESS.name());
		} 
		catch (Exception e) 
		{
			logger.error("Exception in addUser", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return response;

}
	
	@Override
	public Response deleteFranchise(String franchiseId) throws Exception {
		Response response = CommonUtils.getResponseObject("Delete franchise data");
		try {
			

			Franchise franchise=getFranchise(franchiseId);
			franchise.setActive(false);


		
			entityManager.flush();

			response.setStatus(StatusCode.SUCCESS.name());
			
		} 
		catch(Exception ex)
		{
			logger.error("Exception in deletefranchise", ex);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(ex.getMessage());
		}
		return response;	
	}

	@Override
	public Franchise getFranchise(String franchiseId) throws Exception {

		try 
		{
			String hql = "From Franchise where franchiseId=? and isActive=true";
			return (Franchise) entityManager.createQuery(hql).setParameter(0, franchiseId).getSingleResult();
		} 
		catch (EmptyResultDataAccessException e) 
		{
			return null;
		} 
		catch (Exception e) 
		{
			logger.error("Exception in getFranchise"+ e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Franchise> getFranchises() throws Exception {
		try 
		{
			String hql = "FROM Franchise where isActive=true";
			
			return (List<Franchise>) entityManager.createQuery(hql).getResultList();
		} 
		catch (Exception e)
		{
			logger.error("Exception in getFranchises", e);
		}
		return null;
	}

	@Override
	public Franchise authenticate(Franchise franchise) throws Exception {
		try 
		{
		String hql = "FROM Franchise where email=:email  and password=:password  and isActive=true";
		return (Franchise) entityManager.createQuery(hql).setParameter("email", franchise.getemail()).setParameter("password", franchise.getpassword()).getSingleResult();
		} 
		catch (Exception e)
		{
		logger.error("Exception in auteneticate", e);
	}
		return null;
	}

	@Override
	public Response updateFranchise(Franchise franchise) throws Exception {
		Response response = CommonUtils.getResponseObject("Update franchise data");
		try 
		{	
			Franchise franchises = getFranchise(franchise.getfranchiseId());
			franchises.setfullname(franchise.getfullname());
	        franchises.setphoneno(franchise.getphoneno());
			franchises.setemail(franchise.getemail());
			franchises.setActive(true);
			franchises.setpassword(CommonUtils.encriptString(franchise.getpassword()));
	        entityManager.flush();
			response.setStatus(StatusCode.SUCCESS.name());
		} 
		catch 	(Exception e) 
		{
			logger.error("Exception in updateFranchise", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return  response;
	}

	@Override
	public String forgotPassword(String franchiseId, String encriptString) throws Exception {
		try {
			Franchise fran = getFranchise(franchiseId);
			System.out.println(franchiseId);
			fran.setpassword(encriptString);
			entityManager.flush();
			return StatusCode.SUCCESS.name();
	} catch (Exception e) {
		logger.error("Exception in forgotPassword", e);
		return StatusCode.ERROR.name();
	}
	}

	@Override
	public boolean isFullnameExist(String fullname) throws Exception {
		try {
			String hql = "FROM Franchise WHERE fullname=? and  isActive=true ";
			int count = entityManager.createQuery(hql).setParameter(0, fullname).getResultList().size();
			System.out.println(count);
			return count > 0 ? true : false;
		} catch (Exception e) {
			logger.error("Exception in isfullnameExist: ", e);
		}
		return false;
	}
	@Override
	public Response updateFranchiseStatus(Franchise franchise) throws Exception {
		Response response = CommonUtils.getResponseObject("Update franchise data");
		try {
			Franchise franchise1 = getFranchise(franchise.getfranchiseId());
			
			
			entityManager.flush();
			response.setStatus(StatusCode.SUCCESS.name());
		} catch (Exception e) {
			logger.error("Exception in deleteFranchise", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return response;
	}

	@Override
	public Franchise isFranchiseExist(Franchise franchise) throws Exception {
		try {
			String hql = "FROM Franchise where email=? and isActive=true";
			return (Franchise) entityManager.createQuery(hql).setParameter(0, franchise.getemail()).getSingleResult();
		} catch (Exception e) {
			logger.error("Exception in pjisFranchiseExist", e);
		}
	
	
		return franchise;
	}





/**	@Override
	public String changePassword(String franchiseId, String confirmpassword, String encriptString) throws Exception {
		try 
		{
			Franchise franchise = getFranchise(franchiseId);
			franchise.setpassword(encriptString);
			entityManager.flush();
			return StatusCode.SUCCESS.name();
		} 
		catch (Exception e) 
		{
			logger.error("Exception in changePassword", e);
			return StatusCode.ERROR.name();	
		}
	}**/
	
		/*try {
			String hql = "FROM User WHERE userName=? and isActive=true";
			int count = entityManager.createQuery(hql).setParameter(0, userName).getResultList().size();
			System.out.println(count);
			return count > 0 ? true : false;
		} catch (Exception e) {
			logger.error("Exception in isUserNameExist: ", e);
		}
		return false;**/
/**	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Franchise> getFranchisesByRole(String role) throws Exception {
		try { 
			
			String hql = "FROM Franchise  WHERE role=?and isActive=true " ;
			return (List<Franchise>) entityManager.createQuery(hql).setParameter(0, role).getResultList();
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			logger.error("Exception in getFranchisesByRole", e);
			return null;
		}
	} **/



	




}
	




