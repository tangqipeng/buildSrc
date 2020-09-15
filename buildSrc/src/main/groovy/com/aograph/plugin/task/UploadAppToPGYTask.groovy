package com.aograph.plugin.task

import com.aograph.plugin.request.httpurlconnect.HttpRequestUtil
import com.aograph.plugin.request.httpurlconnect.HttpUrlConnectRequest
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by tangqipeng on 2020/9/11.
 * email: tangqipeng@aograph.com
 */
class UploadAppToPGYTask extends DefaultTask{

    static final def PGY_apiKey = "****"

    HttpUrlConnectRequest mHttpUrlConnectRequest;
    def init(HttpUrlConnectRequest httpUrlConnectRequest) {
        mHttpUrlConnectRequest = httpUrlConnectRequest;
    }

    @TaskAction
    def postAppToPgy(){
        println('postAppToPgy')
        def path = "/Users/tangqipeng/codeManager/androidProjects/agent_android/app/build/outputs/apk/staTest/release/release-"+releaseTime()+".apk"
        println("path is "+path)
        File file = new File(path)
        uploadUseHttpUtil(file)
//        uploadAppUserHttpUrl(file)
    }
    def uploadUseHttpUtil(File file){
        if (file != null) {
            def url = "https://www.pgyer.com/apiv2/app/upload"
            HttpRequestUtil requestUtil = new HttpRequestUtil()
            requestUtil.addTextParameter("_api_key", PGY_apiKey)
            requestUtil.addFileParameter(file)
            requestUtil.addTextParameter("buildInstallType", "2")
            requestUtil.addTextParameter("buildPassword", "123456")
            requestUtil.addTextParameter("buildUpdateDescription", "最新测试包在前端加入算法判定，最后融合进token中")
            requestUtil.addTextParameter("buildInstallDate", "2")
            requestUtil.addTextParameter("channelShortcut", "aograph_test")
            def response =  requestUtil.uploadFile(url)
            println("response is " + response)
        }
    }

    static def releaseTime() {
        return new Date().format("yyyy-MM-dd", TimeZone.getTimeZone("UTC"))
    }
}
