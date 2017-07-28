//所有cmd模块的路径
seajs.config({
    base : "/static/",
    alias: {
        //== 第三方组件

        "jquery"        : "plugin/jQuery-2.1.4.min",
        //日历汉化
        "art"           : "plugin/artTemplate/artTemplate",
        //提醒牌
        "layer"         : "plugin/layer/layer",
        //单选框和复选框美化插件
        "checkAndRadio" : "plugin/iCheck/icheck.min",
        // 时间插件
        "dp"            : "plugin/My97DatePicker/WdatePicker",
        // 上传文件插件
        "fileupload"    : "plugin/fileupload/jquery.fileupload",
        // jquery工具扩展
        "widget"        : "plugin/fileupload/vendor/jquery.ui.widget",
        // echarts 图标工具
        "charts"        : "plugin/echarts/echarts.common.min",
        // "charts"        : "plugin/echarts/echarts",

        //== 自定义组件

        //弹出框
        "dialog"        : "js/modules/dialog",
        //分页
        "page"          : "js/modules/paging",
        //ajax
        "ajax"          : "js/modules/ajax",
        //表格分页和搜索表单
        "table"         : "js/modules/table",
        //单选框和复选框美化初始化
        "icheck"        : "js/modules/icheck",
        //获取数据
        "formData"      : "js/modules/formData",
        //下拉检索列表
        "downlist"      : "js/modules/downlist",
        //验证模块
        "check"         : "js/modules/check",
        //日期选择模块
        "datepicker"    : "js/modules/datepicker",
        // 目录结构模块
        'treeView'      : "js/modules/treeView",
        // 上传文件
        "upload"        : "js/modules/upload",
        //模仿百度的搜索
        "baiduSearch"      : "js/modules/baiduSearch"
    }
});