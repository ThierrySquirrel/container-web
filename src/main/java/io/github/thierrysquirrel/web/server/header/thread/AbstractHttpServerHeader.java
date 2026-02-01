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

package io.github.thierrysquirrel.web.server.header.thread;

import io.github.thierrysquirrel.hummingbird.core.extend.http.core.domain.HttpRequestContext;
import io.github.thierrysquirrel.hummingbird.core.facade.SocketChannelFacade;
import io.github.thierrysquirrel.web.container.util.HttpContainerUtil;
import io.github.thierrysquirrel.web.intercept.GlobalHttpRequestIntercept;
import io.github.thierrysquirrel.web.intercept.GlobalHttpResponseIntercept;
import io.github.thierrysquirrel.web.loading.WebLoading;
import io.github.thierrysquirrel.web.server.header.factory.HttpServerHeaderFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classname: AbstractHttpServerHeader
 * Description:
 * Date:2026/2/1
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public abstract class AbstractHttpServerHeader implements Runnable {
    private static final Logger logger = Logger.getLogger(AbstractHttpServerHeader.class.getName());

    private WebLoading webLoading;
    private GlobalHttpRequestIntercept globalHttpRequestIntercept;
    private GlobalHttpResponseIntercept globalHttpResponseIntercept;
    private SocketChannelFacade<HttpRequestContext> socketChannelFacade;
    private HttpRequestContext httpRequestContext;

    protected AbstractHttpServerHeader(WebLoading webLoading, GlobalHttpRequestIntercept globalHttpRequestIntercept, GlobalHttpResponseIntercept globalHttpResponseIntercept, SocketChannelFacade<HttpRequestContext> socketChannelFacade, HttpRequestContext httpRequestContext) {
        this.webLoading = webLoading;
        this.globalHttpRequestIntercept = globalHttpRequestIntercept;
        this.globalHttpResponseIntercept = globalHttpResponseIntercept;
        this.socketChannelFacade = socketChannelFacade;
        this.httpRequestContext = httpRequestContext;
    }

    protected abstract void channelMessage(WebLoading webLoading, GlobalHttpRequestIntercept globalHttpRequestIntercept, GlobalHttpResponseIntercept globalHttpResponseIntercept, SocketChannelFacade<HttpRequestContext> socketChannelFacade, HttpRequestContext httpRequestContext);

    @Override
    public void run() {
        try {
            HttpContainerUtil.init(this.httpRequestContext);
            channelMessage(this.webLoading,
                    this.globalHttpRequestIntercept,
                    this.globalHttpResponseIntercept,
                    this.socketChannelFacade,
                    this.httpRequestContext);
        } catch (Exception e) {
            String logMsg = "AbstractHttpServerHeader Error";
            logger.log(Level.WARNING, logMsg, e);
            HttpServerHeaderFactory.responseError(this.webLoading,
                    this.socketChannelFacade);
        } finally {
            HttpContainerUtil.delete();
        }
    }

    public WebLoading getWebLoading() {
        return webLoading;
    }

    public void setWebLoading(WebLoading webLoading) {
        this.webLoading = webLoading;
    }

    public GlobalHttpRequestIntercept getGlobalHttpRequestIntercept() {
        return globalHttpRequestIntercept;
    }

    public void setGlobalHttpRequestIntercept(GlobalHttpRequestIntercept globalHttpRequestIntercept) {
        this.globalHttpRequestIntercept = globalHttpRequestIntercept;
    }

    public GlobalHttpResponseIntercept getGlobalHttpResponseIntercept() {
        return globalHttpResponseIntercept;
    }

    public void setGlobalHttpResponseIntercept(GlobalHttpResponseIntercept globalHttpResponseIntercept) {
        this.globalHttpResponseIntercept = globalHttpResponseIntercept;
    }

    public SocketChannelFacade<HttpRequestContext> getSocketChannelFacade() {
        return socketChannelFacade;
    }

    public void setSocketChannelFacade(SocketChannelFacade<HttpRequestContext> socketChannelFacade) {
        this.socketChannelFacade = socketChannelFacade;
    }

    public HttpRequestContext getHttpRequestContext() {
        return httpRequestContext;
    }

    public void setHttpRequestContext(HttpRequestContext httpRequestContext) {
        this.httpRequestContext = httpRequestContext;
    }
}
