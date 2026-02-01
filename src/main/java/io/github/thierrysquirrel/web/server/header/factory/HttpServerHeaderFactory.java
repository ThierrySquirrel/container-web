/**
 * Copyright 2026/2/1 ThierrySquirrel
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package io.github.thierrysquirrel.web.server.header.factory;

import io.github.thierrysquirrel.hummingbird.core.extend.http.core.coder.constant.UrlCoderConstant;
import io.github.thierrysquirrel.hummingbird.core.extend.http.core.coder.factory.HttpUrlCoderRootChainFactory;
import io.github.thierrysquirrel.hummingbird.core.extend.http.core.coder.server.factory.HttpServerBodyDecoderFactory;
import io.github.thierrysquirrel.hummingbird.core.extend.http.core.coder.server.factory.domain.HttpFormData;
import io.github.thierrysquirrel.hummingbird.core.extend.http.core.domain.HttpRequestContext;
import io.github.thierrysquirrel.hummingbird.core.extend.http.core.domain.builder.HttpRequestContextBuilder;
import io.github.thierrysquirrel.hummingbird.core.extend.http.core.domain.constant.HttpHeaderValueConstant;
import io.github.thierrysquirrel.hummingbird.core.extend.http.core.factory.HttpHeaderFactory;
import io.github.thierrysquirrel.hummingbird.core.facade.SocketChannelFacade;
import io.github.thierrysquirrel.web.container.WebContainer;
import io.github.thierrysquirrel.web.container.reflect.WebContainerReflect;
import io.github.thierrysquirrel.web.container.request.HttpRequestContainer;
import io.github.thierrysquirrel.web.container.response.HttpResponseContainer;
import io.github.thierrysquirrel.web.intercept.GlobalHttpRequestIntercept;
import io.github.thierrysquirrel.web.intercept.GlobalHttpResponseIntercept;
import io.github.thierrysquirrel.web.loading.WebLoading;
import io.github.thierrysquirrel.web.serialize.WebSerializeUtils;
import io.github.thierrysquirrel.web.server.header.factory.template.HttpServerHeaderFactoryTemplate;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classname: HttpServerHeaderFactory
 * Description:
 * Date:2026/2/1
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class HttpServerHeaderFactory {
    private HttpServerHeaderFactory() {
    }

    private static final Logger logger = Logger.getLogger(HttpServerHeaderFactory.class.getName());

    public static boolean requestIntercept(GlobalHttpRequestIntercept globalHttpRequestIntercept, SocketChannelFacade<HttpRequestContext> socketChannelFacade) {
        if (Objects.nonNull(globalHttpRequestIntercept)) {
            HttpRequestContext localRequestContext = HttpRequestContainer.get();
            HttpRequestContext localResponseContext = HttpResponseContainer.get();
            boolean responseIntercept = globalHttpRequestIntercept.requestIntercept(localRequestContext, localResponseContext);
            if (responseIntercept) {
                sendMessage(socketChannelFacade, localResponseContext);
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public static WebContainerReflect getReflect(HttpRequestContext httpRequestContext) {
        String httpUri = httpRequestContext.getHttpRequest().getHttpUri();
        String uri = httpUri;
        int indexTag = httpUri.indexOf(UrlCoderConstant.QUESTION_MARK);
        if (indexTag != -1) {
            uri = httpUri.substring(0, indexTag);
        }
        String httpMethod = httpRequestContext.getHttpRequest().getHttpMethod();
        return WebContainer.getReflect(httpMethod, uri);


    }

    public static Object reflect(HttpRequestContext httpRequestContext, WebContainerReflect webContainerReflect) throws Exception {
        String httpUri = httpRequestContext.getHttpRequest().getHttpUri();
        Map<String, String> urlMap = HttpUrlCoderRootChainFactory.createUrlMap(httpUri);
        Map<String, String> httpHeader = httpRequestContext.getHttpHeader();
        Map<String, HttpFormData> httpFormDataMap = null;
        Map<String, String> httpFormUrlencoded = null;
        String jsonBody = null;

        boolean isFormData = HttpHeaderFactory.equalsIgnoreCaseContentType(httpHeader, HttpHeaderValueConstant.FORM_DATA);
        if (isFormData) {
            httpFormDataMap = HttpServerBodyDecoderFactory.builderFormData(httpRequestContext);
        }

        boolean isFormUrlencoded = HttpHeaderFactory.equalsIgnoreCaseContentType(httpHeader, HttpHeaderValueConstant.FORM_URLENCODED);
        if (isFormUrlencoded) {
            httpFormUrlencoded = HttpServerBodyDecoderFactory.builderFormUrlencoded(httpRequestContext);
        }
        boolean isJsonBody = HttpHeaderFactory.equalsIgnoreCaseContentType(httpHeader, HttpHeaderValueConstant.JSON);
        if (isJsonBody) {
            jsonBody = HttpServerBodyDecoderFactory.builderText(httpRequestContext);
        }

        Object httpObject = webContainerReflect.getHttpObject();
        Method httpMethod = webContainerReflect.getHttpMethod();

        Parameter[] methodParameters = httpMethod.getParameters();
        Object[] params = new Object[methodParameters.length];

        for (int i = 0; i < methodParameters.length; i++) {

            String typeName = methodParameters[i].getType().getTypeName();
            Class<?> type = methodParameters[i].getType();
            for (Annotation annotation : methodParameters[i].getAnnotations()) {
                Object parameterValue = HttpServerHeaderFactoryTemplate.findMethodParameterValue(annotation, typeName,
                        urlMap, httpFormDataMap, httpFormUrlencoded, type, jsonBody);
                if (Objects.nonNull(parameterValue)) {
                    params[i] = parameterValue;
                }
            }
        }
        return httpMethod.invoke(httpObject, params);

    }


    public static boolean responseIntercept(GlobalHttpResponseIntercept globalHttpResponseIntercept, SocketChannelFacade<HttpRequestContext> socketChannelFacade) {
        if (Objects.nonNull(globalHttpResponseIntercept)) {
            HttpRequestContext localRequestContext = HttpRequestContainer.get();
            HttpRequestContext localResponseContext = HttpResponseContainer.get();
            boolean responseIntercept = globalHttpResponseIntercept.responseIntercept(localRequestContext, localResponseContext);
            if (responseIntercept) {
                sendMessage(socketChannelFacade, localResponseContext);
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public static void responseError(WebLoading webLoading, SocketChannelFacade<HttpRequestContext> socketChannelFacade) {
        try {
            String httpErrorUrl = webLoading.getHttpErrorUrl();
            String httpErrorMethod = webLoading.getHttpErrorMethod();

            WebContainerReflect reflect = WebContainer.getReflect(httpErrorMethod, httpErrorUrl);
            Object object = reflect.getHttpObject();
            Method reflectMethod = reflect.getHttpMethod();
            Object invokeValue = reflectMethod.invoke(object);

            HttpRequestContext requestContext = HttpResponseContainer.get();
            HttpRequestContextBuilder.builderJsonResponse(requestContext, WebSerializeUtils.serializeJson(invokeValue));
            sendMessage(socketChannelFacade, requestContext);
        } catch (Exception e) {
            String logMsg = "response Error";
            logger.log(Level.WARNING, logMsg, e);
        }

    }

    private static void sendMessage(SocketChannelFacade<HttpRequestContext> socketChannelFacade, HttpRequestContext httpRequestContext) {
        try {
            socketChannelFacade.sendMessage(httpRequestContext);
        } catch (IOException e) {
            String logMsg = "sendMessage Error";
            logger.log(Level.WARNING, logMsg, e);
        }
    }
}
