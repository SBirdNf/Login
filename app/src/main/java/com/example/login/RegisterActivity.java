package com.example.login;




import android.content.Intent;
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
import com.example.login.util.OKHttpUtil;



import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {


    private TextView email;
    private TextView name;
    private TextView password1;
    private TextView password2;
    private TextView code;

    @Override



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button button1 = (Button) findViewById(R.id.bt_vcode_reg);//id后面为上方button的id
        Button button2 = (Button) findViewById(R.id.bt_register_reg);
        Button button3 = (Button) findViewById(R.id.bt_already_reg);
        name = (TextView) findViewById(R.id.et_name_reg);
        email =(TextView) findViewById(R.id.et_user_reg);
        password1 =(TextView) findViewById(R.id.et_password_reg) ;
        password2=(TextView) findViewById(R.id.et_conpassword_reg);
        code = (TextView) findViewById(R.id.et_vcode_reg) ;


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String s = email.getText().toString();

                            Map<String,String> map=new HashMap<>();
                            map.put("email",s);
                            OKHttpUtil http=new OKHttpUtil();
                            Response response=http.getHttpWithParam("http://"+ Ip.ip+":8082/getRCode",map);
                            System.out.println(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(getApplicationContext(),"fddf",Toast.LENGTH_SHORT).show();


               // password1 = editText1.getText().toString().trim();//第一次输入的密码赋值给password1
                // password2 = editText2.getText().toString().trim();//第二次输入的密码赋值给password2
                if (password1.getText().toString().equals("")||password2.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"密码不能为空",Toast.LENGTH_SHORT).show();
                    Toast.makeText(RegisterActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                }else if (password1.getText().toString().equals(password2.getText().toString())){
                    Toast.makeText(getApplicationContext(),"fddf",Toast.LENGTH_SHORT).show();

                final Member member =new Member();
                member.setName(name.getText().toString());
                member.setPassword(password1.getText().toString());
                member.setEmail(email.getText().toString());

                final String vcode=code.getText().toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {


                            Map<String,String> map=new HashMap<>();
                            map.put("code",vcode);
                            OKHttpUtil http=new OKHttpUtil();
                            //Response response=http.getHttpWithParam("http://"+ Ip.ip+":8082/registe",map);
                            Response response=http.postHttpWithBody("http://"+ Ip.ip+":8082/registe",member,map);
                            System.out.println(response.body().string());
                            //JSONObject obect= JSON.parseObject(response.body().string());
                            //System.out.println(obect.get("msg"));
                            //Toast.makeText(RegisterActivity.this,obect.get("msg"),Toast.LENGTH_SHORT).show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }else if(password1.getText().toString().equals("")!=password2.getText().toString().equals("")){
                Toast.makeText(getApplication(),"密码不一致，请重新输入",Toast.LENGTH_SHORT).show();
                }
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);//this前面为当前activty名称，class前面为要跳转到得activity名称
                startActivity(intent);
            }
        });

    }
}
