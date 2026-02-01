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

package io.github.thierrysquirrel.web.annotation.method.constant;

/**
 * Classname: WebAnnotationMethodConstant
 * Description:
 * Date:2026/2/1
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class WebAnnotationMethodConstant {
    private WebAnnotationMethodConstant() {
    }

    public static final String GET = "io.github.thierrysquirrel.web.annotation.method.Get";
    public static final String POST = "io.github.thierrysquirrel.web.annotation.method.Post";
    public static final String PUT = "io.github.thierrysquirrel.web.annotation.method.Put";
    public static final String PATCH = "io.github.thierrysquirrel.web.annotation.method.Patch";
    public static final String DELETE = "io.github.thierrysquirrel.web.annotation.method.Delete";
    public static final String HEAD = "io.github.thierrysquirrel.web.annotation.method.Head";
    public static final String OPTIONS = "io.github.thierrysquirrel.web.annotation.method.Options";
}
