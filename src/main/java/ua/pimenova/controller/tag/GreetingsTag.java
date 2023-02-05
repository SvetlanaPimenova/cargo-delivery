package ua.pimenova.controller.tag;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;

/**
 * GreetingsTag class
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class GreetingsTag extends SimpleTagSupport {
    private String name;

    /**
     *  Writes to JspWriter user's name
     */
    @Override
    public void doTag() throws JspException, IOException {
        if(name != null) {
            JspWriter out = getJspContext().getOut();
            out.print(name);
        }
    }

    /**
     *  Sets user's name.
     */
    public void setName(String userName) {
        this.name = userName;
    }
}
