package classes.lab2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class Invoker {
    
    public static List<String> invokeProtectedAndPrivateMethods(Object obj) {
        List<String> results = new ArrayList<>();
        Class<?> clazz = obj.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        results.add("=== ВЫЗОВ АННОТИРОВАННЫХ МЕТОДОВ ЧЕРЕЗ РЕФЛЕКСИЮ ===\n");
        for (Method method : methods) {
            int modifiers = method.getModifiers();
            if (Modifier.isProtected(modifiers) || Modifier.isPrivate(modifiers)) {
                invokeMethodWithAnnotation(obj, method, results);
            }
        }
        return results;
    }
    
    public static List<String> invokePrivateMethods(Object obj) {
        List<String> results = new ArrayList<>();
        Class<?> clazz = obj.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        results.add("=== ВЫЗОВ ПРИВАТНЫХ МЕТОДОВ ===\n");
        for (Method method : methods) {
            int modifiers = method.getModifiers();
            if (Modifier.isPrivate(modifiers)) {
                invokeMethodWithAnnotation(obj, method, results);
            }
        }
        if (results.size() == 1) {
            results.add("Приватные методы не найдены");
        }
        return results;
    }
    
    public static List<String> invokeProtectedMethods(Object obj) {
        List<String> results = new ArrayList<>();
        Class<?> clazz = obj.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        results.add("=== ВЫЗОВ ЗАЩИЩЕННЫХ МЕТОДОВ ===\n");
        for (Method method : methods) {
            int modifiers = method.getModifiers();
            if (Modifier.isProtected(modifiers)) {
                invokeMethodWithAnnotation(obj, method, results);
            }
        }
        if (results.size() == 1) {
            results.add("Защищенные методы не найдены");
        }
        return results;
    }
    
    private static void invokeMethodWithAnnotation(Object obj, Method method, List<String> results) {
        int modifiers = method.getModifiers();
        results.add("Найден метод: " + method.getName() + " (модификатор: " + getModifierName(modifiers) + ")");
        int repeatCount = 1;
        if (method.isAnnotationPresent(Repeat.class)) {
            Repeat annotation = method.getAnnotation(Repeat.class);
            repeatCount = annotation.value();
            results.add("Аннотация @Repeat(" + repeatCount + ") найдена");
        }
        method.setAccessible(true);
        for (int i = 0; i < repeatCount; i++) {
            try {
                StringBuilder callInfo = new StringBuilder();
                callInfo.append("Вызов ").append(i + 1).append("/").append(repeatCount).append(": ");
                Object[] parameters = createDefaultParameters(method.getParameterTypes());
                Object result = method.invoke(obj, parameters);
                if (!method.getReturnType().equals(void.class)) {
                    callInfo.append("Возвращено: ").append(result);
                }
                results.add(callInfo.toString());
            } catch (IllegalAccessException | IllegalArgumentException | 
                     InvocationTargetException e) {
                results.add("ОШИБКА при вызове метода " + method.getName() + ": " + e.getMessage());
            }
        }
        results.add("");
    }
    
    public static List<String> getClassInfo(Object obj) {
        List<String> info = new ArrayList<>();
        Class<?> clazz = obj.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        info.add("=== ИНФОРМАЦИЯ О КЛАССЕ ===");
        info.add("Имя класса: " + clazz.getSimpleName());
        info.add("Полное имя: " + clazz.getName());
        info.add("Количество методов: " + methods.length);
        info.add("");
        info.add("=== СПИСОК ВСЕХ МЕТОДОВ ===");
        for (Method method : methods) {
            StringBuilder methodInfo = new StringBuilder();
            methodInfo.append(getModifierName(method.getModifiers())).append(" ");
            methodInfo.append(method.getReturnType().getSimpleName()).append(" ");
            methodInfo.append(method.getName()).append("(");
            Class<?>[] params = method.getParameterTypes();
            for (int i = 0; i < params.length; i++) {
                if (i > 0) methodInfo.append(", ");
                methodInfo.append(params[i].getSimpleName());
            }
            methodInfo.append(")");
            if (method.isAnnotationPresent(Repeat.class)) {
                Repeat annotation = method.getAnnotation(Repeat.class);
                methodInfo.append(" [@Repeat(").append(annotation.value()).append(")]");
            }
            info.add(methodInfo.toString());
        }
        return info;
    }
    
    private static Object[] createDefaultParameters(Class<?>[] parameterTypes) {
        Object[] parameters = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            parameters[i] = getDefaultValue(parameterTypes[i]);
        }
        return parameters;
    }
    
    private static Object getDefaultValue(Class<?> type) {
        if (type == int.class || type == Integer.class) {
            return 10;
        } else if (type == double.class || type == Double.class) {
            return 3.14;
        } else if (type == boolean.class || type == Boolean.class) {
            return true;
        } else if (type == String.class) {
            return "string";
        } else if (type == char.class || type == Character.class) {
            return 'A';
        } else if (type == long.class || type == Long.class) {
            return 100L;
        } else if (type == float.class || type == Float.class) {
            return 2.5f;
        } else {
            return null;
        }
    }
    
    private static String getModifierName(int modifiers) {
        if (Modifier.isPrivate(modifiers)) {
            return "private";
        } else if (Modifier.isProtected(modifiers)) {
            return "protected";
        } else if (Modifier.isPublic(modifiers)) {
            return "public";
        } else {
            return "package-private";
        }
    }
}