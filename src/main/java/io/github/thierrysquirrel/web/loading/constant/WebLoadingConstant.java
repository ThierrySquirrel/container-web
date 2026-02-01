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

package io.github.thierrysquirrel.web.loading.constant;

/**
 * Classname: WebLoadingConstant
 * Description:
 * Date:2026/2/1
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class WebLoadingConstant {
    private WebLoadingConstant() {
    }

    public static final String DEFAULT_URL = "127.0.0.1:8080";
    public static final int DEFAULT_READ_HEARTBEAT_TIME = 4000;
    public static final int DEFAULT_WRITE_HEARTBEAT_TIME = 0;
    public static final String DEFAULT_HTTP_ERROR_URL = "/web/error";
    public static final String DEFAULT_HTTP_ERROR_METHOD = "POST";
}
