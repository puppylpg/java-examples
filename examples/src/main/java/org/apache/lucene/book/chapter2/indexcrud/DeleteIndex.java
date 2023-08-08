package org.apache.lucene.book.chapter2.indexcrud;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.book.chapter2.ik.IKAnalyzer6x;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class DeleteIndex {
    public static void main(String[] args) {
        // 删除title中含有“美国”的文档
        deleteDoc("title", "美国");
    }

    public static void deleteDoc(String field, String key) {
        Analyzer analyzer = new IKAnalyzer6x();
        IndexWriterConfig icw = new IndexWriterConfig(analyzer);
        Path indexPath = Paths.get(System.getProperty("java.io.tmpdir") + "/lucene");
        Directory directory;
        try {
            directory = FSDirectory.open(indexPath);
            IndexWriter indexWriter = new IndexWriter(directory, icw);
            indexWriter.deleteDocuments(new Term(field, key));
            indexWriter.commit();
            indexWriter.close();
            System.out.println("删除完成!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
