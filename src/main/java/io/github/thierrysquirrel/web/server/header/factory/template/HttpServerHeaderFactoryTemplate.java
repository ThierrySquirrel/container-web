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

package io.github.thierrysquirrel.web.server.header.factory.template;

import io.github.thierrysquirrel.hummingbird.core.extend.http.core.coder.server.factory.domain.HttpFormData;
import io.github.thierrysquirrel.json.deserialize.util.JsonDeSerializeUtil;
import io.github.thierrysquirrel.json.reflect.JsonReflect;
import io.github.thierrysquirrel.json.util.JsonUtil;
import io.github.thierrysquirrel.web.annotation.param.FormDataFileParam;
import io.github.thierrysquirrel.web.annotation.param.FormDataParam;
import io.github.thierrysquirrel.web.annotation.param.FormUrlEncodedParam;
import io.github.thierrysquirrel.web.annotation.param.UrlParam;
import io.github.thierrysquirrel.web.annotation.param.pojo.constant.WebParamPojoConstant;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

/**
 * Classname: HttpServerHeaderFactoryTemplate
 * Description:
 * Date:2026/2/1
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class HttpServerHeaderFactoryTemplate {
    private HttpServerHeaderFactoryTemplate() {
    }

    public static Object findMethodParameterValue(Annotation annotation, String parameterTypeName, Map<String, String> urlMap, Map<String, HttpFormData> httpFormDataMap, Map<String, String> httpFormUrlencoded, Class<?> thisClass, String jsonBody) {
        String typeName = annotation.annotationType().getTypeName();
        return switch (typeName) {
            case WebParamPojoConstant.URL_PARAM -> getUrlParamValue((UrlParam) annotation, parameterTypeName, urlMap);
            case WebParamPojoConstant.FORM_DATA_PARAM ->
                    getFormDataParamValue((FormDataParam) annotation, parameterTypeName, httpFormDataMap);
            case WebParamPojoConstant.FORM_DATA_FILE_PARAM ->
                    getFormDataFileParamValue((FormDataFileParam) annotation, httpFormDataMap);
            case WebParamPojoConstant.FORM_URL_ENCODED_PARAM ->
                    getFormUrlEncodedParamValue((FormUrlEncodedParam) annotation, parameterTypeName, httpFormUrlencoded);
            case WebParamPojoConstant.FORM_DATA_POJO -> getFormDataPojo(httpFormDataMap, thisClass);
            case WebParamPojoConstant.FORM_URL_ENCODED_POJO -> getFormUrlEncodedPojo(httpFormUrlencoded, thisClass);
            case WebParamPojoConstant.JSON_POJO -> getJsonPojo(jsonBody, thisClass);
            default -> null;
        };
    }

    private static Object getUrlParamValue(UrlParam annotation, String parameterTypeName, Map<String, String> urlMap) {
        String value = annotation.value();
        String mapValue = urlMap.get(value);
        return JsonDeSerializeUtil.getWarpValue(mapValue, parameterTypeName);
    }

    private static Object getFormDataParamValue(FormDataParam annotation, String parameterTypeName, Map<String, HttpFormData> httpFormDataMap) {
        String value = annotation.value();
        HttpFormData httpFormData = httpFormDataMap.get(value);
        String formDataValue = httpFormData.getValue();
        return JsonDeSerializeUtil.getWarpValue(formDataValue, parameterTypeName);
    }

    private static Object getFormDataFileParamValue(FormDataFileParam annotation, Map<String, HttpFormData> httpFormDataMap) {
        String value = annotation.value();
        return httpFormDataMap.get(value);
    }

    private static Object getFormUrlEncodedParamValue(FormUrlEncodedParam annotation, String parameterTypeName, Map<String, String> httpFormUrlencoded) {
        String value = annotation.value();
        String mapValue = httpFormUrlencoded.get(value);
        return JsonDeSerializeUtil.getWarpValue(mapValue, parameterTypeName);
    }

    private static Object getFormDataPojo(Map<String, HttpFormData> httpFormDataMap, Class<?> pojoClass) {
        Object pojo = JsonReflect.newInstance(pojoClass);
        for (Field declaredField : pojo.getClass().getDeclaredFields()) {
            String fieldName = declaredField.getName();
            HttpFormData httpFormData = httpFormDataMap.get(fieldName);
            if (Objects.isNull(httpFormData)) {
                continue;
            }
            String httpFormDataValue = httpFormData.getValue();
            Object warpValue = JsonDeSerializeUtil.getWarpValue(httpFormDataValue, declaredField.getType().getTypeName());
            JsonReflect.fieldSetValue(declaredField, pojo, warpValue);
        }
        return pojo;
    }

    private static Object getFormUrlEncodedPojo(Map<String, String> httpFormUrlencoded, Class<?> pojoClass) {
        Object pojo = JsonReflect.newInstance(pojoClass);
        for (Field declaredField : pojo.getClass().getDeclaredFields()) {
            String fieldName = declaredField.getName();
            String formUrlencoded = httpFormUrlencoded.get(fieldName);
            if (Objects.isNull(formUrlencoded)) {
                continue;
            }
            Object warpValue = JsonDeSerializeUtil.getWarpValue(formUrlencoded, declaredField.getType().getTypeName());
            JsonReflect.fieldSetValue(declaredField, pojo, warpValue);
        }
        return pojo;
    }

    private static Object getJsonPojo(String jsonBody, Class<?> pojoClass) {
        return JsonUtil.deSerialize(jsonBody, pojoClass);
    }
}
