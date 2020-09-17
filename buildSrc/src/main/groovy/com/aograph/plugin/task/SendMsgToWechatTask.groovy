package com.aograph.plugin.task

import com.aograph.plugin.extension.UploadModeExtension
import com.aograph.plugin.request.httpurlconnect.HttpUrlConnectRequest
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by tangqipeng on 2020/9/11.
 * email: tangqipeng@aograph.com
 */
class SendMsgToWechatTask extends DefaultTask {

    HttpUrlConnectRequest mHttpUrlConnectRequest
    UploadModeExtension modeExtension
    def init(HttpUrlConnectRequest httpUrlConnectRequest, UploadModeExtension uploadModeExtension) {
        mHttpUrlConnectRequest = httpUrlConnectRequest
        modeExtension = uploadModeExtension
    }

    @TaskAction
    def sendMessageToWechat(){
        println('sendMessageToWechat')
        if (modeExtension.notifyTag == 0){
            //发送消息提示在蒲公英下载
            sendNewsPGYMessage(modeExtension.des, modeExtension.issue)
        }
        if (modeExtension.notifyTag == 0 || modeExtension.notifyTag == 1){
            //直接发送到企业微信
            def media_id = uploadApkAndGetMediaId()
            if (media_id != null){
                postApkMessageToWechat(media_id)
            }
        } else {
            //发送消息提示在蒲公英下载
            sendNewsPGYMessage(modeExtension.des, modeExtension.issue)
            sendMessage("请从蒲公英上下载app测试，或是找到最近一次在企业微信中的app")
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

    def sendNewsPGYMessage(String des, String issue){
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
                "            \"description\" : \"<div class=\\\"gray\\\">"+currentTime()+"</div> <div class=\\\"normal\\\">"+des+"</div><div class=\\\"highlight\\\">"+issue+"，下载密码123456</div>\",\n" +
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
