# 架构中存在的问题和不足

### 代码重构
* module_db 重新构思
* SearchEditText(lib_common)替换掉
* module_permission重新构思
* 生命周期管理
* BaseObservable重新设计（全局遮照、生命周期、异常过滤）
* 全局网络判断、重封装
* 将一些通用的自定义View放入module_util里

### 升级的工具类
* base_util
    * StringUtils
    * TimeUtils
    * ByteUtils
    * CrcUtils
    * RxTimerUtils
* device_util
    * DeviceUtils