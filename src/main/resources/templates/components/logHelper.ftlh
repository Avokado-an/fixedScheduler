<#import "../components/Validation/inputUserDetails.ftlh" as detailsInputter>

<#macro loginsample path isRegisterForm>
    <div class="container">
        <@detailsInputter.register isRegisterForm, "confirm"/>
    </div>
    <!--<form action="${path}" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Username:</label>
            <div class="col-sm-8">
                <input type="text" name="username" class="form-control"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Password: </label>
            <div class="col-sm-8">
                <input type="password" name="password" class="form-control"/>
            </div>
        </div>
        <#if isRegisterForm>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Email: </label>
                <div class="col-sm-8">
                    <input type="email" name="email" class="form-control" placeholder="some@some.com"/>
                </div>
            </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div><input type="submit" value="Sign In"/></div>
    </form>-->
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