package demo.core.com.coreapp.http.requst;


import com.powerrich.corelib.utils.DESUtil;

/**
 * Created by fanliang on 18/5/27.
 * 数据参数拼接类
 */

public class ApiRequestParam {
    public static String login(String staffName, String password){
        String xml = "<Root>" +
                "<Action>login</Action>" +
                "<Parameter>" +
                "<staffName>"+staffName+"</staffName>" +
                "<password>"+password+"</password>" +
                "</Parameter>" +
                "</Root>";
        return DESUtil.enctryXml(xml);
    }


    public static String modifyPwd(String staffNo, String authToken, String oldPassword, String newPassword){
        String xml = "<Root>" +
                "<Action>updatePassword</Action>" +
                "<Parameter>" +
                "<staffNo>"+staffNo+"</staffNo>" +
                "<authToken>"+authToken+"</authToken>" +
                "<oldPassword>"+oldPassword+"</oldPassword>" +
                "<newPassword>"+newPassword+"</newPassword>" +
                "</Parameter>" +
                "</Root>";
        return DESUtil.enctryXml(xml);
    }

    public static String logout(String authToken){
        String xml = "<Root>" +
                "<Action>logout</Action>" +
                "<Parameter>" +
                "<authToken>"+authToken+"</authToken>" +
                "</Parameter>" +
                "</Root>";
        return DESUtil.enctryXml(xml);
    }

    public String getUserInfo(String staffNo, String authToken){
        String xml = "<Root>" +
                "<Action>getUserInfo</Action>" +
                "<Parameter>" +
                "<staffNo>"+staffNo+"</staffNo>" +
                "<authToken>"+authToken+"</authToken>" +
                "</Parameter>" +
                "</Root>";
        return DESUtil.enctryXml(xml);
    }

    public static String updateUserInfo(String staffNo, String authToken
    , String realName, String sex, String phone, String mobile){
        String xml = "<Root>" +
                "<Action>editUserInfo</Action>" +
                "<Parameter>" +
                "<staffNo>"+staffNo+"</staffNo>" +
                "<authToken>"+authToken+"</authToken>" +
                "<realName>"+realName+"</realName>" +
                "<sex>"+sex+"</sex>" +
                "<phone>"+phone+"</phone>" +
                "<mobile>"+mobile+"</mobile>" +
                "</Parameter>" +
                "</Root>";
        return DESUtil.enctryXml(xml);
    }
}
