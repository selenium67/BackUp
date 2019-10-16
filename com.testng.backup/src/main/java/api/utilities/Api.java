package com.aviall.api;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aviall.utilities.GenericMethods;
import com.aviall.utilities.GlobalConstants;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;

public class Api {
	
	protected  String baseURI ="";
	protected static String accessToken="";
	protected static PrintStream ps;
	protected static ExtentTest test;
	protected static final String baseSiteId = "aviall";
	private Response resp;
	private ExtentTest node;
	private static String tokenuri="";

	private static final String oAuth= "/oauth/token";
	public static Map<String, String> creds;
	static {
		creds = new HashMap<String, String>();
		creds.put("client_id", "trusted_client");
		creds.put("client_secret", "secret");
		creds.put("grant_type", "password");
		creds.put("password", "Aviall321");
	}


	protected Api(String username) {
		String env = System.getProperty("env");
		ps=Utils.getPrintStream();
		switch(env) {
		case "Q2":
			baseURI=GlobalConstants.QA_HOST;
			tokenuri = GlobalConstants.QA_TOKEN;
			break;
		case"D2":
			baseURI=GlobalConstants.D2_HOST;
			tokenuri = GlobalConstants.D2_TOKEN;
			break;
		default:
			baseURI=GlobalConstants.QA_HOST;
			tokenuri = GlobalConstants.QA_TOKEN;	
		}
		generateAccessToken(username);
	}
	
	protected Api(Response resp, ExtentTest node) {
		this.resp = resp;
		this.node = node;
	}

	

	public Response getResp() {
		return resp;
	}

	public ExtentTest getNode() {
		return node;
	}

	static String setEnv() {
		String env = System.getProperty("env");
		if(env.equals("Q2")) {
			return  GlobalConstants.QA_HOST;
		}else if(env.equals("DEV")) {
			return GlobalConstants.D2_HOST;
		}
		return GlobalConstants.QA_HOST;
		
		//generateToken(environmentName, "", "");
	}

	

	protected  RequestSpecBuilder requestSpec() {
		RequestSpecBuilder rsp = new RequestSpecBuilder();
		rsp.setBaseUri(baseURI);
		rsp.setProxy(GlobalConstants.PRXY, 9090);
		rsp.setConfig(RestAssuredConfig.config().logConfig(new LogConfig(ps,true)));
		return rsp;

	}

	protected  RequestSpecBuilder requestSpec(String baseUri) {
		RequestSpecBuilder rsp = new RequestSpecBuilder();
		rsp.setBaseUri(baseUri);
		rsp.setProxy(GlobalConstants.PRXY, 9090);
		rsp.setConfig(RestAssuredConfig.config().logConfig(new LogConfig(ps,true)));
		return rsp;

	}

	protected  RequestSpecBuilder requestSpecWithToken() {
		RequestSpecBuilder rsp = requestSpec();
		rsp.addHeader("Authorization", accessToken);
		return rsp;

	}


	protected  RequestSpecBuilder requestSpecWithTokenWithBody() {
		RequestSpecBuilder rsp = requestSpecWithToken();
		rsp.setContentType("application/json");
		return rsp;

	}

	protected  void printlineRequest() {
		ps.println("****** REQUEST *********************************************************************************");
		ps.println();
	}


	protected  void printlineResponse() {
		ps.println();
		ps.println("----------------------RESPONSE----------------------------------------");
		ps.println();
	}

	protected  Api requestPostPut(Method method,String resource,String reqBody, Map<String,Object>pathParam) {
		printlineRequest();
		Response resp = given()
				.spec(requestSpecWithTokenWithBody().build())
				.body(reqBody)
				.pathParams(pathParam)
				.log().all()
				.when()
				.request(method, resource)
				.andReturn();		
		printlineResponse();		
		resp.then().log().all();
		ps.println();	
		
		ExtentTest node = GenericMethods.logger.createNode(method.name()+" - "+resource);
		String code = method.toString() + ":"+baseURI+resource +"\n"+pathParam.toString();
		node.info(MarkupHelper.createCodeBlock(code));
		node.info(MarkupHelper.createCodeBlock(reqBody, CodeLanguage.JSON));
		node.info(MarkupHelper.createCodeBlock(resp.getBody().asString(), CodeLanguage.JSON));		
		System.out.println(method.toString() + ":"+baseURI+resource);		
		return new Api(resp, node);	
	}


	protected Api request(Method method, String resource, Map<String,Object>pathParam) {
		printlineRequest();		
		Response resp = given()
				.spec(requestSpecWithToken().build())
				.pathParams(pathParam)
				.log().all()
				.when()
				.request(method, resource)
				.andReturn();
		printlineResponse();
		resp.then().log().all();
		ps.println();
		
		ExtentTest node = GenericMethods.logger.createNode(method.name()+" - "+resource);
		String code = method.toString() + ":"+baseURI+resource +"\n"+pathParam.toString();
		node.info(MarkupHelper.createCodeBlock(code));
		node.info(MarkupHelper.createCodeBlock(resp.getBody().asString(), CodeLanguage.JSON));
		
		System.out.println(method.toString() + ":"+baseURI+resource);
		
		return new Api(resp, node);	
	}

	protected  Api request(Method method, String resource, Map<String,Object> pathParam, 
			Map<String,Object>queryParam) {
		printlineRequest();		
		Response resp = null;
		//test.info(method.toString() + ":"+baseURI+resource );
		
			resp = given()
					.spec(requestSpecWithToken().build())
					.queryParams(queryParam)
					.pathParams(pathParam)
					.log().all()
					.when()
					.request(method, resource)
					.andReturn();
			printlineResponse();
			resp.then().log().all();
			ps.println();

			ExtentTest node = GenericMethods.logger.createNode(method.toString()+"-"+resource);
			String code = method.toString() + ":"+baseURI+resource +"\n"+pathParam.toString()+"\n"+queryParam.toString();
			node.info(MarkupHelper.createCodeBlock(code));
			node.info(MarkupHelper.createCodeBlock(resp.getBody().asString(), CodeLanguage.JSON));

		System.out.println(method.toString() + ":"+baseURI+resource);

		return new Api(resp, node);		
	}

	protected  Response request(Method method, String resource, String pathParam, String pathParam2) {
		printlineRequest();		
		Response resp = given()
				.spec(requestSpecWithToken().build())
				.log().all()
				.when()
				.request(method, resource, pathParam, pathParam2)
				.andReturn();
		printlineResponse();
		resp.then().log().all();
		ps.println();
		test.info(method.toString() + ":"+baseURI+resource);
		test.info(pathParam+" , "+ pathParam2 );
		System.out.println(method.toString() + ":"+baseURI+resource);

		return resp;	
	}

	public Map<String, Object> getbaseSitepathParams(){
		Map<String, Object> pathParams= new HashMap<String, Object>();
		pathParams.put("baseSiteId", baseSiteId);
		return pathParams;
	}

	public  String generateAccessToken(String username) {
		printlineRequest();
		creds.put("username", username);
		
		Response resp = given()
				.spec(requestSpec(tokenuri).build())
				.formParams(creds)
				.contentType(ContentType.URLENC)
				.log().all()
				.when()
				.request(Method.POST, oAuth)
				.andReturn();
		printlineResponse();
		resp.then().log().all();
		
		ps.println();
		String code = Method.POST.toString() + ":"+tokenuri+oAuth 
				+ "\n" +creds.toString();
		
		ExtentTest node = GenericMethods.logger.createNode(Method.POST.toString()+"-"+oAuth, "Token Generation");	
		node.info(MarkupHelper.createCodeBlock(code));
		node.info(MarkupHelper.createCodeBlock(resp.getBody().asString(), CodeLanguage.JSON));
		
		Api api = new Api(resp,node);
		System.out.println(Method.POST.toString() + ":"+tokenuri+oAuth );
		Verify execute = new Verify.Builder(api, 200)
				.verifyJsonPathExistence("$.access_token")
				.execute();
		if(execute.getStatus()) {
			accessToken = "Bearer "+new JsonPath(resp.body().asString()).get("access_token");			
		}else {
			node.log(Status.FAIL,MarkupHelper.createLabel("Token generation failed: ", ExtentColor.RED));
		}
		return accessToken;
	}


}
