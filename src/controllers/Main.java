package controllers;

import java.io.IOException;
import java.util.Collection;

import asg.cliche.Command;
import asg.cliche.Param;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;
import models.User;

public class Main
{
  pacemakerAPI paceApi = new pacemakerAPI();

  @Command(description="Create a new User")
  public void createUser (@Param(name="first name") String firstName, @Param(name="last name") String lastName, 
      @Param(name="email")      String email,     @Param(name="password")  String password)
  {
    paceApi.createUser(firstName, lastName, email, password);
  }

  @Command(description="Get a Users details")
  public void getUser (@Param(name="email") String email)
  {
    User user = paceApi.getUser(email);
    System.out.println(user);
  }

  @Command(description="Get all users details")
  public void getUsers ()
  {
    Collection<User> users = paceApi.getUsers();
    System.out.println(users);
  }

  @Command(description="Delete a User")
  public void deleteUser (@Param(name="email") String email)
  {
    paceApi.deleteUser(email);
  }

  public static void main(String[] args) throws IOException
  {
    Shell shell = ShellFactory.createConsoleShell("pc", "Welcome to pcemaker-console - ?help for instructions", new Main());
    shell.commandLoop(); 
  }
}
