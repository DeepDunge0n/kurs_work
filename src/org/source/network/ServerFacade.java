package org.source.network;

import java.net.ConnectException;

import java.util.Map.Entry;

import org.fr2eman.send.*;
import org.source.builder.BuilderRequest;
import org.source.service.CacheService;
import org.source.service.DataService;

public class ServerFacade {
	
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
	final public static String PARAM_ID_USER = "id_user";
	final public static String PARAM_ID_ORDER = "id_order";
	final public static String PARAM_TYPE_SORT = "type_sort";
	final public static String PARAM_PRODUCT = "product";
	final public static String PARAM_NUMBER_PRODUCT = "number_product";
	final public static String PARAM_NUMBER_PRODUCTS_ORDER = "number_products_order";
	final public static String PARAM_BEGIN_DATE = "begin_date";
	final public static String PARAM_END_DATE = "end_date";
	final public static String PARAM_NAME_PRODUCT = "name_product";
	final public static String PARAM_PRICE_PRODUCT = "price_product";
	final public static String PARAM_DESCRIPTION_PRODUCT = "description_product";
	final public static String PARAM_ID_PRODUCT = "id_product";
	final public static String PARAM_ORDER_STATUS = "order_status";
	final public static String PARAM_IS_ALL_ORDERS = "is_all_orders";
	final public static String PARAM_SEARCH_LOGIN = "search_login";
	final public static String PARAM_YEAR = "year";
	final public static String PARAM_MONTH = "month";
	
	final public static int TYPE_AUTHORIZATION = 1;
	final public static int TYPE_REGISTRATION = 2;
	final public static int TYPE_GET_PRODUCTS = 3;
	final public static int TYPE_DESCRIPTION_PRODUCT = 4;
	final public static int TYPE_MAKE_ORDER = 5;
	final public static int TYPE_DATAS_USER = 6;
	final public static int TYPE_GET_ORDERS_USER = 7;
	final public static int TYPE_ORDER_DETAILS = 8;
	final public static int TYPE_CANCEL_ORDER = 9;
	final public static int TYPE_ADD_PRODUCT = 10;
	final public static int TYPE_UPDATE_PRODUCT = 11;
	final public static int TYPE_GET_ALL_ORDERS = 12;
	final public static int TYPE_UPDATE_STATUS_ORDER = 13;
	final public static int TYPE_GET_ALL_USERS = 14;
	final public static int TYPE_USER_DETAILS_BY_ID = 15;
	final public static int TYPE_REMOVE_USER = 16;
	final public static int TYPE_STATISTIC_PRODUCTS = 17;
	final public static int TYPE_STATISTIC_USERS = 18;
	final public static int TYPE_STATISTIC_ORDERS = 19;
	final public static int TYPE_STATISTIC_ORDERS_FOR_YEAR = 20;
	final public static int TYPE_STATISTIC_ORDERS_FOR_MONTH = 21;
	
	private static ServerFacade instance = new ServerFacade();

	public static ServerFacade getInstance() {
		return instance;
	}
	public DataResponse requestAuthorization() throws ConnectException {
		
		BuilderRequest builderRequest = new BuilderRequest(TYPE_AUTHORIZATION);
		
		builderRequest.addRequestDatas(PARAM_LOGIN, CacheService.getInstance().getLogin());
		builderRequest.addRequestDatas(PARAM_PASS, CacheService.getInstance().getPassword());
		builderRequest.addRequestDatas(PARAM_ACCESS, String.valueOf(
				CacheService.getInstance().getAccessUser()));
		
		return send(builderRequest.buildRequest());
	}
	
	public DataResponse requestRegistration() throws ConnectException {	
		
		BuilderRequest builderRequest = new BuilderRequest(TYPE_REGISTRATION);
		
		builderRequest.addRequestDatas(PARAM_SNAME, CacheService.getInstance().getSecondName());
		builderRequest.addRequestDatas(PARAM_FNAME, CacheService.getInstance().getFirstName());
		builderRequest.addRequestDatas(PARAM_MNAME, CacheService.getInstance().getMiddleName());
		builderRequest.addRequestDatas(PARAM_EMAIL, CacheService.getInstance().getEmail());
		builderRequest.addRequestDatas(PARAM_LOGIN, CacheService.getInstance().getLogin());
		builderRequest.addRequestDatas(PARAM_PASS, CacheService.getInstance().getPassword());
		builderRequest.addRequestDatas(PARAM_ACCESS, String.valueOf(
				CacheService.getInstance().getAccessUser()));

		return send(builderRequest.buildRequest());
	}
	public DataResponse requestProducts() throws ConnectException {
		
		BuilderRequest builderRequest = new BuilderRequest(TYPE_GET_PRODUCTS);
		
		builderRequest.addRequestDatas(PARAM_SEARCH, CacheService.getInstance().getSearchString());
		builderRequest.addRequestDatas(PARAM_SORT, CacheService.getInstance().getSortColumn());
		builderRequest.addRequestDatas(PARAM_TYPE_SORT, CacheService.getInstance().getTypeSort());
		
		return send(builderRequest.buildRequest());
	}
	public DataResponse requestDescriptionProduct() throws ConnectException {

		BuilderRequest builderRequest = new BuilderRequest(TYPE_DESCRIPTION_PRODUCT);
		
		builderRequest.addRequestDatas(PARAM_ID, String.valueOf(CacheService.getInstance().getIdSelectedProduct()));
		
		return send(builderRequest.buildRequest());
	}
	public DataResponse requestMakeOrder() throws ConnectException {

		BuilderRequest builderRequest = new BuilderRequest(TYPE_MAKE_ORDER);
		
		builderRequest.addRequestDatas(PARAM_LOGIN, 
				DataService.getInstance().getLoginCurrentUser());
		builderRequest.addRequestDatas(PARAM_PASS, 
				DataService.getInstance().getPassCurrentUser());
		builderRequest.addRequestDatas(PARAM_NUMBER_PRODUCTS_ORDER, 
				String.valueOf(DataService.getInstance().getBasket().getDataOrder().size()));
		
		int index = 1;
		for(Entry<Integer, Integer> entry : 
			DataService.getInstance().getBasket().getDataOrder().entrySet()) {
			builderRequest.addRequestDatas(PARAM_PRODUCT + index, 
					String.valueOf(entry.getKey()));
			builderRequest.addRequestDatas(PARAM_NUMBER_PRODUCT + index, 
					String.valueOf(entry.getValue()));
			++index;
		}
		
		return send(builderRequest.buildRequest());
	}
	
	public DataResponse requestDadasUser() throws ConnectException {

		BuilderRequest builderRequest = new BuilderRequest(TYPE_DATAS_USER);
		
		builderRequest.addRequestDatas(PARAM_LOGIN, 
				DataService.getInstance().getLoginCurrentUser());
		builderRequest.addRequestDatas(PARAM_PASS, 
				DataService.getInstance().getPassCurrentUser());
		
		return send(builderRequest.buildRequest());
	}
	
	public DataResponse requestGetOrdersUser() throws ConnectException {

		BuilderRequest builderRequest = new BuilderRequest(TYPE_GET_ORDERS_USER);
		
		builderRequest.addRequestDatas(PARAM_LOGIN, 
				DataService.getInstance().getLoginCurrentUser());
		builderRequest.addRequestDatas(PARAM_PASS, 
				DataService.getInstance().getPassCurrentUser());
		builderRequest.addRequestDatas(PARAM_SEARCH, 
				CacheService.getInstance().getSearchString());
		builderRequest.addRequestDatas(PARAM_BEGIN_DATE, 
				CacheService.getInstance().getFirstDate());
		builderRequest.addRequestDatas(PARAM_END_DATE, 
				CacheService.getInstance().getLastDate());
		
		return send(builderRequest.buildRequest());
	}
	
	public DataResponse requestOrderDetails() throws ConnectException {

		BuilderRequest builderRequest = new BuilderRequest(TYPE_ORDER_DETAILS);
		
		builderRequest.addRequestDatas(PARAM_ID_ORDER, String.valueOf(
					CacheService.getInstance().getIdSelectedOrder()));
		
		return send(builderRequest.buildRequest());
	}
	
	public DataResponse requestCancelOrder() throws ConnectException {

		BuilderRequest builderRequest = new BuilderRequest(TYPE_CANCEL_ORDER);
		
		builderRequest.addRequestDatas(PARAM_ID_ORDER, String.valueOf(
					CacheService.getInstance().getCurrentOrder().getId()));
		
		return send(builderRequest.buildRequest());
	}
	
	public DataResponse requestAddProduct() throws ConnectException {

		BuilderRequest builderRequest = new BuilderRequest(TYPE_ADD_PRODUCT);
		
		builderRequest.addRequestDatas(PARAM_NAME_PRODUCT,
					CacheService.getInstance().getNameProduct());
		builderRequest.addRequestDatas(PARAM_NUMBER_PRODUCT,
				CacheService.getInstance().getNumberProduct());
		builderRequest.addRequestDatas(PARAM_PRICE_PRODUCT,
				CacheService.getInstance().getPriceProduct());
		builderRequest.addRequestDatas(PARAM_DESCRIPTION_PRODUCT,
				CacheService.getInstance().getDescriptionProduct());
		
		return send(builderRequest.buildRequest());
	}
	
	public DataResponse requestUpdateProduct() throws ConnectException {

		BuilderRequest builderRequest = new BuilderRequest(TYPE_UPDATE_PRODUCT);
		
		builderRequest.addRequestDatas(PARAM_ID_PRODUCT, String.
				valueOf(CacheService.getInstance().getIdSelectedProduct()));
		builderRequest.addRequestDatas(PARAM_NAME_PRODUCT,
					CacheService.getInstance().getNameProduct());
		builderRequest.addRequestDatas(PARAM_NUMBER_PRODUCT,
				CacheService.getInstance().getNumberProduct());
		builderRequest.addRequestDatas(PARAM_PRICE_PRODUCT,
				CacheService.getInstance().getPriceProduct());
		builderRequest.addRequestDatas(PARAM_DESCRIPTION_PRODUCT,
				CacheService.getInstance().getDescriptionProduct());
		
		return send(builderRequest.buildRequest());
	}
	
	public DataResponse requestGetAllOrders() throws ConnectException {

		BuilderRequest builderRequest = new BuilderRequest(TYPE_GET_ALL_ORDERS);
		
		builderRequest.addRequestDatas(PARAM_IS_ALL_ORDERS, 
				String.valueOf(CacheService.getInstance().getIsAllOrders()));
		if(!CacheService.getInstance().getIsAllOrders()) {
			builderRequest.addRequestDatas(PARAM_ID_USER, 
					String.valueOf(CacheService.getInstance().getIdSelectedUser()));
		}
		builderRequest.addRequestDatas(PARAM_SEARCH, 
				CacheService.getInstance().getSearchString());
		builderRequest.addRequestDatas(PARAM_BEGIN_DATE, 
				CacheService.getInstance().getFirstDate());
		builderRequest.addRequestDatas(PARAM_END_DATE, 
				CacheService.getInstance().getLastDate());
		
		return send(builderRequest.buildRequest());
	}
	
	public DataResponse requestUpdateStatusOrder() throws ConnectException {

		BuilderRequest builderRequest = new BuilderRequest(TYPE_UPDATE_STATUS_ORDER);
		
		builderRequest.addRequestDatas(PARAM_ID_ORDER, String.valueOf(
				CacheService.getInstance().getCurrentOrder().getId()));
		builderRequest.addRequestDatas(PARAM_ORDER_STATUS, 
				String.valueOf(CacheService.getInstance().getOrderStatus()));
		
		return send(builderRequest.buildRequest());
	}
	
	public DataResponse requestGetAllUsers() throws ConnectException {

		BuilderRequest builderRequest = new BuilderRequest(TYPE_GET_ALL_USERS);
		
		builderRequest.addRequestDatas(PARAM_SEARCH_LOGIN, 
				CacheService.getInstance().getSearchString());
		
		return send(builderRequest.buildRequest());
	}
	
	public DataResponse requestUserDetailsById() throws ConnectException {

		BuilderRequest builderRequest = new BuilderRequest(TYPE_USER_DETAILS_BY_ID);
		
		builderRequest.addRequestDatas(PARAM_ID_USER, String.valueOf(
				CacheService.getInstance().getIdSelectedUser()));
		
		return send(builderRequest.buildRequest());
	}
	
	public DataResponse requestRemoveUser() throws ConnectException {

		BuilderRequest builderRequest = new BuilderRequest(TYPE_REMOVE_USER);
		
		builderRequest.addRequestDatas(PARAM_ID_USER, String.valueOf(
				CacheService.getInstance().getIdSelectedUser()));
		
		return send(builderRequest.buildRequest());
	}
	
	public DataResponse requestStatisticProducts() throws ConnectException {

		BuilderRequest builderRequest = new BuilderRequest(TYPE_STATISTIC_PRODUCTS);
		
		builderRequest.addRequestDatas(PARAM_NUMBER_PRODUCT, String.valueOf(
				CacheService.getInstance().getSearchString()));
		builderRequest.addRequestDatas(PARAM_BEGIN_DATE, 
				CacheService.getInstance().getFirstDate());
		builderRequest.addRequestDatas(PARAM_END_DATE, 
				CacheService.getInstance().getLastDate());
		
		return send(builderRequest.buildRequest());
	}
	
	public DataResponse requestStatisticUsers() throws ConnectException {

		BuilderRequest builderRequest = new BuilderRequest(TYPE_STATISTIC_USERS);
		
		builderRequest.addRequestDatas(PARAM_NUMBER_PRODUCT, String.valueOf(
				CacheService.getInstance().getSearchString()));
		builderRequest.addRequestDatas(PARAM_BEGIN_DATE, 
				CacheService.getInstance().getFirstDate());
		builderRequest.addRequestDatas(PARAM_END_DATE, 
				CacheService.getInstance().getLastDate());
		
		return send(builderRequest.buildRequest());
	}
	
	public DataResponse requestStatisticOrders() throws ConnectException {

		BuilderRequest builderRequest = new BuilderRequest(TYPE_STATISTIC_ORDERS);
		
		builderRequest.addRequestDatas(PARAM_NUMBER_PRODUCT, String.valueOf(
				CacheService.getInstance().getSearchString()));
		builderRequest.addRequestDatas(PARAM_BEGIN_DATE, 
				CacheService.getInstance().getFirstDate());
		builderRequest.addRequestDatas(PARAM_END_DATE, 
				CacheService.getInstance().getLastDate());
		
		return send(builderRequest.buildRequest());
	}
	
	public DataResponse requestStatisticOrdersForYear() throws ConnectException {

		BuilderRequest builderRequest = new BuilderRequest(TYPE_STATISTIC_ORDERS_FOR_YEAR);
		
		builderRequest.addRequestDatas(PARAM_YEAR, String.valueOf(
				CacheService.getInstance().getStatisticYear()));
		
		return send(builderRequest.buildRequest());
	}
	
	public DataResponse requestStatisticOrdersForMonth() throws ConnectException {

		BuilderRequest builderRequest = new BuilderRequest(TYPE_STATISTIC_ORDERS_FOR_MONTH);
		
		builderRequest.addRequestDatas(PARAM_YEAR, String.valueOf(
				CacheService.getInstance().getStatisticYear()));
		builderRequest.addRequestDatas(PARAM_MONTH, String.valueOf(
				CacheService.getInstance().getStatisticMonth()));
		
		return send(builderRequest.buildRequest());
	}
	
	public DataResponse send(DataRequest data) throws ConnectException {
		ServerConnect connect = new ServerConnect();
		connect.connectToServer();
		connect.sendRequest(data);
		DataResponse response = connect.getResponse();
		connect.closeConnect();
		return response;
	}
}
