package ru.srtqa.dmiv.github;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class GithubTests {

  @Test
  public void testCommits() throws IOException {
    Github github = new RtGithub("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");
    RepoCommits commits = github.repos().get(new Coordinates.Simple("dpn6", "training_java_2021")).commits();

    for(RepoCommit commit : commits.iterate(new ImmutableMap.Builder<String, String>().build())){
      System.out.println(new RepoCommit.Smart(commit).message());
    }
  }
}
