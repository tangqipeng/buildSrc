package com.aograph.plugin.request.httpurlconnect

/**
 * Created by tangqipeng on 2020/9/14.
 * email: tangqipeng@aograph.com
 */
class HttpRequestUtil {
    static final int TIME_OUT = 10 * 1000                       //超时时间
    static final String CHARSET = 'utf-8'                       //编码格式
    static final String PREFIX = '--'                           //前缀
    static final String BOUNDARY = '******'                     //边界标识
    static final String CONTENT_TYPE = 'multipart/form-data'    //内容类型
    static final String LINE_END = '\r\n'                       //换行

    Map<String, String> stringMap = new HashMap<>()
    Map<String, File> fileMap = new HashMap<>()

    def addTextParameter(String key, String value){
        stringMap.put(key,value)
    }
    def addFileParameter(File file){
        fileMap.put(file.name,file)
    }

    def uploadFile(String requestUrl){
        return postRequest(requestUrl, stringMap, fileMap)
    }

    def postRequest(String requestUrl, Map<String, String> strMap, Map<String, File> fileMap){
        //第一步设置地址
        URL url = new URL(requestUrl)
        HttpURLConnection connection = (HttpURLConnection)url.openConnection()
        //第二步设置基本属性
        connection.setRequestMethod("POST")
        connection.setReadTimeout(TIME_OUT)
        connection.setConnectTimeout(TIME_OUT)
        connection.setDoInput(true)
        connection.setDoOutput(true)
        connection.setUseCaches(false)
        //第三步设置请求头参数
        connection.setRequestProperty("Connection","Keep-Alive")
        connection.setRequestProperty("Charset",CHARSET)
        connection.setRequestProperty("Content-Type",CONTENT_TYPE+";boundary="+BOUNDARY)
        //第四步上传参数
        DataOutputStream dos = new DataOutputStream(connection.getOutputStream())
        getStrParams(strMap, dos)
        getFileParams(fileMap, dos)
        dos.writeBytes(PREFIX+BOUNDARY+PREFIX+LINE_END)
        dos.flush()
        dos.close()
        if (connection.getResponseCode() == 200){
            InputStream stream = connection.getInputStream()
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream))
            String line = null;
            StringBuilder response = new StringBuilder();
            while ((line = bufferedReader.readLine())!=null){
                response.append(line)
            }

            return response
        }
    }

    def getStrParams(Map<String, String> strMap, DataOutputStream dos){
        StringBuilder builder = new StringBuilder()
        for (Map.Entry<String, String> entry:strMap.entrySet()){
            builder.append(PREFIX).append(BOUNDARY).append(LINE_END)
                    .append("Content-Disposition:form-data;name=\"").append(entry.key).append("\"").append(LINE_END)
                    .append("Content-Type:text/plain;charset=").append(CHARSET).append(LINE_END)
                    .append("Content-Transder-Encoding:8bit").append(LINE_END)
                    .append(LINE_END)
                    .append(entry.value)
                    .append(LINE_END)
        }
        dos.writeBytes(builder.toString())
        dos.flush()
    }

    def getFileParams(Map<String, File> fileMap, DataOutputStream dos){
        StringBuilder builder = new StringBuilder()
        for (Map.Entry<String, File> entry:fileMap.entrySet()){
            builder.append(PREFIX).append(BOUNDARY).append(LINE_END)
                    .append("Content-Disposition:form-data;name=\"file\";filename=\"").append(entry.key).append("\"").append(LINE_END)
                    .append("Content-Type:application/vnd.android.package-archive").append(LINE_END)
                    .append("Content-Transder-Encoding:8bit").append(LINE_END)
                    .append(LINE_END)
            dos.writeBytes(builder.toString())
            dos.flush()
            InputStream is = new FileInputStream(entry.value)
            byte [] buffer = new byte[1024]
            int len = 0
            while ((len = is.read(buffer)) != -1){
                dos.write(buffer,0, len)
            }
            is.close()
            dos.writeBytes(LINE_END)
        }
    }
}
