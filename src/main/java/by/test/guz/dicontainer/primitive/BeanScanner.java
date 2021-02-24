package by.test.guz.dicontainer.primitive;

import by.test.guz.dicontainer.primitive.annotations.Bean;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;

public class BeanScanner {
    public static void scan(String basePackage, Injector injector) throws IOException, URISyntaxException, ClassNotFoundException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        String path = basePackage.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);

        while (resources.hasMoreElements()) {

            URL resource = resources.nextElement();

            File file = new File(resource.toURI());

            //TODO: add recursive check through subdirectories
            for (File classFile : file.listFiles()) {
                String fileName = classFile.getName();
                if (fileName.endsWith(".class")) {
                    String className = fileName.substring(0, fileName.lastIndexOf("."));
                    Class classObject = Class.forName(basePackage + "." + className);
                    if (classObject.isAnnotationPresent(Bean.class)) {
                        System.out.println("Bean : " + classObject);
                        Bean annotation = (Bean) classObject.getAnnotation(Bean.class);
                        if (annotation.scope().equals("Singleton"))
                            for (Class interfaceUnit : classObject.getInterfaces()) {
                                injector.bindSingleton(interfaceUnit, classObject);
                            }
                        else
                            for (Class interfaceUnit : classObject.getInterfaces()) {
                                injector.bind(interfaceUnit, classObject);
                            }
                    }
                }

            }
        }
    }
}
