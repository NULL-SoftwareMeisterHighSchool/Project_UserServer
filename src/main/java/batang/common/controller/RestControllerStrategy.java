package batang.common.controller;


import javax.mail.MessagingException;

public interface RestControllerStrategy
{
	public Object execute() throws MessagingException;
}
