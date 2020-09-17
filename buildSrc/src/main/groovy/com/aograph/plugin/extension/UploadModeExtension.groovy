package com.aograph.plugin.extension

import org.gradle.api.Project


/**
 * Created by tangqipeng on 2020/9/15.
 * email: tangqipeng@aograph.com
 */
class UploadModeExtension {

    UploadModeExtension() {
    }

    UploadModeExtension(Project project) {
    }

//    static UploadModeExtension getConfig(Project project){
//        UploadModeExtension config = project.getExtensions().findByType(UploadModeExtension)
//        if (config == null){
//            config = new UploadModeExtension()
//        }
//        return config
//    }

    int notifyTag
    String issue
    String des

}
