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

package io.github.thierrysquirrel.web.container;

import io.github.thierrysquirrel.jellyfish.concurrency.map.hash.ConcurrencyHashMap;
import io.github.thierrysquirrel.web.constant.WebConstant;
import io.github.thierrysquirrel.web.container.reflect.WebContainerReflect;

import java.util.Objects;

/**
 * Classname: WebContainer
 * Description:
 * Date:2026/2/1
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class WebContainer {
    private WebContainer() {
    }

    private static final ConcurrencyHashMap<String, ConcurrencyHashMap<String, WebContainerReflect>> METHOD_URL_CONTAINER = new ConcurrencyHashMap<>(WebConstant.DEFAULT_MAP_OFFSET);

    public static WebContainerReflect getReflect(String method, String url) {
        ConcurrencyHashMap<String, WebContainerReflect> urlMap = METHOD_URL_CONTAINER.get(method);
        if (Objects.isNull(urlMap)) {
            urlMap = buildUrlMap(method);
        }

        return urlMap.get(url);
    }

    public static void setReflect(String method, String url, WebContainerReflect reflect) {
        ConcurrencyHashMap<String, WebContainerReflect> urlMap = METHOD_URL_CONTAINER.get(method);
        if (Objects.isNull(urlMap)) {
            urlMap = buildUrlMap(method);
        }
        urlMap.set(url, reflect);
    }

    private static ConcurrencyHashMap<String, WebContainerReflect> buildUrlMap(String method) {
        ConcurrencyHashMap<String, WebContainerReflect> urlMap = new ConcurrencyHashMap<>(WebConstant.DEFAULT_MAP_OFFSET);
        METHOD_URL_CONTAINER.set(method, urlMap);
        return urlMap;
    }
}
