#sdk的相关说明
##本地仓库使用
- 在项目文件中的gradle文件夹下的gradle-wrapper.properties中将#distributionUrl=https\://services.gradle.org/distributions/gradle-6.1.1-all.zip更改
distributionUrl=http\://192.168.1.192:8081/artifactory/android_local/gradle-6.1.1-all.zip
##就是替换前面的https\://services.gradle.org/distributions为http\://192.168.1.192:8081/artifactory/android_local

- 将根目录下的build.gradle文件中两处repositories更改为下面的样子，其他的都去掉
    repositories {
        maven { url 'http://192.168.1.192:8081/artifactory/android_group/' }
    }


- 遗留了一个问题：就是releaseHelper的参数没有传入