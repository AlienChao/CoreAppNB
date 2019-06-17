# CoreAppNB
⚔️🔥🔥公共库🔥🔥

框架特点
快速开发

只需要写项目的业务逻辑，不用再去关心网络请求、权限申请、View的生命周期等问题，撸起袖子就是干。

维护方便

MVVM开发模式，低耦合，逻辑分明。Model层负责将请求的数据交给ViewModel；ViewModel层负责将请求到的数据做业务逻辑处理，最后交给View层去展示，与View一一对应；View层只负责界面绘制刷新，不处理业务逻辑，非常适合分配独立模块开发。

流行框架

retrofit+okhttp+rxJava负责网络请求；gson负责解析json数据；glide负责加载图片；rxlifecycle负责管理view的生命周期；与网络请求共存亡；rxbinding结合databinding扩展UI事件；rxpermissions负责Android 6.0权限申请；material-dialogs一个漂亮的、流畅的、可定制的material design风格的对话框。

数据绑定

满足google目前控件支持的databinding双向绑定，并扩展原控件一些不支持的数据绑定。例如将图片的url路径绑定到ImageView控件中，在BindingAdapter方法里面则使用Glide加载图片；View的OnClick事件在BindingAdapter中方法使用RxView防重复点击，再把事件回调到ViewModel层，实现xml与ViewModel之间数据和事件的绑定(框架里面部分扩展控件和回调命令使用的是@kelin原创的)。

基类封装

专门针对MVVM模式打造的BaseActivity、BaseFragment、BaseViewModel，在View层中不再需要定义ViewDataBinding和ViewModel，直接在BaseActivity、BaseFragment上限定泛型即可使用。普通界面只需要编写Fragment，然后使用ContainerActivity盛装(代理)，这样就不需要每个界面都在AndroidManifest中注册一遍。

全局操作

全局的Activity堆栈式管理，在程序任何地方可以打开、结束指定的Activity，一键退出应用程序。
LoggingInterceptor全局拦截网络请求日志，打印Request和Response，格式化json、xml数据显示，方便与后台调试接口。
全局Cookie，支持SharedPreferences和内存两种管理模式。
通用的网络请求异常监听，根据不同的状态码或异常设置相应的message。
全局的异常捕获，程序发生异常时不会崩溃，可跳入异常界面重启应用。
全局事件回调，提供RxBus、Messenger两种回调方式。
全局任意位置一行代码实现文件下载进度监听（暂不支持多文件进度监听）。
全局点击事件防抖动处理，防止点击过快。
 
 不要再看了，瞎几把Copy的............
