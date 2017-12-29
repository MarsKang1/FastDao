package com.processor;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Created by marsKang on 2017/12/29
 */

public class ClassMakManager {
    private String packName;//包名
    private String className;//类名
    private List<ColumnModel> fields = new ArrayList<>();//对象容器
    private TypeElement typeElement;//类型

    public ClassMakManager(String packName, String className, TypeElement typeElement) {
        this.packName = packName;
        this.className = className;
        this.typeElement = typeElement;
    }

    public void addField(ColumnModel viewField) {
        fields.add(viewField);
    }

    public JavaFile generateCode() {
        //成员变量创建
//        FieldSpec.Builder fid = FieldSpec.builder(TypeName.get(typeElement.asType()), "activity");
        //构造方法创建
//        MethodSpec.Builder con = MethodSpec.constructorBuilder()
//                .addModifiers(Modifier.PUBLIC)
//                .addParameter(TypeName.get(typeElement.asType()), "activity")
//                .addParameter(ClassName.get("android.view", "View"), "view")
//                .beginControlFlow("if (activity == null)")
//                .addStatement("return")
//                .endControlFlow();
//
//        for (ColumnModel f : fields) {
//            con.addStatement("activity.$N = view.findViewById($L)", f.getFieldName(), f.getId());
//        }


        MethodSpec.Builder getSql = MethodSpec.methodBuilder("getSql").addModifiers(Modifier.PUBLIC);
        getSql.addStatement("$T<String,String> map = new $T<String,String>()", Map.class, HashMap.class);

        TypeSpec typeSpec = TypeSpec.classBuilder(className + "_DB")
                .addModifiers(Modifier.PUBLIC)
//                .addField(fid.build())
//                .addMethod(con.build())
                .addMethod(getSql.build())
                .build();
        return JavaFile.builder(packName, typeSpec).build();
    }
}
