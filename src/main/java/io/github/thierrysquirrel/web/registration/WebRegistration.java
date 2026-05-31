/**
 * Copyright 2026/6/1 ThierrySquirrel
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

import io.github.thierrysquirrel.hummingbird.core.extend.http.core.coder.server.HttpServerDecoder;
import io.github.thierrysquirrel.hummingbird.core.extend.http.core.coder.server.HttpServerEncoder;
import io.github.thierrysquirrel.hummingbird.core.server.init.HummingbirdServerInit;
import io.github.thierrysquirrel.web.annotation.Http;
import io.github.thierrysquirrel.web.loading.WebLoading;
import io.github.thierrysquirrel.web.registration.constant.WebRegistrationConstant;
import io.github.thierrysquirrel.web.registration.factory.WebRegistrationFactory;
import io.github.thierrysquirrel.web.server.header.HttpServerHeader;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classname: WebRegistration
 * Description:
 * Date:2026/6/1
 *
 * @author ThierrySquirrel
 * @since JDK25
 **/
public class WebRegistration {

    private static final Logger logger = Logger.getLogger(WebRegistration.class.getName());

    private WebRegistration() {
    }

    public static void webRegistrationScannerAll(List<Class<?>> scannerClassList, Map<Class<?>, Object> registrationMap) {
        for (Class<?> thisClass : scannerClassList) {
            Annotation[] annotations = thisClass.getAnnotations();
            for (Annotation annotation : annotations) {
                findAnnotation(thisClass, registrationMap, annotation);
            }
        }

        WebLoading object = (WebLoading) registrationMap.get(WebLoading.class);
        String url = object.getUrl();
        int readHeartbeatTime = object.getReadHeartbeatTime();
        int writeHeartbeatTime = object.getWriteHeartbeatTime();

        HttpServerHeader serverHeader = (HttpServerHeader) registrationMap.get(HttpServerHeader.class);
        try {
            HummingbirdServerInit.init(url, readHeartbeatTime, writeHeartbeatTime
                    , new HttpServerDecoder(), new HttpServerEncoder(), serverHeader);
        } catch (IOException e) {
            String logMsg = "Init Error";
            logger.log(Level.WARNING, logMsg, e);
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
