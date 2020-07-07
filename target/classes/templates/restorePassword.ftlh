<#import "component/basic.ftlh" as base>

<@base.page>
    <nav class="nav">
        <a href="/" class="nav-link">Home</a>
        <a href="/login" class="nav-link">Back</a>
    </nav>
    <div class="container">
        <form method="post" enctype="multipart/form-data">
            <div class="container">
                <div class="row">
                    <div class="col-sm-6">
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <input type="text" name="username" placeholder="username..."/>
                    </div>
                </div>
                <button type="submit">Confirm</button>
            </div>
        </form>
    </div>
</@base.page>