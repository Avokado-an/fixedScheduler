<#import "component/basic.ftlh" as c>
<#import "component/logHelper.ftlh" as l>

<@c.page>
    <nav class="nav">
        <a href="#" class="nav-link disabled">${message!"Add new user"}</a>
        <a href="/" class="nav-link">Home</a>
        <a href="/login" class="nav-link">Back</a>
    </nav>
    <@l.loginsample "/registration" true />
</@c.page>