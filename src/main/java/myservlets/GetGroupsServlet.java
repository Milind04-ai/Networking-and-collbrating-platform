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

@WebServlet("/getGroups")
public class GetGroupsServlet extends HttpServlet {

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

        response.setContentType(
                "application/json");

        PrintWriter out =
                response.getWriter();

        try{

            Connection con =
                    DBConnection.getConnection();

            String sql =
            "SELECT gc.id,gc.name " +
            "FROM group_chats gc " +
            "JOIN group_chat_members gm " +
            "ON gc.id = gm.chat_id " +
            "WHERE gm.user_id=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, userId);

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

                out.print("\"id\":\""
                        + rs.getString("id")
                        + "\",");

                out.print("\"name\":\""
                        + rs.getString("name")
                        + "\"");

                out.print("}");
            }

            out.print("]");

        }catch(Exception e){

            e.printStackTrace();
        }
    }
}