package com.processor;



import com.google.auto.service.AutoService;
import com.models.Column;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

/**
 * Created by kjh 2017/12/29
 */
@AutoService(Processor.class)
public class DBProcessor extends AbstractProcessor {
    private Filer filer;
    private Elements elements;
    private Map<String, ClassMakManager> map = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
        elements = processingEnvironment.getElementUtils();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(Column.class.getCanonicalName());
        return annotations;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        map.clear(); //该方法会被调用多次, 所以每次进入时, 首先清空之前的脏数据
        for (Element e : roundEnvironment.getElementsAnnotatedWith(Column.class)) {
            performParseAnnotations(e);
        }
        for (ClassMakManager vc : map.values()) {
            try {
                vc.generateCode().writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private void performParseAnnotations(Element element) {
        VariableElement variableElement = (VariableElement) element;
        TypeElement typeElement = (TypeElement) variableElement.getEnclosingElement();
        String className = typeElement.getSimpleName().toString();
        ClassMakManager viewClass = map.get(className);
        ColumnModel field = new ColumnModel(variableElement);
        if (viewClass == null) {
            viewClass = new ClassMakManager(elements.getPackageOf(variableElement).getQualifiedName().toString(), className, typeElement);
            map.put(className, viewClass);
        }
        viewClass.addField(field);
    }


}
