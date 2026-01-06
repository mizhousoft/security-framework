package com.mizhousoft.security.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * 安全工具类
 *
 * @version
 */
public abstract class SecurityUtils
{
	private static final Logger LOG = LoggerFactory.getLogger(SecurityUtils.class);

	/**
	 * 判断是否为一个表达式路径
	 * 
	 * @param path
	 * @return
	 */
	public static boolean isPatternPath(String path)
	{
		if (path.contains("*"))
		{
			return true;
		}

		return false;
	}

	/**
	 * 路径转换为PathPattern
	 * 
	 * @param paths
	 * @return
	 */
	public static List<PathPattern> convertPathPattern(Set<String> paths)
	{
		List<PathPattern> pathPatterns = new ArrayList<>(paths.size());

		PathPatternParser parser = new PathPatternParser();
		parser.setCaseSensitive(true);

		for (String path : paths)
		{
			LOG.warn("Pattern path is {}.", path);

			PathPattern pattern = parser.parse(path);
			pathPatterns.add(pattern);
		}

		return pathPatterns;
	}

}
