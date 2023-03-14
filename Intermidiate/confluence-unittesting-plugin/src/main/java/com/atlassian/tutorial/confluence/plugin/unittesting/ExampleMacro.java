
package com.atlassian.tutorial.confluence.plugin.unittesting;

import java.util.Map;
import java.util.List;
import com.atlassian.confluence.content.render.xhtml.ConversionContext;
import com.atlassian.confluence.macro.Macro;
import com.atlassian.confluence.macro.MacroExecutionException;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.confluence.user.AuthenticatedUserThreadLocal;
import com.atlassian.user.User;
import com.opensymphony.util.TextUtils;

public class ExampleMacro implements Macro
{
    private final PageManager pageManager;
    private final SpaceManager spaceManager;

    public ExampleMacro(PageManager pageManager, SpaceManager spaceManager)
    {
        this.pageManager = pageManager;
        this.spaceManager = spaceManager;
    }

    @Override
    public String execute(Map<String, String> parameters, String body, ConversionContext context) throws MacroExecutionException
    {
        StringBuffer result = new StringBuffer();

        User user = AuthenticatedUserThreadLocal.getUser();
        if (user != null)
        {
            String greeting = "<p>Hello <b>" + TextUtils.htmlEncode(user.getFullName()) + "</b>.</p>";
            result.append(greeting);
        }

        List<Page> list = pageManager.getRecentlyAddedPages(55, "DS");
        result.append("<p>");
        result.append("Some stats for <b>Demonstration Space</b>:");
        result.append("<table class=\"confluenceTable\">");
        result.append("<thead><tr><th class=\"confluenceTh\">Page</th><th class=\"confluenceTh\">Number of children</th></tr></thead>");
        result.append("<tbody>");
        for (Page page : list)
        {
            int numberOfChildren = page.getChildren().size();
            String pageWithChildren = "<tr><td class=\"confluenceTd\">" + TextUtils.htmlEncode(page.getTitle()) + "</td><td class=\"confluenceTd\" style=\"text-align:right\">" + numberOfChildren + "</td></tr>";
            result.append(pageWithChildren);
        }
        result.append("</tbody>");
        result.append("</table>");
        result.append("</p>");

        String spaces = "<p>Altogether, this installation has <b>" + spaceManager.getAllSpaces().size() + "</b> spaces.</p>";
        result.append(spaces);

        return result.toString();
    }

    @Override
    public BodyType getBodyType()
    {
        return BodyType.NONE;
    }

    @Override
    public OutputType getOutputType()
    {
        return OutputType.BLOCK;
    }
}
