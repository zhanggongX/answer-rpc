package tech.zg.answer.server;

import com.sun.corba.se.impl.ior.OldJIDLObjectKeyTemplate;
import javafx.fxml.Initializable;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

/**
* 框架的RPC 服务器（用于将用户系统的业务类发布为 RPC 服务）
* <p>
* @author ：zhanggong
* @version : 1.0.0
* @date ：2018/5/1
*/
public class AnswerServer implements ApplicationContextAware, InitializingBean{

    //用于存储业务接口和实现类的实例对象(由spring所构造)
    private Map<String, Object> handlerMap = new HashMap<String, Object>();




    @Override
    public void afterPropertiesSet() throws Exception {

    }


    /**
     * 通过注解，获取标注了rpc服务注解的业务类的----接口及impl对象，将它放到handlerMap中
     * <p>
     *
     * @param applicationContext spring 上下文
     * @return
     * @throws
     * @author : zhanggong
     * @version : 1.0.0
     * @date : 2018/5/1
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(Answer.class);
        if(MapUtils.isNotEmpty(serviceBeanMap)){
            for(Object serviceBean : serviceBeanMap.values()){
                //从业务实现类上的自定义注解中获取到value，从来获取到业务接口的全名
                String interfaceName = serviceBean.getClass().getAnnotation(Answer.class).value().getName();
                handlerMap.put(interfaceName, serviceBean);
            }
        }
    }
}