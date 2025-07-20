package API_test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

public class GETUsers {
    Playwright playwright;
    APIRequest request;
    APIRequestContext requestContext;

    @BeforeTest
    public void setup() {
        playwright = Playwright.create();
        request = playwright.request();
        requestContext = request.newContext();
    }

    @Test
    public void GetUsers() throws IOException {

        APIResponse response = requestContext.get("https://gorest.co.in/public/v2/users");
        int status = response.status();
        System.out.println("Print status code");
        System.out.println("Status code is " + status);
        Assert.assertEquals(status, 200);
        Assert.assertEquals(response.ok(), true);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(response.body());
        String toPrettyResponse = jsonResponse.toPrettyString();
        System.out.println("Print Response");
        System.out.println(toPrettyResponse);
        Map<String, String> responseHeaders = response.headers();
        Assert.assertEquals(responseHeaders.get("content-type"), "application/json; charset=utf-8");
        System.out.println("Print API url");
        System.out.println(response.url());

    }
    @Test
    public void getSpecificUser(){
        APIResponse response = requestContext.get("https://gorest.co.in/public/v2/users", RequestOptions.create()
                .setQueryParam("gender","male")
                .setQueryParam("status","active"));
        int status = response.status();
        Assert.assertEquals(status,200);
    }
    @AfterTest
    public void tearDown() {
        playwright.close();
    }
}