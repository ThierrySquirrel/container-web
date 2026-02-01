
# Container Web

Web 容器

[English](./README.md)

支持功能:
- [x] 加载Java.ClassLoading文件
- [x] 全局HttpRequest拦截
- [x] HTTP服务器
- [x] 全局HttpResponse拦截

# 加载Java.ClassLoading文件:
加载Java.ClassLoading文件,位于resources目录中

# 全局HttpRequest拦截:
拦截HttpRequest

# HTTP服务器:
开始处理HTTP数据

# 全局HttpResponse拦截:
拦截HttpResponse:


## Quick Start

```xml
<!--Adding dependencies to pom. XML-->
        <dependency>
            <groupId>io.github.thierrysquirrel</groupId>
            <artifactId>container-web</artifactId>
            <version>1.0.0.0-RELEASE</version>
        </dependency>
```

# 加载Java.ClassLoading文件:

 ```properties
 ## Java.ClassLoading
Class.forName=io.github.thierrysquirrel.web.loading.WebLoading
Method.setUrl.String=127.0.0.1:8080
Method.setReadHeartbeatTime.int=4000
Method.setWriteHeartbeatTime.int=0
Method.setHttpErrorUrl.String=/web/error
Method.setHttpErrorMethod.String=POST
 ```

# 全局HttpRequest拦截:
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

# HTTP服务器:

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

# 全局HttpResponse:

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