<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
<head>
    <title>Create Project</title>
</head>
<body>

<h1>Create Project</h1>

<form action="<%=request.getContextPath()%>/projects/create"
      method="post">

    <p>
        Title
    </p>

    <input type="text"
           name="title"
           required>

    <br><br>

    <p>
        Description
    </p>

    <textarea name="description"
              rows="5"
              cols="40"></textarea>

    <br><br>

    <p>
        Skills / Tags
    </p>

    <input type="text"
           name="skills">

    <br><br>

    <button type="submit">
        Create Project
    </button>

</form>

</body>
</html>