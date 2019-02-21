package com.example.demo.service.franchise;

import java.util.List;

import com.example.demo.model.franchise.FranchiseModel;
import com.example.demo.response.Response;


public interface FranchiseService {

	public Response addFranchise(FranchiseModel franchise) throws Exception;
	
	public Response updateFranchise(FranchiseModel franchiseModel) throws Exception;

	public FranchiseModel getFranchise(String franchiseId) throws Exception;

	public Response deleteFranchise(String franchiseId) throws Exception;

	public List<FranchiseModel> getFranchises() throws Exception;
	
	public FranchiseModel authenticate(FranchiseModel franchise) throws Exception;

	public String forgotPassword(FranchiseModel franchiseModel) throws Exception;

	public boolean isFullnameExist(String fullname)  throws Exception;
	
	public Response updateFranchiseStatus(FranchiseModel franchiseModel) throws Exception;

}
