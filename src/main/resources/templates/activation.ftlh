<#import "component/basic.ftlh" as base>
<#import "component/Validation/inputValidator.ftlh" as validator>
<#import  "component/Validation/inputSingleElement.ftlh" as validate>

<@base.page>
    <nav class="nav">
        <a href="/" class="nav-link">Home</a>
        <a href="/login" class="nav-link">Back</a>
    </nav>
    <div class="container">

        <form method="post" enctype="multipart/form-data">
            <@validator.input "Confirm">
                <@validate.input "activationCode", "", "text", "Provide activation code"/>
            </@validator.input>
            <!--<div class="container">
                <div class="row">
                    <div class="col-sm-6">
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <input type="text" name="activationCode" placeholder="code..."/>
                    </div>
                </div>
                <button type="submit">Confirm</button>
            </div>-->
        </form>
    </div>
</@base.page>