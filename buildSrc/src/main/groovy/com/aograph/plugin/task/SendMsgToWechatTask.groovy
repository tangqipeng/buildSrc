package com.aograph.plugin.task

import com.aograph.plugin.request.httpurlconnect.HttpUrlConnectRequest
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by tangqipeng on 2020/9/11.
 * email: tangqipeng@aograph.com
 */
class SendMsgToWechatTask extends DefaultTask {

    HttpUrlConnectRequest mHttpUrlConnectRequest
    String mTag
    def init(HttpUrlConnectRequest httpUrlConnectRequest, String tag) {
        mHttpUrlConnectRequest = httpUrlConnectRequest
        mTag = tag
    }

    static final String jiraUrl = "http://jira.aograph.com:8085/browse/IOS-15?filter=-2"

    @TaskAction
    def sendMessageToWechat(){
        println('sendMessageToWechat')
        //发送消息提示在蒲公英下载
        sendNewsPGYMessage(jiraUrl)
        if (mTag == null || !"1".equals(mTag)){
            //直接发送到企业微信
            def media_id = uploadApkAndGetMediaId()
            if (media_id != null){
                postApkMessageToWechat(media_id)
            }
        }

    }

    def postApkMessageToWechat(String mediaID){
        def url = "message/send?access_token="
        def param = "{\n" +
                "   \"touser\" : \"TangQiPeng|wanglishuo@aograph.com|lijiaojiao@aograph.com\",\n" +
                "   \"toparty\" : \"\",\n" +
                "   \"totag\" : \"\",\n" +
                "   \"msgtype\" : \"file\",\n" +
                "   \"agentid\" : 1000011,\n" +
                "   \"file\" : {\n" +
                "        \"media_id\": \"" + mediaID + "\"\n" +
                "   },\n" +
                "   \"safe\":0,\n" +
                "   \"enable_duplicate_check\": 0,\n" +
                "   \"duplicate_check_interval\": 1800\n" +
                "}"

        def response = mHttpUrlConnectRequest.postNetWorkData(url, param)
        println("response is "+ response)
    }

    def sendMessage(String contentStr){
        def url = "message/send?access_token="

//        "  \"touser\": \"TangQiPeng|wanglishuo@aograph.com|lijiaojiao@aograph.com\",\n" +

        def param = "{\n" +
                "  \"touser\": \"TangQiPeng|lijiaojiao@aograph.com\",\n" +
                "  \"toparty\": \"\",\n" +
                "  \"totag\": \"\",\n" +
                "  \"msgtype\": \"text\",\n" +
                "  \"agentid\": 1000011,\n" +
                "  \"text\": {\n" +
                "    \"content\": \"" + contentStr + "\"\n" +
                "  },\n" +
                "  \"safe\": 0,\n" +
                "  \"enable_id_trans\": 0,\n" +
                "  \"enable_duplicate_check\": 0\n" +
                "}"

        def response = mHttpUrlConnectRequest.postNetWorkData(url, param)
        println("response is "+ response)
    }

    def sendNewsPGYMessage(String des){
        def url = "message/send?access_token="

//        "  \"touser\": \"TangQiPeng|wanglishuo@aograph.com|lijiaojiao@aograph.com\",\n" +

        def params = "{\n" +
                "   \"touser\" : \"TangQiPeng|lijiaojiao@aograph.com\",\n" +
                "   \"toparty\" : \"\",\n" +
                "   \"totag\" : \"\",\n" +
                "   \"msgtype\" : \"textcard\",\n" +
                "   \"agentid\" : 1000011,\n" +
                "   \"textcard\" : {\n" +
                "            \"title\" : \"aographTest测试Apk\",\n" +
                "            \"description\" : \"<div class=\\\"gray\\\">"+currentTime()+"</div> <div class=\\\"normal\\\">主要进行了sdk升级，修复部分bug</div><div class=\\\"highlight\\\">"+des+"，下载密码123456</div>\",\n" +
                "            \"url\" : \"https://www.pgyer.com/BON6\",\n" +
                "            \"btntxt\":\"前往下载\"\n" +
                "   },\n" +
                "   \"enable_id_trans\": 0,\n" +
                "   \"enable_duplicate_check\": 0,\n" +
                "   \"duplicate_check_interval\": 1800\n" +
                "}"

        def response = mHttpUrlConnectRequest.postNetWorkData(url, params)
        println("response is "+ response)
    }

    def uploadApkAndGetMediaId(){
        def path = "/Users/tangqipeng/codeManager/androidProjects/agent_android/app/build/outputs/apk/staTest/release/release-"+releaseTime()+".apk"
        File file = new File(path)
        if (!file.exists()){
            return ""
        }
        println("file is "+ file.getAbsolutePath())
        def url = "media/upload?type=file&access_token="
        def response = mHttpUrlConnectRequest.uploadFile(url, file)
        println("response is "+ response)
        return response['media_id']
    }


    static def releaseTime() {
        return new Date().format("yyyy-MM-dd", TimeZone.getTimeZone("UTC"))
    }

    static def currentTime() {
        return new Date().format("yyyy年MM月dd日 HH:mm:ss", TimeZone.getTimeZone("UTC"))
    }


}
