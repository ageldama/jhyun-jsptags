package kr.co.inger.jhyun_jsptags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 이 태그로 감싼 블럭은 요청된 내에서 한번만 평가함.
 * 
 * @author jhyun
 * @since 2012/10/11
 */
public class EvalOnlyOnceTag extends BodyTagSupport {

	private static Logger log = LoggerFactory.getLogger(EvalOnlyOnceTag.class);

	private static final long serialVersionUID = 8953413826615357330L;

	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public EvalOnlyOnceTag() {
		super();
	}

	@Override
	public int doStartTag() throws JspException {
		final Object touch = pageContext.getAttribute(getKey(),
				PageContext.REQUEST_SCOPE);
		if (touch == null) {
			log.debug(String.format("eval-block [%s]", getKey()));
			pageContext.setAttribute(getKey(), "true",
					PageContext.REQUEST_SCOPE);
			return super.doStartTag();
		} else {
			if (bodyContent != null)
				bodyContent.clearBody();
			log.debug(String.format("skip-eval-block [%s]", getKey()));
			return SKIP_BODY;
		}
	}

	@Override
	public int doEndTag() throws JspException {
		if (bodyContent != null) {
			try {
				bodyContent.writeOut(bodyContent.getEnclosingWriter());
			} catch (IOException e) {
				log.warn("body-content write fail", e);
			}
		}
		return EVAL_PAGE;
	}

}
