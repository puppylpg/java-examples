package org.apache.lucene.book.chapter2.indexcrud;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.book.chapter2.ik.IKAnalyzer6x;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class CreateIndex {

    public static void main(String[] args) {

        // 创建News对象
        News news1 = new News();
        news1.setId(1);
        news1.setTitle("IKAnalyzer真不戳");
        news1.setContent("IKAnalyzer是一个开源的,基于java语言开发的轻量级的中文分词工具包。美国总统唐纳德·特朗普都在用！");
        news1.setReply(672);

        News news2 = new News();
        news2.setId(2);
        news2.setTitle("北大迎4380名新生 农村学生700多人近年最多");
        news2.setContent("昨天，北京大学迎来4380名来自全国各地及数十个国家的本科新生。其中，农村学生共700余名，为近年最多...");
        news2.setReply(995);

        News news3 = new News();
        news3.setId(3);
        news3.setTitle("特朗普宣誓(Donald Trump)就任美国第45任总统");
        news3.setContent("当地时间1月20日，唐纳德·特朗普在美国国会宣誓就职，正式成为美国第45任总统。");
        news3.setReply(1872);

        Date start = new Date();
        System.out.println("**********开始创建索引**********");
        // 创建IK分词器
        Analyzer analyzer = new IKAnalyzer6x();
        // config: analyzer, open mode
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setOpenMode(OpenMode.CREATE);
        Directory indexDir;

        IndexWriter inWriter;
        Path indexPath = Paths.get(System.getProperty("java.io.tmpdir") + "/lucene");

        try {
            if (!Files.exists(indexPath)) {
                Files.createDirectories(indexPath);
            }
            indexDir = FSDirectory.open(indexPath);

            // index writer: location, config
            inWriter = new IndexWriter(indexDir, config);

            // field
            FieldType idType = new FieldType();
            idType.setIndexOptions(IndexOptions.DOCS);
            idType.setStored(true);

            FieldType titleType = new FieldType();
            titleType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
            // lucene不像es会保存个_source。默认是不store的，只在索引中有，但不保留原始值。此时使用luke可以看到该doc的title为空值
//            titleType.setStored(true);
            titleType.setTokenized(true);

            FieldType contentType = new FieldType();
            contentType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
            contentType.setStored(true);
            contentType.setTokenized(true);
            // TermVector是一种存储文档中词项及其相关信息的数据结构（一个拥有多个field的简单对象）。TermVector可以存储每个词项在文档中出现的位置、偏移量、词频、文档频率等信息。TermVector可以用于实现某些基于词项的检索算法，如短语查询、高亮显示等。
            // 用来计算相似度的是KNNVector
            contentType.setStoreTermVectors(true);
            contentType.setStoreTermVectorPositions(true);
            contentType.setStoreTermVectorOffsets(true);
//            contentType.setDocValuesType(DocValuesType.SORTED);

            // document: to index
            Document doc1 = new Document();
            doc1.add(new Field("id", String.valueOf(news1.getId()), idType));
            doc1.add(new Field("title", news1.getTitle(), titleType));
            doc1.add(new Field("content", news1.getContent(), contentType));
            doc1.add(new IntPoint("reply", news1.getReply()));
            doc1.add(new StoredField("reply_display", news1.getReply()));

            Document doc2 = new Document();
            doc2.add(new Field("id", String.valueOf(news2.getId()), idType));
            doc2.add(new Field("title", news2.getTitle(), titleType));
            doc2.add(new Field("content", news2.getContent(), contentType));
            doc2.add(new IntPoint("reply", news2.getReply()));
            doc2.add(new StoredField("reply_display", news2.getReply()));

            Document doc3 = new Document();
            doc3.add(new Field("id", String.valueOf(news3.getId()), idType));
            doc3.add(new Field("title", news3.getTitle(), titleType));
            doc3.add(new Field("content", news3.getContent(), contentType));
            doc3.add(new IntPoint("reply", news3.getReply()));
            doc3.add(new StoredField("reply_display", news3.getReply()));

            inWriter.addDocument(doc1);
            inWriter.addDocument(doc2);
            inWriter.addDocument(doc3);

            // create index
            inWriter.commit();

            inWriter.close();
            indexDir.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Date end = new Date();
        System.out.println("索引文档用时:" + (end.getTime() - start.getTime()) + " milliseconds");
        System.out.println("**********索引创建完成**********");
    }
}
