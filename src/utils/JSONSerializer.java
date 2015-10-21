package utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Stack;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;

public class JSONSerializer implements Serializer
{

  private Stack stack = new Stack();
  private File file;

  public JSONSerializer(File file)
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
    ObjectInputStream is = null;

    try
    {
      XStream xstream = new XStream(new JettisonMappedXmlDriver());
      is = xstream.createObjectInputStream(new FileReader(file));
      xstream.setMode(XStream.NO_REFERENCES);
      xstream.alias("stack", Stack.class);
      stack = (Stack) is.readObject();
    }
    finally
    {
      if (is != null)
      {
        is.close();
      }
    }
  }

  public void write() throws Exception
  {
    ObjectOutputStream os = null;

    try
    {
      XStream xstream = new XStream(new JettisonMappedXmlDriver());
      os = xstream.createObjectOutputStream(new FileWriter(file));
      xstream.setMode(XStream.NO_REFERENCES);
      xstream.alias("stack", Stack.class);
      os.writeObject(stack);
      /*
       * while (!stack.empty()) { os.writeObject(stack.pop()); }
       */
    }
    finally
    {
      if (os != null)
      {
        os.close();
      }
    }
  }
}