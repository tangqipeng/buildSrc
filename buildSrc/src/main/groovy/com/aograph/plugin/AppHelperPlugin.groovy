package com.aograph.plugin

import com.aograph.plugin.extension.LineCounterExtension
import com.aograph.plugin.extension.UploadModeExtension
import com.aograph.plugin.request.httpurlconnect.HttpUrlConnectRequest
import com.aograph.plugin.task.LineCounterTask
import com.aograph.plugin.task.SendMsgToWechatTask
import com.aograph.plugin.task.UploadAppToPGYTask
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by tangqipeng on 2020/9/10.
 * email: tangqipeng@aograph.com
 */
class AppHelperPlugin implements Plugin<Project> {

    private static final String LINE_COUNTER_TASK_NAME = "_lineCounter"
    private static final String SEND_MESSAGE_TO_WECHAT_TASK_NAME = "_sendMesToWechat"
    private static final String POST_APK_TO_PGY_TASK_NAME = "_postApkToPGY"
    private static final String RELESASE_HELPER_NAME = "releaseHelper"
    private static final String WINDOWS_JAVA_PATH = "\\app\\src\\main\\java"
    private static final String WINDOWS_RES_PATH = "\\app\\src\\main\\res\\layout"
    private static final String UNIX_JAVA_PATH = "/app/src/main/java"
    private static final String UNIX_RES_PATH = "/app/src/main/res/layout"


    @Override
    void apply(Project project) {
        checkedCodeLines(project)
    }

    static def checkedCodeLines(Project project){
//        StatisticsTask statisticsTask = project.tasks.create("_statisticsTask", StatisticsTask)
//        statisticsTask.init(project)
//        statisticsTask.dependsOn(counterTask)

        project.getExtensions().create(RELESASE_HELPER_NAME, UploadModeExtension)

        project.afterEvaluate(new Action<Project>() {
            @Override
            void execute(Project project1) {
                UploadModeExtension uploadModeExtension = project1.getExtensions().findByName(RELESASE_HELPER_NAME)
                LineCounterExtension counterExtension = project1.getExtensions().create(LINE_COUNTER_TASK_NAME, LineCounterExtension)
                LineCounterTask counterTask = project1.tasks.create(LINE_COUNTER_TASK_NAME, LineCounterTask)
                counterTask.doFirst {
                    String rootPath = project1.rootDir.absolutePath
                    String javaPath = counterExtension.unix?UNIX_JAVA_PATH:WINDOWS_JAVA_PATH
                    String resPath = counterExtension.unix?UNIX_RES_PATH:WINDOWS_RES_PATH
                    counterTask.javaPath = rootPath + javaPath
                    counterTask.resPath = rootPath + resPath
                    counterTask.projectName = project1.name
                }


                HttpUrlConnectRequest httpUrlConnectRequest = project.getExtensions().create("HttpUrlConnectRequest", HttpUrlConnectRequest)

                SendMsgToWechatTask sendMsgToWechat = project1.tasks.create(SEND_MESSAGE_TO_WECHAT_TASK_NAME, SendMsgToWechatTask)
                sendMsgToWechat.init(httpUrlConnectRequest, uploadModeExtension.notifyTag)
                UploadAppToPGYTask uploadAppToPGYTask = project1.tasks.create(POST_APK_TO_PGY_TASK_NAME, UploadAppToPGYTask)
                uploadAppToPGYTask.init(httpUrlConnectRequest)
//                project1.tasks.findByName("build").dependsOn(project1.tasks.findByName("clean"))
                counterTask.dependsOn(project1.tasks.findByName("build"))
                uploadAppToPGYTask.dependsOn(counterTask)
                sendMsgToWechat.dependsOn(uploadAppToPGYTask)
//                sendMsgToWechat.dependsOn("build")

            }
        })
    }

}
