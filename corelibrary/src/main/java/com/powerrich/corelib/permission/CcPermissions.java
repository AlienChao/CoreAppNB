package com.powerrich.corelib.permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CcPermissions {

    private static Activity mActivity;
    private List<String> mPermissions = new ArrayList<>();
    private boolean mConstant;

    private CcPermissions(Activity activity) {
        mActivity = activity;
    }

    public static Activity getSingleContext(){
        return mActivity;
    }


    /**
     * 设置请求的对象
     */
    public static CcPermissions with(Activity activity) {
        return new CcPermissions(activity);
    }

    /**
     * 设置权限组
     */
    public CcPermissions permission(String... permissions) {
        mPermissions.addAll(Arrays.asList(permissions));
        return this;
    }

    /**
     * 设置权限组
     */
    public CcPermissions permission(String[]... permissions) {
        for (String[] group : permissions) {
            mPermissions.addAll(Arrays.asList(group));
        }
        return this;
    }

    /**
     * 设置权限组
     */
    public CcPermissions permission(List<String> permissions) {
        mPermissions.addAll(permissions);
        return this;
    }

    /**
     * 被拒绝后继续申请，直到授权或者永久拒绝
     */
    public CcPermissions constantRequest() {
        mConstant = true;
        return this;
    }

    /**
     * 请求权限
     */
    public void request(Consumer call) {
        //如果没有指定请求的权限，就使用清单注册的权限进行请求
        if (mPermissions == null || mPermissions.size() == 0) mPermissions = PermissionUtils.getManifestPermissions(mActivity);
        if (mPermissions == null || mPermissions.size() == 0) throw new IllegalArgumentException("The requested permission cannot be empty");

        //使用isFinishing方法Activity在熄屏状态下会导致崩溃
        //if (mActivity == null || mActivity.isFinishing()) throw new IllegalArgumentException("Illegal Activity was passed in");
        if (mActivity == null) throw new IllegalArgumentException("The activity is empty");
        if (call == null) throw new IllegalArgumentException("The permission request callback interface must be implemented");

        //检查targetSdkVersion是否符合要求
        PermissionUtils.checkTargetSdkVersion(mActivity, mPermissions);
        //获取没有授予的权限
        ArrayList<String> failPermissions = PermissionUtils.getFailPermissions(mActivity, mPermissions);

        if (failPermissions == null || failPermissions.size() == 0) {
            //证明权限已经全部授予过
            call.accept(mPermissions, true);
        } else {
            //检测权限有没有在清单文件中注册
            PermissionUtils.checkPermissions(mActivity, mPermissions);
            //申请没有授予过的权限
            PermissionFragment.newInstant((new ArrayList<>(mPermissions)), mConstant).prepareRequest(mActivity, call);
        }
    }

    /**
     * 检查某些权限是否全部授予了
     *
     * @param context     上下文对象
     * @param permissions 需要请求的权限组
     */
    public static boolean isHasPermission(Context context, String... permissions) {
        ArrayList<String> failPermissions = PermissionUtils.getFailPermissions(context, Arrays.asList(permissions));
        return failPermissions == null || failPermissions.size() == 0;
    }

    /**
     * 检查某些权限是否全部授予了
     *
     * @param context     上下文对象
     * @param permissions 需要请求的权限组
     */
    public static boolean isHasPermission(Context context, String[]... permissions) {
        List<String> permissionList = new ArrayList<>();
        for (String[] group : permissions) {
            permissionList.addAll(Arrays.asList(group));
        }
        ArrayList<String> failPermissions = PermissionUtils.getFailPermissions(context, permissionList);
        return failPermissions == null || failPermissions.size() == 0;
    }

    /**
     * 跳转到应用权限设置页面
     *
     * @param context 上下文对象
     */
    public static void gotoPermissionSettings(Context context) {
        PermissionSettingPage.start(context, false);
    }

    /**
     * 跳转到应用权限设置页面
     *
     * @param context 上下文对象
     * @param newTask 是否使用新的任务栈启动
     */
    public static void gotoPermissionSettings(Context context, boolean newTask) {
       // PermissionSettingPage.start(context, newTask);
        goIntentSetting(context);

    }
    private static void goIntentSetting(Context mContext) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
        intent.setData(uri);
        try {
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
