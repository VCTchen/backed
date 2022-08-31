package yygh.parent.dandp.config;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.Charset;

@Configuration
public class FastJsonHttpMessageConverterEx extends FastJsonHttpMessageConverter {

    public FastJsonHttpMessageConverterEx(){
        //在这里配置fastjson特性(全局设置)
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");    //自定义时间格式
        fastJsonConfig.setCharset(Charset.forName("UTF-8"));
        this.setFastJsonConfig(fastJsonConfig);
    }
    @Override
    protected boolean supports(Class<?> clazz) {
        return super.supports(clazz);
    }

}

