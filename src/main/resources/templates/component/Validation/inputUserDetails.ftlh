<#import "inputValidator.ftlh" as validator>
<#import "inputSingleElement.ftlh" as input>

<#macro register isRegisterForm, submitName>
    <@validator.input "${submitName}">
        <div class="form-row">
            <div class="col-md-6 mb-3">
                <label for="name">Name</label>
                <input type="text" name="username" id="name"
                       placeholder="name" class="form-control" required>
                <div class="invalid-feedback">
                    Please provide a valid username.
                </div>
            </div>
        </div>
        <div class="form-row">
            <div class="col-md-6 mb-3">
                <label for="password">Password</label>
                <input type="text" name="password" id="password"
                       placeholder="password" class="form-control" required>
                <div class="invalid-feedback">
                    Please provide a valid password.
                </div>
            </div>
        </div>
        <#if isRegisterForm>
            <div class="form-row">
                <div class="col-md-3 mb-3">
                    <label for="email">Email</label>
                    <input type="text" name="email" id="email" placeholder="email"
                           class="form-control" required>
                    <div class="invalid-feedback">
                        Please provide a valid email.
                    </div>
                </div>
            </div>
        </#if>
    </@validator.input>
</#macro>