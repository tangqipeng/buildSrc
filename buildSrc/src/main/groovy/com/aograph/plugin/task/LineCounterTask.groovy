package com.aograph.plugin.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
/**
 * Created by tangqipeng on 2020/9/10.
 * email: tangqipeng@aograph.com
 */
class LineCounterTask extends DefaultTask {
    def javaPath
    def resPath
    def javaLines = 0
    def resLines = 0
    def projectName

    @TaskAction
    def startCounting(){
        println 'startCounting'
        if (null != javaPath){
            count(new File(javaPath))
        }
        if (resPath != null) {
            count(new File(resPath))
        }
        printResult()
    }

    def count(File file){
        if (file.directory){
            file.traverse {dir ->
                countSingleFile(dir)
            }
        }
    }

    def countSingleFile(File file){
        if (file.directory)
            return
        int lines = file.readLines().size()
        String fileName = file.name
        if (fileName.endsWith(".java")){
            javaLines += lines
        }else if (fileName.endsWith(".xml")){
            resLines += lines
        }
    }

    def printResult() {
        println '-----------------------------'
        println 'you wrote:'
        println javaLines + ' lines in java'
        println resLines + ' lines in xml'
        println 'powered by line counter, in '+ projectName
        println '-----------------------------'
    }

}
