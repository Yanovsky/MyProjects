package ru.dreamkas.update;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import ru.dreamkas.update.data.FileInfo;
import ru.dreamkas.update.data.Info;
import ru.dreamkas.update.data.Patch;
import ru.dreamkas.update.data.UpdateInfo;
import ru.dreamkas.update.data.VersionInfo;

public class Main {

    private static final String QUERY = "SELECT \n" +
        "v_fr.full_version AS ver_from, \n" +
        "v_to.full_version AS ver_to, \n" +
        "v_to.id,  \n" +
        "ptc.info,\n" +
        "ptc.md5,\n" +
        "ptc.size,\n" +
        "ptc.url\n" +
        "FROM patches ptc\n" +
        "JOIN products prd ON (ptc.product_id = prd.id)\n" +
        "JOIN versions v_to ON (ptc.version_id = v_to.id)\n" +
        "JOIN versions v_fr ON (ptc.version_from_id = v_fr.id)\n" +
        "WHERE prd.id = 1\n" +
        "AND v_to.id > 547\n" +
        "AND v_to.id <= 692\n" +
        "ORDER BY v_to.id";

    public static void main(String[] args) throws Exception {
        JSch jsch = new JSch();
        jsch.addIdentity("D:/YandexDisk/Работа/PUTTY/id_rsa.ppk");
        Session session = jsch.getSession("yanovsky", "update.dreamkas.ru");
        session.setConfig("PreferredAuthentications", "publickey");
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        session.setPortForwardingL(15432, "localhost", 5432);

        String url = "jdbc:postgresql://localhost:15432/update";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "p2vJVwyCU9Kj476R");
        Connection conn = DriverManager.getConnection(url, props);
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(QUERY);

        UpdateInfo updateInfo = new UpdateInfo("start_update");
        Info bugs = new Info("Исправленные ошибки");
        Info news = new Info("Новое в версии");
        List<Patch> patches = new ArrayList<>();
        while (resultSet.next()) {
            String version_from = resultSet.getString("ver_from");
            String version_to = resultSet.getString("ver_to");
            long size = resultSet.getLong("size");
            String md5 = resultSet.getString("md5");
            String path = StringUtils.substringAfterLast(resultSet.getString("url"), "/");
            patches.add(
                new Patch()
                    .setFile(new FileInfo().setSize(size).setMd5(md5).setPatch("patches/" + path))
                    .setVersion(new VersionInfo(version_from, version_to))
            );
            String info = resultSet.getString("info");
            boolean toNews = false;
            for (String s : info.split("\r\n")) {
                if (StringUtils.isBlank(StringUtils.trimToEmpty(s))) {
                    continue;
                }
                if (StringUtils.equalsIgnoreCase(s, "Новое в версии")) {
                    toNews = true;
                } else if (StringUtils.equalsIgnoreCase(s, "Исправленные ошибки")) {
                    toNews = false;
                } else {
                    String value = StringUtils.trimToNull(StringUtils.substringAfter(s, "- "));
                    if (value != null) {
                        if (toNews) {
                            news.getContent().add(value);
                        } else {
                            bugs.getContent().add(value);
                        }
                    }
                }
            }
        }
        conn.close();
        session.disconnect();

        updateInfo.getDescription().getInfo().addAll(Arrays.asList(news,bugs));
        updateInfo.getPatches().addAll(patches);

        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(updateInfo));
    }
}
