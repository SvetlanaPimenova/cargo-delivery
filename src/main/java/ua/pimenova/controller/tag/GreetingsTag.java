package ua.pimenova.controller.tag;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;

public class GreetingsTag extends SimpleTagSupport {
    private String name;

    @Override
    public void doTag() throws JspException, IOException {
        if(name != null) {
            JspWriter out = getJspContext().getOut();
            out.print(name);
        }
    }

    public void setName(String userName) {
        this.name = userName;
    }
}
