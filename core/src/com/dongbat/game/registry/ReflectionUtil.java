/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.registry;

import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Field;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Admin
 */
public class ReflectionUtil {

  /**
   * Set value for declared field Field data type is String, float, int, long,
   * boolean
   *
   * @param clazz class name
   * @param fieldName field name
   * @param obj object type
   * @param value field value
   */
  static void setFieldValue(Class clazz, String fieldName, Object obj, String value) {

    try {
      Field field = ClassReflection.getDeclaredField(clazz, fieldName);
      field.setAccessible(true);
      Class type = field.getType();
      if (type.equals(float.class)) {
        field.set(obj, Float.parseFloat(value));
      } else if (type.equals(int.class)) {
        field.set(obj, Integer.parseInt(value));
      } else if (type.equals(long.class)) {
        field.set(obj, Long.parseLong(value));
      } else if (type.equals(boolean.class)) {
        field.set(obj, Boolean.parseBoolean(value));
      } else {
        field.set(obj, value);
      }
    } catch (ReflectionException ex) {
      Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * Set object value for declared field
   *
   * @param clazz class name
   * @param fieldName field name
   * @param obj object type
   * @param value field value
   */
  static void setFieldValue(Class clazz, String fieldName, Object obj, Object value) {

    try {
      Field field = ClassReflection.getDeclaredField(clazz, fieldName);
      field.setAccessible(true);
      Class type = field.getType();
      field.set(obj, value);
    } catch (ReflectionException ex) {
      Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * Create Object with data from ObjectMap
   *
   * @param className class name
   * @param data object data
   * @return Object that was just created
   */
  static Object createObject(String className, ObjectMap<String, String> data) {
    Object obj = null;

    try {
      Class clazz = ClassReflection.forName(className.trim());
      obj = clazz.newInstance();

      if (data != null && data.size != 0) {
        for (ObjectMap.Entry<String, String> entry : data) {
          setFieldValue(clazz, entry.key, obj, entry.value);
        }
      }
    } catch (ReflectionException ex) {
      Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
    }

    return obj;
  }

  /**
   * Parse data from string to Object
   *
   * @param clazz class name
   * @param info string want to parse
   * @return Object that was just parsed
   */
  static Object parseData(Class clazz, String info) {
    String[] frags = info.split("\\r?\\n\\r?\\n");
    String data = null;

    if (frags.length == 2) {
      data = frags[1];
    }
    Object object = null;

    try {
      object = clazz.newInstance();

      // process params
      String[] params = frags[0].split("\\r?\\n");
      for (String param : params) {
        String[] split = param.split("=");
        String fieldName = split[0];
        String value = split[1];

        setFieldValue(clazz, fieldName, object, value);
      }

      if (data != null) {
        // process data
        frags = data.split("\\r?\\n");
        ObjectMap<String, String> dataMap = new ObjectMap<String, String>();
        for (String frag : frags) {
          String[] split = frag.split("=");

          dataMap.put(split[0], split[1]);
        }
        Method setData = clazz.getMethod("setData", ObjectMap.class);
        setData.invoke(object, dataMap);
      }
    } catch (InstantiationException ex) {
      Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
    } catch (NoSuchMethodException ex) {
      Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SecurityException ex) {
      Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IllegalArgumentException ex) {
      Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
    } catch (InvocationTargetException ex) {
      Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
    }

    return object;
  }
}
