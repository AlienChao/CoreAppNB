package demo.core.com.coreapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.EncryptUtils;

import demo.core.com.coreapp.R;

public class RSA_Activity extends AppCompatActivity {

    private Button btn;
    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsa_);
        initView();
    }

    private void initView() {
        btn = (Button) findViewById(R.id.btn);
        tvContent = (TextView) findViewById(R.id.tv_content);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String data = "abc";
//                byte[] bytes = EncryptUtils.decryptRSA(data.getBytes(), PUCLIC_KEY.getBytes(), true, "RSA/CBC/PKCS1Padding");
//                tvContent.setText(bytes.toString());
                byte[] bytes = EncryptUtils.decryptBase64RSA(data.getBytes(), PUCLIC_KEY.getBytes(), true, "RSA/CBC/PKCS1Padding");
                tvContent.setText(bytes.toString());
            }
        });
    }

    public static String PUCLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAj4m5NR/5deorF8r2fZRV1mmPMU81/Hjn\n"
            + "WDcgbTEZJZtgXDDqRMTtDejumQ7CXoRvWRtWBzFWi05r34CTbgdl9KY4vvo+IrxPoJ3+gRgm3i1P\n"
            + "E09dRkEsyHuCKmBYduF4KSvdiEnQYfz20n4LNStawV5Lm9L1f7BwPju1FXytOLsuc40Gpcoqgvy1\n"
            + "XliRVYBaRyId2csJTsL82HynkrbNC+bIdtIEz/zdcYFiI1JgeY+S0aJ9UXPJFVidmiTyjVg9leWP\n"
            + "EscjHY4VvKMfEifmlsHV6pEgdPpAzvC98km4GAchXvJ6BPSd0q+XtIx+mmiKAQZbUKclUYWMqthH\n"
            + "vWQpLQIDAQAB";
    public static String PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCPibk1H/l16isXyvZ9lFXWaY8x\n"
            + "TzX8eOdYNyBtMRklm2BcMOpExO0N6O6ZDsJehG9ZG1YHMVaLTmvfgJNuB2X0pji++j4ivE+gnf6B\n"
            + "GCbeLU8TT11GQSzIe4IqYFh24XgpK92ISdBh/PbSfgs1K1rBXkub0vV/sHA+O7UVfK04uy5zjQal\n"
            + "yiqC/LVeWJFVgFpHIh3ZywlOwvzYfKeSts0L5sh20gTP/N1xgWIjUmB5j5LRon1Rc8kVWJ2aJPKN\n"
            + "WD2V5Y8SxyMdjhW8ox8SJ+aWwdXqkSB0+kDO8L3ySbgYByFe8noE9J3Sr5e0jH6aaIoBBltQpyVR\n"
            + "hYyq2Ee9ZCktAgMBAAECggEAXiHAzyEn9PdbyV+2ZMa5wEI7wOekjO0xMn38iv9O8YXDHNKLYAwR\n"
            + "NjUS/FdciqWpbDNsKI1npzYHsmxcwySg7V9xCXWM+DSst7XFaR3Qp5XpLM24atFlMBknVOkYOuZE\n"
            + "aiO1RaK6wPgnWgWFDhpfiz5usUMY8uNfyMBZUj0QUIocjC2gyWV4N429GB7bpAN5DOdqhFiq4S4i\n"
            + "xlAEMB4GIxhr8ZJ55x2PM4jFv+cySzvhFW3Y/goHQHuYDu8VF+P3WpAZ+11lkccuFQX6Fg/KsaXd\n"
            + "NrMab9eIaCJXJ7pRvpsYgSQfnt6Z6+5dqVTGwqV2VLflFWV4V4xhI+OvSuBpgQKBgQDPQhlgZIiy\n"
            + "vhLM2Mm9suTtOOfeY26Em45piNWOQCOglfwNjzpzDykpfdmVMUG0CVt2HPWlVuq83KHrlQsyWNzC\n"
            + "trRP3j78OSlhGFn2T7TSUAP/Hu5XUEnX1J6rzj08cn2vIL79W/kEOESD5l+lkHLTtXKt8Hxw9ZxI\n"
            + "vJ+AghbZYQKBgQCxS195rtudokWFEAhTo5DAcufeUJ7dhdFJ3DH2oDECv0vg8r1s5cD99Bittk+V\n"
            + "eUxGfPEKulNEBJ0sykAjvhYwolVJKt/QB2siJ6RQtXorNLJBMnomfgd/QDJT1ueWFOF7LRi4Zx2b\n"
            + "OFzCt38fe5IgkXS6nvY5abU22USqh8cnTQKBgAcNYruq8BPU7+LUhZjU24FO7GTe0UXl3SSuKUYb\n"
            + "3Jp8QAVeGEkN/QKSa7Lt6CRuJRSRy7ICkR44C7JuceEWsPxXJy+IinAeqc/J+lWtRKPbYSdvMAD8\n"
            + "lGapr3R9IgxKgQhSfu00EfcYywwJ7Ls/fQjDJdHRWwqk8NpwwKSoyIpBAoGAA68/UrYRllsS+sr0\n"
            + "gVh636UDsJkYVB491T0gRLHbkuUK3Kfuy96SfjSedq29Rav5q9VY5RIuPNGjupSRdKy0pVoaT6T2\n"
            + "dcpRraoJPiwnV9KGidhUtYwU984LvbZPpczVSswLIXnzIZCwTNOnbq4QwWCEfoRjNZo+y98EEE82\n"
            + "ElkCgYEAvfGljZYkVkN34Jm8jgNSeIbpkajOy5ob5u61Q9BLhCc287blig0cfs9JkrFhzI6qg1q1\n"
            + "yRuCuB3T2RmUs8KP4jO4078ZilnL7VVF5JNFIvFNU+uU+Z9mGfAP6CJ3apFei3cMHgs4fJ1vnqG+\n"
            + "bVLlrwxYR+IocPzfcJLIS6Wca08=";
}
