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

package io.github.thierrysquirrel.web.loading;

import io.github.thierrysquirrel.container.scanner.annotation.ScannerPackage;
import io.github.thierrysquirrel.container.scanner.registration.InterfaceManualRegistration;
import io.github.thierrysquirrel.web.loading.constant.WebLoadingConstant;

import java.util.List;
import java.util.Map;

/**
 * Classname: WebLoading
 * Description:
 * Date:2026/2/1
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
@ScannerPackage(packageName = "io.github.thierrysquirrel.web.server.header")
public class WebLoading implements InterfaceManualRegistration {
    private String url = WebLoadingConstant.DEFAULT_URL;
    private int readHeartbeatTime = WebLoadingConstant.DEFAULT_READ_HEARTBEAT_TIME;
    private int writeHeartbeatTime = WebLoadingConstant.DEFAULT_WRITE_HEARTBEAT_TIME;
    private String httpErrorUrl = WebLoadingConstant.DEFAULT_HTTP_ERROR_URL;
    private String httpErrorMethod = WebLoadingConstant.DEFAULT_HTTP_ERROR_METHOD;

    @Override
    public void scannerAll(List<Class<?>> scannerClassList, Map<Class<?>, Object> registrationMap) {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getReadHeartbeatTime() {
        return readHeartbeatTime;
    }

    public void setReadHeartbeatTime(int readHeartbeatTime) {
        this.readHeartbeatTime = readHeartbeatTime;
    }

    public int getWriteHeartbeatTime() {
        return writeHeartbeatTime;
    }

    public void setWriteHeartbeatTime(int writeHeartbeatTime) {
        this.writeHeartbeatTime = writeHeartbeatTime;
    }

    public String getHttpErrorUrl() {
        return httpErrorUrl;
    }

    public void setHttpErrorUrl(String httpErrorUrl) {
        this.httpErrorUrl = httpErrorUrl;
    }

    public String getHttpErrorMethod() {
        return httpErrorMethod;
    }

    public void setHttpErrorMethod(String httpErrorMethod) {
        this.httpErrorMethod = httpErrorMethod;
    }

    @Override
    public String toString() {
        return "WebLoading{" +
                "url='" + url + '\'' +
                ", readHeartbeatTime=" + readHeartbeatTime +
                ", writeHeartbeatTime=" + writeHeartbeatTime +
                ", httpErrorUrl='" + httpErrorUrl + '\'' +
                ", httpErrorMethod='" + httpErrorMethod + '\'' +
                '}';
    }
}
