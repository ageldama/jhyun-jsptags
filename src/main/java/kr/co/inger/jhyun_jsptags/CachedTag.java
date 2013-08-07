package kr.co.inger.jhyun_jsptags;

import java.io.IOException;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 감싼 JSP 내용을 캐슁하는 태그.
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

	private CacheManager cacheManager;

	public CachedTag() {
		super();
		CachingProvider cp = Caching.getCachingProvider();
		this.cacheManager = cp.getCacheManager();
	}

	protected String getCacheName() {
		return "jsp-cached-tag";
	}

	private Cache getCache() {
		return this.cacheManager.getCache(getCacheName());
	}

	@Deprecated
	private boolean isCacheExists(final String cacheName) {
		for (String cn : this.cacheManager.getCacheNames()) {
			if (StringUtils.equals(cn, cacheName)) {
				return true;
			}
		}
		return false;
	}

	public Cache getOrCreateCache() {
		return this.getCache();
	}

	@Override
	public int doStartTag() throws JspException {
		// check is-there-cached-string
		Object v = this.getOrCreateCache().get(getKey());
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
		Object v = this.getOrCreateCache().get(getKey());
		if (v == null) {
			final String enclosingContent = this.getBodyContent().getString();
			this.getOrCreateCache().put(getKey(), enclosingContent);
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
