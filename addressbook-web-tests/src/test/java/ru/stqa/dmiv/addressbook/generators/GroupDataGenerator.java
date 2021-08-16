package ru.stqa.dmiv.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamInclude;
import ru.stqa.dmiv.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class GroupDataGenerator {

  @Parameter(names = {"-c", "count"}, description = "Groups count")
  int count;

  @Parameter(names = {"-f", "file"}, description = "File path")
  String file;

  @Parameter(names = {"-d", "format"}, description = "Data format")
  String format;

  public static void main(String[] args) throws IOException {
    GroupDataGenerator generator = new GroupDataGenerator();
    try {
      JCommander.newBuilder()
              .addObject(generator)
              .build()
              .parse(args);
    } catch (ParameterException e) {
      e.getJCommander().usage();
    }
    generator.run();
  }

  private void run() throws IOException {
    List<GroupData> groups = generate(count);
    if (format.equals("csv")) {
      save(groups, new File(file));
    } else if (format.equals("xml")) {
      saveAsXml(groups, new File(file));
    } else if (format.equals("json")) {
      saveAsJson(groups, new File(file));
    } else {
      System.out.println(String.format("format %s is unrecognized", format));
    }
  }

  private void save(List<GroupData> groups, File file) throws IOException {
    Writer writer = new FileWriter(file);
    for (GroupData group : groups) {
      writer.write(String.format("%s; %s; %s\n", group.getName(), group.getHeader(), group.getFooter()));
    }
    writer.close();
  }

  private void saveAsXml(List<GroupData> groups, File file) throws IOException {
    Writer writer = new FileWriter(file);
    XStream xStream = new XStream();
    xStream.processAnnotations(GroupData.class);
    xStream.toXML(groups, writer);
    writer.close();
  }

  private void saveAsJson(List<GroupData> groups, File file) throws IOException {
    Writer writer = new FileWriter(file);
    Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    gson.toJson(groups, writer);
    writer.close();
  }

  private List<GroupData> generate(int count) {
    List<GroupData> groups = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      groups.add(new GroupData().withName(String.format("name %s", i)).withHeader(String.format("header %s", i))
              .withFooter(String.format("footer %s", i)));
    }
    return groups;
  }
}
