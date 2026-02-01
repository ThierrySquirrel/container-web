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

package io.github.thierrysquirrel.web.registration;

import io.github.thierrysquirrel.web.annotation.Http;
import io.github.thierrysquirrel.web.registration.constant.WebRegistrationConstant;
import io.github.thierrysquirrel.web.registration.factory.WebRegistrationFactory;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

/**
 * Classname: WebRegistration
 * Description:
 * Date:2026/2/1
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class WebRegistration {
    private WebRegistration() {
    }

    public static void webRegistrationScannerAll(List<Class<?>> scannerClassList, Map<Class<?>, Object> registrationMap) {
        for (Class<?> thisClass : scannerClassList) {
            Annotation[] annotations = thisClass.getAnnotations();
            for (Annotation annotation : annotations) {
                findAnnotation(thisClass, registrationMap, annotation);
            }
        }
    }

    private static void findAnnotation(Class<?> thisClass, Map<Class<?>, Object> registrationMap, Annotation annotation) {
        String typeName = annotation.annotationType().getTypeName();
        switch (typeName) {
            case WebRegistrationConstant.HTTP ->
                    WebRegistrationFactory.httpRegistration(thisClass, registrationMap, (Http) annotation);
            case WebRegistrationConstant.REQUEST_INTERCEPT ->
                    WebRegistrationFactory.requestInterceptRegistration(thisClass, registrationMap);
            case WebRegistrationConstant.RESPONSE_INTERCEPT ->
                    WebRegistrationFactory.responseInterceptRegistration(thisClass, registrationMap);
        }
    }
}
