package com.example.login;



import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.login.common.Ip;
import com.example.login.entity.Member;
import com.example.login.util.OKHttpUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Response;

public class FindPWActivity extends AppCompatActivity {

    private TextView email;
    private TextView password1;
    private TextView password2;
    private TextView code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pw);
        Button button1 = (Button) findViewById(R.id.bt_vcode_fpw);//找回密码页面获取验证码按钮
        Button button2 = (Button) findViewById(R.id.bt_get_fpw);//找回密码页面找回按钮
        email = (TextView) findViewById(R.id.et_user_fpw);//找回密码页面账号输入框
        password1 = (TextView) findViewById(R.id.et_password_fpw);//找回页面密码输入框
        password2 = (TextView) findViewById(R.id.et_conpassword_fpw);//找回页面确认密码输入框
        code = (TextView) findViewById(R.id.et_vcode_fpw);//找回页面验证码输入框

        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            String s = email.getText().toString();
                            Map<String,String> map=new HashMap<>();
                            map.put("email",s);
                            OKHttpUtil http=new OKHttpUtil();
                            Response response=http.getHttpWithParam("http://"+ Ip.ip+":8082/getFCode",map);
                            System.out.println(response.body().string());
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (password1.getText().toString().equals("")||password2.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"密码不能为空",Toast.LENGTH_SHORT).show();
                    Toast.makeText(FindPWActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                }else if(password1.getText().toString().equals(password2.getText().toString())){

                   final String vemail = email.getText().toString();
                   final String vpassword = password1.getText().toString();

                    final String vcode=code.getText().toString();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{


                                Map<String,String> map=new HashMap<>();
                                map.put("email",vemail);
                                map.put("password",vpassword);
                                map.put("code",vcode);
                                OKHttpUtil http=new OKHttpUtil();
                                Response response=http.postHttpWithBody("http://"+Ip.ip+":8082/forgetPassword",null,map);
                                System.out.println(response.body().string());
                                JSONObject obect= JSON.parseObject(response.body().string());
                                System.out.println(obect.get("msg"));

                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }else if (password1.getText().toString().equals("")!=password2.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"两次密码不一致，请重新输入",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
