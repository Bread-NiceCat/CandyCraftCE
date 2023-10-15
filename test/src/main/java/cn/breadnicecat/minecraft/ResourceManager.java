package cn.breadnicecat.minecraft;

import java.io.*;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created in 2023/8/23 21:07
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 */
public class ResourceManager {
	public static InputStream getResource(String resourcePath) throws IOException {
		URL resource = ResourceManager.class.getClassLoader().getResource(resourcePath);
		if (resource == null) throw new FileNotFoundException(resourcePath);
		return resource.openStream();
	}

	public static List<String> readAllLines(Reader reader) throws IOException {
		BufferedReader br;
		if (reader instanceof BufferedReader r) br = r;
		else br = new BufferedReader(reader);
		LinkedList<String> list = new LinkedList<>();
		String line;
		while ((line = br.readLine()) != null) {
			list.add(line);
		}
		return Collections.unmodifiableList(list);
	}


}
