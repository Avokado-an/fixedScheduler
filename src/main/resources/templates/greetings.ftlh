<#ftl output_format="HTML"/>
<#import "components/basic.ftlh" as base>
<#import "components/logHelper.ftlh" as log>
<#assign sec=JspTaglibs["http://www.springframework.org/security/tags"]/>

<@base.page>
    <div class="container">
        Welcome

        <@sec.authorize access="isAuthenticated()">
            <div>
                <p><a href="/tasks" class="tn btn-primary">Tasks</a></p>
                <@log.logout />
            </div>
        </@sec.authorize>
        <@sec.authorize access="!isAuthenticated()">
            <div>
                <p><a href="/login" class="tn btn-primary">Login</a></p>
                <p><a href="/registration" class="tn btn-primary">Registration</a></p>
            </div>
        </@sec.authorize>
    </div>
</@base.page>