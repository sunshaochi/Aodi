package com.iris.cada.entity;

public class IviewDiagnosisBean {
	public String SalesConsultant;
	public String XinPotentialCustomer;
	public String LookToBuyRate;
	public String DerivativesBusinessAverageCarProfits;
	//销售顾问别名 
	private String Alias;

	public String getAlias() {
			return Alias;
		}

		public void setAlias(String alias) {
			Alias = alias;
		}

	public String getSalesConsultant() {
		return SalesConsultant;
	}

	public void setSalesConsultant(String salesConsultant) {
		SalesConsultant = salesConsultant;
	}

	public String getXinPotentialCustomer() {
		return XinPotentialCustomer;
	}

	public void setXinPotentialCustomer(String xinPotentialCustomer) {
		XinPotentialCustomer = xinPotentialCustomer;
	}

	public String getLookToBuyRate() {
		return LookToBuyRate;
	}

	public void setLookToBuyRate(String lookToBuyRate) {
		LookToBuyRate = lookToBuyRate;
	}

	public String getDerivativesBusinessAverageCarProfits() {
		return DerivativesBusinessAverageCarProfits;
	}

	public void setDerivativesBusinessAverageCarProfits(String derivativesBusinessAverageCarProfits) {
		DerivativesBusinessAverageCarProfits = derivativesBusinessAverageCarProfits;
	}

}