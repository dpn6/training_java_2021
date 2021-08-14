package ru.stqa.dmiv.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
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

  @Parameter(names = {"-f", "file"}, description = "file path")
  String file;

  public static void main(String[] args) throws IOException {
    GroupDataGenerator generator = new GroupDataGenerator();
    try {
      JCommander.newBuilder()
              .addObject(generator)
              .build()
              .parse(args);
    }catch (ParameterException e){
      e.getJCommander().usage();
    }
    generator.run();
  }

  private void run() throws IOException {
    List<GroupData> groups = generate(count);
    save(groups, new File(file));
  }

  private void save(List<GroupData> groups, File file) throws IOException {
    Writer writer = new FileWriter(file);
    for (GroupData group : groups) {
      writer.write(String.format("%s; %s; %s\n", group.getName(), group.getHeader(), group.getFooter()));
    }
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
