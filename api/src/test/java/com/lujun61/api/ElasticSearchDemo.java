package com.lujun61.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lujun61.ApiApplication;
import com.lujun61.beans.entity.User;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @description ES基本用法示例
 * @author Jun Lu
 * @date 2022-08-24 22:02:02
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApiApplication.class})
public class ElasticSearchDemo {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Resource
    private ObjectMapper objectMapper;

    /**
     * @description 在ES中创建索引
     * @author Jun Lu
     * @date 2022-08-24 19:52:31
     */
    @Test
    public void createIndex() {

        try {
            CreateIndexRequest createIndexRequest = new CreateIndexRequest("index1");
            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);

            // 操作成功返回true
            System.out.println(createIndexResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @description 在ES中删除索引
     * @author Jun Lu
     * @date 2022-08-24 19:53:06
     */
    @Test
    public void delIndex() {
        try {
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("index1");
            AcknowledgedResponse delete = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);

            System.out.println(delete.isAcknowledged());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @description 往index中添加Document
     * @author Jun Lu
     * @date 2022-08-24 20:27:07
     */
    @Test
    public void addDocumentToIndex() {

        // 准备数据
        User user = new User();
        user.setUserId("2003");
        user.setUsername("lujun");
        user.setRealname("陆俊");
        user.setNickname("架构师陆俊");

        user = new User();
        user.setUserId("2004");
        user.setUsername("RainLu");
        user.setRealname("路俊");
        user.setNickname("Boos陆");

        //user = new User();
        //user.setUserId("2005");
        //user.setUsername("lujun");
        //user.setRealname("陆俊");
        //user.setNickname("架构师陆俊");

        // 添加数据
        try {
            IndexRequest indexRequest = new IndexRequest("index1");
            indexRequest.id(user.getUserId());
            indexRequest.source(objectMapper.writeValueAsString(user), XContentType.JSON);

            IndexResponse index = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

            System.out.println(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @description 查询ES中的数据
     * @author Jun Lu
     * @date 2022-08-24 20:43:30
     */
    @Test
    public void search() {

        // 指定查询请求的index
        SearchRequest searchRequest = new SearchRequest("index1");

        // 封装查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();

        // 查询条件
        //builder.query(QueryBuilders.matchAllQuery());   // 查询所有数据
        //builder.query(QueryBuilders.matchQuery("username", "俊"));    // 单字段匹配
        builder.query(QueryBuilders.multiMatchQuery("架构师", "username", "nickname"));  // 多字段匹配

        // 分页条件
        builder.from(0);
        builder.size(3);

        // 高亮显示
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        HighlightBuilder.Field field = new HighlightBuilder.Field("nickname");
        highlightBuilder.field(field);
        highlightBuilder.preTags("<lable style='color: red'>");
        highlightBuilder.postTags("</lable>");

        builder.highlighter(highlightBuilder);

        try {
            searchRequest.source(builder);
            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

            // 在ES中，一个hit代表一组数据（Document）
            SearchHits hits = response.getHits();

            // SearchHits提供了迭代器
            Iterator<SearchHit> iterator = hits.iterator();
            List<User> userList = new LinkedList<>();
            while (iterator.hasNext()) {

                SearchHit hit = iterator.next();
                // 获取hit中的Document信息
                User user = objectMapper.readValue(hit.getSourceAsString(), User.class);
                // 获取所匹配到的高亮显示的数据
                HighlightField highlightField = hit.getHighlightFields().get("nickname");
                if (highlightField != null) {
                    Text[] fragments = highlightField.fragments();
                    String s = Arrays.toString(fragments);

                    String replace = s.replace("[", "");
                    String usernameHL = replace.replace("]", "");

                    user.setNickname(usernameHL);
                }
                userList.add(user);

            }

            for (User user : userList) {
                System.out.println(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
