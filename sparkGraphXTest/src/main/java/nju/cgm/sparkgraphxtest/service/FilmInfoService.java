package nju.cgm.sparkgraphxtest.service;

import nju.cgm.sparkgraphxtest.model.Actor;
import nju.cgm.sparkgraphxtest.model.FilmInfo;
import nju.cgm.sparkgraphxtest.model.TotalRes;
import nju.cgm.sparkgraphxtest.model.YearRes;
import org.apache.spark.api.java.JavaPairRDD;
import scala.Tuple2;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: Bright Chan
 * @date: 2020/11/20 19:12
 * @description: FilmInfoService
 */
public class FilmInfoService implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    private static final String DB_URL = "jdbc:mysql://host:3306/spark_data?" +
            "useSSL=false&serverTimezone=UTC";

    private Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,"root","root");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public List<FilmInfo> getFilmInfoByYear(String year) {
        List<FilmInfo> res = new ArrayList<>();
        try {
            String sql = "select film_name, film_actors from maoyan_film_info " +
                    "where film_year = ?";
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, year);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                FilmInfo one = new FilmInfo(
                        resultSet.getString("film_name"),
                        Arrays.asList(resultSet.getString("film_actors").split(";")),
                        year);
                res.add(one);
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public List<FilmInfo> getAllFilmInfo() {
        List<FilmInfo> res = new ArrayList<>();
        try {
            String sql = "select film_name, film_actors, film_year from maoyan_film_info";
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                FilmInfo one = new FilmInfo(
                        resultSet.getString("film_name"),
                        Arrays.asList(resultSet.getString("film_actors").split(";")),
                        resultSet.getString("film_year"));
                res.add(one);
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public List<Actor> getFilmActorByYear(String year) {
        List<Actor> res = new ArrayList<>();
        try {
            String sql = "select distinct(actor_name) from maoyan_film_actor " +
                    "where film_year = ?";
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, year);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Actor one = new Actor(resultSet.getString("actor_name"));
                res.add(one);
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public List<Actor> getAllFilmActor() {
        List<Actor> res = new ArrayList<>();
        try {
            String sql = "select distinct(actor_name) from maoyan_film_actor";
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Actor one = new Actor(resultSet.getString("actor_name"));
                res.add(one);
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public void insertBatchYearRes(JavaPairRDD<Long, Tuple2<String, Integer>> data, String year) {
        String sql = "insert into maoyan_film_year_res(actor_name, film_num, year) values(?, ?, ?)";
        data.foreachPartition(tuple2Iterator -> {
            List<YearRes> store = new ArrayList<>();
            while (tuple2Iterator.hasNext()) {
                Tuple2<String, Integer> one = tuple2Iterator.next()._2;
                YearRes oneRes = new YearRes(one._1, one._2);
                store.add(oneRes);
            }
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            for (YearRes one : store) {
                stmt.setString(1, one.getActorName());
                stmt.setInt(2, one.getFilmNum());
                stmt.setString(3, year);
                stmt.execute();
            }
            stmt.close();
        });
    }

    public void insertBatchTotalRes(JavaPairRDD<Long, Tuple2<String, Double>> data) {
        String sql = "insert into maoyan_film_total_res(actor_name, rank_num) values(?, ?)";
        data.foreachPartition(tuple2Iterator -> {
            List<TotalRes> store = new ArrayList<>();
            while (tuple2Iterator.hasNext()) {
                Tuple2<String, Double> one = tuple2Iterator.next()._2;
                TotalRes oneRes = new TotalRes(one._1, one._2);
                store.add(oneRes);
            }
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            for (TotalRes one : store) {
                stmt.setString(1, one.getActorName());
                stmt.setDouble(2, one.getRankNum());
                stmt.execute();
            }
            stmt.close();
        });
    }

}
