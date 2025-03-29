<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Image Report</title>
</head>
<body>
<h1>Image Report</h1>
<ul>
    <#list images as image>
        <img src="${image.path()}" alt="Image" width="200">
    </#list>
</ul>
</body>
</html>
