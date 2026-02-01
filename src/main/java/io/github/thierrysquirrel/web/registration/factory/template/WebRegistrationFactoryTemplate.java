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

package io.github.thierrysquirrel.web.registration.factory.template;

import io.github.thierrysquirrel.hummingbird.core.extend.http.core.domain.constant.HttpMethodConstant;
import io.github.thierrysquirrel.web.annotation.method.*;
import io.github.thierrysquirrel.web.annotation.method.constant.WebAnnotationMethodConstant;
import io.github.thierrysquirrel.web.registration.pojo.WebRegistrationPojo;

import java.lang.annotation.Annotation;

/**
 * Classname: WebRegistrationFactoryTemplate
 * Description:
 * Date:2026/2/1
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class WebRegistrationFactoryTemplate {
    private WebRegistrationFactoryTemplate() {
    }

    public static WebRegistrationPojo template(String typeName, String url, Annotation annotation) {
        return switch (typeName) {
            case WebAnnotationMethodConstant.GET -> buildWebRegistrationPojo(url, (Get) annotation);
            case WebAnnotationMethodConstant.POST -> buildWebRegistrationPojo(url, (Post) annotation);
            case WebAnnotationMethodConstant.PUT -> buildWebRegistrationPojo(url, (Put) annotation);
            case WebAnnotationMethodConstant.PATCH -> buildWebRegistrationPojo(url, (Patch) annotation);
            case WebAnnotationMethodConstant.DELETE -> buildWebRegistrationPojo(url, (Delete) annotation);
            case WebAnnotationMethodConstant.HEAD -> buildWebRegistrationPojo(url, (Head) annotation);
            case WebAnnotationMethodConstant.OPTIONS -> buildWebRegistrationPojo(url, (Options) annotation);
            default -> null;
        };
    }

    private static WebRegistrationPojo buildWebRegistrationPojo(String url, Get annotation) {
        url += annotation.value();
        return new WebRegistrationPojo(url, HttpMethodConstant.GET);
    }

    private static WebRegistrationPojo buildWebRegistrationPojo(String url, Post annotation) {
        url += annotation.value();
        return new WebRegistrationPojo(url, HttpMethodConstant.POST);
    }

    private static WebRegistrationPojo buildWebRegistrationPojo(String url, Put annotation) {
        url += annotation.value();
        return new WebRegistrationPojo(url, HttpMethodConstant.PUT);
    }

    private static WebRegistrationPojo buildWebRegistrationPojo(String url, Patch annotation) {
        url += annotation.value();
        return new WebRegistrationPojo(url, HttpMethodConstant.PATCH);
    }

    private static WebRegistrationPojo buildWebRegistrationPojo(String url, Delete annotation) {
        url += annotation.value();
        return new WebRegistrationPojo(url, HttpMethodConstant.DELETE);
    }

    private static WebRegistrationPojo buildWebRegistrationPojo(String url, Head annotation) {
        url += annotation.value();
        return new WebRegistrationPojo(url, HttpMethodConstant.HEAD);
    }

    private static WebRegistrationPojo buildWebRegistrationPojo(String url, Options annotation) {
        url += annotation.value();
        return new WebRegistrationPojo(url, HttpMethodConstant.OPTIONS);
    }
}
