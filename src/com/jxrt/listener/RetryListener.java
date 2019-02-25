package com.jxrt.listener;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;
import org.testng.internal.annotations.IAnnotationTransformer;

import com.jxrt.util.TestngRetry;

public class RetryListener implements IAnnotationTransformer{
	
	@SuppressWarnings("rawtypes")
    @Override
    public void transform(ITestAnnotation annotation, Class testClass,
            Constructor testConstructor, Method testMethod) {
        IRetryAnalyzer retry = annotation.getRetryAnalyzer();
        if(retry == null){
            annotation.setRetryAnalyzer(TestngRetry.class);
        }
    }
}
