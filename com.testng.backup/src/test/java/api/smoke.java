
package com.aviall.apitest;

import org.testng.annotations.Test;

import com.aviall.api.Api;
import com.aviall.api.Products;
import com.aviall.api.Quotes;
import com.aviall.api.Utils;
import com.aviall.api.Verify;
import com.aviall.utilities.GenericMethods;
import static com.aviall.api.Utils.getApiTestData;

public class SmokeTestAPI extends GenericMethods {


	@Test(priority = 0)
	public void productsAPI() {
		logger.assignAuthor("Subhash");
		Products prdt = new Products(getApiTestData("username"));

		Api searchProduct = prdt.searchProduct(getApiTestData("query"));
		new Verify.Builder(searchProduct,200)
		.verifyJsonPathExistence("$.products");


		Api productByCode = prdt.getProductByCode(getApiTestData("productCode"));
		new Verify.Builder(productByCode, 200)
		.verifyJsonPathExistence("$.name");
		
		Api productByCodePandA = prdt.getProductByCodePandA(getApiTestData("productCode"));
		new Verify.Builder(productByCodePandA, 200);
		
		Api suggestions = prdt.getSuggestions();
		new Verify.Builder(suggestions, 200);
		
		Api getPandAInfo =prdt.getPandAInformation(Utils.getPayload("getPandAInfoPayLoad.json"));
		new Verify.Builder(getPandAInfo, 201);

	}

	@Test(priority = 1)
	public void QuotesAPI(){
		logger.assignAuthor("Subhash");
		Quotes quote = new Quotes(getApiTestData("username"));

		//Create Quote
		Api createQuote = quote.createQuote(Utils.getPayload("quotePayLoad.json"));
		new Verify.Builder(createQuote, 201)
		.verifyJsonPathExistence("$.quoteReferenceNumber");
		//.execute();

		// Get Quotes
		Api quotes = quote.getQuotes("20");
		new Verify.Builder(quotes, 200);
		//.execute();

		String code = Utils.getJsonPathValue(quotes, "$.quotes[0].code");
		
		// Get QuoteDetails
		Api quoteDetails = quote.getQuoteDetails(code);
		new Verify.Builder(quoteDetails, 200);
			//.execute();
		
		Api removeQuote = quote.removeQuote(code);
		new Verify.Builder(removeQuote, 200);

	}





















































}
