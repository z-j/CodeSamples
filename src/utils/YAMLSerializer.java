package utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import models.Activity;
import models.User;
import net.sourceforge.yamlbeans.YamlReader;
import net.sourceforge.yamlbeans.YamlWriter;

public class YAMLSerializer implements Serializer
{

  private Stack stack = new Stack();
  private File file;

  public YAMLSerializer(File file)
  {
    this.file = file;
  }

  public void push(Object o)
  {
    stack.push(o);
  }

  public Object pop()
  {
    return stack.pop();
  }

  @SuppressWarnings("unchecked")
  public void read() throws Exception
  {
    YamlReader reader = null;

    try
    {
      
      reader = new YamlReader(new FileReader("datastore.yaml"));
      Object object = reader.read();
      Stack tempStack = (Stack)object;
      //System.out.println("stack size java "+stack.size());
      
      Map tempActivityIndex = (Map<Long, Activity>) tempStack.pop();
      Map tempEmailIndex = (Map<String, User>) tempStack.pop();
      Map tempUserIndex = (Map<Long, User>) tempStack.pop();

      Map<Long, Activity> newActivityIndex = new HashMap<Long, Activity>();
      Map<String, User> newEmailIndex = new HashMap<String, User>();
      Map<Long, User> newUserIndex = new HashMap<Long, User>();
      
      Iterator<String> oAI = tempActivityIndex.keySet().iterator();
      while(oAI.hasNext()) {
        String k = oAI.next();
        Activity act = (Activity)tempActivityIndex.get(k);
        newActivityIndex.put(Long.valueOf(k), act);
      }

      Iterator<String> oEI = tempEmailIndex.keySet().iterator();
      while(oEI.hasNext()) {
        String k = oEI.next();
        User usr = (User)tempEmailIndex.get(k);
        newEmailIndex.put(k, usr);
      }

      Iterator<String> oUI = tempUserIndex.keySet().iterator();
      while(oUI.hasNext()) {
        String k = oUI.next();
        User usr = (User)tempUserIndex.get(k);
        newUserIndex.put(Long.valueOf(k), usr);
      }

      
      stack.push(newUserIndex);
      stack.push(newEmailIndex);
      stack.push(newActivityIndex);

      
      
    } catch(Exception e) {
      e.printStackTrace();
    }
    finally
    {
      if (reader != null)
      {
        reader.close();
      }
    }
  }

  public void write() throws Exception
  {
    YamlWriter writer = null;

    try
    {
      writer = new YamlWriter(new FileWriter(file));
      writer.write(stack);
      writer.close();
    }
    finally
    {
      if (writer != null)
      {
        writer.close();
      }
    }
  }
}