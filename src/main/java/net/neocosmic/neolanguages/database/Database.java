package net.neocosmic.neolanguages.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.neocosmic.neolanguages.language.PlayerLanguage;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.concurrent.CompletableFuture;

public class Database {

    private final HikariDataSource dataSource;

    public Database(String host, String database, String username, String password) {
        final HikariConfig config = new HikariConfig();

        config.setJdbcUrl("jdbc:mysql://" + host + "/" + database);

        config.setUsername(username);
        config.setPassword(password);

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(10);
        config.setConnectionTimeout(5000);
        config.setIdleTimeout(60000);

        dataSource = new HikariDataSource(config);
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

    public void createTable() {
        String table = """
                CREATE TABLE IF NOT EXISTS languages (
                    uuid VARCHAR(36) PRIMARY KEY,
                    language VARCHAR(2)
                )
                """;

        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(table);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CompletableFuture<@NotNull PlayerLanguage> getPlayerLanguage(String uuid) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT language FROM languages WHERE uuid = ?")) {
                preparedStatement.setString(1, uuid);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return PlayerLanguage.valueOf(resultSet.getString("language"));
                }

                return PlayerLanguage.getDefault();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return PlayerLanguage.getDefault();
        });
    }

    public CompletableFuture<Boolean> setPlayerLanguage(@NotNull String uuid, @NotNull PlayerLanguage language) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO languages (uuid, language) VALUES (?, ?) ON DUPLICATE KEY UPDATE language = ?")) {
                preparedStatement.setString(1, uuid);
                preparedStatement.setString(2, language.name());
                preparedStatement.setString(3, language.name());

                preparedStatement.executeUpdate();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        });
    }

}
