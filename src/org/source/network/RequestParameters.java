package org.source.network;

import java.util.HashMap;
import java.util.Map;

import org.source.service.CacheService;

public class RequestParameters {

	final public static String PARAM_LOGIN = "login";
	final public static String PARAM_PASS = "pass";
	final public static String PARAM_ACCESS = "access";
	final public static String PARAM_FNAME = "first_name";
	final public static String PARAM_SNAME = "second_name";
	final public static String PARAM_MNAME = "middle_name";
	final public static String PARAM_EMAIL = "email";
	final public static String PARAM_SEARCH = "search";
	final public static String PARAM_SORT = "sort";
	final public static String PARAM_ID = "id";
	final public static String PARAM_TYPE_SORT = "type_sort";
	
	private static RequestParameters instance = new RequestParameters();

	public static RequestParameters getInstance() {
		return instance;
	}
	
	Map<String, String> getParametersAuthorization() {
		
		Map<String, String> parametersMap = new HashMap<String, String>();
		parametersMap.put(PARAM_LOGIN, CacheService.getInstance().getLogin());
		parametersMap.put(PARAM_PASS, CacheService.getInstance().getPassword());
		parametersMap.put(PARAM_ACCESS, String.valueOf(
				CacheService.getInstance().getAccessUser()));
		return parametersMap;
		
	}
	
	Map<String, String> getParametersRegistration() {

		Map<String, String> parametersMap = new HashMap<String, String>();
		parametersMap.put(PARAM_SNAME, CacheService.getInstance().getSecondName());
		parametersMap.put(PARAM_FNAME, CacheService.getInstance().getFirstName());
		parametersMap.put(PARAM_MNAME, CacheService.getInstance().getMiddleName());
		parametersMap.put(PARAM_EMAIL, CacheService.getInstance().getEmail());
		parametersMap.put(PARAM_LOGIN, CacheService.getInstance().getLogin());
		parametersMap.put(PARAM_PASS, CacheService.getInstance().getPassword());
		parametersMap.put(PARAM_ACCESS, String.valueOf(
				CacheService.getInstance().getAccessUser()));
		return parametersMap;
		
	}
	
	Map<String, String> getParametersProducts() {

		Map<String, String> parametersMap = new HashMap<String, String>();
		parametersMap.put(PARAM_SEARCH, CacheService.getInstance().getSearchString());
		parametersMap.put(PARAM_SORT, CacheService.getInstance().getSortColumn());
		parametersMap.put(PARAM_TYPE_SORT, CacheService.getInstance().getTypeSort());
		return parametersMap;
		
	}
	
	Map<String, String> getParametersDescriptionProduct() {

		Map<String, String> parametersMap = new HashMap<String, String>();
		parametersMap.put(PARAM_ID, String.valueOf(CacheService.getInstance().getIdSelectedProduct()));
		return parametersMap;
		
	}
	
}
