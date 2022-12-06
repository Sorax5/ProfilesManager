package fr.soraxdubbing.profilsmanagercore.manager;

import fr.soraxdubbing.profilsmanagercore.ProfilsManagerCore;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.util.List;

public class DataSourceProvider {
    private static BasicDataSource oneDataSource;

    private DataSourceProvider(){
    }

    public static DataSource getOneDataSourceInstance() {
        if (oneDataSource==null) {
            List<String> config = ProfilsManagerCore.getInstance().getConfig().getStringList("database");
            oneDataSource = new BasicDataSource();
            oneDataSource.setDriverClassName("com.mysql.jdbc.Driver");
            oneDataSource.setUrl(String.format("jdbc:mysql:thin:@%s:%s:%s", config.get(0), config.get(1), config.get(2)));
            oneDataSource.setUsername(config.get(3));
            oneDataSource.setPassword(config.get(4));
            oneDataSource.setInitialSize(5) ;
        }
        return oneDataSource;
    }
}
