package com.mizhousoft.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import com.mizhousoft.commons.lang.CharEncoding;

/**
 * 入口函数
 *
 * @version
 */
public class ErrorCodeFinderTest
{
	@Test
	public void find() throws Exception
	{
		String projectPath = "..";
		String i18nPath = ".\\src\\main\\resources\\i18n\\security_zh_CN.txt";
		String codePrefix = "\"security.";

		Properties prop = loadProperties(i18nPath);

		Set<String> missKeys = new HashSet<>(10);

		Collection<File> files = FileUtils.listFiles(new File(projectPath), new String[] { "java" }, true);
		for (File file : files)
		{
			if (file.getName().contains("ErrorCodeFinder"))
			{
				continue;
			}

			List<String> lines = FileUtils.readLines(file, CharEncoding.UTF8_NAME);
			for (String line : lines)
			{
				int index = line.indexOf(codePrefix);
				if (index >= 0)
				{
					int end = line.indexOf("\"", index + 1);
					if (end >= 0)
					{
						String key = line.substring(index + 1, end);

						if (!prop.containsKey(key) && !missKeys.contains(key))
						{
							System.out.println(key);

							missKeys.add(key);
						}
					}
				}
			}
		}
	}

	private static Properties loadProperties(String i18nPath) throws Exception
	{
		InputStream in = new FileInputStream(i18nPath);
		Properties ps = new Properties();
		ps.load(in);

		return ps;
	}
}
