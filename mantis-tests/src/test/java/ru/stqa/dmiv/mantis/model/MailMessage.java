package ru.stqa.dmiv.mantis.model;

public class MailMessage {
  public String to;
  public String text;

  public MailMessage(String to, String message) {
    this.to = to;
    this.text = message;
  }
}
