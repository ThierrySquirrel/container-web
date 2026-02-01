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

package io.github.thierrysquirrel.web.thread.pool.builder;

import io.github.thierrysquirrel.jellyfish.thread.pool.ThreadPool;
import io.github.thierrysquirrel.web.thread.pool.builder.constant.WebThreadPoolBuilderConstant;

/**
 * Classname: WebThreadPoolBuilder
 * Description:
 * Date:2026/2/1
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class WebThreadPoolBuilder {
    private WebThreadPoolBuilder() {
    }

    public static ThreadPool buildeHttpServerHeaderThreadPool() {
        return new ThreadPool(WebThreadPoolBuilderConstant.HTTP_SERVER_HEADER_CORE_POOL_SIZE);
    }
}
