<#macro input submitName>
    <div class="container">
    <form method="post" enctype="multipart/form-data" class="needs-validation" novalidate>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <#nested>
        <button type="submit" class="btn btn-primary">${submitName}</button>
    </form>

    <script>
        (function () {
            'use strict';
            window.addEventListener('load', function () {
                var forms = document.getElementsByClassName('needs-validation');
                var validation = Array.prototype.filter.call(forms, function (form) {
                    form.addEventListener('submit', function (event) {
                        if (form.checkValidity() === false) {
                            event.preventDefault();
                            event.stopPropagation();
                        }
                        form.classList.add('was-validated');
                    }, false);
                });
            }, false);
        })();
    </script>
</#macro>