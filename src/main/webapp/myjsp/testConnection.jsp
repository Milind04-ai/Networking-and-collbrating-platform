<html>
<body>

<h2>Send Connection Request</h2>

<form action="<%=request.getContextPath()%>/sendConnection"
      method="post">

    Receiver User ID:

    <input type="text"
           name="receiverId">

    <br><br>

    <button type="submit">
        Connect
    </button>

</form>

</body>
</html>