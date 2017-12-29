package com.commonlib;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by desperado on 17-11-28.
 */

public class ViewBinding {

    private static String LATTER_NAME = "_DB";

    private static Map<Class<?>, Constructor<?>> CACHE = new HashMap<>();

    public static <T extends Activity> void bind(T target) {
        View sourceView = target.getWindow().getDecorView();
        bind(target, sourceView);
    }

    private static <T extends Activity> void bind(T target, View sourceView) {
        Class<? extends Activity> cl = target.getClass();
        try {
            Class<?> bindClass = findBindClass(cl);
            Constructor<?> constructor = findBindConstructor(bindClass, cl);
            constructor.newInstance(target, sourceView);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static Class<?> findBindClass(Class<?> targetClass) throws ClassNotFoundException {
        return targetClass.getClassLoader().loadClass(targetClass.getName() + LATTER_NAME);
    }

    private static Constructor<?> findBindConstructor(Class<?> bindClass, Class<? extends Activity> targetClass) throws NoSuchMethodException {
        Constructor<?> constructor = CACHE.get(bindClass);
        if (constructor == null) {
            constructor = bindClass.getConstructor(targetClass, View.class);
            CACHE.put(bindClass, constructor);
        }
        return constructor;
    }
}
