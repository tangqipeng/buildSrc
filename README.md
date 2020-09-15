#sdk的相关说明
##本地仓库使用
- 在项目文件中的gradle文件夹下的gradle-wrapper.properties中将#distributionUrl=https\://services.gradle.org/distributions/gradle-6.1.1-all.zip更改
distributionUrl=http\://192.168.1.192:8081/artifactory/android_local/gradle-6.1.1-all.zip
##就是替换前面的https\://services.gradle.org/distributions为http\://192.168.1.192:8081/artifactory/android_local

- 将根目录下的build.gradle文件中两处repositories更改为下面的样子，其他的都去掉
    repositories {
        maven { url 'http://192.168.1.192:8081/artifactory/android_group/' }
    }

##打包流程
一 打包说明：
  在打之前也可以clean一下 即：./gradlew clean
  然后： ./gradlew agent:build
  debug版本：./gradlew agent:makeDebugJar
  release版本：./gradlew makeReleaseJar
  打完之后agent-**.jar包的位置在 agent/build/libs/下

##接入说明
 1. 将agent.jar包放在项目的libs下，右击jar包，
  如图：
  ![Image text](image/sdk-1.png)

 2.点击Add Ad Library...，并将包加入build.gradle中的dependencies下，会有implementation files('libs/agent-**.jar')
  ![Image text](image/sdk-2.png)

 3.在app/src/main下创建jniLibs文件夹，将so文件放入jniLibs下：
  ![Image text](image/sdk-3.png)

 4.在build.gradle中的android下加入：
  android {
      defaultConfig {
              ndk {
                  abiFilters "arm64-v8a", "armeabi-v7a", "x86", "x86_64"
              }
      }
      useLibrary 'org.apache.http.legacy'
      sourceSets {
             main {
                        jniLibs.srcDirs = ["src/main/jniLibs"]
              }
      }
  }
  ![Image text](image/sdk-4.png)

 2. 初始化
    初始化语句如下
    Aograph.withApplicationToken("token").start(this.getApplicationContext());
    说明：最好在application中初始化，如果在activity中初始化，那在退出的时候加上Aograph.stopAograph();

    a.可替换网络请求的地址，在初始化的语句中加入withCollectorAddress(String host)方法，host也可以是域名
      举例：Aograph.withApplicationToken("androidTest").withCollectorAddress("192.168.0.1:8080"/"api.aograph.com").start(this.getApplicationContext();

    b.上面默认采用的是http协议，可以在初始化语句中加入withUsingSsl(true)，会采用https

    c.获取外部的app的deviceID时，需要现在初始化语句中加入withUseOuterDeviceID(true)方法，然后在调用Aograph.setDeviceId(String deviceId);方法

    d.app如果要获取sdk的deviceID，需要在初始化语句中加入withOpenResource(true)方法，然后在调用Aograph.getDeviceId();方法

    e.sdk与梆梆联合，需要获取梆梆的uuid时，需要在初始化语句中加入withBangcleDeviceID(true)方法，sdk会反射调用梆梆的uuid作为deviceID

    f.移动MM有特别的要求，移动MM项目接入时需要在初始化语句中加入withYDMM(true)方法，这样才能设置traceID，设置traceID的方法Aograph.setTraceID(final String traceID);

    g.设置手机号Aograph.setPhone(final String phone);

    h.设置渠道Aograph.setChannel(final String channel);

    i.sdk提供反爬的token，需要在初始化语句中调用withPreventCrawler(true)打开，然后调用Aograph.getToken()获取。

    j.sdk测试的时候需要打印日志，可以在初始化语句中加入withLoggingEnabled(true)

    k.建行版需求，在初始化语句中加上withCCB(true)，开启后能够调用logCustom、setEpData、openCollect方法

    l.建行版中setEpData、openCollect方法需要配合web sdk使用，调用方式如下
      1.在webview所在activity中需要加入webView.addJavascriptInterface(this, "aograph");支持js调用android
      2.然后设置两个方法即可
        @JavascriptInterface
        public void setEpMessage(String data) {
            Aograph.setEpData(data);
        }
        @JavascriptInterface
        public void startCollect() {
            Aograph.openCollect();
        }
      3.建行版调用Aograph.logCustom(final String type, final String json, final int version)设置对应信息


 3.需要加入的权限
   <uses-permission android:name="android.permission.INTERNET"/>
   <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
   <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
   <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
   <uses-permission android:name="android.permission.READ_PHONE_STATE" />
   <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
   <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
   <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
   <uses-permission android:name="android..permission.MOUNT_UNMOUNT_FILESYSTEMS" />


 4.如果app要混淆，需要加入
    -dontwarn com.aograph.agent.*
    -keep class com.aograph.agent.** { *;}

 5.适配Android Q，在AndroidManifest.xml中加入如下方法：
   a. 在<application中加入android:usesCleartextTraffic="true"
   b. 在<application中加入
       <uses-library
           android:name="org.apache.http.legacy"
           android:required="false" />
   c.如下图:
      ![Image text](image/sdk-5.png)
