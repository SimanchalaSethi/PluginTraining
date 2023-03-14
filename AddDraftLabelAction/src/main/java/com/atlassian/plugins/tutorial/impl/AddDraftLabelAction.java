package com.atlassian.plugins.tutorial.impl;

import com.atlassian.confluence.core.ConfluenceActionSupport;
import com.atlassian.confluence.pages.AbstractPage;
import com.atlassian.confluence.pages.actions.PageAware;
import com.atlassian.confluence.labels.Label;
import com.atlassian.confluence.labels.LabelManager;
import com.atlassian.confluence.labels.Labelable;

public class AddDraftLabelAction extends ConfluenceActionSupport implements
        PageAware {

    private AbstractPage page;
    private LabelManager labelManager;

    @Override
    public AbstractPage getPage()
    {
        return page;
    }

    public void setPage(AbstractPage page)
    {
        this.page = page;
    }

    @Override
    public boolean isLatestVersionRequired() {
        return true;
    }

    @Override
    public boolean isPageRequired() {
        return true;
    }

    @Override
    public boolean isViewPermissionRequired() {
        return true;
    }

    @Override
    public boolean isEditPermissionRequired() {
        return false;
    }

    public void setLabelManager(LabelManager labelManager) {
        this.labelManager = labelManager;
    }

    public String execute() {
        Label label = new Label("draft");
        labelManager.addLabel((Labelable) page, label);
        return "success";
    }
}