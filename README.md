
# Container Web

Container Web

[中文](./README_zh_CN.md)

Support function:
- [x] Load Java.ClassLoading file
- [x] Global HttpRequest Intercept
- [x] HTTP Server
- [x] Global HttpResponse Intercept

# Load Java.ClassLoading file
Load Java.ClassLoading file, located in the resources directory

# Global HttpRequest Intercept:
Intercept HttpRequest

# HTTP Server:
Start processing HTTP data

# Global HttpResponse Intercept:
Intercept HttpResponse

## Quick Start

```xml
<!--Adding dependencies to pom. XML-->
        <dependency>
            <groupId>io.github.thierrysquirrel</groupId>
            <artifactId>container-web</artifactId>
            <version>1.0.0.0-RELEASE</version>
        </dependency>
```

# Load Java.ClassLoading file:

 ```properties
 ## Java.ClassLoading
Class.forName=io.github.thierrysquirrel.web.loading.WebLoading
Method.setUrl.String=127.0.0.1:8080
Method.setReadHeartbeatTime.int=4000
Method.setWriteHeartbeatTime.int=0
Method.setHttpErrorUrl.String=/web/error
Method.setHttpErrorMethod.String=POST
 ```

# Global HttpRequest Intercept:
```java
public class GlobalHttpRequestInterceptImpl implements GlobalHttpRequestIntercept {
    @Override
    public boolean requestIntercept(HttpRequestContext request, HttpRequestContext response) {
        String httpUri = request.getHttpRequest().getHttpUri();
        if (httpUri.startsWith("/requestIntercept")) {

            response.getHttpHeader().put("Intercept", "Request-Intercept");
            HttpRequestContextBuilder.builderTextResponse(response, "Request-Intercept");
            return true;
        }
        return false;
    }
}
```

# HTTP Server:

```java
@ScannerPackage(packageName = "com.hello.world.web")
public class WebRegistrationImpl implements InterfaceManualRegistration  {
    @Override
    public void scannerAll(List<Class<?>> scannerClassList, Map<Class<?>, Object> registrationMap) {
        WebRegistration.webRegistrationScannerAll(scannerClassList, registrationMap);
    }
}
```

```java
@Http("/web")
public class HttpDemo {
    @Set
    private WebLoading webLoading;

    // Get,Post,Put,Patch,Delete,Head,Options

    // UrlParam,FormDataParam,FormDataFileParam,FormUrlEncodedParam
    // FormDataPojo,FormUrlEncodedPojo,JsonPojo
    @Post("/hello")
    public String hello(@UrlParam("paramA") String paramA, @UrlParam("paramB") int paramB,
                        @UrlParam("paramC") Boolean paramC,
                        @FormDataParam("formString") String formString,
                        @FormDataParam("formInt") int formInt,
                        @FormDataFileParam("file") HttpFormData file) {


        System.out.println(webLoading.toString());

        System.out.println("HTTP client");
        System.out.println("paramA: " + paramA);
        System.out.println("paramB: " + paramB);
        System.out.println("paramC: " + paramC);
        System.out.println("formString: " + formString);
        System.out.println("formInt: " + formInt);
        boolean isFile = file.isFile();
        if (isFile) {
            HttpFormDataBodyFactory.writeFile(file, "/cache/" + file.getFileName());
            System.out.println("fileName::" + file.getFileName());
        } else {
            System.out.println("file: " + file.getValue());

        }
        return "helloWrold";
    }

    @Post("/formDataBody")
    public User formDataBody(@FormDataPojo User user) {
        System.out.println("formDataBody");
        return user;
    }

    @Post("/error")
    public String error() {
        System.out.println("error");
        return "error";
    }

}
```

```java
public class MainInit {
    public static void main(String[] args) {
        WebInit.webInit(MainInit.class);
    }
}
```

# Global HttpResponse Intercept:

```java
@ResponseIntercept
public class GlobalHttpResponseInterceptImpl implements GlobalHttpResponseIntercept {
    @Override
    public boolean responseIntercept(HttpRequestContext request, HttpRequestContext response) {
        String hello = response.getHttpHeader().get("hello");
        if(!Objects.isNull(hello)){
            response.getHttpHeader().put("Response","Response-Intercept");
            HttpRequestContextBuilder.builderTextResponse(response,"Response-Intercept");
            return true;
        }
        return false;
    }
}
```