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

package io.github.thierrysquirrel.web.registration.factory;

import io.github.thierrysquirrel.json.reflect.JsonReflect;
import io.github.thierrysquirrel.web.annotation.Http;
import io.github.thierrysquirrel.web.container.WebContainer;
import io.github.thierrysquirrel.web.container.reflect.WebContainerReflect;
import io.github.thierrysquirrel.web.intercept.GlobalHttpRequestIntercept;
import io.github.thierrysquirrel.web.intercept.GlobalHttpResponseIntercept;
import io.github.thierrysquirrel.web.registration.factory.template.WebRegistrationFactoryTemplate;
import io.github.thierrysquirrel.web.registration.pojo.WebRegistrationPojo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * Classname: WebRegistrationFactory
 * Description:
 * Date:2026/2/1
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class WebRegistrationFactory {
    private WebRegistrationFactory() {
    }

    public static void httpRegistration(Class<?> thisClass, Map<Class<?>, Object> registrationMap, Http http) {
        String url = http.value();
        Object webPojo = JsonReflect.newInstance(thisClass);
        registrationMap.put(thisClass, webPojo);

        for (Method method : thisClass.getMethods()) {
            for (Annotation annotation : method.getAnnotations()) {
                String typeName = annotation.annotationType().getTypeName();
                WebRegistrationPojo pojo = WebRegistrationFactoryTemplate.template(typeName, url, annotation);
                if (Objects.isNull(pojo)) {
                    continue;
                }
                WebContainerReflect reflect = new WebContainerReflect(webPojo, method);
                WebContainer.setReflect(pojo.getMethodName(), pojo.getUrl(), reflect);
            }

        }

    }

    public static void requestInterceptRegistration(Class<?> thisClass, Map<Class<?>, Object> registrationMap) {
        Object requestIntercept = JsonReflect.newInstance(thisClass);
        registrationMap.put(GlobalHttpRequestIntercept.class, requestIntercept);
    }

    public static void responseInterceptRegistration(Class<?> thisClass, Map<Class<?>, Object> registrationMap) {
        Object responseIntercept = JsonReflect.newInstance(thisClass);
        registrationMap.put(GlobalHttpResponseIntercept.class, responseIntercept);
    }
}
