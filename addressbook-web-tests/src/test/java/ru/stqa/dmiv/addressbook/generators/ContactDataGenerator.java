package ru.stqa.dmiv.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.stqa.dmiv.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {
  @Parameter(names = {"-c", "count"}, description = "Count rows")
  int count;

  @Parameter(names = {"-f", "file"}, description = "File path")
  String file;

  @Parameter(names = {"-e", "extension"}, description = "File extension")
  String extension;

  public static void main(String[] args) throws IOException {

    ContactDataGenerator generator = new ContactDataGenerator();
    try {
      JCommander.newBuilder()
              .addObject(generator)
              .build()
              .parse(args);
    } catch (ParameterException e) {
      e.getJCommander().usage();
    }
    List<ContactData> contacts = generate(generator.count);

    if (generator.extension.equals("csv")) {
      saveData(contacts, generator.file);
    } else if (generator.extension.equals("xml")) {
      saveDataToXml(contacts, generator.file);
    } else if (generator.extension.equals("json")) {
      saveDatatoJson(contacts, generator.file);
    } else {
      System.out.println(String.format("Format \"%s\" is not recognized", generator.extension));
    }
  }

  private static void saveDatatoJson(List<ContactData> contacts, String file) throws IOException {
    try (Writer writer = new FileWriter(file)) {
      GsonBuilder builder = new GsonBuilder();
      Gson gson = builder.setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
      gson.toJson(contacts, writer);
    }
  }

  private static void saveDataToXml(List<ContactData> contacts, String file) throws IOException {
    try (Writer writer = new FileWriter(file)) {
      XStream xStream = new XStream();
      xStream.processAnnotations(ContactData.class);
      xStream.toXML(contacts, writer);
    }
  }

  private static void saveData(List<ContactData> contacts, String file) throws IOException {
    System.out.println("!!! " + new File(file).getAbsolutePath());
    try (Writer writer = new FileWriter(new File(file))) {
      for (ContactData contact : contacts) {
        String line = String.format("%s;%s;%s;%s;%s;%s;%s;\n", contact.getLastname(), contact.getFirstname(),
                contact.getAddress(), contact.getHomePhone(), contact.getWorkPhone(), contact.getEmail(), contact.getEmail3());

        writer.write(line);
      }
    }
  }

  private static List<ContactData> generate(int count) {
    List<ContactData> list = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      ContactData contact = new ContactData().withLastname(String.format("lastname %s", i))
              .withFirstname(String.format("firstname %s", i)).withAddress(String.format("address %s", i))
              .withWorkPhone(String.format("+7(999)11-22%s", i)).withHomePhone(String.format("555-444%s", i))
              .withEmail(String.format("my_mail%s@ngs.ru", i)).withEmail3(String.format("work_mail%s@ngs.ru", i));
      list.add(contact);
    }
    return list;
  }
}
