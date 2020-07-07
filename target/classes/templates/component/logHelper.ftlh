<#import "Validation/inputUserDetails.ftlh" as detailsInputter>

<#macro loginsample path isRegisterForm>
    <div class="container">
        <@detailsInputter.register isRegisterForm, "confirm"/>
    </div>
    <#if !isRegisterForm>
        <a href="/registration">Registration</a>
    </#if>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input type="submit" value="Sign Out"/>
    </form>
</#macro>