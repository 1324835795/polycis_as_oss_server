package com.polycis.main.common.util.script;

import com.alibaba.fastjson.JSONObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther cg
 * @date 2019-06-19
 */
public class JSEngine {


    /** 上报数据（解密数据）<br>
     * 示例数据：
     *  传入参数 -> 001122334455
     *  输出结果 -> {"method":"thing.event.HumiError.post","id":"12345","params":{"Humidity":2},"version":"1.1"}
     *
     *  js脚本中的方法名必须为rawDataToProtocol
     *
     * @param rawData 加密数据
     * @param isFile 是否是文件
     * @param filePath 文件路径
     * @param jsFunctionStr js方法内容（如果不是文件，则需要填写这个）
     * @return
     */
    public static String rawDataToProtocol(String rawData, boolean isFile, String filePath, String jsFunctionStr)
            throws FileNotFoundException, ScriptException, NoSuchMethodException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        String jsFile;
        if(isFile){
            jsFile = filePath;
            FileReader reader = new FileReader(jsFile);//执行指定脚本(js文件)
            engine.eval(reader);
        }else{
            jsFile = jsFunctionStr;
            engine.eval(jsFile);//执行指定脚本(js内容)
        }
        if (engine instanceof Invocable) {
            Invocable invoke = (Invocable) engine;
            ScriptObjectMirror rtn = (ScriptObjectMirror)invoke.invokeFunction("rawDataToProtocol", rawData);
            Map<String,Object> tempMap = new HashMap<>();
            for(Map.Entry<String,Object> m : rtn.entrySet()){
                tempMap.put(m.getKey(), m.getValue());
            }
            return JSONObject.toJSONString(tempMap);
        }
        return null;
   }

    /** 下发数据（加密数据）<br>
     * 示例数据：
     *  传入参数 -> {"method":"thing.event.HumiError.post","id":"12345","params":{"Humidity":2},"version":"1.1"}
     *  输出结果 -> 001122334455
     *
     *  js脚本中的方法名必须为protocolToRawData
     *
     * @param jsonData 解密数据
     * @param isFile 是否是文件
     * @param filePath 文件路径
     * @param jsFunctionStr js方法内容（如果不是文件，则需要填写这个）
     * @return
     */
    public static String protocolToRawData(String jsonData, boolean isFile, String filePath, String jsFunctionStr)
            throws FileNotFoundException, ScriptException, NoSuchMethodException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        String jsFile;
        if(isFile){
            jsFile = filePath;
            FileReader reader = new FileReader(jsFile);//执行指定脚本(js文件)
            engine.eval(reader);
        }else{
            jsFile = jsFunctionStr;
            engine.eval(jsFile);//执行指定脚本(js内容)
        }
        if (engine instanceof Invocable) {
            Invocable invoke = (Invocable) engine;
            Map<String,Object> mapData = JSONObject.parseObject(jsonData,Map.class);
            String rtn = (String)invoke.invokeFunction("rawDataToProtocol", mapData);
            return rtn;
        }
        return null;
    }

}
