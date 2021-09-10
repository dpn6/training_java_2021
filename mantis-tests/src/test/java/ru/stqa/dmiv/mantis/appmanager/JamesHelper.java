package ru.stqa.dmiv.mantis.appmanager;

import org.apache.commons.net.telnet.TelnetClient;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.dmiv.mantis.model.MailMessage;

import javax.mail.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JamesHelper {
//  private final ApplicationManager app;
//  private final TelnetClient telnet;
//  private final Session mailSession;

  private ApplicationManager app;

  private TelnetClient telnet;
  private InputStream in;
  private PrintStream out;

  private Session mailSession;
  private Store store;
  private String mailserver;


  public JamesHelper(ApplicationManager app) {
    this.app = app;
    this.telnet = new TelnetClient();
    this.mailSession = Session.getDefaultInstance(System.getProperties());
  }

  public void createUser(String user, String password) {
    initTelnetSession();
    write(String.format("adduser %s %s", user, password));
    String result = readUntil(String.format("User %s added", user));
    closeTelnetSession();
  }

  private void initTelnetSession() {
    mailserver = app.getProperty("mailserver.host");
    int port = Integer.parseInt(app.getProperty("mailserver.port"));
    String login = app.getProperty("mailserver.adminlogin");
    String password = app.getProperty("mailserver.adminpassword");

    try {
      telnet.connect(mailserver, port);
      in = telnet.getInputStream();
      out = new PrintStream(telnet.getOutputStream());
    } catch (Exception e) {
      e.printStackTrace();
    }

    //Don't know why it doesn't allow login at the first attempt
    readUntil("Login id:");
    write("");
    readUntil("Password:");
    write("");

    //Second login attempt, must be successful
    readUntil("Login id:");
    write(login);
    readUntil("Password:");
    write(password);

    //Read welcome message
    readUntil(String.format("Welcome %s. HELP for a list of commands", login));
  }

  private String readUntil(String pattern) {
    char lastChar = pattern.charAt(pattern.length() - 1);
    StringBuffer sb = new StringBuffer();
    try {
      char ch = (char) in.read();
      while (true) {
        System.out.print(ch);
        sb.append(ch);
        if (ch == lastChar) {
          if (sb.toString().endsWith(pattern)) {
            return sb.toString();
          }
        }
        ch = (char) in.read();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private void write(String value) {
    try {
      out.println(value);
      out.flush();
      System.out.println(value);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void closeTelnetSession() {
    write("quit");
  }

  public List<MailMessage> waitForMail(String username, String password, long timeout) throws MessagingException {
    long now = System.currentTimeMillis();

    while (System.currentTimeMillis() < (now + timeout)) {
      List<MailMessage> allMail = getAllMail(username, password);
      if (allMail.size() > 0) {
        return allMail;
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    throw new Error("No mail :(");
  }

  public List<MailMessage> getAllMail(String username, String password) throws MessagingException {
    Folder inbox = openInbox(username, password);
    List<MailMessage> messages = Arrays.asList(inbox.getMessages()).stream().map(m -> toModelMail(m))
            .collect(Collectors.toList());
    closeFolder(inbox);
    return messages;
  }

  public static MailMessage toModelMail(Message m) {
    try {
      for (int i = 0; i < m.getAllRecipients().length; i++) {
        System.out.println(i + " " + m.getAllRecipients()[i].toString());
        System.out.println(i + " Content:  " + (String) m.getContent());
      }
      return new MailMessage(m.getAllRecipients()[0].toString(), (String) m.getContent());
    } catch (MessagingException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage message = mailMessages.stream().filter(m -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(message.text);
  }

  private void closeFolder(Folder folder) throws MessagingException {
    folder.close(true);
    store.close();
  }

  private Folder openInbox(String username, String password) throws MessagingException {
    if (mailserver == null) {
      mailserver = app.getProperty("mailserver.host");
    }
    store = mailSession.getStore("pop3");
    store.connect(mailserver, username, password);
    Folder folder = store.getDefaultFolder().getFolder("INBOX");
    folder.open(Folder.READ_WRITE);
    return folder;
  }


  public void drainEmail(String username, String password) throws MessagingException {
    Folder inbox = openInbox(username, password);
    for(Message m : inbox.getMessages()){
      m.setFlag(Flags.Flag.DELETED, true);
    }
    closeFolder(inbox);
  }
}
