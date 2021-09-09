package ru.stqa.dmiv.mantis.appmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.stqa.dmiv.mantis.model.UserData;
import ru.stqa.dmiv.mantis.model.Users;

import java.util.List;

public class DbHelper {
  private final SessionFactory sessionFactory;
  private final ApplicationManager app;
  Logger logger = LoggerFactory.getLogger(DbHelper.class);

  public DbHelper(ApplicationManager app) {

    this.app = app;

//  public DbHelper() {
    // A SessionFactory is set up once for an application!
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure() // configures settings from hibernate.cfg.xml
            .build();

    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
  }


  public Users users() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<UserData> result = session.createQuery("from UserData").list();
    for (UserData user : result) {
      System.out.println(user);
    }
    session.getTransaction().commit();
    session.close();
    return new Users(result);
  }


}
