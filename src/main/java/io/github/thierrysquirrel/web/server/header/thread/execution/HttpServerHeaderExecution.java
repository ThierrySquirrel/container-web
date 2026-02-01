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

package io.github.thierrysquirrel.web.server.header.thread.execution;

import io.github.thierrysquirrel.hummingbird.core.extend.http.core.domain.HttpRequestContext;
import io.github.thierrysquirrel.hummingbird.core.extend.http.core.domain.builder.HttpRequestContextBuilder;
import io.github.thierrysquirrel.hummingbird.core.facade.SocketChannelFacade;
import io.github.thierrysquirrel.web.container.reflect.WebContainerReflect;
import io.github.thierrysquirrel.web.container.response.HttpResponseContainer;
import io.github.thierrysquirrel.web.intercept.GlobalHttpRequestIntercept;
import io.github.thierrysquirrel.web.intercept.GlobalHttpResponseIntercept;
import io.github.thierrysquirrel.web.loading.WebLoading;
import io.github.thierrysquirrel.web.serialize.WebSerializeUtils;
import io.github.thierrysquirrel.web.server.header.factory.HttpServerHeaderFactory;
import io.github.thierrysquirrel.web.server.header.thread.AbstractHttpServerHeader;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classname: HttpServerHeaderExecution
 * Description:
 * Date:2026/2/1
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class HttpServerHeaderExecution extends AbstractHttpServerHeader {
    private static final Logger logger = Logger.getLogger(HttpServerHeaderExecution.class.getName());

    public HttpServerHeaderExecution(WebLoading webLoading, GlobalHttpRequestIntercept globalHttpRequestIntercept, GlobalHttpResponseIntercept globalHttpResponseIntercept, SocketChannelFacade<HttpRequestContext> socketChannelFacade, HttpRequestContext httpRequestContext) {
        super(webLoading, globalHttpRequestIntercept, globalHttpResponseIntercept, socketChannelFacade, httpRequestContext);
    }

    @Override
    protected void channelMessage(WebLoading webLoading, GlobalHttpRequestIntercept globalHttpRequestIntercept, GlobalHttpResponseIntercept globalHttpResponseIntercept, SocketChannelFacade<HttpRequestContext> socketChannelFacade, HttpRequestContext httpRequestContext) {
        boolean requestIntercept = HttpServerHeaderFactory.requestIntercept(globalHttpRequestIntercept, socketChannelFacade);
        if (requestIntercept) {
            return;
        }

        WebContainerReflect reflect = HttpServerHeaderFactory.getReflect(httpRequestContext);
        if (Objects.isNull(reflect)) {
            HttpServerHeaderFactory.responseError(webLoading, socketChannelFacade);
            return;
        }

        try {
            Object reflectValue = HttpServerHeaderFactory.reflect(httpRequestContext, reflect);
            boolean responseIntercept = HttpServerHeaderFactory.responseIntercept(globalHttpResponseIntercept, socketChannelFacade);
            if (responseIntercept) {
                return;
            }
            HttpRequestContext response = HttpResponseContainer.get();
            HttpRequestContextBuilder.builderJsonResponse(response, WebSerializeUtils.serializeJson(reflectValue));
            sendMessage(socketChannelFacade, response);
        } catch (Exception e) {
            String logMsg = "reflect Error";
            logger.log(Level.WARNING, logMsg, e);
            HttpServerHeaderFactory.responseError(webLoading, socketChannelFacade);
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
