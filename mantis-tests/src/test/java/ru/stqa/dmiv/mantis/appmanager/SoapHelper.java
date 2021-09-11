package ru.stqa.dmiv.mantis.appmanager;

import biz.futureware.mantis.rpc.soap.client.*;
import ru.stqa.dmiv.mantis.model.Issue;
import ru.stqa.dmiv.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class SoapHelper {
  private ApplicationManager app;

  public SoapHelper(ApplicationManager app) {
    this.app = app;
  }

  public Set<Project> getProjects() throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = getMantisConnect();

    ProjectData[] projects = mc.mc_projects_get_user_accessible("administrator", "root");
    return Arrays.asList(projects).stream().map(p -> new Project().withId(p.getId().intValue()).withName(p.getName()))
            .collect(Collectors.toSet());
  }

  private MantisConnectPortType getMantisConnect() throws ServiceException, MalformedURLException {
    return new MantisConnectLocator()
            .getMantisConnectPort(new URL("http://localhost/mantisbt-2.25.2/api/soap/mantisconnect.php"));
  }


  public Issue addIssue(Issue issue) throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = getMantisConnect();
    String[] categories = mc.mc_project_get_categories("administrator",
            "root", BigInteger.valueOf(issue.getProject().getId()));
    IssueData issueData = new IssueData();
    issueData.setSummary(issue.getSummary());
    issueData.setDescription(issue.getDescription());
    issueData.setProject(
            new ObjectRef(BigInteger.valueOf(issue.getProject().getId()), issue.getProject().getName()));
    issueData.setCategory(categories[0]);
    BigInteger issueId = mc.mc_issue_add("administrator", "root", issueData);
    IssueData createdIssueData = mc.mc_issue_get("administrator", "root", issueId);

    return new Issue().withId(issueId.intValue())
            .withSummary(createdIssueData.getSummary())
            .withDescription(createdIssueData.getDescription())
            .withProject(new Project()
                    .withId(createdIssueData.getProject().getId().intValue())
                    .withName(createdIssueData.getProject().getName()));
  }

  public boolean isIssueOpen(int issueId) throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = getMantisConnect();
    IssueData issueData = mc.mc_issue_get("administrator", "root", BigInteger.valueOf(issueId));

    if (issueData == null) {
      return false;
    }

    if (issueData.getStatus().getName().equals("closed")) {
      return false;
    }

    return true;
  }

  public void closeIssue(Issue issue) throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = getMantisConnect();
    String[] categories = mc.mc_project_get_categories("administrator",
            "root", BigInteger.valueOf(issue.getProject().getId()));
    ObjectRef[] statuses = mc.mc_enum_status("administrator", "root");
    System.out.println();
    ObjectRef statusClose = Arrays.asList(statuses).stream().filter(s -> s.getName().equals("closed")).findAny().get();
    IssueData updated = new IssueData();
    updated.setId(BigInteger.valueOf(issue.getId()));
    updated.setStatus(statusClose);
    updated.setSummary(issue.getSummary());
    updated.setDescription(issue.getDescription());
    updated.setStatus(statusClose);
    updated.setProject(new ObjectRef(BigInteger.valueOf(issue.getProject().getId()), issue.getProject().getName()));
    updated.setCategory(categories[0]);

    mc.mc_issue_update("administrator", "root", BigInteger.valueOf(issue.getId()), updated);
  }
}
