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

package io.github.thierrysquirrel.web.server.header;

import io.github.thierrysquirrel.container.scanner.annotation.Registration;
import io.github.thierrysquirrel.container.scanner.annotation.Set;
import io.github.thierrysquirrel.hummingbird.core.extend.http.core.domain.HttpRequestContext;
import io.github.thierrysquirrel.hummingbird.core.facade.SocketChannelFacade;
import io.github.thierrysquirrel.hummingbird.core.handler.HummingbirdHandler;
import io.github.thierrysquirrel.web.intercept.GlobalHttpRequestIntercept;
import io.github.thierrysquirrel.web.intercept.GlobalHttpResponseIntercept;
import io.github.thierrysquirrel.web.loading.WebLoading;
import io.github.thierrysquirrel.web.server.header.thread.execution.HttpServerHeaderExecution;
import io.github.thierrysquirrel.web.thread.pool.constant.WebThreadPoolConstant;

import java.net.SocketAddress;
import java.util.logging.Logger;

/**
 * Classname: HttpServerHeader
 * Description:
 * Date:2026/2/1
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
@Registration
public class HttpServerHeader implements HummingbirdHandler<HttpRequestContext> {
    private static final Logger logger = Logger.getLogger(HttpServerHeader.class.getName());

    @Set
    private WebLoading webLoading;

    @Set
    private GlobalHttpRequestIntercept globalHttpRequestIntercept;

    @Set
    private GlobalHttpResponseIntercept globalHttpResponseIntercept;

    @Override
    public void channelMessage(SocketChannelFacade<HttpRequestContext> socketChannelFacade, HttpRequestContext httpRequestContext) {
        HttpServerHeaderExecution httpServerHeaderExecution = new HttpServerHeaderExecution(this.webLoading,
                this.globalHttpRequestIntercept,
                this.globalHttpResponseIntercept,
                socketChannelFacade, httpRequestContext);
        WebThreadPoolConstant.HTTP_SERVER_HEADER.execute(httpServerHeaderExecution);
    }

    @Override
    public void channelTimeout(SocketChannelFacade<HttpRequestContext> socketChannelFacade) {
        logger.info("timeout");
        socketChannelFacade.close();
    }

    @Override
    public void channelClose(SocketAddress remoteAddress, SocketAddress localAddress) {
        String format = String.format("channelClose remoteAddress:%s localAddress:%s", remoteAddress, localAddress);
        logger.info(format);
    }
}
