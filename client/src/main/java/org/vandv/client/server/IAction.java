package org.vandv.client.server;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface IAction {

	public void execute(OutputStream out, List<String> lines, char[] data) throws IOException;
}
