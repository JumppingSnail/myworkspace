package cn.com.metamedical.util.comm;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import cn.com.metamedical.util.constant.CommonConstant;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import cn.hutool.json.JSONUtil;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
	public XssHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	private boolean isRichContent(String name) {
		for (String str : CommonConstant.RICHTEXT_ARRAY) {
			if (str.equals(name)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getParameter(String name) {
		String value = super.getParameter(name);

		if (isRichContent(name)) {
			return value;
		}

		if (!StrUtil.hasEmpty(value)) {
			value = HtmlUtil.filter(value);
		}
		return value;
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] values = super.getParameterValues(name);

		if (isRichContent(name)) {
			return values;
		}

		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				String value = values[i];
				if (!StrUtil.hasEmpty(value)) {
					value = HtmlUtil.filter(value);
				}
				values[i] = value;
			}
		}
		return values;
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> parameters = super.getParameterMap();
		Map<String, String[]> map = new LinkedHashMap<>();
		if (parameters != null) {
			for (String key : parameters.keySet()) {
				String[] values = parameters.get(key);

				if (!isRichContent(key)) {
					for (int i = 0; i < values.length; i++) {
						String value = values[i];
						if (!StrUtil.hasEmpty(value)) {
							value = HtmlUtil.filter(value);
						}
						values[i] = value;
					}
				}

				map.put(key, values);
			}
		}
		return map;
	}

	@Override
	public String getHeader(String name) {
		String value = super.getHeader(name);
		if (!StrUtil.hasEmpty(value)) {
			value = HtmlUtil.filter(value);
		}
		return value;
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		InputStream in = super.getInputStream();
		StringBuffer body = new StringBuffer();
		InputStreamReader reader = new InputStreamReader(in, Charset.forName("UTF-8"));
		BufferedReader buffer = new BufferedReader(reader);
		String line = buffer.readLine();
		while (line != null) {
			body.append(line);
			line = buffer.readLine();
		}
		buffer.close();
		reader.close();
		in.close();

		Map<String, Object> map = JSONUtil.parseObj(body.toString());
		Map<String, Object> resultMap = new HashMap<>(map.size());
		for (String key : map.keySet()) {
			Object val = map.get(key);
			if (map.get(key) instanceof String) {
				if (isRichContent(key)) {
					resultMap.put(key, val.toString());
				} else {
					resultMap.put(key, HtmlUtil.filter(val.toString()));
				}

			} else {
				resultMap.put(key, val);
			}
		}
		String str = JSONUtil.toJsonStr(resultMap);
		final ByteArrayInputStream bain = new ByteArrayInputStream(str.getBytes());
		return new ServletInputStream() {
			@Override
			public int read() throws IOException {
				return bain.read();
			}

			@Override
			public boolean isFinished() {
				return false;
			}

			@Override
			public boolean isReady() {
				return false;
			}

			@Override
			public void setReadListener(ReadListener listener) {
			}
		};
	}
}
