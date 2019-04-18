## TinMvc ##

### 说在前面 ###
虽然已经在项目中添加了比较详细的代码注释,但是肯定还有很多需要注意的地方没有解释清楚,希望大家在使用的过程中如果发现了什么问题,及时提出来,大家共同解决.

### 功能列表 ###

1. 解决屏幕适配问题,适配全面屏/刘海屏(AndroidAutoSize是代替AndroidAutoLayout的屏幕适配框架,原理是基于今日头条的适配方案)
2. 代码解耦,给baseActivity/baseFragment减压
3. 使用堆栈对activity进行统一管理,ActivityUtil封装了各种常用方法
4. Activity标题栏统一设置,支持标题栏和状态栏统一设置背景color/shape/drawable
5. 支持fragment设置状态栏沉浸式,多fragment无缝切换
6. 解决fragment重叠的bug,fragment任务栈统一管理
7. 提供懒加载onLazyInitView()/fragment可见性onSupportVisible()/onSupportInvisible()方法回调
8. SmartRefresh和BaseRecyclerViewAdapterHelper相结合,提供封装的baseAdapter,列表刷新加载更简单
9. 使用RxEasyHttp网络框架链式调用,与Rxjava2相结合,线程智能控制
10. 解决Toast禁用通知权限不能弹出的bug,不分主次线程,可自定义Toast样式
11. 使用bottombar优化用户体验
12. 支持新手指引,编程中使用不规范的地方,会引导你正确使用
13. 使用插件一键生成Activity/Fragment
14. 依赖RxPermission,权限申请更简单
15. 使用EventBus,事件传递更加清晰


### 开发准备 ###

必须的项目配置,框架初始化都已添加,所以直接复制本项目更改报名,即可进行开发使用

### 开发指南 ###

使用的第三方框架:

[BottomBar](https://github.com/roughike/BottomBar "BottomBar")

[Fragmentation](https://github.com/YoKeyword/Fragmentation "Fragmentation")

[ImmersionBar](https://github.com/gyf-dev/ImmersionBar "ImmersionBar")

[AndroidAutoSize](https://github.com/JessYanCoding/AndroidAutoSize)

[RxEasyHttp](https://github.com/zhou-you/RxEasyHttp "RxEasyHttp")

[ToastUtils](https://github.com/getActivity/ToastUtils "ToastUtils")

[BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper "BaseRecyclerViewAdapterHelper")

[butterknife](https://github.com/JakeWharton/butterknife "butterknife")

[SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout "SmartRefreshLayout")

[ALog](https://github.com/Blankj/ALog "ALog")

[RxPermissions](https://github.com/tbruyelle/RxPermissions "RxPermissions")

[EventBus](https://github.com/greenrobot/EventBus "EventBus")

### 使用插件一键生成Activity/Fragment ###

templates.zip这个压缩文件里放着LhhActivity和LhhFragment两个文件夹

首先解压得到这两个文件夹

LhhActivity放到AS安装目录\plugins\android\lib\templates\activities里

LhhFragment放到AS安装目录\plugins\android\lib\templates\other里

放进去之后重启AS,然后在需要建Activity/Fragment的地方,右键--New--Activity/Fragment--LhhActivity/LhhFragment,然后给Activity/Fragment取个名字,点击Finish,欧克,打完收功!

### 注意: ###


1. 设置标题栏和状态栏是在ActivityLifeCycleCallBackIml类里实现的,还进行了其他初始化与销毁业务,实现了对baseActivity的解耦,另外,设置标题栏时要在activity对应的layout里include标题栏布局

2. 如果要在fragment里设置状态栏沉浸,可以让该fragment实现SimpleImmersionOwner接口，或者实现ImmersionOwner接口,具体实现可以参考ImmersionBar的demo使用

3. 对于一个activity加载多fragment或者一个fragment加载多fragment的需求,可以参考MainActivity里的代码,activity与fragment场景使用方法大同小异,其他需求可参考Fragmentation的demo使用

4. 三方库的初始化是在Application里进行的,建议能在子线程初始化的在子线程初始化,RxEasyHttp的初始化默认被注释了,请先设置BASE_URL之后,再取消这段代码的注释.

5. 项目的基本用法演示都会放在Demo文件夹中供大家随时查阅.





