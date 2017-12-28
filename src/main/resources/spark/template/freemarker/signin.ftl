<!DOCTYPE HTML>
<html>
<head>
    <title>Mwaka !</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <!--[if lte IE 8]>
    <script src="assets/js/ie/html5shiv.js"></script><![endif]-->
    <link rel="stylesheet" href="assets/css/main.css"/>
    <!--[if lte IE 9]>
    <link rel="stylesheet" href="assets/css/ie9.css"/><![endif]-->
    <!--[if lte IE 8]>
    <link rel="stylesheet" href="assets/css/ie8.css"/><![endif]-->
    <script src='https://www.google.com/recaptcha/api.js'></script>
</head>
<body>
<!-- Wrapper -->
<div id="wrapper">
    <!-- Intro -->
    <section id="intro" class="wrapper style1 fullscreen fade-up">
        <div class="inner" style="text-align: center;width: 500px;height: auto;margin: 0 auto;">
            <form id="register-form" action="/signin" method="post">
                <div class="field">
                    <label for="email">Email</label>
                    <input type="email" required id="email" name="email"/>
                </div>
                <div class="field">
                    <label for="password">Password</label>
                    <input type="password" required id="password" name="password"/>
                </div>
                <div class="g-recaptcha" style="display: inline-flex;" data-callback="resetRecaptcha"
                     data-sitekey="6LcMHjsUAAAAAEKFcqlFCFrlUcP8pqd_-8Gy8JNn"></div>
                <div id="error" style=";color: #ff0000;">
                <#if error??>
                    ${error}
                </#if>
                </div>
                <ul class="actions">
                    <li><input id="register" type="submit" class="button submit" value="Login"></li>
                </ul>
            </form>
        </div>
    </section>
</div>

<!-- Footer -->
<footer id="footer" class="wrapper style1-alt">
    <div class="inner">
        <ul class="menu">
            <li>&copy; Mwaka. All rights reserved.</li>
        </ul>
    </div>
</footer>

<!-- Scripts -->
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/jquery.scrollex.min.js"></script>
<script src="assets/js/jquery.scrolly.min.js"></script>
<script src="assets/js/skel.min.js"></script>
<script src="assets/js/util.js"></script>
<!--[if lte IE 8]>
<script src="assets/js/ie/respond.min.js"></script><![endif]-->
<script src="assets/js/main.js"></script>
</body>
</html>