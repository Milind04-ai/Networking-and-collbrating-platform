package myservlets;

import myclasses.Message;
import myutil.DBConnection;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/getMessages")
public class GetMessagesServlet
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

        String userId =
                session.getAttribute("userId")
                       .toString();

        String receiverId =
                request.getParameter(
                        "receiverId");

        response.setContentType(
                "application/json");

        PrintWriter out =
                response.getWriter();

        try{

            Connection con =
                    DBConnection.getConnection();
            
            String statusSql =
"SELECT is_online,last_seen " +
"FROM user_status " +
"WHERE user_id=?";

PreparedStatement statusPs =
        con.prepareStatement(statusSql);

statusPs.setString(1, receiverId);

ResultSet statusRs =
        statusPs.executeQuery();
boolean isOnline = false;
String lastSeen = "";

if(statusRs.next()){

    isOnline =
        statusRs.getBoolean(
            "is_online");

    Timestamp ts =
        statusRs.getTimestamp(
            "last_seen");

    if(ts != null){

        lastSeen =
            ts.toString();
    }
}

            String sql =
            "SELECT * FROM direct_messages " +
            "WHERE (sender_id=? AND receiver_id=?) " +
            "OR (sender_id=? AND receiver_id=?) " +
            "ORDER BY sent_at";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, userId);
            ps.setString(2, receiverId);
            ps.setString(3, receiverId);
            ps.setString(4, userId);

            ResultSet rs =
                    ps.executeQuery();

            out.print("[");

            boolean first = true;

            while(rs.next()){

                if(!first){
                    out.print(",");
                }

                first = false;

                out.print("{");

                out.print("\"senderId\":\""
                        + rs.getString(
                            "sender_id")
                        + "\",");

                String content =
        rs.getString("content");

content = content
        .replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\n", "\\n")
        .replace("\r", "");

out.print("\"content\":\""
        + content
        + "\",");

                out.print("\"sentAt\":\""
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