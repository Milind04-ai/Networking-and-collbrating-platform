package myservlets;

import myutil.DBConnection;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/getGroupMessages")
public class GetGroupMessagesServlet
        extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        if(session == null ||
           session.getAttribute("userId") == null){

            response.setStatus(401);
            return;
        }

        String groupId =
                request.getParameter(
                        "groupId");

        response.setContentType(
                "application/json");

        PrintWriter out =
                response.getWriter();

        try{

            Connection con =
                    DBConnection.getConnection();

            String sql =
            "SELECT " +
            "gm.sender_id, " +
            "u.full_name, " +
            "gm.content, " +
            "gm.sent_at " +
            "FROM group_messages gm " +
            "JOIN users u " +
            "ON gm.sender_id=u.id " +
            "WHERE gm.chat_id=? " +
            "ORDER BY gm.sent_at";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, groupId);

            ResultSet rs =
                    ps.executeQuery();

            out.print("[");

            boolean first = true;

            while(rs.next()){

                if(!first){

                    out.print(",");
                }

                first = false;

                String content =
                        rs.getString(
                                "content");

                content = content
                        .replace("\\", "\\\\")
                        .replace("\"", "\\\"")
                        .replace("\n", "\\n")
                        .replace("\r", "");

                out.print("{");

                out.print(
                "\"senderId\":\""
                + rs.getString(
                    "sender_id")
                + "\",");

                out.print(
                "\"senderName\":\""
                + rs.getString(
                    "full_name")
                + "\",");

                out.print(
                "\"content\":\""
                + content
                + "\",");

                out.print(
                "\"sentAt\":\""
                + rs.getString(
                    "sent_at")
                + "\"");

                out.print("}");
            }

            out.print("]");

        }catch(Exception e){

            e.printStackTrace();
        }
    }
}