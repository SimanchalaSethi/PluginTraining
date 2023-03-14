
package com.atlassian.tutorial.confluence.plugin.unittesting;

import com.atlassian.tutorial.confluence.plugin.unittesting.ExampleMacro;
import com.atlassian.confluence.content.render.xhtml.ConversionContext;
import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.confluence.user.AuthenticatedUserThreadLocal;
import com.atlassian.user.impl.DefaultUser;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Testing {@link com.atlassian.tutorial.confluence.plugin.unittesting.ExampleMacro}
 */
@RunWith (MockitoJUnitRunner.class)
public class ExampleMacroTest extends TestCase
{
    @Mock
    private PageManager pageManager;
    @Mock
    private SpaceManager spaceManager;
    @Mock
    private ConversionContext conversionContext;
    @Test
    public void testOutputIncludesRecentPages() throws Exception
    {
        // create test page
        Page page = new Page();
        page.setTitle("Page title");
// set up stub method to return our test page
        when(pageManager.getRecentlyAddedPages(55, "DS")).thenReturn(Arrays.asList(page));
// create the macro
        ExampleMacro exampleMacro = new ExampleMacro(pageManager, spaceManager);
// verify that the output contains the page title
        String output = exampleMacro.execute(new HashMap(), "", conversionContext);
        assertTrue("Output should contain page title but was: " + output,
                output.contains(page.getTitle())); // Test code will go here
    }
    @Test
    public void testOutputIncludesCurrentUser() throws Exception
    {
        // create test user
        DefaultUser user = new DefaultUser("test");
        user.setFullName("Test User");
        // create the macro
        ExampleMacro exampleMacro = new ExampleMacro(pageManager, spaceManager);
        // set current user to test user
        AuthenticatedUserThreadLocal.setUser(user);
        try
        {
            // verify that the output contains the current user
            String output = exampleMacro.execute(new HashMap(), "", conversionContext);
            assertTrue("Output should contain current user but was: " + output,
                    output.contains(user.getFullName()));
        }
        finally
        {
            // reset current user
            AuthenticatedUserThreadLocal.setUser(null);
        }
    }
}