package xyz.imstro.rpc.context;

import xyz.imstro.rpc.annotation.RpcService;
import xyz.imstro.rpc.exception.RpcException;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: MOSTRO
 */
public class RpcContext {

    private Map<String, Object> rpcObjectMap = new HashMap<String, Object>();


    public RpcContext(Class<?> clazz) {
        try {
            init(clazz);
        } catch (RpcException e) {
            e.printStackTrace();
        }
    }

    public void init(Class<?> startClass) throws RpcException {
        String packageName = startClass.getPackage().getName();
        String packagePath = packageName.replaceAll("\\.", "/");
        URL url = startClass.getClassLoader().getResource(packagePath);
        if(url == null){
            throw new RpcException("启动失败，启动类错误");
        }

        loadRpcService(packageName, packagePath, url.getFile());

        System.out.println("rpc service init finish.");
    }

    private void loadRpcService(String packageName, String packagePath, String dirPath) throws RpcException {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        for (File file : files) {
            if(file.isDirectory()){
                String name = file.getName();
                loadRpcService(packageName+ "."+name, packagePath + "/"+name, file.getPath());
            }else {
                String fileName = file.getName();
                if(fileName.endsWith(".class")){
                    fileName = fileName.replace(".class", "");
                    try {
                        Class<?> clazz = Class.forName(packageName + "."+ fileName);
                        if(clazz.isInterface()) continue;
                        if(clazz.isAnnotationPresent(RpcService.class)){
                            Object instance = clazz.newInstance();
                            RpcService rpcService = clazz.getAnnotation(RpcService.class);
                            String value = rpcService.value();
                            if(!"".equals(value.trim())){
                                rpcObjectMap.put(value.trim(), instance);
                            }
                            Class<?>[] interfaces = clazz.getInterfaces();
                            if (interfaces == null){
                                throw new RpcException("Rpc service need interface.");
                            }
                            for (Class<?> interf : interfaces) {
                                rpcObjectMap.put(interf.getName(), instance);
                            }
                        }
                    } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public Map<String, Object> getRpcObjectMap() {
        return rpcObjectMap;
    }
}
