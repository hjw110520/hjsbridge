package com.hjw.hjsbridge.processor;


import com.google.auto.service.AutoService;
import com.hjw.hjsbridge.annotation.BridgeMethod;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * Created by hjw on 2017/9/6.13:26
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes("com.hjw.hjsbridge.annotation.BridgeMethod")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class BridgeProcessor extends AbstractProcessor {
    private Filer mFiler;
    private Messager messager;
    private Elements mElementUtils; //元素相关的辅助类
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
        mElementUtils = processingEnv.getElementUtils();
        mFiler = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        createJ();
        for (TypeElement typeElement : annotations) {
            for (Element element : roundEnv.getElementsAnnotatedWith(typeElement)) {
                String info = "### content = " + element.toString();
                messager.printMessage(Diagnostic.Kind.NOTE, info);
                //获取Annotation
                BridgeMethod bridgeMethod = element.getAnnotation(BridgeMethod.class);
                if (bridgeMethod != null) {
                    messager.printMessage(Diagnostic.Kind.NOTE, bridgeMethod.value());
                }
            }
        }
        return false;
    }

    private void createJ() {
        try {
            MethodSpec main = MethodSpec.methodBuilder("main")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(void.class)
                    .addParameter(String[].class, "args")
                    .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                    .build();

            TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethod(main)
                    .build();

            JavaFile javaFile = JavaFile.builder("com.hjw.output", helloWorld)
                    .build();

            javaFile.writeTo(mFiler);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
