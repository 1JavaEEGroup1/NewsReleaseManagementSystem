package com.example.newsreleasemanagementsystem.spark;

import com.example.newsreleasemanagementsystem.config.SpringUtil;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.mllib.recommendation.ALS;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;
import org.apache.spark.rdd.RDD;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;

public class Spark {

    private static JavaSparkContext javaSparkContext = SpringUtil.getBean(JavaSparkContext.class);

    public static List<String> spark(Long userId) {
        List<String> list=new ArrayList<String>();
        list.add("1,6,0");
        list.add("2,1,4.5");
        list.add("2,2,9.9");
        list.add("3,3,5.0");
        list.add("3,4,2.0");
        list.add("3,5,5.0");
        list.add("3,6,9.9");
        list.add("4,2,9.9");
        list.add("4,5,0");
        list.add("4,6,0");
        list.add("5,2,9.9");
        list.add("5,3,9.9");
        list.add("5,4,9.9");
        list.add("3,10,5.0");
        list.add("3,11,2.0");
        list.add("3,12,5.0");
        list.add("3,12,9.9");
        list.add("4,14,9.9");
        list.add("4,15,0");
        list.add("4,16,7.0");
        list.add("5,17,9.9");
        list.add("5,18,9.9");
        list.add("5,19,6.9");
//        JavaRDD<String> temp=sc.parallelize(list);
        //上述方式等价于
//        JavaRDD<String> temp2=sc.parallelize(Arrays.asList("a","b","c"));
        System.out.println("牛逼牛逼");
//        SparkConf conf = new SparkConf().setAppName("als").setMaster("local[5]");
//        JavaSparkContext jsc = new JavaSparkContext(conf);
//        JavaRDD<String> lines = jsc.textFile("D:\\NirvanaRebirth\\study\\spark\\recommend.txt");
        JavaRDD<String> lines = javaSparkContext.parallelize(list);
        // 映射
        RDD<Rating> ratingRDD = lines.map(new Function<String, Rating>() {
            public Rating call(String line) throws Exception {
                String[] arr = line.split(",");
                return new Rating(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Double.parseDouble(arr[2]));
            }
        }).rdd();
        MatrixFactorizationModel model = ALS.train(ratingRDD, 10, 10);
        // 通过原始数据进行测试
        JavaPairRDD<Integer, Integer> testJPRDD = ratingRDD.toJavaRDD().mapToPair(new PairFunction<Rating, Integer, Integer>() {
            public Tuple2<Integer, Integer> call(Rating rating) throws Exception {
                return new Tuple2<Integer, Integer>(rating.user(), rating.product());
            }
        });
        // 对原始数据进行推荐值预测
        JavaRDD<Rating> predict = model.predict(testJPRDD);
        System.out.println("原始数据测试结果为：");
        predict.foreach(new VoidFunction<Rating>() {
            public void call(Rating rating) throws Exception {
                System.out.println("UID:" + rating.user() + ",PID:" + rating.product() + ",SCORE:" + rating.rating());
            }
        });
          // 向指定id的用户推荐n件商品
//        Rating[] predictProducts = model.recommendProducts(2, 8);
//        System.out.println("\r 向指定id的用户推荐n件商品");
//        for(Rating r1:predictProducts){
//            System.out.println("UID:" + r1.user() + ",PID:" + r1.product() + ",SCORE:" + r1.rating());
//        }
        // 向指定id的商品推荐给n给用户
//        Rating[] predictUsers = model.recommendUsers(2, 4);
//        System.out.println("\r 向指定id的商品推荐给n给用户");
//        for(Rating r1:predictProducts){
//            System.out.println("UID:" + r1.user() + ",PID:" + r1.product() + ",SCORE:" + r1.rating());
//        }
        // 向所有用户推荐N个商品
//        RDD<Tuple2<Object, Rating[]>> predictProductsForUsers = model.recommendProductsForUsers(3);
//        System.out.println("\r ******向所有用户推荐N个商品******");
//        predictProductsForUsers.toJavaRDD().foreach(new VoidFunction<Tuple2<Object, Rating[]>>() {
//            public void call(Tuple2<Object, Rating[]> tuple2) throws Exception {
//                System.out.println("以下为向id为：" + tuple2._1 + "的用户推荐的商品：");
//                for(Rating r1:tuple2._2){
//                    System.out.println("UID:" + r1.user() + ",PID:" + r1.product() + ",SCORE:" + r1.rating());
//                }
//            }
//        });
        // 将所有商品推荐给n个用户
//        RDD<Tuple2<Object, Rating[]>> predictUsersForProducts = model.recommendUsersForProducts(2);
//        System.out.println("\r ******将所有商品推荐给n个用户******");
//        predictUsersForProducts.toJavaRDD().foreach(new VoidFunction<Tuple2<Object, Rating[]>>() {
//            public void call(Tuple2<Object, Rating[]> tuple2) throws Exception {
//                System.out.println("以下为向id为：" + tuple2._1 + "的商品推荐的用户：");
//                for(Rating r1:tuple2._2){
//                    System.out.println("UID:" + r1.user() + ",PID:" + r1.product() + ",SCORE:" + r1.rating());
//                }
//            }
//        });
        Rating[] Returns = model.recommendProducts(Math.toIntExact(userId), 8);
        System.out.println("\r 向指定id的用户推荐n件商品");
        List<String> returns = new ArrayList<>();
        for(Rating rating : Returns) {
            returns.add("UID:" + rating.user() + ",PID:" + rating.product() + ",SCORE:" + rating.rating());
        }
        return returns;
    }
}

