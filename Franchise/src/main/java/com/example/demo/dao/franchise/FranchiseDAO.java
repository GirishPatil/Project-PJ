package com.example.demo.dao.franchise;

import java.util.List;

import com.example.demo.domain.franchise.Franchise;
import com.example.demo.response.Response;

public interface FranchiseDAO {
	public Response addFranchise(Franchise franchise)throws Exception;
	
	public Response deleteFranchise(String franchiseId)throws Exception;

	public Franchise getFranchise(String franchiseId)throws Exception;

	public List<Franchise> getFranchises()throws Exception;

	public Franchise authenticate(Franchise franchise)throws Exception;

	public Response updateFranchise(Franchise franchise)throws Exception;

    public String forgotPassword(String franchiseId, String encriptString)throws Exception;
	
    public Response updateFranchiseStatus(Franchise franchise)throws Exception;

    public Franchise isFranchiseExist(Franchise franchise)throws Exception;

	public	boolean isFullnameExist(String fullname) throws Exception;
	

	



}
