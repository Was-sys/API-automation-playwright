package API_test;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Map;

public class GetAPIHeaders {
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
    public void getHeaders(){
        APIResponse response = requestContext.get("https://gorest.co.in/public/v2/users");
        int status = response.status();
        Assert.assertEquals(status,200);
        Map<String,String> headersMap = response.headers();
        headersMap.forEach((k,v) -> System.out.println(k +" : "+ v));
        System.out.println(headersMap.size());
        Assert.assertEquals(headersMap.get("server"),("cloudflare"));
    }
    @AfterTest
    public void tearDown(){
        playwright.close();
    }
}
