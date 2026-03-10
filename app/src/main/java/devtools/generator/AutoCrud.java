package devtools.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;

public class AutoCrud {
    private String className = "";
    private String packageName = "";
    private String artifactName = "";

    public AutoCrud(String className, String packageName, String artifactName){
        this.className = className;
        this.packageName = packageName;
        this.artifactName = artifactName;
    }

    public void createAutoCrud(){
        String objName = className.substring(0, 1).toLowerCase() 
                + className.substring(1);
        String tableName = camelToSnake(className);
        HashMap<String, String> keys = new HashMap<>();
        keys.put("packageName", packageName);
        keys.put("artifactName", artifactName);
        keys.put("className", className);
        keys.put("objName", objName);
        keys.put("tableName", tableName);
        
        Templates templates = new Templates();
        
        String template = templates.getModeloTemplate();
        createJavaFile(template, "model", keys);
        
        template = templates.getDtoTemplate();
        createJavaFile(template, "Dto", keys);
        
        template = templates.getMapperTemplate();
        createJavaFile(template, "Mapper", keys);
        
        template = templates.getSpecificationTemplate();
        createJavaFile(template, "Specifications", keys);
        
        template = templates.getRepositoryTemplate();
        createJavaFile(template, "Repository", keys);
        
        template = templates.getServiceTemplate();
        createJavaFile(template, "Service", keys);
        
        template = templates.getServiceImplTemplate();
        createJavaFile(template, "ServiceImpl", keys);
        
        template = templates.getControllerTemplate();
        createJavaFile(template, "Controller", keys);
    }

    private String buildTemplate(String template, Map<String, String> keys){
        Iterator it = keys.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry<String, String> pair = (HashMap.Entry)it.next();
            String key = pair.getKey().toString().trim();
            String value = pair.getValue().toString().trim();
            template = template.replaceAll(key, value);
        }
        return template;
    }
    
    private String camelToSnake(String input) {
        if (input == null || input.isEmpty())
            return input;
        return input
            .replaceAll("([a-z])([A-Z])", "$1_$2")
            .replaceAll("([A-Z])([A-Z][a-z])", "$1_$2")
            .toLowerCase();
    }
    
    private File createFile(String dir, String className, String fileType) {
        if(fileType.equalsIgnoreCase("model"))
            fileType = "";

        try {
            File directory = new File(dir);
            directory.mkdirs();

            File file = new File(dir + className + fileType + ".java");

            if (file.exists())
                throw new RuntimeException("File already exists: " + file.getName());

            file.createNewFile();
            System.out.println("Created: " + file.getPath());
            return file;

        } catch (IOException e) {
            throw new RuntimeException("Error creating file", e);
        }
    }
    
    private void createJavaFile(String template, String fileType, 
            HashMap<String, String> keys){
        template = buildTemplate(template, keys);
        String className = keys.get("className");
        String packageName = keys.get("packageName");
        String artifactName = keys.get("artifactName");
        String basedir = "src/main/java/com/"+packageName+"/"+artifactName;
        String dir = getDirectory(fileType, basedir);
        if(dir.equals("")){
            throw new RuntimeException("Directory cannot be empty.");
        }
        File file = createFile(dir, className, fileType);
        writeFile(file, template);
    }
    
    private String getDirectory(String fileType, String basedir){
        switch(fileType.toLowerCase()) {
            case "model": return basedir + "/model/";
            case "dto": return basedir + "/dto/";
            case "mapper": return basedir + "/mapper/";
            case "specifications": return basedir + "/specifications/";
            case "repository": return basedir + "/repository/";
            case "service":
            case "serviceimpl": return basedir + "/service/";
            case "controller": return basedir + "/controller/";
            default: return "";
        }
    }
    
    private void writeFile(File file, String content){
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
        } catch (IOException ex) {
            throw new RuntimeException("An error occurred writting file: "+ex.getMessage());
        }
    }
}
