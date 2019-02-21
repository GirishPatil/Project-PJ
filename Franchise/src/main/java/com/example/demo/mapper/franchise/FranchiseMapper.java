package com.example.demo.mapper.franchise;

import org.springframework.stereotype.Component;

import com.example.demo.domain.franchise.Franchise;
import com.example.demo.mapper.AbstractModelMapper;
import com.example.demo.model.franchise.FranchiseModel;


@Component
public class FranchiseMapper extends AbstractModelMapper<FranchiseModel, Franchise>{

	@Override
	public Class<FranchiseModel> entityType() {
		return FranchiseModel.class;
	}

	@Override
	public Class<Franchise> modelType() {
		return Franchise.class;
	}


}
