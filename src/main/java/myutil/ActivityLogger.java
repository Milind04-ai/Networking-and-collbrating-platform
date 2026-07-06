package myutil;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ActivityLogger {

    public static void log(
            String projectId,
            String userId,
            String activity){

        try{

            Connection con =
                    DBConnection.getConnection();

            String sql =
            "INSERT INTO project_activities " +
            "(project_id,user_id,activity) " +
            "VALUES(?,?,?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, projectId);
            ps.setString(2, userId);
            ps.setString(3, activity);

            ps.executeUpdate();

        }catch(Exception e){

            e.printStackTrace();
        }
    }
}