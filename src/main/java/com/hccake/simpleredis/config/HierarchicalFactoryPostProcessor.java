package com.hccake.simpleredis.config;

import com.hccake.simpleredis.string.CacheStringAspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 层次注解解析
 * @author wub
 * @version HierarchicalFactoryPostProcessor, v1.0 2019/9/5 14:44
 */
public class HierarchicalFactoryPostProcessor implements BeanFactoryPostProcessor, ResourceLoaderAware {

    private final static List<String> SUB_CACHE_NAMES = new ArrayList<>();

    private final static String CLASS_SUFFIX = ".class";

    private final static String ORIGIN_ANNOTATION = "com.hccake.simpleredis.Cached";

    static {
        SUB_CACHE_NAMES.add(ORIGIN_ANNOTATION);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        try {
            Method declaredMethod = CacheStringAspect.class.getDeclaredMethod("pointCut");
            Pointcut pointcut = declaredMethod.getAnnotation(Pointcut.class);
            InvocationHandler handler = Proxy.getInvocationHandler(pointcut);
            Field field = handler.getClass().getDeclaredField("memberValues");
            field.setAccessible(true);
            Map<String, String> values = (Map<String, String>) field.get(handler);
            // 拼接表达式
            StringBuilder buffer = new StringBuilder();
            for (int i = 0; i < SUB_CACHE_NAMES.size(); i++) {
                if (i == 0) {
                    buffer.append("@annotation(").append(SUB_CACHE_NAMES.get(i)).append(")");
                } else {
                    buffer.append(" || @annotation(").append(SUB_CACHE_NAMES.get(i)).append(")");
                }
            }
            values.put("value",buffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        CachingMetadataReaderFactory cachingMetadataReaderFactory = new CachingMetadataReaderFactory(resourceLoader);
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver(resourceLoader);
        try {
            Resource[] resources = pathMatchingResourcePatternResolver.getResources("/**");
            for(Resource resource:resources){
                String fileName = resource.getFilename();
                if(resource.isFile() && fileName.contains(CLASS_SUFFIX) && resource.isReadable()){
                    ClassMetadata classMetadata = cachingMetadataReaderFactory.getMetadataReader(resource).getClassMetadata();
                    AnnotationMetadata annotationMetadata = cachingMetadataReaderFactory.getMetadataReader(resource).getAnnotationMetadata();
                    if(annotationMetadata.hasAnnotation(ORIGIN_ANNOTATION)){
                        SUB_CACHE_NAMES.add(classMetadata.getClassName());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
