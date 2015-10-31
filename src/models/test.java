package models;

import java.io.FileReader;
import java.util.Map;
import java.util.Stack;

import net.sourceforge.yamlbeans.YamlReader;

public class test
{

  public static void main(String[] args) throws Exception
  {


    YamlReader reader = new YamlReader(new FileReader("datastore.yaml"));
    Object object = reader.read();
    Stack stck = (Stack)object;
    
    System.out.println(stck.toString());
    System.out.println(stck.size());
    
    Map<Long, Activity> activityIndex = (Map<Long, Activity>) stck.pop();
    Map<String, User> emailIndex = (Map<String, User>) stck.pop();
    Map<Long, User> userIndex = (Map<Long, User>) stck.pop();

    System.out.println("Activity Index:");
    System.out.println(activityIndex.toString());
    
    System.out.println("email Index:");
    System.out.println(emailIndex.toString());
    
    System.out.println("user Index:");
    System.out.println(userIndex.toString());
    
    Object obj = stck.pop();
    System.out.println("Obj");
    System.out.println(obj);

    obj = stck.pop();
    System.out.println("Obj2");
    System.out.println(obj);

    obj = stck.pop();
    System.out.println("Obj3");
    System.out.println(obj);

    obj = stck.pop();
    System.out.println("Obj4");
    System.out.println(obj);

  }
}
