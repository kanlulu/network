# network    

网络请求组件 Retrofit v2.9.0    

**RequestCommon.kt**      
在应用层配置公共请求头参数&公共请求参数
```
/**
     * 公共请求头参数
     */
    val HEADER_COMMON_PARAMS = mutableMapOf<String, String>()

    /**
     * GET 公共请求参数
     */
    val REQUEST_COMMON_PARAMS = mutableMapOf<String, String>()
```    

在应用层common模块创建网络请求对象，定义url、接口。。。
