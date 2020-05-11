<#import "components/basic.ftlh" as base>
<#import "components/logHelper.ftlh" as log>

<@base.page>
    <@log.loginsample "/login" false />
    <p><a href="/restorePassword">Forgot password?</a></p>
</@base.page>
