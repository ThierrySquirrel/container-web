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

package io.github.thierrysquirrel.web.container.response;

import io.github.thierrysquirrel.hummingbird.core.extend.http.core.domain.HttpRequestContext;
import io.github.thierrysquirrel.jellyfish.container.JellyfishContainer;
import io.github.thierrysquirrel.jellyfish.thread.local.map.ThreadLocalMap;
import io.github.thierrysquirrel.web.constant.WebConstant;

/**
 * Classname: HttpResponseContainer
 * Description:
 * Date:2026/2/1
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class HttpResponseContainer {
    private HttpResponseContainer() {
    }

    private static final ThreadLocalMap<HttpRequestContext> RESPONSE_CONTAINER = new ThreadLocalMap<>(WebConstant.DEFAULT_THREAD_LOCAL_MAP_SIZE);

    public static void set(HttpRequestContext defaultResponse) {
        RESPONSE_CONTAINER.set(defaultResponse);
    }

    public static HttpRequestContext get() {
        JellyfishContainer<HttpRequestContext> jellyfishContainer = RESPONSE_CONTAINER.get();
        if (jellyfishContainer.isEmpty()) {
            return null;
        }
        return jellyfishContainer.getValue();
    }

    public static void delete() {
        RESPONSE_CONTAINER.deleteValue();
    }
}
