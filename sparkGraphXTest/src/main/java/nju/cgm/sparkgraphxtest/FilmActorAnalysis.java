package nju.cgm.sparkgraphxtest;

import nju.cgm.sparkgraphxtest.model.Actor;
import nju.cgm.sparkgraphxtest.model.FilmInfo;
import nju.cgm.sparkgraphxtest.service.FilmInfoService;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.graphx.Edge;
import org.apache.spark.graphx.Graph;
import org.apache.spark.graphx.VertexRDD;
import org.apache.spark.storage.StorageLevel;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.MultiNode;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;
import scala.Tuple2;
import scala.reflect.ClassTag$;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Bright Chan
 * @date: 2020/11/10 20:12
 * @description: FilmActorAnalysis
 */
public class FilmActorAnalysis {

    public static void main(String[] args) {
        FilmInfoService filmInfoService = new FilmInfoService();

        // 构建图的点
        List<Actor> filmActors = filmInfoService.getAllFilmActor();
        List<Tuple2<Object, Actor>> vertexList = new ArrayList<>();
        long totalVertexNum = 1;
        for (Actor one : filmActors) {
            vertexList.add(new Tuple2<>(totalVertexNum, one));
            totalVertexNum++;
        }

        // 构建图的边
        List<FilmInfo> filmInfos = filmInfoService.getAllFilmInfo();
        List<Edge<String>> edgeList = new ArrayList<>();
        for (FilmInfo one : filmInfos) {
            List<String> actors = one.getFilmActors();
            if (actors.size() >= 2) {
                String actorName1 = actors.get(0);
                String actorName2 = actors.get(1);
                long srcId = 0, dstId = 0;
                for (Tuple2<Object, Actor> tuple : vertexList) {
                    if (srcId > 0 && dstId > 0) break;
                    if (actorName1.equals(tuple._2.getName())) {
                        srcId = (long) tuple._1;
                    }
                    if (actorName2.equals(tuple._2.getName())) {
                        dstId = (long) tuple._1;
                    }
                }
                edgeList.add(new Edge<>(srcId, dstId, one.getFilmName()));
            }
        }

        // 构建图
        SparkConf conf = new SparkConf().setAppName("graphExample").setMaster("local[*]");
        JavaSparkContext ctx = new JavaSparkContext(conf);
        JavaRDD<Tuple2<Object,Actor>> vertexRdd = ctx.parallelize(vertexList);
        JavaRDD<Edge<String>> edgeRdd = ctx.parallelize(edgeList);

        Actor defaultActor = new Actor("default");
        Graph<Actor,String> srcGraph = Graph.apply(
                vertexRdd.rdd(),
                edgeRdd.rdd(),
                defaultActor,
                StorageLevel.MEMORY_AND_DISK(),
                StorageLevel.MEMORY_AND_DISK(),
                ClassTag$.MODULE$.apply(Actor.class),
                ClassTag$.MODULE$.apply(String.class));

        //可视化
        MultiGraph graphStream = new MultiGraph("GraphStream");
        graphStream.addAttribute("ui.stylesheet",
                "url(src/main/resources/style/graphStyle.css)");
        graphStream.addAttribute("ui.quality");
        graphStream.addAttribute("ui.antialias");
        //加载顶点到GraphStream中
        List<Tuple2<Object,Actor>> verList = srcGraph.vertices().toJavaRDD().collect();
        for (Tuple2<Object,Actor> tuple : verList) {
            String vid = tuple._1 + "";
            String name = tuple._2.getName();
            MultiNode node = graphStream.addNode(vid);
            node.addAttribute("ui.label",vid + "\n" + name);
        }
        //加载边到GraphStream中
        List<Edge<String>> edList = srcGraph.edges().toJavaRDD().collect();
        long totalEdgeNum = 0;
        for (Edge<String> edge : edList) {
            String sid = Long.toString(edge.srcId());
            String did = Long.toString(edge.dstId());
            String filmName = edge.attr();
            graphStream.addEdge(totalEdgeNum + "", sid, did, true)
                    .addAttribute("ui.label", filmName);
            totalEdgeNum++;
        }

        Viewer viewer = graphStream.display();
        View view = viewer.getDefaultView();
        view.resizeFrame(800,600);

        // 原图的顶点集合
        VertexRDD<Actor> srcVertices = srcGraph.vertices();
        JavaPairRDD<Long, String> srcVerticesRDD = srcVertices.toJavaRDD()
                .mapToPair(f -> new Tuple2<>((Long) f._1, f._2.getName()));

        // pageRank
        double tol = 0.1;
        double resetProb =0.15;

        Graph<Object, Object> pageRankGraph = srcGraph.ops().pageRank(tol, resetProb);
        VertexRDD<Object> ranks = pageRankGraph.vertices();
        JavaPairRDD<Long, Double> ranksRdd = ranks.toJavaRDD()
                        .mapToPair(tupleObject ->
                                new Tuple2<>((Long) tupleObject._1, (Double) tupleObject._2));
        JavaPairRDD<Long, Tuple2<String, Double>> res = srcVerticesRDD.join(ranksRdd);

        // 保存结果至数据库
        filmInfoService.insertBatchTotalRes(res);
    }

}
