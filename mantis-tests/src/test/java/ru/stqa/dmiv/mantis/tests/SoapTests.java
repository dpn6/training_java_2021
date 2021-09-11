package ru.stqa.dmiv.mantis.tests;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import ru.stqa.dmiv.mantis.model.Issue;
import ru.stqa.dmiv.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

public class SoapTests extends TestBase {

  @Test
  public void testGetProjects() throws MalformedURLException, ServiceException, RemoteException {
    Set<Project> projects = app.soap().getProjects();
    System.out.println(projects.size());
    for (Project project : projects) {
      System.out.println(project.getName());
    }
  }

  @Test
  public void testCreateIssue() throws RemoteException, ServiceException, MalformedURLException {
    Project project = app.soap().getProjects().iterator().next();
    Issue issue = new Issue().withSummary("Test issue").withDescription("Test issue description").withProject(project);
    Issue created = app.soap().addIssue(issue);
    Assert.assertEquals(issue.getSummary(), created.getSummary());
  }

  //тест должен быть выполнен, т.к этот баг-репорт (id == 1) был предварительно закрыт
  @Test
  public void testNotNeedSkip() throws RemoteException, ServiceException, MalformedURLException {
    Project project = app.soap().getProjects().iterator().next();
    Issue issue = new Issue().withSummary("Test issue skip").withDescription("To check the skip of test's executing").withProject(project);
    Issue created = app.soap().addIssue(issue);
    app.soap().closeIssue(created);
    skipIfNotFixed(created.getId());
    System.out.println(String.format("Test was executing, because issue %s is closed", created.getId()));
   }

  //тест должен быть пропущен, т.к баг-репорт (id == 1) был предварительно открыт
  @Test
  public void testNeedSkip() throws Exception {
    Project project = app.soap().getProjects().iterator().next();
    Issue issue = new Issue().withSummary("Test issue skip").withDescription("To check the skip of test's executing").withProject(project);
    Issue created = app.soap().addIssue(issue);

    try {
      skipIfNotFixed(created.getId());
      throw new Exception("Test was executing, but issue is open");
    } catch (SkipException e) {
      System.out.println(e.getMessage());
    }
  }

}
