package kr.co.inger.jhyun_jsptags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * <capture var="...">blahblah...</capture>
 * </pre>
 * 
 * 위처럼 capture 태그를 구현.
 * 
 * var으로 속성으로 바인딩.
 * 
 * 그 태그의 감싼 내용을 문자열로 자동으로 평가하여 바인딩.
 * 
 * 이후 레이아웃등을 구현하기 위해서 활용함.
 * 
 * 
 * @author jhyun
 * 
 */
public class CaptureTag extends BodyTagSupport {

	private static final long serialVersionUID = -8741022883951300264L;

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	public CaptureTag() {
		super();
	}

	private String var;

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	/**
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doAfterBody()
	 */
	@Override
	public int doAfterBody() throws JspException {
		pageContext.setAttribute(getVar(), getBodyContent().getString());
		return super.doAfterBody();
	}

}
