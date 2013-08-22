package kr.co.inger.jhyun_jsptags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 감싼 JSP 내용을 캐슁하는 태그.
 * 
 * "jhyun-jsptags-cached"을 캐쉬로서 JCS에서 사용.
 * 
 * @author jhyun
 * @since 2012/06/12/Tue
 */
public class CachedTag extends BodyTagSupport {

	private static final long serialVersionUID = 8593709612151018060L;

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public CachedTag() {
		super();
	}

	public static final String CACHE_NAME = "jhyun-jsptags-cached";

	private JCS getCache() throws CacheException {
		return JCS.getInstance(CACHE_NAME);
	}

	public JCS getOrCreateCache() throws CacheException {
		return this.getCache();
	}

	@Override
	public int doStartTag() throws JspException {
		// check is-there-cached-string
		Object v;
		try {
			v = this.getOrCreateCache().get(getKey());
		} catch (CacheException e1) {
			throw new JspException(e1);
		}
		if (v != null) {
			JspWriter out = this.pageContext.getOut();
			try {
				out.println(v);
			} catch (IOException e) {
				log.error("println FAIL!!!", e);
				throw new JspException(e);
			}
			return SKIP_BODY;
		} else {
			return EVAL_BODY_TAG;
		}
	}

	@Override
	public int doEndTag() throws JspException {
		//
		Object v;
		try {
			v = this.getOrCreateCache().get(getKey());
		} catch (CacheException e1) {
			throw new JspException(e1);
		}
		if (v == null) {
			final String enclosingContent = this.getBodyContent().getString();
			try {
				this.getOrCreateCache().putSafe(getKey(), enclosingContent);
			} catch (CacheException e1) {
				throw new JspException(e1);
			}
			//
			try {
				this.pageContext.getOut().println(enclosingContent);
			} catch (IOException e) {
				log.error("println FAIL!!!", e);
				throw new JspException(e);
			}
		}
		//
		return EVAL_PAGE;
	}

}
