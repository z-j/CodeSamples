package models;

import static com.google.common.base.MoreObjects.toStringHelper;

import com.google.common.base.Objects;

public class User 
{
  public String firstName;
  public String lastName;
  public String email;
  public String password;
  
  static Long counter = 0l;
  public Long id;
  

  public User()
  {
  }

  public User(String firstName, String lastName, String email, String password)
  {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    
    this.id = counter++;
  }
  
  
  @Override 
  public String toString() {
    return toStringHelper(this).addValue(firstName)
        .addValue(lastName)
        .addValue(password)
        .addValue(email)                               
        .toString();
  }
  
  @Override  
  public int hashCode()  
  {  
     return Objects.hashCode(this.lastName, this.firstName, this.email, this.password);  
  }  
}