package com.aograph.plugin.request.httpurlconnect

import groovy.json.JsonSlurper

/**
 * Created by tangqipeng on 2020/9/11.
 * email: tangqipeng@aograph.com
 */
class HttpUrlConnectRequest {

    static final String baseUrl = "https://qyapi.weixin.qq.com/cgi-bin/"

    static def getAccessToken(){
        def url = "gettoken?corpid=***&corpsecret=****"
        def response = getNetWorkData(url)
        println("response is "+ response)
        println("access_token is "+ response['access_token'])
        return response['access_token']
    }

    static def getNetWorkData(String urlPath) {
        def url = baseUrl + urlPath
        //发送http请求（此处用原生的，也可导入okHttp库进行网络操作）
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection()
        connection.setConnectTimeout(10000)
        connection.setRequestMethod('GET')
        connection.connect()
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))
        StringBuffer stringBuffer = new StringBuffer()
        String json = ""
        while ((json = bufferedReader.readLine()) != null) {
            stringBuffer.append(json)
        }
        String rsp = new String(stringBuffer.toString().getBytes(), "UTF-8")
        //解析
        def jsonSlurper = new JsonSlurper()
        return jsonSlurper.parseText(rsp)
    }

    static def postNetWorkData(String urlPath, String jsonOutput) {
        try {
            def url = baseUrl + urlPath + getAccessToken()
            //发送http请求（此处用原生的，也可导入okHttp库进行网络操作）
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection()
            connection.setConnectTimeout(10000)
            connection.setRequestMethod('POST')
            connection.setDoOutput(true)
            connection.setDoInput(true);
            // 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
            connection.setRequestProperty("Content-Type", "application/json");

            // 获得一个输出流, 用于向服务器写数据, 默认情况下, 系统不允许向服务器输出内容
            OutputStream out = connection.getOutputStream();
            out.write(jsonOutput.getBytes());
            out.flush();
            out.close();

            connection.connect()
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))
            StringBuffer stringBuffer = new StringBuffer()
            String json = ""
            while ((json = bufferedReader.readLine()) != null) {
                stringBuffer.append(json)
            }
            String rsp = new String(stringBuffer.toString().getBytes(), "UTF-8")
            //解析
            def jsonSlurper = new JsonSlurper()
            return jsonSlurper.parseText(rsp)
        }catch(Exception e){
            e.printStackTrace()
        }
        return null
    }

    /* 上传文件至Server的方法 */
    static def uploadFile(String urlPath, File file) {
        String end = "\r\n"
        String twoHyphens = "--"
        String boundary = "*****"
        def actionUrl = baseUrl + urlPath + getAccessToken()
        try {
            URL url = new URL(actionUrl)
            HttpURLConnection con = (HttpURLConnection) url.openConnection()
            /* 允许Input、Output，不使用Cache */
            con.setDoInput(true)
            con.setDoOutput(true)
            con.setUseCaches(false)
            /* 设置传送的method=POST */
            con.setRequestMethod("POST")
            /* setRequestProperty */
            con.setRequestProperty("Connection", "Keep-Alive")
            con.setRequestProperty("Charset", "UTF-8")
            con.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary)
            /* 设置DataOutputStream */
            DataOutputStream ds = new DataOutputStream(con.getOutputStream())
            ds.writeBytes(twoHyphens + boundary + end)
            ds.writeBytes("Content-Disposition: form-data; "
                    + "name=\"media\";filename=\"" + file.name + "\"" + end)
            ds.writeBytes(end)
            /* 取得文件的FileInputStream */
            FileInputStream fStream = new FileInputStream(file)
            /* 设置每次写入1024bytes */
            int bufferSize = 1024
            byte[] buffer = new byte[bufferSize]
            int length = -1
            /* 从文件读取数据至缓冲区 */
            while ((length = fStream.read(buffer)) != -1) {
                /* 将资料写入DataOutputStream中 */
                ds.write(buffer, 0, length)
            }
            ds.writeBytes(end)
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end)
            /* close streams */
            fStream.close()
            ds.flush()
            /* 取得Response内容 */
            InputStream is = con.getInputStream()
            int ch
            StringBuffer b = new StringBuffer()
            while ((ch = is.read()) != -1) {
                b.append((char) ch)
            }
            println("上传成功" + b.toString().trim());
            /* 关闭DataOutputStream */
            ds.close();

            String rsp = new String(b.toString().getBytes(), "UTF-8")
            //解析
            def jsonSlurper = new JsonSlurper()
            return jsonSlurper.parseText(rsp)
        } catch (Exception e) {
            println("上传失败" + e);
        }
        return ""
    }


}
