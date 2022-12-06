package fr.soraxdubbing.profilsmanagercore.manager;

import fr.soraxdubbing.profilsmanagercore.CraftUser.CraftUser;

import java.sql.*;
import java.util.UUID;

public class MySqlManager extends DataManager {

    private Connection connection;

    public MySqlManager(){
        try(Connection connection = DataSourceProvider.getOneDataSourceInstance().getConnection()){
            this.connection = connection;
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (uuid VARCHAR(36), user CraftUser)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CraftUser load(UUID uuid) {
        CraftUser user = null;
        try(Connection connection = DataSourceProvider.getOneDataSourceInstance().getConnection()){
            String query = "SELECT * FROM users WHERE uuid = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setLong(1, Long.parseLong(uuid.toString()));

            ResultSet rs = pstmt.executeQuery();
            rs.next();

            user = rs.getObject(1,CraftUser.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void save(CraftUser user) {
        try(Connection connection = DataSourceProvider.getOneDataSourceInstance().getConnection()){
            String query = "INSERT INTO users (object_value) VALUES (?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setObject(1, user);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reload() {

    }
}
