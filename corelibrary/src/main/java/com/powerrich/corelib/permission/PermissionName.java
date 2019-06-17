package com.powerrich.corelib.permission;

import java.util.HashMap;

/**
 * 权限请求实体类
 */
public class PermissionName {

    public static final String REQUEST_INSTALL_PACKAGES = "android.permission.REQUEST_INSTALL_PACKAGES"; // 8.0及以上应用安装权限

    public static final String SYSTEM_ALERT_WINDOW = "android.permission.SYSTEM_ALERT_WINDOW"; // 6.0及以上悬浮窗权限

    public static final String READ_CALENDAR = "android.permission.READ_CALENDAR"; // 读取日程提醒
    public static final String WRITE_CALENDAR = "android.permission.WRITE_CALENDAR"; // 写入日程提醒

    public static final String CAMERA = "android.permission.CAMERA"; // 拍照权限

    public static final String READ_CONTACTS = "android.permission.READ_CONTACTS"; // 读取联系人
    public static final String WRITE_CONTACTS = "android.permission.WRITE_CONTACTS"; // 写入联系人
    public static final String GET_ACCOUNTS = "android.permission.GET_ACCOUNTS"; // 访问账户列表

    public static final String ACCESS_FINE_LOCATION = "android.permission.ACCESS_FINE_LOCATION"; // 获取精确位置
    public static final String ACCESS_COARSE_LOCATION = "android.permission.ACCESS_COARSE_LOCATION"; // 获取粗略位置

    public static final String RECORD_AUDIO = "android.permission.RECORD_AUDIO"; // 录音权限

    public static final String READ_PHONE_STATE = "android.permission.READ_PHONE_STATE"; // 读取电话状态
    public static final String CALL_PHONE = "android.permission.CALL_PHONE"; // 拨打电话
    public static final String READ_CALL_LOG = "android.permission.READ_CALL_LOG"; // 读取通话记录
    public static final String WRITE_CALL_LOG = "android.permission.WRITE_CALL_LOG"; // 写入通话记录
    public static final String ADD_VOICEMAIL = "com.android.voicemail.permission.ADD_VOICEMAIL"; // 添加语音邮件
    public static final String USE_SIP = "android.permission.USE_SIP"; // 使用SIP视频
    public static final String PROCESS_OUTGOING_CALLS = "android.permission.PROCESS_OUTGOING_CALLS"; // 处理拨出电话
    public static final String ANSWER_PHONE_CALLS = "android.permission.ANSWER_PHONE_CALLS";// 8.0危险权限：允许您的应用通过编程方式接听呼入电话。要在您的应用中处理呼入电话，您可以使用 acceptRingingCall() 函数
    public static final String READ_PHONE_NUMBERS = "android.permission.READ_PHONE_NUMBERS";// 8.0危险权限：权限允许您的应用读取设备中存储的电话号码

    public static final String BODY_SENSORS = "android.permission.BODY_SENSORS"; // 传感器

    public static final String SEND_SMS = "android.permission.SEND_SMS"; // 发送短信
    public static final String RECEIVE_SMS = "android.permission.RECEIVE_SMS"; // 接收短信
    public static final String READ_SMS = "android.permission.READ_SMS"; // 读取短信
    public static final String RECEIVE_WAP_PUSH = "android.permission.RECEIVE_WAP_PUSH"; // 接收WAP PUSH信息
    public static final String RECEIVE_MMS = "android.permission.RECEIVE_MMS"; // 接收彩信

    public static final String READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE"; // 读取外部存储
    public static final String WRITE_EXTERNAL_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE"; // 写入外部存储

    public static final class Group {

        // 日历
        public static final String[] CALENDAR = new String[]{
                PermissionName.READ_CALENDAR,
                PermissionName.WRITE_CALENDAR};

        // 联系人
        public static final String[] CONTACTS = new String[]{
                PermissionName.READ_CONTACTS,
                PermissionName.WRITE_CONTACTS,
                PermissionName.GET_ACCOUNTS};

        // 位置
        public static final String[] LOCATION = new String[]{
                PermissionName.ACCESS_FINE_LOCATION,
                PermissionName.ACCESS_COARSE_LOCATION};

        // 存储
        public static final String[] STORAGE = new String[]{
                PermissionName.READ_EXTERNAL_STORAGE,
                PermissionName.WRITE_EXTERNAL_STORAGE};
    }

    private static HashMap<Object,String> permissionName=null;
    private static void init(){
        permissionName =new HashMap<>();
        permissionName.put(READ_CALENDAR,"日历");
        permissionName.put(CAMERA,"拍照");
        permissionName.put(READ_CONTACTS,"读取");
        permissionName.put(WRITE_CONTACTS,"写入");
        permissionName.put(Group.STORAGE,"存储");
        permissionName.put(Group.LOCATION,"位置");
        permissionName.put(ACCESS_FINE_LOCATION,"获取精确位置");
    }
    public static String getPermissionName(Object object){
        if(permissionName==null){
            init();
        }
        return permissionName.get(object);
    }


}
