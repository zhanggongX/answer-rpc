package tech.zg.answer.common.scanner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class PackageScanner {
    private List<Class<?>> classes;
    private String packagePath;

    public PackageScanner() throws ClassNotFoundException {
        this("");
    }

    public PackageScanner(String basePackage) throws ClassNotFoundException {
        packagePath = System.getProperty("user.dir") + "\\src\\";
        String filePath = packagePath + basePackage.replace('.', '\\');
        classes = new ArrayList<Class<?>>();
        fileScanner(new File(filePath));
    }

    private void fileScanner(File file) throws ClassNotFoundException {
        //5是".java"的长度
        if (file.isFile() && file.getName().lastIndexOf(".java") == file.getName().length() - 5) {
            String filePath = file.getAbsolutePath();
            String qualifiedName = filePath.substring(packagePath.length(), filePath.length() - 5).replace('\\', '.');
            System.out.println(qualifiedName);
            classes.add(Class.forName(qualifiedName));
            return;
        } else if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                fileScanner(f);
            }
        }
    }

    public List<Class<?>> getClasses() {
        return this.classes;
    }
}